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


## 💻 워크 플로우

  <p align = "center">
  <img src="https://raw.githubusercontent.com/buinq/imageServer/main/img/image-20230212171146274.png" alt="image-20230212171146274"  width="640" height="360" />
  </p>


<br>

## 🖱 UserFlow

  <p align = "center">
  <img src="https://github.com/swimming-sis/swimming-community-app-backend/assets/114658792/647d964a-9951-448d-bd7a-40271caa8e3c"  width="640" height="800" />
  </p>



<br>

## 📌 기능 구현  
<details>
 <summary><b>1. 회원 관리</b></summary>
 <div>
 
  - [x] 일반 회원가입
  - [ ] 카카오 회원가입, 로그인
  - [x] 로그인
  - [x] 아이디 찾기
  - [x] 비밀번호 찾기
  - [x] 핸드폰 번호 인증 (Redis)
  - [x] 계정수정, 삭제, 조회

</div>
</details>
  
<details>
  <summary><b>2. 게시판</b></summary>
  <div>

  - [x] 게시판 CRUD
  - [x] 댓글쓰기
  - [x] 좋아요 누르기

</div>
</details>

<details>
 <summary><b>3. 리뷰 </b></summary>
 <div>

  - [x] 수영장 데이터 저장, 조회 
  - [x] 리뷰 CRUD

</div>
</details>

<details>
 <summary><b>4. 일지 </b></summary>
 <div>   

   
   - [x] 일지 CRUD
   - [x] S3 파일 업로드, 삭제

</div>
</details>
