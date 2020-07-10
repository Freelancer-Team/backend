package freelancer.daoimpl;
import freelancer.entity.Job;
import freelancer.repository.JobRepository;
import freelancer.dao.JobDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class JobDaoImpl implements JobDao {

    @Autowired
    private JobRepository jobRepository;

    @Override
    public List<Job> getJobs() {
        List<Job> list;
//        list=jobRepository.findJobs("Graphic Design");
        list = jobRepository.findAll();
        return list;
    }
}
