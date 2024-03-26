# 🥽어푸어푸 - 수영 커뮤니티 앱

## 💡 프로젝트 목표
`수영에 취미가 있는 사람들이 이용하는 서비스를 통합하여 이용하기 위해서 제작하게 되었습니다.`
- 수영장 검색(지도)
- 수영장 리뷰
- 수영 일지 작성
- 수영 커뮤니티

 <br>

## ⚙ 개발환경
- **java 17**
- **Build Tool** : Gradle 7.5.1
- **Web Server** : Nginx
- **Database** : MySQL 8.0, Redis
- **CI / CD** : GitHub Actions, Docker Hub, Docker Compose, Docker Container
- **IDE** : Intellij

<br>

## 📊 ERD

![어푸어푸 (1)](https://github.com/swimming-sis/swimming-community-app-backend/assets/114658792/561f76c5-586b-4834-9796-e68675dea2be)

<br>

## 🖱 UserFlow

  <p align = "center">
  <img src="https://github.com/swimming-sis/swimming-community-app-backend/assets/114658792/647d964a-9951-448d-bd7a-40271caa8e3c"  width="640" height="800" />
  </p>


<br>

## 

## 💻 화면구성 및 기능 구현


1. 로그인
   - [ ] 카카오 회원가입, 로그인
   - [ ] 관리자 계정(adimin)
   - [x] 로그인
    - JWT와 Spring Security의 필터를 이용한 로그인 방식
    - Access Token과 Refresh Token을 Redis에 일정 시간(30분) 간격으로 확인 후, 재발급
   - [x] 아이디 찾기
   - [x] 비밀번호 찾기
        - Naver Cloud 문자발송 서비스를 이용하여 가입하였던 핸드폰번호로 인증 문자 발송

  <p align="center"><img alt="로그인" src="https://github.com/swimming-sis/swimming-community-app-frontend/assets/116139215/84c1fc0c-01f4-40f5-943f-0a375296d2dc" height="600px" width="300px"></p>

<br />

---

<br />

2. 회원가입
   - [x] 일반 회원가입
   - [x] 핸드폰 번호 인증
   - 리뷰의 정확도를 위해서 Naver Cloud 문자발송 서비스를 이용하여 핸드폰번호로 인증 문자 발송
   - redis에 인증번호 3분이후 삭제 처리 (인증번호가 6자리이기 때문에 인증번호 중복문제 최소화) 

  <p align="center"><img  alt="회원가입"  src="https://github.com/swimming-sis/swimming-community-app-frontend/assets/116139215/4c920ee5-1fb3-4573-aa18-e603fa08aceb" height="600px" width="300px"></p>


<br />

---

<br />

3. 내 계정
   - [x] 회원 정보 조회
   - [x] 회원 수정
   - [x] 회원탈퇴

  <p align="center"><img alt="내계정 페이지" src="https://github.com/swimming-sis/swimming-community-app-frontend/assets/116139215/7bd10742-7009-4c38-b107-3d4c834fc2b1" height="600px" width="300px"></p>

<br />

---

<br />

4. 메인 페이지
   - [x] 커뮤니티 최신글 5개 

<p align="center"><img alt="메인페이지" src="https://github.com/swimming-sis/swimming-community-app-frontend/assets/116139215/3bce659c-ac3e-472a-912a-9055b0a7a78d" height="600px" width="300px"></p>

<br />

---

<br />

5. 수영장 검색페이지
   - [x] 카카오맵 API를 이용한 지도 구현
   - [x] 현재위치찾기
   - [x] 지도기준 검색리스트
   - [x] 키워드 검색리스트

  <table>
  <tr>
    <td><img alt="수영장 검색어 검색" src="https://github.com/swimming-sis/swimming-community-app-frontend/assets/116139215/9bc770f7-07d6-467b-8b7e-906ddb9ac0fb"  height="600px" width="300px" /></td><td><img alt="수영장 현재 위치 검색" src="https://github.com/swimming-sis/swimming-community-app-frontend/assets/116139215/cc1d9ec9-1259-45ae-8eaf-3fade8891208"  height="600px" width="300px" /></td>
  <tr>
</table>

<br />

---

<br />

6. 수영장 리뷰페이지
   - [x] 리뷰목록 및 상세 페이지
   - [x] 리뷰 목록 작성 / 수정 / 삭제


  <p align="center"><img alt="내계정 페이지" src="https://github.com/swimming-sis/swimming-community-app-frontend/assets/116139215/ceccce0e-1bb0-43f3-aa95-95b3e1dc9085" height="600px" width="300px"></p>

<br />

---

<br />

7. 커뮤니티
  - [x] 커뮤니티 게시글 작성 / 수정 / 삭제
  - [x] S3 파일 업로드, 삭제 (프론트 미완성)
  - [x] 댓글쓰기
  - [x] 좋아요 누르기
  - [x] 카테고리별 페이징 처리(카테고리 검색 및 키워드 검색)

  <p align="center"><img alt="커뮤니티" src="https://github.com/swimming-sis/swimming-community-app-frontend/assets/116139215/ceccce0e-1bb0-43f3-aa95-95b3e1dc9085" height="600px" width="300px"></p>

<br />

---

<br />

8. 수영 일지
   - [x] 수영 일지 날짜별 조회 캘린더
   - [x] 수영 일지 작성 및 평균 조회
   - [x] 일지 목록 작성 / 수정 / 삭제
   - [x] S3 파일 업로드, 삭제 (프론트 미작업)
   
  <table>
  <tr>
    <td>
     <p align="center"><img alt="일지 조회 수정" src="https://github.com/swimming-sis/swimming-community-app-frontend/assets/116139215/269848e5-70c1-45da-9a41-ab9e8ccd71b5"  height="600px" width="300px" /></td><td><img alt="일지 삭제" src="https://github.com/swimming-sis/swimming-community-app-frontend/assets/116139215/2631501f-6eed-4406-a549-3cde5e9b1ed6"  height="600px" width="300px"  /></p>
  </td>
  <tr>
</table>


