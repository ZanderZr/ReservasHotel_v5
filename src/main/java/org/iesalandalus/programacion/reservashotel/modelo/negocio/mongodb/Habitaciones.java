package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHabitaciones;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Habitaciones implements IHabitaciones {
    private ArrayList<Habitacion> coleccionHabitaciones;

    public Habitaciones() {
        this.coleccionHabitaciones = new ArrayList<>();
    }


    public ArrayList<Habitacion> get() {

        return new ArrayList<>(coleccionHabitaciones);
    }

    public ArrayList<Habitacion> get(TipoHabitacion tipoHabitacion) {
        ArrayList<Habitacion> habitacionesTipo = new ArrayList<>();
        for (Habitacion habitacion : coleccionHabitaciones) {
            switch (tipoHabitacion){
                case SIMPLE :
                    if (habitacion instanceof Simple) {
                    habitacionesTipo.add(habitacion);
                }
                    break;
                case DOBLE:
                    if (habitacion instanceof Doble) {
                        habitacionesTipo.add(habitacion);
                    }
                    break;
                case TRIPLE:
                    if (habitacion instanceof Triple) {
                        habitacionesTipo.add(habitacion);
                    }
                    break;
                case SUITE:
                    if (habitacion instanceof Suite) {
                        habitacionesTipo.add(habitacion);
                    }
                    break;
            }

        }
        return habitacionesTipo;
    }

    public int getTamano() {
        return coleccionHabitaciones.size();
    }

    public void insertar(Habitacion habitacion) throws OperationNotSupportedException {
        if (habitacion == null) {
            throw new IllegalArgumentException("No se puede insertar una habitación nula.");
        }
        if (buscar(habitacion) != null) {
            throw new OperationNotSupportedException("La habitación ya existe y no se admiten repetidos.");
        }
        coleccionHabitaciones.add(habitacion);
    }

    public Habitacion buscar(Habitacion habitacion) {
        int indice = coleccionHabitaciones.indexOf(habitacion);
        if (indice != -1) {
            return coleccionHabitaciones.get(indice);
        }
        return null;
    }

    public void borrar(Habitacion habitacion) throws NoSuchElementException {
        if (!coleccionHabitaciones.remove(habitacion)) {
            throw new NoSuchElementException("La habitación proporcionada no se encuentra en la colección.");
        }
    }

    @Override
    public void comenzar() {

    }

    @Override
    public void terminar() {

    }

}
