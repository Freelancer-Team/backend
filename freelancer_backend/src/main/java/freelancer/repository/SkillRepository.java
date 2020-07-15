package freelancer.repository;

import freelancer.entity.Skill;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface SkillRepository extends MongoRepository<Skill, Integer> {
    @Query(value="{'$and': [{ 'id':?0}]}")
    Skill findId(int id);
}
