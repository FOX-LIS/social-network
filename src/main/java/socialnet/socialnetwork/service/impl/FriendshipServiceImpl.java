package socialnet.socialnetwork.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import socialnet.socialnetwork.model.Friendship;
import socialnet.socialnetwork.model.User;
import socialnet.socialnetwork.repository.FriendshipRepository;
import socialnet.socialnetwork.repository.UserRepository;
import socialnet.socialnetwork.service.FriendshipService;
import socialnet.socialnetwork.web.model.CreateFriendshipRequest;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {

    private final FriendshipRepository friendshipRepository;

    private final UserRepository userRepository;


    @Override
    public Friendship findById(Long id) {
        return friendshipRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                        "Пользователь с ID {0} не найден!", id)));
    }

    @Override
    public Friendship findByFriendId(Long friendId, UserDetails userDetails) {
        Long userId = userRepository.findIdByLogin(userDetails.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("ID пользователя с таким логином не найдено!"));
        return findByFriendId(friendId, userId);
    }

    public Friendship findByFriendId(Long friendId, Long userId) {
        Friendship friendship = friendshipRepository.findByUserInitiatorIdAndUserAcceptorId(userId, friendId);
        if (friendship != null) {
            return friendship;
        }
        friendship = friendshipRepository.findByUserInitiatorIdAndUserAcceptorId(friendId, userId);
        if (friendship != null) {
            return friendship;
        }
        throw new EntityNotFoundException("Дружбы с пользователем не существует!");
    }

    @Override
    public Friendship save(Friendship friendship) {
        return friendshipRepository.save(friendship);
    }

    @Override
    public void deleteById(Long id) {
        friendshipRepository.deleteById(id);
    }

    @Override
    public Boolean existsById(Long id) {
        return friendshipRepository.existsById(id);
    }

    @Override
    public Boolean existsByFriendId(Long friendId, UserDetails userDetails) {
        Long userId = userRepository.findIdByLogin(userDetails.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("ID пользователя с таким логином не найдено!"));
        return friendshipRepository.existsByUserInitiatorIdAndUserAcceptorId(userId, friendId)
                || friendshipRepository.existsByUserInitiatorIdAndUserAcceptorId(friendId, userId);
    }


    @Override
    public List<User> findAllByLogin(String login) {
        Long userId = userRepository.findIdByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException("ID пользователя с таким логином не найдено!"));
        HashSet<Long> userIds =
                friendshipRepository.findAllUserAcceptorIdsByUserInitiatorIdAndIsAccepted(userId, true);
        userIds.addAll(
                friendshipRepository.findAllUserInitiatorIdsByUserAcceptorIdAndIsAccepted(userId, true));
        return userRepository.findAllByIdIn(userIds.stream().toList());
    }

    @Override
    public Friendship create(CreateFriendshipRequest request, UserDetails userDetails) {
        Long userInitiatorId = userRepository.findIdByLogin(userDetails.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("ID пользователя с таким логином не найдено!"));
        var friendship = Friendship.builder()
                .userInitiatorId(userInitiatorId)
                .userAcceptorId(request.getUserAcceptor().getId())
                .isAccepted(false)
                .build();
        return save(friendship);
    }

    @Override
    public void accept(Long id, UserDetails userDetails) {
        Friendship friendship = findById(id);
        friendship.setIsAccepted(true);
    }

    @Override
    public Boolean getIsAcceptedById(Long id) {
        return friendshipRepository.getIsAcceptedById(id);
    }

    @Override
    public void deleteByFriendId(Long friendId, UserDetails userDetails) {
        Long userId = userRepository.findIdByLogin(userDetails.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("ID пользователя с таким логином не найдено!"));
        Friendship friendship = findByFriendId(friendId, userId);
        deleteFriendship(friendship);
        friendship = findByFriendId(userId, friendId);
        deleteFriendship(friendship);
    }

    private void deleteFriendship(Friendship friendship){
        if (friendship != null) {
            friendshipRepository.delete(friendship);
        }
    }

}
