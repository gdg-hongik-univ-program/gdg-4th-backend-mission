# 쇼핑몰 웹페이지

아래의 모든 기능은 한번에 1명만 접속해서 사용한다고 가정한다.  
이 프로그램의 사용자는 "소비자" 와 "관리자", 2개로 나뉜다. 기본적으로, 관리자는 소비자의 모든 기능을 이용할 수 있다.  
모든 입력에는 이름과 직책이 담겨있다.
```JSON
{
    "name": "지디지",
    "position": "CONSUMER",
    // ...other things...
}
```
이후의 입력 예시에 대해, 위의 내용은 기본적으로 담겨있다고 생각한다.

## 소비자
### 재고 검색
해당하는 이름에 대한 물건의 정보를 보여준다.  
이름당 물건은 1개뿐이라고 가정한다.
```json
// INPUT
{
    "name": "apple"// 검색할 물건에 대한 이름
}
```

```JSON
// OUTPUT
{
    "id": 1, // 물건별 고유 ID
    "name": "apple",
    "price": 1000,
    "stock": 100, // 물건 재고 수   
}
```
---

### 재고 구매
장바구니에 담긴 물건을 구매한다.  
출력값으로 아래를 반환한다.

* 총 구매액
* 각 물건의 정보
    * 이름
    * 구매한 개수
    * 해당 물건에서 소비한 금액

을 출력한다.
```JSON
//INPUT
{
    "items": [
        {
            "name": "apple",
            "count": 5
        },
        {
            "name": "banana",
            "count": 10
        }
    ]
}
```

```JSON
// OUTPUT
{
    "totalPrice": 20000,
    "items": [
        {
            "name": "apple",
            "count": 5,
            "price": 5000
        },
        {
            "name": "banana",
            "count": 10,
            "price": 15000
        }
    ]
}
```

## 관리자
관리자 기능을 소비자가 시도할 경우, 에러를 반환한다.

### 재고 등록
새로운 물품을 쇼핑몰에 등록한다.  
기존에 존재하는 물품을 시도할 경우, 에러를 반환한다.
```JSON
// INPUT
{
    "name": "apple",
    "price": 1000,
    "stock": 100 // 물건 재고 수   
}
```
OUTPUT 은 따로 존재하지 않는다.

---

### 재고 추가
기존의 물품에 대해, 재고를 늘린다.  
출력값으로 늘리고 난 뒤, 최종 재고를 반환한다.
```JSON
// INPUT
{
    "name": "apple",
    "count": 20 // 추가 재고 개수
}
```

```JSON
// OUTPUT
{
    "name": "apple",
    "stock": 120 // 현재 물건 재고 수   
}
```

---

### 물품 삭제
입력으로 주어진 물품에 대해, 쇼핑몰에서 삭제한다.  
입력은 1개이상의 물품일 수 있다.  
출력으로 현재 쇼핑물에 남아있는 물품과 개수를 반환한다.
```JSON
// INPUT
{
    "items": [
        { "name": "apple" },
        { "name": "banana" }
    ]
}
```

```JSON
// OUTPUT
{
    "items": [
        { 
            "name": "mango",
            "stock": 5
        },
        {
            "name": "kiwi",
            "stock": 10
        }
    ]
}
```


# EXTRA MISSION
추후 4주차의 과제를 미리 공개합니다!  
해당 과제에 대한 검사는 4주차에 진행될 예정이며,  
처음 설계에 함께 고려해보시라는 의미에서 공개합니다 :)  
1주차에서 구현하실 필요는 없습니다.

## 예약 시스템
물건을 구매해두지 않고, 미리 예약해둘 수 있다.  
소비자는 1개의 예약만 할 수 있다.

### 예약하기
예약은 쇼핑몰의 재고를 감소시킨다.
```JSON
// INPUT
{
    "items": [
        {
            "name": "apple",
            "count": 5
        },
        {
            "name": "banana",
            "count": 10
        }
    ]
}
```
OUTPUT은 존재하지 않는다.

---

### 예약 취소하기
구매 전이기 때문에, 본인, 혹은 관리자가 취소시킬 수 있다.  
본인이나, 관리자가 아니라면, 취소되지 않는다.  
취소가 되면, 재고가 예약 전으로 돌아간다.
```JSON
// INPUT
{
    "target": "지디지", // 예약자 본인 || 관리자
}
```
OUTPUT은 존재하지 않는다.

---

### 예약 리스트 확인하기(관리자 전용)
관리자는 소비자의 예약 목록을 살펴볼 수 있다.
```JSON
// INPUT
{
    "name": "지디지", // 관리자 Only
    "position": "ADMIN"
}
```

```JSON
// OUTPUT
{
    "data": [
        {
            "name": "지디지",
            "s": [
                {
                    "name": "apple",
                    "count": 5
                },
                {
                    "name": "banana",
                    "count": 10
                }

            ]
        },
        {
            "name": "김와우",
            "s": [
                {
                    "name": "mango",
                    "count": 20
                },
                {
                    "name": "kiwi",
                    "count": 30
                }

            ]
        }
    ]
}
```

## 소비자 구매 기록 및 통계자료(관리자 전용)
### 구매 기록
검색한 소비자에 대한 구매 기록을 보여준다.
```JSON
// INPUT
{
    "target": "김와우", // 검색할 소비자
}
```

```JSON
// OUTPUT
{
    "record": [
        {
            "s": [
                {
                    "name": "apple",
                    "count": 5
                },
                {
                    "name": "banana",
                    "count": 10
                },
                {
                    "name": "mango",
                    "count": 0
                },
                {
                    "name": "kiwi",
                    "count": 0
                }
            ]
        },
        {
            "s": [
                {
                    "name": "apple",
                    "count": 0
                },
                {
                    "name": "banana",
                    "count": 0
                },
                {
                    "name": "mango",
                    "count": 5
                },
                {
                    "name": "kiwi",
                    "count": 10
                }
            ]
        }
    ]
}
```

---

### 구매기록 통계자료
검색한 소비자의 구매기록을 통한 통계를 보여준다.  
오직 "구매"한 물품에 대해 구한다. 예약한 물품은 포함되지 않는다.  
쇼핑몰의 모든 물건에 대해, 아래의 항목을 보여준다.
* 구매한 물건 수
* 1번의 결제당 평균 구매 수

```JSON
// INPUT
{
    "target": "김와우"
}
```

```JSON
// OUTPUT
{
    "s": [
        {
            "name": "apple",
            "count": 30,
            "average": 3.4212
        },
        {
            "name": "banana",
            "count": 20,
            "average": 5.432
        },
        {
            "name": "mango",
            "count": 0,
            "average": 0
        },
        {
            "name": "kiwi",
            "count": 0,
            "average": 0
        }
    ]
}
```