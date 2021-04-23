import java.rmi.Naming;

public class ServidormatrizRMI {
    
    public static void main(String[] args) throws Exception{
        // url del puerto por defecto
        String url = "rmi://localhost/matrices";
        // instancia del objeto remoto
        ClasematrizRMI obj = new ClasematrizRMI();
    
        // registra la instancia en el rmiregistry
        Naming.rebind(url,obj);
    }
}
