package freelancer.service;

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
        double price = 888.8;
        assertEquals(price,auctionService.applyJob(5,"5f0c638cacd8b0e93f8facb2","I can",888.8).getPrice());
    }
}
