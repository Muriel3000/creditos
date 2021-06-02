package ar.com.ada.creditos;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import ar.com.ada.creditos.entities.*;
import ar.com.ada.creditos.excepciones.*;
import ar.com.ada.creditos.managers.*;

public class ABM {

    public static Scanner Teclado = new Scanner(System.in);

    protected ClienteManager ABMCliente = new ClienteManager();
    protected PrestamoManager ABMPrestamo = new PrestamoManager();

    public void iniciar() throws Exception {

        try {

            ABMCliente.setup();
            ABMPrestamo.setup();

            printOpciones();

            int opcion = Teclado.nextInt();
            Teclado.nextLine();

            while (opcion > 0) {

                switch (opcion) {
                    case 1:

                        try {
                            alta();
                        } catch (ClienteDNIException exdni) {
                            System.out.println("Error en el DNI. Indique uno valido");
                        }
                        break;

                    case 2:
                        baja();
                        break;

                    case 3:
                        modifica();
                        break;

                    case 4:
                        listar();
                        break;

                    case 5:
                        listarPorNombre();
                        break;

                    case 6:
                        asignarPrestamo();
                        break;

                    case 7:
                        mostrarPrestamos();
                        break; 

                    default:
                        System.out.println("La opcion no es correcta.");
                        break;
                }

                printOpciones();

                opcion = Teclado.nextInt();
                Teclado.nextLine();
            }

            // Hago un safe exit del manager
            ABMCliente.exit();

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Que lindo mi sistema,se rompio mi sistema");
            throw e;
        } finally {
            System.out.println("Saliendo del sistema, bye bye...");

        }

    }

    public void alta() throws Exception {
        Cliente cliente = new Cliente();
        System.out.println("Ingrese el nombre:");
        cliente.setNombre(Teclado.nextLine());
        System.out.println("Ingrese el DNI:");
        cliente.setDni(Teclado.nextInt());
        Teclado.nextLine();
        System.out.println("Ingrese la direccion:");
        cliente.setDireccion(Teclado.nextLine());

        System.out.println("Ingrese el Direccion alternativa(OPCIONAL):");

        String domAlternativo = Teclado.nextLine();

        if (domAlternativo != null)
            cliente.setDireccionAlternativa(domAlternativo);

        System.out.println("Ingrese fecha de nacimiento:");
        Date fecha = null;
        DateFormat dateformatArgentina = new SimpleDateFormat("dd/MM/yy");

        fecha = dateformatArgentina.parse(Teclado.nextLine());
        cliente.setFechaNacimiento(fecha);

        //Ponerle un prestamo de 10mil a un cliente recien creado.

        this.crearYAsignarPrestamo(cliente);
        /* Prestamo prestamo = new Prestamo();

        System.out.println("Ingrese el importe del prestamo solicitado: ");
        prestamo.setImporte(Teclado.nextBigDecimal());
        System.out.println("Ingrese la cantidad de cuotas que desea abonar: ");
        prestamo.setCuotas(Teclado.nextInt());
        Teclado.nextLine();
        System.out.println("Ingrese fecha de otorgamiento del credito(dd/mm/yy): ");
        fecha = dateformatArgentina.parse(Teclado.nextLine());
        prestamo.setFecha(fecha);
        //prestamo.setImporte(new BigDecimal(10000));
        //prestamo.setCuotas(5);
        //prestamo.setFecha(new Date());
        prestamo.setFechaAlta(new Date());
        prestamo.setCliente(cliente);  */

        ABMCliente.create(cliente); 

        /*
         * Si concateno el OBJETO directamente, me trae todo lo que este en el metodo
         * toString() mi recomendacion es NO usarlo para imprimir cosas en pantallas, si
         * no para loguear info Lo mejor es usar:
         * System.out.println("Cliente generada con exito.  " + cliente.getClienteId);
         */

        System.out.println("Cliente generado con exito.  " + cliente);

    }

    public void baja() {
        System.out.println("Ingrese el nombre:");
        String nombre = Teclado.nextLine();
        System.out.println("Ingrese el ID de Cliente:");
        int id = Teclado.nextInt();
        Teclado.nextLine();
        Cliente clienteEncontrado = ABMCliente.read(id);

        if (clienteEncontrado == null) {
            System.out.println("Cliente no encontrado.");

        } else {

            try {

                ABMCliente.delete(clienteEncontrado);
                System.out
                        .println("El registro del cliente " + clienteEncontrado.getClienteId() + " ha sido eliminado.");
            } catch (Exception e) {
                System.out.println("Ocurrio un error al eliminar una cliente. Error: " + e.getCause());
            }

        }
    }

    // Ejemplo
    public void bajaPorDNI() {
        System.out.println("Ingrese el nombre:");
        String nombre = Teclado.nextLine();
        System.out.println("Ingrese el DNI de Cliente:");
        int dni = Teclado.nextInt();
        Cliente clienteEncontrado = ABMCliente.readByDNI(dni);

        if (clienteEncontrado == null) {
            System.out.println("Cliente no encontrado.");

        } else {
            ABMCliente.delete(clienteEncontrado);
            System.out.println("El registro del DNI " + clienteEncontrado.getDni() + " ha sido eliminado.");
        }
    }

    public void modifica() throws Exception {
        // System.out.println("Ingrese el nombre de la cliente a modificar:");
        // String n = Teclado.nextLine();

        System.out.println("Ingrese el ID de la cliente a modificar:");
        int id = Teclado.nextInt();
        Teclado.nextLine();
        Cliente clienteEncontrado = ABMCliente.read(id);

        if (clienteEncontrado != null) {

            // RECOMENDACION NO USAR toString(), esto solo es a nivel educativo.
            System.out.println(clienteEncontrado.toString() + " seleccionado para modificacion.");

            System.out.println(
                    "Elija qué dato de la cliente desea modificar: \n1: nombre, \n2: DNI, \n3: domicilio, \n4: domicilio alternativo, \n5: fecha nacimiento");
            int selecper = Teclado.nextInt();
            Teclado.nextLine();

            switch (selecper) {
                case 1:
                    System.out.println("Ingrese el nuevo nombre:");
                    clienteEncontrado.setNombre(Teclado.nextLine());

                    break;
                case 2:
                    System.out.println("Ingrese el nuevo DNI:");

                    clienteEncontrado.setDni(Teclado.nextInt());
                    Teclado.nextLine();

                    break;
                case 3:
                    System.out.println("Ingrese la nueva direccion:");
                    clienteEncontrado.setDireccion(Teclado.nextLine());

                    break;
                case 4:
                    System.out.println("Ingrese la nueva direccion alternativa:");
                    clienteEncontrado.setDireccionAlternativa(Teclado.nextLine());

                    break;
                case 5:
                    System.out.println("Ingrese fecha de nacimiento:");
                    Date fecha = null;
                    DateFormat dateformatArgentina = new SimpleDateFormat("dd/MM/yy");

                    fecha = dateformatArgentina.parse(Teclado.nextLine());
                    clienteEncontrado.setFechaNacimiento(fecha);
                    break;
                default:
                    break;
            }

            // Teclado.nextLine();

            ABMCliente.update(clienteEncontrado);

            System.out.println("El registro de " + clienteEncontrado.getNombre() + " ha sido modificado.");

        } else {
            System.out.println("Cliente no encontrado.");
        }

    }

    public void listar() {

        List<Cliente> todos = ABMCliente.buscarTodos();
        for (Cliente c : todos) {
            mostrarCliente(c);
        }
    }

    public void listarPorNombre() {

        System.out.println("Ingrese el nombre:");
        String nombre = Teclado.nextLine();

        List<Cliente> clientes = ABMCliente.buscarPor(nombre);
        for (Cliente cliente : clientes) {
            mostrarCliente(cliente);
        }
    }

    // listarPorNombre
    public void mostrarCliente(Cliente cliente) {

        System.out.print("Id: " + cliente.getClienteId() + " Nombre: " + cliente.getNombre() + " DNI: "
                + cliente.getDni() + " Domicilio: " + cliente.getDireccion());

        if (cliente.getDireccionAlternativa() != null)
            System.out.print(" Alternativo: " + cliente.getDireccionAlternativa());

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String fechaNacimientoStr = formatter.format(cliente.getFechaNacimiento());

        System.out.println(" Fecha Nacimiento: " + fechaNacimientoStr);
    }

    public void asignarPrestamo() {

        System.out.println("Ingrese el ID de la cliente:");
        int id = Teclado.nextInt();
        Teclado.nextLine();
        Cliente clienteEncontrado = ABMCliente.read(id);

        if (clienteEncontrado != null) {
            this.crearYAsignarPrestamo(clienteEncontrado);
            ABMCliente.update(clienteEncontrado);
            System.out.println("Prestamo otorgado con exito");
        } else {
        System.out.println("Cliente no encontrado.");
        }
    }

    // alta + asignarPrestamo
    public void crearYAsignarPrestamo(Cliente cliente) {
        Prestamo prestamo = new Prestamo();

        System.out.println("Ingrese el importe del prestamo solicitado: ");
        prestamo.setImporte(Teclado.nextBigDecimal());
        System.out.println("Ingrese la cantidad de cuotas que desea abonar: ");
        prestamo.setCuotas(Teclado.nextInt());
        Teclado.nextLine();

        System.out.println("Ingrese fecha de otorgamiento del credito(dd/mm/yy): ");
        try {
            Date fecha = null;
        DateFormat dateformatArgentina = new SimpleDateFormat("dd/MM/yy");
        String fechaString = Teclado.nextLine();
        fecha = dateformatArgentina.parse(fechaString);
        prestamo.setFecha(fecha);
        }  catch (Exception e) {
            System.out.println("Error en fecha de prestamo T-T");
        }
        
        prestamo.setFechaAlta(new Date());
        prestamo.setCliente(cliente);
    }
    

    public void mostrarPrestamos() {

        List<Prestamo> todos = ABMPrestamo.mostrarTodos();
        for (Prestamo p : todos) {
            mostrarPrestamo(p);
        }
    }

    //mostrarPrestamos
    public void mostrarPrestamo(Prestamo prestamo) {
        System.out.println("Id: " + prestamo.getPrestamoId() + " Cliente ID: " + prestamo.getCliente().getClienteId() +
        " Importe: " + prestamo.getImporte() + " Cuotas: " + prestamo.getCuotas());

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String fechaStr = formatter.format(prestamo.getFecha());
        String fechaAltaStr = formatter.format(prestamo.getFechaAlta());

        System.out.println("Fecha: " + fechaStr + " Fecha Alta: " + fechaAltaStr);
        System.out.println("Estado: " + prestamo.getEstadoId());
    }

   

    public static void printOpciones() {
        System.out.println("=======================================");
        System.out.println("");
        System.out.println("1. Para agregar un cliente.");
        System.out.println("2. Para eliminar un cliente.");
        System.out.println("3. Para modificar un cliente.");
        System.out.println("4. Para ver el listado.");
        System.out.println("5. Buscar un cliente por nombre especifico(SQL Injection)).");
        System.out.println("6. Asignar prestamo a un cliente existente");
        System.out.println("7. Para ver listado de prestamos.");
        System.out.println("0. Para terminar.");
        System.out.println("");
        System.out.println("=======================================");
    }
}