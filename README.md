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
<img src="https://img.shields.io/badge/KakaoAPI-FFCD00?style=for-the-badge&logo=kakao&logoColor=black"> <img src="https://img.shields.io/badge/socketjs-010101?style=for-the-badge&logo=socketdotio&logoColor=white">
<img src="https://img.shields.io/badge/stomp-010101?style=for-the-badge&logo=socketdotio&logoColor=white">
<br>
<br>

## 🧾 요구 사항 명세서
| No. | 요구사항 ID | 사용자 | 구분 | 기능 종류 | 요구사항 명칭 | 상세 설명 |
|-----|-------------|--------|------|-----------|----------------|-----------|
| 1   | FR-01       | 비회원 | 기능 | 회원 기능  | 회원가입       | 사용자가 정보를 입력받아 회원가입을 진행할 수 있습니다. |
| 2   | FR-02       | 비회원 | 기능 | 회원 기능  | 중복 확인      | 중복 확인을 과정을 통해 동일한 아이디가 발생하지 않도록 합니다. |
| 3   | FR-03       | 회원   | 기능 | 회원 기능  | 로그인         | 회원가입을 통해 생성된 계정으로 로그인을 진행합니다. |
| 4   | FR-04       | 회원   | 기능 | 회원 기능  | 세션/쿠키      | 로그인 성공시 세션 및 쿠키에 회원 정보를 저장합니다. |
| 5   | FR-05       | 회원   | 기능 | 회원 기능  | 프로필 작성    | 한줄 소개 작성 및 여행형 사진 업로드가 가능합니다. |
| 6   | FR-06       | 비회원 | 기능 | 회원 기능  | 타 회원 정보 조회 | 프로필을 등록한 회원들을 조회가 가능합니다. |
| 7   | FR-07       | 회원   | 기능 | 회원 기능  | 마이 페이지(활동 내역 조회) | 등록한 프로필 및 작성한 게시글 확인이 가능합니다. |
| 8   | FR-08       | 비회원 | 비기능 | 게시판(리뷰, 모집, 당근) 기능 | 페이징 기능  | 게시물을 일정 크기로 나눠서 조회 가능합니다. |
| 9   | FR-09       | 회원   | 기능 | 게시판(리뷰, 모집, 당근) 기능 | 게시글 작성  | 게시글 유형에 따라 글작성 이 가능합니다. |
| 10  | FR-10       | 회원   | 기능 | 게시판(리뷰, 모집, 당근) 기능 | 사진 업로드  | 게시글에 사진 업로드가 가능합니다. |
| 11  | FR-11       | 회원   | 기능 | 게시판(리뷰, 모집, 당근) 기능 | 위치 정보 저장 | KakaoMap API를 활용하여 위치 정보 저장이 가능합니다. |
| 12  | FR-12       | 회원   | 기능 | 게시판(리뷰, 모집, 당근) 기능 | 평점 저장    | 게시글 유형이 리뷰인 경우 평점 입력이 가능합니다. |
| 13  | FR-13       | 회원   | 기능 | 게시판(리뷰, 모집) 기능      | 여행 일정 및 경비 정보 업로드 | 게시글 유형이 모집인 경우 여행일정 및 경비 정보 입력이 가능합니다. |
| 14  | FR-14       | 회원   | 기능 | 게시판(당근) 기능          | 판매 정보 업로드 | 게시글 유형이 당근인 경우 판매정보 입력이 가능합니다. |
| 15  | FR-15       | 회원   | 기능 | 게시판(리뷰, 모집, 당근) 기능 | 게시글 수정  | 작성자는 게시글 작성 내용을 수정할 수 있습니다. |
| 16  | FR-16       | 회원   | 기능 | 게시판(리뷰, 모집, 당근) 기능 | 게시글 삭제  | 작성자는 게시글 삭제가 가능합니다. |
| 17  | FR-17       | 비회원 | 기능 | 게시판(리뷰, 모집, 당근) 조회 | 게시글 조회(검색, 필터링) | 게시글 검색 및 필터링을 통해 조건에 맞는 결과 조회가 가능합니다. |
| 18  | FR-18       | 회원   | 기능 | 게시판(리뷰, 모집, 당근) 조회 | 게시글 조회수 증가 | 게시글 상세 조회 시 조회수가 증가합니다. |
| 19  | FR-19       | 회원   | 기능 | 게시판 댓글(리뷰, 모집, 당근) 기능 | 댓글 작성    | 회원은 게시물에 댓글 작성이 가능합니다. |
| 20  | FR-20       | 회원   | 기능 | 채팅방(1대1) 기능            | 1대1 채팅방(사용자) | 회원은 타 회원과 1대1 채팅이 가능합니다. |
| 21  | SC-01       | 회원   | 비기능 | 회원 기능                  | 비밀번호 암호화 | 회원가입 시 비밀번호는 salt와 SHA-256 알고리즘을 활용하여 암호화한다. |
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
![guide_erd_d](https://github.com/GILAJABI/guide/assets/93421611/9eab9190-c60c-4a55-9a78-2e9fa24132ca)

<br>
<br>

## 👀 형상 관리
<br>
<br>

## 📣 기능 소개
<br>
<br>

## 🎥 시연 영상
<br>
<br>
