//Martin Hofer - 22.368.055-0 - ICCI
// 

package taller2;

import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion = 0;

        do {
            opcion = menu(opcion);

            if(opcion == 1) {
                opcion1();
            }

            if(opcion == 2) {
                opcion2();
            }

            if(opcion == 3) {
                opcion3();
            }

        } while(opcion != 4);

        System.out.println("Saliendo del programa...");
    }

    private static void opcion3() {
        // TODO Auto-generated method stub
    }

    private static void opcion2() {
        // TODO Auto-generated method stub
    }

    private static void opcion1() {
        // TODO Auto-generated method stub
    }

    private static int menu(int opcion) {

        System.out.println("============================================");
        System.out.println("           LA FONDA DEL PAPU                ");
        System.out.println("============================================");
        System.out.println("1) Ver registros.");
        System.out.println("2) Actualizar registros.");
        System.out.println("3) Calcular metricas.");
        System.out.println("4) Salir.");
        System.out.print("Seleccione una opcion: ");

        opcion = scanner.nextInt();

        while(opcion < 1 || opcion > 4) {
            System.out.print("Seleccione una opcion correcta: ");
            opcion = scanner.nextInt();
        }

        return opcion;
    }
}

