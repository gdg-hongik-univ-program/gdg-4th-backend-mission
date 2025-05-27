## 3주차 WIL
### Javadoc이란?
- Javadoc은 JDK와 함께 패키지로 제공된 도구로, Java 소스 코드의 코드 문서를 생성하는데 도움을 주는 도구이다.
- Javadoc은 html을 따로 작성하지 않고도 소스 코드에 작성된 코멘트를 따라 문서를 만들 수 있게 됨.

### Javadoc 사용방법
Some basic Git commands are:
```
/**
 * javadoc
 * javadoc1
 * javadoc2
 */
```
위와 같이 주석을 작성해주면 된다.

### Javadoc 키워드
- @version : 구현체(클래스, 메소드, 변수 등)의 버전
- @author : 소스의 저자를 의미한다.
- @link : 내/외부 클래스나 메소드등을 연결할 떄 사용한다.
- @deprecated : 오래되서 더이상 사용을 권장하지 않는 클래스, 메소드, 인터페이스에 사용한다.
- @see : 다른 클래스나 메소드를 참고할 경우 사용한다.
- @see className : 클래스 이름 연결.
- @see #method : 클래스 내 메소드나 변수 연결.
- @since : 클래스나 메소드나 언제부터 있었는지 여부를 지정. API로 제공하는 경우 API 사용자가 어느 버전의 라이브러리에서 이 기능을 제공하는지 알아야 의존성을 제대로 설정할 수 있으므로 중요함.
- @param : 파라미터 이름과 용도 기술.

### ORM(Object-Relation Mapping)
- 데이터베이스의 한 데이터를 하나의 Java 객체에 대응시켜 사용하는 것.
- 기술적으로는 어플리케이션의 객체를 RDB 테이블에 자동으로 영속화 해주는 것.

#### ORM 장점?
- SQL 문이 아닌 Method를 통해 DB를 조작할 수 있어, 개발자는 객체 모델을 이용하여 비즈니스 로직을 구성하는데만 집중할 수 있음.
- Query와 같이 필요한 선언문, 할당 등의 부수적인 코드가 줄어들어, 각종 객체에 대한 코드를 별도로 작성하여 코드의 가독성을 높임.
- 객체지향적인 코드 작성이 가능하다. 오직 객체지향적 접근만 고려하면 되기때문에 생산성 증가.
- 매핑하는 정보가 Class로 명시 되었기 때문에 ERD를 보는 의존도를 낮출 수 있고 유지보수 및 리팩토링에 유리.

#### ORM 단점?
- 프로젝트의 규모가 크고 복잡하여 설계가 잘못된 경우, 속도 저하 및 일관성을 무너뜨리는 문제점이 생길 수 있음.
- SQL문과 같은 언어에서 가능한 최적화를 하기에는 어렵기 때문에, 성능을 우선으로 하는 경우에는 쓰기 어려울 수 있다.

### JPA
- JPA는 자바 진영에서 ORM 기술 표준으로 사용되는 인터페이스의 모음이다. 
- 자바 어플리케이션에서 관계형 데이터베이스를 사용하는 방식을 정의한 인터페이스
- Hibernate, OpenJPA 등이 JPA를 구현함.
![](https://velog.velcdn.com/images/skays12/post/6b74231c-96e9-471b-a2ab-50de33cbce68/image.png)

#### JPA를 사용하는 이유
- JPA는 반복적인 CRUD SQL을 처리한다. JPA는 매핑된 관계를 이용해서 SQL을 생성하고 실행하는데, 개발자는 어떤 SQL이 실행될지 생각만하면 되고, 예측도 쉽게 가능하다. 
- JPA의 가장 큰 장점은 SQL을 사용하는게 아니기 때문에, 객체 중심으로 개발할 수 있다는 것이다. 
- 하나의 예로, 데이터베이스에서는 객체의 상속 관계를 지원하지 않지만, JPA를 통해 객체의 상속 관계를 구현할 수 있다. 

### 영속성 컨텍스트(Persistence Context)
** Entity 를 영구 저장하는 환경 **이라는 뜻
- 애플리케이션과 DB 사이 객체를 보관하는 가상의 DB라고 생각
- EntityManager를 통해 Entity를 영속성 컨텍스트에 보관, 관리
![](https://velog.velcdn.com/images/skays12/post/8babb439-199c-475d-89aa-8241bf776877/image.png)

#### 영속성 컨텍스트의 2가지 영역
- 1차 캐시 저장소 : 영속성 컨텍스트가 관리하는 엔티티 정보를 보관함.
※ 하지만 '영속 상태'는 아직 DB에 저장된 상태는 아님! (단순히 영속성 컨텍스트에서 관리하는 상태일 뿐)
- 쿼리문 저장소 : 필요한 쿼리문(SQL)을 보관함.

#### Entity Manager
**EntityManager를 통해 영속성 컨텍스트에 접근하고 관리**
- EntityManager를 생성하면 그 안에 영속성 컨텍스트가 있음.
- EntityManagerFactory 통해 요청이 올 때마다 EntityManager를 생성함
- EntityManager는 내부적으로 Connection 사용하여 DB에 접근함


#### Entity의 생명주기
1. 비영속(new, transient) 상태
- 엔티티가 영속성 컨텍스트와 전혀 관련이 없는 상태
![](https://velog.velcdn.com/images/skays12/post/c4258a73-c4c6-4556-a9fe-1be70653b263/image.png)

2. 영속(managed) 상태
- 엔티티가 영속성 컨텍스트에서 관리되고 있는 상태(아직 DB에 저장된 상태가 아님!)
![](https://velog.velcdn.com/images/skays12/post/4ef31335-f220-4611-ad4b-f7ff895f4673/image.png)
- 위의 이미지에서 엔티티를 저장하는 INSERT 쿼리문이 생성되었지만, 아직 DB에게 전달되지 않고 쿼리문 저장소에 보관되었음. (flush()가 실행되기 전에는 실제 DB에 접근X)
![](https://velog.velcdn.com/images/skays12/post/2e4732e0-90c8-46f5-9f4e-e2d576c78bf3/image.png)
- 여러개의 엔티티를 persist()하더라도 해당하는 INSERT 쿼리문은 계속 보관하게 됨.
![](https://velog.velcdn.com/images/skays12/post/a1b0f737-d643-4b24-948e-f19396cc1fa8/image.png)
- 모아둔 쿼리문은 flush() 명령을 실행해서 DB에 반영함. flush()를 하더라도 1차 캐시 저장소에 관리중인 엔티티들이 사라지는 것은 아님. flush()는 영속성 컨텍스트와 DB를 동기화할 뿐임.

![](https://velog.velcdn.com/images/skays12/post/ffc41fc1-b3b8-4321-a6ab-48db9a506e1e/image.png)
- 생성한 엔티티를 입력할 때 이외에도 엔티티 매니저가 DB에서 조회해온 데이터도 '영속 상태'인 엔티티가 된다.
- 같은 엔티티를 한번 더 조회하게 되면, JPA는 1차 캐시 저장소에 있는 엔티티를 반환하고 실제로 DB에 접근은 하지 않는다.

3. 준영속(detached) 상태
- 특정 엔티티를 준영속 상태로 만들기 위해서는 엔티티 매니저의 detach()를 사용함.
![](https://velog.velcdn.com/images/skays12/post/9994869d-9563-44bc-926f-25cc1b9af9b7/image.png)
- 영속성 컨텍스트 전체를 초기화 시키는 clear()를 사용할 수 있음. 이 때 쿼리문 저장소의 보관해둔 퀴리들도 모두 초기화됨.
![](https://velog.velcdn.com/images/skays12/post/0ee1667e-396b-47bd-ac30-30997fcfe00a/image.png)
- 영속성 컨텍스트를 닫아버리는 close()를 사용하면, 영속성 컨텍스트 자체가 사라지며 관리되던 엔티티들은 모두 준영속 상태가 됨.
![](https://velog.velcdn.com/images/skays12/post/8fdfecf3-288b-45d7-af48-0476cf1b81fa/image.png)

4. 삭제(removed) 상태
- 삭제 상태는 엔티티를 영속성 컨텍스트에서 관리하지 않게 되고, 해당 엔티티를 DB에서 삭제하는 DELETE 쿼리문을 보관하게 된다. persist와 마찬가지로 flush()가 호출되기 전까지는 실제 DB에 접근되지 않는다.
![](https://velog.velcdn.com/images/skays12/post/a28ff753-d3d8-451f-84ab-72d7c7f68dd0/image.png)

## WEEK3 회고
이번주는 Javadoc을 활용한 주석과 ORM,그중에서도 JPA와 영속성 컨텍스트에 대해 공부해보았다. 
일단 일반 주석만 써보고 javadoc이라는 것이 있는지도 몰랐었는데, javadoc을 이용하면 좀 더 효율적으로 코드에 대한 정보를 작성할 수 있다는 사실을 알게 되었다. 
그리고 데이터베이스를 사용하기 위해 SQL문을 이용하지 않고도 JPA를 통해 편하게 데이터베이스를 사용할 수 있다는 사실을 알게 되었다. 
일단 전체적인 개념에 대해선 이해했다고 생각하지만, 실제로 프로젝트에 적용해보고 ORM을 어떻게 사용하는지 직접 해봐야 제대로 이해가 될거 같다. 
앞으로 있을 프로젝트에 꼭 JPA를 사용해서 데이터베이스를 연결해보는 작업을 해보고 싶다. 



> 그림자료 출처 : https://siyoon210.tistory.com/138





