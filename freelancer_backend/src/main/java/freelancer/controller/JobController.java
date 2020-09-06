package freelancer.controller;
import freelancer.entity.Job;
import freelancer.security.ManagerLoginToken;
import freelancer.security.PassToken;
import freelancer.security.UserLoginToken;
import freelancer.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class JobController {

    @Autowired
    private JobService jobService;

    //获得推荐job
    @PassToken
    @PostMapping("/getSuggestJobs")
    public List<Job> getSuggestJobs(@RequestParam("userId") int userId,@RequestParam("cnt") int cnt) {return jobService.getSuggestJobs(userId,cnt);}

    //获得作为雇员的工作信息
    @UserLoginToken
    @GetMapping("/getEmployeeJob")
    public List<Job> getEmployeeJob(@RequestParam("userId") int userId){
        return jobService.getEmployeeJob(userId);
    }

    //获得作为雇主的工作信息
    @UserLoginToken
    @GetMapping("/getEmployerJob")
    public List<Job> getEmployerJob(@RequestParam("userId") int userId){
        return jobService.getEmployerJob(userId);
    }

    //获得所有job
    @ManagerLoginToken
    @GetMapping("/getJobs")
    public List<Job> getJobs() {
        return jobService.getJobs();
    }

    //获得可用job
    @PassToken
    @GetMapping("/getCurrentJobs")
    public List<Job> getCurrentJobs(){return jobService.getCurrentJobs();}

    //保存job
    @UserLoginToken
    @PostMapping("/saveJob")
    public Job saveJob(@RequestBody Job job) {
        return jobService.saveJob(job);
    }

    //使用id获得job
    @PassToken
    @PostMapping("/getJob")
    public Job getJob(@RequestParam("id") String id) {
        return jobService.getJob(id);
    }

    //更新job状态
    @UserLoginToken
    @PostMapping("/setJobState")
    public void setJobState(@RequestParam("jobId") String jobId,@RequestParam("state") int state){jobService.setJobState(jobId,state);}

    //雇主指定雇员接受任务
    @UserLoginToken
    @PostMapping("/assignJob")
    public Job assignJob(@RequestParam("userId") int userId, @RequestParam("userName") String userName,@RequestParam("jobId") String jobId)
    {
        return jobService.assignJob(userId,userName,jobId);
    }

    @UserLoginToken
    @PostMapping("/updateJobSkills")
    public Job updateJobSkills(@RequestBody List<String> skills, @RequestParam("jobId") String jobId){
        return jobService.updateJobSkills(skills,jobId);
    }

    @ManagerLoginToken
    @GetMapping("/getStatistics")
    public List<Integer> getStatistics(@RequestParam("year") int year,@RequestParam("lowMonth") int lowMonth,@RequestParam("highMonth") int highMonth)
    {
        return jobService.getStatistics(year,lowMonth,highMonth);
    }
}
