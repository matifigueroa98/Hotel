package core;

import core.exceptions.InvalidCharacterException;
import dao.BookingDAO;
import dao.RoomDAO;
import dao.UserDAO;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;
import javax.swing.JOptionPane;
import model.Booking;
import model.EUserType;
import model.Room;
import model.User;

public class Hotel {

    private UserDAO userDAO = new UserDAO();
    private RoomDAO roomDAO = new RoomDAO();
    private BookingDAO bookingDAO = new BookingDAO();

    public Hotel() {
        this.userDAO = new UserDAO();
        this.roomDAO = new RoomDAO();
        this.bookingDAO = new BookingDAO();
    }

    public void menu() throws InvalidCharacterException {
        int opcion;
        JOptionPane.showMessageDialog(null, "Bienvenido al HOTEL!");

        User user = logIn();

        if (user != null) {
            System.out.println("Log In exitoso! Bienvenido " + user.getName());
            switch (user.getType()) {
                case ADMIN:
                    adminMenu(user);
                    break;
                case CONCIERGE:
                    break;
                case PASSENGER:
                    break;
                default:
                    System.out.println("Tipo de usuario desconocido");
                    break;
            }
        } else {
            try {
                JOptionPane.showMessageDialog(null, "El usuario no se encuentra en el sistema. Te gustaria agregar uno nuevo?");
                opcion = Integer.parseInt(JOptionPane.showInputDialog("1.SI \n2.EXIT"));
                if (opcion < 1 || opcion > 3) {
                    throw new InvalidCharacterException("Opcion no valida, Ingrese un numero entre 1 y 3");
                }
                switch (opcion) {
                    case 1:
                        signUp();
                        break;
                    case 2:
                        break;
                    default:
                        System.out.println("Opcion no valida.");
                        break;
                }
            } catch (InvalidCharacterException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Error: Se esperaba un numero entero");
            }
        }
    }

    public void adminMenu(User user) throws InvalidCharacterException {
        Scanner input = new Scanner(System.in);
        System.out.println("Ingrese la contraseña como administrador");
        String p = input.next();
        if (p.equals("123")) {
            System.out.println("Bienvenido al sistema de administracion del HOTEL");
            System.out.println("Que le gustaria realizar?");
            System.out.println("""
                           1. Crear usuario
                           2. Modificar usuario
                           3. Eliminar usuario
                           4. Mostrar usuarios
                           5. Crear admin
                           6. Crear conserje
                           7. Asignar rol/permisos
                           8. Mostrar habitaciones
                           9. TEST""");
            try {
                Integer s = input.nextInt();
                if (s < 1 || s > 9) {
                    throw new InvalidCharacterException("Opcion no valida, Ingrese un numero entre 1 y 9");
                }
                switch (s) {
                    case 1:
                        signUp();
                        break;
                    case 2:
                        update();
                        break;
                    case 3:
                        delete(); // elimina sea pasajero, admin o conserje
                        break;
                    case 4:
                        userDAO.findAll();
                        break;
                    case 5:
                        createAdmin();
                        break;
                    case 6:
                        createConcierge();
                        break;
                    case 7:
                        assignUserRole();
                        break;
                    case 8:
                        roomDAO.findAll();
                        break;
                    case 9:
                        newBooking(user);
                        break;
                }
            } catch (InvalidCharacterException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("Error: Se esperaba un numero entero");
                input.next();
            }
        } else {
            System.out.println("USTED NO TIENE PERMISO COMO ADMINISTRADOR!");
        }
        input.close();
    }

    public User logIn() {
        String username = JOptionPane.showInputDialog("Nombre de usuario: ");
        String enteredPswd = JOptionPane.showInputDialog("Contraseña: ");
        User passenger = userDAO.findByUsername(username);
        if (passenger != null && !passenger.getPassword().equals(enteredPswd)) {
            passenger = null;
        }
        return passenger;
    }

    public void signUp() {
        Scanner input = new Scanner(System.in);
        String name, dni, origen, domicilio, id, username, password;

        System.out.print("Nombre: ");
        name = input.nextLine();
        System.out.print("DNI: ");
        dni = input.nextLine();
        System.out.print("Origen(Pais): ");
        origen = input.nextLine();
        System.out.print("Domicilio: ");
        domicilio = input.nextLine();
        System.out.print("Nombre de usuario: ");
        username = input.nextLine();
        User userExists = userDAO.findByUsername(username);
        while (userExists != null) {
            System.out.println("El usuario " + username + " ya existe. Vuelva a ingresar.");
            System.out.print("Nombre de usuario: ");
            username = input.nextLine();
            userExists = userDAO.findByUsername(username);
        }
        System.out.print("Contraseña: ");
        password = input.nextLine();
        id = UUID.randomUUID().toString();
        User passenger = new User(id, name, EUserType.PASSENGER, dni, origen, domicilio, username, password);
        userDAO.save(passenger);
        System.out.println("Pasajero: " + name + " agregado correctamente.");
    }

    public void update() {
        Scanner input = new Scanner(System.in);
        String name, dni, domicilio, username, password;
        System.out.print("Ingrese el usuario del pasajero que desea modificar: ");
        username = input.next();
        input.nextLine(); // Limpieza del búfer
        User passenger = userDAO.findByUsername(username);
        if (passenger != null) {
            System.out.print("Ingrese su nombre: ");
            name = input.nextLine();
            System.out.print("Ingrese su DNI: ");
            dni = input.nextLine();
            System.out.print("Ingrese su domicilio: ");
            domicilio = input.nextLine();
            System.out.print("Ingrese su nueva contraseña: ");
            password = input.nextLine();

            Boolean update = userDAO.update(username, name, dni, domicilio, password);
            if (update) {
                System.out.println("Usuario: " + username + " ha sido actualizado correctamente!");
            } else {
                System.out.println("Ha ocurrido un error al intentar modificar el usuario.");
            }
            System.out.println("Le gustaria cambiar su nombre de usuario?\n1.SI \n2.NO");
            Integer option = input.nextInt();
            switch (option) {
                case 1:
                    Boolean valid = false;
                    while (!valid) {
                        System.out.print("Ingrese su nombre de usuario: ");
                        String newUsername = input.next();
                        if (!newUsername.equals(username) && !userDAO.userExists(newUsername)) {// validando si el usuario esta en la base de datos
                            userDAO.updateUsername(username, newUsername); // actualizando usuario
                            System.out.println("El nombre de usuario ha sido actualizado correctamente.");
                            valid = true;
                        } else {
                            System.out.println("ERROR! El nuevo usuario es el mismo o ya existe.");
                        }
                    }
                    break;
                case 2:
                    System.out.println("Adios!");
                    break;
                default:
                    System.out.println("Opcion invalida. Vuelva a intentar!");
                    break;
            }
        } else {
            System.out.println("El usuario ingresado es incorrecto o no existe.");
        }
    }

    public void delete() {
        Scanner in = new Scanner(System.in);
        String username;

        System.out.print("Ingrese el usuario que desea eliminar: ");
        username = in.next();
        Boolean deleteUser = userDAO.delete(username);
        if (deleteUser) {
            System.out.println("El usuario " + username + " ha sido eliminado satisfactoriamente!");
        } else {
            System.out.println("El usuario " + username + " no se encontro.");
        }
    }
    
     public void createAdmin() {
        Scanner input = new Scanner(System.in);
        String name, id, username, password;

        System.out.print("Nombre: ");
        name = input.nextLine();
        System.out.print("Nombre de usuario: ");
        username = input.nextLine();
        User userExists = userDAO.findByUsername(username);
        while (userExists != null) {
            System.out.println("El usuario " + username + " ya existe. Vuelva a ingresar.");
            System.out.print("Nombre de usuario: ");
            username = input.nextLine();
            userExists = userDAO.findByUsername(username);
        }
        System.out.print("Contraseña: ");
        password = input.nextLine();
        id = UUID.randomUUID().toString();
        User admin = new User(id, name, EUserType.ADMIN, username, password);
        userDAO.save(admin);
        System.out.println("ADMIN: " + name + " agregado correctamente.");
    }

    public void createConcierge() {
        Scanner input = new Scanner(System.in);
        String name, id, username, password;

        System.out.print("Nombre: ");
        name = input.nextLine();
        System.out.print("Nombre de usuario: ");
        username = input.nextLine();
        User userExists = userDAO.findByUsername(username);
        while (userExists != null) {
            System.out.println("El usuario " + username + " ya existe. Vuelva a ingresar.");
            System.out.print("Nombre de usuario: ");
            username = input.nextLine();
            userExists = userDAO.findByUsername(username);
        }
        System.out.print("Contraseña: ");
        password = input.nextLine();
        id = UUID.randomUUID().toString();
        User concierge = new User(id, name, EUserType.CONCIERGE, username, password);
        userDAO.save(concierge);
        System.out.println("Concierge: " + name + " agregado correctamente.");
    }

    public void assignUserRole() {
        Scanner input = new Scanner(System.in);
        String username;
        Boolean update = false;
        System.out.print("Escriba el usuario que desea asignar rol: ");
        username = input.nextLine();
        User user = userDAO.findByUsername(username);
        if (user != null) {
            System.out.print("Escriba 'A' para cambiar el rol a administrador o 'C' para conserje: ");
            String option = input.nextLine();
            if (option.equalsIgnoreCase("a")) {
                update = userDAO.updateRol(username, EUserType.ADMIN);
            } else if (option.equalsIgnoreCase("c")) {
                update = userDAO.updateRol(username, EUserType.CONCIERGE);
            } else {
                System.out.println("Opción inválida. Rol no asignado");
            }
            if (update) {
                System.out.println("Usuario: " + username + " ha sido actualizado correctamente!");
            } else {
                System.out.println("Ha ocurrido un error al intentar modificar el usuario");
            }
        } else {
            System.out.println("El usuario no existe");
        }
    }
    
    public void newBooking (User user) {
        LocalDate checkIn, checkOut;
        Room room;
        
        checkIn = checkIn();
        checkOut = checkOut(checkIn);
        room = roomAvailable(checkIn, checkOut);
        
        Booking booking = new Booking (user, checkIn, checkOut, true, room, Double.NaN);
        bookingDAO.save(booking);
    }

    public String chooseRoom() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Por favor escriba 'individual', 'double', 'king' para elegir su cuarto: ");
        String room = scan.nextLine();
        while (!room.equals("individual") && !room.equals("double") && !room.equals("king")) {
            System.out.println("Error! Habitacion equivocada");
            System.out.print("Vuelva a intentar: ");
            room = scan.nextLine();
        }
        return room;
    }

    public Room roomAvailable(LocalDate checkIn, LocalDate checkOut) {
        Room room;
        String roomPick;
        Boolean notAvailable;
        do {
            roomPick = chooseRoom();
            room = roomDAO.findRoom(roomPick);
            if (room != null) {
                notAvailable = bookingDAO.findBookingByDate(room, checkIn, checkOut);
                if (notAvailable) {
                    System.out.println("El cuarto seleccionado no esta disponible en esa fecha");
                    room = null;
                } else {
                    System.out.println("El cuarto seleccionado se encuentra disponible!\n");
                }
            } else {
                System.out.println("El cuarto seleccionado no esta disponible.");
            }
        } while (room == null);
        return room;
    }

    public LocalDate checkIn() {
        Scanner in = new Scanner(System.in);
        LocalDate checkIn = null;

        System.out.println("Indique la fecha (dd/mm/yyyy) que desea realizar el CHECK-IN: ");
        while (checkIn == null || checkIn.isBefore(LocalDate.now())) {
            try {
                String date = in.next();
                checkIn = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                if (checkIn.isBefore(LocalDate.now())) {
                    System.out.println("No puedes elegir una fecha pasada. Intente una fecha de hoy en adelante!");
                    checkIn = null;
                }
            } catch (java.time.format.DateTimeParseException e) {
                System.out.println("La fecha no fue ingresada correctamente");
            }
        }
        System.out.println("CHECK-IN: " + checkIn.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        return checkIn;
    }

    public LocalDate checkOut(LocalDate checkIn) {
        Scanner in = new Scanner(System.in);
        LocalDate checkOut = null;

        System.out.println("Indique la fecha (dd/mm/yyyy) en la que desea realizar el CHECK-OUT: ");
        while (checkOut == null || checkOut.isBefore(checkIn)) {
            try {
                String date = in.next();
                checkOut = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                if (checkOut.isBefore(checkIn)) {
                    System.out.println("La fecha de check-out debe ser posterior a la fecha de check-in. Intente nuevamente");
                    checkOut = null;
                }
            } catch (DateTimeParseException e) {
                System.out.println("La fecha no fue ingresada correctamente");
            }
        }
        System.out.println("CHECK-OUT: " + checkOut.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        return checkOut;
    }
}