package freelancer.repository;

import freelancer.entity.Skill;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface SkillRepository extends MongoRepository<Skill, Integer> {

}
