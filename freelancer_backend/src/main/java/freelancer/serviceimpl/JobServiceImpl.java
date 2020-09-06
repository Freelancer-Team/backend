package freelancer.serviceimpl;

import freelancer.entity.Job;
import freelancer.repository.JobRepository;
import freelancer.service.JobService;
import freelancer.utilities.Suggest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;

@EnableCaching
@Service
public class JobServiceImpl implements JobService {
    @Autowired
    private JobRepository jobRepository;

    @Override
    public List<Integer> getStatistics(int year, int lowMonth, int highMonth) {
        int cnt;
        List<Integer> res = new ArrayList<>();
        List<Job> jobs = jobRepository.findAll();
        int size = jobs.size();
        for(int i=lowMonth;i<=highMonth;i++)
        {
            cnt=0;
            res.add(cnt);
        }
        String time;
        for(int i=0;i<size;i++)
        {
            time = jobs.get(i).getPublishTime();
            String[] str1 = time.split(" ");

            String s = str1[0].replace("."," ");
            String[] str2 = s.split(" ");
            int index;
            if(year == Integer.parseInt(str2[0]) && Integer.parseInt(str2[1]) >=lowMonth && Integer.parseInt(str2[1])<=highMonth)
                {
                    index = Integer.parseInt(str2[1])-lowMonth;
                    res.set(index,res.get(index)+1);
                }
        }
        return res;
    }

    @Override
    public List<Job> getJobs() {
        List<Job> list;
        list = jobRepository.findAll();
        return list;
    }

    @Override
    public List<Job> getEmployeeJob(int userId){
        List<Job> list = jobRepository.findAll();
        List<Job> tmp = new ArrayList<>();
        Job job;
        for(int i=0;i<list.size();i++)
        {
            job = list.get(i);
            if(job.getEmployeeId() == userId)
                tmp.add(job);
        }
        return tmp;
    }

    @Override
    public List<Job> getEmployerJob(int userId){
        List<Job> list = jobRepository.findAll();
        List<Job> tmp = new ArrayList<>();
        Job job;
        for(int i=0;i<list.size();i++)
        {
            job = list.get(i);
            if(job.getEmployerId() == userId)
                tmp.add(job);
        }
        return tmp;
    }

    @Override
    public List<Job> getSuggestJobs(int userId,int cnt){
        Suggest suggest=new Suggest();
        return suggest.getSuggest(userId,cnt);
    }

    @Override
    public void setJobState(String jobId,int state){
        Job job = jobRepository.findById(jobId).get();
        job.setState(state);
        jobRepository.save(job);
    }

    @Override
    public Job assignJob(int userId,String userName,String jobId){
        Job job = jobRepository.findById(jobId).get();
        job.setEmployeeName(userName);
        job.setEmployeeId(userId);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        job.setStartTime(df.format(new Date()));
        job.setState(1);
        jobRepository.save(job);
        return job;
    }


    @Override
    @Cacheable(value ="sampleCache")
    public List<Job> getCurrentJobs(){
        List<Job> jobs = jobRepository.findAll();
        Job job;
        List<Job> jobs1 = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        String dateString = formatter.format(date);
        Calendar c1=Calendar.getInstance();
        try{
        c1.setTime(formatter.parse(dateString));}
        catch (ParseException e){
            e.printStackTrace();
        }
        for(int i=0;i<jobs.size();i++)
        {
            Calendar c2=Calendar.getInstance();
            job = jobs.get(i);
            String tmp = job.getDeadline();
            try{
            c2.setTime(formatter.parse(tmp));}
            catch (ParseException e){
                e.printStackTrace();
            }
            if(job.getState() == 0 && c1.compareTo(c2)<0)
                jobs1.add(job);

        }
        return jobs1;
    }

    @Override
    public Job getJob(String id){
        if(jobRepository.findById(id).isPresent())
        return jobRepository.findById(id).get();
        else return null;
    }

    @Override
    public Job updateJobSkills(List<String> skills,String jobId)
    {
        Job job = jobRepository.findById(jobId).get();
        job.setSkills(skills);
        return jobRepository.save(job);
    }

    @Override
    public Job saveJob(Job job){
        job.setState(-3);
        return jobRepository.save(job);
    }

}
