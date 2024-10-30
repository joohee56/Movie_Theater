package mt.movie_theater.api.service.user;

import lombok.RequiredArgsConstructor;
import mt.movie_theater.api.controller.user.request.UserCreateRequest;
import mt.movie_theater.api.controller.user.response.UserResponse;
import mt.movie_theater.domain.user.User;
import mt.movie_theater.domain.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserResponse createUser(UserCreateRequest request) {
        User savedUser = userRepository.save(request.toEntity());
        return UserResponse.create(savedUser);
    }
}
