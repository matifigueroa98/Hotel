package model;


public class Passenger extends User{
    
    private String dni;
    private String origen;
    private String domicilio;
    private boolean active; // indica si el pasajero tiene una reserva activa

    public Passenger() {
    }

    public Passenger(String dni, String origen, String domicilio, String id, String name, String username, String password, EUserType type) {
        super(id, name, username, password, type);
        this.dni = dni;
        this.origen = origen;
        this.domicilio = domicilio;
        this.active = false;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void setType(EUserType type) {
        super.setType(type);
    }

    @Override
    public EUserType getType() {
        return super.getType();
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Override
    public String getPassword() {
        return super.getPassword(); 
    }

    @Override
    public void setUsername(String username) {
        super.setUsername(username);
    }

    @Override
    public String getUsername() {
        return super.getUsername(); 
    }

    @Override
    public void setName(String name) {
        super.setName(name); 
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setId(String id) {
        super.setId(id);
    }

    @Override
    public String getId() {
        return super.getId();
    }

    @Override
    public String toString() {
        return "NOMBRE= " + getName()+" USUARIO= "+getUsername()+" DNI= " + dni + " ORIGEN= " + origen + " DOMICILIO= " + domicilio;
    }
    
    
}
