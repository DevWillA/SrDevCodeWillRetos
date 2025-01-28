package Reto02;

public class Emergencia {

    private String tipo;
    private String ubicacion;
    private int nivelGravedad; // 1 Bajo, 2 Medio , 3 Alto
    protected int tiempoRespuesta;

    public Emergencia(String tipo, String ubicacion, int nivelGravedad, int tiempoRespuesta) {
        this.tipo = tipo;
        this.ubicacion = ubicacion;
        this.nivelGravedad = nivelGravedad;
        this.tiempoRespuesta = tiempoRespuesta;
    }

        // Métodos para obtener los detalles de la emergencia
        public String obtenerDetalles() {
            String gravedad = "";
            switch (nivelGravedad) {
                case 1:
                    gravedad = "Bajo";
                    break;
                case 2:
                    gravedad = "Medio";
                    break;
                case 3:
                    gravedad = "Alto";
                    break;
                default:
                    gravedad = "Desconocido";
            }
            return "Emergencia: " + tipo + "\nUbicación: " + ubicacion + "\nGravedad: " + gravedad + "\nTiempo de Respuesta Estimado: " + tiempoRespuesta + " minutos";
        }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getNivelGravedad() {
        return nivelGravedad;
    }

    public void setNivelGravedad(int nivelGravedad) {
        this.nivelGravedad = nivelGravedad;
    }

    public int getTiempoRespuesta() {
        return tiempoRespuesta;
    }

    public void setTiempoRespuesta(int tiempoRespuesta) {
        this.tiempoRespuesta = tiempoRespuesta;
    }

       // Método para mostrar un resumen de la emergencia
       public void mostrarResumen() {
        System.out.println("Emergencia de tipo: " + tipo);
        System.out.println("Ubicación: " + ubicacion);
        System.out.println("Nivel de gravedad: " + nivelGravedad);
        System.out.println("Tiempo de respuesta: " + tiempoRespuesta + " minutos");
    }



}
