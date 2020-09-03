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
    @RequestMapping("/getSuggestJobs")
    public List<Job> getSuggestJobs(@RequestParam("userId") int userId) {return jobService.getSuggestJobs(userId);}

    //获得作为雇员的工作信息
    @UserLoginToken
    @RequestMapping("/getEmployeeJob")
    public List<Job> getEmployeeJob(@RequestParam("userId") int userId){
        return jobService.getEmployeeJob(userId);
    }

    //获得作为雇主的工作信息
    @UserLoginToken
    @RequestMapping("/getEmployerJob")
    public List<Job> getEmployerJob(@RequestParam("userId") int userId){
        return jobService.getEmployerJob(userId);
    }

    //获得所有job
    @ManagerLoginToken
    @RequestMapping("/getJobs")
    public List<Job> getJobs() {
        return jobService.getJobs();
    }

    //获得可用job
    @PassToken
    @GetMapping("/getCurrentJobs")
    public List<Job> getCurrentJobs(){return jobService.getCurrentJobs();}

    //保存job
    @UserLoginToken
    @RequestMapping("/saveJob")
    public Job saveJob(@RequestBody Job job) {
        return jobService.saveJob(job);
    }

    //使用id获得job
    @PassToken
    @RequestMapping("/getJob")
    public Job getJob(@RequestParam("id") String id) {
        return jobService.getJob(id);
    }

    //更新job状态
    @ManagerLoginToken
    @RequestMapping("/setJobState")
    public void setJobState(@RequestParam("jobId") String jobId,@RequestParam("state") int state){jobService.setJobState(jobId,state);}

    //生成数据使用
    @RequestMapping("/createRelation")
    public void createRelation(){jobService.createRelation();}

    //更新新增数据使用
    @RequestMapping("/updateRelation")
    public void updateRelation(){jobService.updateRelation();}

    @RequestMapping("createAuction")
    public void createAuction(){jobService.createAuction();}

    @RequestMapping("fixPrice")
    public void fixPrice(){jobService.fixPrice();}
    //雇主指定雇员接受任务
    @UserLoginToken
    @RequestMapping("/assignJob")
    public Job assignJob(@RequestParam("userId") int userId, @RequestParam("userName") String userName,@RequestParam("jobId") String jobId)
    {
        return jobService.assignJob(userId,userName,jobId);
    }

    @UserLoginToken
    @RequestMapping("/updateJobSkills")
    public Job updateJobSkills(@RequestBody List<String> skills, @RequestParam("jobId") String jobId){
        return jobService.updateJobSkills(skills,jobId);
    }
}
