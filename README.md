# Toy_Project

# 기능

## 1. 회원가입

   - url : /api/user/join

   - 접근 방식 : POST

   - 정상 동작일 경우 응답코드 201

## 2. 회원 목록 조회

   - url : /api/user/list

   - 접근 방식 : GET

   - @RequestParam으로 페이지 번호, 한 페이지에 보여주는 갯수

   - 이름 순으로 정렬

## 3. 회원 수정

   - url : /api/user/{loginId}

   - 접근 방식 : PATCH

   - loginId에 token을 부여해 해당하는 아이디를 가진 회원의 정보를 수정할 수 있도록 구현

## 4. 로그인

   - url : /api/user/login

   - 접근 방식 : POST

   - 로그인 시 loginId에 토큰을 부여
