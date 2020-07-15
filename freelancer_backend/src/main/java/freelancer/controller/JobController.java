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

    @RequestMapping("/getJobs")
    public List<Job> getJobs() {
        return jobService.getJobs();
    }

    @RequestMapping("/saveJob")
    public Job saveJob(@RequestBody Job job) {
        return jobService.saveJob(job);
    }

    @RequestMapping("/getJob")
    public Job getJob(@RequestParam("id") String id) {
        return jobService.getJob(id);
    }

    @RequestMapping("/createRelation")
    public void createRelation(){jobService.createRelation();}
  }
