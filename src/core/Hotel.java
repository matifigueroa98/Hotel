package core;

import core.exceptions.InvalidCharacterException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;
import model.EUserType;
import model.Passenger;


public class Hotel {
    
    private final Scanner in = new Scanner(System.in);
    
    public void adminMenu() throws InvalidCharacterException {
        System.out.println("Bienvenido al sistema de administracion del HOTEL");
        System.out.println("Que le gustaria realizar?");
        System.out.println("1. Crear un usuario\n2. Modificar un usuario\n3. Log in");
        try {
            Integer s = in.nextInt();
            if (s < 1 || s > 4) {
                throw new InvalidCharacterException("Opcion no valida, Ingrese un numero entre 1 y 3");
            }
            switch (s) {
                case 1: signUp();
                    break;
                case 2:
                    break;
            }
        } catch (InvalidCharacterException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Error: Se esperaba un numero entero");
            in.next();
        }
    }

    public void signUp() {
        String name, dni, origen, domicilio, id, username, password;
         
        Scanner input = new Scanner(System.in);

        System.out.print("Nombre: ");
        name = input.nextLine();
        System.out.print("D.N.I: ");
        dni = input.nextLine();
        System.out.print("Origen(Pais): ");
        origen = input.nextLine();
        System.out.println("Domicilio: ");
        domicilio = input.nextLine();
        System.out.println("Nombre de usuario: ");
        username = input.nextLine();
        System.out.println("Contraseña: ");
        password = input.nextLine();
        id = UUID.randomUUID().toString();
        Passenger p = new Passenger (dni, origen, domicilio, id, name, username, password, EUserType.PASSENGER);
    }
    
}
