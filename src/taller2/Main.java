//Martin Hofer - 22.368.055-0 - ICCI
//Vicente Jara - 
package taller2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
            opcion = menu();

            if(opcion == 1) {
                opcion1();
            } else if(opcion == 2) {
                opcion2();
            } else if(opcion == 3) {
                opcion3();
            }

        } while(opcion != 4);

        System.out.println(">> Saliendo del programa...");
    }

    private static void LecturaComida() throws FileNotFoundException {
		File arch = new File("Consumido.txt");
		if (!arch.exists()) {
			System.out.println(">> ADVERTENCIA: no se encontro el archivo \"Consumido.txt\". Se iniciara sin registros.");
			return;
		}
		Scanner sFile = new Scanner(arch);
		while(sFile.hasNextLine()) {
			String linea = sFile.nextLine().trim();
			if(linea.isEmpty()) continue;
			
			String[] partes = linea.split(";");
			String tipoComida = partes[0];
			if(tipoComida.equalsIgnoreCase("TERREMOTO") && partes.length == 4) {
				String consumidor = partes[1];
				double litros = Double.parseDouble(partes[2]);
				String fecha = partes[3];
				comidas[CantComida] = new Comida(tipoComida, consumidor, litros, fecha);
				CantComida++;
			} else if(tipoComida.equalsIgnoreCase("CHORIPAN") && partes.length == 5) {
				String consumidor = partes[1];
				int largo = Integer.parseInt(partes[2]);
				int ancho = Integer.parseInt(partes[3]);
				String fecha = partes[4];
				comidas[CantComida] = new Comida(tipoComida, consumidor, largo, ancho, fecha);
				CantComida++;
			}	
		}
		sFile.close();
	}

	private static void LecturaParticipantes() throws FileNotFoundException {
		File arch = new File("Participantes.txt");
		if (!arch.exists()) return;
		Scanner sFile = new Scanner(arch);
		while(sFile.hasNextLine()) {
			String linea = sFile.nextLine().trim();
			if(!linea.isEmpty()) {
				Participantes[CantParticipantes] = linea;
				CantParticipantes++;
			}
		}
		sFile.close();
	}

	private static void guardarArchivo() {
		// hoferziño acá hice que cada vez que agregamos, editamos o borramos, 
		// se sobrescriba el archivo de texto para mantener la persistencia al día.
		try {
			FileWriter writer = new FileWriter("Consumido.txt");
			for (int i = 0; i < CantComida; i++) {
				Comida c = comidas[i];
				if (c.getTipo().equalsIgnoreCase("Terremoto")) {
					writer.write(c.getTipo() + ";" + c.getConsumidor() + ";" + c.getLitros() + ";" + c.getFecha() + "\n");
				} else {
					writer.write(c.getTipo() + ";" + c.getConsumidor() + ";" + c.getLargo() + ";" + c.getAncho() + ";" + c.getFecha() + "\n");
				}
			}
			writer.close();
		} catch (IOException e) {
			System.out.println(">> Error al guardar los cambios en Consumido.txt.");
		}
	}

	private static void opcion3() {
        // TODO: Implementar métricas
    }

    private static void opcion2() {
    	
    	
        // hoferziño acá armé el submenú de actualización para que el usuario 
        
        int opcionSub = 0;
        do {
            System.out.println("========== ACTUALIZAR REGISTROS ============");
            System.out.println("  [1] Agregar");
            System.out.println("  [2] Editar");
            System.out.println("  [3] Eliminar");
            System.out.println("  [4] Volver al menu principal");
            System.out.println("============================================");
            System.out.print("Seleccione una opcion: ");
            
            String entrada = scanner.nextLine().trim();
            if (!entrada.matches("[1-4]")) {
                System.out.println(">> Error: la opcion debe ser un numero entre 1 y 4. Intente nuevamente.");
                continue;
            }
            opcionSub = Integer.parseInt(entrada);

            if (opcionSub == 1) {
                agregarRegistro();
            } else if (opcionSub == 2) {
                editarRegistro();
            } else if (opcionSub == 3) {
                eliminarRegistro();
            }
        } while (opcionSub != 4);
    }

    private static void agregarRegistro() {
        if (CantComida >= 300) {
            System.out.println(">> Error: se alcanzo la capacidad maxima de registros (300). No es posible agregar mas.");
            return;
        }

        System.out.println("\nTipo de registro:");
        System.out.println("  [1] Choripan");
        System.out.println("  [2] Terremoto");
        System.out.println("  [3] Cancelar");
        System.out.print("Seleccione una opcion: ");
        
        String tipoOpt = scanner.nextLine().trim();
        if (!tipoOpt.matches("[1-3]")) {
            System.out.println(">> Error: opcion invalida.");
            return;
        }
        int t = Integer.parseInt(tipoOpt);
        if (t == 3) return;

        if (CantParticipantes == 0) {
            System.out.println(">> Error: No hay participantes cargados.");
            return;
        }

        System.out.println("\nConsumidores disponibles:");
        for (int i = 0; i < CantParticipantes; i++) {
            System.out.println("  [" + (i + 1) + "] " + Participantes[i]);
        }
        System.out.print("Seleccione el consumidor (numero): ");
        String partOpt = scanner.nextLine().trim();
        
        try {
            int pIndex = Integer.parseInt(partOpt) - 1;
            if (pIndex < 0 || pIndex >= CantParticipantes) {
                System.out.println(">> Error: consumidor no valido.");
                return;
            }
            String consumidor = Participantes[pIndex];

            if (t == 1) { // Choripán
                System.out.print("Largo del choripan (cm): ");
                int largo = Integer.parseInt(scanner.nextLine().trim());
                if (largo <= 0) {
                    System.out.println(">> Error: el largo debe ser mayor a 0.");
                    return;
                }

                System.out.print("Ancho del choripan (cm): ");
                int ancho = Integer.parseInt(scanner.nextLine().trim());
                if (ancho <= 0) {
                    System.out.println(">> Error: el ancho debe ser mayor a 0.");
                    return;
                }

                System.out.print("Fecha (YYYY-MM-DD): ");
                
                
                String fecha = scanner.nextLine().trim();

                // hoferziño acá guardo el nuevo choripán 
                
                
                
                
                comidas[CantComida] = new Comida("Choripan", consumidor, largo, ancho, fecha);
                CantComida++;
                guardarArchivo();
                
                
                
                System.out.println(">> Registro agregado correctamente y guardado en Consumido.txt\n");

            } else if (t == 2) { // Terremoto
                System.out.print("Litros del terremoto: ");
                double litros = Double.parseDouble(scanner.nextLine().trim());
                if (litros <= 0) {
                    System.out.println(">> Error: los litros deben ser mayores a 0.");
                    return;
                }

                System.out.print("Fecha (YYYY-MM-DD): ");
                String fecha = scanner.nextLine().trim();

                // hoferziño acá guardo el nuevo terremoto
                
                
                
                comidas[CantComida] = new Comida("Terremoto", consumidor, litros, fecha);
                CantComida++;
                guardarArchivo();
                System.out.println(">> Registro agregado correctamente y guardado en Consumido.txt\n");
            }

        } catch (NumberFormatException e) {
            System.out.println(">> Error: formato de numero invalido.");
        }
    }

    private static void editarRegistro() {
        System.out.println("\nTipo de registro a editar:");
        System.out.println("  [1] Choripan");
        System.out.println("  [2] Terremoto");
        System.out.println("  [3] Cancelar");
        System.out.print("Seleccione una opcion: ");
        
        String tipoOpt = scanner.nextLine().trim();
        if (!tipoOpt.matches("[1-3]")) {
            System.out.println(">> Error: opcion invalida.");
            return;
        }
        int t = Integer.parseInt(tipoOpt);
        if (t == 3) return;

        String tipoBuscado = (t == 1) ? "Choripan" : "Terremoto";

        System.out.println("\n  #   Consumidor    Detalles");
        int[] indicesReales = new int[CantComida];
        int contadorMostrados = 1;

        // hoferziño acá filtro los registros 
        // y guardo la posición real en el arreglo grande usando una lista que cree
        for (int i = 0; i < CantComida; i++) {
        	
        	
        	
            if (comidas[i].getTipo().equalsIgnoreCase(tipoBuscado)) {
                indicesReales[contadorMostrados] = i;
                
                
                
                if (tipoBuscado.equals("Choripan")) {
                    System.out.println("  " + contadorMostrados + "   " + comidas[i].getConsumidor() + "       " + comidas[i].getLargo() + "cm x " + comidas[i].getAncho() + "cm   " + comidas[i].getFecha());
                } else {
                	
                	
                	
                    System.out.println("  " + contadorMostrados + "   " + comidas[i].getConsumidor() + "       " + comidas[i].getLitros() + "L   " + comidas[i].getFecha());
                }
                contadorMostrados++;
            }
        }

        if (contadorMostrados == 1) {
            System.out.println(">> No hay registros de este tipo para editar.");
            return;
        }

        System.out.print("\nSeleccione el registro a editar (0 para cancelar): ");
        try {
            int seleccion = Integer.parseInt(scanner.nextLine().trim());
            if (seleccion == 0) return;

            if (seleccion < 1 || seleccion >= contadorMostrados) {
                System.out.println(">> Error: no existe un registro con ese numero.");
                return;
            }

            int indexReal = indicesReales[seleccion];
            
            
            Comida actual = comidas[indexReal];

            System.out.println("Registro actual -> " + actual.getTipo() + " | " + actual.getConsumidor() + " | Fecha: " + actual.getFecha());
            System.out.println("Deje el campo vacio para mantener el valor actual.");

            if (tipoBuscado.equals("Choripan")) {
            	
            	
                System.out.print("Consumidor [" + actual.getConsumidor() + "]: ");
                String nuevoCons = scanner.nextLine().trim();
                
                
                if (nuevoCons.isEmpty()) nuevoCons = actual.getConsumidor();

                System.out.print("Largo [" + actual.getLargo() + "]: ");
                String nuevoLargoStr = scanner.nextLine().trim();
                
                
                int largo = nuevoLargoStr.isEmpty() ? actual.getLargo() : Integer.parseInt(nuevoLargoStr);

                System.out.print("Ancho [" + actual.getAncho() + "]: ");
                
                
                String nuevoAnchoStr = scanner.nextLine().trim();
                int ancho = nuevoAnchoStr.isEmpty() ? actual.getAncho() : Integer.parseInt(nuevoAnchoStr);

                System.out.print("Fecha [" + actual.getFecha() + "]: ");
                String fecha = scanner.nextLine().trim();
                if (fecha.isEmpty()) fecha = actual.getFecha();

                comidas[indexReal] = new Comida("Choripan", nuevoCons, largo, ancho, fecha);

            } else {
                System.out.print("Consumidor [" + actual.getConsumidor() + "]: ");
                String nuevoCons = scanner.nextLine().trim();
                if (nuevoCons.isEmpty()) nuevoCons = actual.getConsumidor();

                System.out.print("Litros [" + actual.getLitros() + "]: ");
                String nuevosLitrosStr = scanner.nextLine().trim();
                double litros = nuevosLitrosStr.isEmpty() ? actual.getLitros() : Double.parseDouble(nuevosLitrosStr);

                System.out.print("Fecha [" + actual.getFecha() + "]: ");
                
                
                String fecha = scanner.nextLine().trim();
                if (fecha.isEmpty()) fecha = actual.getFecha();

                comidas[indexReal] = new Comida("Terremoto", nuevoCons, litros, fecha);
            }

            // hoferziño acá guardo los cambios en el archibo
            
            
            guardarArchivo();
            System.out.println(">> Registro actualizado correctamente y guardado en Consumido.txt\n");

        } catch (NumberFormatException e) {
            System.out.println(">> Error en el formato de entrada.");
        }
    }

    private static void eliminarRegistro() {
        System.out.println("\nTipo de registro a eliminar:");
        System.out.println("  [1] Choripan");
        System.out.println("  [2] Terremoto");
        System.out.println("  [3] Cancelar");
        System.out.print("Seleccione una opcion: ");
        
        String tipoOpt = scanner.nextLine().trim();
        if (!tipoOpt.matches("[1-3]")) {
            System.out.println(">> Error: opcion invalida.");
            return;
        }
        int t = Integer.parseInt(tipoOpt);
        
        
        
        if (t == 3) return;

        String tipoBuscado = (t == 1) ? "Choripan" : "Terremoto";

        System.out.println("\n  #   Consumidor    Detalles");
        
        
        
        int[] indicesReales = new int[CantComida];
        int contadorMostrados = 1;

        for (int i = 0; i < CantComida; i++) {
        	
        	
        	
            if (comidas[i].getTipo().equalsIgnoreCase(tipoBuscado)) {
            	
            	
                indicesReales[contadorMostrados] = i;
                if (tipoBuscado.equals("Choripan")) {
                	
                	
                	
                    System.out.println("  " + contadorMostrados + "   " + comidas[i].getConsumidor() + "       " + comidas[i].getLargo() + "cm x " + comidas[i].getAncho() + "cm   " + comidas[i].getFecha());
                } else {
                	
                	
                	
                    System.out.println("  " + contadorMostrados + "   " + comidas[i].getConsumidor() + "       " + comidas[i].getLitros() + "L   " + comidas[i].getFecha());
                }
                contadorMostrados++;
            }
        }

        if (contadorMostrados == 1) {
            System.out.println(">> No hay registros de este tipo para eliminar.");
            return;
        }

        System.out.print("\nSeleccione el registro a eliminar (0 para cancelar): ");{
        	
        	
        	
        }
        try {
            int seleccion = Integer.parseInt(scanner.nextLine().trim());
            if (seleccion == 0) return;

            if (seleccion < 1 || seleccion >= contadorMostrados) {
            	
            	
            	
                System.out.println(">> Error: no existe un registro con ese numero.");
                return;
            }

            int indexReal = indicesReales[seleccion];
            Comida aEliminar = comidas[indexReal];

            System.out.print("Confirma la eliminacion de \"" + aEliminar.getTipo() + " | " + aEliminar.getConsumidor() + " | " + aEliminar.getFecha() + "\"? (s/n): ");
            
            
            
            
            String confirmacion = scanner.nextLine().trim();

            if (confirmacion.equalsIgnoreCase("s")) {
            	
            	
                // hoferziño acá muevo todos los elementos a la izquierda para rellenar 
                // la caga de espacio del registro 
            	
            	
                for (int i = indexReal; i < CantComida - 1; i++) {
                    comidas[i] = comidas[i + 1];
                }
                comidas[CantComida - 1] = null;
                CantComida--;

                // hoferziñ acá actualizo el archivo con el elemento ya eliminado
                guardarArchivo();
                
                System.out.println(">> Registro eliminado correctamente. Cambios guardados en Consumido.txt\n");
            } else {
            	
            	
            	
                System.out.println(">> Operacion cancelada.\n");
            }

        } catch (NumberFormatException e) {
        	
        	
        	
            System.out.println(">> Error en el formato de entrada.");
        }
    }

    private static void opcion1() {
    	System.out.println("============ VER REGISTROS =================");
    	System.out.println("1) Choripanes.");
    	System.out.println("2) Terremotos.");
    	System.out.println("3) Volver al menu principal.");
    	System.out.print("Seleccione una opcion: ");
    	String ent = scanner.nextLine().trim();
    	if (!ent.matches("[1-3]")) {
    	    System.out.println(">> Opcion invalida.");
    	    return;
    	}
    	int opcion = Integer.parseInt(ent);
    	
    	if(opcion == 1) {
    		System.out.println("=============== CHORIPANES =================");
    		System.out.println("#   Consumidor    Largo   Ancho   Fecha           KCal");
    		int cont = 1;
    		for (int i = 0 ; i < CantComida; i++) {
    			if(comidas[i].getTipo().equalsIgnoreCase("Choripan")){
        			System.out.println(cont + "      " + 
    			comidas[i].getConsumidor() + "       " + comidas[i].getLargo()
    			+ "      " + comidas[i].getAncho() + "       " + comidas[i].getFecha() 
    			+ "        " + comidas[i].getKcal());
        		cont++;
        		}
    		}
    		System.out.println("Total: " + (cont - 1) + " choripanes registrados");
    	}
    	if(opcion == 2) {
    		System.out.println("=============== TERREMOTOS =================");
    		System.out.println("#   Consumidor    Litros   Fecha          KCal");
    		int cont = 1;
    		double contLitros = 0;
    		for ( int i = 0 ; i < CantComida ; i++) {
    			if(comidas[i].getTipo().equalsIgnoreCase("Terremoto")) {
    				System.out.println(cont +"   " + comidas[i].getConsumidor()
    						+ "       " + comidas[i].getLitros() + "      " + 
    						comidas[i].getFecha() + "     " + comidas[i].getKcal());
    				cont++;
    				contLitros += comidas[i].getLitros();
    			}
    		}
    		System.out.println("Total: " + (cont - 1) + " Terremotos contados ("+
        			contLitros + "L)");
    	}
    }

    private static int menu() {
        System.out.println("============================================");
        System.out.println("           LA FONDA DEL PAPU                ");
        System.out.println("============================================");
        System.out.println("	REGISTROS CARGADOS: " + CantComida +"   ");
        System.out.println("1) Ver registros.");
        System.out.println("2) Actualizar registros.");
        System.out.println("3) Calcular metricas.");
        System.out.println("4) Salir.");
        System.out.print("Seleccione una opcion: ");

        String entrada = scanner.nextLine().trim();

        while (!entrada.matches("[1-4]")) {
            System.out.println(">> Error: la opcion debe ser un numero entre 1 y 4. Intente nuevamente.");
            System.out.print("Seleccione una opcion: ");
            entrada = scanner.nextLine().trim();
        }

        return Integer.parseInt(entrada);
    }
}
