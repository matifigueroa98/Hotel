package model;


public class Room {
    
    private int roomNumber;
    private int capacity; // número de pasajeros que puede alojar
    private boolean isAvailable; // booleano que indica si la habitación está ocupada o no

    public Room() {
    }

    public Room(int roomNumber, int capacity, boolean isAvailable) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.isAvailable = isAvailable;
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

    public boolean isIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    } 
    
}
