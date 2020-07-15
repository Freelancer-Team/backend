package freelancer.service;

import freelancer.entity.Job;

import java.util.List;


public interface JobService {
    List<Job> getJobs();
    Job saveJob(Job job);
    Job getJob(String id);
    void createRelation();
}
