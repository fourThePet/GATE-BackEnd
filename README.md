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
    │   ├── port
    │   │   └── ScheduleService.java
    │   ├── response
    │   │   └── ScheduleReseponse.java
    │   ├── request
    │   │   └── ScheduleRequest.java
    │   ├── ScheduleController.java
    │   └── ScheduleCreateController.java
    ├── domain
    │   ├── Schedule.java
    │   └── ScheduleCreate.java
    ├── infrastructure
    │   ├── ScheduleEntity.java
    │   ├── ScheduleJpaRepository.java
    │   └── ScheduleRepositoryImpl.java
    └── service
        ├── port
        │   └── ScheduleRepository.java
        └── ScheduleServiceImpl.java
 
```

## 인프라 구조
![인프라 구조](https://github.com/user-attachments/assets/0b3bb7d6-43b1-4dd3-a04e-ab93e65561f4)

## 팀 규칙
- [깃허브 컨벤션](https://grand-distance-643.notion.site/Github-13fb3dd3958f80419252c23f66430deb?pvs=4)

## 고민한 이야기
- [공공데이터 전처리](https://grand-distance-643.notion.site/142b3dd3958f8091985ec731eab65a40?pvs=4)
