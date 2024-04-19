package org.iesalandalus.programacion.reservashotel.modelo.negocio;

import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.FuenteDatosMongoDB;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria.FuenteDatosMemoria;

public enum FactoriaFuenteDatos {

        MEMORIA {
            @Override
            public IFuenteDatos crearFuenteDatos() {
                return new FuenteDatosMemoria();
            }
        },

        MONGODB {
            @Override
            public IFuenteDatos crearFuenteDatos() {
                return new FuenteDatosMongoDB();
            }
        };

        public abstract IFuenteDatos crearFuenteDatos();
    }

