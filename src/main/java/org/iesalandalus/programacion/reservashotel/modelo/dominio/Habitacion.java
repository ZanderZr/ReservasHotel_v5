package org.iesalandalus.programacion.reservashotel.modelo.dominio;

import java.util.Objects;
public abstract class Habitacion {
    public static final double MIN_PRECIO_HABITACION = 40.0;
    public static final double MAX_PRECIO_HABITACION = 150.0;
    public static final int MIN_NUMERO_PUERTA = 1;
    public static final int MAX_NUMERO_PUERTA = 15;
    public static final int MIN_NUMERO_PLANTA = 1;
    public static final int MAX_NUMERO_PLANTA = 3;
    protected String identificador;
    protected int planta;
    protected int puerta;
    protected double precio;



    public Habitacion (int planta, int puerta, double precio){
        setPlanta(planta);
        setPuerta(puerta);
        setPrecio(precio);
        setIdentificador();
    }

    public Habitacion(Habitacion habitacion) {
        setIdentificador(habitacion.identificador);
        setPlanta(habitacion.planta);
        setPuerta(habitacion.puerta);
        setPrecio(habitacion.precio);
    }

    public abstract int getNumeroMaximoPersonas();

    public String getIdentificador() {
        return identificador;
    }

    protected void setIdentificador() {
        this.identificador = planta + "-" + puerta;
    }

    protected void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public int getPlanta() {
        return planta;
    }

    protected void setPlanta(int planta) {

        if (planta < MIN_NUMERO_PLANTA || planta > MAX_NUMERO_PLANTA) {
            throw new IllegalArgumentException("N�mero de planta no v�lido.");
        } else {
            this.planta = planta;
        }
    }

    public int getPuerta() {
        return puerta;
    }

    protected void setPuerta(int puerta) {
        if (puerta < MIN_NUMERO_PUERTA || puerta > MAX_NUMERO_PUERTA) {
            throw new IllegalArgumentException("N�mero de puerta no v�lido.");
        } else {
            this.puerta = puerta;
        }
    }

    public double getPrecio() {
        return precio;
    }

    protected void setPrecio(double precio) {
        if (precio < MIN_PRECIO_HABITACION || precio > MAX_PRECIO_HABITACION) {
            throw new IllegalArgumentException("Precio no v�lido.");
        } else {
            this.precio = precio;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Habitacion that = (Habitacion) obj;
        return Objects.equals(identificador, that.identificador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }

    @Override
    public String toString() {
        return "Habitacion{" +
                "identificador='" + identificador + '\'' +
                ", planta=" + planta +
                ", puerta=" + puerta +
                ", precio=" + precio + '\'' +
                '}';
    }
}
