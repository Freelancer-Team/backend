package freelancer.repository;

import freelancer.entity.Job;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;


public interface JobRepository extends MongoRepository<Job, String> {
    @Query(value = "{'$and': [{ 'employeeId':?0}]}")
    List<Job> findAsEmployee(int id);

    @Query(value = "{'$and': [{ 'employerId':?0}]}")
    List<Job> findAsEmployer(int id);
}
