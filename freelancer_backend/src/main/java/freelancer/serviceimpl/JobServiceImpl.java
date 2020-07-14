package freelancer.serviceimpl;

import freelancer.entity.Job;
import freelancer.repository.JobRepository;
import freelancer.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
