package freelancer.service;

import freelancer.FreelancerApplicationTest;
import freelancer.entity.Job;
import freelancer.repository.JobRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JobServiceTest extends FreelancerApplicationTest {

    @Autowired
    JobService jobService;

    @Test
    public void getJobs() {
        int num = 12791;
        assertEquals(num,jobService.getJobs().size());
    }

    @Test
    void saveJob() {
        Job job = jobService.getJob("5f0c638cacd8b0e93f8facb6");
        job.setClick(9002);
        assertEquals(job,jobService.saveJob(job));
    }

    @Test
    void getJob() {
        String title = "wordpress multi-network";
        assertEquals(title,jobService.getJob("5f0c638cacd8b0e93f8facb6").getTitle());
    }

    @Test
    void assignJob() {
        int id = 5;
        assertEquals(id,jobService.assignJob(5,"Cjdrnx","5f0c638cacd8b0e93f8facae").getEmployeeId());
    }

    @Test
    void updateJobSkills(){
        List<String> skills = new ArrayList<String>();
        skills.add("Article Writing");
        skills.add("Content Writing");
        assertEquals(skills,jobService.updateJobSkills(skills,"5f0c638cacd8b0e93f8facae").getSkills());
    }


    @Test
    void getSuggestJobs(){
        assertEquals(8,jobService.getSuggestJobs().size());
    }

    @Test
    void getCurrentJobs(){
        assertEquals(891,jobService.getCurrentJobs().size());
    }

    @Test
    void setJobState(){
        jobService.setJobState("5f0c638cacd8b0e93f8facae",0);
        assertEquals(0,jobService.getJob("5f0c638cacd8b0e93f8facae").getState());
    }

    @Test
    void getEmployeeJob(){
        assertEquals(9,jobService.getEmployeeJob(2).size());
    }

    @Test
    void getEmployerJob(){
        assertEquals(17,jobService.getEmployerJob(2).size());
    }

}
