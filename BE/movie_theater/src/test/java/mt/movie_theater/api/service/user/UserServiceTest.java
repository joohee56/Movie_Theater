package mt.movie_theater.api.service.user;

import static org.assertj.core.api.Assertions.assertThat;

import mt.movie_theater.IntegrationTestSupport;
import mt.movie_theater.api.controller.user.request.UserCreateRequest;
import mt.movie_theater.api.controller.user.response.UserResponse;
import mt.movie_theater.domain.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class UserServiceTest extends IntegrationTestSupport {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAllInBatch();
    }

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