<div align="center">
<h2>GATE : 강아지와의 데이트, 행복의 문을 여는 순간</h2>

'GATE'는 반려인과 반려견이 함께 행복한 시간을 보낼 수 있도록 돕는 플랫폼입니다.<br>
반려견 동반 가능 장소를 쉽고 정확하게 검색할 수 있는 기능을 제공하며,<br>
사용자와 반려견의 프로필을 기반으로 맞춤 장소를 추천합니다. 

#### "GATE와 함께 행복의 문을 열어보세요!"
</div>


# 목차
- [기능 소개](#각-기능-소개)
- [개발환경](#개발환경)
- [디렉토리 구조](#디렉토리-구조)
- [인프라 구조](#인프라-구조)
- [팀 규칙](#팀-규칙)
- [고민한 이야기](#고민한-이야기)

## 각 기능 소개 
#### [회원 시스템]
- 카카오 소셜 로그인
- 회원정보 조회/수정/삭제

#### [반려견 정보 관리 시스템]
- 반려견 프로필 조회/등록/수정/삭제

#### [장소 검색 시스템]
- 검색 조건에 따른 장소 리스트 조회
- 현 위치 기반 조회

#### [즐겨찾기 시스템]
- 장소 상세보기에서 즐겨찾기 등록
- 일정 생성 시, 즐겨찾기 리스트 조회가능
- 지도에 즐겨찾기에 등록한 위치 표시

#### [후기 시스템]
- 장소에 대한 사진, 동영상, 별점, 텍스트 리뷰 작성
- AI 후기 요약 (ChatGPT 이용)

#### [일정 시스템]
- 일정 경로 추천
- 일정 생성/조회/수정/삭제

## 개발환경
![Java](https://img.shields.io/badge/Java-17-007396?style=for-the-badge&logo=OpenJDK&logoColor=white)  
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.5-6DB33F?style=for-the-badge&logo=Spring&logoColor=white)  
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=for-the-badge&logo=Spring&logoColor=white)  
![Lombok](https://img.shields.io/badge/Lombok-green?style=for-the-badge&logo=Awesomelists&logoColor=white)  
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=PostgreSQL&logoColor=white)  
![Redis](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=Redis&logoColor=white)  
![OpenFeign](https://img.shields.io/badge/OpenFeign-4.1.1-blue?style=for-the-badge)  
![Spring AI](https://img.shields.io/badge/Spring%20AI-1.0.0--SNAPSHOT-orange?style=for-the-badge)  
![Querydsl](https://img.shields.io/badge/Querydsl-5.0.0-jakarta?style=for-the-badge&logo=Hibernate&logoColor=white)  
![JUnit 5](https://img.shields.io/badge/JUnit%205-25A162?style=for-the-badge&logo=JUnit5&logoColor=white)  
![Elasticsearch](https://img.shields.io/badge/Elasticsearch-005571?style=for-the-badge&logo=Elasticsearch&logoColor=white)  

## 디렉토리 구조
```
src/main/java/com/ureca/gate
├── dog                # 반려견 관리 기능
├── favorites          # 즐겨찾기 기능
├── global             # 설정 및 공통 기능
├── member             # 사용자 관리 기능
├── place              # 장소 검색 및 추천
├── plan               # 일정 관리 기능
└── review             # 후기 관리 기능
    ├── controller     # REST API 컨트롤러
    ├── domain         # 도메인 객체 정의
    ├── infrastructure # 어댑터 및 연동
    └── service        # 서비스 로직 및 비즈니스 처리
 
```

## 인프라 아키텍처
![인프라 아키텍처](https://github.com/user-attachments/assets/c3d9c1b5-ea03-4444-a644-33f714ec2d1c)


#### 백엔드 파이프라인
github action - S3 - CodeDeploy - ec2
github action으로 jar 파일을 S3에 저장한 후 CodeDeploy로 ec2에 배포합니다.

#### 프론트엔드 파이프라인
github action - S3 - CloudFront - Route53
github action으로 react 프로젝트를 S3에 저장한 후에 CloudFront를 사용해 웹서버를 만듭니다. 그리고 route53을 이용해서 도메인을 인증하고 ssl인증서를 발급하고 ALB와 https 통신을 합니다.

#### 전체 구조
사용자는 웹서버로 접속하고 ALB를 통해서 spring boot가 올라가 있는 ec2와 통신합니다. 그리고 spring boot는 rds와 elasticache의 redis를 이용해 데이터를 저장하거나 가져옵니다.

## url
#### swagger url
http://gate-ttest-alb-2010650530.ap-northeast-2.elb.amazonaws.com/swagger-ui/index.html#/Place%20API/searchCities_1

#### 사이트 도매인
https://www.gatepet.kro.kr/

## 추가 자료 링크
- [깃허브 컨벤션](https://grand-distance-643.notion.site/Github-13fb3dd3958f80419252c23f66430deb?pvs=4)
- [ERD](https://drive.google.com/file/d/1JrQ-1bARXDlIoiiZa85IKoe68m3ZUgZf/view?usp=sharing)
- [기획안](https://drive.google.com/file/d/1bFmoEa3N8Gt4Te9tDfZ9LUrhtVv30bPR/view?usp=sharing)
