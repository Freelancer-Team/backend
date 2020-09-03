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
    String jobId;
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
    private UserRepository userRepository;
    private JobService jobService;

    List<JScore> scores;

    //各参数
    double skillWeight = 0.6;
    double rateWeight = 0.1;
    double peerWeight = 0.3;

    List<Job> getSuggest(int userId) {
        List<Job> jobs = jobService.getCurrentJobs();
        User user = userRepository.findById(userId).get();

        List<String> skills = user.getSkills();
        List<User> peers = getPeers(skills);
        List<Integer> blackList = getBlackList(userId);

        //获得scores
        jobs.forEach(job -> {
            if (!blackList.contains(job.getEmployerId())) {
                JScore item = new JScore();
                item.jobId = job.getId();
                item.score=0;
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
            Job j = jobRepository.findById(scores.get(i).jobId).get();
            suggestJob.add(j);
        }

        return suggestJob;
    }

    void markBySkill(List<String> skills) {

    }

    List<User> getPeers(List<String> skills) {
        List<User> peers = new ArrayList<>();

        return null;
    }

    void markByEmployer(List<User> peers) {

    }

    List<Integer> getBlackList(int userId) {
        List<Job> mine = jobRepository.findAsEmployee(userId);
        List<Integer> black = new ArrayList<>();
        mine.forEach(job -> {
            if (job.getEmployeeRate() <= 2) black.add(job.getEmployerId());
        });
        return black;
    }

}
