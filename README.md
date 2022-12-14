# ERD 설계

<img width="100%" alt="fintech-erd" src="https://user-images.githubusercontent.com/102597172/208899026-220c3e8a-24e4-409b-8c73-e39ee55b411b.png">


# 어플리케이션 구조

<img width="100%" src="https://user-images.githubusercontent.com/102597172/208916279-a55d0f8d-0959-4853-a0fc-12cc28f675f2.png">


# Swagger - API Document

![Untitled-6](https://user-images.githubusercontent.com/102597172/209642606-eb8da3b4-4bee-4031-9894-cff2b5e7fa0f.png)


# 상세 요구 사항

<table align="center">


<tr>


<th>사용자</th>

<th>관리자</th>

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

# 키워드

- JWT 토큰

보안에는 강하지만 서버의 메모리나 데이터베이스의 자원을 사용한다는 단점을 가진 세션을 보완하기 위해서 토큰 방식으로 구현을 했습니다.
여기서 말하는 토큰은 JWT 토큰 방식이며 Public Key는 웹 브라우저에 저장을 하지만 Secret Key가 저장되어 있는 곳에서만 복호화를 할 수 있다는 장점이 있기에 사용했습니다.

- Redis

조회가 잦은 데이터들은 Redis 캐시 서버에 저장을 하여 로딩을 최소화하였습니다. 아무래도 인 메모리 구조를 가지고 있기 때문에 하드디스크 같은 저장소에서 가져오는 것보다는 1000배 이상으로 빠른 속도를 기대할 수 있었고, 실무에서도 꾸준히 쓰이는 데이터베이스이기 때문에 적용을 시켰습니다. 다만, 메모리에 데이터가 저장되기 때문에 재부팅시에는 모든 데이터가 휘발된다는 단점이 있는데 이러한 단점은 Master Slave 방식으로 보완 가능합니다.

- 구글 SMTP 이메일 API

구글에서 제공하는 이메일 인증 API 기능을 사용하여 유저의 회원가입시 이메일 인증을 요구하도록 하고 있습니다. 인증이 완료되어야만 정상적으로 ACTIVE한 상태가 되고, 서비스를 이용할 수 있게 됩니다.

- Spring Security

해당 서비스는 USER와 ADMIN의 역할 총 2개로 나눠져있으며, ADMIN은 어떤 도메인이든 접근할 수 있지만, USER는 USER만 접근할 수 있는 도메인만 허용을 해야 하기 때문에 Spring Security를 구현    했습니다. 회원가입을 하면서 비밀번호나 주민등록번호를 입력하게 되는데 이때, 적용되는 패스워드 알고리즘도 Spring Security에서 제공하는 기능 중 하나입니다.
