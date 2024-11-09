package mt.movie_theater.api.user.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mt.movie_theater.domain.user.User;

@Getter
@NoArgsConstructor
public class UserCreateRequest {
    private String loginId;
    private String password;
    private String name;
    private String email;

    @Builder
    public UserCreateRequest(String loginId, String password, String name, String email) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public User toEntity() {
        return User.builder()
                .loginId(this.loginId)
                .password(this.password)
                .name(this.name)
                .email(this.email)
                .build();
    }
}
