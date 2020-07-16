package freelancer.controller;
import freelancer.entity.Job;
import freelancer.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class JobController {

    @Autowired
    private JobService jobService;

    //获得所有job
    @RequestMapping("/getJobs")
    public List<Job> getJobs() {
        return jobService.getJobs();
    }


    //保存job
    @RequestMapping("/saveJob")
    public Job saveJob(@RequestBody Job job) {
        return jobService.saveJob(job);
    }


    //使用id获得job
    @RequestMapping("/getJob")
    public Job getJob(@RequestParam("id") String id) {
        return jobService.getJob(id);
    }


    //生成数据使用
    @RequestMapping("/createRelation")
    public void createRelation(){jobService.createRelation();}


    //雇主指定雇员接受任务
    @RequestMapping("/assignJob")
    public Job assignJob(@RequestParam("userId") int userId, @RequestParam("userName") String userName,@RequestParam("jobId") String jobId)
    {
        return jobService.assignJob(userId,userName,jobId);
    }
  }
