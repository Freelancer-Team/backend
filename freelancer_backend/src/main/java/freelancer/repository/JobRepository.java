package freelancer.repository;

import freelancer.entity.Job;
import freelancer.entity.Skill;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;


public interface JobRepository extends MongoRepository<Job, String> {
//    @Query("{ skills: ?0}")
//    public List<Job> findJobs(String skills);
@Query(value="{'$and': [{ 'employeeId':?0}]}")
List<Job> findAsEmployee(int id);
}
