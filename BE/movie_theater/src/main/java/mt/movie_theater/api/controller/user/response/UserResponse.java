package mt.movie_theater.api.controller.user.response;

import lombok.Builder;
import lombok.Getter;
import mt.movie_theater.domain.user.User;

@Getter
public class UserResponse {
    private Long id;
    private String loginId;
    private String name;
    private String email;

    @Builder
    public UserResponse(Long id, String loginId, String name, String email) {
        this.id = id;
        this.loginId = loginId;
        this.name = name;
        this.email = email;
    }

    public static UserResponse create(User savedUser) {
        return UserResponse.builder()
                .id(savedUser.getId())
                .loginId(savedUser.getLoginId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .build();
    }
}
