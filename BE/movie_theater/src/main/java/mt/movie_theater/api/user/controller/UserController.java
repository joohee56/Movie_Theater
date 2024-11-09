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
import org.springframework.http.HttpStatus;
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
        if (userService.authenticate(userLoginRequest.getLoginId(), userLoginRequest.getPassword())) {
            sessionService.createLoginSession(request, userLoginRequest.getLoginId());
            return ApiResponse.ok("로그인 성공");
        }

        return ApiResponse.of(HttpStatus.UNAUTHORIZED, "로그인 실패");
    }
}
