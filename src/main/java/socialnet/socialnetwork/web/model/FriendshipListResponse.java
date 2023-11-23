package socialnet.socialnetwork.web.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FriendshipListResponse {
    private List<FriendshipResponse> friendships = new ArrayList<>();
}
