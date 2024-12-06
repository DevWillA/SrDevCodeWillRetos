package simuladorInterplanetario;

import java.util.Scanner;

public class SimuladorInterplanetario {

    private static double fuelReerva = 0;
    private static double oxigenoReserva = 0;
    private static double fuelCosumido = 0;
    private static double oxigenoCosumido = 0;
    private static double fuel;
    private static double oxigeno;
    private static boolean aumentoRecursos;
    private static boolean naveFallandoGasolina = false;
    private static boolean naveFallandoOxigeno = false;

    public static void startGame() {
        var sc = new Scanner(System.in);

        int destino;
        int nave;
        int eventosSubitos;

        // Inicimos el programa
        System.out.println("""       
                            .   *     o     .
           O   .  *    o .   .    O   .  *    o  *  o     *    .   o
            Hola Bienvenido al Simulador de Vuelo interplanetario
              o   *    .    * o   *   .     *   . *   .   o   *  o    *
                            .  *  o    .   *
                """);
        do {
            String[] menu1 = menuInicial();

            for (String mensaje : menu1) {
                System.out.println(mensaje);
                mensajesStop1();
            }

            destino = sc.nextInt();
            sc.nextLine(); // Limpia el buffer

            if (destino < 1 || destino > 7) {
                System.out.println("Destino no válido");
            }
        } while (destino < 1 || destino > 7);

        mensajesStop1();
        // Despues de seleccionar el destino, nos pide el nave
        do {
            String[] secuencia = menuNaves(destino);

            for (String mensaje : secuencia) {
                System.out.println(mensaje);
                mensajesStop1();
            }

            nave = sc.nextInt();
            sc.nextLine(); // Limpia el buffer

            if (nave < 1 || nave > 3) {
                System.out.println("Nave no válida");
            }
        } while (nave < 1 || nave > 3);

        mensajesStop1();

        int cantidadTripulantes;

        // Despues de seleccionar la nave, nos pide la cantidad de personas

        System.out.println("Vamos a " + planetSelect(destino) + " con una nave " + naveSelect(nave));
        mensajesStop1();
        do {
            System.out.println("Este viaje se realizará con cuántas personas?");
            cantidadTripulantes = sc.nextInt();
            if (cantidadTripulantes < 1 || cantidadTripulantes > capacidadNave(nave)) {
                System.out.println("Capacidad no válida. Intente nuevamente.");
            }
        } while (cantidadTripulantes < 1 || cantidadTripulantes > capacidadNave(nave));

        mensajesStop1();

        // Despues de seleccionar la cantidad de personas, calculamos los eventos
        // aleatorios

        eventosSubitos = calcularEventosAleatorios(distanceKM(destino));
        int eventosCompletados = 0;

        // Mostramos el tiempo y distancia

        System.out.println(calculateDistanceAndTime(destino, nave));
        mensajesStop1();
        // Asignamos los recursos iniciales

        fuel = calculateFuel(destino, nave);
        oxigeno = calculateOxigen(destino, nave, cantidadTripulantes);

        int opcion;
        aumentoRecursos = false;

        // Ingresamos al segundo menu

        do {
            String[] menu2 = menuSecond();

            for (String mensaje : menu2) {
                System.out.println(mensaje);
                mensajesStop1();
            }

            while (!sc.hasNextInt()) {
                System.out.println("Por favor, ingrese un número válido.");
                sc.next(); // Limpiar entrada no válida
            }
            opcion = sc.nextInt();
            sc.nextLine(); // Limpia el buffer.

            switch (opcion) {
                case 1:
                    if (!aumentoRecursos) {
                        System.out
                                .println("La cantidad de combustible necesario es: " + fuel + " Kilogramos");

                        mensajesStop1();

                        System.out.println("La cantidad de oxigeno necesario para " + cantidadTripulantes
                                + " personas es: " + oxigeno + " Kilogramos");

                        mensajesStop1();

                        System.out.println("Es recomendable aumentar los recursos de la nave para evitar imprevistos");

                        mensajesStop1();

                    } else {
                        System.out.println("La cantidad de combustible es: " + fuel
                                + " Kilogramos y la reserva de combustible es: " + fuelReerva + " Kilogramos");
                        mensajesStop1();

                        System.out.println("La cantidad de oxigeno necesario para " + cantidadTripulantes
                                + " personas es: " + oxigeno + " Kilogramos y la reserva de oxigeno es: "
                                + oxigenoReserva + " Kilogramos");
                        mensajesStop1();

                    }
                    break;
                case 2:
                    calculateAumentoRecursos(sc, cantidadTripulantes);
                    aumentoRecursos = true;
                    break;
                case 3:
                    System.out.println("La nave se encuentra en optimas condiciones");
                    break;
                case 4:
                    String[] secuencia = secuenciaDespegue();

                    for (String mensaje : secuencia) {
                        System.out.println(mensaje);
                        mensajesStop1();
                    }
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }

        } while (opcion != 4);

        // Cuando inciamos el despegue de la nave, empezamos a contar las etapas

        for (int etapa = 1; etapa <= 10; etapa++) {

            boolean etapaValida = consultarEtapa(etapa, eventosCompletados, eventosSubitos);

            if (fuelCosumido > fuel) {

                mensajeMuerto();
                break;

            }

            if (oxigenoCosumido > oxigeno) {

                mensajeMuerto();
                break;

            }

            // Verifica si los recursos son suficientes para continuar con la etapa
            if (!validarRecursos((fuel - fuelCosumido), (oxigeno - oxigenoCosumido))) {
                break; // Si los recursos no son suficientes, termina el viaje
            }

            if (!etapaValida) {

                // Validamos al azar si se presentara un evento subito
                int randomValue = (Math.random() < 0.5) ? 1 : 2;

                // Validamos al azar si el evento es falla del sistema o asteroides
                if (randomValue == 1) {
                    eventoSubitoFallaSistema(sc);
                } else {
                    eventoSubitoAsteroides(sc);
                }
            }

            // Mostramos el mensaje de salida de la nave
            if (etapa == 1) {

                System.out.println("Salimos de la Tierra");
                System.out.println("Precione enter para continuar");
                sc.nextLine();
            }

            System.out.println(naveVolando(etapa));
            

            // Validamos si es necesario las reservas de combustible y oxígeno
            consumirReservaCombustible();
            consumirReservaOxigeno();

            mensajesStop1();

            System.out.println("El proceso del viaje es " + etapa + "0%");

            mensajesStop1();

            // Validamos si la nave tiene una perdida de combustible
            if (naveFallandoGasolina) {
                // Si la nave está fallando, se pierde un 5% de combustible.
                fuelCosumido += (fuel / 10) + (fuel * 0.05);
                System.out.println("La nave está fallando y esta perdiendo un 5% de combustible.");
                mensajesStop1();
            } else {
                fuelCosumido += (fuel / 10);
            }

            // Validamos si la nave tiene una perdida de oxígeno
            if (naveFallandoOxigeno) {
                // Si la nave está fallando, se pierde un 5% de oxígeno.
                oxigenoCosumido += (oxigeno / 10) + (oxigeno * 0.05);
                System.out.println("La nave está fallando y esta perdiendo un 5% de oxigeno.");
                mensajesStop1();
            } else {
                oxigenoCosumido += (oxigeno / 10);
            }

            // Calculamos el porcentaje de combustible y oxígeno que quedan
            double porcentajeCombustible = ((fuel - fuelCosumido) / fuel) * 100;
            double porcentajeOxigeno = ((oxigeno - oxigenoCosumido) / oxigeno) * 100;

            if (fuelCosumido > fuel) {
                mensajeMuerto();
                break;
            } else {
                System.out.println("El combustible consumido es " + fuelCosumido + " kilogramos. Aun nos quedan "
                        + porcentajeCombustible + "% de combustible.");
            }
            mensajesStop1();
            if (oxigenoCosumido > oxigeno) {
                mensajeMuerto();
                break;
            } else {
                System.out.println("El oxigeno consumido es " + oxigenoCosumido + " kilogramos. Aun nos quedan "
                        + porcentajeOxigeno + "% de oxigeno.");
            }
            mensajesStop1();
            // System.out.println("Precione enter para continuar");

        }

        // Validamos si comcluyo el proceso con exito
        if (!validarRecursos((fuel - fuelCosumido), (oxigeno - oxigenoCosumido))) {
            System.out.println("Mision abortada, no hay suficiente combustible o oxígeno para continuar");
        } else {
            System.out.println("Proceso completado, llegamos a " + planetSelect(destino));
            mensajeLlegaste();
        }
        sc.close();
    }

    // Retornos de mensajes asccii
    private static void mensajeLlegaste() {
        System.out.println("""
                  L       L        EEEEE  GGGG   AAAAA  SSSSS  TTTTT  EEEEE
                  L       L        E     G       A     A S        T    E
                  L       L        EEEE  G  GG   AAAAAAA SSSSS    T    EEEE
                  L       L        E     G   G   A     A     S    T    E
                  LLLLL   LLLLL    EEEEE GGGG    A     A SSSSS    T    EEEEE
                """);
    }

    private static String naveVolando(int count) {
        return switch (count) {
            case 1 -> """
                            _______________
                    ----   |        ___    \\
                    ---    |       |   |     \\
                    ---    |       |___|     /
                    ----   |_______________/
                    """;
            case 2 -> """
                                _______________
                    --------   |        ___    \\
                    -------    |       |   |     \\
                    -------    |       |___|     /
                    --------   |_______________/
                    """;
            case 3 -> """
                                   _______________
                    -----------   |        ___    \\
                    ----------    |       |   |     \\
                    ----------    |       |___|     /
                    -----------   |_______________/
                    """;
            case 4 -> """
                                       _______________
                    ---------------   |        ___    \\
                    --------------    |       |   |     \\
                    --------------    |       |___|     /
                    ---------------   |_______________/
                    """;
            case 5 -> """
                                           _______________
                    -------------------   |        ___    \\
                    ------------------    |       |   |     \\
                    ------------------    |       |___|     /
                    -------------------   |_______________/
                    """;
            case 6 -> """
                                               _______________
                    -----------------------   |        ___    \\
                    ----------------------    |       |   |     \\
                    ----------------------    |       |___|     /
                    -----------------------   |_______________/
                    """;
            case 7 -> """
                                                   _______________
                    ---------------------------   |        ___    \\
                    --------------------------    |       |   |     \\
                    --------------------------    |       |___|     /
                    ---------------------------   |_______________/
                    """;
            case 8 -> """
                                                       _______________
                    -------------------------------   |        ___    \\
                    ------------------------------    |       |   |     \\
                    ------------------------------    |       |___|     /
                    -------------------------------   |_______________/
                    """;
            case 9 -> """
                                                           _______________
                    -----------------------------------   |        ___    \\
                    ----------------------------------    |       |   |     \\
                    ----------------------------------    |       |___|     /
                    -----------------------------------   |_______________/
                    """;
            case 10 -> """
                                                               _______________
                    ---------------------------------------   |        ___    \\
                    --------------------------------------    |       |   |     \\
                    --------------------------------------    |       |___|     /
                    ---------------------------------------   |_______________/
                    """;
            default -> """
                        _______________
                       |        ___    \\
                       |       |   |     \\
                       |       |___|     /
                       |_______________/
                    """;

        };
    }

    private static void mensajeMuerto() {
        System.out.println("""
                  M   M  U   U  EEEEE  RRRR    TTTTT   OOO
                  MM MM  U   U  E      R   R    T    O   O
                  M M M  U   U  EEEE   RRRR     T    O   O
                  M   M  U   U  E      R  R     T    O   O
                  M   M  UUUUU  EEEEE  R   R    T     OOO
                """);
    }

    // Funcion que retrasa los mensajes de salida 1 segundo
    private static void mensajesStop1() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Funcion que valida si la nave tiene reservas de combustible y las consume de
    // ser necesario
    private static void consumirReservaCombustible() {
        if ((fuel - fuelCosumido) <= fuelReerva && fuelReerva > 0) {

            System.out.println("¡Atención! El combustible se esta agotando, se pasaran las reservas a la nave.");
            mensajesStop1();
            fuel += fuelReerva;
            fuelReerva = 0;

        }
    }

    // Funcion que valida si la nave tiene reservas de oxígeno y las consume de ser
    // necesario
    private static void consumirReservaOxigeno() {
        if ((oxigeno - oxigenoCosumido) <= oxigenoReserva && oxigenoReserva > 0) {

            System.out.println("¡Atención! El oxígeno se esta agotando, se pasaran las reservas a la nave.");
            mensajesStop1();
            oxigeno += oxigenoReserva;
            oxigenoReserva = 0;

        }
    }

    // Función se encarga de validar si los recursos (combustible y oxígeno) son
    // suficientes para la siguiente etapa.
    private static boolean validarRecursos(double fuelRestante, double oxigenoRestante) {
        if (fuelRestante <= 0) {
            System.out.println("¡Alerta! El combustible se ha agotado antes de tiempo. ¡La nave se detendrá!");
            mensajesStop1();
            return false; // Retorna false si el combustible se ha agotado
        }

        if (oxigenoRestante <= 0) {
            System.out.println("¡Alerta! El oxígeno se ha agotado antes de tiempo. ¡La nave se detendrá!");
            mensajesStop1();
            return false; // Retorna false si el oxígeno se ha agotado
        }

        return true; // Si los recursos están bien, continúa
    }

    // Función que consulta si una etapa es válida o no
    private static boolean consultarEtapa(int etapa, int eventosCompletados, int eventosSubitos) {

        if (eventosCompletados < eventosSubitos) {
            return Math.random() > 0.5; // Simula si una etapa es válida (50% de probabilidades de fallar)
        } else {
            return true;
        }
    }

    // Función que calcula el número de eventos aleatorios basados en la distancia
    private static int calcularEventosAleatorios(double distancia) {
        // A mayor distancia, mayor probabilidad de eventos
        double probabilidadEvento = Math.min(1, distancia / 5000.0);
        int eventos = 1; // Comenzamos con al menos un evento

        // Generar eventos aleatorios basados en la probabilidad
        for (int i = 0; i < 7; i++) {
            if (Math.random() < probabilidadEvento) {
                eventos++;
            }
        }

        // Limitar el número total de eventos a un máximo de 7
        return Math.min(eventos, 7);
    }

    // Funcion que determina la velocidad de una nave
    private static int velocidadNave(int nave) {
        return switch (nave) {
            case 1 -> 100000;
            case 2 -> 89000;
            case 3 -> 80000;
            default -> -1;
        };
    }

    // Función que determina la capacidad de una nave
    private static int capacidadNave(int nave) {
        return switch (nave) {
            case 1 -> 3;
            case 2 -> 2;
            case 3 -> 4;
            default -> -1;
        };
    }

    // Funcion que genera un evento subito: Falla del sistema
    private static void eventoSubitoFallaSistema(Scanner sc) {

        int opcion;
        double porcentajeOxigenoPerdido = 0.05; // 5% de oxígeno perdido por cada etapa.
        double oxigenoExtra = 0; // Para almacenar la cantidad de oxígeno perdido.

        System.out.println("""
                   +----------------------------------+
                   |  ⚠️  SYSTEM FAILURE DETECTED  ⚠️  |
                   |----------------------------------|
                   |        ERROR CODE: 503           |
                   |   CRITICAL SYSTEM MALFUNCTION    |
                   |                                  |
                   |  REBOOT IMMEDIATELY OR CONTACT   |
                   |        TECHNICAL SUPPORT         |
                   +----------------------------------+

                        [!!] SYSTEM SHUTTING DOWN...
                """);
        mensajesStop1();

        do {
            System.out.println("Evento subito: Fallo del sistema");
            mensajesStop1();
            System.out.println("Que acción tomar?");
            mensajesStop1();
            System.out.println("1. Reparar fallo");
            mensajesStop1();
            System.out.println("2. Seguir con la nave fallando");

            opcion = sc.nextInt();
            sc.nextLine(); // Limpia el buffer.

            if (opcion != 1 && opcion != 2) {
                System.out.println("Opción no válida");
                mensajesStop1();

            }
        } while (opcion != 1 && opcion != 2);

        if (opcion == 1) {
            System.out.println("Fallo reparado");
            mensajesStop1();
            naveFallandoOxigeno = false;
            System.out.println("La reparacion retrazo la nave");
            mensajesStop1();
            double tiempoReparacion = (int) (Math.random() * 100);
            System.out.println("Tiempo de reparación: " + tiempoReparacion + " minutos");
            mensajesStop1();

            // Durante la reparación, se pierde un porcentaje de oxígeno.
            oxigenoExtra = ((oxigeno - oxigenoCosumido) * porcentajeOxigenoPerdido); // 5% de oxígeno durante la
                                                                                     // reparación.
            oxigenoCosumido += oxigenoExtra; // Reducimos el oxígeno disponible.

            System.out.println("Oxígeno consumido durante la reparación: " + oxigenoExtra);
            mensajesStop1();
            System.out.println("Oxígeno restante: " + (oxigeno - oxigenoCosumido));
            mensajesStop1();
            System.out.println("Oxigeno consumido: " + oxigenoCosumido);
            mensajesStop1();
        } else {
            System.out.println("Nave fallando");
            // La nave pierde un 5% de oxígeno por el fallo.
            oxigenoExtra = (oxigeno * porcentajeOxigenoPerdido); // 5% de oxígeno perdido por fallo.
            oxigenoCosumido += oxigenoExtra; // Reducimos el oxígeno disponible.

            System.out.println("Oxígeno consumido debido al fallo: " + oxigenoExtra);
            mensajesStop1();
            System.out.println("Oxígeno restante: " + (oxigeno - oxigenoCosumido));
            mensajesStop1();
            System.out.println("Oxigeno consumido: " + oxigenoCosumido);
            mensajesStop1();

            naveFallandoOxigeno = true; // Marcamos que la nave está fallando.
        }

        consumirReservaOxigeno();

        if ((oxigeno - oxigenoCosumido) <= 0) {
            System.out.println("¡Atención! El oxígeno se ha agotado. La nave está en peligro crítico.");
            mensajesStop1();
            // Aquí podrías finalizar la misión o ejecutar alguna lógica de emergencia.
        }
    }

    private static void eventoSubitoAsteroides(Scanner sc) {
        int opcion;
        int opcion2;
        double gasolinaExtra = 0; // Para almacenar la gasolina perdida.
        double porcentajePerdida = 0.05; // El 5% de gasolina que se pierde por cada etapa de fallo.
        System.out.println("""
                   *  o     *    .   o
                     .   *     o     .
                  .   .    O   .  *    o
                   o   *   .     *   .
                 *   .   o   *  o    *
                     o   *    .    *
                  .  *  o    .   *
                """);
        mensajesStop1();
        do {
            System.out.println("Evento subito: Asteroides");
            mensajesStop1();
            System.out.println("¿Qué acción tomar?");
            mensajesStop1();
            System.out.println("1. Tomar ruta nueva");
            mensajesStop1();
            System.out.println("2. Atravesar");
            mensajesStop1();

            opcion = sc.nextInt();
            sc.nextLine(); // Limpia el buffer.

            if (opcion != 1 && opcion != 2) {
                System.out.println("Opción no válida");
                mensajesStop1();
            }
        } while (opcion != 1 && opcion != 2);

        if (opcion == 1) {
            // Si se elige atravesar asteroides, se gasta gasolina.
            gasolinaExtra = fuel * 0.05; // El gasto de gasolina por atravesar los asteroides.
            System.out.println("El evento de asteroides ha consumido: " + gasolinaExtra + " Kilogramos de gasolina.");
            mensajesStop1();
            fuelCosumido += gasolinaExtra; // Reducimos el combustible.
        } else if (opcion == 2) {
            // Si se decide atravesar los asteroides, también se puede generar un fallo.
            System.out.println("Atravesando Asteroides");
            mensajesStop1();
            boolean azar = Math.random() > 0.5; // Determina si el atravesar los asteroides es exitoso o no.

            if (azar) {
                System.out.println("Atravesando sin problemas");
                mensajesStop1();
                gasolinaExtra = fuel * 0.05; // El gasto de gasolina por atravesar los asteroides.
                System.out
                        .println("El evento de asteroides ha consumido: " + gasolinaExtra + " Kilogramos de gasolina.");
                mensajesStop1();
                fuelCosumido += gasolinaExtra; // Reducimos el combustible.
            } else {
                System.out.println("Atravesando con problemas");
                do {
                    System.out.println("Se presenta un fallo en la nave");
                    mensajesStop1();
                    System.out.println("¿Qué acción tomar?");
                    mensajesStop1();
                    System.out.println("1. Reparar fallo");
                    mensajesStop1();
                    System.out.println("2. Seguir con la nave fallando");
                    opcion2 = sc.nextInt();
                    sc.nextLine(); // Limpia el buffer.

                    if (opcion2 != 1 && opcion2 != 2) {
                        System.out.println("Opción no válida");
                        mensajesStop1();
                    }
                } while (opcion2 != 1 && opcion2 != 2);

                if (opcion2 == 1) {
                    // Si se repara, se pierde menos gasolina que si la nave sigue fallando.
                    System.out.println("Fallo reparado");
                    mensajesStop1();
                    naveFallandoGasolina = false;
                    System.out.println("La reparación retrasó la nave, pero no pierde más gasolina.");
                    mensajesStop1();
                    gasolinaExtra = fuel * 0.05; // Se pierde menos gasolina en reparación.
                    System.out.println("Gasolina consumida en la reparación: " + gasolinaExtra + " Kilogramos.");
                    mensajesStop1();
                    fuelCosumido += gasolinaExtra; // Reducimos el combustible.
                } else {
                    // Si sigue fallando, se pierde un 5% adicional de gasolina.
                    System.out.println("Nave fallando");
                    mensajesStop1();
                    gasolinaExtra = ((fuel - fuelCosumido) * porcentajePerdida); // Se pierde un 5% de la gasolina
                                                                                 // actual.
                    System.out.println("Gasolina perdida por fallo: " + gasolinaExtra + " Kilogramos.");
                    mensajesStop1();
                    fuelCosumido += gasolinaExtra; // Reducimos el combustible.
                    naveFallandoGasolina = true; // Marcamos que la nave está fallando.
                }
            }
        }

        // Mostrar el resultado final de la gasolina.
        System.out.println("El combustible restante es: " + (fuel - fuelCosumido) + " Kilogramos.");
        mensajesStop1();
        System.out.println("Combustible consumido: " + fuelCosumido);
        mensajesStop1();

        // De ser necesario consumimos reservas
        consumirReservaCombustible();

        if (fuel - fuelCosumido <= 0) {
            System.out.println("¡Atención! El combustible se ha agotado. La nave está en peligro crítico.");
            mensajesStop1();
        }
    }

    // Funciones de menus
    private static String[] menuNaves(int destino) {

        System.out.println("Para su viaje a " + planetSelect(destino) + " necesitamos una nave.");
        
        mensajesStop1();

        return new String[] {
                "¿Qué tipo de nave desea?",
                "1. Nave: Star Voyager | Capacidad: 3 personas | Velocidad: 100000 km/h",
                "2. Nave: Cosmo Cruiser | Capacidad: 2 personas | Velocidad: 89000 km/h",
                "3. Nave: Galaxy Explorer | Capacidad: 4 personas | Velocidad: 80000 km/h"
        };

    }

    private static String naveSelect(int nave) {
        return switch (nave) {
            case 1 -> "Star Voyager";
            case 2 -> "Cosmo Cruiser";
            case 3 -> "Galaxy Explorer";
            default -> "Opcion aun no creada en este universo";
        };
    }

    private static String[] menuSecond() {
        return new String[] {
                "¿Ahora que desea hacer?",
                "1. Verificar los recursos de la nave",
                "2. Modificar los recursos de la nave",
                "3. Verificar el estado de la nave",
                "4. Iniciar el viaje"
        };
    }

    private static String[] secuenciaDespegue() {
        return new String[] {
                "Iniciando Despegue....",
                "Despegamos en",
                "5",
                "4",
                "3",
                "2",
                "1",
                "Despegue......"
        };
    }

    private static String[] menuInicial() {
        return new String[] {
                "¿Cuál es tu destino?",
                "1. Mercurio",
                "2. Venus",
                "3. Marte",
                "4. Júpiter",
                "5. Saturno",
                "6. Urano",
                "7. Neptuno"
        };
    }

    private static String planetSelect(int destino) {
        return switch (destino) {
            case 1 -> "Mercurio";
            case 2 -> "Venus";
            case 3 -> "Marte";
            case 4 -> "Júpiter";
            case 5 -> "Saturno";
            case 6 -> "Urano";
            case 7 -> "Neptuno";
            default -> "Opcion aun no creada en este universo";
        };
    }

    private static double distanceKM(int destino) {
        return switch (destino) {
            case 1 -> 91.7;
            case 2 -> 41.4;
            case 3 -> 225;
            case 4 -> 778;
            case 5 -> 1429;
            case 6 -> 2871;
            case 7 -> 4497;
            default -> -1;
        };
    }

    // Funcion que calcula el aumento de recursos y lo asigna a las reservas
    private static void calculateAumentoRecursos(Scanner sc, int cantidadTripulantes) {

        if (aumentoRecursos) {

            System.out.println(
                    "Recuerde que ya lleva reservas de combustible y oxígeno, por lo tanto se tendran en cuenta los recursos base para las nuevas reservas");
            mensajesStop1();
        }
        System.out.println(
                "En cantidad de %, ¿cuánto quiere aumentar el combustible? (Máximo puede aumentar en un 30%)");
        double aumentoC = sc.nextDouble();
        mensajesStop1();
        System.out
                .println("En cantidad de %, ¿cuánto quiere aumentar el oxígeno? (Máximo puede aumentar en un 30%)");
        double aumentoO = sc.nextDouble();
        mensajesStop1();

        if (aumentoC <= 30 && aumentoO <= 30) {
            fuelReerva = (fuel * aumentoC / 100);
            oxigenoReserva = (oxigeno * aumentoO / 100);

            System.out.println("La cantidad de combustible es: " + fuel + " Kilogramos"
                    + " y la reserva de combustible es: " + fuelReerva + " Kilogramos");
            mensajesStop1();
            System.out.println("La cantidad de oxígeno para " + cantidadTripulantes + " personas es: " + oxigeno
                    + " Kilogramos" + " y la reserva de oxígeno es: " + oxigenoReserva + " Kilogramos");
            mensajesStop1();

        } else {
            System.out.println("El aumento excede el total máximo");
            mensajesStop1();
        }
    }

    // Funcion que regersa el tiempo de vuelo en Dias
    private static double timeDD(int destino, int nave) {
        double distancia = distanceKM(destino) * 1e6; // Distancia en kilómetros
        double velocidad = velocidadNave(nave); // Velocidad promedio en km/h
        double tiempoHH = distancia / velocidad;
        return tiempoHH / 24; // Convertir a días
    }

    // Funcion que calcula la distancia y el tiempo de viaje al destino
    private static String calculateDistanceAndTime(int destino, int nave) {
        if (distanceKM(destino) == -1) {
            return "Opcion aun no creada en este universo, Destino no válido.";
        }

        int tiempoMM = (int) Math.ceil(timeDD(destino, nave) / 30.0); // Redondeo hacia arriba
        return String.format(
                "La distancia a %s es %.1f millones de kilómetros desde la Tierra y el tiempo estimado es de %d meses.",
                planetSelect(destino), distanceKM(destino), tiempoMM);
    }

    // Funcion que calcula la gasolina nesesaria para el viaje segun el destino
    private static double calculateFuel(int destino, int nave) {
        if (distanceKM(destino) == -1) {
            return 0.0;
        }

        double distanciaMillonesKM = distanceKM(destino);
        double consumoBasePorMillonKM = 500;
        double factorVelocidad = velocidadNave(nave) / 100000.0; // Ajuste basado en velocidad (normalizado con 100,000
                                                                 // km/h)
        double consumoTotal = consumoBasePorMillonKM * distanciaMillonesKM * factorVelocidad;

        consumoTotal += consumoTotal + 0.1; // Agregamos un 10% para llegar ccon mas base

        return consumoTotal;
    }

    private static double calculateOxigen(int destino, int nave, int cantidadTripulantes) {
        // Consumo promedio de oxigeno de una persona 0.84kg
        double consumoTotal = cantidadTripulantes * timeDD(destino, nave) * 24 * 0.84; // Oxígeno en kilogramos
                                                                                       // considerando horas

        consumoTotal += consumoTotal * 0.1; // Agregamos un 10% para llegar ccon mas base
        return consumoTotal;
    }
}
