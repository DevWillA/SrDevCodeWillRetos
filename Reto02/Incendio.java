package Reto02;

public class Incendio extends Emergencia implements Responder{
    public Incendio(String tipo, String ubicacion, int nivelGravedad, int tiempoRespuesta) {
        super(tipo, ubicacion, nivelGravedad, tiempoRespuesta);

    }

    @Override
    public void atenderEmergencia() {
        System.out.println("Bomberos en camino.");
    }

    @Override
    public void evaluarEstado() {
        System.out.println("Revisar el estado de la estructura.");
    }
}
