package freelancer.service;

import freelancer.entity.Auction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AuctionServiceTest {

    @Autowired
    AuctionService auctionService;

    @Test
    public void applyJob() {
        int price = 888;
        assertEquals(price,auctionService.applyJob(5,"5f0c638cacd8b0e93f8facb2","I can",888).getPrice());
    }

    @Test
    public void getAuction(){
        Auction auction = auctionService.getAuction("5f0c638cacd8b0e93f8facae").get(0);
        assertEquals("Need content writer for educational website pages.",auction.getProjectName());
    }
}
