# Tarea: Reservas Hotel V4
## Profesor: Andrés Rubio del Río
## Alumno: Juan Carlos Lopez Fenoy

Ahora que ya tienes afianzados los conceptos básicos de la Programación Orientada a Objetos, es un buen momento para llevar a cabo algunas modificaciones que afectan al modelo diseñado ya que hay conceptos avanzados 
de la Programación Orientada a Objetos que aún no han sido vistos en las unidades anteriores y que deberían ser aplicados. Por tal motivo, en este **cuarto spring** se van a llevar a cabo una serie de modificaciones 
con las que se pretende mejorar el diseño de clases implementado hasta ahora.

Para ello te muestro un diagrama de clases para el mismo y poco a poco te iré explicando los diferentes pasos a realizar:

![Diagrama de clases para reservasHotel](https://raw.githubusercontent.com/andresrubiodelrio/ReservasHotel_v3/master/src/main/resources/uml/ReservasHotel_v3.png))

#### Primeros Pasos
1. Lo primero que debes hacer es realizar un **fork** del repositorio donde he colocado el esqueleto de este proyecto.
2. Clona tu repositorio remoto recién copiado en GitHub a un repositorio local que será donde irás realizando lo que a continuación se te pide.
3. Modifica el archivo `README.md` para que incluya tu nombre en el apartado "Alumno". Realiza tu **primer commit**.

#### TipoHabitacion
1. Actualiza el **Enum** `Tipo Habitacion` para que se ajuste a lo indicado en el diagrama de clases.
2. Realiza el **commit** correspondiente.

#### Herencia de Habitacion
1. Elimina de la clase el atributo `tipoHabitacion` ya que la clase `Habitacion` será ahora una **clase abstracta**, tal y como se indica en el diagrama de clases.
2. Haz los cambios necesarios en los constructores de la clase para ajustarlos al diagrama de clases.
3. Declara el método abstracto `getNumeroMaximoPersonas` cuyo valor dependerá de la habitación que se vaya a crear (**Suite, Simple, Doble Triple**).
4. Implementa la clase `Simple` como se muestra en el diagrama. Haz uso de `super` siempre que sea posible.
   1. Implementa adecuadamente el método `getNumeroMaximoPersonas` deberá devolver el valor almacenado en la constante `NUM_MAXIMO_PERSONAS` (1).
   2. Implementa el método `toString` para que la información de salida se corresponda con la exigida en el test. 
5. Implementa la clase `Doble` como se muestra en el diagrama. Haz uso de `super` siempre que sea posible.
   1. Implementa adecuadamente el método `getNumeroMaximoPersonas` que deberá devolver el valor almacenado en la constante `NUM_MAXIMO_PERSONAS` (2).
   2. Añade los atributos y los métodos `getters` y `setters` que aparecen en el diagrama de clases. Los dos atributos propios de la clase almacenarán el número de camas individuales y de camas dobles que hay en la habitación. Dado que es una habitación para dos personas, este tipo de habitaciones tendrá, o bien dos camas individuales o bien una sola cama doble, lo cual será comprobado a través del método `validaNumCamas`. Usa las constantes que aparecen en el diagrama asignándole el valor adecuado.
   3. Implementa el método `toString` para que la información de salida se corresponda con la exigida en el test.
6. Implementa la clase `Triple` como se muestra en el diagrama. Haz uso de `super` siempre que sea posible.
   1. Implementa adecuadamente el método `getNumeroMaximoPersonas` que deberá devolver el valor almacenado en la constante `NUM_MAXIMO_PERSONAS` (3).
   2. Añade los atributos y los métodos `getters` y `setters` que aparecen en el diagrama de clases. Los tres atributos propios de la clase almacenarán el número de baños de los que dispone la habitación, y el número de camas individuales y de camas dobles que hay en la habitación. Dado que es una habitación para tres personas, este tipo de habitaciones tendrá, o bien dos camas individuales y una doble o bien tres camas individuales, lo cual será comprobado a través del método `validaNumCamas`. Usa las constantes que aparecen en el diagrama asignándole el valor adecuado.
   3. Implementa el método `toString` para que la información de salida se corresponda con la exigida en el test.
7. Implementa la clase `Suite` como se muestra en el diagrama. Haz uso de `super` siempre que sea posible.           
   1. Implementa adecuadamente el método `getNumeroMaximoPersonas` que deberá devolver el valor almacenado en la constante `NUM_MAXIMO_PERSONAS` (4).
   2. Añade los atributos y los métodos `getters` y `setters` que aparecen en el diagrama de clases. Los dos atributos propios de la clase almacenarán el número de baños de los que dispone la habitación, y si tiene o no jacuzzi. Usa las constantes que aparecen en el diagrama asignándole el valor adecuado.
   3. Implementa el método `toString` para que la información de salida se corresponda con la exigida en el test.
8. Modifica todos aquellos métodos de la clase `Habitación` que deban ser actualizados debido a la aplicación de la herencia. 
9. Realiza el **commit** correspondiente.

#### Reserva
1. Reimplementa los métodos `getHabitacion` y `setHabitacion` de la clase `Reserva` ya que deberá devolverse una habitación u otra en función de la instancia de la habitación.
2. Modifica todos aquellos métodos de la clase `Reserva` que deban ser actualizados debido a la aplicación de la herencia en la clase `Habitacion`.
2. Realiza el **commit** correspondiente.

#### Paquete Negocio
1. Reimplementa todos los métodos de las clases pertenecientes al paquete negocio que se hayan visto afectadas por incluir la herencia en la clase `Habitacion`.
2. Las tres clases que tenía este paquete en la versión anterior deberán ser movidas al paquete memoria.
3. Crea en el paquete negocio las tres interfaces que se muestran en el diagrama de clases.
4. Modifica las clases del paquete memoria para que implementen la Interfaz que le corresponda.
5. Realiza el **commit** correspondiente.

#### Modelo
1. Modifica la clase `Modelo` para que se ajuste a lo mostrado en el diagrama de clases.
2. Realiza el **commit** correspondiente.

#### Controlador
1. Modifica la clase `Controlador` para que se ajuste a lo mostrado en el diagrama de clases.
2. Realiza el **commit** correspondiente.

#### Consola
1. Modifica el método `leerHabitación` que en función del tipo de habitación que elija el usuario deberá crear la instancia de la habitación (`Simple`, `Doble`, `Triple` o `Suite`) correspondiente a la opción elegida.
2. Realiza el **commit** correspondiente.

#### Vista
1. Modifica la clase `Vista` y añade los métodos:
   1. `mostrarReservasHuesped` que pedirá los datos del huesped (`getHuespedPorDni`) y llamará al método `listarReservas` de un huésped para que muestre todas las reservas de dicho huésped.
   2. `mostrarReservasTipoHabitación` que pedirá los datos de la habitación (`leerHabitacionPorIdentificador`) y llamará al método `listarReservas` de un tipo de habitación para que muestre las reservas de dicho tipo de habitación.
   3. `comprobarDisponibilidad` que pedirá todos los datos necesarios para llamar al método `consultarDisponibilidad`. Tras la respuesta de este método, mostrará por consola los datos de la habitación solicitada si hay disponibilidad, o un mensaje indicando que el tipo de habitación solicitado no está disponible.
   4. Realiza todos los cambios adicionales que sean necesarios para que la clase se ajuste a lo mostrado en el diagrama de clases.
2. Realiza el **commit** correspondiente.

#### Opcion
1. Modifica el enum `Opcion` teniendo en cuenta lo siguiente:
   1. Añade un atributo estático de tipo `Vista`.
   2. Implementa el método `setVista` tal y como aparece en el diagrama de clases y que deberá ser llamado desde el constructor de la clase `Vista`.
   3. Añade el método abstracto `ejecutar`, que deberá ser implementado en cada instancia del enum `Opcion`, llamando al método que le corresponda de la clase `Vista`. Con esto se pretende que cada opción no solo contenga el mensaje que debe mostrarse por pantalla, sino también, el método de la clase `Vista` que debe ser ejecutado cuando el usuario de la aplicación elija dicha opción.
2. Realiza el **commit** corresopndiente.
3. Finalmente, realiza el **push** hacia tu repositorio remoto en GitHub.

#### Se valorará:

- La indentación debe ser correcta en cada uno de los apartados.
- El nombre de las variables debe ser adecuado.
- Se debe utilizar la clase `Entrada` para realizar la entrada por teclado.
- El programa debe pasar todas las pruebas que van en el esqueleto del proyecto y toda entrada del programa será validada, para evitar que el programa termine abruptamente debido a una excepción. Además, que ni decir tiene, el programa no debe contener ningún error léxico, sintáctico, de dependencias, etc.
- La corrección ortográfica tanto en los comentarios como en los mensajes que se muestren al usuario.

