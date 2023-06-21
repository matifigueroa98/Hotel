package dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import model.Admin;
import model.EUserType;

public class AdminDAO {
    
    private ArrayList<Admin> admins = new ArrayList<>();
    private final String path = "resources/admins.json";
    private final ObjectMapper objMapper = new ObjectMapper();
    private final File file = new File(path);
    
    public AdminDAO() {
    }
    
    public void save(Admin admin) {
        retrieveData();
        try {
            admins.add(admin);
            objMapper.writeValue(file, admins);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public ArrayList<Admin> findAll() {
        retrieveData();
        for (Admin p : admins) {
            System.out.println(p.toString());
        }
        return admins;
    }

    public Boolean update(String username, String name, String password) {
        retrieveData();
        Admin update = findByUsername(username);
        Boolean success = false;
        if (update != null) {
            update.setUsername(username);
            update.setName(name);
            update.setPassword(password);
            update.setType(EUserType.ADMIN);
            success = true;
            try {
                objMapper.writeValue(file, admins);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    public Admin findByUsername(String username) {
        retrieveData();
        Admin toFind = null;
        for (Admin p : admins) {
            if (p.getUsername().equals(username)) {
                toFind = p;
            }
        }
        return toFind;
    }
    
    public Boolean adminExists(String username) {
        Boolean flag = false;
        for (Admin p : admins) {
            if (p.getUsername().equals(username)) {
                flag = true;
            }
        }
        return flag;
    }

    public void updateUsername(String oldUsername, String newUsername) {
        retrieveData();
        Admin update = findByUsername(oldUsername);
        if (update != null) {
            update.setUsername(newUsername);
            try {
                objMapper.writeValue(file, admins);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Boolean delete(String username) {
        retrieveData();
        Admin update = findByUsername(username);
        Boolean success = false;
        if (update != null) {
            admins.remove(update);
            success = true;
            try {
                objMapper.writeValue(file, admins);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return success;
    }
  
    private void retrieveData() { // levanto la informacion de los admins
        try {
            if (file.exists()) {
                admins = objMapper.readValue(file, new TypeReference<ArrayList<Admin>>() {
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }  
}
