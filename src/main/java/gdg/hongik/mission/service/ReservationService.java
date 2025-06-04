package gdg.hongik.mission.service;

import gdg.hongik.mission.dto.*;
import gdg.hongik.mission.model.*;
import gdg.hongik.mission.repository.ItemRepository;
import gdg.hongik.mission.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ItemRepository itemRepository;

    public ReservationService(ReservationRepository reservationRepository, ItemRepository itemRepository) {
        this.reservationRepository = reservationRepository;
        this.itemRepository = itemRepository;
    }

    /**
     * 예약 생성
     * - 1인당 1건 제한
     * - 재고 감소
     */
    public void makeReservation(ReservationRequestDto request) {
        if (reservationRepository.existsByName(request.name())) {
            throw new IllegalArgumentException("이미 예약이 존재합니다.");
        }

        List<ReservedItem> reservedItems = new ArrayList<>();
        for (ItemCountDto dto : request.items()) {
            Item item = itemRepository.findByName(dto.name())
                    .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다: " + dto.name()));

            if (item.getStock() < dto.count()) {
                throw new IllegalArgumentException("재고가 부족합니다: " + dto.name());
            }

            item.setStock(item.getStock() - dto.count());
            reservedItems.add(new ReservedItem(dto.name(), dto.count()));
        }

        reservationRepository.save(new Reservation(request.name(), reservedItems));
    }

    /**
     * 예약 취소
     * - 본인 또는 관리자만 가능
     * - 재고 복구
     */
    public void cancelReservation(String target) {
        Reservation reservation = reservationRepository.findByName(target)
                .orElseThrow(() -> new IllegalArgumentException("예약 정보가 없습니다."));

        for (ReservedItem reservedItem : reservation.getItems()) {
            Item item = itemRepository.findByName(reservedItem.getName())
                    .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다: " + reservedItem.getName()));
            item.setStock(item.getStock() + reservedItem.getCount());
        }

        reservationRepository.deleteByName(target);
    }

    /**
     * 관리자 전용 - 전체 예약 목록 반환
     */
    public List<ReservationRequestDto> getAllReservations(String requester, String position) {
        if (!"ADMIN".equalsIgnoreCase(position)) {
            throw new IllegalArgumentException("관리자 권한이 필요합니다.");
        }

        return reservationRepository.findAll().stream()
                .map(res -> {
                    List<ItemCountDto> items = res.getItems().stream()
                            .map(i -> new ItemCountDto(i.getName(), i.getCount()))
                            .toList();
                    return new ReservationRequestDto(res.getName(), items);
                })
                .toList();
    }
}
