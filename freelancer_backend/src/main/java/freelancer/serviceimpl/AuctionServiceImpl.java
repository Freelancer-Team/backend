package freelancer.serviceimpl;

import freelancer.entity.Auction;
import freelancer.entity.Job;
import freelancer.entity.User;
import freelancer.repository.AuctionRepository;
import freelancer.repository.JobRepository;
import freelancer.repository.UserRepository;
import freelancer.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AuctionServiceImpl implements AuctionService {

    @Autowired
    AuctionRepository auctionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JobRepository jobRepository;

    @Override
    public Auction applyJob(int userId, String jobId, String description, int price){
        User user = userRepository.findById(userId).get();
        Job job = jobRepository.findById(jobId).get();
        Auction auction = new Auction();
        auction.setProjectId(jobId);
        auction.setProjectName(job.getTitle());
        auction.setUserId(userId);
        auction.setUserName(user.getName());
        auction.setDescription(description);
        auction.setPrice(price);
        auction.setSkills(user.getSkills());
        auction.setType(job.getType());
        return auctionRepository.save(auction);
    }

    @Override
    public List<Auction> getAuction(String jobId){
        return auctionRepository.getAuction(jobId);
    }
}
