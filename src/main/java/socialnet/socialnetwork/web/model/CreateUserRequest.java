package socialnet.socialnetwork.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import socialnet.socialnetwork.model.RoleType;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserRequest {

    private String firstname;
    private String lastName;
    private String login;
    private String password;
    private String email;
    private LocalDate birthDate;
    private String city;
    private String country;
    private String photo;
    private Set<RoleType> roles;

}
