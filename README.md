# EnjoyTrip ✈
REST API + SWAGGER 구현

SWAGGER : `http://localhost:8090/enjoytrip/swagger-ui.html`

## 1. 관광지 정보 조회
동적 쿼리를 이용하여 `시군`, `구군`, `관광지유형`, `검색어`에 따라 결과를 JSON 형태로 return한다
![1](/uploads/a7418dcef1cfbf1b9b01dd0d7b471dd8/1.JPG)
시도코드 31, 검색어 "계곡"에 해당하는 관광지 정보를 JSON 형태로 POST



![2](/uploads/3d4b6a5b91f7cd7bb3eb4132ad2a0286/2.JPG)\
시도코드 31, 검색어 "계곡"에 해당하는 관광지 정보를 JSON 형태로 가져온다

## 2. 회원관리
`members`테이블이다.
role이 1이면 관리자이고, 2이면 일반사용자이다.

![2](/uploads/396e6573106c5a9db9eb5cc818cdd648/2.PNG)


일반회원과 관리자에 대한 Swagger이다.
![1](/uploads/14da88856cadc0ffecc04ab495e4b365/1.PNG)
