package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHabitaciones;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades.MongoDB;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Habitaciones implements IHabitaciones {
    private ArrayList<Habitacion> coleccionHabitaciones;
    private static final String COLECCION = "habitaciones";

    public Habitaciones() {
        this.coleccionHabitaciones = new ArrayList<>();
    }


    public ArrayList<Habitacion> get() {
        MongoDB mongoDB = new MongoDB();
        MongoDatabase database = mongoDB.getBD();
        MongoCollection<Document> collection = database.getCollection(COLECCION);

        ArrayList<Habitacion> habitaciones = new ArrayList<>();
        for (Document doc : collection.find().sort(Sorts.ascending("identificador"))) {
            Habitacion habitacion = mongoDB.getHabitacion(doc);
            habitaciones.add(habitacion);
        }
        return habitaciones;
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
        MongoDB mongoDB = new MongoDB();
        MongoDatabase database = mongoDB.getBD();
        MongoCollection<Document> collection = database.getCollection(COLECCION);

        long tamano = collection.countDocuments();

        return (int) tamano;
    }

    public void insertar(Habitacion habitacion) throws OperationNotSupportedException {
        if (habitacion == null) {
            throw new IllegalArgumentException("No se puede insertar una habitación nula.");
        }
        if (buscar(habitacion) != null) {
            throw new OperationNotSupportedException("La habitación ya existe y no se admiten repetidos.");
        }
        MongoDB mongoDB = new MongoDB();
        MongoDatabase database = mongoDB.getBD();
        MongoCollection<Document> collection = database.getCollection(COLECCION);

        Document doc = mongoDB.getDocumento(habitacion);
        collection.insertOne(doc);
    }

    public Habitacion buscar(Habitacion habitacion) {
        MongoDB mongoDB = new MongoDB();
        MongoDatabase database = mongoDB.getBD();
        MongoCollection<Document> collection = database.getCollection(COLECCION);

        Document query = new Document("identificador", habitacion.getIdentificador());
        Document result = collection.find(query).first();

        if (result == null) {
            throw new NoSuchElementException("La habitacion a buscar no se encuentra en la colecci?n.");
        } else {
            return mongoDB.getHabitacion(result);
        }
    }

    public void borrar(Habitacion habitacion) throws NoSuchElementException {

        MongoDB mongoDB = new MongoDB();
        MongoDatabase database = mongoDB.getBD();
        MongoCollection<Document> collection = database.getCollection(COLECCION);

        Document query = new Document("identificador", habitacion.getIdentificador());
        DeleteResult result = collection.deleteOne(query);

        if (result.getDeletedCount() == 0) {
            throw new NoSuchElementException("La habitación proporcionada no se encuentra en la colección.");
        }
    }

    @Override
    public void comenzar() {
        MongoDB mongoDB = new MongoDB();
        MongoDatabase database = mongoDB.getBD();
        MongoCollection<Document> coleccionHabitaciones = database.getCollection(COLECCION);
    }

    @Override
    public void terminar() {
        MongoDB mongoDB = new MongoDB();
        mongoDB.cerrarConexion();
    }

}
