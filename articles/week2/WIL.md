# 2주차 WIL 적는 곳

##  Week 2: 프로젝트를 위한 배경지식 공부

이번 주차에는 백엔드 프로젝트를 위한 핵심 이론들을 정리하며, 객체지향 설계 원칙과 웹 통신 기초, REST API 설계 방식, 배포 및 클라우드 개념 등을 학습하였다. 각각의 이론은 실제 Spring 기반 프로젝트에서 마주하게 될 구조 설계, API 개발, 디버깅, 배포 상황을 이해하는 데 큰 도움이 되었다.

---

## ✅ 객체지향 설계

### 🔸 결합도와 응집도
- **결합도(Coupling)**는 클래스 간의 의존성 정도를 뜻하며, 낮을수록 모듈의 독립성이 높아진다.
- **응집도(Cohesion)**는 한 클래스 내부의 기능이 얼마나 밀접하게 연관되어 있는지를 나타내며, 높을수록 하나의 책임에 집중된 설계가 가능하다.
- 예를 들어, 데이터 접근 로직과 비즈니스 로직을 하나의 클래스에 넣는 것은 응집도가 낮고 결합도가 높아지는 대표적인 사례이다.

🔗 참고: [inpa 블로그 - 결합도/응집도](https://inpa.tistory.com/entry/OOP-%F0%9F%92%A0-%EA%B0%9D%EC%B2%B4%EC%9D%98-%EA%B2%B0%ED%95%A9%EB%8F%84-%EC%9D%91%EC%A7%91%EB%8F%84-%EC%9D%98%EB%AF%B8%EC%99%80-%EB%8B%A8%EA%B3%84-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0-%EC%89%BD%EA%B2%8C-%EC%A0%95%EB%A6%AC)

### 🔸 SOLID 원칙
- SRP (단일 책임 원칙): 클래스는 하나의 책임만 가져야 한다.
- OCP (개방-폐쇄 원칙): 확장에는 열려 있고 변경에는 닫혀 있어야 한다.
- LSP (리스코프 치환 원칙): 자식 클래스는 부모 클래스를 대체할 수 있어야 한다.
- ISP (인터페이스 분리 원칙): 사용하지 않는 인터페이스에 의존하지 않아야 한다.
- DIP (의존 역전 원칙): 고수준 모듈은 저수준 모듈에 의존하면 안 되며, 추상화에 의존해야 한다.

🔗 참고: [inpa 블로그 - SOLID 원칙](https://inpa.tistory.com/entry/OOP-%F0%9F%92%A0-%EA%B0%9D%EC%B2%B4-%EC%A7%80%ED%96%A5-%EC%84%A4%EA%B3%84%EC%9D%98-5%EA%B0%80%EC%A7%80-%EC%9B%90%EC%B9%99-SOLID)

### 🔸 멘토님 객체지향 글 정리
- 객체지향이 중요한 이유는 유지보수성과 확장성이 높은 코드를 작성하기 위함이다.
- 현실 세계의 개념(예: '상품')을 추상화하여 코드로 표현하고, 이 추상화를 일관성 있게 유지하는 것이 설계의 핵심임을 강조하셨다.

🔗 참고: [GDG 멘토 객체지향 글](https://github.com/gdg-hongik-univ-program/gdg-4th-backend-mission/blob/main/articles/assignment_spec/week2/articles/object.md)

---

## 🌐 네트워크 및 HTTP 기초

### 🔸 웹 통신 개념
- 웹 통신은 요청(Request)과 응답(Response)으로 구성되며, HTTP 프로토콜 위에서 작동한다.
- 이때 요청 메서드(GET, POST, PUT, DELETE 등), 상태 코드, 헤더 정보 등이 중요하게 작용한다.

🔗 참고: [웹 통신 기초](https://inpa.tistory.com/entry/%EC%9B%B9-%ED%86%B5%EC%8B%A0-%EA%B8%B0%EB%B3%B8-%EC%9D%B4%EB%A1%A0-%ED%86%B5%ED%95%A9-%EC%9A%94%EC%95%BD-%EC%A0%95%EB%A6%AC)

### 🔸 URL 구조
- URL은 프로토콜, 호스트, 포트, 경로, 쿼리 스트링 등으로 구성되며, RESTful API 설계에서 경로는 자원의 위치를 표현한다.
- 예시: `https://api.example.com/items/1`

🔗 참고: [URL 구성 정리](https://inpa.tistory.com/entry/WEB-%F0%9F%8C%90-URL-%EA%B5%AC%EC%84%B1-%EC%9A%94%EC%86%8C-%EC%9A%94%EC%B2%AD-%ED%9D%90%EB%A6%84-%EC%A0%95%EB%A6%AC)

### 🔸 HTTP 상태 코드
- 2xx: 성공 (200 OK, 201 Created 등)
- 4xx: 클라이언트 오류 (400 Bad Request, 401 Unauthorized 등)
- 5xx: 서버 오류 (500 Internal Server Error 등)

🔗 참고: [HTTP 상태코드 정리](https://inpa.tistory.com/entry/HTTP-%F0%9F%8C%90-%EC%83%81%ED%83%9C-%EC%BD%94%EB%93%9C-1XX-5XX-%EC%B4%9D%EC%A0%95%EB%A6%AC%ED%8C%90-%F0%9F%93%96)

### 🔸 REST API와 Stateful / Stateless

- **REST API**는 리소스를 URI로 식별하고, HTTP 메서드(GET, POST, PUT, DELETE 등)를 통해 해당 리소스에 대한 행위를 표현하는 방식이다.
- REST의 중요한 설계 원칙 중 하나는 **Stateless(무상태성)** 이다.

#### 🔹 Stateless (무상태성)
- 서버는 이전 요청의 상태를 **기억하지 않는다**.
- 따라서 매 요청마다 클라이언트가 **필요한 상태 정보를 함께 전송**해야 한다.
- 예: JWT 기반 인증에서는 사용자가 로그인한 후에도 매 요청에 토큰을 실어 보내야 한다.
- 서버는 현재 요청만을 보고 처리하며, 클라이언트와의 세션을 유지하지 않는다.

#### 🔹 Stateful (상태 유지형)
- 서버가 클라이언트의 상태 정보를 **기억하고 관리**하는 구조이다.
- 클라이언트는 로그인 이후 별도의 인증 정보를 전달하지 않아도 되고, 서버는 이전 상태를 바탕으로 요청을 처리한다.
- 상태 정보가 누적되기 때문에 분산 처리나 확장에 제약이 생길 수 있다.

👉 REST API는 본질적으로 **Stateless 설계 원칙을 따르며**,  
이로 인해 서버는 확장성과 유연성을 확보할 수 있고, 클라이언트는 매 요청마다 필요한 정보를 명확히 제공해야 한다.

🔗 참고:
- [Stateful vs Stateless](https://inpa.tistory.com/entry/WEB-%F0%9F%93%9A-Stateful-Stateless-%EC%A0%95%EB%A6%AC)
- [REST API 정리](https://inpa.tistory.com/entry/WEB-%F0%9F%8C%90-REST-API-%EC%A0%95%EB%A6%AC)

---

## ☁️ 클라우드 및 배포 관련

### 🔸 CORS (Cross-Origin Resource Sharing)
- 서로 다른 Origin 간 요청이 브라우저에 의해 차단되는 것을 방지하기 위한 정책.
- API 개발 시 백엔드에서 명시적으로 CORS를 허용해야 하며, 허용되지 않으면 `Access-Control-Allow-Origin` 오류가 발생한다.

🔗 참고: [CORS 정리](https://inpa.tistory.com/entry/WEB-%F0%9F%93%9A-CORS-%F0%9F%92%AF-%EC%A0%95%EB%A6%AC-%ED%95%B4%EA%B2%B0-%EB%B0%A9%EB%B2%95-%F0%9F%91%8F)

### 🔸 서버리스(Serverless)
- 서버를 직접 구축하지 않고, 필요한 함수 단위의 코드만 배포하는 방식.
- BaaS(Backend as a Service)나 FaaS(Function as a Service)의 형태로 제공되며, 간단한 로직 구현에 유리함.

🔗 참고: [서버리스 개념 정리](https://inpa.tistory.com/entry/WEB-%F0%9F%8C%90-%EC%84%9C%EB%B2%84%EB%A6%AC%EC%8A%A4ServerLess-%EA%B0%9C%EB%85%90-%F0%9F%92%AF-%EC%B4%9D%EC%A0%95%EB%A6%AC-BaaS-FaaS)

---