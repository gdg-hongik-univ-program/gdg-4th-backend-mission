# 3주차 WIL 적는 곳
## Week 3: 협업을 위한 주석 작성법과 JPA 기반 핵심 개념 정리

이번 주차에는 협업 환경에서 코드의 가독성을 높이기 위한 주석 작성 방식(JavaDoc, Swagger)과  
Spring Data JPA를 사용하는 데 필요한 기초 이론(ORM, Persistence Context)에 대해 학습했다.

---

## ✅ JavaDoc

JavaDoc은 메서드, 클래스, 필드에 문서화된 주석을 작성해 코드의 의도와 사용법을 명확히 전달할 수 있도록 도와준다.  
기본 문법은 `/** ... */` 블록에 `@param`, `@return`, `@throws` 등 태그를 사용하는 방식이며,  
IDE에서 자동으로 문서화되거나 툴팁으로 표시된다.

### 주요 적용
- `ItemService`, `ItemController` 내 모든 public 메서드에 작성
- 주석 예시:
```java
/**
 * 주어진 이름의 아이템을 조회합니다.
 * @param name 조회할 아이템 이름
 * @return 해당 이름을 가진 Item 객체
 */
```

---

## ✅ Swagger

Swagger는 REST API 명세를 자동 생성하는 도구로, 프론트엔드와의 협업에서 API 구조를 명확히 공유할 수 있도록 한다.  
Spring에서는 `springdoc-openapi` 라이브러리를 활용해 설정하며,  
`@Operation`, `@ApiResponses`, `@Tag` 등의 어노테이션을 사용해 각 API 엔드포인트에 주석을 추가한다.

### 적용 방식
- Controller 클래스의 모든 API에 Swagger 어노테이션 추가
- Swagger UI 주소: `http://localhost:8080/swagger-ui/index.html`

### 주석 예시
```java
@Operation(summary = "재고 추가", description = "아이템의 재고를 추가합니다.")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "재고 추가 성공"),
    @ApiResponse(responseCode = "404", description = "아이템을 찾을 수 없음")
})
```

---

## ✅ JPA (Java Persistence API)

JPA는 Java에서 ORM(Object-Relational Mapping)을 표준화한 인터페이스로,  
객체지향 코드만으로 DB 데이터를 다룰 수 있도록 해준다.  
Spring Data JPA는 이를 기반으로 CRUD 기능을 간단한 메서드 정의만으로 사용할 수 있도록 지원한다.

### 주요 구성
- `@Entity`: 클래스와 테이블 매핑
- `@Id`, `@GeneratedValue`: 기본키 설정
- `@Repository`: DB 접근 인터페이스 명시
- `save()`, `findById()` 등 기본 메서드 제공 (JpaRepository 상속 시)

### 적용 예시
- 1주차 프로젝트의 `Item` 클래스에 `@Entity` 적용
- `ItemRepository`는 `JpaRepository<Item, Long>`을 상속받아 기본 CRUD 구현

---

## ✅ ORM (Object-Relational Mapping)

ORM은 객체지향 언어의 객체와 관계형 데이터베이스의 테이블 간 매핑을 자동화하는 기술이다.  
SQL 쿼리를 직접 작성하지 않고, 클래스 기반으로 데이터 조작이 가능해진다.

### 장점
- 코드 수준에서 DB 접근 가능 → 생산성 향상
- 반복적인 SQL 작성 감소, 추상화된 구조 설계 가능

### 단점
- 복잡한 쿼리 성능 저하 가능
- 내부 동작 원리에 대한 이해 없이는 디버깅이 어려울 수 있음

---

## ✅ Persistence Context (영속성 컨텍스트)

Persistence Context는 JPA 내부에서 엔티티의 상태를 관리하는 1차 캐시 구조로,  
트랜잭션(하나의 작업 단위를 이루는 일련의 연산 묶음) 단위에서 엔티티 객체를 추적하고, 변경 사항을 DB에 반영하는 시점을 제어한다.

### 핵심 동작
- `persist()` 호출 시 DB에 바로 반영되지 않고 영속성 컨텍스트에 저장됨
- 트랜잭션 커밋 시점에서 `flush()`가 발생하며 DB에 최종 반영
- 같은 엔티티는 동일 트랜잭션 내에서 항상 같은 인스턴스로 관리됨 (1차 캐시)

### 관련 문제 예시
- `save()` 호출 후 DB에 데이터가 보이지 않는 경우: 트랜잭션 커밋 전 상태일 수 있음
- 비정상 종료 시 flush되지 않아 DB 반영이 누락되는 사례도 존재

---

## ✅ 핵심 용어 요약

| 용어 | 설명 |
|------|------|
| **JavaDoc** | 메서드/클래스에 주석을 달아 기능과 사용법을 문서화하는 도구 |
| **Swagger** | REST API 명세를 자동 생성하고 시각화하는 도구 (UI로 제공됨) |
| **JPA** | Java에서 ORM을 구현하기 위한 표준 인터페이스 |
| **ORM** | 객체와 테이블 간의 매핑을 자동화하여 SQL 없이 DB를 다루는 방식 |
| **Entity** | DB 테이블과 매핑되는 클래스 (예: `Item`) |
| **Repository** | DB 접근을 담당하는 계층 (예: `ItemRepository`) |
| **Persistence Context** | JPA에서 엔티티의 생명주기를 관리하는 1차 캐시 |

