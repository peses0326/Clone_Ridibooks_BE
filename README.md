### 주저리 주저리

### 오늘 배운 것

# Clone_Ridibooks_BE

## **📕 개요**

- 명칭 : Ridibooks 클론코딩
- 개발 인원 : 4명
    - Front end: **노예찬, 우지음**
    - Back end: **박응수, 김인섭A**
- 개발 기간 : 2021.07.16 ~ 2021.07.22
- 주요 기능
    - 댓글 좋아요
    - 게시글과 댓글에 대한 CRUD
    - 책에 별점 1~5점 기능
    - JWT를 이용한 로그인
- 스택 : react/spring
- 형상 관리 툴 : git

## **☝🏻 프로젝트 특징**

- 프론트엔드와 백엔드를 분리하여 프로젝트 개발
    - 각 파트별로 Repository를 생성 후 작업
    - 프론트: AWS S3
    - 백엔드: AWS EC2
    - DB : AWS RDS
    - 빌드 후, S3와 EC2를 연동
        - API 명세서에 따라 API호출 및 응답 확인
    - 로그인 시 JWT, 쿠키 사용

## **🎈 개발 환경**

- 프론트: AWS S3
- 백엔드: AWS EC2
- DB : AWS RDS`
- Framwork : spring
- IntelliJ Ultimategradle-7.1.1 Java 8SpringBoot 2.5.2  
- 의존성 추가Lombok WebSecurity Jpa MySql  

## **📃 API 설계**


| 메인       | 페이지별  책리스트가져오기    | GET    |
| ---------- | ----------------------------- | ------ |
| 로그인     | 로그인                        | POST   |
| 로그아웃   | 로그아웃                      | GET    |
| 회원가입   | 회원가입                      | POST   |
| 리뷰페이지 | 특정  책 리뷰 페이지 가져오기 | GET    |
| 리뷰페이지 | 댓글  가져오기                | GET    |
| 리뷰페이지 | 해당  유저 댓글 가져오기      | GET    |
| 리뷰페이지 | 댓글 쓰기                     | POST   |
| 리뷰페이지 | 댓글 수정                     | PUT    |
| 리뷰페이지 | 댓글 삭제                     | DELETE |
| 리뷰페이지 | 좋아요                        | POST   |
