package freelancer.serviceimpl;

import freelancer.entity.Job;
import freelancer.entity.User;
import freelancer.repository.JobRepository;
import freelancer.repository.UserRepository;
import freelancer.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;


@Service
public class JobServiceImpl implements JobService {
    @Autowired
    private JobRepository jobRepository;

    @Override
    public List<Job> getJobs() {
        List<Job> list;
        list = jobRepository.findAll();
        return list;
    }

    @Override
    public Job getJob(String id){
        if(jobRepository.findById(id).isPresent())
        return jobRepository.findById(id).get();
        else return null;
    }

    @Override
    public Job saveJob(Job job){
        return jobRepository.save(job);
    }

    @Autowired
    UserRepository userRepository;

    @Override
    public void createRelation(){
        List<Job> jobs = jobRepository.findAll();
        Job job;
        int k;
        for(int i=0;i<2000;i++)
        {
            job = jobs.get(i);
            job.setEmployeeId(0);
            job.setEmployeeName("");
            k = 1+(int)(Math.random()*1217);
            job.setEmployerId(k);
            job.setEmployerName(userRepository.findById(k).get().getName());
            job.setEmployeeRate(0);
            job.setEmployerRate(0);
            jobRepository.save(job);
        }
        for (int i=2000;i<jobs.size();i++)
        {
            job = jobs.get(i);
            k = 1+(int)(Math.random()*1217);
            job.setEmployeeId(k);
            job.setEmployeeName(userRepository.findById(k).get().getName());
            int tmp;
            tmp = k;
            while (k==tmp)
               k = 1+(int)(Math.random()*1217);
            job.setEmployerId(k);
            job.setEmployerName(userRepository.findById(k).get().getName());
            double employeeRate,employerRate;
            employeeRate = 3.0+Math.random()*2;
            employerRate = 3.0+Math.random()*2;
            DecimalFormat df = new DecimalFormat("#.#");
            String obj1= df.format(employeeRate);
            String obj2= df.format(employerRate);
            employeeRate= Double.parseDouble(obj1);
            employerRate= Double.parseDouble(obj2);
            job.setEmployeeRate(employeeRate);
            job.setEmployerRate(employerRate);
            jobRepository.save(job);
        }
    }
}
