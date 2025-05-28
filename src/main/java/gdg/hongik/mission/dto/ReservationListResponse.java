package gdg.hongik.mission.dto;

import gdg.hongik.mission.reservation.Reservation;

import java.util.List;

public class ReservationListResponse {
    private List<Reservation> data;

    public ReservationListResponse(List<Reservation> data) {
        this.data = data;
    }
    public List<Reservation> getData() { return data; }
}

