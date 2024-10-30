package mt.movie_theater.api.service.user;

import lombok.RequiredArgsConstructor;
import mt.movie_theater.api.controller.user.request.UserCreateRequest;
import mt.movie_theater.api.controller.user.response.UserResponse;
import mt.movie_theater.domain.user.User;
import mt.movie_theater.domain.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponse createUser(UserCreateRequest request) {
        User savedUser = userRepository.save(request.toEntity());
        return UserResponse.create(savedUser);
    }
}
