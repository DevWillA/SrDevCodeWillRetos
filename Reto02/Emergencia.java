package Reto02;

public class Emergencia {

    private final String tipo;
    private final String ubicacion;
    private final int nivelGravedad; // 1 Bajo, 2 Medio , 3 Alto
    private final int tiempoResouesta;

    public Emergencia(String tipo, String ubicacion, int nivelGravedad, int tiempoResouesta) {
        this.tipo = tipo;
        this.ubicacion = ubicacion;
        this.nivelGravedad = nivelGravedad;
        this.tiempoResouesta = tiempoResouesta;
    }

    public String getTipo() {
        return tipo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public int getNivelGravedad() {
        return nivelGravedad;
    }

    public int getTiempoResouesta() {
        return tiempoResouesta;
    }

    @Override
    public String toString() {
        return "Emergencia [tipo=" + tipo + ", ubicacion=" + ubicacion + ", nivelGravedad=" + nivelGravedad
                + ", tiempoResouesta=" + tiempoResouesta + "]";
    }

    

}
