# 3주차 WIL

## JavaDoc
- 프로젝트를 진행할 때, 협업을 하는 경우가 많이 있을 것이다. 이 때 다른 사람에게 나의 코드를 이해시키기 위해서는 코드에 주석을 잘 달아야 한다. JavaDoc 을 통해 메서드별로 주석을 달 수 있다.
- JavaDoc 주석을 쓸 때 보통 아래 양식을 많이 따르는 것 같다.
```java
    /**
     * 새로운 아이템을 등록합니다.
     *
     * @param name 등록할 아이템의 이름
     * @param price 등록할 아이템의 가격
     * @param stock 등록할 아이템의 개수
     * @return 등록한 아아템 객체를 반환합니다.
     */
    public Item save(String name,int price, int stock) {
        Item item = new Item(idCounter++, name, price, stock);
        items.put(item.getName(), item);
        return item;
    }
```
- 첫 줄에 메서드에 대한 설명을 쓰고 한 줄 띄어쓴 뒤에, 메서드의 매개변수 설명을 쓰고, 반환 값에 대한 설명을 쓴다.

## ORM (Object-Relation Mapping)
- ORM : Object (Java 의 객체) 와 Relation (관계형 DB 의 테이블) 을 Mapping (연결).
- Java 에서는 클래스와 객체로 데이터를 다룬다. 하지만 데이터베이스에서는 테이블과 행으로 데이터를 다룬다. ORM 은 자바 객체 <-> 데이터베이스의 테이블 간의 변환을 자동으로 처리해주는 기술이다.
- ORM 을 사용할 때의 장단점

| 장점 | 단점 |
|------|------|
| SQL을 거의 안 써도 됨 → 메서드 호출을 통해 DB에 접근 가능 | 복잡한 쿼리 작성은 어려울 수 있음 |
| DB 데이터를 객체처럼 처리 가능 | 쿼리 최적화 어려움 |
| DB 교체가 쉬움 (DB 독립성 높음) | 내부 동작 원리를 모르면 문제가 생길 수 있음 |
- 결국 장점은 SQL 을 거의 다루지 않아도 된다는 것이고, 단점은 SQL 을 직접 다루지 않아서 문제가 발생할 수 있다는 것이다.
- 그럼 ORM 이 있으니까 SQL 은 신경 안써도 되나?
- 간단한 쿼리는 ORM 으로 해결 가능하지만 복잡한 쿼리를 처리해야 하는 상황에서는 SQL 을 직접 작성해야 할 필요도 있기 때문에 SQL 에 대해서도 어느정도 지식이 있어야 한다.
- JPA : ORM 기술의 표준

## 영속성 컨텍스트
- 영속성 컨텍스트란, 데이터베이스와 내 코드 사이에서 1차 캐시 같은 역할을 하는 임시 저장소이다.
- 역할
  - 객체와 DB 연결
  - 같은 객체는 한 번만 저장 (1차 캐시)
  - 변경 감지 (Dirty Checking) 기능 제공
  - Transaction 단위로 분리
- 영속성 컨텍스트의 상태 (생명 주기)
  
| 상태              | 설명 | 예시 |
|-----------------|------|------|
| 비영속 (new)       | 객체는 생성되었지만 DB에 연결되지 않음 | User user = new User(); |
| 영속 (managed)    | 엔티티가 영속성 컨텍스트에 저장됨 (=엔티티를 영속성 컨텍스트가 관리) | em.persist(user); |
| 준영속 (detached)  | 엔티티가 영속성 컨텍스트에 저장되었다가 분리됨 (=엔티티를 영속성 컨텍스트가 관리하지 않음) | em.detach(user); |
| 삭제 (removed)    | 영속성 컨텍스트에 있는 엔티티를 삭제 (Transaction이 끝나면 DB에서도 삭제됨) | em.remove(user); |

- 코드 예시
```java
@Entity
public class User {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private int age;

    // 기본 생성자, getter/setter
}
```
- User 객체가 이렇게 정의되어 있고, EntityManager (em) 을 사용중이라고 가정하자.
```java
@Transactional
public void testPersistenceContext() {
    // 1. 엔티티 저장 (persist)
    User user1 = new User();
    user1.setName("John");
    user1.setAge(25);

    em.persist(user1);  // 영속성 컨텍스트에 저장됨 (DB에는 아직 저장 안 됨)

    // 2. 1차 캐시에서 객체 재사용
    User findUser = em.find(User.class, user1.getId());
    System.out.println(user1 == findUser);  // true (같은 객체)

    // 3. 값 변경 (Dirty Checking)
    findUser.setAge(26);  // 변경 감지

    // 4. 트랜잭션 끝나면 flush() → commit()
} // 여기서 flush + commit → DB에 INSERT/UPDATE 실행됨
```
- 위 코드에서 Entity 의 생명 주기를 살펴볼 수 있다. 먼저 객체가 생성된 후, persist 명령어를 통해 영속성 컨텍스트에 엔티티로 저장된다. 이 때 아직 DB 에는 저장되지 않는다. 이후 엔티티를 재사용 하게 되는데 이 때 DB 가 아닌 영속성 컨텍스트에 접근하여 작업을 수행한다. 이 과정에서 영속성 컨텍스트가 1차 캐시의 역할을 한다고 할 수 있다. 엔티티의 값을 변경하면 영속성 컨텍스트가 이를 감지하여 update SQL을 자동 생성한다. 이 과정이 dirty checking 이다. 트랜젝션이 끝나면 영속성 컨텍스트에 저장되어 있던 내용들이 DB에 저장된다.


- 실제로 날아가는 SQL
```sql
insert into user (name, age, id) values ('John', 25, 1);
update user set age = 26 where id = 1;
```
- 위 SQL 코드는 트랜잭션이 끝난 후 영속성 컨텍스트에 있던 내용을 DB 에 저장하기 위한 코드이다.
- 영속성 컨텍스트는 왜 필요할까? 영속성 컨텍스트를 사용하지 않는다면?
  - 저장, 조회, 변경마다 쿼리 직접 작성해야 함
  - 객체 변경해도 자동으로 DB에 반영 안 됨 (UPDATE 직접 작성해야 함)
  - 캐시 없음 → 같은 데이터 여러 번 조회하면 매번 DB에 쿼리 날아감

## 나의 코드 점검
- 오늘 정리한 내용을 바탕으로 내가 작성한 CRUD 프로젝트 코드를 되짚어보자.
- 일단 현재 나의 코드에서는 DB를 사용하지 않고 있다. 간단한 쿼리만 처리하면 된다고 생각하여 DB 에 데이터를 저장할 필요까지 없다고 생각해서 이렇게 구현했는데 사용자와 아이템의 정보를 저장하고 변경을 처리하기 위해서는 DB 를 사용하고 관리하는 것이 필수적일 것 같다. 프로젝트에서 필요한 객체들(User, Item, Reservation, Purchase 등)을 모두 구현하고, 영속성 컨텍스트와 DB 를 활용하여 정보를 저장하는 기능을 추가하는 방향으로 코드를 리팩토링 해야 할 것 같다.
