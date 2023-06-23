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

    public ArrayList<Booking> findAll(LocalDate date) {
        retrieveData();
        for (Booking booking : bookings) {
            if (booking.getCheckInDate().equals(date)) {
                System.out.println(booking.toString());
            }
        }
        return bookings;
    }
    
    public Boolean findBookingByDate(Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        retrieveData();
        Boolean foundBooking = false;
        for (Booking booking : bookings) {
            if (booking.getCheckInDate().equals(checkInDate) && booking.getCheckOutDate().equals(checkOutDate)
                    && booking.getRoom().getRoomType().equals(room.getRoomType())) {
                foundBooking = true;
            }
        }
        return foundBooking;
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
