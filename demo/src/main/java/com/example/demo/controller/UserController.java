package com.example.demo.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserRequestDto;
import com.example.demo.service.UserSerivce;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

	private final UserSerivce userService;

	// 회원가입
	@PostMapping("/join")
	public ResponseEntity<?> join(@RequestBody @Valid UserRequestDto requestDto) {
		return userService.join(requestDto);
	}
	
	// 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequestDto requestDto, HttpServletResponse res) {
        return userService.login(requestDto, res);
    }

    // 회원 목록 조회
	@GetMapping("/list")
    public ResponseEntity<Map<String, Object>> readUser(@RequestParam("page") int page, @RequestParam("pageSize") int size) {
    	return ResponseEntity.ok(userService.readUser(page, size));
    }

	// 회원 정보 수정
	@PatchMapping("/{loginId}")
    public ResponseEntity<?> update(@PathVariable("loginId") String loginId,
    								String tokenValue,
    								@RequestBody UserRequestDto requestDto) {
    	return userService.update(loginId, tokenValue, requestDto);
    }

}
