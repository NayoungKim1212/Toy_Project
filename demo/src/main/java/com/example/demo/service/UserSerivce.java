package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ErrorResponseDto;
import com.example.demo.dto.UserRequestDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.JwtUtil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserSerivce {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

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

    private User findUser(String loginId) {   	
        return userRepository.findByLoginId(loginId).orElseThrow(() ->
        new IllegalArgumentException("헤당 유저는 존재하지 않습니다."));
	}

    // 로그인
	public ResponseEntity<String> login(UserRequestDto requestDto, HttpServletResponse res) {
        String loginId = requestDto.getLoginId();
        String password = requestDto.getPassword();

        Optional<User> checkLoginId = userRepository.findByLoginId(loginId);
        if (!checkLoginId.isPresent()) {
            return new ResponseEntity<>("등록된 사용자가 아닙니다.", HttpStatus.NOT_FOUND);
        }

        User user = checkLoginId.get();

        if(!passwordEncoder.matches(password, user.getPassword())){
            return new ResponseEntity<>("비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED);
        }

        String token = jwtUtil.createToken(user.getLoginId());
        jwtUtil.addJwtToHeader(token, res);
        return new ResponseEntity<>("로그인 성공", HttpStatus.OK);
    }

	// 회원 정보 수정
	@Transactional
	public ResponseEntity<?> update(String loginId, String tokenValue, UserRequestDto requestDto) {
		
	//	User user = findUser(loginId);
        String token = authentication(tokenValue);
        String loginIdFromToken = getUsernameFromJwt(token);
		
        // 요청받은 loginId와 토큰에서 추출한 loginId가 일치하는지 검증합니다.
        if (!loginId.equals(loginIdFromToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access: Login ID does not match token.");
        }

        // 데이터베이스에서 사용자 정보를 조회합니다.
        User user = findUser(loginId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        // 사용자 정보를 업데이트합니다.
        user.update(requestDto);

        // 업데이트된 사용자 정보로 응답을 생성합니다.
        return new ResponseEntity<>(new UserResponseDto(user), HttpStatus.OK);
    }

	private String authentication(String tokenValue) { // 유효한 토큰인지 확인하고 토큰 반환
        String decodedToken = jwtUtil.decodingToken(tokenValue);
        String token = jwtUtil.substringToken(decodedToken);
        if (!jwtUtil.validateToken(token)) {
            throw new IllegalArgumentException("Token Error");
        }
        return token;
    }
    private String getUsernameFromJwt(String token) { // 토큰에서 사용자 정보 가져오기
        Claims info = jwtUtil.getUserInfoFromToken(token);
        String loginId = info.getSubject();
        return loginId;
    }

}
