package freelancer.serviceimpl;

import freelancer.dao.JobDao;
import freelancer.entity.Job;
import freelancer.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class JobServiceImpl implements JobService {
    @Autowired
    private JobDao jobDao;

    @Override
    public List<Job> getJobs() {
        return jobDao.getJobs();
    }
}
