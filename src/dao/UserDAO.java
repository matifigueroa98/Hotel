package dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import model.EUserType;
import model.Passenger;

public class UserDAO implements IRepositoryUser {
    
    private ArrayList<Passenger> passengers = new ArrayList<>();
    private final String path = "resources/passengers.json";
    private final ObjectMapper objMapper = new ObjectMapper();
    private final File file = new File(path);
    
    public UserDAO() {
    }

    @Override
    public void save(Passenger passenger) {
        retrieveData();
        try {
            passengers.add(passenger);
            objMapper.writeValue(file, passengers);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Passenger> findAll() {
        retrieveData();
        for (Passenger p : passengers) {
            System.out.println(p.toString());
        }
        return passengers;
    }

    @Override
    public Boolean update(String username, String name, String dni, String domicilio, String password) {
        retrieveData();
        Passenger update = findByUsername(username);
        Boolean success = false;
        if (update != null) {
            update.setName(name);
            update.setDni(dni);
            update.setDomicilio(domicilio);
            update.setType(EUserType.PASSENGER);
            update.setPassword(password);
            success = true;
            try {
                objMapper.writeValue(file, passengers);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    public Boolean userExists(String username) {
        Boolean flag = false;
        for (Passenger p : passengers) {
            if (p.getUsername().equals(username)) {
                flag = true;
            }
        }
        return flag;
    }

    public void updateUsername(String oldUsername, String newUsername) {
        retrieveData();
        Passenger update = findByUsername(oldUsername);
        if (update != null) {
            update.setUsername(newUsername);
            try {
                objMapper.writeValue(file, passengers);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Passenger findByUsername(String username) {
        retrieveData();
        Passenger toFind = null;
        for (Passenger p : passengers) {
            if (p.getUsername().equals(username)) {
                toFind = p;
            }
        }
        return toFind;
    }

    @Override
    public Boolean delete(String username) {
        retrieveData();
        Passenger update = findByUsername(username);
        Boolean success = false;
        if (update != null) {
            passengers.remove(update);
            success = true;
            try {
                objMapper.writeValue(file, passengers);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return success;
    }
    
    private void retrieveData() { // levanto la informacion de los pasajeros
        try {
            if (file.exists()) {
                passengers = objMapper.readValue(file, new TypeReference<ArrayList<Passenger>>() {
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
