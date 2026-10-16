//Martin Hofer - 22.368.055-0 - ICCI
//Vicente Jara - 22.108.526-4 - ICCI
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

    private static boolean validarFecha(String fecha) {
        if (!fecha.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return false;
        }

        int anio = Integer.parseInt(fecha.substring(0, 4));
        if (anio < 1) {
            return false;
        }
        int mes = Integer.parseInt(fecha.substring(5, 7));
        int dia = Integer.parseInt(fecha.substring(8, 10));

        if (mes < 1 || mes > 12) {
            return false;
        }

        int diasMes = 31;

        if (mes == 4 || mes == 6 || mes == 9 || mes == 11) {
            diasMes = 30;
        } else if (mes == 2) {
            diasMes = 28;

            if (anio % 400 == 0 || (anio % 4 == 0 && anio % 100 != 0)) {
                diasMes = 29;
            }
        }

        if (dia < 1 || dia > diasMes) {
            return false;
        }

        return true;
    }
    


	private static boolean participanteExiste(String nombre) {
	    for (int i = 0; i < CantParticipantes; i++) {
	        if (Participantes[i].equalsIgnoreCase(nombre)) {
	            return true;
	        }
	    }
	
	    return false;
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
			if (tipoComida.equalsIgnoreCase("TERREMOTO") && partes.length == 4) {
			    try {
			        String consumidor = partes[1];
			        double litros = Double.parseDouble(partes[2]);
			        String fecha = partes[3];
			        if (consumidor.trim().isEmpty()) {
			            System.out.println(">> Registro sin consumidor: " + linea);
			            continue;
			        }
			        if (!participanteExiste(consumidor)) {
			            System.out.println(">> Consumidor no registrado: " + linea);
			            continue;
			        }
			        if (litros <= 0 || !validarFecha(fecha)) {
			            System.out.println(">> Registro invalido: " + linea);
			            continue;
			        }
			        if (CantComida >= comidas.length) {
			            System.out.println(">> Se alcanzo el maximo de registros.");
			            break;
			        }
			        comidas[CantComida] = new Comida(tipoComida, consumidor, litros, fecha);
			        CantComida++;
			    } catch (NumberFormatException e) {
			        System.out.println(">> Numero invalido en el registro: " + linea);
			    }
			}
			else if (tipoComida.equalsIgnoreCase("CHORIPAN") && partes.length == 5) {
			    try {
			        String consumidor = partes[1];
			        int largo = Integer.parseInt(partes[2]);
			        int ancho = Integer.parseInt(partes[3]);
			        String fecha = partes[4];
			        if (consumidor.trim().isEmpty()) {
			            System.out.println(">> Registro sin consumidor: " + linea);
			            continue;
			        }
			        if (!participanteExiste(consumidor)) {
			            System.out.println(">> Consumidor no registrado: " + linea);
			            continue;
			        }
			        if (largo <= 0 || ancho <= 0 || !validarFecha(fecha)) {
			            System.out.println(">> Registro invalido: " + linea);
			            continue;
			        }
			        if (CantComida >= comidas.length) {
			            System.out.println(">> Se alcanzo el maximo de registros.");
			            break;
			        }
			        comidas[CantComida] = new Comida(tipoComida, consumidor, largo, ancho, fecha);
			        CantComida++;
			    } catch (NumberFormatException e) {
			        System.out.println(">> Numero invalido en el registro: " + linea);
			    }
			}	

			else {
				System.out.println("Línea ignorada por formato incorrecto: " + linea);
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
			if (!linea.isEmpty()) {
			    if (CantParticipantes < Participantes.length) {
			        Participantes[CantParticipantes] = linea;
			        CantParticipantes++;
			    } else {
			        System.out.println(">> Se alcanzo el maximo de participantes.");
			        break;
			    }
			}
		}
		sFile.close();
	}


	private static boolean guardarArchivo() {
	    try (FileWriter writer = new FileWriter("Consumido.txt")) {
	        for (int i = 0; i < CantComida; i++) {
	            Comida c = comidas[i];
	
	            if (c.getTipo().equalsIgnoreCase("Terremoto")) {
	                writer.write(c.getTipo() + ";" + c.getConsumidor() + ";"
	                        + c.getLitros() + ";" + c.getFecha() + "\n");
	            } else {
	                writer.write(c.getTipo() + ";" + c.getConsumidor() + ";"
	                        + c.getLargo() + ";" + c.getAncho() + ";"
	                        + c.getFecha() + "\n");
	            }
	        }
	
	        return true;
	
	    } catch (IOException e) {
	        System.out.println(">> Error al guardar los cambios en Consumido.txt.");
	        return false;
	    }
	}



	private static void opcion3() {
	    int opcionSub = 0;
	
	    do {
	        System.out.println("\n========== CALCULAR METRICAS =============");
	        System.out.println("[1] Choripanes comidos en total");
	        System.out.println("[2] Terremotos tomados en total");
	        System.out.println("[3] Choripanes por usuario");
	        System.out.println("[4] Terremotos por usuario");
	        System.out.println("[5] KCal consumidas por usuario");
	        System.out.println("[6] Dias ebrio por usuario");
	        System.out.println("[7] Volver al menu principal");
	        System.out.println("==========================================");
	        System.out.print("Seleccione una opcion: ");
	
	        String entrada = scanner.nextLine().trim();
	
	        if (!entrada.matches("[1-7]")) {
	            System.out.println(">> Error: ingrese un numero entre 1 y 7.");
	            continue;
	        }
	
	        opcionSub = Integer.parseInt(entrada);
	
	        if (opcionSub == 1) {
	            int total = 0;
	
	            for (int i = 0; i < CantComida; i++) {
	                if (comidas[i].getTipo().equalsIgnoreCase("Choripan")) {
	                    total++;
	                }
	            }
	
	            System.out.println("Total de choripanes comidos: " + total);
	
	        } else if (opcionSub == 2) {
	            int total = 0;
	            double litrosTotal = 0;
	
	            for (int i = 0; i < CantComida; i++) {
	                if (comidas[i].getTipo().equalsIgnoreCase("Terremoto")) {
	                    total++;
	                    litrosTotal += comidas[i].getLitros();
	                }
	            }
	
	            System.out.println("Total de terremotos tomados: " + total);
	            System.out.printf("Litros consumidos en total: %.2f L%n",
	                    litrosTotal);
	
	        } else if (opcionSub == 3) {
	            System.out.println("\nCHORIPANES POR USUARIO");
	
	            for (int p = 0; p < CantParticipantes; p++) {
	                int cantidad = 0;
	
	                for (int i = 0; i < CantComida; i++) {
	                    if (comidas[i].getTipo().equalsIgnoreCase("Choripan")
	                            && comidas[i].getConsumidor().equalsIgnoreCase(Participantes[p])) {
	                        cantidad++;
	                    }
	                }
	
	                System.out.println(Participantes[p] + ": " + cantidad);
	            }
	
	        } else if (opcionSub == 4) {
	            System.out.println("\nTERREMOTOS POR USUARIO");
	
	            for (int p = 0; p < CantParticipantes; p++) {
	                int cantidad = 0;
	                double litros = 0;
	
	                for (int i = 0; i < CantComida; i++) {
	                    if (comidas[i].getTipo().equalsIgnoreCase("Terremoto")
	                            && comidas[i].getConsumidor().equalsIgnoreCase(Participantes[p])) {
	                        cantidad++;
	                        litros += comidas[i].getLitros();
	                    }
	                }
	
	                System.out.printf("%s: %d terremotos, %.2f L%n",
	                        Participantes[p], cantidad, litros);
	            }
	
	        } else if (opcionSub == 5) {
	            System.out.println("\nKCAL CONSUMIDAS POR USUARIO");
	
	            double totalFonda = 0;
	
	            for (int p = 0; p < CantParticipantes; p++) {
	                double kcalChoripan = 0;
	                double kcalTerremoto = 0;
	
	                for (int i = 0; i < CantComida; i++) {
	                    if (comidas[i].getConsumidor().equalsIgnoreCase(Participantes[p])) {
	                        if (comidas[i].getTipo().equalsIgnoreCase("Choripan")) {
	                            kcalChoripan += comidas[i].getKcal();
	                        } else if (comidas[i].getTipo().equalsIgnoreCase("Terremoto")) {
	                            kcalTerremoto += comidas[i].getKcal();
	                        }
	                    }
	                }
	
	                double totalUsuario = kcalChoripan + kcalTerremoto;
	                totalFonda += totalUsuario;
	
	                System.out.printf(
	                        "%s: Choripan %.2f | Terremoto %.2f | Total %.2f kcal%n",
	                        Participantes[p], kcalChoripan,
	                        kcalTerremoto, totalUsuario);
	            }
	
	            System.out.printf("Consumo total de la fonda: %.2f kcal%n",
	                    totalFonda);
	
	        } else if (opcionSub == 6) {
	            System.out.println("\nDIAS EBRIO POR USUARIO");
	            System.out.println("(Se cuentan 2 litros o mas en un mismo dia)");
	
	            for (int p = 0; p < CantParticipantes; p++) {
	                int diasEbrio = 0;
	
	                System.out.println("\nUsuario: " + Participantes[p]);
	
	                for (int i = 0; i < CantComida; i++) {
	                    if (comidas[i].getTipo().equalsIgnoreCase("Terremoto")
	                            && comidas[i].getConsumidor().equalsIgnoreCase(Participantes[p])) {
	
	                        String fecha = comidas[i].getFecha();
	                        boolean fechaRevisada = false;
	
	                        // Revisar si ya contamos esta fecha
	                        for (int j = 0; j < i; j++) {
	                            if (comidas[j].getTipo().equalsIgnoreCase("Terremoto")
	                                    && comidas[j].getConsumidor().equalsIgnoreCase(Participantes[p])
	                                    && comidas[j].getFecha().equals(fecha)) {
	                                fechaRevisada = true;
	                                break;
	                            }
	                        }
	
	                        if (!fechaRevisada) {
	                            double litrosDia = 0;
	
	                            // Sumar todos los terremotos de ese dia
	                            for (int j = 0; j < CantComida; j++) {
	                                if (comidas[j].getTipo().equalsIgnoreCase("Terremoto")
	                                        && comidas[j].getConsumidor().equalsIgnoreCase(Participantes[p])
	                                        && comidas[j].getFecha().equals(fecha)) {
	                                    litrosDia += comidas[j].getLitros();
	                                }
	                            }
	
	                            if (litrosDia >= 2) {
	                                diasEbrio++;
	
	                                System.out.printf("%s: %.2f L%n",
	                                        fecha, litrosDia);
	                            }
	                        }
	                    }
	                }
	
	                System.out.println("Total de dias ebrio: " + diasEbrio);
	            }
	        }
	
	    } while (opcionSub != 7);
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
        if (CantComida >= comidas.length) {
            System.out.println(">> Error: se alcanzo la capacidad maxima de registros (300).");
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

        if (t == 3) {
            return;
        }

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

            if (t == 1) {
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

                if (!validarFecha(fecha)) {
                    System.out.println(">> Error: fecha invalida. Use AAAA-MM-DD.");
                    return;
                }

                comidas[CantComida] =
                        new Comida("Choripan", consumidor, largo, ancho, fecha);
                CantComida++;

                if (guardarArchivo()) {
                    System.out.println(">> Registro agregado y guardado correctamente.");
                } else {
                    CantComida--;
                    comidas[CantComida] = null;
                    System.out.println(">> Error al guardar. Se cancelo el registro.");
                }

            } else if (t == 2) {
                System.out.print("Litros del terremoto: ");
                double litros = Double.parseDouble(scanner.nextLine().trim());

                if (litros <= 0) {
                    System.out.println(">> Error: los litros deben ser mayores a 0.");
                    return;
                }

                System.out.print("Fecha (YYYY-MM-DD): ");
                String fecha = scanner.nextLine().trim();

                if (!validarFecha(fecha)) {
                    System.out.println(">> Error: fecha invalida. Use AAAA-MM-DD.");
                    return;
                }

                comidas[CantComida] =
                        new Comida("Terremoto", consumidor, litros, fecha);
                CantComida++;

                if (guardarArchivo()) {
                    System.out.println(">> Registro agregado y guardado correctamente.");
                } else {
                    CantComida--;
                    comidas[CantComida] = null;
                    System.out.println(">> Error al guardar. Se cancelo el registro.");
                }
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
        int[] indicesReales = new int[CantComida + 1];
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
				
				if (!participanteExiste(nuevoCons)) {
				    System.out.println(">> Error: el participante no existe.");
				    return;
				}


                System.out.print("Largo [" + actual.getLargo() + "]: ");
                String nuevoLargoStr = scanner.nextLine().trim();
                
                
                int largo = nuevoLargoStr.isEmpty() ? actual.getLargo() : Integer.parseInt(nuevoLargoStr);
                if (largo <= 0) {
                    System.out.println(">> Error: el largo debe ser mayor a 0.");
                    return;
                }
                
                System.out.print("Ancho [" + actual.getAncho() + "]: ");
                
                
                String nuevoAnchoStr = scanner.nextLine().trim();
                int ancho = nuevoAnchoStr.isEmpty() ? actual.getAncho() : Integer.parseInt(nuevoAnchoStr);
                if (ancho <= 0) {
                    System.out.println(">> Error: el ancho debe ser mayor a 0.");
                    return;
                }
                
                System.out.print("Fecha [" + actual.getFecha() + "]: ");
                String fecha = scanner.nextLine().trim();
                if (fecha.isEmpty()) fecha = actual.getFecha();
                if (!validarFecha(fecha)) {
                    System.out.println(">> Error: fecha invalida. Use AAAA-MM-DD.");
                    return;
                }
                comidas[indexReal] = new Comida("Choripan", nuevoCons, largo, ancho, fecha);

            } else {
                System.out.print("Consumidor [" + actual.getConsumidor() + "]: ");
                String nuevoCons = scanner.nextLine().trim();
                if (nuevoCons.isEmpty()) nuevoCons = actual.getConsumidor();

				if (!participanteExiste(nuevoCons)) {
				    System.out.println(">> Error: el participante no existe.");
				    return;
				}

                System.out.print("Litros [" + actual.getLitros() + "]: ");
                String nuevosLitrosStr = scanner.nextLine().trim();
                double litros = nuevosLitrosStr.isEmpty() ? actual.getLitros() : Double.parseDouble(nuevosLitrosStr);
                if (litros <= 0) {
                    System.out.println(">> Error: los litros deben ser mayores a 0.");
                    return;
                }
                

				System.out.print("Fecha [" + actual.getFecha() + "]: ");
				String fecha = scanner.nextLine().trim();
				
				if (fecha.isEmpty()) {
				    fecha = actual.getFecha();
				}
				
				if (!validarFecha(fecha)) {
				    System.out.println(">> Error: fecha invalida. Use AAAA-MM-DD.");
				    return;
				}

                comidas[indexReal] = new Comida("Terremoto", nuevoCons, litros, fecha);
            }

            // hoferziño acá guardo los cambios en el archibo
            
            
            if (guardarArchivo()) {
                System.out.println(">> Registro actualizado y guardado correctamente.");
            } else {
                comidas[indexReal] = actual;
                System.out.println(">> Error al guardar. Se restauro el registro anterior.");
            }

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
        
        
        
        int[] indicesReales = new int[CantComida + 1];
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
			    Comida eliminado = comidas[indexReal];
			
			    for (int i = indexReal; i < CantComida - 1; i++) {
			        comidas[i] = comidas[i + 1];
			    }
			
			    comidas[CantComida - 1] = null;
			    CantComida--;
			
			    if (guardarArchivo()) {
			        System.out.println(">> Registro eliminado y guardado correctamente.");
			    } else {
			        for (int i = CantComida; i > indexReal; i--) {
			            comidas[i] = comidas[i - 1];
			        }
			
			        comidas[indexReal] = eliminado;
			        CantComida++;
			
			        System.out.println(">> Error al guardar. Se restauro el registro eliminado.");
			    }
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
