package model;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("double")
public class DoubleRoom extends Room{

    public DoubleRoom() {
    }

    public DoubleRoom(int roomNumber, int capacity, ERoomType roomType) {
        super(roomNumber, capacity, roomType);
    } 
     
}
