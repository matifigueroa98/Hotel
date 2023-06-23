package model;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("individual")
public class IndividualRoom extends Room {

    public IndividualRoom() {
    }

    public IndividualRoom(int roomNumber, int capacity, ERoomType roomType) {
        super(roomNumber, capacity, roomType);
    } 
}
