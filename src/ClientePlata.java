/**
 * Esta clase representa a un cliente de tipo Plata,
 * Se extiende desde Cliente y especifica los beneficios para este tipo.
 */
public class ClientePlata extends Cliente
{ 

     /**
     * Constructor de la clase ClienteOro.
     * @param dni El DNI del cliente.
     * @param nombre El nombre completo del cliente.
     * @param anioIngreso El año en el que el cliente ingreso al banco.
     */ 
    public ClientePlata(Integer dni, String nombre, Integer anioIngreso)
    {
        super(dni,nombre, anioIngreso);   
    }

    /**
     * Devuelve el limite de credito para un cliente plata.
     * @return El monto limite de credito, equivalente a 0.0
     */
    public Double getLimiteCredito()
    {
        return 0.0;
    }

    /**
     * Devuelve el tipo de tarjeta asociada a la cuenta del cliente plata.
     * @return Una cadena de texto, indicando que no accede a ninguna tarjeta.
     */
    public String getTipoTarjeta()
    {
        return "No accede.";
    }

    /**
     * Devuelve el nombre del tipo de cliente.
     * @return Una cadena detexto con el nombre del tipo de cliente Plata.
     */
    public String getTipoCliente()
    {
        return "Plata";
    }

    
}
