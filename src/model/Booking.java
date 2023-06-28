package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.util.UUID;

public class Booking {

    private UUID uuid;
    private long id; // numero de reserva
    @JsonIgnoreProperties(value = {"id","dni", "origen", "domicilio", "password", "active"})
    private User passenger; // informacion del pasajero
    private Integer passengers; // cantidad de pasajeros, si esta acompañado o no
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Room room;
    private Double totalPrice;

    public Booking() {
    }

    public Booking(User passenger, Integer passengers, LocalDate checkInDate, LocalDate checkOutDate, Room room, Double totalPrice) {
        this.uuid = UUID.randomUUID();
        this.id = uuid.getMostSignificantBits();
        this.passenger = passenger;
        this.passengers = passengers;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.room = room;
        this.totalPrice = totalPrice;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getPassenger() {
        return passenger;
    }

    public void setPassenger(User passenger) {
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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getPassengers() {
        return passengers;
    }

    public void setPassengers(Integer passengers) {
        this.passengers = passengers;
    }

    @Override
    public String toString() {
        return "ID = " + id + " Pasajero: " + passenger.getName() + " Reserva: "
                + passenger.isActive() + " CHECK IN = " + checkInDate + " CHECK OUT = " + checkOutDate + " TOTAL = " + totalPrice;
    }
}
