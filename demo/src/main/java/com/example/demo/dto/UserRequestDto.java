package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserRequestDto {

	@Pattern(regexp = "^[a-z0-9]{4,10}$", message = "소문자와 숫자를 포함한 4~10자 이내로 작성해주세요")
	@NotBlank
	private String loginId; // 아이디
	
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[`~!@#$%^&*()])[a-zA-Z0-9`~!@#$%^&*()]{8,15}$", message = "대소문자와 숫자, 특수문자를 포함한 8~15자 이내로 작성해주세요")
	@NotBlank
	private String password; // 비밀번호
	
	@NotBlank
	private String nickname; // 닉네임
	
	@NotBlank
	private String name; // 이름
	
	@NotBlank
	private String phone; // 전화번호
	
	@NotBlank
	private String email; // 이메일주소
}
