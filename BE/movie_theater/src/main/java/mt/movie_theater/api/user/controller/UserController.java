package mt.movie_theater.api.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mt.movie_theater.api.apiResponse.ApiResponse;
import mt.movie_theater.api.user.request.UserCreateRequest;
import mt.movie_theater.api.user.request.UserLoginRequest;
import mt.movie_theater.api.user.response.UserResponse;
import mt.movie_theater.api.user.service.SessionService;
import mt.movie_theater.api.user.service.UserService;
import mt.movie_theater.domain.user.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final SessionService sessionService;

    @PostMapping("/new")
    public ApiResponse<UserResponse> createUser(@Valid @RequestBody UserCreateRequest request) {
        UserResponse response = userService.createUser(request);
        return ApiResponse.ok(response);
    }

    @PostMapping("/user/login")
    public ApiResponse<String> login(@Valid @RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        User user = userService.authenticate(userLoginRequest.getLoginId(), userLoginRequest.getPassword());
        sessionService.createLoginSession(request, user.getId());
        return ApiResponse.ok("로그인 성공");
    }
}
