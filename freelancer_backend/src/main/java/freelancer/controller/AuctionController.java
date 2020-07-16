package freelancer.controller;

import freelancer.entity.Auction;
import freelancer.entity.Job;
import freelancer.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuctionController {

    @Autowired
    AuctionService auctionService;
    //申请工作
    @RequestMapping("/applyJob")
    public Auction assignJob(@RequestParam("userId") int userId,@RequestParam("jobId") String jobId,
                             @RequestParam("description") String description, @RequestParam("price") double price)
    {
        return auctionService.applyJob(userId,jobId,description,price);
    }
}
