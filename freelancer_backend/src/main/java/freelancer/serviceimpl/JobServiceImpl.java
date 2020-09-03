package freelancer.serviceimpl;

import freelancer.entity.Auction;
import freelancer.entity.Job;
import freelancer.entity.User;
import freelancer.repository.JobRepository;
import freelancer.repository.UserRepository;
import freelancer.service.AuctionService;
import freelancer.service.JobService;
import freelancer.utilities.Suggest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;

@Service
public class JobServiceImpl implements JobService {
    @Autowired
    private JobRepository jobRepository;
    private Suggest suggest;

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
        jobRepository.save(job);
        return job;
    }

    @Override
    public List<Job> getSuggestJobs(int userId){
//        List<Job> jobs = jobRepository.findAll();
//        List<Job> jobs1 = new ArrayList<>();
//        for(int i=0;i<8;i++)
//          jobs1.add(jobs.get(i));
//        return jobs1;
        return  suggest.getSuggest(userId);
    }

    @Override
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
        return jobRepository.save(job);
    }

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuctionService auctionService;

    @Override
    public void updateRelation(){
        List<Job> jobs = jobRepository.findAll();
        Job job;
        int k;
        for(int i=0;i<jobs.size();i++)
        {
            if(i % 2==0){
                 k=(int)(Math.random()*4)-3;
                 job = jobs.get(i);
                 job.setState(k);
                Date date = randomDate("2020-01-01", "2020-06-01"); //发布日期
                Date date0 = randomDate("2020-06-01", "2020-12-31"); //接单截至日期
                job.setPublishTime(new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(date));
                job.setDeadline(new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(date0));
                k = (int) (Math.random()*10000);
                job.setClick(k);
            }
            else {
                job = jobs.get(i);
                job.setState(2);
                Date date = randomDate("2019-09-01", "2019-12-01"); //发布日期
                Date date0 = randomDate("2020-03-01", "2020-05-31"); //接单截至日期
                Date date1 = randomDate("2020-01-01", "2020-03-01"); //接单日期
                Date date2 = randomDate("2020-06-01", "2020-07-25"); //完成时间
                job.setPublishTime(new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(date));
                job.setDeadline(new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(date0));
                job.setStartTime(new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(date1));
                job.setFinishTime(new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(date2));
                k = (int) (Math.random()*10000);
                job.setClick(k);
            }
            jobRepository.save(job);
        }
    }

    @Override
    public void createAuction(){
        List<Job> jobs = jobRepository.findAll();
        Job job;
        List<User> users = userRepository.findAll();
        User user;
        int k = 0;
        for(int i=0;i<jobs.size();i++){
            job = jobs.get(i);
            if(job.getState()==-3)
                k = 0;
            else {
                if(i%2 == 1)
                    k = 0;
                else{
                    if(i%4 == 2)
                        k =(int)(Math.random()*4);
                    if(i%4 == 0)
                        k = (int)(Math.random()*8);
                    if(i%100 == 0)
                        k = (int)(Math.random()*30);
                }
            }
            job.setCandidateNum(k);
            jobRepository.save(job);
            for(int j=0;j<k;j++) {
                String jobId = job.getId();
                int userId = 1+(int)(Math.random()*1217);
                String description = "My ID is: "+userId+" ,trust I can do it!";
                int tmp;
                tmp = (int)((job.getHigh()-job.getLow())*Math.random()) + job.getLow();
                auctionService.applyJob(userId, jobId, description, tmp);
            }
        }
    }

    @Override
    public void fixPrice(){
        List<Job> jobs = jobRepository.findAll();
        Job job;
        int low = 0;
        int high = 0;
        for(int i=0;i<jobs.size();i++)
        {
            job = jobs.get(i);
            String price = job.getPrice();
            if(price.contains("min"))
            {
                jobRepository.delete(job);
                continue;
            }
            price =  price.replace("$","");
            if(price.contains("hr")){
                job.setType(1);
            }
            else{
                job.setType(0);
            }
            if(price.contains("-"))
            {
                String[] tmp = price.split(" ");
                String tmpLow = tmp[0];
                String tmpHigh = tmp[2];
                low = Integer.parseInt(tmpLow);
                high = Integer.parseInt(tmpHigh);
            }
            else {
                String[] tmp = price.split(" ");
                String tmpLow = tmp[0];
                low = Integer.parseInt(tmpLow);
                high = low;
            }
            job.setLow(low);
            job.setHigh(high);
            jobRepository.save(job);
        }
    }

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
