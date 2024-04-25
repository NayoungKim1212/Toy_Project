package com.example.demo.entity;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.dto.UserRequestDto;
import com.example.demo.util.Timestamped;

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
public class User extends Timestamped{
	
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

    public void update(UserRequestDto requestDto, PasswordEncoder passwordEncoder) {
        this.nickname = requestDto.getNickname();
        
        // 비밀번호 Encode
        if (requestDto.getPassword() != null && !requestDto.getPassword().trim().isEmpty()) {
            this.password = passwordEncoder.encode(requestDto.getPassword());
        }
        
        this.email = requestDto.getEmail();
        this.phone = requestDto.getPhone();
    }
}
