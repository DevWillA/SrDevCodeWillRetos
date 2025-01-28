package Reto02;

public class Incendio extends Emergencia implements Responder{

    private SistemaEmergencias sistemaEmergencias; 
    int bomberosAsignados = 0;

    
    public Incendio(String ubicacion, int nivelGravedad, int tiempoRespuesta, SistemaEmergencias sistemaEmergencias) {
        super("Incendio", ubicacion, nivelGravedad, tiempoRespuesta);
        this.sistemaEmergencias = sistemaEmergencias;
    }


    @Override
    public void atenderEmergencia() {
        System.out.println("Bomberos en camino.");
    }

    @Override
    public void evaluarEstado() {
        System.out.println("Revisar el estado de la estructura.");
    }

    public void asignarBomberos() {
        int bomberosDisponibles = sistemaEmergencias.getRecursosDisponibles("Bomberos");
     
        
        // Asignación de recursos basada en la gravedad
        switch (getNivelGravedad()) {
            case 1: // Gravedad baja
                bomberosAsignados = 1; // Menos bomberos para emergencias de baja gravedad
                break;
            case 2: // Gravedad media
                bomberosAsignados = 3; // Bomberos estándar para emergencias de gravedad media
                break;
            case 3: // Gravedad alta
                bomberosAsignados = 5; // Más bomberos para emergencias de alta gravedad
                break;
        }

        // Verificamos si hay suficientes bomberos disponibles
        if (bomberosDisponibles >= bomberosAsignados) {
            sistemaEmergencias.asignarRecurso("Bomberos", bomberosAsignados);
            System.out.println(bomberosAsignados + " bomberos asignados a la emergencia de incendio.");
        } else {
            System.out.println("No hay suficientes bomberos disponibles.");
        }
    }

    // Método para calcular el tiempo total de resolución de la emergencia
    public int calcularTiempoResolucion() {
        int tiempoResolucion = 0;

        // El tiempo de resolución depende de la gravedad de la emergencia
        switch (getNivelGravedad()) {
            case 1: // Gravedad baja
                tiempoResolucion = 5; // 5 minutos
                break;
            case 2: // Gravedad media
                tiempoResolucion = 15; // 15 minutos
                break;
            case 3: // Gravedad alta
                tiempoResolucion = 30; // 30 minutos
                break;
        }

        // El tiempo total será la suma del tiempo de respuesta y el tiempo de resolución
        return tiempoRespuesta + tiempoResolucion; 
    }

    // Crear un hilo para liberar los bomberos después del tiempo total de emergencia
    public void liberarBomberos() {
        int tiempoTotal = calcularTiempoResolucion(); // Sumar tiempos de respuesta y resolución
        
        Thread liberarRecursos = new Thread(() -> {
            try {
                Thread.sleep(tiempoTotal * 1000);  // Simulamos el tiempo total (en segundos)
                sistemaEmergencias.liberarRecurso("Bomberos", bomberosAsignados );  // Liberamos los bomberos asignados
                //System.out.println("Bomberos liberados después de la emergencia de incendio.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Iniciar el hilo
        liberarRecursos.start();
    }


    public int getBomberosAsignados() {
        return bomberosAsignados;
    }

}
