package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHuespedes;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades.MongoDB;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Huespedes implements IHuespedes {

    private static final String COLECCION = "huespedes";
    private ArrayList<Huesped> coleccionHuespedes;

    public Huespedes() {
        this.coleccionHuespedes = new ArrayList<>();
    }

    public ArrayList<Huesped> get(){
        MongoDB mongoDB = new MongoDB();
        MongoDatabase database = mongoDB.getBD();
        MongoCollection<Document> collection = database.getCollection(COLECCION);

        ArrayList<Huesped> huespedes = new ArrayList<>();
        for (Document doc : collection.find().sort(Sorts.ascending("dni"))) {
            Huesped huesped = mongoDB.getHuesped(doc);
            huespedes.add(huesped);
        }

        return huespedes;
    }

    public int getTamano(){
        MongoDB mongoDB = new MongoDB();
        MongoDatabase database = mongoDB.getBD();
        MongoCollection<Document> collection = database.getCollection(COLECCION);

        long num = collection.countDocuments();

        return (int) num;
    }

    public void insertar(Huesped huesped) throws OperationNotSupportedException {
        if (huesped == null) {
            throw new IllegalArgumentException("No se puede insertar un hu�sped nulo.");
        }
        if (buscar(huesped) != null) {
            throw new OperationNotSupportedException("El hu�sped ya est� registrado y no se admiten repetidos.");
        }

        MongoDB mongoDB = new MongoDB();
        MongoDatabase database = mongoDB.getBD();
        MongoCollection<Document> collection = database.getCollection(COLECCION);

        Document doc = mongoDB.getDocumento(huesped);
        collection.insertOne(doc);
    }

    public Huesped buscar(Huesped huesped) throws NoSuchElementException{
        MongoDB mongoDB = new MongoDB();
        MongoDatabase database = mongoDB.getBD();
        MongoCollection<Document> collection = database.getCollection(COLECCION);

        Document query = new Document("dni", huesped.getDni());
        Document result = collection.find(query).first();

        if (result == null) {
            throw new NoSuchElementException("El hu�sped a buscar no se encuentra en la colecci�n.");
        } else {
            return mongoDB.getHuesped(result);
        }
    }

    public void borrar(Huesped huesped) throws NoSuchElementException {
        MongoDB mongoDB = new MongoDB();
        MongoDatabase database = mongoDB.getBD();
        MongoCollection<Document> collection = database.getCollection(COLECCION);

        Document query = new Document("dni", huesped.getDni());
        DeleteResult result = collection.deleteOne(query);

        if (result.getDeletedCount() == 0) {
            throw new NoSuchElementException("El huésped a borrar no se encuentra en la colección.");
        }
    }

    @Override
    public void comenzar() {
        MongoDB mongoDB = new MongoDB();
        MongoDatabase database = mongoDB.getBD();
        MongoCollection<Document> coleccionHuespedes = database.getCollection(COLECCION);
    }

    @Override
    public void terminar() {
        MongoDB mongoDB = new MongoDB();
        mongoDB.cerrarConexion();
    }
}
