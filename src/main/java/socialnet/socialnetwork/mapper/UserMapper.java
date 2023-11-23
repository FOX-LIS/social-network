package socialnet.socialnetwork.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import socialnet.socialnetwork.model.User;
import socialnet.socialnetwork.web.model.UserListResponse;
import socialnet.socialnetwork.web.model.UserResponse;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserResponse userToResponse(User user);

    default UserListResponse userListToUserResponseList(List<User> users){
        UserListResponse response = new UserListResponse();
        response.setUsers(users.stream()
                .map(this::userToResponse).collect(Collectors.toList()));
        return response;
    }
}
