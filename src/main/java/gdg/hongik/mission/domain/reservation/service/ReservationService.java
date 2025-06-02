package gdg.hongik.mission.domain.reservation.service;

import gdg.hongik.mission.domain.item.entity.Item;
import gdg.hongik.mission.domain.item.repository.ItemRepository;
import gdg.hongik.mission.domain.reservation.dto.request.ReservationCreateRequest;
import gdg.hongik.mission.domain.reservation.dto.response.ReservationListResponse;
import gdg.hongik.mission.domain.reservation.entity.Reservation;
import gdg.hongik.mission.domain.reservation.entity.ReservationItem;
import gdg.hongik.mission.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 예약 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ItemRepository itemRepository;

    /**
     * 새로운 예약을 생성합니다
     * 
     * @param request 예약 생성 요청 정보
     * @throws IllegalArgumentException 이미 예약이 존재하거나 상품이 없거나 재고가 부족한 경우
     */
    @Transactional
    public void createReservation(ReservationCreateRequest request) {
        // 이미 예약이 존재하는지 확인
        if (reservationRepository.existsByUserName(request.getUserName())) {
            throw new IllegalArgumentException("이미 예약이 존재합니다. 소비자는 1개의 예약만 할 수 있습니다.");
        }

        // 예약 생성
        Reservation reservation = Reservation.builder()
                .userName(request.getUserName())
                .build();

        // 예약 상품들 처리
        for (ReservationCreateRequest.ReservationItem requestItem : request.getItems()) {
            Item item = itemRepository.findByName(requestItem.getName())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다: " + requestItem.getName()));

            // 재고 확인 및 차감
            if (item.getStock() < requestItem.getCount()) {
                throw new IllegalArgumentException("재고가 부족합니다. 상품: " + item.getName() + 
                        ", 요청 수량: " + requestItem.getCount() + ", 현재 재고: " + item.getStock());
            }

            item.reduceStock(requestItem.getCount());

            // 예약 상품 생성
            ReservationItem reservationItem = ReservationItem.builder()
                    .itemName(requestItem.getName())
                    .count(requestItem.getCount())
                    .build();

            reservation.addReservationItem(reservationItem);
        }

        reservationRepository.save(reservation);
    }

    /**
     * 예약을 취소합니다
     * 
     * @param userName 예약자명
     * @param requester 취소 요청자
     * @throws IllegalArgumentException 예약이 존재하지 않거나 권한이 없는 경우
     */
    @Transactional
    public void cancelReservation(String userName, String requester) {
        Reservation reservation = reservationRepository.findByUserName(userName)
                .orElseThrow(() -> new IllegalArgumentException("예약이 존재하지 않습니다."));

        // 권한 확인 (본인 또는 관리자)
        if (!reservation.getUserName().equals(requester) && !"ADMIN".equals(requester)) {
            throw new IllegalArgumentException("예약 취소 권한이 없습니다.");
        }

        // 재고 복원
        for (ReservationItem reservationItem : reservation.getItems()) {
            Item item = itemRepository.findByName(reservationItem.getItemName())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다: " + reservationItem.getItemName()));
            
            item.addStock(reservationItem.getCount());
        }

        reservationRepository.delete(reservation);
    }

    /**
     * 모든 예약 목록을 조회합니다 (관리자 전용)
     * 
     * @return 예약 목록
     */
    public ReservationListResponse getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        
        List<ReservationListResponse.ReservationData> data = reservations.stream()
                .map(reservation -> {
                    List<ReservationListResponse.ReservationItemData> items = reservation.getItems().stream()
                            .map(item -> new ReservationListResponse.ReservationItemData(
                                    item.getItemName(), 
                                    item.getCount()
                            ))
                            .collect(Collectors.toList());
                    
                    return new ReservationListResponse.ReservationData(
                            reservation.getUserName(), 
                            items
                    );
                })
                .collect(Collectors.toList());
        
        return new ReservationListResponse(data);
    }
} 