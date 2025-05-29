# 캡슐화, 책임을 분명하게 하기
캡슐화는 보통 "데이터나 메소드를 외부에서 접근하지 못하게 막는 것"이라고 표현합니다.  
이것이 왜 중요한지, 어떻게 실현되는지 간단하게 알아볼께요.

현실의 물건을 객체로 옮긴 `Item` 이 있고, 창고인 `Store` 에서 쓰고자 한다고 해봅시다.  
이 `Item` 은 스스로 재고를 관리하고, 구매한 물품 수에 따라 지불해야할 가격을 알려줍니다.  
그렇기 때문에 `Item` 은 재고를 의미하는 `stock` 과 가격을 의미하는 `price` 속성을 가집니다.
```java
    public class Item {
    private int stock;
    private int price;
    
    public void decreaseItem(int quantity) {
        // ....
    } 
    // 구매한 결과에 따른 총액을 반환
    public int totalPrice(int quantity) {
        // ....
    }
}
```

앗, 하지만 `Item` 은 사실 재고 관리와 가격을 반환하는 책임을 가지고 있는게 아니었습니다.  
그저 `Store` 에게 요청받은 구매처리만 해주면 됩니다.  
내부적으로 재고가 줄어들고, 가격을 반환하는 것은 `item` 이 스스로 처리하면 됩니다.  
그렇다면 다음과 같이 다시 작성할 수 있습니다.

```java
    public class Item {
    private int stock;
    private int price;
    
    public int buyItem(int quantity) {
        this.decreaseItem(quantity);
        return this.totalPrice(quantity);
    }
    
    // !! private !!
    private void decreaseItem(int quantity) {
        // ....
    }
    // !! private !!
    // 구매한 결과에 따른 총액을 반환
    private int totalPrice(int quantity) {
        // ....
    }
}
```

`private` 로 함수를 작성함으로서 외부에서 따로 통제할 수 없습니다.  
이를 통해 캡슐화를 지키게 됩니다.  
즉, `buyItem` 함수를 추가함으로서 `Store` 측은 단순히 요청만 하게 됩니다.  
세부적인 구현은 `Store` 는 신경쓰지 않습니다.

이는 `Item` 객체가 내부 로직이 바뀌더라도 `Store` 에 까지 영향을 끼치는 것을 방지해줍니다.  
예컨데, `decreaseItem` 에서 개수에 대한 제한(`quantity` 가 0 이상이어야 함) 을 추가로 체크하더라도,  
`Store` 측의 `buyItem` 호출은 변하지 않습니다.

# 예제 구조 (!!스포 주의!!)
너무 어려우신 분들을 위해 제가 구현한 코드를 보면서 방향성을 잡아보시면 좋을 것 같습니다.

## 단, 보시기 전에 충분히 고민해보시고 확인해주셨으면 좋겠습니다.

명세서를 스윽 훑어보면 다음 정도의 기능으로 정리해볼 수 있습니다.
* 기본적인 물건의 정보 확인하기
* 구매 목록 확인하기 + 총액 계산
* 구매에 따라 물품의 재고를 관리하기

물건은 재고 관리 측면과 구매 측면 2가지로 나뉜다고 생각해볼 수 있습니다.  
이에 따라 각 상황에 따라 객체를 분리하고자 합니다.  
그리고 재고를 관리하는 창고와 구매 목록을 관리하는 객체가 필요합니다.  
이에 따라 4가지 객체로 구분합니다.

* `Item`: 기본적인 정보 확인 & 재고 관리 & 개수에 따른 구매액 전달
* ``