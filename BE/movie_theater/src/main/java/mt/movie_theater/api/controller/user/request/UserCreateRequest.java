package mt.movie_theater.api.controller.user.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mt.movie_theater.domain.user.User;

@Getter
@NoArgsConstructor
public class UserCreateRequest {
    private String loginId;
    private String name;
    private String email;

    @Builder
    public UserCreateRequest(String loginId, String name, String email) {
        this.loginId = loginId;
        this.name = name;
        this.email = email;
    }

    public User toEntity() {
        return User.builder()
                .loginId(this.loginId)
                .name(this.name)
                .email(this.email)
                .build();
    }
}
