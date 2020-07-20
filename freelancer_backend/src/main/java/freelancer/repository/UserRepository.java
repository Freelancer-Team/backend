package freelancer.repository;

import freelancer.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface UserRepository extends MongoRepository<User, Integer> {
    @Query(value="{'$and': [{ 'email':?0},{'password':?1}]}")
    User login(String email, String password);
    @Query(value="{'$and': [{ 'email':?0}]}")
    User findByEmail(String name);
}
