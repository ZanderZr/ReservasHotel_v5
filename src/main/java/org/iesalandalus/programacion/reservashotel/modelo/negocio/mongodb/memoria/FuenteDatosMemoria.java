package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.memoria;

import org.iesalandalus.programacion.reservashotel.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHabitaciones;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHuespedes;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IReservas;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.Habitaciones;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.Huespedes;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.Reservas;

public class FuenteDatosMemoria implements IFuenteDatos {
    @Override
    public IHuespedes crearHuespedes() {
        return new Huespedes();
    }

    @Override
    public IHabitaciones crearHabitaciones() {
        return new Habitaciones();
    }

    @Override
    public IReservas crearReservas() {
        return new Reservas();
    }
}
