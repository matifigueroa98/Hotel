package dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import model.Booking;
import model.EUserType;
import model.User;

public class UserDAO implements IRepositoryUser {
    
    private ArrayList<User> users = new ArrayList<>();
    private final String path = "resources/users.json";
    private final ObjectMapper objMapper = new ObjectMapper();
    private final File file = new File(path);
    
    public UserDAO() {
    }

    @Override
    public void save(User user) {
        retrieveData();
        try {
            users.add(user);
            objMapper.writeValue(file, users);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<User> findAll() {
        retrieveData();
        for (User p : users) {
            System.out.println(p.toString());
        }
        return users;
    }

    public void updateUserStatus(User user) {
        retrieveData();
        try {
            User userToUpdate = null;
            for (User p : users) {
                if (p.getId().equals(user.getId())) {
                    userToUpdate = p;
                    userToUpdate.setActive(true);
                }
            }
            objMapper.writeValue(file, users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveTotalSpent(User user, Booking booking) {
        retrieveData();
        try {
            User userToUpdate = null;
            for (User u : users) {
                if (u.getId().equals(user.getId())) {
                    userToUpdate = u;
                }
            }
            double newTotalSpent = userToUpdate.getTotalSpent() + booking.getTotalPrice();
            userToUpdate.setTotalSpent(newTotalSpent);
            objMapper.writeValue(file, users); // guardamos el total gastado del usuario
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteTotalSpent(User user, Booking booking) {
        retrieveData();
        try {
            User userToUpdate = null;
            for (User u : users) {
                if (u.getId().equals(user.getId())) {
                    userToUpdate = u;
                }
            }
            double newTotalSpent = userToUpdate.getTotalSpent() - booking.getTotalPrice();
            userToUpdate.setTotalSpent(newTotalSpent);

            objMapper.writeValue(file, users); // guardamos el total gastado del usuario
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean update(String username, String name, String dni, String domicilio, String password) {
        retrieveData();
        User update = findByUsername(username);
        Boolean success = false;
        if (update != null) {
            update.setName(name);
            update.setDni(dni);
            update.setDomicilio(domicilio);
            update.setPassword(password);
            success = true;
            try {
                objMapper.writeValue(file, users);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return success;
    }
    
    public Boolean updateRol(String username, EUserType type) {
        retrieveData();
        User update = findByUsername(username);
        Boolean success = false;
        if (update != null) {
            update.setType(type);
            success = true;
            try {
                objMapper.writeValue(file, users);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    public Boolean userExists(String username) {
        Boolean flag = false;
        for (User p : users) {
            if (p.getUsername().equals(username)) {
                flag = true;
            }
        }
        return flag;
    }

    public void updateUsername(String oldUsername, String newUsername) {
        retrieveData();
        User update = findByUsername(oldUsername);
        if (update != null) {
            update.setUsername(newUsername);
            try {
                objMapper.writeValue(file, users);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public User findByUsername(String username) {
        retrieveData();
        User toFind = null;
        for (User p : users) {
            if (p.getUsername().equals(username)) {
                toFind = p;
            }
        }
        return toFind;
    }

    @Override
    public Boolean delete(String username) {
        retrieveData();
        User update = findByUsername(username);
        Boolean success = false;
        if (update != null) {
            users.remove(update);
            success = true;
            try {
                objMapper.writeValue(file, users);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return success;
    }
    
    private void retrieveData() { // levanto la informacion de los pasajeros
        try {
            if (file.exists()) {
                users = objMapper.readValue(file, new TypeReference<ArrayList<User>>() {
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
