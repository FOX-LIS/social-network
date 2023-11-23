package socialnet.socialnetwork.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import socialnet.socialnetwork.model.User;
import socialnet.socialnetwork.repository.UserRepository;
import socialnet.socialnetwork.repository.UserSpecification;
import socialnet.socialnetwork.service.UserService;
import socialnet.socialnetwork.web.model.UserFilter;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final HttpServletRequest request;

    @Override
    public List<User> findAllOther(String login) {
        return userRepository.findAll(UserSpecification.withExcludeYourselfFilter(login));
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                        "Пользователь с ID {0} не найден!", id)));
    }

    @Override
    public Long findIdByLogin(String login) {
        return userRepository.findIdByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                        "ID пользователя с логином {0} не найден!", login)));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> filterBy(UserFilter filter, String login) {
        return userRepository.findAll(UserSpecification.withFilter(filter, login),
                PageRequest.of(filter.getPageNumber(), filter.getPageSize())).getContent();
    }

    @Override
    public List<User> findAllByIdIn(List<Long> ids) {
        return userRepository.findAllByIdIn(ids);
    }

}
