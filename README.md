# VONGOLE

##  1. 프로젝트 한 줄 설명
빠른 봉사인원 모집 및 참여 가능한 캘린더⭐

[VONGOLER 노션 링크](https://www.notion.so/VONGOLE-ef8d0e42dabb4067b25f57240ddcf171)

## 2. 핵심 기능 (유저 플로우 순)

### 1) 회원가입 / 로그인

    * 일반 회원가입과 봉사 기관 회원가입을 따로 함
    * 기관 회원 가입에서는 사업자 등록증 이미지를 첨부해야 회원 가입 가능
    * 로그인 방법은 VONGOLE 서비스에서 제공하는 로그인과 카카오 소셜 로그인 2가지

### 2) 게시물 / 댓글
    
    * 봉사 기관만이 봉사 활동을 모집하는 게시글, 댓글 CRUD가 모두 가능
    * 게시물을 작성할 때엔 제목, 내용, 이미지, 주소, 해시태그 등을 작성    
    * 일반 회원은 게시물 조회와 댓글 CRUD 가능

### 3) 검색
    * 봉사 활동 날짜 별 검색
    * 해시태그 별 검색
    * 장소 별 검색
    * 동적 쿼리 사용해서 여러 조건으로 검색할 예정
        

### 4) 봉사 신청
    * 일반 회원은 봉사 활동을 모집하는 게시글에 봉사 활동 신청을 할 수 있음
    
### 5) 일반 회원 페이지
    * 프로필 조회 / 수정
    * 봉사 지원한 게시물 목록 조회
    * 신청 승인 여부 별로 조회
    * 내가 작성한 댓글 목록 조회

### 6) 봉사 기관 페이지
    * 프로필 조회 / 수정
    * 내 게시물 목록 조회
    * 봉사 활동 지원자 전체 목록 조회
    * 게시물에 해당하는 지원자 목록 조회

### 7) 좋아요
    * 게시물 / 댓글 좋아요 기능 및 좋아요 수 기능 (예정)    

    

## 3. Member
### 1) Designer
- 전혜진

### 2) FE
- 김성호
- 장석원
- 김경일

### 3) BE
- 김성민
- 김원재
- 강진구
- 강창식

## 4. Dependencies
<div align=center> 
<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/amazone ec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white">
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/amazon s3-569A31?style=for-the-badge&logo=amazons3&logoColor=white">
<img src="https://img.shields.io/badge/github actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white">
<br>
<img src="https://img.shields.io/badge/Websockets-41454A?style=for-the-badge&logo=&logoColor=white">
<img src="https://img.shields.io/badge/JSON Web Tokens-000000?style=for-the-badge&logo=JSONWebTokens&logoColor=white">
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
</div>
## 5. ERD 설계
![ERD](https://github.com/SimBongSa/Back-end/blob/main/Vongole/src/main/resources/static/%EB%B4%89%EA%B3%A8%EB%A0%88%20%EC%B5%9C%EC%A2%85_.png)

## 6. Trouble Shooting


### > 회원 테이블 통합 VS 일반 회원과 기관 회원 테이블 분리
VONGOLE에서는 회원에 따라 권한을 달리한다. 일반 회원은 게시물 조회만 가능하고 봉사 기관 회원은 게시물 CRUD 모두 가능하다. 이 두 회원의 권한을 달리 하기 위해 테이블 설계를 어떻게 해야할지 팀원들과 고민을 많이 했다.

테이블을 둘로 나누게 될 경우, 각 테이블로부터 불러온 멤버 객체를 어떻게 구분을 지어야 할지 잘 모르는 상황이였고, 테이블을 하나로 통합하기에는 일반 회원과 봉사 기관 멤버 회원 가입 시 받는 입력값이 달라서 이 이슈에 대한 해결책이 필요한 상황이였다.

팀원들과의 회의를 통해 회원가입은 일반 회원, 봉사 기관 회원을 둘로 나누어서 하되, 하나의 테이블로 회원을 관리하고, 각 회원에게서 받는 입력값들을 모두 테이블의 컬럼으로 두고 각 회원에게서 입력받지 않은 값들은 null값으로 처리하도록 했다.

이바울 멘토님의 멘토링을 통해 컬럼에 null값을 저장하기보다는 특정값으로 초기화시켜주는 것이 좋다고 하셔서 이에 대한 공부를 해보고 리펙터링해보려고 한다.



### > OAuth 카카오 로그인 시 토큰발급 관련문제 
카카오 로그인 처리를 할때 유저에게 JWT토큰을 발급해줘야 하는데 우리 코드에서 토큰을 발급하는 메서드가 스프링 시큐리티 에서 제공해주는 메서드 같았는데 매개변수로 유저 아이디와 비밀번호를 받아서 토큰을 만들어 주는 메서드 였다. 

근데 카카오 로그인 유저는 비밀번호가 없어서 uuid값으로 만들어서 넣어줬는데 이렇게 하니까 로그인할때 비밀번호를 통해서 토큰을 만들어 줘야 하는데 비밀번호를 알 수 없어서 토큰을 만들 수 없는 문제가 생겼다.

그래서 카카오유저에게 특정 문자열을 비밀번호로 지정해서 토큰을 만들때 그 문자열을 사용해서 토큰을 발급해 주는 방식으로 해결했다.


### > FK 걸기 VS FK 끊기
FK를 걸어주는 목적에 대한 본질적인 궁금증이 팀 내에서 나오게 되었고, ERD 설계를 할 때 FK를 끊어야 할지 걸어야 할지 토론을 하게 되었다. 보통 생명주기를 함께 하는 테이블들을 관계를 맺어주기 위해 FK를 걸어주곤 한다.   

하지만 FK를 걸어 줌으로써 데이터 정합성을 강제하는 측면이 있다는 의견이 있었다. 프로젝트를 예로 들면 한 회원(Member)이 게시물(Board)를 작성한다고 할 때, 게시물 테이블에는 회원 PK값이 FK로 걸려있을 것이다. 이렇게 FK가 걸리게 되면 게시물 테이블을 조회할 때마다 FK로 걸려있는 회원이 존재하는지 정합성을 항상 확인해야만 하고, 회원을 삭제 시에도 이 회원이 작성한 게시글이 있을 경우, 회원만 삭제하면 데이터 정합성이 깨지기 때문에 해당 게시물을 먼저 삭제해 주어야만 한다. 

FK를 걸게 될 경우 DB 차원에서 데이터 정합성을 보장해 주는 것이 장점이자 단점으로 작용할 수 있다. 참조 무결성 제약으로 데이터 정합성을 보장해 주는 것은 일관성있는 테이블 관리를 할 수 있고, 테이블 간의 관계를 명확하게 나타내 준다는 장점이 있는 반면, DB의 성능이 느려지고, 유지보수 등의 이슈로 테이블 구조를 변경하게 될 경우, 확장이 어려울 수 있다는 단점이 있다.

하지만 아직까지 우리 팀은 FK를 걸었을 때의 성능 이슈 등을 체감하지 못하는 상황이고, 경우에 따라 슬랙을 예로 들면, 게시물을 삭제하더라도 댓글 데이터는 그대로 남아있게 하는 정책이 존재하지만 우리 프로젝트에서는 게시물과 댓글의 생명주기를 함께 하기로 결정했기 때문에 FK를 걸어두는 것으로 결론이 나게 되었다.
### > @ModelAttribute 설정한 dto 바인딩 이슈
이미지를 업로드할 때, dto에 @ModelAttribute라는 어노테이션을 붙여서 요청을 들여온다. ModelAttribute의 경우에는 1대1 매핑을 해주는 RequestParam과는 달리 객체 매핑을 해주기 때문에 코드가 한결 깔끔해지고 순서가 바뀔 위험도 없다.

ModelAttribute를 사용할 때 유의할 점은 객체 매핑이 저절로 되는 것이 아니라 어떤 설정을 해줘야만 한다. @ModelAttribute 설정을 해준 dto를 객체로 바인딩해주기 위해서는 두가지 방법이 있다.

1. @AllArgsConstructor를 이용해서 각 필드를 초기화한 객체를 생성한다.
2. @NoargsConstructor과 @Setter로 각각의 필드 초기화

객체를 매핑한다는 개념 자체를 아예 모르고 있었고, dto에 기계적으로 @NoArgsConstructor와 @AllArgsConstructor를 붙여 놓았더니 객체 매핑이 되질 않아 NullPointerException 에러가 났고, @NoArgsConstructor 설정을 제거해 줌으로써 에러를 해결할 수 있었다.

코드 한 줄이라도 생각하고 작성해야 겠다는 다짐을 새삼 하게 되었던 이슈였다.
### > 프로필 수정 시 db에 update 적용 안되는 이슈
프로필 수정 시, Spring Security에서 제공하는 멤버 객체를 매개변수로 받아 요청받은 데이터로 수정했는데 db에 적용이 되지 않는 이슈가 있었다.

JPA가 제공하는 멤버 객체가 아니라서 db에 반영이 안되는 거라고 생각해서 JpaRepository로부터 불러온 멤버 객체를 업데이트하니까 db에 잘 적용이 되었다.


### > JWT 토큰 만료에 대한 예외처리
현재 구현하고 있는 스프링 기반 웹 프로젝트에서 사용자 인증 방식으로 Spring Security + JWT 인증 방식을 사용하고 있다.
처음에는 JwtFilter에서 예외를 던져주면 GlobalExceptionHandler에서 처리하면 되지 않을까라고 생각했지만 Filter는 아직 애플리케이션에 들어가지 못했다는 것을 깨달았다.
Filter는 Dispatcher Servlet 보다 앞단에 존재하며 Handler Intercepter는 뒷단에 존재하기 때문에 Filter 에서 보낸 예외는 Exception Handler로 처리를 못한다.
jwt 토큰 만료에 대한 예외처리를 하기 위해 현재 필터보다 앞단에 예외 처리를 위한 필터를 하나 더 두고 FilterChain으로 원래의 JWT 유효성 검사를 하던 필터로 요청을 넘겨주는 방법이 있었다. 필터 구성을 이런식으로 해두면 다음 차례 필터의 로직 수행 중 던져진 예외가 앞서 거쳤던 필터로 넘어가서 처리가 가능하게 된다.
즉, 원래는
 * 요청 ➡️ JwtAuthenticationFilter
의 형태였다면,
* 요청 ➡️ JwtExceptionFilter ➡️ JwtAuthenticationFilter
로 필터를 구성해서 JwtAuthenticationFilter에서 던진 예외를 JwtExceptionFilter가 처리할 수 있도록 했다.


