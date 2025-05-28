package gdg.hongik.mission.reservation;

import jakarta.persistence.*;

public class Reservation {
    @Entity
    @Table(name = "reservations")
    public class Reservation {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String consumer;

        @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
        private List<ReservationItem> items;

        protected Reservation() {}
        public Reservation(String consumer, List<ReservationItem> items) {
            this.consumer = consumer;
            this.items = items;
            this.items.forEach(i -> i.setReservation(this));
        }

        public Long getId() { return id; }
        public String getConsumer() { return consumer; }
        public List<ReservationItem> getItems() { return items; }
    }
}
