package model;


public class Passenger extends User{
    
    private String dni;
    private String origen;
    private String domicilio;

    public Passenger() {
    }

    public Passenger(String dni, String origen, String domicilio, String id, String name, String username, String password, EUserType type) {
        super(id, name, username, password, type);
        this.dni = dni;
        this.origen = origen;
        this.domicilio = domicilio;
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


    
}
