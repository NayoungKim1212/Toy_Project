package com.example.demo.service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ErrorResponseDto;
import com.example.demo.dto.UserRequestDto;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserSerivce {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    // 회원 가입
    public ResponseEntity<ErrorResponseDto> join(UserRequestDto requestDto) {
    	
        String loginId = requestDto.getLoginId();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String nickname = requestDto.getNickname();

        // 회원 ID 중복 확인
        Optional<User> checkLoginId = userRepository.findByLoginId(loginId);
        if (checkLoginId.isPresent()) {
            throw new IllegalArgumentException("중복된 아이디가 존재합니다.");
        }

        // 닉네임 중복 확인
        Optional<User> checkNickname = userRepository.findByNickname(nickname);
        if (checkNickname.isPresent()) {
            throw new IllegalArgumentException("중복된 닉네임이 존재합니다.");
        }

        // 사용자 등록
        User newUser = new User(
                requestDto.getLoginId(),
                passwordEncoder.encode(requestDto.getPassword()),
                requestDto.getNickname(),
                requestDto.getName(),
                requestDto.getPhone(),
                requestDto.getEmail()
            );
            userRepository.save(newUser);

            ErrorResponseDto responseDto = ErrorResponseDto.builder()
                .status(201L)
                .error("Success")
                .build();
            
            return ResponseEntity.status(201).body(responseDto);
    }
        // 회원 목록 조회
	public Map<String, Object> readUser(int page, int size) {

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "name"));
        Page<User> userList = userRepository.findAll(pageable);
        
        List<UserResponseDto> result = userList.getContent().stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());

        int totalPages = userList.getTotalPages();

        Map<String, Object> response = new HashMap<>();
        response.put("data", result);
        response.put("totalPages", totalPages);

        return response;
	}

}
