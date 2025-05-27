# Mission Course Week3
2번째 미션으로는  
`JPA 에 대한 이론적인 지식`  
을 배우게 돼요.  

여기서 배울 이론적인 지식은 추후 프로젝트에서 발생하는 버그를 해결하거나,  
성능을 고려할 때 중요한 역할을 해요.

구체적인 지식은 필요할 때, 더 공부해도 되지만  
어느 정도의 개념과 용어를 익혀 다음에 어떤 문제에 대한 원인으로서 가능성을 고려하기 위해  
준비해 봤어요.

간단한 예시로는,  
레포지토리에서 코드상으로는 데이터를 저장했는데,  
실제 DB 에는 저장이 되어 있지 않다면 `영속성 컨텍스트` 를 의심해 볼 수 있어요.
 
그럼 아래 내용들을 공부하고 [WIL.md](../../week3/WIL.md) 에 정리해 봅시다!

## ORM(Object-Relation Mapping)
혹시 1주차에 강조했던 `@Repository` 라는 Annotation 을 기억하시나요?  
해당 `Annotation` 을 통해 저희는 DB에 대한 작업을 Java 코드로 수행할 수 있었어요.  
예를 들어 `findItemById` 을 통해 `id` 에 해당하는 `Item` 을 가져올 수 있어요.

이런 식으로 **데이터베이스의 한 데이터를 하나의 Java 객체에 대응시켜 사용하는 것**을  
**ORM(Object-Relational Mapping)** 이라고 해요.  
여러분이 작성하는 코드 중 `@Entity` 는  
"해당 객체를 ORM 을 통해 데이터베이스와 대응시켜줘!"  
라고 요청하는 것과 비슷합니다.

이러한 ORM 은 Java 같은 객체지향 언어 하나로 데이터베이스를 다룰 수 있다는 강력한 장점이 있지만,  
SQL 문 같이 직접적으로 데이터베이스를 다루는 언어에서 가능한 최적화를 똑같이 적용하기엔 어려워요.  

그래서 보통 빠르게 구현하고자 하는 부분은 ORM,  
성능이 중요한 부분은 SQL 문을 직접 이용할 수 있는 다른 방법을 사용하곤 합니다.  

ORM 은 특정 개념으로, 어떤 프레임워크나 라이브러리가 아니라서  
공식 문서라고 할 것이 따로 존재하진 않습니다.  

여러분이 직접 검색해보시면서 감을 익히시면 좋을 것 같아요.  
시작점으로는 [영문 위키피디아](https://en.wikipedia.org/wiki/Object%E2%80%93relational_mapping) 를 추천드려요.  
개념을 이해하고, JPA 를 공부하면서 "이런 식으로 실제 작동하는구나" 를 알아보시는 걸 추천드립니다.

### 심화
근본적으로 RDMS(관계형)와 OOP(객체지향) 의 차이는 그 패러다임의 차이에 있습니다.  
"데이터를 어떻게 효율적으로 다룰 것인가!" 에 대한 답변에 대해,  
객체지향과 관계형은 각자의 답변을 내놓았고 그 답변의 차이에 의해 발생하게 된 것입니다.  
그 차이를 객체지향의 관점에서 해소하고자 하는 것이 `ORM` 이라는 기술적 도전입니다.  
다음의 자료를 참고해 보세요!

* [Object–relational impedance mismatch](https://en.wikipedia.org/wiki/Object%E2%80%93relational_impedance_mismatch#Alternative_architectures)

혹은 해당 키워드를 직접 검색해 보셔도 좋아요!

## 영속성 컨텍스트(Persistence Context)
저희가 JPA 라는 ORM 을 통해, 데이터베이스에 접근할 때,  
사실 숨겨진 중간 장치가 있습니다. 이를 영속성 컨텍스트라고 부릅니다.  

일종의 Cache 역할을 하는 객체로, Cache 의 이점과 동시에 단점도 가지고 있습니다.  
영속성 컨텍스트를 직접 다루기보단, JPA 의 내부 구현으로서 사용되는 경우가 많기 때문에,  
의도하지 않은 결과가 나올 가능성이 있고,  
이를 해결하기 위해서는 어느 정도의 영속성 컨텍스트에 대한 이해가 필요합니다.

위에서 제시한 예시처럼, 저장을 시도했지만, 실제로는 저장이 안된 이유는  
영속성 컨텍스트에 남아있지만, DB에 넘어가지 않아서라고 한다면,  
강제로 DB로 넘기도록 영속성 컨텍스트를 강제해 볼 수 있습니다. (권장하는 방법은 아닙니다)

아래 자료를 통해 공부해보고, 추가적으로 찾아서 공부해 봅시다!

* [10분 테크톡](https://www.youtube.com/watch?v=c4rDrirE7Bc)
* [Hibernate Persistence Context](https://www.baeldung.com/jpa-hibernate-persistence-context)
* [Persistence Context Deep Dive](https://msolo021015.medium.com/jpa-persistence-context-deep-dive-2f36f9bd6214)

### 심화
데이터를 다루는 방법에 대해서, Java 는 많은 해결책이 있어요.  
그중에서 현재 저희는 ORM, 즉 객체지향적으로 다루려고 시도하고 있어요.  

관련된 용어가 많은데 여유가 되신다면 한번 정리해 보시면 좋을 것 같습니다!  
ORM, JPA, Hibernate, Spring Data JPA, JDBC 등의 용어를 공부하고 정리해 봐요.

## TODO

- [ ] ORM
  - [ ] ORM 의 개념을 이해할 수 있다.
  - [ ] ORM 의 장점, 단점을 이해할 수 있다.
  - [ ] ORM 을 실제 코드에 적용할 수 있고, 버그를 해결할 수 있다.
- [ ] Persistence Context
  - [ ] Persistence Context의 개념을 이해할 수 있다.
  - [ ] 특정 구현체(Hibernate 등) 환경에서 특정 메소드(CRUD) 에서 어떻게 작용하는지 이해할 수 있다.
