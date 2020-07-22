package freelancer.repository;

import freelancer.entity.Job;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface JobRepository extends MongoRepository<Job, String> {
//    @Query("{ skills: ?0}")
//    public List<Job> findJobs(String skills);
}
