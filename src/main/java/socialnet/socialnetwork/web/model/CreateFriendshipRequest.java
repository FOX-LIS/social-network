package socialnet.socialnetwork.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import socialnet.socialnetwork.model.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateFriendshipRequest {

    private User userAcceptor;

}
