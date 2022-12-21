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
- [x] 보유 계좌 확인
- [x] 입출금 조회
- [x] 송금 조회
- [x] 전체 거래 조회
- [x] 기간별 거래 조회

</td>

<td width="510">

- [x] 사용자 관리
- [x] 계좌 관리
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
|GET|/user/info{username}|유저 권한 조회|
|GET|/user/email-auth|이메일 인증|


- 관리자

|메소드|URI|설명|
|---|---|---|
|PUT|/admin/account|계정 상태 변경|
|PUT|/admin/user|유저 상태 변경|
|GET|/admin/transactions |최근 거래 조회|
|GET|/admin/users|최근 가입 조회|


- 계좌

|메소드|URI|설명|
|---|---|---|
|POST|/account|계좌 생성|
|DELETE|/account|계좌 삭제|
|POST|/account/deposit|입금|
|POST|/account/withdraw|출금|
|POST|/account/transfer|송금|
|GET|/account/balance|잔액 조회|
|GET|/account/histories|거래 내역 조회|
|GET|/account/lists|계좌 목록 조회|









