# 객체지향적 설계를 위한 Java 문법

## `Interface`
Java 에는 `interface` 라는 method 만 모아둔 class 를 정의할 수 있습니다.  
`interface` 를 하나의 역할로 생각하면, 
안에 존재하는 각각의 method는 하나의 메세지로 생각할 수 있습니다.  

창고의 예시를 들어봅시다.
```java
interface Store {
    addStock();
    deleteStock();
    // Other Method...
}
```
이는 단순히 `Store` class 를 구현하는 것과는 다릅니다.  
`interface` 는 오직 정의만 적혀있기 때문에,  
다양한 구현체를 활용할 수 있다는 장점이 있습니다.  

예를 들어, `main` 에서 다음과 같이 `Store` 를 사용하고 있다고 해봅시다.
```java
public static void main(String[] args) {
    Store s = new Bag();
    s.addStock();
}
```
해당 구멍가게는 놀랍게도 가게주인의 주머니에서 재고를 관리하는 중이었습니다.  
구멍가게가 점점 흥하자 주인은 금고를 들이기로 했습니다.  
이 때, 코드는 단순히
```java
public static void main(String[] args) {
    Store s = new Safe();
    s.addStock();
}
```
로 바꾸면 됩니다.

`Bag` 과 `Safe` 가 `Store` 라는 `iterface` 를 구현하기만 한다면,  
그 둘을 바꾸는 것은 매우 쉽습니다.  
만약 금고로 바뀌면서, `addStock` 내부적으로 `checkPassword` 같은 검증로직이 들어가더라도, 
여전히 위의 코드는 잘 작동합니다.

이렇게 `interface` 는 정의 부분과 구현 부분을 분리하여,  
구현체에 따른 유연한 대처를 할 수 있도록 도와줍니다.  

## `record`, `enum`
`record` 와 `enum` 은 엄밀히는 객체지향과는 관계가 없습니다만,  
개발에 있어 유용한 문법이기 때문에 간단하게 소개하겠습니다.  

### `record`
불변의 데이터를 여럿 유용한 method 와 함께 자동으로 만들어줍니다.  
주로, DTO 라는 데이터 덩어리를 만들 때 유용하게 사용됩니다.  

예를 들어, Repository 에서 Service 계층으로의 데이터 이동에 있어서,  
최종적으로 가공 완료된 데이터를 그대로 Service 에 전달할 때 사용됩니다.  

Controller 에서 method의 return 값으로,  
`ResponseEntity` 라는 Spring 의 class를 자주 활용하는데요.  
이 때, Generics 부분에 `record` 문법으로 만든 데이터를 전달해주면,  
편리한 방식으로 Json Response 를 만들어주곤 합니다.

### `enum`
흔히 상수를 다루는 방법으로 알고 계실 수도 있지만,  
Java 의 `enum` 그 기능이 강력해서 그 이상의 역할을 할 수 있습니다.  
여러 개의 상수를 하나의 `enum` 으로 관리할 수 있으며,  
해당 상수에 대한 처리를 하는 method 도 `enum` 에 담을 수 있습니다.  

대표적인 예시로는 status code 에 따른 응답 메세지를 `enum` 으로 다루곤 했습니다.  

그 외로 지난 학기 미션트랙에서 제가 작성한 다른 글을 같이 공유합니다.  
[지난 시즌 미션코스 글 보러가기](https://github.com/gdg-hongik-univ/2024-2-mission-course-java/tree/main/articles)
