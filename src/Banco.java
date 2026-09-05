import java.util.ArrayList;

/**
 * Esta clase representa el banco que gestiona el registro y operaciones de los clientes.
 * @author Burgos Roman, Basualdo Ignacio, Contreras Matias
 * @version 1.0
 */

public class Banco 
{
    private String nombre;
    private ArrayList<Cliente> clientes; // lista de clientes
    
    /**
     * Constructor de la clase Banco.
     * @param nombre El nombre del banco.
     * @throws NullPointerException Si el nombre ingresado es nulo.
     * @throws IllegalArgumentException Si el nombre ingresado esta vacio.
     */
    public Banco(String nombre)
    {
         if(nombre == null)
        {
            throw new NullPointerException("El nombre del banco no puede estar vacio.");
        }
        if(nombre.trim().isEmpty())
        {
            throw new IllegalArgumentException("El nombre del banco no puede estar vacio.");
        }
        this.nombre = nombre;
        this.clientes = new ArrayList<Cliente>();
    }

    /**
     * Devuelve la lista de los clientes registrados en el banco.
     * @return Un ArrayList que contiene los objetos Cliente.
     */
    public ArrayList<Cliente> getClientes()
    {
        return this.clientes;
    }

    /**
     * Registra un nuevo cliente en el banco.
     * @param cliente El objeto Cliente a agregar a la lista.
     * @throws IllegalArgumentException Si ya existe un cliente con el mismo DNI.
     */
    public void registrarCliente(Cliente cliente)
    {
        for(Cliente c : this.clientes)
        {
            if(c.getDni().equals(cliente.getDni())) // revisa si ya existe el cliente
            {
                throw new IllegalArgumentException("El cliente ya existe.");
            }
        }

        this.clientes.add(cliente);
    }

    /**
     * Busca un cliente especifico usando su DNI.
     * @param dni El DNI del cliente a buscar.
     * @return El objeto Cliente encontrado.
     * @throws IllegalStateException Si la lista esta vacia o si no se encuentra el cliente.
     */
    public Cliente buscarCliente(Integer dni)
    {
        if(this.clientes.isEmpty())
        {
            throw new IllegalStateException("No hay clientes registrados.");
        }
        for(Cliente c : this.clientes)
        {
            if (c.getDni().equals(dni))
            {
                return c;
            }
        }
        throw new IllegalStateException("No hay ningun cliente registrado con el dni " +dni+ ".");
    }

    /**
     * Imprime por consola todos los datos de un cliente, incluyendo su nombre, 
     * tipo de cliente, año de ingreso, estado, tarjeta y limite de tarjeta.
     * @param dni El DNI del cliente a mostrar.
     * @throws IllegalStateException Si no se encuentra el cliente.
     */
    public void mostrarCliente(Integer dni) throws IllegalStateException
    {
        Cliente cliente = buscarCliente(dni);

        System.out.println("====================================== DATOS DEL CLIENTE " + dni + ": ======================================");
        System.out.println("Nombre: "+ cliente.getNombre());
        System.out.println("Tipo de Cliente: "+ cliente.getTipoCliente());
        System.out.println("Año de Ingreso: "+ cliente.getAnioIngreso());
        
        if (cliente.getEstado() == true)
        {
            System.out.println("Estado: ACTIVO");    
        }
        else
        {
            System.out.println("Estado: INACTIVO");
        }
        
        System.out.println("Tarjeta: "+ cliente.getTipoTarjeta());
        System.out.println("Limite de Tarjeta: "+ cliente.getLimiteCredito());
        System.out.println("(================================= CAJAS DE AHORRO =================================");
        // cajas de ahorro
        for (CajaAhorro caja : cliente.getCajaAhorro())     
        {
            System.out.println("Caja en " + caja.getMoneda() + ": " + caja.getSaldo()+ " | ID: " +caja.getId());
        }
        System.out.println("===================================================================================");

    }

    /**
     * Imprime por consola un listado de todos los clientes, indicando su DNI, nombre y estado.
     * @throws IllegalStateException Si no hay clientes registrados.
     */
    public void mostrarClientes()
    {
        if(this.clientes.isEmpty())
        {
            throw new IllegalStateException("No hay clientes registrados.");
        }

        System.out.println("====================================== LISTADO DE CLIENTES ======================================");
        for (Cliente cliente : this.clientes)
        {
            System.out.println("DNI: " + cliente.getDni() + " | Nombre: " + cliente.getNombre() + " | Estado: " + cliente.getEstado());
        }
    }

    /**
     * Imprime por consola el historial de transacciones de un cliente.
     * @param dni El DNI del cliente del cual se imprime el historial.
     * @throws IllegalStateException Si el cliente no existe o si no hay transacciones.
     */
    public void mostrarTransaccionesCliente(Integer dni) throws IllegalStateException
    {
        Cliente aux = buscarCliente(dni);

        ArrayList<Transaccion> transacciones = aux.getTransacciones();
        if(transacciones.isEmpty())
        {
            throw new IllegalStateException("El cliente no cuenta con transacciones hechas.");
        }
        System.out.println("====================================== TRANSACCIONES DEL CLIENTE " + dni + " ======================================");
        for(Transaccion t : transacciones)
        {
           System.out.println(t);
        }
    }
    /**
     * Inicia una nueva transaccion.
     * Valida el formato del documento y busca al cliente dentro del banco.
     * @param dni El DNI del cliente  realizar la transaccion.
     * @param caja El ID de la caa de ahorro afectada.
     * @param tipo El tipo de operacion, deposito o extraccion.
     * @param monto La cantidad de dinero involucrada en la transaccion.
     * @throws NullPointerException Si el DNI es nulo.
     * @throws IllegalArgumentException Si el dni esta fuera de rango.
     * @throws IllegalStateException Si no encuentra un cliente con el DNI proporcionado.
     */
    public void GenerarTransaccion(Integer dni, Integer caja, String tipo, Double monto) throws IllegalStateException
    {
        if(dni == null)
        {
            throw new NullPointerException("El dni no puede estar vacio");
        }
        if(dni<= 0 || dni>= 100000000)
        {
            throw new IllegalArgumentException("El dni no es valido");
        }

        Cliente aux = buscarCliente(dni);
        aux.realizarTransaccion(caja, tipo, monto);
    }
    
}