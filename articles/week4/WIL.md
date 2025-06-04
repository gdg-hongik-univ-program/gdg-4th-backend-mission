1. 이번주에 배운 것

- 코드 리펙토링

- 추가 기능 구현하기

2. 배운 내용

- 코드 리펙토링

package gdg.hongik.mission.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {

    @Schema(description = "상품 고유 ID", example = "1")
    private Long id;
    @Schema(description = "상품 이름", example = "apple")
    private String name;
    @Schema(description = "상품 가격", example = "1000")
    private int price;
    @Schema(description = "상품 재고 수량", example = "100")
    private int stock;
}




기존 코드에서 DTO record문법을 사용하고 Getter와 Setter사용을 지양하는 코드를 작성하였다. 참고로 setter와 getter 사용을 지양하자는 이유는

1. 객체 캡슐화 위반 : 객체지향의 요소인 캡슐화를 위반할 수 있다. setter를 사용하면 외부에서 객체의 상태를 무분변하게 변경할 있어, 객체의 무결성이 깨질 수 있다.

2. 변경에 취약: 코드의 구조가 변경되면 다른 코드는 이 변경사항을 알지 못하고 사용하는 모든 코드를 수정해야한다.

아래는 변경한 ItemDto.java코드이다.

package gdg.hongik.mission.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
* 상품 정보를 담은 DTO
* record 문법을 사용하여 setter 지양
  */
  @Schema(description = "상품 정보 DTO")
  public record ItemDto(

       @Schema(description = "상품 고유 ID", example = "1")
       Long id,

       @Schema(description = "상품 이름", example = "apple")
       String name,

       @Schema(description = "상품 가격", example = "1000")
       int price,

       @Schema(description = "상품 재고 수량", example = "100")
       int stock

) {}




ItemRequest 역시 아래와 같이 변경하였다.

package gdg.hongik.mission.dto;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestDto {

    @Schema(description = "상품 이름", example = "apple")
    private String name;

    @Schema(description = "요청자 직책(CONSUMER 또는 ADMIN)", example = "ADMIN")
    private String position; // CONSUMER or ADMIN

    @Schema(description = "상품 가격 (상품 등록에 사용)", example = "1000")
    private int price;

    @Schema(description = "초기 재고 수량 (상품 등록에 사용)", example = "100")
    private int stock;

    @Schema(description = "수량 정보 (재고 추가나 구매 요청에 사용)", example = "5")
    private int count; // 구매 or 추가시 사용
}


package gdg.hongik.mission.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
* 상품 등록, 재고 추가, 구매 시 사용하는 요청 DTO
* record 문법으로 불변성과 간결성을 확보
  */
  @Schema(description = "상품 요청 DTO")
  public record ItemRequestDto(

       @Schema(description = "상품 이름", example = "apple")
       String name,

       @Schema(description = "요청자 직책(CONSUMER 또는 ADMIN)", example = "ADMIN")
       String position,

       @Schema(description = "상품 가격 (상품 등록에 사용)", example = "1000")
       int price,

       @Schema(description = "초기 재고 수량 (상품 등록에 사용)", example = "100")
       int stock,

       @Schema(description = "수량 정보 (재고 추가나 구매 요청에 사용)", example = "5")
       int count

) {}


참고로 코드 마지막에 {}이 들어가는 이유는 record 함수의 구성이

public record ItemRequestDto{



}

형태를 가지기 때문이다.



다음은 ItemService.java 리펙토링이다.  이번에는 record 사용, Setter 지양뿐만 아니라 stream도 적용하였다.

package gdg.hongik.mission.service;

import gdg.hongik.mission.dto.ItemDto;
import gdg.hongik.mission.model.Item;
import gdg.hongik.mission.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
* 상품 관련 비즈니스 로직 처리 서비스 클래스
* 상품 등록, 조회, 재고 추가, 삭제, 구매 기능을 포함함
  */

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /**
     * 이름으로 상품 조회
     * 
     * @param name 조회할 상품 이름
     * @return 상품 DTO
     */

    public ItemDto findItem(String name) {
        Item item = itemRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));
        return new ItemDto(item.getId(), item.getName(), item.getPrice(), item.getStock());
    }

    /**
     * 새 상품 등록
     * 
     * @param name 상품 이름
     * @param price 가격
     * @param stock 재고 수
     * @param position 요청자 직책책
     */

    public void registerItem(String name, int price, int stock, String position) {
        if (!"ADMIN".equalsIgnoreCase(position)) {
            throw new IllegalArgumentException("관리자만 등록할 수 있습니다.");
        }
        if (itemRepository.findByName(name).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 상품입니다.");
        }
        itemRepository.save(new Item(null, name, price, stock));
    }
    /**
     * 재고 추가
     * @param name 상품 이름
     * @param count 추가 수량
     * @param position 요청자 직책
     * @return 업데이트된 상품품
     */

    public ItemDto addStock(String name, int count, String position) {
        if (!"ADMIN".equalsIgnoreCase(position)) {
            throw new IllegalArgumentException("관리자만 추가할 수 있습니다.");
        }
        Item item = itemRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
        item.setStock(item.getStock() + count);
        return new ItemDto(item.getId(), item.getName(), item.getPrice(), item.getStock());
    }

    /**
     * 여러 상품 삭제
     * @param names 삭제할 상품 이름 리스트
     * @param position 요청자 직책
     * @return 삭제 후 남은 상품품
     */

    public List<ItemDto> deleteItems(List<String> names, String position) {
        if (!"ADMIN".equalsIgnoreCase(position)) {
            throw new IllegalArgumentException("관리자만 삭제할 수 있습니다.");
        }

        names.forEach(name -> itemRepository.findByName(name).ifPresent(itemRepository::delete));
        return itemRepository.findAll().stream()
                .map(item -> new ItemDto(item.getId(), item.getName(), item.getPrice(), item.getStock()))
                .collect(Collectors.toList());
    }

    /**
     * 상품 구매를 처리합니다.
     * 
     * @param items 구매 요청 목록(상품 이름, 수량 포함)
     * @return 총 가격 및 구매 상세 목록
     */

    public Map<String, Object> purchaseItems(List<Map<String, Object>> items) {
        int totalPrice = 0;
        List<Map<String, Object>> resultItems = new ArrayList<>();

        for (Map<String, Object> i : items) {
            String name = (String) i.get("name");
            int count = (Integer) i.get("count");
            Item item = itemRepository.findByName(name)
                    .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
            if (item.getStock() < count) {
                throw new IllegalArgumentException("재고가 부족합니다: " + name);
            }
            item.setStock(item.getStock() - count);
            int price = count * item.getPrice();
            totalPrice += price;
            Map<String, Object> itemResult = new HashMap<>();
            itemResult.put("name", name);
            itemResult.put("count", count);
            itemResult.put("price", price);
            resultItems.add(itemResult);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("totalPrice", totalPrice);
        response.put("items", resultItems);
        return response;
    }
}


package gdg.hongik.mission.service;

import gdg.hongik.mission.dto.ItemDto;
import gdg.hongik.mission.dto.ItemRequestDto;
import gdg.hongik.mission.model.Item;
import gdg.hongik.mission.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.*;

/**
* 상품 관련 비즈니스 로직 처리 서비스 클래스
* 상품 등록, 조회, 재고 추가, 삭제, 구매 기능을 포함함
  */
  @Service
  public class ItemService {

  private final ItemRepository itemRepository;

  public ItemService(ItemRepository itemRepository) {
  this.itemRepository = itemRepository;
  }

  /**
    * 이름으로 상품 조회
    *
    * @param name 조회할 상품 이름
    * @return 상품 DTO
      */
      public ItemDto findItem(String name) {
      Item item = itemRepository.findByName(name)
      .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));
      return toDto(item);
      }

  /**
    * 새 상품 등록
    *
    * @param request 상품 등록 요청 (이름, 가격, 재고, 직책 포함)
      */
      public void registerItem(ItemRequestDto request) {
      validateAdmin(request.position());

      if (itemRepository.findByName(request.name()).isPresent()) {
      throw new IllegalArgumentException("이미 존재하는 상품입니다.");
      }

      itemRepository.save(new Item(null, request.name(), request.price(), request.stock()));
      }

  /**
    * 재고 추가
    *
    * @param request 재고 추가 요청 (이름, 수량, 직책 포함)
    * @return 업데이트된 상품 DTO
      */
      public ItemDto addStock(ItemRequestDto request) {
      validateAdmin(request.position());

      Item item = itemRepository.findByName(request.name())
      .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

      Item updatedItem = new Item(item.getId(), item.getName(), item.getPrice(), item.getStock() + request.count());
      itemRepository.save(updatedItem);

      return toDto(updatedItem);
      }

  /**
    * 여러 상품 삭제
    *
    * @param names 삭제할 상품 이름 리스트
    * @param position 요청자 직책
    * @return 삭제 후 남은 상품 목록
      */
      public List<ItemDto> deleteItems(List<String> names, String position) {
      validateAdmin(position);

      names.stream()
      .map(itemRepository::findByName)
      .filter(Optional::isPresent)
      .map(Optional::get)
      .forEach(itemRepository::delete);

      return itemRepository.findAll().stream()
      .map(this::toDto)
      .toList();
      }

  /**
    * 상품 구매를 처리합니다.
    *
    * @param requests 구매 요청 목록 (상품 이름, 수량 포함)
    * @return 총 가격 및 구매 상세 목록
      */
      public Map<String, Object> purchaseItems(List<ItemRequestDto> requests) {
      int totalPrice = 0;
      List<Map<String, Object>> resultItems = new ArrayList<>();

      for (ItemRequestDto req : requests) {
      Item item = itemRepository.findByName(req.name())
      .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

           if (item.getStock() < req.count()) {
               throw new IllegalArgumentException("재고가 부족합니다: " + req.name());
           }

           Item updatedItem = new Item(item.getId(), item.getName(), item.getPrice(), item.getStock() - req.count());
           itemRepository.save(updatedItem);

           int price = req.count() * item.getPrice();
           totalPrice += price;

           resultItems.add(Map.of(
                   "name", req.name(),
                   "count", req.count(),
                   "price", price
           ));
      }

      return Map.of(
      "totalPrice", totalPrice,
      "items", resultItems
      );
      }

  /**
    * 관리자 권한을 확인합니다.
    *
    * @param position 요청자의 직책
      */
      private void validateAdmin(String position) {
      if (!"ADMIN".equalsIgnoreCase(position)) {
      throw new IllegalArgumentException("관리자 권한이 필요합니다.");
      }
      }

  /**
    * Item 엔티티를 DTO로 변환합니다.
    *
    * @param item Item 엔티티
    * @return ItemDto
      */
      private ItemDto toDto(Item item) {
      return new ItemDto(item.getId(), item.getName(), item.getPrice(), item.getStock());
      }
      }


ItemController수정

package gdg.hongik.mission.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import gdg.hongik.mission.dto.ItemDto;
import gdg.hongik.mission.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Tag(name = "Item API", description = "상품 등록/조회/삭제/구매 관련 API")
@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @Operation(summary = "상품 등록", description = "관리자 권한으로 새로운 상품을 등록합니다.")
    @PostMapping("/register")
    public void registerItem(@RequestBody Map<String, Object> request) {
        itemService.registerItem(
                (String) request.get("name"),
                (Integer) request.get("price"),
                (Integer) request.get("stock"),
                (String) request.get("position")
        );
    }

    @Operation(summary = "상품 조회", description = "상품 이름으로 정보를 조회합니다.")
    @GetMapping("/find")
    public ItemDto findItem(@RequestParam String name) {
        return itemService.findItem(name);
    }

    @Operation(summary = "재고 추가", description = "기존 상품에 재고를 추가합니다. 관리자 권한 필요.")
    @PostMapping("/add-stock")
    public ItemDto addStock(@RequestBody Map<String, Object> request) {
        return itemService.addStock(
                (String) request.get("name"),
                (Integer) request.get("count"),
                (String) request.get("position")
        );
    }

    @Operation(summary = "상품 삭제", description = "여러 개의 상품을 삭제합니다. 관리자 권한 필요.")
    @PostMapping("/delete")
    public List<ItemDto> deleteItems(@RequestBody Map<String, Object> request) {
        List<Map<String, String>> items = (List<Map<String, String>>) request.get("items");
        List<String> names = items.stream().map(map -> map.get("name")).toList();
        String position = (String) request.get("position");
        return itemService.deleteItems(names, position);
    }

    @Operation(summary = "상품 구매", description = "장바구니에 담긴 상품을 구매합니다.")
    @PostMapping("/purchase")
    public Map<String, Object> purchaseItems(@RequestBody Map<String, Object> request) {
        return itemService.purchaseItems((List<Map<String, Object>>) request.get("items"));
    }
}


package gdg.hongik.mission.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import gdg.hongik.mission.dto.ItemDto;
import gdg.hongik.mission.dto.ItemRequestDto;
import gdg.hongik.mission.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Item API", description = "상품 등록/조회/삭제/구매 관련 API")
@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * 상품 등록 (ADMIN만 가능)
     */
    @Operation(summary = "상품 등록", description = "관리자 권한으로 새로운 상품을 등록합니다.")
    @PostMapping("/register")
    public void registerItem(@RequestBody ItemRequestDto request) {
        itemService.registerItem(request);
    }

    /**
     * 상품 조회 (이름 기준)
     */
    @Operation(summary = "상품 조회", description = "상품 이름으로 정보를 조회합니다.")
    @GetMapping("/find")
    public ItemDto findItem(@RequestParam String name) {
        return itemService.findItem(name);
    }

    /**
     * 재고 추가 (ADMIN만 가능)
     */
    @Operation(summary = "재고 추가", description = "기존 상품에 재고를 추가합니다. 관리자 권한 필요.")
    @PostMapping("/add-stock")
    public ItemDto addStock(@RequestBody ItemRequestDto request) {
        return itemService.addStock(request);
    }

    /**
     * 상품 삭제 (ADMIN만 가능)
     */
    @Operation(summary = "상품 삭제", description = "여러 개의 상품을 삭제합니다. 관리자 권한 필요.")
    @PostMapping("/delete")
    public List<ItemDto> deleteItems(@RequestParam String position, @RequestBody List<String> names) {
        return itemService.deleteItems(names, position);
    }

    /**
     * 상품 구매 (CONSUMER)
     */
    @Operation(summary = "상품 구매", description = "장바구니에 담긴 상품을 구매합니다.")
    @PostMapping("/purchase")
    public Object purchaseItems(@RequestBody List<ItemRequestDto> requests) {
        return itemService.purchaseItems(requests);
    }
}


위의 과정을 통해 리펙토링을 마쳤다.



- 추가 기능 구현



추가 기능을 구현하기 위해 디렉토리 구성을 다음과 같이 하였고 각각의 기능을 적었다.



controller

ItemController.java	기존 상품 등록/조회/삭제/구매 처리
ReservationController.java	상품 예약, 취소, 전체 예약 조회 (관리자 전용)
StatisticsController.java	소비자별 구매 기록 및 통계 조회 기능 제공




dto

ItemDto.java	상품 정보를 응답할 때 사용하는 DTO
ItemRequestDto.java	상품 등록/재고추가/구매 요청 DTO
ItemCountDto.java	이름 + 수량을 담는 공통 구조 (예약/구매/통계 공용)
ReservationRequestDto.java	소비자 예약 요청 정보
ReservationCancelDto.java	예약 취소 요청 시 사용하는 DTO
ReservationListRequestDto.java	관리자 전용 예약 목록 요청 DTO
PurchaseRecordDto.java	1회 구매 기록을 표현하는 DTO
PurchaseStatisticsDto.java	상품별 총 구매량 및 평균 구매량 통계 DTO


model

Item.java	쇼핑몰의 실제 상품 엔티티
Reservation.java	소비자 1명의 예약 전체 정보
ReservedItem.java	예약된 개별 아이템 정보
PurchaseHistory.java	소비자 1회의 구매 내역을 나타내는 엔티티
PurchaseItem.java	구매한 개별 상품 정보 (이름, 수량)


repository

ItemRepository.java	상품 조회, 삭제 등 DB 접근
ReservationRepository.java	예약 정보 조회 및 저장
PurchaseHistoryRepository.java	구매 기록 조회 및 통계 계산을 위한 DB 접근


service

ItemService.java	상품 CRUD 및 구매 처리
ReservationService.java	예약 생성, 취소, 조회 로직 담당
StatisticsService.java	구매 기록 조회 및 통계 계산 로직 처리
3. 느낀 점

프로젝트 자체가 규모가 크지 않고 형식에 구애를 받지 않다보니 리펙토링 과정에서 수정해야하는 부분에 대해 수정은 했지만 필요성이 와닿지는 않았다. 여러 글을 읽으며 record 문법 사용이나 setter지양등에 대한 정보를 습득했지만 글만으로는 체감이 되지 않았던 만큼 프로젝트 과정에서 기존 방식과 리펙토링된 방식의 차이점을 느껴보고 싶다.

기능 추가 과정에서는 기존의 변수, 함수 체계를 크게 건들일 필요가 없었기에 단순 기능을 추가하는 함수 설계에만 집중에서 기능을 추가할 수 있었다.



github repository 주소: https://github.com/lionjr56/gdg-4th-backend-mission