package org.iesalandalus.programacion.reservashotel.modelo.dominio;

public enum TipoHabitacion {
    SUITE("Suite"),
    SIMPLE("Habitación simple"),
    DOBLE("Habitación doble"),
    TRIPLE("Habitación triple");

    private String cadenaAMostrar;

    TipoHabitacion(String cadenaAMostrar) {
        this.cadenaAMostrar = cadenaAMostrar;
    }

    @Override
    public String toString() {
        return cadenaAMostrar;
    }
}
