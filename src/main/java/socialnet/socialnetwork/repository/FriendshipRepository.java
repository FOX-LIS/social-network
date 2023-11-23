package socialnet.socialnetwork.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import socialnet.socialnetwork.model.Friendship;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    @NonNull Optional<Friendship> findById(@NonNull Long id);

    Friendship save(Friendship friendship);

    void deleteByIdIn(List<Long>ids);

    HashSet<Long> findAllUserAcceptorIdsByUserInitiatorIdAndIsAccepted(Long userId, boolean isAccepted);
    HashSet<Long> findAllUserInitiatorIdsByUserAcceptorIdAndIsAccepted(Long userId, boolean isAccepted);
    Boolean getIsAcceptedById(Long id);
    Boolean existsByUserInitiatorIdAndUserAcceptorId(Long userInitiatorId, Long userAcceptorId);
    Friendship findByUserInitiatorIdAndUserAcceptorId(Long friendId, Long userId);
}

