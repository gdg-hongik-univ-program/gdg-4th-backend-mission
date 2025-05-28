package gdg.hongik.mission.reservation;

import jakarta.persistence.*;

    @Entity
    @Table(name = "reservation_items")
    public class ReservationItem {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String name;

        @Column(nullable = false)
        private int count;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "reservation_id")
        private Reservation reservation;

        protected ReservationItem() {}
        public ReservationItem(String name, int count) {
            this.name = name;
            this.count = count;
        }

        public Long getId() { return id; }
        public String getName() { return name; }
        public int getCount() { return count; }
        public Reservation getReservation() { return reservation; }
        public void setReservation(Reservation reservation) { this.reservation = reservation; }
    }
