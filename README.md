# Chimtooview · 2020.10 ~ 2021.01

**Chimtooview**는 특정 Youtube 채널의 영상 통계를 보여주고, 다양한 조건의 검색 기능을 제공하는 웹 서비스입니다.

(※ 현재는 배포되지 않은 개인 프로젝트입니다.)

![1](https://github.com/user-attachments/assets/d5f3ff09-e897-42cd-b59d-f224ab94007b)
![2](https://github.com/user-attachments/assets/431ef09f-e494-4c06-ac9d-f790ce49cc16)

## 😀 개발 인원

| 이름 | 역할 | 연락처 |
| --- | --- | --- |
| 박상연 | * | [dhkdwk1041@gmail.com](mailto:dhkdwk1041@gmail.com) |

## 🛠 프로젝트 기술 스택

- **프론트엔드**: React.js, Ant Design, Axios, Styled-components
- **백엔드**: Spring Boot, Spring Data JPA, MySQL
- **배포**: AWS (EC2, S3, Route 53)

## 📞 API

| API 명 | 설명 |
| --- | --- |
| `Youtube Data API` | 특정 Youtube 채널의 영상 데이터를 수집 |

## 🌐 Deploy

- **프론트엔드**: React 앱을 Webpack로 빌드한 후, 정적 파일을 AWS S3에 업로드하여 배포했습니다.
- **백엔드**: Spring Boot 서버를 AWS EC2에 배포했습니다.
