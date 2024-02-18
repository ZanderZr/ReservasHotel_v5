package org.iesalandalus.programacion.reservashotel.vista;

public enum Opcion {
    SALIR("1.- Salir") {
        @Override
        public void ejecutar() {
            vista.terminar();
            System.out.println("Cerrando la aplicaci?n...");
            System.exit(0);
        }
    },
    INSERTAR_HUESPED("2.- Insertar huesped") {
        @Override
        public void ejecutar() {
            vista.insertarHuesped();;
        }
    },
    BUSCAR_HUESPED("3.- Buscar huesped") {
        @Override
        public void ejecutar() {
            vista.buscarHuesped();
        }
    },
    BORRAR_HUESPED("4.- Borrar huesped") {
        @Override
        public void ejecutar() {
            vista.borrarHuesped();
        }
    },
    MOSTRAR_HUESPEDES("5.- Mostrar huespedes") {
        @Override
        public void ejecutar() {
            vista.mostrarHuespedes();
        }
    },
    INSERTAR_HABITACION("6.- Insertar habitacion") {
        @Override
        public void ejecutar() {
            vista.insertarHabitacion();
        }
    },
    BUSCAR_HABITACION("7.- Buscar habitacion") {
        @Override
        public void ejecutar() {
            vista.buscarHabitacion();
        }
    },
    BORRAR_HABITACION("8.- Borrar habitacion") {
        @Override
        public void ejecutar() {
            vista.borrarHabitacion();
        }
    },
    MOSTRAR_HABITACIONES("9.- Mostrar habitaciones") {
        @Override
        public void ejecutar() {
            vista.mostrarHabitaciones();
        }
    },
    INSERTAR_RESERVA("10.- Insertar reserva") {
        @Override
        public void ejecutar() {
            vista.insertarReserva();
        }
    },
    ANULAR_RESERVA("11.- Anular reserva") {
        @Override
        public void ejecutar() {
            vista.anularReserva();
        }
    },
    MOSTRAR_RESERVAS("12.- Mostrar reservas") {
        @Override
        public void ejecutar() {
            vista.mostrarReservas();
        }
    },
    LISTAR_RESERVAS_HUESPED("13.- Consultar disponibilidad") {
        @Override
        public void ejecutar() {
            vista.mostrarReservasHuesped();
        }
    },
    LISTAR_RESERVAS_TIPO_HABITACION("14.- Consultar disponibilidad") {
        @Override
        public void ejecutar() {
            vista.mostrarReservasTipoHabitacion();
        }
    },
    CONSULTAR_DISPONIBILIDAD("15.- Consultar disponibilidad") {
        @Override
        public void ejecutar() {
            vista.comprobarDisponibilidad();
        }
    },
    REALIZAR_CHECKIN("16.- Realizar check-in") {
        @Override
        public void ejecutar() {
            vista.realizarCheckin();
        }
    },
    REALIZAR_CHECKOUT("17.- Realizar check-out") {
        @Override
        public void ejecutar() {
            vista.realizarCheckout();
        }
    };

    private String mensajeAMostrar;

    private static Vista vista;
    private Opcion(String mensajeAMostrar){
        this.mensajeAMostrar = mensajeAMostrar;
    }

    static void setVista(Vista vista){
        Opcion.vista = vista;
    }
    public abstract void ejecutar();
    @Override
    public String toString() {
        return mensajeAMostrar;
    }
}
