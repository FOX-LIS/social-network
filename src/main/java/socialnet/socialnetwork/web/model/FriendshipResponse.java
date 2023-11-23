package socialnet.socialnetwork.web.model;

import lombok.Data;
import socialnet.socialnetwork.model.User;

@Data
public class FriendshipResponse {

    private Long id;

    private User userInitiator;

    private User userAcceptor;

    private Boolean isAccepted;

}
