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

    //各参数
    double skillWeight = 0.6;//技能点覆盖率的权重
    double rateWeight = 0.1;//雇主整体评分的权重
    double peerWeight = 0.3;//同行评分的权重
    double defaultRate = 2.5;//无同行评分时默认分数
    double blackRate = 2; //曾有过合作并当时评分低于blackRate的雇主将不被考虑
    int fullRate = 5;//评分的满分

    List<Job> normalSuggest() {
        List<Job> jobs = jobService.getJobs();//getCurrentJobs();
        List<Job> jobs1 = new ArrayList<>();
        for (int i = 0; i < 8; i++)
            jobs1.add(jobs.get(i));
        return jobs1;
    }

    public List<Job> getSuggest(int userId) {
        List<Job> jobs = jobService.getCurrentJobs();

        //未登录或无相关资料的随机推荐job
        if (userId == 0) return normalSuggest();
        User user = userRepository.findById(userId).get();
        if (jobs.size() <= 0 || user == null)
            return normalSuggest();
        List<String> skills = user.getSkills();
        if (skills.size() <= 0)
            return normalSuggest();

        List<Integer> peers = getPeers(skills, userId);//拥有相同技能的人
        List<Integer> blackList = getBlackList(userId);

        //获得scores
        jobs.forEach(job -> {
//        for(int i=0;i<100;i++){
//            Job job=jobs.get(i);
            if (!blackList.contains(job.getEmployerId())) {
//            System.out.println(job.getId());
                JScore item = new JScore();
                item.job = job;
                item.score = 0;
                scores.add(item);
                markBySkill(skills);
                markByRate();
                markByPeer(peers);
//            }}
            }
        });

        List<Job> suggestJob = new ArrayList<>();
        Collections.sort(scores);
        for (int i = 0; i < 8; i++) {
//            Job j = jobRepository.findById(scores.get(i).jobId).get();
            suggestJob.add(scores.get(i).job);
        }

        return suggestJob;
    }

    void markBySkill(List<String> skills) {
        scores.forEach(item -> {
            List<String> cur = item.job.getSkills();
            double allNum = cur.size();
            cur.retainAll(skills);//cur取交集
            double coverNum = cur.size();
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
            if (user.getId() != me && (cur == skills || skills.containsAll(cur))) {
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
                if (peers.contains(job.getEmployerId())) {
                    allRate += job.getEmployerRate();
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
