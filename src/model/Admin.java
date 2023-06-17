package model;


public class Admin extends User {

    public Admin() {
    }

    public Admin(String id, String name, String username, String password, EUserType type) {
        super(id, name, username, password, type);
    }


}
