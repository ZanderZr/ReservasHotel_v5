package org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IReservas;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class Reservas implements IReservas {
    private ArrayList<Reserva> coleccionReservas;

    public Reservas(){
        this.coleccionReservas = new ArrayList<>();
    }

    public ArrayList<Reserva> get(){
        return new ArrayList<>(coleccionReservas);
    }
    public int getTamano(){
        return coleccionReservas.size();
    }
    public void insertar(Reserva reserva) throws OperationNotSupportedException {
        if (reserva == null) {
            throw new IllegalArgumentException("No se puede insertar una reserva nula.");
        }
        if (buscar(reserva) != null) {
            throw new OperationNotSupportedException("La reserva ya est� registrada y no se admiten repetidos.");
        }
        coleccionReservas.add(new Reserva(reserva));
    }

    public Reserva buscar(Reserva reserva) throws NoSuchElementException {
       int indice = coleccionReservas.indexOf(reserva);
       if (indice != -1) {
           return new Reserva(coleccionReservas.get(indice));
       }
       return null;
    }

    public void borrar(Reserva reserva) throws NoSuchElementException {
        if (!coleccionReservas.remove(reserva)){
            throw new NoSuchElementException("La reserva proporcionada no se encuentra en la colección.");
        }
    }

    public ArrayList<Reserva> getReservas(Huesped huesped){
        ArrayList<Reserva> reservasHuesped = new ArrayList<>();
        for ( Reserva reserva : coleccionReservas){
            if (reserva.getHuesped().equals(huesped)){
                reservasHuesped.add(new Reserva(reserva));
            }
        }
        if (reservasHuesped.isEmpty()) {
            throw new NoSuchElementException("No se encontraron reservas para el huésped proporcionado.");
        }
        return reservasHuesped;
    }

    public ArrayList<Reserva> getReservas(TipoHabitacion tipoHabitacion) {
        ArrayList<Reserva> reservasTipoHabitacion = new ArrayList<>();
        for (Reserva reserva : coleccionReservas) {
            switch (tipoHabitacion){
                case SIMPLE :
                    if (reserva.getHabitacion() instanceof Simple) {
                        reservasTipoHabitacion.add(new Reserva(reserva));
                    }
                    break;
                case DOBLE:
                    if (reserva.getHabitacion() instanceof Doble) {
                        reservasTipoHabitacion.add(new Reserva(reserva));
                    }
                    break;
                case TRIPLE:
                    if (reserva.getHabitacion() instanceof Triple) {
                        reservasTipoHabitacion.add(new Reserva(reserva));
                    }
                    break;
                case SUITE:
                    if (reserva.getHabitacion() instanceof Suite) {
                        reservasTipoHabitacion.add(new Reserva(reserva));
                    }
                    break;
            }
        }
        if (reservasTipoHabitacion.isEmpty()) {
            throw new NoSuchElementException("No se encontraron reservas para el tipo de habitaci�n proporcionado.");
        }
        return reservasTipoHabitacion;
    }

    @Override
    public ArrayList<Reserva> getReservas(Habitacion habitacion) {
        return null;
    }

    public ArrayList<Reserva> getReservasFuturas(Habitacion habitacion) {
        ArrayList<Reserva> reservasFuturas = new ArrayList<>();
        for (Reserva reserva : coleccionReservas) {
            if (reserva.getHabitacion().equals(habitacion) && reserva.getFechaInicioReserva().isAfter(LocalDate.now())) {
                reservasFuturas.add(new Reserva(reserva));
            }
        }
        if (reservasFuturas.isEmpty()) {
            throw new NoSuchElementException("No se encontraron reservas futuras para la habitaci�n proporcionada.");
        }
        return reservasFuturas;
    }

    public void realizarCheckin(Reserva reserva, LocalDateTime fechaHora) {
        try {
            Reserva reservaEncontrada = buscar(reserva);
            if (reservaEncontrada != null) {
                if (reservaEncontrada.getCheckIn() == null) {
                    reservaEncontrada.setCheckIn(fechaHora);
                    System.out.println("Checkin realizado con �xito: " + fechaHora.format(DateTimeFormatter.ofPattern(Reserva.FORMATO_FECHA_HORA_RESERVA)));
                } else {
                    System.out.println("Ya se ha realizado el check-in para esta reserva.");
                }
            } else {
                throw new NoSuchElementException("La reserva no se encuentra en la colecci�n.");
            }
        } catch (Exception e) {
            System.out.println("Error al realizar el check-in: " + e.getMessage());
        }
    }

    public void realizarCheckout(Reserva reserva, LocalDateTime fechaHora) {
        try {
            Reserva reservaEncontrada = buscar(reserva);
            if (reservaEncontrada != null) {
                if (reservaEncontrada.getCheckIn() != null && reservaEncontrada.getCheckOut() == null) {
                    reservaEncontrada.setCheckOut(fechaHora);
                    System.out.println("Checkout realizado con �xito: " + fechaHora.format(DateTimeFormatter.ofPattern(Reserva.FORMATO_FECHA_HORA_RESERVA)));
                } else {
                    System.out.println("No se puede realizar el checkout sin haber hecho el checkin.");
                }
            } else {
                throw new NoSuchElementException("La reserva no se encuentra en la colecci�n.");
            }
        } catch (Exception e) {
            System.out.println("Error al realizar el check-out: " + e.getMessage());
        }
    }

    @Override
    public void comenzar() {

    }

    @Override
    public void terminar() {

    }

}
