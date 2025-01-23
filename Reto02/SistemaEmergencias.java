package Reto02;
import java.util.*;

public class SistemaEmergencias {
    private List<Emergencia> emergencias;
    private Map<String, Integer> recursosDisponibles; 

    public SistemaEmergencias() {
        emergencias = new ArrayList<>();
        recursosDisponibles = new HashMap<>();
        recursosDisponibles.put("Bomberos", 5);
        recursosDisponibles.put("Ambulancias", 3);
        recursosDisponibles.put("Policia", 4);
    }

    // Menú de opciones
    public void menu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("1. Registrar una nueva emergencia");
            System.out.println("2. Ver estado de recursos");
            System.out.println("3. Atender emergencia");
            System.out.println("4. Mostrar estadísticas");
            System.out.println("5. Salir");
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    registrarEmergencia(scanner);
                    break;
                case 2:
                    mostrarRecursos();
                    break;
                case 3:
                    atenderEmergencia(scanner);
                    break;
                case 4:
                    mostrarEstadisticas();
                    break;
                case 5:
                    System.out.println("Cerrando sistema...");
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        } while (opcion != 5);
    }

    // Registrar emergencia
    private void registrarEmergencia(Scanner scanner) {
        System.out.println("Tipos de emergencia disponibles: Incendio, Accidente, Robo");
        System.out.print("Selecciona el tipo de emergencia: ");
        String tipo = scanner.next();

        System.out.print("Ubicación de la emergencia: ");
        String ubicacion = scanner.next();

        System.out.print("Nivel de gravedad (1 bajo, 2 medio, 3 alto): ");
        int nivelGravedad = scanner.nextInt();

        System.out.print("Tiempo estimado de respuesta (minutos): ");
        int tiempoRespuesta = scanner.nextInt();

        Emergencia emergencia = null;
        switch (tipo.toLowerCase()) {
            case "incendio":
            emergencia = new Incendio(ubicacion, nivelGravedad, tiempoRespuesta, this); // Pasar el objeto SistemaEmergencias
            break;
        case "accidente":
            emergencia = new Accidente(ubicacion, nivelGravedad, tiempoRespuesta, this); // Pasar el objeto SistemaEmergencias
            break;
        case "robo":
            emergencia = new Robo(ubicacion, nivelGravedad, tiempoRespuesta, this); // Pasar el objeto SistemaEmergencias
            break;
            default:
                System.out.println("Tipo de emergencia no válido.");
                return;
        }

        emergencias.add(emergencia);
        emergencia.mostrarResumen();
        System.out.println("Emergencia registrada con éxito.");
    }

    // Mostrar estado de los recursos disponibles
    private void mostrarRecursos() {
        System.out.println("Estado de los recursos disponibles:");
        for (Map.Entry<String, Integer> entry : recursosDisponibles.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    // Atender emergencia
    // Atender emergencia
    private void atenderEmergencia(Scanner scanner) {
        System.out.println("Seleccione una emergencia para atender:");
        for (int i = 0; i < emergencias.size(); i++) {
            System.out.println((i + 1) + ". " + emergencias.get(i).getTipo() + " en " + emergencias.get(i).getUbicacion());
        }
        int emergenciaSeleccionada = scanner.nextInt() - 1;
        Emergencia emergencia = emergencias.get(emergenciaSeleccionada);

        if (emergencia instanceof Incendio) {
            ((Incendio) emergencia).asignarBomberos();
            ((Incendio) emergencia).liberarBomberos(); 
        } else if (emergencia instanceof Accidente) {
            ((Accidente) emergencia).asignarAmbulancias();
            ((Accidente) emergencia).liberarAmbulancias(); 
        } else if (emergencia instanceof Robo) {
            ((Robo) emergencia).asignarPolicia();
            ((Robo) emergencia).liberarPolicia(); 
        }

        System.out.println("Recursos asignados a la emergencia.");
    }

    // Mostrar estadísticas
    private void mostrarEstadisticas() {
        System.out.println("Estadísticas de la jornada:");
        System.out.println("Total de emergencias atendidas: " + emergencias.size());
        // Otros cálculos de estadísticas como tiempo promedio, recursos usados, etc.
    }

    public int getRecursosDisponibles(String tipoRecurso) {
        return recursosDisponibles.getOrDefault(tipoRecurso, 0);
    }
    
    // Método para asignar un recurso
    public void asignarRecurso(String tipoRecurso, int cantidad) {
        int recursosRestantes = recursosDisponibles.get(tipoRecurso) - cantidad; // Restar la cantidad de recursos asignados
        recursosDisponibles.put(tipoRecurso, recursosRestantes);
        System.out.println(cantidad + " " + tipoRecurso + "(s) asignado(s).");
    }

    public void liberarRecurso(String tipoRecurso, int cantidad) {
        int recursosRestantes = recursosDisponibles.get(tipoRecurso) + cantidad; // Sumamos los recursos liberados
        recursosDisponibles.put(tipoRecurso, recursosRestantes);
        System.out.println();
        System.out.println(cantidad + " " + tipoRecurso + "(s) liberado(s).");
    }
}
