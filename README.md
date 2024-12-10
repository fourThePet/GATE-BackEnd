# GATE - BackEnd
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
![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=OpenJDK&logoColor=white) 
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=Spring&logoColor=white) 
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=for-the-badge&logo=Spring&logoColor=white) 
![Lombok](https://img.shields.io/badge/Lombok-green?style=for-the-badge&logo=Awesomelists&logoColor=white) 
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white) 
![JUnit 5](https://img.shields.io/badge/JUnit%205-25A162?style=for-the-badge&logo=JUnit5&logoColor=white)

## 디렉토리 구조
```
src/main/java/com/ureca/gate
├── member
├── dog
├── place
├── favorite
├── review
└── schedule
    ├── controller
    │   ├── inputport
    │   │   └── ScheduleService.java
    │   ├── response
    │   │   └── ScheduleReseponse.java
    │   ├── request
    │   │   └── ScheduleRequest.java
    │   ├── ScheduleController.java
    ├── domain
    │   ├── Schedule.java
    ├── infrastructure
    │   ├── jpaAdapter
    │   │   └── Entity
    │   │   │   └── ScheduleEntity.java
    │   │   └── ScheduleJpaRepository.java
    │   │   └── ScheduleRepositoryImpl.java
    │   │
    └── service
        ├── outputport
        │   └── ScheduleRepository.java
        └── ScheduleServiceImpl.java
 
```

## 인프라 구조
![인프라 구조](https://github.com/user-attachments/assets/de9e851a-ddc3-4faa-af26-7a63fd33848e)

#### 백엔드 파이프라인
github action - S3 - CodeDeploy - ec2
github action으로 jar 파일을 S3에 저장한 후 CodeDeploy로 ec2에 배포합니다.

#### 프론트엔드 파이프라인
github action - S3 - CloudFront - Route53
github action으로 react 프로젝트를 S3에 저장한 후에 CloudFront를 사용해 웹서버를 만듭니다. 그리고 route53을 이용해서 도메인을 인증하고 ssl인증서를 발급하고 ALB와 https 통신을 합니다.

#### 전체 구조
사용자는 웹서버로 접속하고 ALB를 통해서 spring boot가 올라가 있는 ec2와 통신합니다. 그리고 spring boot는 rds와 elasticache의 redis를 이용해 데이터를 저장하거나 가져옵니다.

## 팀 규칙
- [깃허브 컨벤션](https://grand-distance-643.notion.site/Github-13fb3dd3958f80419252c23f66430deb?pvs=4)
