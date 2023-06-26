package model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = IndividualRoom.class, name = "individual"),
    @JsonSubTypes.Type(value = DoubleRoom.class, name = "double"),
    @JsonSubTypes.Type(value = KingRoom.class, name = "king")
})
public abstract class Room {

    private int roomNumber;
    private int capacity; // número de pasajeros que puede alojar
    private ERoomType roomType;

    public Room() {
    }

    public Room(int roomNumber, int capacity, ERoomType roomType) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.roomType = roomType;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public ERoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(ERoomType roomType) {
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "Numero de cuarto = " + roomNumber + " Capacidad = " + capacity + " Tipo de cuarto = " + roomType;
    }
}
