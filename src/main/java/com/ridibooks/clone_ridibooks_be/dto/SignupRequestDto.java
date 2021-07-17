package com.ridibooks.clone_ridibooks_be.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignupRequestDto {

    @NotBlank(message = "ID는 필수 입력 값입니다.")
    private String username;

    @NotBlank(message = "PW는 필수 입력 값입니다.")
    private String password;
    private String passwordChecker;
    private boolean admin = false;
    private String adminToken = "";

    public SignupRequestDto(String username, String password, String passwordChecker) {
    }
}