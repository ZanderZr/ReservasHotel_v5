package org.iesalandalus.programacion.reservashotel;

import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.Modelo;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.FactoriaFuenteDatos;
import org.iesalandalus.programacion.reservashotel.vista.Vista;

import javax.naming.OperationNotSupportedException;

    public class MainApp {

        public static void main(String[] args) throws OperationNotSupportedException {
            Vista vista = new Vista();
            Modelo modelo = procesarArgumentosFuenteDatos(args);
            Controlador controlador = new Controlador(modelo, vista);

            controlador.comenzar();
        }

        private static Modelo procesarArgumentosFuenteDatos(String[] args) {
            Modelo modelo;
            if (args.length > 0) {
                if (args[0].equals("-fdmemoria")) {
                    modelo = new Modelo(FactoriaFuenteDatos.MEMORIA.crearFuenteDatos());
                } else if (args[0].equals("-fdmongodb")) {
                    modelo = new Modelo(FactoriaFuenteDatos.MONGODB.crearFuenteDatos());
                } else {
                    throw new IllegalArgumentException("Argumento no reconocido. Usa -fdmemoria para memoria o -fdmongodb para MongoDB.");
                }
            } else {
                throw new IllegalArgumentException("Debes proporcionar un argumento. Usa -fdmemoria para memoria o -fdmongodb para MongoDB.");
            }
            return modelo;
        }
    }
