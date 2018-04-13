/*
    Clase que establece Conexión de Arduino con Computadora mediante comunicación de puerto
    Nota: El nombre o especificación cambia según la computadora que se use y según el Sistema Operativo
    con el que se trabaja. (para este ejemplo se trabajará ccon Linux, es por ello que el nombre del Puerto 
    cambia con relación a otros Sistemas Operativos como Windows).
 */
package Connection;

import View.*;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEventListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;

/**
 *
 * @author Gabriel
 */
public class ConexionArduino {
    //Variables para establecimiento de conexión
    private SerialPort serialPort;
    private final String PUERTO = "/dev/ttyACM0";
    private static final int TIMEOUT = 2000; // 2 segundos.
    private static final int DATA_RATE = 9600; // Baudios. 
    private OutputStream output = null;
    private BufferedReader input = null;
    
    /*Método para inicio de conexión*/
    public void inicializarConexion(){
        CommPortIdentifier puertoID = null;
        Enumeration puertoEnum = CommPortIdentifier.getPortIdentifiers();
        //Ciclo para detección de puertos
        while (puertoEnum.hasMoreElements()){
            CommPortIdentifier actualPuertoID = (CommPortIdentifier) puertoEnum.nextElement();
            System.out.println(actualPuertoID.getName());
            if (PUERTO.equals(actualPuertoID.getName())){
                //Si encuentra el puerto establecido, romper le ciclo
                puertoID = actualPuertoID;
                break;
            }
        }
        
        //Si no se encontró el puerto se notificará
        if (puertoID == null)
            System.out.println("No se puede conectar al puerto");

        try{
            serialPort = (SerialPort) puertoID.open(this.getClass().getName(), TIMEOUT);
            // Parámatros puerto serie
            serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_2, SerialPort.PARITY_NONE);
            //Formalización de variable para envío de información
            output = serialPort.getOutputStream();
            //Formalización de variable para recepción de información
            input= new BufferedReader(new InputStreamReader (serialPort.getInputStream()));
            //notificación de conexión exitosa
            System.out.println("Conexion exitosa");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    /*Método para enviío de datos por el puerto serial con un arreglo de bytes*/
    public void enviarDatos(String data){
        try{
            output.write(data.getBytes()); 
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    
     /*Método para erecepción de datos en forma de String*/
    public String recibirDatos(){
        try{
        return input.readLine();}
        catch (Exception e){
            return "";
        }
    }
    
}
