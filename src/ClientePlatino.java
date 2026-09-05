/**
 * Esta clase representa a un cliente de tipo Platino en el banco.
 * Se extiende desde Cliente y especifica los beneficios para este tipo.
 */
public class ClientePlatino extends Cliente
{ 
    
    /**
     * Constructor de la clase ClientePlatino.
     * @param dni El DNI del cliente.
     * @param nombre El nombre completo del cliente.
     * @param anioIngreso El año en el que el cliente ingreso al banco.
     */
    public ClientePlatino(Integer dni, String nombre, Integer anioIngreso)
    {
        super(dni,nombre, anioIngreso);  
    }

    /**
     * Devuelve el limite de credito para un cliente platino.
     * @return El monto limite de credito, equivalente a 500000.0
     */
    public Double getLimiteCredito()
    {
        return 500000.0;
    }

    /**
     * Devuelve el tipo de tarjeta asociada a la cuenta del cliente platino.
     * @return Una cadena de texto con el tipo de tarjeta Premium.
     */
    public String getTipoTarjeta()
    {
        return "Premium";
    }

    /**
     * Devuelve el nombre del tipo de cliente.
     * @return Una cadena detexto con el nombre del tipo de cliente Platino.
     */
    public String getTipoCliente()
    {
        return "Platino";
    }
}
