package model;


public class Passenger extends User{
    
    private String name;
    private String dni;
    private String origen;
    private String domicilio;

    public Passenger() {
    }

    public Passenger(String username, String password, EUserType type) {
        super(username, password, type.PASSENGER);
    }
    
    
}
