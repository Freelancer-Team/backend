package freelancer.controller;

import freelancer.entity.Skill;
import freelancer.security.PassToken;
import freelancer.security.UserLoginToken;
import freelancer.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SkillController {

    @Autowired
    private SkillService skillService;


    //获得所有技能
    @UserLoginToken
    @RequestMapping("/getSkills")
    public List<Skill> getSkills() {
        return skillService.getSkills();
    }
  }
