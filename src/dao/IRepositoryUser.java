package dao;

import java.util.ArrayList;
import model.User;


public interface IRepositoryUser {
    
    void save(User passenger);

    ArrayList<User> findAll();

    Boolean update(String username, String name, String dni, String domicilio, String password);

    Boolean delete(String id);

    User findByUsername(String username);
}
