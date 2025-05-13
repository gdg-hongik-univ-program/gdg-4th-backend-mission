# Mission Course Week2
1주차 미션은 어땠나요?  
아마 CRUD 프로젝트가 쉽지만은 않았을 거에요.  
앞으로 공부해나가면서, 조금씩 보완해나가봅시다!  
  
2주차에서는
* 팀원의 프로젝트를 리뷰
* 프로젝트를 위해 필요한 지식을 공부  

할 예정이에요.

## 프로젝트 Peer Review  

### 개요
실제 Peer Review 는 하나의 프로젝트에 대해 진행되지만,  
이번에는 다른 분들의 미션 코스를 확인해봅시다!  
 
PR은 타인과의 의사교환이 가장 활발히 일어나는 곳이라고 할 수 있어요.  
원활한 PR을 위해서 많은 사람들이 받아들이고 지키는 관습이 존재해요.  
대표적으로,

* PR은 작은 단위로 올리기
* 정해진 템플릿에 맞게 작성하기
* 분명하고 쉽게 리뷰 작성하기

이 있어요.  
  
개인적으로는 "작은 단위로 올리기" 를 가장 중요시하는 편이에요.  
너무 많은 변화는 리뷰어에게 큰 부담이 되기 때문이에요.  
백문이 불여일견이라고, 직접 해보도록 합시다!  

### 미션 내용
팀 내에서 2명씩 짝을 만듭니다.  
2명은 서로에게 1주차 CRUD 프로젝트에 대한 Review 를 남깁니다.  
이를 위해 여러분의 레포지토리를 포크했던 원본 레포지토리에 Merge 하는 PR을 만들어주세요!  
해당 PR에 대해 Review 를 남겨주시면 됩니다.  
아래는 제가 리뷰할 때 생각하는 우선순위입니다.  
꼭 따르실 필요는 없고 참고용으로 봐주시면 됩니다!

기본적으로 모든 기능은 잘 동작한다고 가정하고 리뷰한다.
* 가독성이 좋은가?
  * 변수명, 함수명 등의 네이밍이 직관적인가?
  * 주석이 잘 작성되어있어 메소드의 기능이 명확한가?


* 확장 가능성 및 유지보수하기 용이한가?
  * 해당 메서드가 적절한가?
  * 해당 메서드가 해당 클래스에 있는 것이 정당한가?


* 버그 가능성은 없는가?
  * 입력값이 0개거나 1개거나 너무 많거나 할 때, 문제가 발생하지는 않는가?


* 로직 개선이 가능한가?

## 프로젝트를 위한 배경지식 공부

### 개요
프로잭트를 위해 필요한 지식을 한번 공부해봅시다!  
주로 이론적인 내용을 정리해볼 예정이에요.  


해당 이론을 한번 익히고 나면,
* 이론에 대한 구현으로 자료를 찾아보기 쉬워요.
  * Ex) Spring 에서 Status Code 를 다루는 방법
* 에러 메세지에 대해 이해하기 쉬워요.
  * Ex) CORS Error 가 발생하면, Controller 쪽의 문제일 가능성이 높아요.



### 미션 내용
프로젝트 진행을 위해서, 필요한 기본적인 지식들을 정리해보았어요.

* 객체지향
  * [결합도와 응집도](https://inpa.tistory.com/entry/OOP-%F0%9F%92%A0-%EA%B0%9D%EC%B2%B4%EC%9D%98-%EA%B2%B0%ED%95%A9%EB%8F%84-%EC%9D%91%EC%A7%91%EB%8F%84-%EC%9D%98%EB%AF%B8%EC%99%80-%EB%8B%A8%EA%B3%84-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0-%EC%89%BD%EA%B2%8C-%EC%A0%95%EB%A6%AC)
  * [SOLID](https://inpa.tistory.com/entry/OOP-%F0%9F%92%A0-%EA%B0%9D%EC%B2%B4-%EC%A7%80%ED%96%A5-%EC%84%A4%EA%B3%84%EC%9D%98-5%EA%B0%80%EC%A7%80-%EC%9B%90%EC%B9%99-SOLID)


* RESTful API
    * [네트워크 기본](https://inpa.tistory.com/entry/%EC%9B%B9-%ED%86%B5%EC%8B%A0-%EA%B8%B0%EB%B3%B8-%EC%9D%B4%EB%A1%A0-%ED%86%B5%ED%95%A9-%EC%9A%94%EC%95%BD-%EC%A0%95%EB%A6%AC)
    * [HTTP 기본](https://inpa.tistory.com/entry/WEB-%F0%9F%8C%90-URL-%EA%B5%AC%EC%84%B1-%EC%9A%94%EC%86%8C-%EC%9A%94%EC%B2%AD-%ED%9D%90%EB%A6%84-%EC%A0%95%EB%A6%AC)
    * [URL 구성요소](https://inpa.tistory.com/entry/WEB-%F0%9F%8C%90-URL-%EA%B5%AC%EC%84%B1-%EC%9A%94%EC%86%8C-%EC%9A%94%EC%B2%AD-%ED%9D%90%EB%A6%84-%EC%A0%95%EB%A6%AC)
    * [Status code](https://inpa.tistory.com/entry/HTTP-%F0%9F%8C%90-%EC%83%81%ED%83%9C-%EC%BD%94%EB%93%9C-1XX-5XX-%EC%B4%9D%EC%A0%95%EB%A6%AC%ED%8C%90-%F0%9F%93%96)
    * [Stateful과 Stateless](https://inpa.tistory.com/entry/WEB-%F0%9F%93%9A-Stateful-Stateless-%EC%A0%95%EB%A6%AC)
    * [REST API 기본](https://inpa.tistory.com/entry/WEB-%F0%9F%8C%90-REST-API-%EC%A0%95%EB%A6%AC)


* 클라우드 및 배포
  * [CORS](https://inpa.tistory.com/entry/WEB-%F0%9F%93%9A-CORS-%F0%9F%92%AF-%EC%A0%95%EB%A6%AC-%ED%95%B4%EA%B2%B0-%EB%B0%A9%EB%B2%95-%F0%9F%91%8F)
  * [서버리스](https://inpa.tistory.com/entry/WEB-%F0%9F%8C%90-%EC%84%9C%EB%B2%84%EB%A6%AC%EC%8A%A4ServerLess-%EA%B0%9C%EB%85%90-%F0%9F%92%AF-%EC%B4%9D%EC%A0%95%EB%A6%AC-BaaS-FaaS)

각 블로그의 글과  
제가 개발을 하면서 느낀 점과 공부한 점을 모아둔 [객체지향에 대한 글](articles/object.md)을 읽고,  
[WIL](../../week2/WIL.md) 에 정리해주세요!
