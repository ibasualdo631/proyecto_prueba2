import java.util.ArrayList;

public class Test
{
    public static void main(String[] args)
    {
        Banco banco = new Banco("Banco IUA");

        Cliente c1 = new ClienteOro(47212750, "Matias", 2020);  
        Cliente c2 = new ClientePlata(47624150, "Roman", 2021); 
        Cliente c3 = new ClientePlatino(47174631, "Ignacio", 2022);

        try 
        { 
            //deberia fallar por los parametros enviados al cliente c4
            Cliente c4 = new ClientePlata(null, "", -1);
            banco.registrarCliente(c4);
        }
        catch (IllegalArgumentException | NullPointerException e)
        {
            System.out.println("El sistema reaccionó bien. Motivo: " + e.getMessage());
        }
 
        //registramos los clientes creados dentro del banco
        banco.registrarCliente(c1);
        banco.registrarCliente(c2);
        banco.registrarCliente(c3);
        banco.mostrarClientes(); //muestra los clientes del banco, deberia mostrar los datos de c1,c2,c3 

        int i = 0;
        ArrayList<CajaAhorro> cajas_ahorrosc2 = c2.getCajaAhorro();
        Integer[] ids_cajasc2 = new Integer[cajas_ahorrosc2.size()];

        for(CajaAhorro ch2 : cajas_ahorrosc2)
        {
            ids_cajasc2[i] = ch2.getId();
            i++;
        }
        banco.GenerarTransaccion(47624150, ids_cajasc2[0], "Deposito", 120.0); //realizamos un deposito de 120.0 en la caja num_1 para el cliente con el dni 47624150
        try 
        {
            banco.GenerarTransaccion(47624150, ids_cajasc2[0], "Extraccion", 200.0);
        }
        catch (IllegalArgumentException e1)
        {
            System.out.println("Falló la extracción por el siguiente motivo: " + e1.getMessage());
        }

        i = 0;
        ArrayList<CajaAhorro> cajas_ahorrosc1 = c1.getCajaAhorro();
        Integer[] ids_cajasc1 = new Integer[cajas_ahorrosc1.size()];

        for(CajaAhorro ch1 : cajas_ahorrosc1)
        {
            ids_cajasc1[i] = ch1.getId();
            i++;
        }
        
        banco.GenerarTransaccion(47212750, ids_cajasc1[1], "Deposito", 1000.0);
        banco.GenerarTransaccion(47212750, ids_cajasc1[1], "Deposito", 1200.0);
        banco.GenerarTransaccion(47212750, ids_cajasc1[1], "Extraccion", 2000.0);
        banco.mostrarTransaccionesCliente(47212750); // deberia aparecer en pantalla los comprobantes de las transacciones realizadas
    }
}
