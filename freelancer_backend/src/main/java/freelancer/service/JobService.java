package freelancer.service;

import freelancer.entity.Job;

import java.util.List;


public interface JobService {
    List<Job> getJobs();
    Job saveJob(Job job);
    Job getJob(String id);
    Job assignJob(int userId,String userName,String jobId);
    Job updateJobSkills(List<String> skills,String jobId);
    List<Job> getSuggestJobs();
    List<Job> getCurrentJobs();
    void setJobState(String jobId,int state);
    List<Job> getEmployeeJob(int userId);
    List<Job> getEmployerJob(int userId);

}
