package freelancer.repository;

import freelancer.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;


public interface UserRepository extends MongoRepository<User, Integer> {
    @Query(value="{'$and': [{ 'email':?0},{'password':?1}]}")
    User login(String email, String password);
    @Query(value="{'$and': [{ 'name':?0}]}")
    User findname(String name);
    @Query(value="{'$and': [{ 'email':?0}]}")
    User findUserByemail(String email);
//    @Query(value="{'$and': [{ 'skills':?0}]}")
//    List<User> findUserBySkills(List<String> skills);
}
