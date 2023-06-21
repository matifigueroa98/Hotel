package dao;

import java.util.ArrayList;
import model.Passenger;


public interface IRepositoryUser {
    
    void save(Passenger passenger);

    ArrayList<Passenger> findAll();

    Boolean update(String username, String name, String dni, String domicilio, String password);

    Boolean delete(String id);

    Passenger findByUsername(String username);
}
