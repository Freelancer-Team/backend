package freelancer.utilities;

import freelancer.entity.Job;
import freelancer.entity.Skill;
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
    private JobRepository jobRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JobService jobService;

    List<JScore> scores;

    //各参数
    double skillWeight = 0.6;//技能点覆盖率的权重
    double rateWeight = 0.1;//雇主整体评分的权重
    double peerWeight = 0.3;//同行评分的权重
    double blackRate = 2; //曾有过合作并当时评分低于blackRate的雇主将不被考虑

    List<Job> normalSuggest() {
        List<Job> jobs = jobService.getCurrentJobs();
        List<Job> jobs1 = new ArrayList<>();
        for (int i = 0; i < 8; i++)
            jobs1.add(jobs.get(i));
        return jobs1;
    }

    public List<Job> getSuggest(int userId) {
        List<Job> jobs = jobService.getCurrentJobs();
        User user = userRepository.findById(userId).get();

        List<String> skills = user.getSkills();
        if (skills.size() <= 0 || userId == 0)
            return normalSuggest();//未登录或无相关资料的随机推荐job
        List<User> peers = getPeers(skills, userId);//拥有相同技能的人
        List<Integer> blackList = getBlackList(userId);

        //获得scores
        jobs.forEach(job -> {
            if (!blackList.contains(job.getEmployerId())) {
                JScore item = new JScore();
                item.job = job;
                item.score = 0;
                scores.add(item);
                markBySkill(skills);
                markByEmployer(peers);
            }
        });

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
            double allNum=cur.size();
            cur.retainAll(skills);//cur取交集
            double coverNum=cur.size();
            item.score+=((coverNum/allNum)*100*skillWeight);
        });
    }

    List<User> getPeers(List<String> skills, int me) {
        List<User> peers = new ArrayList<>();
        List<User> all = userRepository.findAll();
        all.forEach(user -> {
            List<String> cur = user.getSkills();
            if (user.getId() != me && (cur == skills || skills.containsAll(cur))) {
                peers.add(user);
            }
        });
        return peers;
    }

    void markByEmployer(List<User> peers) {

    }

    List<Integer> getBlackList(int userId) {
        List<Job> mine = jobRepository.findAsEmployee(userId);
        List<Integer> black = new ArrayList<>();
        mine.forEach(job -> {
            if (job.getEmployeeRate() <= blackRate) black.add(job.getEmployerId());
        });
        return black;
    }

}
