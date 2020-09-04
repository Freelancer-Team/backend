package freelancer.service;

import freelancer.FreelancerApplicationTest;
import freelancer.entity.Job;
import freelancer.repository.JobRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JobServiceTest extends FreelancerApplicationTest {

    @Autowired
    JobService jobService;

    @Test
    public void getJobs() {
        int num = 12798;
        assertEquals(num,jobService.getJobs().size());
    }

//    @Test
//    void saveJob() {
//        Job job = jobService.getJob("5f0c638cacd8b0e93f8facb6");
//        job.setPrice("$100");
//        assertEquals(job,jobService.saveJob(job));
//    }

    @Test
    void getJob() {
        String title = "wordpress multi-network";
        assertEquals(title,jobService.getJob("5f0c638cacd8b0e93f8facb6").getTitle());
    }

//    @Test
//    void assignJob() {
//        int id = 5;
//        assertEquals(id,jobService.assignJob(5,"Cjdrnx","5f0c638cacd8b0e93f8facae").getEmployeeId());
//    }
}
