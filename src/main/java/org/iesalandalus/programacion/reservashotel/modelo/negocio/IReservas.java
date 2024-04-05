package org.iesalandalus.programacion.reservashotel.modelo.negocio;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public interface IReservas {

    ArrayList<Reserva> get();
    int getTamano();
    void insertar(Reserva reserva) throws OperationNotSupportedException;
    Reserva buscar (Reserva reserva);
    void borrar (Reserva reserva) throws NoSuchElementException;
    ArrayList<Reserva> getReservas(Huesped huesped);
    ArrayList<Reserva> getReservas(TipoHabitacion tipoHabitacion);
    ArrayList<Reserva> getReservas(Habitacion habitacion);
    ArrayList<Reserva> getReservasFuturas(Habitacion habitacion);
    void realizarCheckin(Reserva reserva, LocalDateTime fechaHora);
    void realizarCheckout(Reserva reserva, LocalDateTime fechaHora);

    void comenzar();
    void terminar();
}
