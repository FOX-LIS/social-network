package socialnet.socialnetwork.service;

import socialnet.socialnetwork.model.User;
import socialnet.socialnetwork.web.model.UserFilter;

import java.util.List;

public interface UserService {


    List<User> findAllOther(String login);
    User findById(Long Id);

    Long findIdByLogin(String login);
    User save(User user);
    void deleteById(Long id);
    List<User> filterBy(UserFilter filter, String login);

    List<User> findAllByIdIn(List<Long>ids);

}
