import java.time.LocalDate; // para generar la fecha de la transacción 
import java.util.Random; // para generar un numero random de movimiento

// para generar archivos .txt
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * Esta clase representa una transaccion bancaria, ya sea deposito o extraccion,
 * realizada en una caja de ahorro.
 * @author Burgos Roman, Basualdo Ignacio, Contreras Matias
 * @version 1.0
 */
public class Transaccion
{
    private String fecha; // generado automaticamente
    private Integer nroMovimiento; // generado automaticamente
    private Integer cuentaOrigen; // id de la cuenta de ahorro
    private String tipoTransaccion;
    private String tipoMoneda; // pesos/dolares
    private Double monto;

    /**
     * Constructor de la clase Transaccion.
     * Inicializa los datos de la transaccion y genera automaticamente la fecha actual
     * y el numero de movimiento aleatorio.
     * @param cuentaOrigen El numero de identificacion de la cuenta de origen.
     * @param tipoTransaccion El tipo de operacion realizada, deposito o extraccion.
     * @param tipoMoneda El tipo de moneda utilizada en la transaccion, pesos o dolares.
     * @param monto El monto de dinero en la transaccion.
     */
    public Transaccion(Integer cuentaOrigen, String tipoTransaccion, String tipoMoneda, Double monto)
    {
        if(cuentaOrigen <= 0)
        {
            throw new IllegalArgumentException("El id de la cuenta de origen es invalido");
        }
        if(!tipoTransaccion.trim().equalsIgnoreCase("deposito") && !tipoTransaccion.trim().equalsIgnoreCase("extraccion"))
        {
            throw new IllegalArgumentException("El tipo de transaccion no es valido.");
        }
        if(!tipoMoneda.trim().equalsIgnoreCase("pesos") && !tipoMoneda.trim().equalsIgnoreCase("dolares"))
        {
            throw new IllegalArgumentException("El tipo de moneda no es valido.");
        }
        if(monto <= 0)
        {
            throw new IllegalArgumentException("El monto ingresado no es valido.");
        }
        this.cuentaOrigen = cuentaOrigen;
        this.tipoTransaccion = tipoTransaccion;
        this.tipoMoneda = tipoMoneda;
        this.monto = monto;

        this.fecha = LocalDate.now().toString();

        Random random = new Random();
        this.nroMovimiento = random.nextInt(1000) + 1;

    }
    
    /**
     * Constructor secundario de la clase Transaccion, solo es necesario para el Testing.
     * No genera la fecha actual, para que durante el Testing el usuario introduzca la fecha.
     * Genera automaticamente un numero de movimiento aleatorio.
     * @param cuentaOrigen El numero de identificacion de la cuenta de origen.
     * @param tipoMoneda El tipo de moneda utilizada en la transaccion, pesos o dolares.
     * @param tipoTransaccion El tipo de operacion realizada, deposito o extraccion.
     * @param monto El monto de dinero en la transaccion.
     */
    public Transaccion(Integer cuentaOrigen, String tipoTransaccion, String tipoMoneda, Double monto, String Fecha)
    {
         if(cuentaOrigen <= 0)
        {
            throw new IllegalArgumentException("El id de la cuenta de origen es invalido");
        }
        if(!tipoTransaccion.trim().equalsIgnoreCase("deposito") && !tipoTransaccion.trim().equalsIgnoreCase("extraccion"))
        {
            throw new IllegalArgumentException("El tipo de transaccion no es valido.");
        }
        if(!tipoMoneda.trim().equalsIgnoreCase("pesos") && !tipoMoneda.trim().equalsIgnoreCase("dolares"))
        {
            throw new IllegalArgumentException("El tipo de moneda no es valido.");
        }
        if(monto <= 0)
        {
            throw new IllegalArgumentException("El monto ingresado no es valido.");
        }
        if(Fecha.trim().isEmpty())
        {
            throw new IllegalArgumentException("La fecha ingresada no puede estar vacia");   
        }
        if(Fecha == null)
        {
            throw new NullPointerException("La fecha ingresada no puede estar vacia");   
        }

        this.cuentaOrigen = cuentaOrigen;
        this.tipoTransaccion = tipoTransaccion;
        this.tipoMoneda = tipoMoneda;
        this.monto = monto;
        this.fecha = Fecha;
        Random random = new Random();
        this.nroMovimiento = random.nextInt(1000) + 1;

    }

    /**
     * Devuelve el numero de la cuenta de origen.
     * @return El numero de identificacion de la cuenta de origen.
     */
    public Integer getCuentaOrigen()
    {
        return cuentaOrigen;
    }

    /**
     * Devuelve la fecha en la que se realizo la transaccion.
     * @return Una cadena de texto con la fecha de la transaccion.
     */
    public String getFecha()
    {
        return fecha;
    }

    /**
     * Extrae el año de la fecha de la transacción.
     * @return El año como un objeto Integer.
     */
    public Integer getAnio() {
        // substring(0, 4) toma los caracteres desde la posición 0 hasta la 3 (los primeros 4 dígitos)
        return Integer.valueOf(this.fecha.substring(0, 4));
    }

    /**
     * Extrae el mes de la fecha de la transacción.
     * @return El mes como un objeto Integer.
     */
    public Integer getMes() {
        // substring(5, 7) toma los caracteres en las posiciones 5 y 6 (los dos dígitos del mes)
        return Integer.valueOf(this.fecha.substring(5, 7));
    }



    /**
     * Devuelve el tipo de la transaccion realizada, deposito o extraccion.
     * @return Una cadena de texto con el tipo de transaccion realizada.
     */
    public String getTipoTransaccion()
    {
        return tipoTransaccion;
    }
    
    /**
     * Devuelve el numero de movimiento generado para la transaccion.
     * @return El numero de movimiento de la transaccion.
     */
    public Integer getNroMovimiento()
    {
        return nroMovimiento;
    }

    /**
     * Devuelve el monto de la transaccion.
     * @return El monto de la transaccion.
     */
    public Double getMonto()
    {
        return monto;
    }

    /**
     * Devuelve el tipo de moneda de la transaccion, pesos o dolares.
     * @return Una cadena de texto con el tipo de moneda.
     */
    public String getTipoMoneda()
    {
        return tipoMoneda;
    }


    /**
     * Devuelve una representacion de todos los datos de una transaccion.
     * @return Una cadena de texto con todos los datos de una transaccion.
     */
    public String toString()
    {
        return 
            ("\n=================================================================================================\n" + 
            " | N°Movimiento: " + this.nroMovimiento + 
            " | Fecha: " + this.fecha +
            " | Cuenta Origen: " + this.cuentaOrigen + 
            " | Tipo: " + this.tipoTransaccion + 
            " | Moneda: " + this.tipoMoneda +
            " | Monto: $" + this.monto +
            "\n=================================================================================================");
    }

    /**
     * Genera y adjunta un comprobante de la transaccion en un archivo .txt.
     * El archivo lleva el nombre TRANSACCIONES (DNI).txt y añade los datos
     * sin sobreescribir las transacciones anteriores.
     * Se obtiene el DNI mediante el objeto Cliente.
     * @param cliente El objeto Cliente que pertenece a la transaccion.
     */
    public void generarInformeTransaccionArchivo(Integer dni)
    {   
        FileWriter archivo = null; // los creamos y arrancamos como null
        PrintWriter p = null; // los creamos y arrancamos como null
        
        try
        {
            archivo = new FileWriter ("TRANSACCIONES_" + dni + ".txt", true);
            p = new PrintWriter (archivo);

            p.println("========================================== COMPROBANTE ==========================================");
            p.println("N°Movimiento: " + this.nroMovimiento);
            p.println("Cuenta Origen: " + this.cuentaOrigen);
            p.println("Tipo: " + this.tipoTransaccion);
            p.println("Moneda: " + this.tipoMoneda);
            p.println("Monto: $" + this.monto);
            p.println("Fecha: " +this.fecha);
            p.println("=================================================================================================");
            p.println("");
            p.println("");
        }
        catch (IOException e)
        {
            System.err.println("Error al intentar escribir en el archivo." + e.getMessage());
        }
        finally
        {
            try
            {
                if (archivo != null)
                {
                    archivo.close();
                }
                
            }
            catch (IOException e2)
            {
                System.err.println("Error al cerrar el archivo." + e2.getMessage());
            }
        }
    }
}