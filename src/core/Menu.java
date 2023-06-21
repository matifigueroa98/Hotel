package core;

import core.exceptions.InvalidCharacterException;
import javax.swing.JOptionPane;

public class Menu {

    public void run() throws InvalidCharacterException { // MENU GENERAL
        Integer menu;
        JOptionPane.showMessageDialog(null, """
                                          Bienvenido al Hotel! Usted es?
                                          
                                          1. Pasajero
                                          2. Conserje
                                          3. ADMIN
                                          
                                   """);

        Hotel hotel = new Hotel();
        try {
            menu = Integer.parseInt(JOptionPane.showInputDialog("Elija una opcion"));

            if (menu < 1 || menu > 3) {
                throw new InvalidCharacterException("Opcion no valida, Ingrese un numero entre 1 y 3");
            }
            switch (menu) {
                case 1 -> hotel.menu();
                case 2 -> hotel.adminMenu(); // modificar
                case 3 -> hotel.adminMenu();
            }

        } catch (InvalidCharacterException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException  e) {
            System.out.println("Error: Se esperaba un numero entero");
        }
    }
}
