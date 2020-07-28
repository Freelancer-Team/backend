package freelancer.service;


import freelancer.entity.Auction;

import java.util.List;

public interface AuctionService {
    Auction applyJob(int userId, String jobId, String description, int price);
    List<Auction> getAuction(String jobId);
}
