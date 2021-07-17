package com.ridibooks.clone_ridibooks_be.controller;

import com.ridibooks.clone_ridibooks_be.dto.SignupRequestDto;
import com.ridibooks.clone_ridibooks_be.model.User;
import com.ridibooks.clone_ridibooks_be.repository.UserRepository;
import com.ridibooks.clone_ridibooks_be.security.JwtTokenProvider;
import com.ridibooks.clone_ridibooks_be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final UserRepository userRepository;

    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    public void registerUser(@Valid @RequestBody SignupRequestDto requestDto) {
        userService.registerUser(requestDto);
    }

    @GetMapping("/user/kakao/callback")
    public void kakaoLogin(String code) {
        // authorizedCode: 카카오 서버로부터 받은 인가 코드
        userService.kakaoLogin(code);
    }

    @PostMapping("/user/login")
    public String login(@RequestBody SignupRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 유저입니다."));
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return jwtTokenProvider.createToken(user.getUsername(), user.getRole());
    }
}