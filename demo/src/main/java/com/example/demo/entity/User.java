package com.example.demo.entity;

import com.example.demo.dto.UserRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User{
	
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String loginId;
    
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column
    private String name;

    @Column
    private String phone; 
    
    @Column
	private String email;
    
    public User(String loginId, String password, String nickname, String name, String phone, String email) {
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
    
    public User(UserRequestDto requestDto, String loginId, String password, String nickname) {
        this.loginId = requestDto.getLoginId();
        this.password = requestDto.getPassword();
        this.nickname = requestDto.getNickname();
    }

    public User(String nickname, String password, String email, String phone) {
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

	public void update(UserRequestDto requestDto) {
        this.nickname = requestDto.getNickname();
        this.password = requestDto.getPassword();
        this.email = requestDto.getEmail();
        this.phone = requestDto.getPhone();
	}
}
