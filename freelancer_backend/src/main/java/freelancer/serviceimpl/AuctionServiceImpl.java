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

import java.util.ArrayList;
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

    @Override
    public List<List<String>> getEmployeeAuction(int userId){
        List<Auction> auctions = auctionRepository.findAll();
        List<Auction> filter = new ArrayList<>();
        int size = auctions.size();
        for(int i=0;i<size;i++)
        {
            if(auctions.get(i).getUserId() == userId)
                filter.add(auctions.get(i));
        }
        size = filter.size();
        List<List<String>> res = new ArrayList<>();
        List<String> tmp = new ArrayList<>();
        Auction auction;
        Job job;
        for(int i=0;i<size;i++)
        {
            auction = filter.get(i);
            job = jobRepository.findById(filter.get(i).getProjectId()).get();
            tmp.add(auction.getProjectId());
            tmp.add(auction.getProjectName());
            tmp.add(Integer.toString(job.getState()));
            if(job.getEmployeeId()==0)
                tmp.add("等待确认");
            else {
                if(job.getEmployeeId() == auction.getUserId())
                    tmp.add("竞标成功");
                else tmp.add("竞标失败");
            }
            tmp.add(Integer.toString(auction.getPrice()));
            tmp.add(Integer.toString(job.getAvgPrice()));
            tmp.add(job.getDeadline());
            res.add(tmp);
            tmp = new ArrayList<>();
        }
        return res;
    }
}
