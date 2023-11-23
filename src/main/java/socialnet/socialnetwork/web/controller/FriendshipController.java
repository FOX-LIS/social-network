package socialnet.socialnetwork.web.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import socialnet.socialnetwork.event.exception.AlreadyExistsException;
import socialnet.socialnetwork.mapper.UserMapper;
import socialnet.socialnetwork.web.model.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import socialnet.socialnetwork.mapper.FriendshipMapper;
import socialnet.socialnetwork.model.Friendship;
import socialnet.socialnetwork.service.FriendshipService;

@RestController
@RequestMapping("/api/v1/app/friendship")
@RequiredArgsConstructor
public class FriendshipController {
    private final FriendshipService friendshipService;
    private final FriendshipMapper friendshipMapper;
    private final UserMapper userMapper;

    @GetMapping("/friends")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserListResponse> findAllFriendsOfUser(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(
                userMapper.userListToUserResponseList(
                        friendshipService.findAllByLogin(userDetails.getUsername())
                )
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<FriendshipResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(friendshipMapper.friendshipToResponse(friendshipService.findById(id)));
    }

    @GetMapping("/searchBy/{friendId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<FriendshipResponse> findByFriendId(@PathVariable Long friendId,
                                                             @AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(friendshipMapper.friendshipToResponse(
                friendshipService.findByFriendId(friendId, userDetails)));
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<FriendshipResponse> createFriendship(@RequestBody @Valid CreateFriendshipRequest request,
                                                               @AuthenticationPrincipal UserDetails userDetails){
        Friendship newFriendship = friendshipService.create(request, userDetails);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(friendshipMapper.friendshipToResponse(newFriendship));
    }

    @PostMapping("/accept/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<SimpleResponse> acceptFriendship(@PathVariable Long id,
                                                           @AuthenticationPrincipal UserDetails userDetails){
        if(friendshipService.getIsAcceptedById(id)){
            throw new AlreadyExistsException("Дружба уже принята!");
        }
        friendshipService.accept(id, userDetails);
        return ResponseEntity.ok(new SimpleResponse("Дружба принята!"));
    }

    @PostMapping("/end/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<SimpleResponse> endFriendship(@PathVariable Long id){
        if(!friendshipService.existsById(id)){
            throw new EntityNotFoundException("Дружбы на данный момент нет!");
        }
        friendshipService.deleteById(id);
        return ResponseEntity.ok(new SimpleResponse("Дружба завершена!"));
    }

    @PostMapping("/end/user/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<SimpleResponse> endFriendship(@PathVariable Long userId,
                                                        @AuthenticationPrincipal UserDetails userDetails){
        if(!friendshipService.existsByFriendId(userId, userDetails)){
            throw new EntityNotFoundException("Дружбы на данный момент нет!");
        }
        friendshipService.deleteByFriendId(userId, userDetails);
        return ResponseEntity.ok(new SimpleResponse("Дружба завершена!"));
    }

}
