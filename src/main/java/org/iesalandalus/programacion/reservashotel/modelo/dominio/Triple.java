package org.iesalandalus.programacion.reservashotel.modelo.dominio;

public class Triple extends Habitacion{

    private static final int NUM_MAXIMO_PERSONAS = 3;
    static final int MIN_NUM_BANOS = 0;
    static final int MAX_NUM_BANOS = 1;
    static final int MIN_NUM_CAMAS_INDIVIDUALES = 1;
    static final int MAX_NUM_CAMAS_INDIVIDUALES = 3;
    static final int MIN_NUM_CAMAS_DOBLES = 0;
    static final int MAX_NUM_CAMAS_DOBLES = 1;
    private int numBanos;
    private int numCamasIndividuales;
    private int numCamasDobles;

    public Triple(int planta, int puerta, double precio, int numBanos, int numCamasIndividuales, int numCamasDobles) {
        super(planta, puerta, precio);
        setNumBanos(numBanos);
        setNumCamasIndividuales(numCamasIndividuales);
        setNumCamasDobles(numCamasDobles);
    }

    public Triple(Triple habitacionTriple) {
        super(habitacionTriple.getPlanta(), habitacionTriple.getPuerta(), habitacionTriple.getPrecio());
        setNumBanos(habitacionTriple.numBanos);
        setNumCamasIndividuales(habitacionTriple.numCamasIndividuales);
        setNumCamasDobles(habitacionTriple.numCamasDobles);
    }

    public int getNumBanos() {
        return numBanos;
    }

    public void setNumBanos(int numBanos) {
        this.numBanos = numBanos;
    }

    public int getNumCamasIndividuales() {
        return numCamasIndividuales;
    }

    public void setNumCamasIndividuales(int numCamasIndividuales) {
        this.numCamasIndividuales = numCamasIndividuales;
        validaNumCamas();
    }

    public int getNumCamasDobles() {
        return numCamasDobles;
    }

    public void setNumCamasDobles(int numCamasDobles) {
        this.numCamasDobles = numCamasDobles;
        validaNumCamas();
    }

    private void validaNumCamas(){
        if(!((numCamasIndividuales == 3 && numCamasDobles == 0) || (numCamasIndividuales == 1 && numCamasDobles == 1))){
            throw new IllegalArgumentException("Error numero de camas equivocado.\nUna habitación triple debe tener o bien 3 camas individuales o bien 1 cama doble y 1 cama individual.");
        }
    }

    @Override
    public int getNumeroMaximoPersonas() {
        return NUM_MAXIMO_PERSONAS;
    }

    @Override
    public String toString() {
        return "Triple{" +
                "identificador='" + identificador + '\'' +
                ", planta=" + planta +
                ", puerta=" + puerta +
                ", precio=" + precio +
                ", numBanos=" + numBanos +
                ", numCamasIndividuales=" + numCamasIndividuales +
                ", numCamasDobles=" + numCamasDobles +

                '}';
    }
}
