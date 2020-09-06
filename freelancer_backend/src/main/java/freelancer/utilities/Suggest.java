package freelancer.utilities;

import freelancer.entity.Job;
import freelancer.entity.User;
import freelancer.service.JobService;
import freelancer.repository.JobRepository;
import freelancer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class JScore implements Comparable<JScore> {
    Job job;
    double score = 0;

    @Override
    public int compareTo(JScore js) {           //重写Comparable接口的compareTo方法，
        int ans = 0;
        double tmp = js.score - this.score;//降序排列
        if (tmp > 0) ans = 1;
        else if (tmp < 0) ans = -1;
        return ans;
    }
};

public class Suggest {
    @Autowired
    private JobRepository jobRepository = SpringContextUtil.getBean(JobRepository.class);
    @Autowired
    private UserRepository userRepository = SpringContextUtil.getBean(UserRepository.class);
    @Autowired
    private JobService jobService = SpringContextUtil.getBean(JobService.class);

    List<JScore> scores = new ArrayList<>();
//    List<Job> jobs=new ArrayList<>();

    //各参数
    int groupNum = 20;//每组job的数量
    double skillWeight = 0.6;//技能点覆盖率的权重
    double rateWeight = 0.1;//雇主整体评分的权重
    double peerWeight = 0.3;//同行评分的权重
    double defaultRate = 2.5;//无同行评分时默认分数
    double blackRate = 2; //曾有过合作并当时评分低于blackRate的雇主将不被考虑
    int fullRate = 5;//评分的满分

    List<Job> normalSuggest() {
        List<Job> jobs = jobService.getCurrentJobs();
        List<Job> jobs1 = new ArrayList<>();
        for (int i = 0; i < 8; i++)
            jobs1.add(jobs.get(i));
        return jobs1;
    }

    List<Job> sadSuggest() {
        List<Job> jobs = jobRepository.findAll();
        List<Job> jobs1 = new ArrayList<>();
        for (int i = 0; i < 8; i++)
            jobs1.add(jobs.get(i));
        return jobs1;
    }

    List<Job> addSuggest(List<Job> jobs) {
        List<Job> addJobs = jobService.getCurrentJobs();
        List<Job> jobs1 = new ArrayList<>();
        jobs1.addAll(jobs);
        for (int i = jobs.size(); i < 8; i++) {
            jobs1.add(addJobs.get(i));
        }
        return jobs1;
    }

    public List<Job> getSuggest(int userId, int cnt) {
        List<Job> jobs = jobService.getCurrentJobs();
        if (jobs.size() <= 0)
            return sadSuggest();
        //未登录或无相关资料的随机推荐job
        if (userId == 0) return normalSuggest();
        User user = userRepository.findById(userId).get();
        List<String> skills = user.getSkills();
        if (skills.size() <= 0)
            return normalSuggest();
        //筛出有技能的job
        jobs = filterBySkills(skills, jobs);
        if (jobs.size() <= 8) return addSuggest(jobs);

        List<Integer> peers = getPeers(skills, userId);//拥有相同技能的人
        List<Integer> blackList = getBlackList(userId);

        jobs = filterByBlack(jobs, blackList);
        if (jobs.size() <= 8) return addSuggest(jobs);

        int begin = cnt * groupNum;
        int end = (cnt + 1) * groupNum;
        int sum = jobs.size();
        if (sum < groupNum) end = begin + sum;
        //获得scores
//        jobs.forEach(job -> {
        for (int i = begin; i < end; i++) {
            Job job = jobs.get(i % sum);
            JScore item = new JScore();
            item.job = job;
            item.score = 0;
            scores.add(item);
            markBySkill(skills);
            markByRate();
            markByPeer(peers);
        }
//        });

        List<Job> suggestJob = new ArrayList<>();
        Collections.sort(scores);
        for (int i = 0; i < 8; i++) {
            suggestJob.add(scores.get(i).job);
        }

        return suggestJob;
    }

    List<Job> filterBySkills(List<String> skills, List<Job> jobs) {
        List<Job> newJobs = new ArrayList<>();
        jobs.forEach(item -> {
            List<String> cur = item.getSkills();
            List<String> u = new ArrayList<>();
            u.addAll(cur);
            u.retainAll(skills);//cur取交集
            if (u.size() > 0) newJobs.add(item);
        });
        System.out.println(newJobs.size());
        return newJobs;
    }

    void markBySkill(List<String> skills) {
        scores.forEach(item -> {
            List<String> cur = item.job.getSkills();
            double allNum = cur.size();
            List<String> u = new ArrayList<>();
            u.addAll(cur);
            u.retainAll(skills);//cur取交集
            double coverNum = u.size();
            if (allNum == 0) allNum = coverNum;
            item.score += ((coverNum / allNum) * 100 * skillWeight);
        });
    }

    void markByRate() {
        scores.forEach(item -> {
            int cur = item.job.getEmployerId();//雇主id
            User e = userRepository.findById(cur).get();
            double rate = e.getEmployerRate();
            item.score += ((rate / fullRate) * 100 * rateWeight);
        });
    }

    List<Integer> getPeers(List<String> skills, int me) {
        List<Integer> peers = new ArrayList<>();
        List<User> all = userRepository.findAll();
        all.forEach(user -> {
            List<String> cur = user.getSkills();
            if (user.getId() != me && skills.containsAll(cur)) {
                peers.add(user.getId());
            }
        });
        return peers;
    }

    void markByPeer(List<Integer> peers) {
        scores.forEach(item -> {
            int e = item.job.getEmployerId();
            List<Job> historyJobs = jobRepository.findAsEmployer(e);
            int l = historyJobs.size();
            double allRate = 0;
            int sum = 0;
            for (int i = 0; i < l; i++) {
                Job job = historyJobs.get(i);
                if (peers.contains(job.getEmployeeId())) {
                    allRate += job.getEmployeeRate();
                    sum++;
                }
            }
            if (sum != 0) item.score += (((allRate / sum) / fullRate) * 100 * peerWeight);
            else item.score += ((defaultRate / fullRate) * 100 * peerWeight);
        });
    }

    List<Integer> getBlackList(int userId) {
        List<Job> mine = jobRepository.findAsEmployee(userId);
        List<Integer> black = new ArrayList<>();
        mine.forEach(job -> {
            if (job.getEmployeeRate() <= blackRate) black.add(job.getEmployerId());
        });
        return black;
    }

    List<Job> filterByBlack(List<Job> jobs, List<Integer> black) {
        List<Job> newJobs = new ArrayList<>();
        jobs.forEach(item -> {
            if (!black.contains(item.getEmployerId())) newJobs.add(item);
        });
        if (newJobs.size() >= 8) return newJobs;
        else return jobs;
    }

//    void sort(){
    //排序
//        double max = 0;
//        double min = 10000;
//        int sum = scores.size();
//        List<JScore> suggest = new ArrayList<>();
//        for (int i = 0; i < sum; i++) {
//            JScore cur = scores.get(i);
//            double score = cur.score;
//            if (i < 8) {
//                if (score > max) max = score;
//                if (score < min) min = score;
//                suggest.add(cur);
//                Collections.sort(suggest);//排序
//            } else {
//                if (score > max) {
//                    max = score;
//                    suggest.add(cur);
//                    //未完成
//                }
//            }
//        }
//    }

}
