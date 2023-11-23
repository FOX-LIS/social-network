package socialnet.socialnetwork.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import socialnet.socialnetwork.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    List<User> findAll();
    Optional<User> findById(Long id);

    Optional<Long> findIdByLogin(String login);

    @NonNull User save(@NonNull User user);

    Optional<User> findByLogin(String login);

    Boolean existsByLogin(String email);

    Boolean existsByEmail(String email);

    List<User> findAllByIdIn(List<Long> id);

}
