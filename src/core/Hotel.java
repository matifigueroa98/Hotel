package core;

import core.exceptions.InvalidCharacterException;
import dao.AdminDAO;
import dao.UserDAO;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;
import model.Admin;
import model.EUserType;
import model.Passenger;

public class Hotel {

    private UserDAO userDAO = new UserDAO();
    private AdminDAO adminDAO = new AdminDAO();

    public Hotel() {
        this.userDAO = new UserDAO();
        this.adminDAO = new AdminDAO();
    }

    public void menu() {
        Scanner in = new Scanner(System.in);
        System.out.println("Bienvenido al HOTEL!");
        Passenger passenger = logIn();

        if (passenger != null) {
            System.out.println("Log In exitoso! Bienvenido " + passenger.getName());
        } else {
            System.out.println("El usuario no se encuentra en el sistema. Te gustaria agregar uno nuevo?");
            System.out.println("1.SI \n2.EXIT");

            Integer option = in.nextInt();
            switch (option) {
                case 1:
                    signUp();
                    break;
                case 2:
                    break;
                default:
                    System.out.println("Opcion no valida.");
                    break;
            }
        }
    }

    public void adminMenu() throws InvalidCharacterException {
        Scanner input = new Scanner(System.in);
        System.out.println("Ingrese la contraseña como administrador");
        String p = input.next();
        if (p.equals("123")) {
            System.out.println("Bienvenido al sistema de administracion del HOTEL");
            System.out.println("Que le gustaria realizar?");
            System.out.println("""
                           1. Informacion sobre usuarios
                           2. Informacion sobre admins
                           3. Informacion sobre conserjes""");
            try {
                Integer s = input.nextInt();
                if (s < 1 || s > 5) {
                    throw new InvalidCharacterException("Opcion no valida, Ingrese un numero entre 1 y 5");
                }
                switch (s) {
                    case 1:
                        menu();
                        break;
                    case 2:
                        secondaryAdminMenu();
                        break;
                    case 3:
                        adminDAO.findAll();
                        break;
                    case 4:
                        userDAO.findAll();
                        break;
                    case 5:
                        logIn();
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
    
    public void secondaryAdminMenu() {
        Scanner in = new Scanner(System.in);
        System.out.println("Bienvenido al HOTEL como ADMINISTRADOR!");
        Admin admin = logInAdmin();

        if (admin != null) {
            System.out.println("Log In exitoso! Bienvenido ADMIN: " + admin.getName());
            System.out.println("Que desea hacer?");
            System.out.println("Presione:\n1. Modificar ADMIN\n2. Eliminar ADMIN\n3. Mostrar ADMINS");
            Integer op = in.nextInt();
            switch (op) {
                case 1:
                    updateAdmin();
                    break;
                case 2:
                    deleteAdmin();
                    break;
                case 3:
                    adminDAO.findAll();
                    break;
            }
        } else {
            System.out.println("El ADMIN no se encuentra en el sistema. Te gustaria agregar uno nuevo?");
            System.out.println("1.SI \n2.EXIT");

            Integer option = in.nextInt();
            switch (option) {
                case 1:
                    signUpAdmin();
                    break;
                case 2:
                    break;
                default:
                    System.out.println("Opcion no valida.");
                    break;
            }
        }
    }

    public Passenger logIn() {
        Scanner in = new Scanner(System.in);
        System.out.print("Nombre de usuario: ");
        String username = in.next();
        System.out.print("Contraseña: ");
        String enteredPswd = in.next();
        Passenger passenger = userDAO.findByUsername(username);
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
        Passenger userExists = userDAO.findByUsername(username);
        while (userExists != null) {
            System.out.println("El usuario " + username + " ya existe. Vuelva a ingresar.");
            System.out.print("Nombre de usuario: ");
            username = input.nextLine();
            userExists = userDAO.findByUsername(username);
        }
        System.out.print("Contraseña: ");
        password = input.nextLine();
        id = UUID.randomUUID().toString();
        Passenger passenger = new Passenger(dni, origen, domicilio, id, name, username, password, EUserType.PASSENGER);
        userDAO.save(passenger);
        System.out.println("Pasajero: " + name + " agregado correctamente.");
    }

    public void update() {
        Scanner input = new Scanner(System.in);
        String name, dni, domicilio, username, password;
        System.out.print("Ingrese el usuario del pasajero que desea modificar: ");
        username = input.next();
        input.nextLine(); // Limpieza del búfer
        Passenger passenger = userDAO.findByUsername(username);
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
    // SECCION ADMIN

    public void signUpAdmin() {
        String name, id, username, password;

        Scanner input = new Scanner(System.in);

        System.out.print("Nombre: ");
        name = input.nextLine();
        System.out.print("Nombre de usuario: ");
        username = input.nextLine();
        Admin userExists = adminDAO.findByUsername(username);
        while (userExists != null) {
            System.out.println("El ADMIN " + username + " ya existe. Vuelva a ingresar.");
            System.out.print("Nombre de usuario: ");
            username = input.nextLine();
            userExists = adminDAO.findByUsername(username);
        }
        System.out.print("Contraseña: ");
        password = input.nextLine();
        id = UUID.randomUUID().toString();
        Admin admin = new Admin(id, name, username, password, EUserType.ADMIN);
        adminDAO.save(admin);
        System.out.println("Admin: " + name + " agregado correctamente.");
    }

    public void updateAdmin() {
        Scanner input = new Scanner(System.in);
        String name, username, password;
        System.out.print("Ingrese el usuario del ADMIN que desea modificar: ");
        username = input.next();
        input.nextLine(); // Limpieza del búfer
        Admin admin = adminDAO.findByUsername(username);
        if (admin != null) {
            System.out.print("Ingrese su nombre: ");
            name = input.nextLine();
            System.out.print("Ingrese su nueva contraseña: ");
            password = input.nextLine();

            Boolean update = adminDAO.update(username, name, password);
            if (update) {
                System.out.println("ADMIN: " + username + " ha sido actualizado correctamente!");
            } else {
                System.out.println("Ha ocurrido un error al intentar modificar el admin.");
            }
            System.out.println("Le gustaria cambiar su nombre de usuario?\n1.SI \n2.NO");
            Integer option = input.nextInt();
            switch (option) {
                case 1:
                    Boolean valid = false;
                    while (!valid) {
                        System.out.print("Ingrese su nombre de usuario: ");
                        String newUsername = input.next();
                        if (!newUsername.equals(username) && !adminDAO.adminExists(newUsername)) {// validando si el admin esta en la base de datos
                            adminDAO.updateUsername(username, newUsername); // actualizando admin
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
            System.out.println("El admin ingresado es incorrecto o no existe.");
        }
    }

    public void deleteAdmin() {
        Scanner in = new Scanner(System.in);
        String username;

        System.out.print("Ingrese el ADMIN que desea eliminar: ");
        username = in.next();
        Boolean deleteUser = adminDAO.delete(username);
        if (deleteUser) {
            System.out.println("El admin " + username + " ha sido eliminado satisfactoriamente!");
        } else {
            System.out.println("El admin " + username + " no se encontro.");
        }
    }
    
    public Admin logInAdmin() {
        Scanner in = new Scanner(System.in);
        System.out.print("Nombre de usuario: ");
        String username = in.next();
        System.out.print("Contraseña: ");
        String enteredPswd = in.next();
        Admin admin = adminDAO.findByUsername(username);
        if (admin != null && !admin.getPassword().equals(enteredPswd)) {
            admin = null;
        }
        return admin;
    }

}
