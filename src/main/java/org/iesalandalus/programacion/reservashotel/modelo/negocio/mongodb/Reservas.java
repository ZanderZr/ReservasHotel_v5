package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IReservas;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades.MongoDB;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Reservas implements IReservas {
    private static final String COLECCION = "reservas";
    private ArrayList<Reserva> coleccionReservas;

    public Reservas(){
        this.coleccionReservas = new ArrayList<>();
    }

    public ArrayList<Reserva> get(){
        MongoDB mongoDB = new MongoDB();
        MongoDatabase database = mongoDB.getBD();
        MongoCollection<Document> collection = database.getCollection(COLECCION);

        ArrayList<Reserva> reservas = new ArrayList<>();
        for (Document doc : collection.find().sort(Sorts.ascending("fecha_inicio_reserva"))) {
            Reserva reserva = mongoDB.getReserva(doc);
            reservas.add(reserva);
        }

        return reservas;
    }
    public int getTamano(){
        MongoDB mongoDB = new MongoDB();
        MongoDatabase database = mongoDB.getBD();
        MongoCollection<Document> collection = database.getCollection(COLECCION);

        long num = collection.countDocuments();

        return (int) num;
    }
    public void insertar(Reserva reserva) throws OperationNotSupportedException {
        if (reserva == null) {
            throw new IllegalArgumentException("No se puede insertar un hu�sped nulo.");
        }
        if (buscar(reserva) != null) {
            throw new OperationNotSupportedException("El hu�sped ya est� registrado y no se admiten repetidos.");
        }

        MongoDB mongoDB = new MongoDB();
        MongoDatabase database = mongoDB.getBD();
        MongoCollection<Document> collection = database.getCollection(COLECCION);

        Document doc = mongoDB.getDocumento(reserva);
        collection.insertOne(doc);
    }

    public Reserva buscar(Reserva reserva) throws NoSuchElementException {
        MongoDB mongoDB = new MongoDB();
        MongoDatabase database = mongoDB.getBD();
        MongoCollection<Document> collection = database.getCollection(COLECCION);

        Document query = new Document("fecha_inicio_reserva", reserva.getFechaInicioReserva());
        Document result = collection.find(query).first();

        if (result == null) {
            throw new NoSuchElementException("El hu�sped a buscar no se encuentra en la colecci�n.");
        } else {
            return mongoDB.getReserva(result);
        }
    }

    public void borrar(Reserva reserva) throws NoSuchElementException {
        MongoDB mongoDB = new MongoDB();
        MongoDatabase database = mongoDB.getBD();
        MongoCollection<Document> collection = database.getCollection(COLECCION);

        Document query = new Document("fecha_inicio_reserva", reserva.getFechaInicioReserva());
        DeleteResult result = collection.deleteOne(query);

        if (result.getDeletedCount() == 0) {
            throw new NoSuchElementException("La reserva a borrar no se encuentra en la colección.");
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

    public ArrayList<Reserva> getReservas(Habitacion habitacion){
        ArrayList<Reserva> reservasHabitacion = new ArrayList<>();
        for ( Reserva reserva : coleccionReservas){
            if (reserva.getHabitacion().equals(habitacion)){
                reservasHabitacion.add(new Reserva(reserva));
            }
        }
        if (reservasHabitacion.isEmpty()) {
            throw new NoSuchElementException("No se encontraron reservas para la habitacion.");
        }
        return reservasHabitacion;
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
        MongoDB mongoDB = new MongoDB();
        MongoDatabase database = mongoDB.getBD();
        MongoCollection<Document> coleccionReservas = database.getCollection(COLECCION);
    }

    @Override
    public void terminar() {
        MongoDB mongoDB = new MongoDB();
        mongoDB.cerrarConexion();
    }

}
