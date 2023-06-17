package model;

import java.time.LocalDate;
import java.util.UUID;


public class Booking {
    
    private UUID uuid;
    private long id; // numero de reserva
    private Passenger passenger; // informacion del pasajero
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private boolean active; // esta activa cuando esta paga la habitacion (reserva)
    private Room room;

    public Booking() {
    }

    public Booking(UUID uuid, long id, Passenger passenger, LocalDate checkInDate, LocalDate checkOutDate, boolean active, Room room) {
        this.uuid = UUID.randomUUID();
        this.id = uuid.getMostSignificantBits();
        this.passenger = passenger;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.active = active;
        this.room = room;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "Booking{"+" id=" + id + ", passenger=" + passenger + ", checkInDate=" + checkInDate + ", checkOutDate=" + checkOutDate + ", active=" + active + ", room=" + room + '}';
    }
    
}
