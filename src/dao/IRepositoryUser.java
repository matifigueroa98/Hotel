package dao;

import java.util.ArrayList;
import model.User;


public interface IRepositoryUser {
    
    void save(User user);

    ArrayList<User> findAll();

    Boolean update(String id, String name, String username, String password);

    Boolean delete(String id);

    User findByUsername(String username);
}
