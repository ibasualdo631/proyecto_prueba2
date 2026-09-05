import java.util.ArrayList;
import java.util.Random;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * Esta clase representa un cliente del banco.
 * Incluye los datos personales del cliente, estado, cajas de ahorro e historial.
 * @author Burgos Roman, Basualdo Ignacio, Contreras Matias
 * @version 1.0
 */
public abstract class Cliente
{
    private Integer dni; // ID
    private String nombre;
    private Integer AnioIngreso;
    private Boolean estado;
    private ArrayList<CajaAhorro> cajasAhorro;
    private ArrayList<Transaccion> transacciones;

    /**
     * Constructor de la clase Cliente
     * @param dni El DNI del cliente.
     * @param nombre El nombre completo del cliente.
     * @param AnioIngreso El año en el que el cliente se registro.
     * @throws ClienteNoValidoException Si el DNI esta fuera de rango,
     * el nombre esta vacio o nulo, o si el año de ingreso es invalido.
     */
    public Cliente(Integer dni, String nombre, Integer AnioIngreso)
    {
        if(dni<= 0 || dni>= 100000000)
        {
            throw new IllegalArgumentException("El dni no es valido");
        }
        if(nombre == null)
        {
            throw new NullPointerException("El nombre no pued estar vacio");
        }
        if(nombre.trim().isEmpty())
        {
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }
        if(AnioIngreso <= 0)
        {
            throw new  IllegalArgumentException("El anio de ingreso es invalido");
        }
        this.dni = dni;
        this.nombre = nombre.toUpperCase();
        this.AnioIngreso = AnioIngreso;
        this.estado = true;
        this.cajasAhorro = new ArrayList<CajaAhorro>();
        this.transacciones = new ArrayList<Transaccion>();
        crearCajasAhorro();
    }

    /**
     * Devuelve el DNI del cliente.
     * @return El DNI del cliente.
     */    
    public Integer getDni()
    {
        return this.dni;
    }

    /**
     * Devuelve el nombre completo del cliente.
     * @return Una cadena de texto con el nombre completo del cliente.
     */
    public String getNombre()
    {
        return this.nombre;
    }
    
    /**
     * Devuelve el estado del cliente, false si encuentra dado de baja,
     * y true si se encuentra en el sistema.
     * @return El estado del cliente. 
     */
    public Boolean getEstado()
    {
        return this.estado;
    }

    /**
     * Devuelve el año en el que el cliente ingreso al sistema.
     * @return El año en el que el cliente ingreso al sistema.
     */
    public Integer getAnioIngreso()
    {
        return this.AnioIngreso;
    }

    /**
     * Devuelve el ArrayList con las cajas de ahorro.
     * @return El ArrayList con las cajas de ahorro.
     */
    public ArrayList<CajaAhorro> getCajaAhorro()
    {
        return this.cajasAhorro;
    }

    /**
     * Devuelve el ArrayList con transacciones.
     * @return El ArrayList con las transacciones
     */
    public ArrayList<Transaccion> getTransacciones()
    {
        return transacciones;
    }

    /**
     * Devuelve el limite de de credito disponible segun el tipo de cliente.
     * @return El monto maximo de credito.
     */
    public abstract Double getLimiteCredito();

    /**
     * Devuelve el tipo de tarjeta segun el tipo de cliente.
     * @return Una cadena de texto con el tipo de tarjeta.
     */
    public abstract String getTipoTarjeta();

    /**
     * Devuelve el tipo de cliente, como platino, plata.
     * @return El tipo de cliente.
     */
    public abstract String getTipoCliente();

    /**
     * Actualiza el nombre del cliente.
     * @param nombre El nuevo nombre a establecer.
     * @throws IllegalArgumentException Si el nombre ingresado esta vacio o nulo.
     */
    public void setNombre(String nombre)
    {
        if(nombre == null || nombre.trim().isEmpty())
        {
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }
        this.nombre = nombre;
    }

    /**
     * Da de baja al cliente cambiado su estado a false.
     * @throws IllegalStateException Si el cliente ya esta dado de baja.
     */
    public void darDeBaja ()
    {
        // siendo true activo y false inactivo:
        if(this.estado.equals(false))
        {
            throw new IllegalStateException("El cliente ya se encuentra dado de baja");
        }
        this.estado = false;
    }

    /**
     * Inicializa automaticamente una caja de ahorro en pesos y otra en dolares,
     * y se les asigna un ID aleatorio.
     */
    private void crearCajasAhorro()
    {
        Random random = new Random();
        Integer idCajaPesos = random.nextInt(1000000) + 100000;
        Integer idCajaDolares = random.nextInt(1000000) + 100000; // falta validación ??

        cajasAhorro.add(new CajaAhorro(idCajaPesos, "Pesos", 0.0));
        cajasAhorro.add(new CajaAhorro(idCajaDolares, "Dolares", 0.0));
    }

    
    /**
     * Gestiona una transaccion, deposito o extraccion, en una de las cajas de ahorro.
     * Genera automaticamente el registro de la transaccion.
     * @param idCaja El ID de la caja de ahorro sobre la cual se realizara la operacion.
     * @param tipo El tipo de operacion.
     * @param monto La cantidad de dinero de la operacion.
     * @throws IllegalArgumentException Si el monto es invalido, no existe el tipo de operacion,
     * o si la caja de ahorro no existe o no pertenece al cliente.
     */
    public void realizarTransaccion(Integer idCaja, String tipo, Double monto)
    {
        
        if (monto == null || monto <= 0)
        {
            throw new IllegalArgumentException("El monto de la transacción debe ser válido.");
        }

        for(CajaAhorro caja : this.cajasAhorro)
        {
            if (caja.getId().equals(idCaja)) // si se encuentra la coincidencia 
            {
                // hay 2 opciones: depósito y extracción
                if (tipo.trim().equalsIgnoreCase("deposito"))
                {
                    caja.depositar(monto);

                    // ahora creamos el objeto transaccion

                    Transaccion transaccion = new Transaccion(idCaja, "DEPOSITO", caja.getMoneda(), monto);
                    this.transacciones.add(transaccion);
                    transaccion.generarInformeTransaccionArchivo(getDni());
                    return;
                }
                else if(tipo.trim().equalsIgnoreCase("extraccion"))
                {
                    caja.extraer(monto);

                    // ahora creamos el objeto transaccion

                    Transaccion transaccion = new Transaccion(idCaja, "EXTRACCION", caja.getMoneda(), monto);
                    this.transacciones.add(transaccion);
                    transaccion.generarInformeTransaccionArchivo(getDni());
                    return;
                }
                else
                {
                    throw new IllegalArgumentException("Tipo de transacción NO valido.");
                }

            }

        }
        throw new IllegalArgumentException("La cuenta buscada no existe / No pertenece al cliente.");
    }
    
    public void realizarTransaccionConFecha(Integer idCaja, String tipo, Double monto, String Fecha)
    {
        
        if (monto == null || monto <= 0)
        {
            throw new IllegalArgumentException("El monto de la transacción debe ser válido.");
        }

        for(CajaAhorro caja : this.cajasAhorro)
        {
            if (caja.getId().equals(idCaja)) // si se encuentra la coincidencia 
            {
                // hay 2 opciones: depósito y extracción
                if (tipo.trim().equalsIgnoreCase("deposito"))
                {
                    caja.depositar(monto);

                    // ahora creamos el objeto transaccion

                    Transaccion transaccion = new Transaccion(idCaja, "DEPOSITO", caja.getMoneda(), monto, Fecha);
                    this.transacciones.add(transaccion);
                    transaccion.generarInformeTransaccionArchivo(getDni());
                    return;
                }
                else if(tipo.trim().equalsIgnoreCase("extraccion"))
                {
                    caja.extraer(monto);

                    // ahora creamos el objeto transaccion

                    Transaccion transaccion = new Transaccion(idCaja, "EXTRACCION", caja.getMoneda(), monto, Fecha);
                    this.transacciones.add(transaccion);
                    transaccion.generarInformeTransaccionArchivo(getDni());
                    return;
                }
                else
                {
                    throw new IllegalArgumentException("Tipo de transacción NO valido.");
                }

            }

        }
        throw new IllegalArgumentException("La cuenta buscada no existe / No pertenece al cliente.");
    }
    
    /**
     * Genera y actualiza archivos .txt con el historial del banco y del cliente.
     * @param transaccion El objeto Transaccion que persiste en los archivos.
     */
    public void historialTransaccionesCliente(Transaccion transaccion)
    {
        FileWriter archivoCliente = null;
        PrintWriter pcliente = null;

        try
        {
            archivoCliente = new FileWriter("historial_transacciones_cliente"+this.dni+".txt", true);
            pcliente = new PrintWriter(archivoCliente);
            for(Transaccion t : transacciones)
            {
            pcliente.println("========================================== COMPROBANTE ==========================================");
            pcliente.println("N°Movimiento: " + t.getNroMovimiento());
            pcliente.println("Cuenta Origen: " + t.getCuentaOrigen());
            pcliente.println("Fecha: " + t.getFecha());
            pcliente.println("Tipo: " + t.getTipoTransaccion());
            pcliente.println("Moneda: " + t.getTipoMoneda());
            pcliente.println("Monto: $" + t.getMonto());
            pcliente.println("=================================================================================================");
            pcliente.println("");
            pcliente.println("");
            }
        }
        catch (IOException e)
        {
            System.err.println("Error al tratar de escribir alguno  de los archivos." + e.getMessage());
        }
        finally
        {
            try
            {
                if (archivoCliente != null)
                {
                    archivoCliente.close();
                }
                
            }
            catch (IOException e2)
            {
                System.err.println("Error al tratar de cerrar alguno de los archivos." + e2.getMessage());
            }
        }
       
    }
    
    /**
     * Exporta un archivo .txt con todos los depositos realizados por el cliente.
     */
    public void historialDEPOSITOS()
    {
        FileWriter archivoClienteDEPOSITOS = null;
        PrintWriter pclienteDEPOSITOS = null;

        try
        {
            archivoClienteDEPOSITOS = new FileWriter("historial_DEPOSITOS_Cliente_N_"+this.dni+".txt");
            pclienteDEPOSITOS = new PrintWriter(archivoClienteDEPOSITOS);
            
            pclienteDEPOSITOS.println("===========================================================================================================");
            pclienteDEPOSITOS.println("INFORME HISTORICO DE DEPOSITOS DEL CLIENTE N°"+this.dni+":");
            pclienteDEPOSITOS.println("===========================================================================================================");
            pclienteDEPOSITOS.println("Nro Movimiento ; Cuenta Origen ; Fecha ; Tipo de Moneda ; Monto");
            pclienteDEPOSITOS.println("-------------------------------------------------------------------------------------------------");

            // recorremos buscando unicamente depositos

            for (Transaccion transaccion : this.transacciones)
            {
                if (transaccion.getTipoTransaccion().trim().equalsIgnoreCase("DEPOSITO"))
                {           
                    pclienteDEPOSITOS.println(
                        transaccion.getNroMovimiento() + " ; " + 
                        transaccion.getCuentaOrigen() + " ; " + 
                        transaccion.getFecha() + "; " +
                        transaccion.getTipoMoneda() + " ; $" + 
                        transaccion.getMonto()); 
                        
                }
            }
            System.out.println("Informe del historial de DEPOSITOS del Cliente N°"+ this.dni + " generado exitosamente.");
        }
        
        catch (IOException e)
        {
            System.out.println("Se pudo GENERAR/EXPORTAR correctamente el archivo de informe de DEPOSITOS.");
        }
        finally
        {
            try
            {
                if (archivoClienteDEPOSITOS != null)
                {
                    archivoClienteDEPOSITOS.close();
                    System.out.println("Se pudo CERRAR correctamente el archivo de informe de DEPOSITOS.");
                }
            }
            catch(IOException e2)
            {
                System.err.println("ERROR al CERRAR el informe de DEPOSITOS: " + e2.getMessage());


            }
        }
    }
    
    /**
     * Exporta un archivo .txt con el historial de todas las extracciones 
     * realizadas por un cliente.
     */
    public void historialEXTRACCIONES()
    {
        FileWriter archivoClienteEXTRACCIONES = null;
        PrintWriter pclienteEXTRACCIONES = null;
        
        try
        {  
            archivoClienteEXTRACCIONES = new FileWriter("historial_EXTRACCIONES_Cliente_N_"+this.dni+".txt");
            pclienteEXTRACCIONES = new PrintWriter(archivoClienteEXTRACCIONES); 
            pclienteEXTRACCIONES.println("===========================================================================================================");
            pclienteEXTRACCIONES.println("INFORME HISTORICO DE EXTRACCIONES DEL CLIENTE N°"+this.dni+":");
            pclienteEXTRACCIONES.println("===========================================================================================================");
            pclienteEXTRACCIONES.println("Nro Movimiento ; Cuenta Origen ; Fecha ; Tipo de Moneda ; Monto");
            pclienteEXTRACCIONES.println("-------------------------------------------------------------------------------------------------");

            // recorremos buscando unicamente EXTRACCIONES

            for (Transaccion transaccion : this.transacciones)
            {
                if (transaccion.getTipoTransaccion().trim().equalsIgnoreCase("EXTRACCION"))
                {
                    pclienteEXTRACCIONES.println(
                        transaccion.getNroMovimiento() + " ; " + 
                        transaccion.getCuentaOrigen() + " ; " + 
                        transaccion.getFecha() + "; " +
                        transaccion.getTipoMoneda() + " ; $" + 
                        transaccion.getMonto()); 
                }
            }
            System.out.println("Informe del historial de EXTRACCIONES del Cliente N°"+ this.dni + " generado exitosamente.");
        }
        
        catch (IOException e)
        {
            System.out.println("Se pudo GENERAR/EXPORTAR correctamente el archivo de informe de EXTRACCIONES.");
        }
        finally
        {
            try
            {
                if (archivoClienteEXTRACCIONES != null)
                {
                    archivoClienteEXTRACCIONES.close();
                    System.out.println("Se pudo CERRAR correctamente el archivo de informe de EXTRACCIONES.");
                }
            }
            catch(IOException e2)
            {
                System.err.println("ERROR al CERRAR el informe de EXTRACCIONES: " + e2.getMessage());
            }
        }
    }
    
    /**
     * Exporta un archivo .txt con el historial de todos los depositos
     * realizados por un cliente en un mes especifico.
     * @param mes El mes en el cual se realizaron los depositos.
     */
    public void historialDEPOSITOSxMES(Integer mes)
    {
        if(mes == null)
        {
            throw new NullPointerException("El mes no puede estar vacio.");
        }

        if(mes <= 0 || mes > 12)
        {
            throw new IllegalArgumentException("El mes no es valido.");
        }
        FileWriter archivoClienteDEPOSITOSmes = null;
        PrintWriter pclienteDEPOSITOSmes = null;

        try
        {   
            archivoClienteDEPOSITOSmes = new FileWriter("historial_DEPOSITOS_Cliente_N_"+this.dni+"_Mes_"+mes+".txt");
            pclienteDEPOSITOSmes = new PrintWriter(archivoClienteDEPOSITOSmes);     
            
            pclienteDEPOSITOSmes.println("===========================================================================================================");
            pclienteDEPOSITOSmes.println("INFORME HISTORICO DE  DEPOSITOS DEL CLIENTE N°"+this.dni+" en el Mes "+ mes +":");
            pclienteDEPOSITOSmes.println("===========================================================================================================");
            pclienteDEPOSITOSmes.println("Nro Movimiento ; Cuenta Origen ; Fecha ; Tipo de Moneda ; Monto");
            pclienteDEPOSITOSmes.println("-------------------------------------------------------------------------------------------------");

            // recorremos buscando unicamente depositos

            for (Transaccion transaccion : this.transacciones)
            {
                if (transaccion.getTipoTransaccion().trim().equalsIgnoreCase("DEPOSITO"))
                {
                    if (transaccion.getMes().equals(mes))
                    {
                        pclienteDEPOSITOSmes.println(
                        transaccion.getNroMovimiento() + " ; " + 
                        transaccion.getCuentaOrigen() + " ; " + 
                        transaccion.getFecha() + "; " +
                        transaccion.getTipoMoneda() + " ; $" + 
                        transaccion.getMonto()); 
                    }           
        
                }
            }
            System.out.println("Informe del historial de DEPOSITOS del Cliente N°"+ this.dni + " en el mes de " + mes + " fue generado exitosamente.");
        }
        
        catch (IOException e)
        {
            System.out.println("NO se pudo GENERAR/EXPORTAR correctamente el archivo de informe de DEPOSITOS del mes de " + mes + ": " + e.getMessage());
        }
        finally
        {
            try
            {
                if (archivoClienteDEPOSITOSmes != null)
                {
                    archivoClienteDEPOSITOSmes.close();
                    System.out.println("Se pudo cerrar correctamente el archivo de informe de DEPOSITOS del mes de " + mes + ".");
                }
            }
            catch(IOException e2)
            {
                System.err.println("ERROR al CERRAR el informe de DEPOSITOS del mes de " + mes + ": " + e2.getMessage());


            }
        }
    }

    /**
     * Exporta un archivo .txt con el historial de todos los depositos
     * realizados por un cliente en un año especifico.
     * @param anio El año en el cual se realizaron los depositos.
     * 
     */
    public void historialDEPOSITOSxANIO(Integer anio)
    {
        if(anio == null)
        {
            throw new NullPointerException("El anio no puede estar vacio.");
        }

        if(anio <= 0 || anio > 2026)
        {
            throw new IllegalArgumentException("El anio no es valido.");
        }
        FileWriter archivoClienteDEPOSITOSanio = null;
        PrintWriter pclienteDEPOSITOSanio = null;

        try
        {        
            archivoClienteDEPOSITOSanio = new FileWriter("historial_DEPOSITOS_Cliente_N_"+this.dni+"_Anio_"+anio+".txt");
            pclienteDEPOSITOSanio = new PrintWriter(archivoClienteDEPOSITOSanio);

            pclienteDEPOSITOSanio.println("===========================================================================================================");
            pclienteDEPOSITOSanio.println("INFORME HISTORICO DE  DEPOSITOS DEL CLIENTE N°"+this.dni+" en "+anio+":");
            pclienteDEPOSITOSanio.println("===========================================================================================================");
            pclienteDEPOSITOSanio.println("Nro Movimiento ; Cuenta Origen ; Fecha ; Tipo de Moneda ; Monto");
            pclienteDEPOSITOSanio.println("-------------------------------------------------------------------------------------------------");

            // recorremos buscando unicamente depositos

            for (Transaccion transaccion : this.transacciones)
            {
                if (transaccion.getTipoTransaccion().trim().equalsIgnoreCase("DEPOSITO"))
                {
                    if (transaccion.getAnio().equals(anio))
                    {
                        pclienteDEPOSITOSanio.println(
                        transaccion.getNroMovimiento() + " ; " + 
                        transaccion.getCuentaOrigen() + " ; " + 
                        transaccion.getFecha() + "; " +
                        transaccion.getTipoMoneda() + " ; $" + 
                        transaccion.getMonto()); 
                    }           
        
                }
            }
            System.out.println("Informe del historial de DEPOSITOS del Cliente N°"+ this.dni + " en el anio " + anio + " fue generado exitosamente.");
        }
        
        catch (IOException e)
        {
            System.out.println("NO se pudo GENERAR/EXPORTAR correctamente el archivo de informe de DEPOSITOS del año " + anio + ": " + e.getMessage());
        }
        finally
        {
            try
            {
                if (archivoClienteDEPOSITOSanio != null)
                {
                    archivoClienteDEPOSITOSanio.close();
                    System.out.println("Se pudo cerrar correctamente el archivo de informe de DEPOSITOS del anio " + anio + ".");
                }
            }
            catch(IOException e2)
            {
                System.err.println("ERROR al CERRAR el informe de DEPOSITOS del anio " + anio + ": " + e2.getMessage());


            }
        }
    }

    /**
     * Exporta un archivo .txt con el historial de todos las EXTRACCIONES
     * realizados por un cliente en un mes especifico.
     * @param mes El mes en el cual se realizaron las EXTRACCIONES.
     * 
     */
    public void historialEXTRACCIONESxMES(Integer mes)
    {
        if(mes == null)
        {
            throw new NullPointerException("El mes no puede estar vacio.");
        }

        if(mes <= 0 || mes > 12)
        {
            throw new IllegalArgumentException("El mes no es valido.");
        }

        FileWriter archivoClienteEXTRACCIONESmes = null;
        PrintWriter pclienteEXTRACCIONESmes = null;

        try
        {   
            archivoClienteEXTRACCIONESmes = new FileWriter("historial_EXTRACCIONES_Cliente_N_"+this.dni+"_Mes_"+mes+".txt");
            pclienteEXTRACCIONESmes = new PrintWriter(archivoClienteEXTRACCIONESmes);

            pclienteEXTRACCIONESmes.println("===========================================================================================================");
            pclienteEXTRACCIONESmes.println("INFORME HISTORICO DE  EXTRACCIONES DEL CLIENTE N°"+this.dni+" en el Mes "+ mes +":");
            pclienteEXTRACCIONESmes.println("===========================================================================================================");
            pclienteEXTRACCIONESmes.println("Nro Movimiento ; Cuenta Origen ; Fecha ; Tipo de Moneda ; Monto");
            pclienteEXTRACCIONESmes.println("-------------------------------------------------------------------------------------------------");

            // recorremos buscando unicamente EXTRACCIONES

            for (Transaccion transaccion : this.transacciones)
            {
                if (transaccion.getTipoTransaccion().trim().equalsIgnoreCase("EXTRACCION"))
                {
                    if (transaccion.getMes().equals(mes))
                    {
                        pclienteEXTRACCIONESmes.println(
                        transaccion.getNroMovimiento() + " ; " + 
                        transaccion.getCuentaOrigen() + " ; " + 
                        transaccion.getFecha() + "; " +
                        transaccion.getTipoMoneda() + " ; $" + 
                        transaccion.getMonto()); 
                    }           
        
                }
            }
            System.out.println("Informe del historial de EXTRACCIONES del Cliente N°"+ this.dni + " en el mes de " + mes + " fue generado exitosamente.");
        }
        
        catch (IOException e)
        {
            System.out.println("NO se pudo GENERAR/EXPORTAR correctamente el archivo de informe de EXTRACCIONES del mes de " + mes + ": " + e.getMessage());
        }
        finally
        {
            try
            {
                if (archivoClienteEXTRACCIONESmes != null)
                {
                    archivoClienteEXTRACCIONESmes.close();
                    System.out.println("Se pudo cerrar correctamente el archivo de informe de EXTRACCIONES del mes de " + mes + ".");
                }
            }
            catch(IOException e2)
            {
                System.err.println("ERROR al CERRAR el informe de EXTRACCIONES del mes de " + mes + ": " + e2.getMessage());


            }
        }
    }

    /**
     * Exporta un archivo .txt con el historial de todos las EXTRACCIONES
     * realizados por un cliente en un año especifico.
     * @param anio El año en el cual se realizaron las EXTRACCIONES.
     * 
     */
    public void historialEXTRACCIONESxANIO(Integer anio)
    {
        if(anio == null)
        {
            throw new NullPointerException("El anio no puede estar vacio.");
        }

        if(anio <= 0 || anio > 2026)
        {
            throw new IllegalArgumentException("El anio no es valido.");
        }

        FileWriter archivoClienteEXTRACCIONESanio = null;
        PrintWriter pclienteEXTRACCIONESanio = null;

        try
        {    
            archivoClienteEXTRACCIONESanio = new FileWriter("historial_EXTRACCIONES_Cliente_N_"+this.dni+"_Anio_"+anio+".txt");
            pclienteEXTRACCIONESanio = new PrintWriter(archivoClienteEXTRACCIONESanio); 

            pclienteEXTRACCIONESanio.println("===========================================================================================================");
            pclienteEXTRACCIONESanio.println("INFORME HISTORICO DE  EXTRACCIONES DEL CLIENTE N°"+this.dni+" en "+anio+":");
            pclienteEXTRACCIONESanio.println("===========================================================================================================");
            pclienteEXTRACCIONESanio.println("Nro Movimiento ; Cuenta Origen ; Fecha ; Tipo de Moneda ; Monto");
            pclienteEXTRACCIONESanio.println("-------------------------------------------------------------------------------------------------");

            // recorremos buscando unicamente EXTRACCIONES

            for (Transaccion transaccion : this.transacciones)
            {
                if (transaccion.getTipoTransaccion().trim().equalsIgnoreCase("EXTRACCION"))
                {
                    if (transaccion.getAnio().equals(anio))
                    {
                        pclienteEXTRACCIONESanio.println(
                        transaccion.getNroMovimiento() + " ; " + 
                        transaccion.getCuentaOrigen() + " ; " + 
                        transaccion.getFecha() + "; " +
                        transaccion.getTipoMoneda() + " ; $" + 
                        transaccion.getMonto()); 
                    }           
        
                }
            }
            System.out.println("Informe del historial de EXTRACCIONES del Cliente N°"+ this.dni + " en el anio " + anio + " fue generado exitosamente.");
        }
        
        catch (IOException e)
        {
            System.out.println("NO se pudo GENERAR/EXPORTAR correctamente el archivo de informe de EXTRACCIONES del año " + anio + ": " + e.getMessage());
        }
        finally
        {
            try
            {
                if (archivoClienteEXTRACCIONESanio != null)
                {
                    archivoClienteEXTRACCIONESanio.close();
                    System.out.println("Se pudo cerrar correctamente el archivo de informe de EXTRACCIONES del anio " + anio + ".");
                }
            }
            catch(IOException e2)
            {
                System.err.println("ERROR al CERRAR el informe de EXTRACCIONES del anio " + anio + ": " + e2.getMessage());


            }
        }
    }
    /**
     * Exporta en un archivo .txt el historial de transacciones 
     * de un cliente en un mes especifico.
     * @param mes El numero de mes a filtrar
     * @throws NullPointerException si el mes es nulo.
     * @throws IllegalArgumentException si el mes esta fuera de rango.
     */
    public void filtrarXMES(Integer mes)
    {
        if(mes == null)
        {
            throw new NullPointerException("El mes no puede estar vacio.");
        }

        if(mes <= 0 || mes > 12)
        {
            throw new IllegalArgumentException("El mes no es valido.");
        }
   
        
        FileWriter archivo = null;
        PrintWriter p = null;

        try
        {
            archivo = new FileWriter("Transacciones_mes_" + mes + "_" +getDni()+ ".txt");
            p = new PrintWriter(archivo);
            
            p.println("===========================================================================================================");
            p.println("INFORME HISTORICO DE TRANSACCIONES DEL CLIENTE N°"+this.dni+" en el mes "+ mes +":");
            p.println("==========================================================================================================="); 
        
            for(Transaccion t : transacciones)
            {
                if(mes.equals(t.getMes()))
                {
                    p.println("========================================== COMPROBANTE ==========================================");
                    p.println("N°Movimiento: " + t.getNroMovimiento());
                    p.println("Cuenta Origen: " + t.getCuentaOrigen());
                    p.println("Tipo: " + t.getTipoTransaccion());
                    p.println("Moneda: " + t.getTipoMoneda());
                    p.println("Monto: $" + t.getMonto());
                    p.println("=================================================================================================");
                    p.println("");
                    p.println("");
                }
            }
        }
        catch(IOException e)
        {
           System.out.println("Error al escribir el archivo: " + e.getMessage()); 
        }
        finally
        {
            if(p!= null)
            {
                p.close();
            }
        }
    }
    
    /**
     * Exporta en un archivo .txt el historial de transacciones 
     * de un cliente en un año especifico
     * @param anio El año a filtrar
     * @throws NullPointerException si el año es nulo.
     * @throws IllegalArgumentException si el año es menor o igual a cero.
     */
    public void filtrarXANIO(Integer anio)
    {
           if(anio == null)
        {
            throw new NullPointerException("El anio no puede estar vacio.");
        }

        if(anio <= 0)
        {
            throw new IllegalArgumentException("El anio no es valido.");
        }   
        
        FileWriter archivo = null;
        PrintWriter p = null;

        try
        {
            archivo = new FileWriter("Transacciones_anio_" +anio+ "_" +getDni()+".txt");
            p = new PrintWriter(archivo);
            // :p
            
            p.println("===========================================================================================================");
            p.println("INFORME HISTORICO DE TRANSACCIONES DEL CLIENTE N°"+ this.dni +" en "+ anio +":");
            p.println("===========================================================================================================");
            
            for(Transaccion t : transacciones)
            {
                if(anio.equals(t.getAnio()))
                {
                    p.println("========================================== COMPROBANTE ==========================================");
                    p.println("N°Movimiento: " + t.getNroMovimiento());
                    p.println("Cuenta Origen: " + t.getCuentaOrigen());
                    p.println("Tipo: " + t.getTipoTransaccion());
                    p.println("Moneda: " + t.getTipoMoneda());
                    p.println("Monto: $" + t.getMonto());
                    p.println("=================================================================================================");
                    p.println("");
                    p.println("");
                }
            }
        }
        catch(IOException e)
        {
           System.out.println("Error al escribir el archivo: " + e.getMessage()); 
        }
        finally
        {
            if(p!= null)
            {
                p.close();
            }
        }
    }
}

  


