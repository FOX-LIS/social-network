package socialnet.socialnetwork.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "friendship")
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "user_initiator_id", nullable = false)
    private Long userInitiatorId;

    @NonNull
    @Column(name = "user_acceptor_id", nullable = false)
    private Long userAcceptorId;

    @Column(nullable = false)
    private Boolean isAccepted;
}
