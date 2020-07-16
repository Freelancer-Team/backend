package freelancer.repository;

import freelancer.entity.Auction;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface AuctionRepository extends MongoRepository<Auction, String> {

}
