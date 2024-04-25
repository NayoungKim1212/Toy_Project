package com.example.demo.dto;

import com.example.demo.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {

	private Long id;
    private String loginId;
    private String name;
    private String phone;
    private String email;
    private String nickname;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.loginId = user.getLoginId();
        this.name = user.getName();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
    }
}