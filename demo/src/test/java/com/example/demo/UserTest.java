package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

class UserTest {

  @DataJpaTest
  @ActiveProfiles("test")
  public class UserRepositoryTest {

      @Autowired
      private UserRepository userRepository;

      @Test
      public void saveUserAndRetrieveByEmail() {
          // 새로운 User 엔티티 생성
          User user = User.builder()
                  .loginId("vkfksgksmf15")
                  .password("rnrnrn12@")
                  .nickname("testuser")
                  .name("이성계")
                  .phone("010-8888-5555")
                  .email("test@example.com")
                  .build();

          // UserRepository를 통해 User 엔티티 저장
          userRepository.save(user);

          // UserRepository를 통해 저장된 User 엔티티를 로그인아이디로 조회
          User savedUser = userRepository.findByLoginId("vkfksgksmf15").orElse(null);

          // 조회한 User 엔티티가 null이 아니어야 하고, 필드 값들이 일치하는지 검증
          assertNotNull(savedUser);
          assertEquals("vkfksgksmf15", savedUser.getLoginId());
          assertEquals("rnrnrn12", savedUser.getPassword());
          assertEquals("testuser", savedUser.getNickname());
          assertEquals("이성계", savedUser.getName());
          assertEquals("010-8888-5555", savedUser.getPhone());
          assertEquals("test@example.com", savedUser.getEmail());
      }
  }
}
