/**
 * Esta clase representa a un cliente de tipo Oro,
 * Se extiende desde Cliente y especifica los beneficios para este tipo.
 */
public class ClienteOro extends Cliente
{ 

    /**
     * Constructor de la clase ClienteOro.
     * @param dni El DNI del cliente.
     * @param nombre El nombre completo del cliente.
     * @param anioIngreso El año en el que el cliente ingreso al banco.
     */
    public ClienteOro(Integer dni, String nombre, Integer anioIngreso)
    {
        super(dni,nombre, anioIngreso);
    }

    /**
     * Devuelve el limite de credito para un cliente oro.
     * @return El monto limite de credito, equivalente a 250000.0
     */
    public Double getLimiteCredito()
    {
        return 250000.0;
    }

    /**
     * Devuelve el tipo de tarjeta asociada a la cuenta del cliente platino.
     * @return Una cadena de texto con el tipo de tarjeta Credix.
     */
    public String getTipoTarjeta()
    {
        return "Credix";
    }

    /**
     * Devuelve el nombre del tipo de cliente.
     * @return Una cadena detexto con el nombre del tipo de cliente Oro.
     */
    public String getTipoCliente()
    {
        return "Oro";
    }

    
}
