package Reto02;

import java.util.ArrayList;
import java.util.List;

class MapaUrbano {
    private List<String> ubicacionesCriticas;

    public MapaUrbano() {
        ubicacionesCriticas = new ArrayList<>();
        ubicacionesCriticas.add("Hospital Central");
        ubicacionesCriticas.add("Estación de Policía");
    }

    public boolean estaCerca(String ubicacion) {
        return ubicacionesCriticas.stream().anyMatch(ubicacion::equalsIgnoreCase);
    }
}