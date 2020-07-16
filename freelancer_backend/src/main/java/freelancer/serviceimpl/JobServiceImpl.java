package freelancer.serviceimpl;

import freelancer.entity.Job;
import freelancer.repository.JobRepository;
import freelancer.repository.UserRepository;
import freelancer.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Date;

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
    public Job assignJob(int userId,String userName,String jobId){
        Job job = jobRepository.findById(jobId).get();
        job.setEmployeeName(userName);
        job.setEmployeeId(userId);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        job.setStartTime(df.format(new Date()));
        jobRepository.save(job);
        return job;
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
        for(int i=0;i<jobs.size();i++) {
            if(i%2 == 0){
                job = jobs.get(i);
                job.setEmployeeId(0);
                job.setEmployeeName("");
                k = 1 + (int) (Math.random() * 1217);
                job.setEmployerId(k);
                job.setEmployerName(userRepository.findById(k).get().getName());
                job.setEmployeeRate(0);
                job.setEmployerRate(0);
                job.setStartTime("");
                job.setFinishTime("");
                jobRepository.save(job);
            }
            else  {
                job = jobs.get(i);
                k = 1 + (int) (Math.random() * 1217);
                job.setEmployeeId(k);
                job.setEmployeeName(userRepository.findById(k).get().getName());
                int tmp;
                tmp = k;
                while (k == tmp)
                    k = 1 + (int) (Math.random() * 1217);
                job.setEmployerId(k);
                job.setEmployerName(userRepository.findById(k).get().getName());
                double employeeRate, employerRate;
                employeeRate = 3.0 + Math.random() * 2;
                employerRate = 3.0 + Math.random() * 2;
                DecimalFormat df = new DecimalFormat("#.#");
                String obj1 = df.format(employeeRate);
                String obj2 = df.format(employerRate);
                employeeRate = Double.parseDouble(obj1);
                employerRate = Double.parseDouble(obj2);
                job.setEmployeeRate(employeeRate);
                job.setEmployerRate(employerRate);
                Date date1 = randomDate("2019-01-01", "2019-03-31");
                Date date2 = randomDate("2019-04-01", "2019-07-15");
                job.setStartTime(new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(date1));
                job.setFinishTime(new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(date2));
                jobRepository.save(job);
            }
        }
    }

    private static Date randomDate(String beginDate,String endDate){
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date start = format.parse(beginDate);
            Date end = format.parse(endDate);

            if(start.getTime() >= end.getTime()){
                return null;
            }
            long date = random(start.getTime(),end.getTime());
            return new Date(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static long random(long begin,long end){
        long rtn = begin + (long)(Math.random() * (end - begin));
        if(rtn == begin || rtn == end){
            return random(begin,end);
        }
        return rtn;
    }
}
