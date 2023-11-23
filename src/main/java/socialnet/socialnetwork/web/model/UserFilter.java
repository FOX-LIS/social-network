package socialnet.socialnetwork.web.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import socialnet.socialnetwork.validation.UserFilterValid;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@UserFilterValid
public class UserFilter {
    private Integer pageSize;
    private Integer pageNumber;
    private String firstName;
    private String lastName;
    private String login;
    private String city;
    private String country;
    private LocalDate birthDateFrom;
    private LocalDate birthDateTo;
    private Integer ageFrom;
    private Integer ageTo;
    private Boolean isOnline;
}
