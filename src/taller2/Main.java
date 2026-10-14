//Martin Hofer - 22.368.055-0 - ICCI
// 

package taller2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);
    public static String[] Participantes = new String[10];
    public static int CantParticipantes = 0;
    public static Comida[] comidas = new Comida[300];
    public static int CantComida = 0;
    
    public static void main(String[] args) throws FileNotFoundException {
    	LecturaParticipantes();
    	LecturaComida();
    	
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

    private static void LecturaComida() throws FileNotFoundException {
		File arch = new File("txt/Consumido.txt");
		Scanner sFile = new Scanner(arch);
		while(sFile.hasNextLine()) {
			String linea = sFile.nextLine();
			String[] partes = linea.split(";");
			String tipoComida = partes[0];
			if(tipoComida.equalsIgnoreCase("TERREMOTO")) {
				String consumidor = partes[1];
				double litros = Double.parseDouble(partes[2]);
				String fecha = partes[3];
				comidas[CantComida] = new Comida(tipoComida, consumidor, litros, fecha);
				CantComida++;
			}else {
				String consumidor = partes[1];
				int largo = Integer.parseInt(partes[2]);
				int ancho = Integer.parseInt(partes[3]);
				String fecha = partes[4];
				comidas[CantComida] = new Comida(tipoComida, consumidor,largo,ancho,fecha);
				CantComida++;
			}	
		}
		sFile.close();
	}

	private static void LecturaParticipantes() throws FileNotFoundException {
		File arch = new File("txt/Participantes.txt");
		Scanner sFile = new Scanner(arch);
		while(sFile.hasNextLine()) {
			String linea = sFile.nextLine();
			Participantes[CantParticipantes] = linea;
			CantParticipantes++;
		}
		sFile.close();
	}

	private static void opcion3() {
        // TODO Auto-generated method stub
    }

    private static void opcion2() {
        // TODO Auto-generated method stub
    }

    private static void opcion1() {
    	System.out.println("============ VER REGISTROS =================");
    	System.out.println("1) Choripanes.");
    	System.out.println("2) Terremotos.");
    	System.out.println("3) Volver al menu principal.");
    	System.out.print("Seleccione una opcion: ");
    	int opcion = scanner.nextInt();
    	if(opcion == 1) {
    		System.out.println("=============== CHORIPANES =================");
    		System.out.println("#   Consumidor    Largo   Ancho   Fecha           KCal");
    		int cont = 0;
    		for (int i = 0 ; i < CantComida; i++) {
    			if(comidas[i].getTipo().equalsIgnoreCase("Choripan")){
        			System.out.println(cont + "      " + 
    			comidas[i].getConsumidor() + "       " + comidas[i].getLargo()
    			+ "      " + comidas[i].getAncho() + "       " + comidas[i].getFecha() 
    			+ "        " + comidas[i].getKcal());
        			
        		cont++;
        		}
    		}
    		System.out.println("Total: " + cont + " choripanes registrados");
    	}
    	if(opcion == 2) {
    		System.out.println("=============== TERREMOTOS =================");
    		System.out.println("#   Consumidor    Litros   Fecha          KCal");
    		int cont = 0;
    		int contLitros = 0;
    		for ( int i = 0 ; i < CantComida ; i++) {
    			if(comidas[i].getTipo().equalsIgnoreCase("Terremoto")) {
    				System.out.println(cont +"   " + comidas[i].getConsumidor()
    						+ "       " + comidas[i].getLitros() + "      " + 
    						comidas[i].getFecha() + "     " + comidas[i].getKcal());
    				cont++;
    				contLitros+= comidas[i].getLitros();
    			}
    		}
    		System.out.println("Total: " + cont + " Terremotos contados("+
        			contLitros + "L)");
    	}
    	if(opcion == 3) {
    		return;
    	}
    }

    private static int menu(int opcion) {

        System.out.println("============================================");
        System.out.println("           LA FONDA DEL PAPU                ");
        System.out.println("============================================");
        System.out.println("	REGISTROS CARGADOS: " + CantComida +"   ");
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

