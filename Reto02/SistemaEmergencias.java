package Reto02;

import java.util.*;

public class SistemaEmergencias {
    private List<Emergencia> emergencias;
    private List<Emergencia> emergenciasTotales;
    private Map<String, Integer> recursosDisponibles;
    private Map<String, Integer> recursosGastados;
    private Map<String, Integer> tiempoRespuestaPorTipo;

    public SistemaEmergencias() {
        emergencias = new ArrayList<>();
        emergenciasTotales = new ArrayList<>();
        recursosDisponibles = new HashMap<>();
        recursosGastados = new HashMap<>();
        tiempoRespuestaPorTipo = new HashMap<>();

        // Inicializar los recursos
        recursosDisponibles.put("Bomberos", 10);
        recursosDisponibles.put("Ambulancias", 5);
        recursosDisponibles.put("Policia", 20);

        // Inicializar los recursos gastados
        recursosGastados.put("Bomberos", 0);
        recursosGastados.put("Ambulancias", 0);
        recursosGastados.put("Policia", 0);

    }

    // Menú de opciones
    public void menu() {
        System.out.println("Bienvenido al Sistema de Emegercias");
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
                emergencia = new Incendio(ubicacion, nivelGravedad, tiempoRespuesta, this);
                break;
            case "accidente":
                emergencia = new Accidente(ubicacion, nivelGravedad, tiempoRespuesta, this);
                break;
            case "robo":
                emergencia = new Robo(ubicacion, nivelGravedad, tiempoRespuesta, this);
                break;
            default:
                System.out.println("Tipo de emergencia no válido.");
                return;
        }

        emergencias.add(emergencia);
        emergenciasTotales.add(emergencia);
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
    private void atenderEmergencia(Scanner scanner) {
        if (emergencias.isEmpty()) {
            System.out.println("No hay emergencias pendientes por atender.");
            return;
        }

        // Ordenar las emergencias por nivelGravedad (de mayor a menor gravedad)
        emergencias.sort((e1, e2) -> Integer.compare(e2.getNivelGravedad(), e1.getNivelGravedad()));

        System.out.println("Seleccione una emergencia para atender:");
        for (int i = 0; i < emergencias.size(); i++) {
            System.out.println(
                    (i + 1) + ". " + emergencias.get(i).getTipo() + " en " + emergencias.get(i).getUbicacion() + " Nivel Gravedad " + emergencias.get(i).getNivelGravedad());
        }

        int emergenciaSeleccionada = scanner.nextInt() - 1;

        // Validar si el índice ingresado es válido
        if (emergenciaSeleccionada < 0 || emergenciaSeleccionada >= emergencias.size()) {
            System.out.println("Selección inválida. Intenta de nuevo.");
            return;
        }

        Emergencia emergencia = emergencias.get(emergenciaSeleccionada);

        if (emergencia instanceof Incendio) {
            ((Incendio) emergencia).asignarBomberos();
            agregarRecursosGastados("Bomberos", ((Incendio) emergencia).getBomberosAsignados());
            agregarTiempoRespuesta("Incendio", emergencia.getTiempoRespuesta());
            ((Incendio) emergencia).liberarBomberos();
        } else if (emergencia instanceof Accidente) {
            ((Accidente) emergencia).asignarAmbulancias();
            agregarRecursosGastados("Ambulancias", ((Accidente) emergencia).getAmbulanciasAsignadas());
            agregarTiempoRespuesta("Accidente", emergencia.getTiempoRespuesta());
            ((Accidente) emergencia).liberarAmbulancias();
        } else if (emergencia instanceof Robo) {
            ((Robo) emergencia).asignarPolicia();
            agregarRecursosGastados("Policia", ((Robo) emergencia).getPoliciasAsignados());
            agregarTiempoRespuesta("Robo", emergencia.getTiempoRespuesta());
            ((Robo) emergencia).liberarPolicia();
        }



        // Eliminar la emergencia de la lista
        emergencias.remove(emergenciaSeleccionada);
        // System.out.println("La emergencia ha sido eliminada del sistema.");
    }

    // Agregar recursos gastados
    private void agregarRecursosGastados(String recurso, int cantidad) {
        recursosGastados.put(recurso, recursosGastados.get(recurso) + cantidad);
    }

    // Agregar tiempo de respuesta
    private void agregarTiempoRespuesta(String tipoEmergencia, int tiempo) {
        tiempoRespuestaPorTipo.put(tipoEmergencia, tiempoRespuestaPorTipo.getOrDefault(tipoEmergencia, 0) + tiempo);
    }

    // Mostrar estadísticas
    private void mostrarEstadisticas() {
        
        if (emergenciasTotales.isEmpty()) {
            System.out.println("No hay emergencias registradas.");
            return;
        } else {
            System.out.println("Estadísticas de la jornada:");
            System.out.println("Total de emergencias atendidas: " + emergenciasTotales.size());}
       

        // Mostrar tiempo promedio de respuesta por tipo de emergencia
        System.out.println("\nTiempo promedio de respuesta por tipo de emergencia:");
        for (Map.Entry<String, Integer> entry : tiempoRespuestaPorTipo.entrySet()) {
            String tipo = entry.getKey();
            int tiempoTotal = entry.getValue();
            long count = emergencias.stream().filter(e -> e.getTipo().equals(tipo)).count();
            int tiempoPromedio = (int) (tiempoTotal / count);
            System.out.println(tipo + ": " + tiempoPromedio + " minutos");
        }

        // Mostrar recursos gastados vs disponibles
        System.out.println("\nRecursos gastados vs. recursos disponibles:");
        for (Map.Entry<String, Integer> entry : recursosGastados.entrySet()) {
            String recurso = entry.getKey();
            int recursosGastados = entry.getValue();
            int recursosDisponibles = this.recursosDisponibles.get(recurso);
            System.out.println(recurso + " - Gastados: " + recursosGastados + ", Disponibles: " + recursosDisponibles);
        }
    }

    // Mostrar estadísticas
    // private void mostrarEstadisticas() {
    // System.out.println("Estadísticas de la jornada:");
    // System.out.println("Total de emergencias atendidas: " + emergencias.size());
    // Otros cálculos de estadísticas como tiempo promedio, recursos usados, etc.
    // }

    public int getRecursosDisponibles(String tipoRecurso) {
        return recursosDisponibles.getOrDefault(tipoRecurso, 0);
    }

    // Método para asignar un recurso
    public void asignarRecurso(String tipoRecurso, int cantidad) {
        int recursosRestantes = recursosDisponibles.get(tipoRecurso) - cantidad; // Restar la cantidad de recursos
                                                                                 // asignados
        recursosDisponibles.put(tipoRecurso, recursosRestantes);
        //System.out.println(cantidad + " " + tipoRecurso + "(s) asignado(s).");
    }

    public void liberarRecurso(String tipoRecurso, int cantidad) {
        int recursosRestantes = recursosDisponibles.get(tipoRecurso) + cantidad; // Sumamos los recursos liberados
        recursosDisponibles.put(tipoRecurso, recursosRestantes);
        System.out.println();
        System.out.println(cantidad + " " + tipoRecurso + "(s) liberado(s).");
    }
}
