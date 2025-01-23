package Reto02;

public interface ServicioEmergencia {
    void atenderEmergencia(Emergencia emergencia);
}

class Bomberos implements ServicioEmergencia {
    @Override
    public void atenderEmergencia(Emergencia emergencia) {
        System.out.println("Bomberos atendiendo " + emergencia.getTipo() + " en " + emergencia.getUbicacion());
    }
}

class Ambulancia implements ServicioEmergencia {
    @Override
    public void atenderEmergencia(Emergencia emergencia) {
        System.out.println("Ambulancia atendiendo " + emergencia.getTipo() + " en " + emergencia.getUbicacion());
    }
}

class Policia implements ServicioEmergencia {
    @Override
    public void atenderEmergencia(Emergencia emergencia) {
        System.out.println("Policía atendiendo " + emergencia.getTipo() + " en " + emergencia.getUbicacion());
    }
}