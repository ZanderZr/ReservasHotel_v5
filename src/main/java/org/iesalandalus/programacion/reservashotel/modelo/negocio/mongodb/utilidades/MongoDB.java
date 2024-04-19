package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;

import javax.swing.*;
import org.bson.Document;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MongoDB {

    public static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final DateTimeFormatter FORMATO_DIA_HORA = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private static final String USUARIO = "reservashotel";
    private static final String CONTRASENA = "reservashotel-2024";
    private static final String SERVIDOR = "mongodb+srv://"+USUARIO+":"+CONTRASENA+"@clusterreservashotel.nkgaokt.mongodb.net/";

    private static final int PUERTO = 27017;
    private static final String BD = "reservashotel";
    public static final String HUESPED = "huesped";
    public static final String NOMBRE = "nombre";
    public static final String DNI = "dni";
    public static final String TELEFONO = "telefono";
    public static final String CORREO = "correo";
    public static final String FECHA_NACIMIENTO = "fecha_nacimiento";
    public static final String HUESPED_DNI = HUESPED + "." + DNI;
    public static final String HABITACION = "habitacion";
    public static final String IDENTIFICADOR = "identificador";
    public static final String PLANTA = "planta";
    public static final String PUERTA = "puerta";
    public static final String PRECIO = "precio";
    public static final String HABITACION_IDENTIFICADOR = HABITACION + "." + IDENTIFICADOR;
    public static final String TIPO = "tipo";
    public static final String HABITACION_TIPO = HABITACION + "." + TIPO;
    public static final String TIPO_SIMPLE = "SIMPLE";
    public static final String TIPO_DOBLE = "DOBLE";
    public static final String TIPO_TRIPLE = "TRIPLE";
    public static final String TIPO_SUITE = "SUITE";
    public static final String CAMAS_INDIVIDUALES = "camas_individuales";
    public static final String CAMAS_DOBLES = "camas_dobles";
    public static final String BANOS = "banos";
    public static final String JACUZZI = "jacuzzi";
    public static final String REGIMEN = "regimen";
    public static final String FECHA_INICIO_RESERVA = "fecha_inicio_reserva";
    public static final String FECHA_FIN_RESERVA = "fecha_fin_reserva";
    public static final String CHECKIN = "checkin";
    public static final String CHECKOUT = "checkout";
    public static final String PRECIO_RESERVA = "precio_reserva";
    public static final String NUMERO_PERSONAS = "numero_personas";

    private MongoClient conexion = null;

    // --- METODOS --

    public MongoDB(){
    }

    public MongoDatabase getBD(){
        if (conexion == null) {
            establecerConexion();
        }
        return conexion.getDatabase(BD);
    }

    private void establecerConexion(){
        try{
            ConnectionString connectionString = new ConnectionString(SERVIDOR);
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .build();
            conexion = MongoClients.create(settings);
        } catch (MongoException e){
            JOptionPane.showMessageDialog(null, "Error al establecer la conexión" + e.toString());
        }
    }

    public void cerrarConexion(){
        if (conexion != null) {
            conexion.close();
        }
    }
    public Document getDocumento(Huesped huesped) {
        Document documentoHuesped = new Document();
        documentoHuesped.append(NOMBRE, huesped.getNombre())
                .append(DNI, huesped.getDni())
                .append(TELEFONO, huesped.getTelefono())
                .append(CORREO, huesped.getCorreo())
                .append(FECHA_NACIMIENTO, huesped.getFechaNacimiento().format(FORMATO_DIA));
        return documentoHuesped;
    }
    public Huesped getHuesped(Document documentoHuesped) {
        String nombre = documentoHuesped.getString(NOMBRE);
        String dni = documentoHuesped.getString(DNI);
        String telefono = documentoHuesped.getString(TELEFONO);
        String correo = documentoHuesped.getString(CORREO);
        LocalDate fechaNacimiento = LocalDate.parse(documentoHuesped.getString(FECHA_NACIMIENTO), FORMATO_DIA);

        // Crear y devolver un nuevo objeto Huesped con los valores obtenidos
        return new Huesped(nombre, dni, telefono, correo, fechaNacimiento);
    }
    public Document getDocumento(Habitacion habitacion) {
        Document documentoHabitacion = new Document();
        documentoHabitacion.append(IDENTIFICADOR, habitacion.getIdentificador())
                .append(PLANTA, habitacion.getPlanta())
                .append(PUERTA, habitacion.getPuerta())
                .append(PRECIO, habitacion.getPrecio());

        // Aquí puedes añadir campos específicos de cada tipo de habitación
        if (habitacion instanceof Suite) {
            Suite suite = (Suite) habitacion;
            documentoHabitacion.append(TIPO, TIPO_SUITE)
                    .append(BANOS, suite.getNumBanos())
                    .append(JACUZZI, suite.isTieneJacuzzi());
        } else if (habitacion instanceof Simple) {
            Simple simple = (Simple) habitacion;
            documentoHabitacion.append(TIPO, TIPO_SIMPLE);
        } else if (habitacion instanceof Doble) {
            Doble doble = (Doble) habitacion;
            documentoHabitacion.append(CAMAS_INDIVIDUALES, doble.getNumCamasIndividuales())
                    .append(CAMAS_DOBLES, doble.getNumCamasDobles());
        } else if (habitacion instanceof Triple) {
            Triple triple = (Triple) habitacion;
            documentoHabitacion.append(BANOS, triple.getNumBanos())
                    .append(CAMAS_INDIVIDUALES, triple.getNumCamasIndividuales())
                    .append(CAMAS_DOBLES, triple.getNumCamasDobles());
        }
        documentoHabitacion.append(HABITACION_TIPO, TIPO);
        return documentoHabitacion;
    }
    public Habitacion getHabitacion(Document documentoHabitacion) {
        String tipo = documentoHabitacion.getString(TIPO);
        Habitacion habitacion;

        switch (tipo) {
            case TIPO_SIMPLE:
                habitacion = new Simple(
                        documentoHabitacion.getInteger(PLANTA),
                        documentoHabitacion.getInteger(PUERTA),
                        documentoHabitacion.getDouble(PRECIO));
                break;
            case TIPO_DOBLE:
                habitacion = new Doble(
                        documentoHabitacion.getInteger(PLANTA),
                        documentoHabitacion.getInteger(PUERTA),
                        documentoHabitacion.getDouble(PRECIO),
                        documentoHabitacion.getInteger(CAMAS_INDIVIDUALES),
                        documentoHabitacion.getInteger(CAMAS_DOBLES));
                break;
            case TIPO_TRIPLE:
                habitacion = new Triple(
                        documentoHabitacion.getInteger(PLANTA),
                        documentoHabitacion.getInteger(PUERTA),
                        documentoHabitacion.getDouble(PRECIO),
                        documentoHabitacion.getInteger(BANOS),
                        documentoHabitacion.getInteger(CAMAS_INDIVIDUALES),
                        documentoHabitacion.getInteger(CAMAS_DOBLES));
                break;
            case TIPO_SUITE:
                habitacion = new Suite(
                        documentoHabitacion.getInteger(PLANTA),
                        documentoHabitacion.getInteger(PUERTA),
                        documentoHabitacion.getDouble(PRECIO),
                        documentoHabitacion.getInteger(BANOS),
                        documentoHabitacion.getBoolean(JACUZZI));
                break;
            default:
                throw new IllegalArgumentException("Tipo de habitación desconocido: " + tipo);
        }

        return habitacion;
    }
    public Document getDocumento(Reserva reserva) {
        Document documentoReserva = new Document();
        documentoReserva.append(HUESPED_DNI, reserva.getHuesped().getDni())
                .append(HABITACION_IDENTIFICADOR, reserva.getHabitacion().getIdentificador())
                .append(REGIMEN, reserva.getRegimen())
                .append(FECHA_INICIO_RESERVA, reserva.getFechaInicioReserva().format(FORMATO_DIA))
                .append(CHECKIN, reserva.getCheckIn().format(FORMATO_DIA_HORA))
                .append(CHECKOUT, reserva.getCheckOut().format(FORMATO_DIA_HORA))
                .append(PRECIO_RESERVA, reserva.getPrecio())
                .append(NUMERO_PERSONAS, reserva.getNumeroPersonas());
        return documentoReserva;
    }
    public Reserva getReserva(Document documentoReserva){

        Document huespedDoc = documentoReserva.get(HUESPED, Document.class); // Obtener el documento del huesped
        Huesped huesped = getHuesped(huespedDoc);

        Document habitacionDoc = documentoReserva.get(HABITACION, Document.class);
        Habitacion habitacion = getHabitacion(habitacionDoc);

        // Obtener el valor del campo REGIMEN como una cadena
        String regimenStr = documentoReserva.getString(REGIMEN);

        // Convertir la cadena a un enum Regimen
        Regimen regimen = Regimen.valueOf(regimenStr);

        String fechaInicioReservaString = documentoReserva.getString(FECHA_INICIO_RESERVA);
        LocalDate fechaInicioReservaLocalDate = LocalDate.parse(fechaInicioReservaString, FORMATO_DIA);
        String fechaFinReservaString = documentoReserva.getString(FECHA_FIN_RESERVA);
        LocalDate fechaFinReservaLocalDate = LocalDate.parse(fechaFinReservaString, FORMATO_DIA);
        Integer numeroPersonas = documentoReserva.getInteger(NUMERO_PERSONAS);

        return new Reserva(huesped, habitacion, regimen, fechaInicioReservaLocalDate, fechaFinReservaLocalDate, numeroPersonas);
    }
}
