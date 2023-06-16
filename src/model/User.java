package model;


public abstract class User {
    
    private String username;
    private String password;
    private EUserType type;
    
    public User(){
        
    }

    public User(String username, String password, EUserType type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }
   
    
}
