package freelancer.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "auction")
public class Auction {

    @Id
    private String id;


}
