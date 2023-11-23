package socialnet.socialnetwork.web.model;

import lombok.Data;
import socialnet.socialnetwork.model.Friendship;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserResponse {

    private Long id;

    private String firstname;

    private String lastName;

    private String login;

    private String password;

    private String email;

    private List<FriendshipResponse>friendships = new ArrayList<>();

}
