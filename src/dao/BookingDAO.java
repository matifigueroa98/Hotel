package dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import model.Booking;
import model.Room;

public class BookingDAO {

    private ArrayList<Booking> bookings = new ArrayList<>();
    private final String path = "resources/bookings.json";
    private final ObjectMapper objMapper = new ObjectMapper();
    private final File file = new File(path);

    public void save(Booking booking) {
        retrieveData();
        try {
            objMapper.registerModule(new JavaTimeModule());
            bookings.add(booking);
            objMapper.writeValue(file, bookings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Booking> findAll() {
        retrieveData();
        for (Booking booking : bookings) {
            System.out.println(booking.toString());
        }
        return bookings;
    }

    public Boolean findBookingByDate(Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        retrieveData();
        Boolean foundBooking = false;
        for (Booking booking : bookings) {
            LocalDate existingCheckIn = booking.getCheckInDate();
            LocalDate existingCheckOut = booking.getCheckOutDate();

            if (booking.getRoom().getRoomType().equals(room.getRoomType())) {
                if ((checkInDate.isAfter(existingCheckIn) || checkInDate.isEqual(existingCheckIn))
                        && (checkInDate.isBefore(existingCheckOut) || checkInDate.isEqual(existingCheckOut))
                        && (checkOutDate.isAfter(existingCheckIn) || checkOutDate.isEqual(existingCheckIn))
                        && (checkOutDate.isBefore(existingCheckOut) || checkOutDate.isEqual(existingCheckOut))) {
                    foundBooking = true;
                    break;
                }
            }
        }
        return foundBooking;
    }

    public Booking findById(Long id) {
        retrieveData();
        Booking toFind = null;
        for (Booking booking : bookings) {
            if (booking.getId() == id) {
                toFind = booking;
            }
        }
        return toFind;
    }

    public Boolean delete(Long id) {
        retrieveData();
        Booking delete = findById(id);
        Boolean success = false;
        if (delete != null) {
            LocalDate today = LocalDate.now();
            LocalDate checkInDate = delete.getCheckInDate();
            if (checkInDate.isAfter(today.plusDays(1))) {// no puede cancelar la reserva a menos de 24 hs
                bookings.remove(delete);
                success = true;
                try {
                    objMapper.writeValue(file, bookings);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return success;
    }

    private void retrieveData() {
        try {
            if (file.exists()) {
                objMapper.registerModule(new JavaTimeModule());
                bookings = objMapper.readValue(file, new TypeReference<ArrayList<Booking>>() {
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
