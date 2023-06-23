package model;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("king")
public class KingRoom extends Room{

    public KingRoom() {
    }

    public KingRoom(int roomNumber, int capacity, ERoomType roomType) {
        super(roomNumber, capacity, roomType);
    } 

}
