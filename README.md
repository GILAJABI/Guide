# ⭐️ 프로젝트 소개 ⭐️
혼자 여행 가긴 싫은데... 😢😢
<br>
<br>
혼자 여행가기 싫은 사람들을 위한 여행 커뮤니티, 같이 여행 갈 여행메이트를 구할 수 있으며 실시간으로 다른 회원과의 채팅으로 여행 정보를 물어볼 수 있습니다.<br>
게시판으로 같이 여행 갈 여팽 메이트 구하는 모집 페이지, 여행지에서 사고 판 물건을 거래할 수 있는 거래 페이지, 다른 회원들을 위해 여행지에 대한 경험을 공유하는 리뷰 페이지 또한 글쓴이의 여행 타입을 분석하여 나와 맞는 여행 특성인지 알 수 있는 여행 MBTI들로 구성되어 있습니다.
<br>
<br>

## ✈️ 프로젝트 기간: 2024.4월 29일(월) ~ 2024.5월 15(수)
<br>
<br>

## 🤝 프로젝트 인원 & 역할 분배
| 이름 | 역할 | 기능 |
|---------|---------|---------|
| 강승규 | 팀장 / 채팅 | 프론트엔드 |
| 김시언 | 게시판 | 프론트엔드 |
| 김재호 | 총괄 | 풀스택 |
| 엄정식 | 채팅 | 백엔드 |
| 이채림 | 회원 | 풀스택 |
| 정범수 | 게시판 | 백엔드 |
<br>
<br>

## 📚 사용 스택
|  | 이름 |
|---------|---------|
| 프론트엔드| <img src="https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=HTML5&logoColor=white">  <img src="https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=CSS3&logoColor=white">  <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=CSS3&logoColor=white">  <img src="https://img.shields.io/badge/thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white">|
| 백엔드| <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">|
| DB| <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">|
<br>
<br>

## 🛠️ Tools
<img src="https://img.shields.io/badge/intellijidea-000000?style=for-the-badge&logo=intellijidea&logoColor=white"> <img src="https://img.shields.io/badge/Visual%20Studio%20Code-007ACC?style=for-the-badge&logo=VisualStudioCode&logoColor=white" /> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
<br>
<br> 

## 📖 사용 라이브러리
<img src="https://img.shields.io/badge/KakaoAPI-FFCD00?style=for-the-badge&logo=kakao&logoColor=black"> <img src="https://img.shields.io/badge/sockjs-010101?style=for-the-badge&logo=socketdotio&logoColor=white">
<img src="https://img.shields.io/badge/stomp-010101?style=for-the-badge&logo=socketdotio&logoColor=white">
<br>
<br>

## 🧾 요구 사항 명세서
![스크린샷 2024-05-22 172221](https://github.com/GILAJABI/guide/assets/93421611/b265e0d7-9190-4eb4-b478-b6127c32afd4)

<br>
<br>

## 🎨 피그마
https://www.figma.com/design/nc1D7av1s0GGj9xY48Q5DH/TRAVELMAKER?node-id=0%3A1&t=89nA830d1BsoRpDI-1
<br>
<br>

## 🗺️ 프로젝트 아키텍쳐
![ac](https://github.com/GILAJABI/guide/assets/93421611/aa514af2-01bd-43e2-bea1-23d7285b0f44)

<br>
<br>

## 🛢️ ERD 설계
![스크린샷 2024-05-23 120255](https://github.com/GILAJABI/guide/assets/93421611/06edd656-ff0a-4fa2-bc36-222417c9812c)


<br>
<br>

## 👀 형상 관리
<br>
<br>

## 📣 기능 소개
### 메인 페이지
<b>Header :</b> 사용자는 로그인 유무에 따라 보여지는 항목이 달라집니다. [로그인, 회원가입] -> [마이페이지, 로그아웃]<br>
<b>회원 정보 영역 :</b> 사용자의 프로필 등록 여부, 로그인 상태에 따라 보여지는 내용이 달라집니다.<br>
<b>회원 슬라이더 :</b> 프로필을 등록한 사용자들을 조회할 수 있습니다.<br>
<img width="1710" alt="main" src="https://github.com/GILAJABI/guide/assets/93421611/6a86f00a-0335-4ee2-8926-634dc2647988">

### 회원 기능

**회원가입**
  - 사용자로부터 정보를 입력받아 회원가입을 진행합니다.
  - JS를 활용하여 입력값을 모두 입력받을 수 있도록 하며, 중복확인 과정을 통해 중복된 ID가 없도록 합니다.
  - 비밀번호는 salt를 할당받아 SHA-256으로 해싱합니다.

**로그인**
  - ID와 PW를 입력받아 회원 정보가 일치하면 로그인에 성공합니다.
  - 로그인 성공 시 Session, Session Storage에 회원 정보를 할당합니다.

**프로필**
  - 회원 한줄 소개, 여행유형, 사진 업로드가 가능합니다.

**마이페이지**
  - 프로필이 등록되지 않은 회원은 프로필 등록 페이지로 이동됩니다.
  - 회원 정보, 프로필, 작성한 게시글 등을 확인 및 이동이 가능합니다.

**타회원 조회**
  - 다른 회원의 정보, 프로필, 작성한 게시글 등을 확인 및 이동이 가능합니다.
  - 1대1 채팅방으로 이동할 수 있습니다.


<img width="1710" alt="2024-05-16_2 26 37" src="https://github.com/GILAJABI/guide/assets/93421611/181d17e9-4efa-4f79-98a8-030073162138">
<img width="1710" alt="2024-05-16_2 26 33" src="https://github.com/GILAJABI/guide/assets/93421611/6a7fe496-3c63-4bcf-ab15-a6d340735d93">
<img width="1710" alt="2024-05-16_2 27 21" src="https://github.com/GILAJABI/guide/assets/93421611/15291830-afec-41ee-bba7-e894b21b250a">
<img width="1710" alt="2024-05-16_2 27 38" src="https://github.com/GILAJABI/guide/assets/93421611/39a59e09-9270-4a93-b692-1a14163dd4a2">
<img width="1710" alt="2024-05-16_2 27 50" src="https://github.com/GILAJABI/guide/assets/93421611/b1d43e56-6c1d-4e9a-ab76-c82f0edf0258">

<br>
<br>

## 🎥 시연 영상
<br>
<br>
