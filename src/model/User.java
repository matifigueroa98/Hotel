package model;

import java.util.UUID;


public class User{
    
    private String id;
    private String name;
    private EUserType type;
    private String dni;
    private String origen;
    private String domicilio;
    private String username;
    private String password;
    private boolean active; // indica si el pasajero tiene una reserva activa

    public User() {
    }

    public User(String id, String name, EUserType type, String dni, String origen, String domicilio, String username, String password) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.type = type;
        this.dni = dni;
        this.origen = origen;
        this.domicilio = domicilio;
        this.username= username;
        this.password=password;
        this.active = false;
    }
    
    public User(String id, String name, EUserType type, String username, String password) { // constructor para el conserje y admin
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.type = type;
        this.username= username;
        this.password=password;
        this.active = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EUserType getType() {
        return type;
    }

    public void setType(EUserType type) {
        this.type = type;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "NOMBRE= " + getName()+" USUARIO= "+username+ " TIPO= "+ type +" DNI= " + dni + " ORIGEN= " + origen + " DOMICILIO= " + domicilio;
    }

}
