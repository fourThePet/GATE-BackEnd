# GATE - Frontend
<div align="center">
<h2>GATE : 강아지와의 데이트, 행복의 문을 여는 순간</h2>

'GATE'는 반려인과 반려견이 함께 행복한 시간을 보낼 수 있도록 돕는 플랫폼입니다. 반려견 동반 가능 장소를 쉽고 정확하게 검색할 수 있는 기능을 제공하며, 사용자와 반려견의 프로필을 기반으로 맞춤 장소를 추천합니다. 

#### GATE와 함께 행복의 문을 열어보세요!
</div>


# 목차
- [개발환경](#개발환경) 
- [서버 실행 방법](#서버-실행-방법)
- [디렉토리 구조](#디렉토리-구조)
- [각 기능 소개](#각-기능-소개)

## 개발환경
![React](https://img.shields.io/badge/react-%2320232a.svg?style=for-the-badge&logo=react&logoColor=%2361DAFB) ![TypeScript](https://img.shields.io/badge/typescript-%23007ACC.svg?style=for-the-badge&logo=typescript&logoColor=white) ![Vite](https://img.shields.io/badge/vite-%23646CFF.svg?style=for-the-badge&logo=vite&logoColor=white) ![Yarn](https://img.shields.io/badge/yarn-%232C8EBB.svg?style=for-the-badge&logo=yarn&logoColor=white)

## 서버 실행 방법

- 최초 실행
```
# node 버전 확인
node -v

# yarn 설치
npm install -g yarn
yarn --version
yarn install 

```

- 서버 실행 
```
yarn dev
```

## 디렉토리 구조
```
📦src
 ┃ ┣ 📂pages
 ┃ ┃ ┣ 📂 home
 ┃ ┃ ┣ 📂 mypage
 ┃ ┃ ┣ 📂 login
 ┃ ┃ ┗ 📂 ...
 ┃ ┣ 📂assets
 ┃ ┣ 📂api
 ┃ ┣ 📂components
 ┃ ┣ 📂interfaces
 ┃ ┣ 📂styles
 ┃ ┣ 📂stores
 ┃ ┣ 📂stories
 ┃ ┣ 📜App.tsx
 ┣📜package.json
 ┗📜package-lock.json
 
```
#### 1. 📦 src
애플리케이션의 핵심 소스 코드가 저장된 루트 폴더

#### 2. 📂 pages
각 페이지별로 컴포넌트를 분리하여 저장. 라우팅 경로에 따라 연결되는 UI를 정의

#### 3. 📂 assets
정적 파일을 관리.
이미지, 폰트, 아이콘 등과 같은 애플리케이션 전역에서 사용되는 리소스.

#### 4. 📂 api
서버와 통신하기 위한 API 관련 코드를 저장. Axios 요청 파일, API 경로, 데이터 통신 로직 등이 포함.
#### 5. 📂 components
재사용 가능한 UI 컴포넌트를 저장. 버튼, 모달, 입력 폼 등 여러 페이지에서 공통으로 사용할 수 있는 컴포넌트.
#### 6. 📂 interfaces
TypeScript에서 사용하는 데이터 타입과 인터페이스를 정의.
API 응답 타입, 전역 상태 타입 등 프로젝트에서 사용하는 타입 정의를 관리.
#### 7. 📂 styles
전역 스타일과 테마를 관리.
#### 8. 📂 stores
전역 상태 관리 파일을 저장합니다. 상태 관리 라이브러리를 사용하는 경우 상태와 액션을 정의하는 파일.
#### 9. 📂 stories
Storybook을 사용하는 경우 컴포넌트의 시각적 테스트를 위한 파일을 저장.
컴포넌트별 .stories.tsx 파일을 통해 각 컴포넌트의 다양한 상태를 미리보기.
#### 10. 📜 App.tsx
애플리케이션의 진입점 컴포넌트입니다.
전역 라우팅 설정 및 애플리케이션의 기본 레이아웃 정의.
#### 11. 📜 package.json
프로젝트의 의존성 및 스크립트를 정의하는 파일.
설치된 라이브러리, 버전, 실행 가능한 스크립트 등의 정보 포함.
#### 12. 📜 package-lock.json
설치된 의존성의 정확한 버전을 기록.
내용:
동일한 환경에서 애플리케이션을 재현하기 위해 사용.


## 각 기능 소개 
#### [회원 시스템]
- 소셜 로그인
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

test
