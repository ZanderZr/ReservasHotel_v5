package org.iesalandalus.programacion.reservashotel.modelo.negocio;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public interface IHabitaciones {
    ArrayList<Habitacion> get();
    ArrayList<Habitacion> get(TipoHabitacion tipoHabitacion);
    int getTamano();
    void insertar(Habitacion habitacion) throws OperationNotSupportedException;

    Habitacion buscar(Habitacion habitacion);
    void borrar(Habitacion habitacion) throws NoSuchElementException;
}