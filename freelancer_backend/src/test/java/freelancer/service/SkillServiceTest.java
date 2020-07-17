package freelancer.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SkillServiceTest {

    @Autowired
    SkillService skillService;

    @Test
    public void getSkills() {
        int num = 1814;
        assertEquals(num,skillService.getSkills().size());
    }
}
