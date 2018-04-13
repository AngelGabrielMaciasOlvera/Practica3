 /*  ------------------- Clase VistaMensajeAlarmaData.java ---------------------------------  
    Clase que establece la lógica o forma de trabajo de la interfaz gráfica planteada. En dicha clase
    se implementa la interface ActionListener para asignar funcionalidad a los botones establecidos
    en el JFrame planteado; además, se inicializa la la conexión a Arduino mediante una variable con 
    apuntador a ConexionArduino.java y se inicializa el hilo encargado de desplegar los mensajes mostrados en 
    pantalla con LCD en conjunto con Arduino con una variable con apuntador a ControladoraMensajes.java
 */

package Data;

import Connection.ConexionArduino;
import View.MensajeAlarmaView;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;
import javax.swing.JOptionPane;

public class VistaMensajeAlarmaData implements ActionListener{
    //Variable con apuntador a la interfaz grafica
    private MensajeAlarmaView o;
    /*Variable con apuntador a la clase ControladoraMensajes, quien controlará
    la emisión de mensajes a al pantalla arduino*/
    private ControladoraMensajes control;
    private ConexionArduino ob;
    
    
    public VistaMensajeAlarmaData(MensajeAlarmaView o){
        //Inicialización de variables
        ob = new ConexionArduino();
        this.o=o;
        ob.inicializarConexion(); 
        control=new ControladoraMensajes("Proceso",ob);
        //Inicio de hilo para mandar mensajes 
        control.start(); 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Detección de fuente de evento
        if (e.getSource()== o.getBtnAgregarMensaje())
            agregarMensaje();
        else if (e.getSource()==o.getBtnEstablecerAlarma())
            agregarAlarma();
    }
    
    /*Metodo para iniciará el proceso de añadir un mensaje a la colección de 
    mensajes predefinidos*/
    public void agregarMensaje(){
        //Agregar el mensaje  a la interfaz gráfica y al arrayList de mensajes 
        String msg=o.getAreaMensaje().getText();
        control.agregarMensaje(msg);
        o.getAreaMensaje().setText("");
        String [] arreglo = new String [2];
        arreglo[0]=control.getMensajes().size()+"";
        arreglo[1]=msg;
        o.getDtmMensaje().addRow(arreglo);
        JOptionPane.showMessageDialog(null, "Mensaje Agregado con éxito");
    }
    
    /*Metodo para iniciará el proceso de añadir alarma*/
    public void agregarAlarma(){
       String hora=o.getTxtAlarma().getText();
       o.getTxtAlarmaEstablecida().setText(hora);
       control.setHora(hora);
    }
    
   
}
