package socialnet.socialnetwork.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import socialnet.socialnetwork.mapper.UserMapper;
import socialnet.socialnetwork.security.AppUserDetails;
import socialnet.socialnetwork.service.UserService;
import socialnet.socialnetwork.web.model.UserFilter;
import socialnet.socialnetwork.web.model.UserListResponse;
import socialnet.socialnetwork.web.model.UserResponse;

@RestController
@RequestMapping("/api/v1/app/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/search")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserListResponse> filterBy(@Valid UserFilter filter, @AuthenticationPrincipal AppUserDetails userDetails) {
        return ResponseEntity.ok(
                userMapper.userListToUserResponseList(
                        userService.filterBy(filter, userDetails.getUsername())
                )
        );
    }

    @GetMapping("/search/allOther")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserListResponse> findAllOther(@AuthenticationPrincipal AppUserDetails userDetails) {
        return ResponseEntity.ok(
                userMapper.userListToUserResponseList(
                        userService.findAllOther(userDetails.getUsername())
                )
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserResponse> findById(@PathVariable long id) {
        return ResponseEntity.ok(userMapper.userToResponse(userService.findById(id)));
    }

}
