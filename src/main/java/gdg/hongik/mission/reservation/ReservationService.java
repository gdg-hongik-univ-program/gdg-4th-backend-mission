package gdg.hongik.mission.reservation;

import gdg.hongik.mission.item.Item;
import gdg.hongik.mission.item.ItemService;
import gdg.hongik.mission.user.User;
import gdg.hongik.mission.user.UserService;
import gdg.hongik.mission.reservation.dto.ReservationRequestDTO;
import org.springframework.stereotype.Service;

/**
 * 예약 로직을 처리하는 서비스 클래스입니다.
 */
@Service
public class ReservationService {

    private final UserService userService;
    private final ItemService itemService;
    private final ReservationRepository reservationRepository;

    public ReservationService(UserService userService, ItemService itemService, ReservationRepository reservationRepository) {
        this.userService = userService;
        this.itemService = itemService;
        this.reservationRepository = reservationRepository;
    }

    /**
     * 예약 요청을 처리합니다.
     *
     * @param request 예약 요청 DTO
     */
    public void makeReservation(ReservationRequestDTO request) {
        User user = userService.findByName(request.getName());
        if (!"CONSUMER".equalsIgnoreCase(user.getPosition())) {
            throw new IllegalArgumentException("CONSUMER만 예약할 수 있습니다.");
        }

        Reservation reservation = new Reservation(user);
        for (ReservationRequestDTO.ReservationItemDTO itemRequest : request.getItems()) {
            Item item = itemService.findByName(itemRequest.getName());
            item.decreaseStock(itemRequest.getCount());
            ReservationItem reservationItem = new ReservationItem(item, itemRequest.getCount());
            reservation.addReservationItem(reservationItem);
        }

        reservationRepository.save(reservation);
    }
}
