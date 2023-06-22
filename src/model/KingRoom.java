package model;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("king")
public class KingRoom extends Room{

    public KingRoom() {
    }

    public KingRoom(int roomNumber, int capacity, boolean isAvailable) {
        super(roomNumber, capacity, isAvailable);
    }

}
