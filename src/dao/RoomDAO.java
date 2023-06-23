package dao;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import model.Room;

public class RoomDAO {
    
    private ArrayList<Room> rooms = new ArrayList<>();
    private final String path = "resources/rooms.json";
    private final ObjectMapper objMapper = new ObjectMapper();
    private final File file = new File(path);
    
    public ArrayList<Room> findAll() {
      retrieveData();
        for (Room room : rooms) {
            System.out.println(room.toString());
        }
        return rooms;
    }

    public Room findRoom(String type) {
        retrieveData();
        Room toFind = null;
        for (Room room : rooms) {
            if (room.getClass().getAnnotation(JsonTypeName.class).value().equals(type)) {
                toFind = room;
                break;
            }
        }
        return toFind;
    }

    private void retrieveData() { 
        try {
            if (file.exists()) {
                rooms = objMapper.readValue(file, new TypeReference<ArrayList<Room>>() {
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
}
