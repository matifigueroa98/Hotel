package model;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("double")
public class DoubleRoom extends Room{

    public DoubleRoom() {
    }

    public DoubleRoom(int roomNumber, int capacity, boolean isAvailable) {
        super(roomNumber, capacity, isAvailable);
    }
     
}
