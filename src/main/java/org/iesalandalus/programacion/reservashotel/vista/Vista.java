package org.iesalandalus.programacion.reservashotel.vista;

import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.utilidades.Entrada;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Vista {

    private Controlador controlador;

    public Vista() {
        Opcion.setVista(this);
    }

    public void setControlador(Controlador controlador) {
        if (controlador == null) {
            throw new IllegalArgumentException("El controlador no puede ser nulo.");
        }
        this.controlador = controlador;
    }

    public void comenzar() {
        Opcion opcion;
        do {
            Consola.mostrarMenu();
            opcion = Consola.elegirOpcion();
            opcion.ejecutar();
        } while (opcion != Opcion.SALIR);
        terminar();
    }

    public void terminar() {
        System.out.println("Gracias por utilizar nuestra aplicaci�n. �Hasta pronto!");
        controlador.terminar();
    }


    // Huesped -----------------------------------------------------
    public void insertarHuesped() {

        try {
            Huesped nuevoHuesped = Consola.leerHuesped();
            ArrayList<Huesped> huespedes = controlador.getHuespedes();
            if (!huespedes.equals(nuevoHuesped)) {
                try {
                    controlador.insertar(nuevoHuesped);
                    System.out.println("Hu�sped insertado correctamente.");
                } catch (OperationNotSupportedException e) {
                    System.out.println("Error al insertar el hu�sped: " + e.getMessage());
                }
            } else {
                System.out.println("El hu�sped ya est� registrado en el sistema.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error al leer el hu�sped: " + e.getMessage());
        }
    }

    public void buscarHuesped() {
        try {
            Huesped huespedFicticio = Consola.getHuespedPorDni();
            Huesped huespedEncontrado = controlador.buscar(huespedFicticio);
            if (huespedEncontrado != null) {
                System.out.println("Hu�sped encontrado: " + huespedEncontrado);
            } else {
                System.out.println("No se encontr� ning�n hu�sped con el DNI proporcionado.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error al buscar el hu�sped: " + e.getMessage());
        }
    }

    public void borrarHuesped() {
        try {
            Huesped huespedFicticio = Consola.getHuespedPorDni();
            Huesped huespedBorrado = controlador.buscar(huespedFicticio);
            if (huespedBorrado != null) {
                controlador.borrar(huespedBorrado);
                System.out.println("Hu�sped borrado: " + huespedBorrado);
            } else {
                System.out.println("No se encontr� ning�n hu�sped con el DNI proporcionado.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error al buscar el hu�sped: " + e.getMessage());
        } catch (OperationNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public void mostrarHuespedes() {
        ArrayList<Huesped> huespedes = controlador.getHuespedes();
        if (huespedes.size() == 0) {
            System.out.println("No hay huéspedes almacenados.");
        } else {
            huespedes.sort(Comparator.comparing(Huesped::getNombre)); // expresión lambda que compara los huéspedes por su nombre.
            System.out.println("Lista de huéspedes almacenados: ");
            for (Huesped huesped : huespedes) {
                System.out.println(huesped);
            }
        }
    }

    // Habitacion -----------------------------------------------------
    public void insertarHabitacion() {
        try {
            Habitacion nuevaHabitacion = Consola.leerHabitacion();
            ArrayList<Habitacion> habitaciones = controlador.getHabitaciones();
            if (!habitaciones.equals(nuevaHabitacion)) {
                try {
                    controlador.insertar(nuevaHabitacion);
                    System.out.println("Habitacion insertada correctamente.");
                } catch (OperationNotSupportedException e) {
                    System.out.println("Error al insertar la habitacion: " + e.getMessage());
                }
            } else {
                System.out.println("La habitacion ya est� registrada en el sistema.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error al leer la habitaci�n: " + e.getMessage());
        }
    }

    public void buscarHabitacion() {
        try {
            Habitacion habitacionFicticia = Consola.leerHabitacionPorIdentificador();
            Habitacion habitacionEncontrada = controlador.buscar(habitacionFicticia);
            if (habitacionEncontrada != null) {
                System.out.println("Habitaci�n encontrada: " + habitacionEncontrada);
            } else {
                System.out.println("La habitaci�n buscada no se encuentra en la colecci�n.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error al buscar la habitaci�n: " + e.getMessage());
        }
    }

    public void borrarHabitacion() {
        try {
            Habitacion habitacionFicticia = Consola.leerHabitacionPorIdentificador();
            Habitacion habitacionBorrada = controlador.buscar(habitacionFicticia);
            if (habitacionBorrada != null) {
                controlador.borrar(habitacionBorrada);
                System.out.println("Habitacion borrada: " + habitacionBorrada);
            } else {
                System.out.println("No se encontro ninguna habitacion con el identificador proporcionado.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error al buscar la habitaci�n: " + e.getMessage());
        } catch (OperationNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public void mostrarHabitaciones() {
        ArrayList<Habitacion> habitaciones = controlador.getHabitaciones();

        if (habitaciones.size() == 0) {
            System.out.println("No hay habitaciones almacenadas.");
        } else {
            habitaciones.sort(Comparator.comparing(Habitacion::getPlanta) // expresión lambda que compara las habitaciones por su planta
                    .thenComparing(Habitacion::getPuerta)); // y despues por su puerta.
            System.out.println("Lista de habitaciones almacenadas:");
            for (Habitacion habitacion : habitaciones) {
                System.out.println(habitacion);
            }
        }
    }

    // Reserva -----------------------------------------------------
    public void insertarReserva() {
        Habitacion habitacionDisponible = null;
        try {
            Reserva reservaFicticia = Consola.leerReserva();
            ArrayList<Reserva> reservas = controlador.getReservas();
            if (reservaFicticia == null) {
                System.out.println("No se pudo leer la reserva.");
                return;
            }

            Huesped huespedFicticio = reservaFicticia.getHuesped();
            Habitacion habitacionFicticia = reservaFicticia.getHabitacion();

            Huesped huespedReal = controlador.buscar(huespedFicticio);
            Habitacion habitacionReal = controlador.buscar(habitacionFicticia);

            if (huespedReal == null) {
                throw new NoSuchElementException("El hu�sped con el DNI proporcionado no se encuentra en el sistema.");
            }

            if (habitacionReal == null) {
                throw new NoSuchElementException("La habitaci�n con el identificador proporcionado no se encuentra en el sistema.");
            }

            Reserva nuevaReserva = new Reserva(huespedReal, habitacionReal, reservaFicticia.getRegimen(), reservaFicticia.getFechaInicioReserva(), reservaFicticia.getFechaFinReserva(), reservaFicticia.getNumeroPersonas());

            if (nuevaReserva.getNumeroPersonas() < 0 || nuevaReserva.getNumeroPersonas() > nuevaReserva.getHabitacion().getNumeroMaximoPersonas()) {
                insertarReserva();
            }
            Habitacion habitacionDeseada = nuevaReserva.getHabitacion();

            if (habitacionDeseada instanceof Simple){
                habitacionDisponible = consultarDisponibilidad(TipoHabitacion.SIMPLE, nuevaReserva.getFechaInicioReserva(), nuevaReserva.getFechaFinReserva());
            } else if (habitacionDeseada instanceof Doble){
                habitacionDisponible = consultarDisponibilidad(TipoHabitacion.DOBLE, nuevaReserva.getFechaInicioReserva(), nuevaReserva.getFechaFinReserva());
            } else if (habitacionDeseada instanceof Triple){
                habitacionDisponible = consultarDisponibilidad(TipoHabitacion.TRIPLE, nuevaReserva.getFechaInicioReserva(), nuevaReserva.getFechaFinReserva());
            } else if (habitacionDeseada instanceof Suite){
                habitacionDisponible = consultarDisponibilidad(TipoHabitacion.SUITE, nuevaReserva.getFechaInicioReserva(), nuevaReserva.getFechaFinReserva());
            }

            if (habitacionDisponible != null) {
                nuevaReserva.setHabitacion(habitacionDisponible);
                if (!reservas.equals(reservaFicticia)) {
                    try {
                        controlador.insertar(nuevaReserva);
                        System.out.println("Reserva insertada correctamente.");
                    } catch (OperationNotSupportedException e) {
                        System.out.println("Error al insertar la reserva: " + e.getMessage());
                    }
                } else {
                    System.out.println("La reserva ya est� registrada en el sistema.");
                }
            } else {
                System.out.println("No hay disponibilidad para el tipo de habitaci�n deseada en el periodo indicado.");
            }
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
    }

    public void mostrarReservasHuesped() {
        Huesped huesped = Consola.getHuespedPorDni();
        listarReservas(huesped);
    }

    public void listarReservas(Huesped huesped) {
        int i = 0;
        try {
            ArrayList<Reserva> reservasH = controlador.getReservas(huesped);
            if (reservasH.size() == 0) {
                System.out.println("No hay reservas para el hu�sped indicado.");
            } else {
                reservasH.sort(Comparator.comparing(Reserva::getFechaInicioReserva).reversed()
                        .thenComparing(reserva -> reserva.getHabitacion().getPlanta())
                        .thenComparing(reserva -> reserva.getHabitacion().getPuerta()));
                System.out.println("Lista de reservas del hu�sped:");
                for (Reserva reserva : reservasH) {
                    System.out.print(i + ".- ");
                    System.out.println(reserva);
                    i++;
                }
            }
        } catch (Exception e) {
            System.out.println("Error al listar las reservas: " + e.getMessage());
        }
    }

    public void mostrarReservasTipoHabitacion() {
        Habitacion hab = Consola.leerHabitacionPorIdentificador();
        TipoHabitacion tipoHabitacion = null;
        for (Habitacion habitacion : controlador.getHabitaciones()) {
            if (hab.equals(habitacion)) {
                if (habitacion instanceof Simple) {
                    tipoHabitacion = TipoHabitacion.SIMPLE;
                } else if (habitacion instanceof Doble) {
                    tipoHabitacion = TipoHabitacion.DOBLE;
                } else if (habitacion instanceof Triple) {
                    tipoHabitacion = TipoHabitacion.TRIPLE;
                } else if (habitacion instanceof Suite) {
                    tipoHabitacion = TipoHabitacion.SUITE;
                } else {
                    System.out.println("Tipo de habitación desconocido");
                }
            }
        }
        listarReservas(tipoHabitacion);
    }

    public void comprobarDisponibilidad() {

        TipoHabitacion tipoHabitacion = Consola.leerTipoHabitacion();
        LocalDate fecIn = Consola.leerFecha("Introduce la fecha de inicio de la reserva en formato (dd/MM/yyyy)");
        LocalDate fecFin = Consola.leerFecha("Introduce la fecha de fin de la reserva en formato (dd/MM/yyyy)");

        Habitacion h = consultarDisponibilidad(tipoHabitacion,fecIn,fecFin);

        h.toString();
    }

    public void listarReservas(TipoHabitacion tipoHabitacion) {
        int i = 0;
        try {
            ArrayList<Reserva> reservasTHab = controlador.getReservas(tipoHabitacion);
            if (reservasTHab.size() == 0) {
                System.out.println("No hay reservas para el hu�sped indicado.");
            } else {
                reservasTHab.sort(Comparator.comparing(Reserva::getFechaInicioReserva).reversed()
                        .thenComparing(reserva -> reserva.getHuesped().getNombre()));
                System.out.println("Lista de reservas del hu�sped:");
                for (Reserva reserva : reservasTHab) {
                    System.out.print(i + ".- ");
                    System.out.print(reserva);
                    i++;
                }
            }
        } catch (Exception e) {
            System.out.println("Error al listar las reservas: " + e.getMessage());
        }
    }

    public ArrayList<Reserva> getReservasAnulables(ArrayList<Reserva> reservasAAnular) {
        ArrayList<Reserva> reservasAnulables = new ArrayList<>();
        LocalDate hoy = LocalDate.now(); // Obtenemos la fecha actual.

        for (Reserva reserva : reservasAAnular) {
            if (reserva.getFechaInicioReserva().isAfter(hoy)) {
                // Si la fecha de inicio de la reserva a�n no ha llegado, es anulable.
                reservasAnulables.add(reserva);
            }
        }
        // Convertimos la lista de reservas anulables a un array y lo devolvemos.
        return reservasAnulables;
    }

    public void anularReserva() {
        try {
            System.out.println("Anular su reserva:");
            Huesped huespedFicticio = Consola.getHuespedPorDni();
            ArrayList<Reserva> reservasAnulables = getReservasAnulables(controlador.getReservas(huespedFicticio));

            if (reservasAnulables.size() == 0) {
                throw new NoSuchElementException("El huésped no tiene reservas anulables.");
            } else {
                System.out.println("El huésped tiene las siguientes reservas anulables:");
                listarReservas(huespedFicticio);
                System.out.println("Introduce el número de la reserva que deseas anular:");
                int indice = Entrada.entero();
                if (indice >= 0 && indice < reservasAnulables.size()) {
                    controlador.borrar(reservasAnulables.get(indice));
                    System.out.println("La reserva seleccionada ha sido anulada.");
                } else {
                    System.out.println("Número de reserva no válido.");
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al anular la reserva: " + e.getMessage());
        }
    }

    public void mostrarReservas() {
        ArrayList<Reserva> reservas = controlador.getReservas();
        if (reservas.size() == 0) {
            System.out.println("No hay reservas almacenadas.");
        } else {
            reservas.sort(Comparator.comparing(Reserva::getFechaInicioReserva).reversed()
                    .thenComparing(reserva -> reserva.getHabitacion().getPlanta())
                    .thenComparing(reserva -> reserva.getHabitacion().getPuerta()));
            System.out.println("Lista de todas las reservas almacenadas:");
            for (Reserva reserva : reservas) {
                System.out.println(reserva);
            }
        }
    }

    public Habitacion consultarDisponibilidad(TipoHabitacion tipoHabitacion, LocalDate fechaInicio, LocalDate fechaFin) {
        boolean estaDisponible = false;
        for (Habitacion habitacion : controlador.getHabitaciones()) {
            if (habitacion instanceof Simple && tipoHabitacion == TipoHabitacion.SIMPLE) {
                estaDisponible = true;
            } else if (habitacion instanceof Doble && tipoHabitacion == TipoHabitacion.DOBLE) {
                estaDisponible = true;
            } else if (habitacion instanceof Triple && tipoHabitacion == TipoHabitacion.TRIPLE) {
                estaDisponible = true;
            } else if (habitacion instanceof Suite && tipoHabitacion == TipoHabitacion.SUITE) {
                estaDisponible = true;
            }
            for (Reserva reserva : controlador.getReservas()) {
                if (reserva.getHabitacion().equals(habitacion) && !reserva.getFechaFinReserva().isBefore(fechaInicio) && !reserva.getFechaInicioReserva().isAfter(fechaFin)) {
                    estaDisponible = false;
                    break;
                }
            }

            if (estaDisponible) {
                return habitacion;
            }
        }
            System.out.println("Esa habitacion no est� disponible.");
            return null;
        }

        public void realizarCheckin () {
            System.out.println("Introduce el DNI del huésped que ha realizado la reserva");
            try {
                Huesped huesped = controlador.buscar(Consola.getHuespedPorDni());
                ArrayList<Reserva> reservasHuesped = controlador.getReservas(huesped);

                if (reservasHuesped.size() > 0) {
                    listarReservas(huesped);
                    System.out.println();
                    System.out.println("Elija a qué reserva quiere hacer el checkin introduciendo su número:");
                    int eleccion = Entrada.entero();
                    if (eleccion >= 0 && eleccion < reservasHuesped.size()) {
                        controlador.realizarCheckin(reservasHuesped.get(eleccion), LocalDateTime.now());
                        System.out.println("El check-in para la reserva seleccionada se ha realizado.");
                    } else {
                        System.out.println("Número de reserva no válido. Por favor, inténtalo de nuevo.");
                        realizarCheckin();
                    }
                } else {
                    System.out.println("No se encontraron reservas para el huésped proporcionado. Por favor, introduce un DNI válido.");
                    realizarCheckin();
                }
            } catch (NoSuchElementException e) {
                System.out.println("Error a realizar el check-in");
            }
        }
        public void realizarCheckout () {
            System.out.println("Introduce el DNI del huésped que ha realizado la reserva");
            try {
                Huesped huesped = controlador.buscar(Consola.getHuespedPorDni());
                ArrayList<Reserva> reservasHuesped = controlador.getReservas(huesped);

                if (reservasHuesped.size() > 0) {
                    listarReservas(huesped);
                    System.out.println("Elija a qué reserva quiere hacer el checkout introduciendo su número:");
                    int eleccion = Entrada.entero();
                    if (eleccion >= 0 && eleccion < reservasHuesped.size()) {
                        controlador.realizarCheckout(reservasHuesped.get(eleccion), LocalDateTime.now());
                        System.out.println("El checkout para la reserva seleccionada se ha realizado.");
                    } else {
                        System.out.println("Número de reserva no válido. Por favor, inténtalo de nuevo.");
                        realizarCheckout();
                    }
                } else {
                    System.out.println("No se encontraron reservas para el huésped proporcionado. Por favor, introduce un DNI válido.");
                    realizarCheckout();
                }
            } catch (NoSuchElementException e) {
                System.out.println("Error a realizar el check-out");
            }
        }


    }