package socialnet.socialnetwork.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import socialnet.socialnetwork.model.Friendship;
import socialnet.socialnetwork.web.model.FriendshipResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FriendshipMapper {
    FriendshipResponse friendshipToResponse(Friendship friendship);

}
