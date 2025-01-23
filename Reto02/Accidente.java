package Reto02;

public class Accidente extends Emergencia implements Responder {

    private SistemaEmergencias sistemaEmergencias; 
    int ambulanciaAsignadas = 0;

    public Accidente(String ubicacion, int nivelGravedad, int tiempoRespuesta, SistemaEmergencias sistemaEmergencias) {
        super("Accidente Vehicular", ubicacion, nivelGravedad, tiempoRespuesta);
        this.sistemaEmergencias = sistemaEmergencias;
    }


    @Override
    public void atenderEmergencia() {
        System.out.println("Ambulacia en camino.");
    }

    @Override
    public void evaluarEstado() {
        System.out.println("Revisar signos vitales de los pasajeros.");
    }

    public void asignarAmbulancias() {
        int ambulanciasDisponibles = sistemaEmergencias.getRecursosDisponibles("Ambulancias");
        
        
        // Asignación de recursos basada en la gravedad
        switch (getNivelGravedad()) {
            case 1: // Gravedad baja
                ambulanciaAsignadas = 1; 
                break;
            case 2: // Gravedad media
                ambulanciaAsignadas = 3; 
                break;
            case 3: // Gravedad alta
                ambulanciaAsignadas = 5; 
                break;
        }

        // Verificamos si hay suficientes 
        if (ambulanciasDisponibles >= ambulanciaAsignadas) {
            sistemaEmergencias.asignarRecurso("Ambulancias", ambulanciaAsignadas);
            System.out.println(ambulanciaAsignadas + " ambulancias asignadas a la emergencia.");
        } else {
            System.out.println("No hay suficientes ambulancias disponibles.");
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
    public void liberarAmbulancias() {
        int tiempoTotal = calcularTiempoResolucion(); // Sumar tiempos de respuesta y resolución
        
        Thread liberarRecursos = new Thread(() -> {
            try {
                Thread.sleep(tiempoTotal * 1000);  // Simulamos el tiempo total (en segundos)
                sistemaEmergencias.liberarRecurso("Ambulancias", ambulanciaAsignadas);                  
                System.out.println("Ambulancias liberadas después de la emergencia.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Iniciar el hilo
        liberarRecursos.start();
    }



}
