package freelancer.controller;

import freelancer.entity.Auction;
import freelancer.entity.Job;
import freelancer.service.AuctionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuctionController {

    //申请工作
//    @RequestMapping("/applyJob")
//    public Auction assignJob(@RequestParam("userId") int userId, @RequestParam("userName") String userName, @RequestParam("jobId") String jobId)
//    {
//        return AuctionService.applyJob(userId,userName,jobId);
//    }
}
