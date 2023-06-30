package model;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("suite")
public class SuiteRoom extends Room {

    public SuiteRoom() {
    }

    public SuiteRoom(int roomNumber, int capacity, ERoomType roomType) {
        super(roomNumber, capacity, roomType);
    }

    public String cateringService() {
        return "catering service";
    }
    
    public String jacuzzi() {
        return "jacuzzi";
    }
}
