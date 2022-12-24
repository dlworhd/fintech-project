# 명세서

1. ERD 설계

<img width="100%" alt="fintech-erd" src="https://user-images.githubusercontent.com/102597172/208899026-220c3e8a-24e4-409b-8c73-e39ee55b411b.png">


2. 어플리케이션 구조

<img width="100%" src="https://user-images.githubusercontent.com/102597172/208916279-a55d0f8d-0959-4853-a0fc-12cc28f675f2.png">





3. 기능 구현

<table align="center">


<tr>


<th>서비스</th>

<th>관리</th>

</tr>


<td width="510">

- [x] 회원가입
- [x] 로그인
- [x] 계좌 생성/삭제
- [x] 계좌 입금/출금
- [x] 계좌 송금
- [x] 잔액 조회
- [x] 계좌 목록 조회
- [x] 거래 내역 조회
- [x] 사용자 정보 조회
- [x] 기간별 거래 조회

</td>

<td width="510">

- [x] 계정 상태 변경
- [x] 유저 상태 변경
- [x] 최근 가입 조회
- [x] 최근 거래 조회

</td>

</tr>

</table>



- 사용자

|메소드|URI|설명|
|---|---|---|
|POST|/auth/login|로그인|
|POST|/user/register|회원가입|
|GET|/user/info|유저 정보 조회|
|GET|/user/info/{username}|유저 권한 조회|
|GET|/user/email-auth|이메일 인증|
|GET|/user/accounts|계좌 목록 조회|


- 관리자

|메소드|URI|설명|
|---|---|---|
|PUT|/admin/account|계좌 상태 변경|
|PUT|/admin/user|사용자 상태 변경|
|GET|/admin/transactions |전체 거래 조회|
|GET|/admin/users|최근 가입 조회|



- 계좌

|메소드|URI|설명|
|---|---|---|
|POST|/account|계좌 생성|
|DELETE|/account|계좌 삭제|
|POST|/account/deposit|입금|
|POST|/account/withdraw|출금|
|POST|/account/transfer|송금|
|POST|/account/period|기간별 거래 조회|
|GET|/account/balance|잔액 조회|
|GET|/account/histories|거래 조회|



---

# 키워드

- JWT 토큰

-> 보안에는 강하지만 서버의 메모리나 데이터베이스의 자원을 사용한다는 단점을 가진 세션을 보완하기 위해서 토큰 방식으로 구현을 했습니다.
   여기서 말하는 토큰은 JWT 토큰 방식이며 Public Key는 웹 브라우저에 저장을 하지만 Secret Key가 저장되어 있는 곳에서만 복호화를 할 수 있다는 장점이 있기에 사용했습니다.

- Redis



- 구글 SMTP 이메일 API
- Spring Security




