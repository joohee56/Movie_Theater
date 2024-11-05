package mt.movie_theater.api.user.service;

import static org.assertj.core.api.Assertions.assertThat;

import mt.movie_theater.IntegrationTestSupport;
import mt.movie_theater.api.user.request.UserCreateRequest;
import mt.movie_theater.api.user.response.UserResponse;
import mt.movie_theater.domain.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class UserServiceTest extends IntegrationTestSupport {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @DisplayName("새 회원을 등록한다.")
    @Test
    void createUser() {
        //given
        UserCreateRequest request = UserCreateRequest.builder()
                .loginId("joohee123")
                .name("이주희")
                .email("test@test.com")
                .build();
        //when
        UserResponse response = userService.createUser(request);

        //then
        assertThat(response.getId()).isNotNull();
        assertThat(response)
                .extracting("loginId", "name", "email")
                .contains("joohee123", "이주희", "test@test.com");
    }
}