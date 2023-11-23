package socialnet.socialnetwork.service;

import org.springframework.security.core.userdetails.UserDetails;
import socialnet.socialnetwork.model.Friendship;
import socialnet.socialnetwork.model.User;
import socialnet.socialnetwork.web.model.CreateFriendshipRequest;

import java.util.List;

public interface FriendshipService {

    Friendship findById(Long id);

    Friendship findByFriendId(Long friendId, UserDetails userDetails);

    Friendship save(Friendship friendship);

    void deleteById(Long id);

    Boolean existsById(Long id);

    Boolean existsByFriendId(Long userId, UserDetails userDetails);

    List<User> findAllByLogin(String login);

    Friendship create(CreateFriendshipRequest request, UserDetails userDetails);

    void accept(Long id, UserDetails userDetails);

    Boolean getIsAcceptedById(Long id);

    void deleteByFriendId(Long userId, UserDetails userDetails);

}
