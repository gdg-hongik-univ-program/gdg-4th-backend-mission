# 3주차 WIL

## PostgreSQL 데이터베이스 적용
- 기존에 구현한 CRUD 프로젝트는 DB를 사용하지 않았고, 테스트 용도로 코드가 잘 작동하는지만 살펴볼 수 있게 로컬 메모리를 사용했었다. 실제 서비스를 제공하기 위해서는 DB를 사용하여 데이터를 관리해야 했는데, 과제 명세에 in-memory 데이터베이스를 사용하지 말라는 말이 있어서 PostgreSQL을 사용해보기로 했다. PostgreSQL에 대해 간단하게 알아보자
- PostgreSQL은 객체-관계형 데이터베이스 관리 시스템(ORDBMS)이며 SQL 표준을 준수하면서도, 고급 기능과 확장성을 제공한다. MySQL에 비해 복잡한 쿼리를 처리할 수 있으며, 확장성도 뛰어나다.
- 다음은 PostgreSQL 데이터베이스를 적용하기 전과 후의 차이점이다. PostgreSQL (JPA repository) 방식에서는 여러가지 메서드를 JPA가 자동 제공하기 때문에 직접 작성했던 메서드들을 신경쓸 필요가 없어진 것이다!
```java
// PostgreSQL 적용 전

package gdg.hongik.mission.item;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class ItemRepository {
    private final Map<String, Item> items = new HashMap<>();
    private int idCounter = 1;

    public Optional<Item> findByName(String name) {     // 아이템 이름으로 검색
        return Optional.ofNullable(items.get(name));
    }

    public Item save(String name,int price, int stock) {
        Item item = new Item(idCounter++, name, price, stock);
        items.put(item.getName(), item);
        return item;
    }

    public void deleteByName(String name) {
        items.remove(name);
    }

    public Collection<Item> findAll() {
        return items.values();
    }

    public boolean existsByName(String name) {
        return items.containsKey(name);
    }
}
```

```java
// PostgreSQL 적용 후

package gdg.hongik.mission.item;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findByName(String name);

    boolean existsByName(String name);

    void deleteByName(String name);
}
```

## 코드 세분화와 기능 구현 (Setter 지양, DTO 생성)
- 기능 분리 : 원래 구현한 코드에서는 Item 패키지 내에서 모든 기능을 구현했다. 2주차에서 분석한 것처럼 이런 방식으로 구현하는 것은 확장성이 없고, 여러 문제가 발생할 수 있다. 이제 Item에 집중되어있던 기능들을 User, Order, Reservation에 배분하여 구현했다.
- Setter 지양 : Setter 어노테이션을 사용하진 않았지만, setPrice, setStock와 같이 값을 직접 변경하는 메서드들을 사용했었다. 이는 값의 변경이 무분별하게 일어날 수 있고, 명확하지 않을 수 있기 때문에 이런 메서드들의 사용을 지양하고, addStock과 같은 메서드로 대체했다.
- DTO : 데이터 처리를 Map 기반으로 구현했었다. 이런식으로 구현하면 쿼리 형식이 바뀌거나, 기능이 추가될 때에 유연하게 코드를 변경하기 어렵다. 그래서 따로 DTO를 구현하여 확장성을 높였다.
- 새로운 기능 구현 : 가격 변경, 예약, 예약 취소, 구매 기록 및 통계 기록 -> 조회 등을 새로 구현했다.

## 느낀 점
- 한 번에 여러가지 기능들을 넣고 클래스의 역할과 로직을 나누고 하려다 보니 어려운 점이 많았다. 어느 한 가지 기능을 추가하려면 여러 클래스의 코드를 모두 살펴보면서 바꿔야 했다. 처음에 코드를 짤 때, 확장성을 고려하면서 기능들을 구현하는 것이 중요함을 다시 한 번 느꼈다.
- 이렇게 많은 내용들을 한 번에 업데이트 하고, 코드를 대규모로 바꾸면, 팀 프로젝트에서 굉장히 안좋을 것 같다. 다른 팀원들이 나의 코드의 변경점들을 이해하기 어려울 것이다.
- Setter의 사용이 독이 될 수 있다는 것을 알게 되었다. 쉽게 생각하면 Setter 를 이용하면 private의 보장이 안되기 때문에 발생하는 문제인 것 같다. 최대한 Setter를 지양하고 다른 방법으로 코드를 짜야 할 것이다.
- 코딩을 할 때, 한 가지에만 매몰되지 말자! 이번에 리팩토링하면서 가장 크게 느낀 부분이다. 어떤 기능을 어느 패키지에 구현할지, 새로 만든 기능들로 인해 다른 기존의 패키지나 기능들에 어떤 영향을 끼치게 될 지 등을 항상 생각하고 고민해야 한다. 특히, 프로젝트의 규모가 커질수록 이러한 과정이 어려워질 것이고, 그만큼 코드와 역할 분리를 잘해야 한다. 앞으로는 미리 어떤 방향으로 어떻게 코드를 리팩토링할 지 좀 더 많이 고민해보고 구체적으로 계획을 세우고, 그 뒤에 코드를 리팩토링하는 습관을 가져야겠다.