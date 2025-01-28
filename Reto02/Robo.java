package Reto02;

public class Robo extends Emergencia implements Responder{

    private SistemaEmergencias sistemaEmergencias; 
    int policiasAsignados = 0;

    public Robo(String ubicacion, int nivelGravedad, int tiempoRespuesta, SistemaEmergencias sistemaEmergencias) {
        super("Robo", ubicacion, nivelGravedad, tiempoRespuesta);
        this.sistemaEmergencias = sistemaEmergencias;
    }


    @Override
    public void atenderEmergencia() {
        System.out.println("Policia en camino.");
    }

    @Override
    public void evaluarEstado() {
        System.out.println("Revisar el estado de los afectados.");
    }

    public void asignarPolicia() {
        int policiasDisponibles = sistemaEmergencias.getRecursosDisponibles("Policia");
        
        
        // Asignación de recursos basada en la gravedad
        switch (getNivelGravedad()) {
            case 1: // Gravedad baja
                policiasAsignados = 1; 
                break;
            case 2: // Gravedad media
                policiasAsignados = 3; 
                break;
            case 3: // Gravedad alta
                policiasAsignados = 5; 
                break;
        }

        // Verificamos si hay suficientes 
        if (policiasDisponibles >= policiasAsignados) {
            sistemaEmergencias.asignarRecurso("Policia", policiasAsignados);
            System.out.println(policiasAsignados + " Policias asignados a la emergencia.");
        } else {
            System.out.println("No hay suficientes Policias disponibles.");
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
    public void liberarPolicia() {
        int tiempoTotal = calcularTiempoResolucion(); // Sumar tiempos de respuesta y resolución
        
        Thread liberarRecursos = new Thread(() -> {
            try {
                Thread.sleep(tiempoTotal * 1000);  // Simulamos el tiempo total (en segundos)
                sistemaEmergencias.liberarRecurso("Policia", policiasAsignados);                  
                //System.out.println("Policias liberadas después de la emergencia.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Iniciar el hilo
        liberarRecursos.start();
    }


    public int getPoliciasAsignados() {
        return policiasAsignados;
    }

}
