/**
 * Esta clase representa una caja de ahorro asociada a un cliente.
 * Gestiona el saldo disponible y permite hacer transacciones.
 * @author Burgos Roman, Basualdo Ignacio, Contreras Matias
 * @version 1.0
 */
public class CajaAhorro
{
    private Integer id;
    private String moneda;
    private Double saldo;

    /**
     * Constructor de la clase CajaAhorro.
     * Inicializa una nueva cuenta y valida que la moneda y saldo sean validos.
     * @param id El numero de identificacion de la caja de ahorro.
     * @param moneda El tipo de moneda de la cuenta, pesos o dolares.
     * @param saldo El monto de dinero con el que se abre la caja.
     * @throws NullPointerException Si el tipo de moneda ingresado es nulo.
     * @throws IllegalArgumentException Si el tipo de moneda esta vacio, no es valido, o si 
     * el saldo es negativo.
     */
    public CajaAhorro(Integer id, String moneda, Double saldo)
    {
        if(moneda == null)
        {
            throw new NullPointerException("El tipo de moneda no puede estar vacio.");
        }
        if(moneda.trim().isEmpty())
        {
            throw new IllegalArgumentException("El tipo de moneda no puede estar vacio.");
        }
        if(moneda.trim().toLowerCase().equals("pesos") == false && moneda.trim().toLowerCase().equals("dolares") == false)
        {
            throw new IllegalArgumentException("El tipo de moneda debe ser PESOS o DOLARES.");
        }
        if(saldo < 0)
        {
            throw new IllegalArgumentException("El saldo no puede ser negativo.");
        }
        this.id = id;
        this.moneda = moneda.toUpperCase();
        this.saldo = saldo;
    }

    /**
     * Devuelve el numero de identificacion de la caja de ahorro.
     * @return El ID de la cuenta.
     */
    public Integer getId() 
    { 
        return this.id; 
    }

    /**
     * Devuelve el tipo de moneda de la caja de ahorro.
     * @return Una cadena de texto con el tipo de moneda, pesos o dolares.
     */
    public String getMoneda() 
    { 
        return this.moneda; 
    }
    
    /**
     * Devuelve el saldo actual disponible en la caja de ahorro.
     * @return El total de dinero en la cuenta.
     */
    public Double getSaldo() 
    { 
        return this.saldo; 
    }

    /**
     * Aumenta el saldo de la caja de ahorro sumando el monto.
     * @param monto La cantidad de dinero a depositar en la cuenta.
     * @throws IllegalArgumentException Si el monto a depositar es menor a cero.
     */
    public void depositar(Double monto)
    {
        if (monto <= 0)
        {
            throw new IllegalArgumentException("El monto a depositar no puede ser negativo ni 0.");
        }
        this.saldo += monto;
    }
    
    /**
     * Disminuye el saldo de la caja de ahorro restando el monto, y verifica
     * que este disponible el saldo necesario para la operacion.
     * @param monto La cantidad de dinero a extraer de la cuenta.
     * @throws IllegalArgumentException Si el monto es mayor al saldo disponible,
     * o si el monto a extraer es menor o igual a cero.
     */
    public void extraer(Double monto)
    {
        if (this.saldo < monto)
        {
            throw new IllegalArgumentException("Saldo insuficiente.");
        }
        if(monto <= 0)
        {
            throw new IllegalArgumentException("El monto a extraer no puede ser negativo ni 0.");
        }
        this.saldo -= monto;
    }
}
