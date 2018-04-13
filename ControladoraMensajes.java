package Data;

import Connection.ConexionArduino;
import java.util.ArrayList;
import java.util.Calendar;


public class ControladoraMensajes extends Thread{
    //Variable del tipo ConexionArduino para enviar y recibir mensajes
    private ConexionArduino o;
    //Array para guardar mensajes
    private ArrayList<String> mensajes;
    //Varibale para la guardarla hora asignada
    private String hora;
    
    public ControladoraMensajes(String msg, ConexionArduino o){
        super(msg);
        //Formalización de variabñes
        mensajes = new ArrayList<String>();
        cargaMensajesPredeterminados();
        hora="";
        this.o=o;
    }
    
    public void run(){
        int i=0;
        //Inicio de bucle infinito para mandar mensajes permanentemente a arduino
        while(true){
            /*Si la variable i alcanza el tammaño del arrayList ya no hay 
                mensajes por mostrar y mandrá palabra clave TEMP a arduino 
                para mostrar temperatura*/
            if (i==mensajes.size())
                o.enviarDatos("TEMP");
            else {
                //Si i es igual a 0 que ilustre la hora actual
                if (i==0)
                    o.enviarDatos("La hora Actual  es: "+obtenerHoraActual());
                else 
                    //Caso contrario mostrar mensajes ordianles
                    o.enviarDatos(mensajes.get(i));
            }
                
            /*Bucle para mostrar mensaje y detectar si un pulsador ha 
                sido presioando*/
            for (int a = 0 ; a <=10; a++){
                try { 
                    String res=o.recibirDatos();
                    //Si se detecta una cadena emitida de arduino
                    //entrar a decisión
                    if(res.length()>0){
                        //Si arduino emitio un 1 romper el ciclo y mostrar sig.mesnaje
                        if (res.equalsIgnoreCase("1"))
                            break;
                        //Si arduino emitio una cadena diferente  a 1 retroceder mensaje 
                        else{
                            if (i>0)
                                i=i-2;    
                        }
                    }
                    else{
                        //Detectar si la hora actual es igual a la hora guardada  
                        if (this.hora.equalsIgnoreCase(obtenerHoraActual()))
                            //Se envia la palabra clave ALARMA a arduino para que se active alarma
                            o.enviarDatos("ALARMA");
                            this.setHora("");
                            //Dormir el hilo
                        Thread.sleep(5);
                    }
                } 
                catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
            //Incrementop de contador
            i++;
            //Si el contador la excedio el tamaño del ArrayList en 1, poner el mismo en 0
            if (i==mensajes.size()+1)
                i=0;
        }
    }
    
    /*Método para definir los mensajer precargardos por el sistema*/
    public void cargaMensajesPredeterminados(){
        mensajes.add("Hora");
        mensajes.add("Leon");
        mensajes.add("Guanajuato");
        mensajes.add("Mexico");
    }
    
    /*Método para obetner la hora Actual*/
    public String obtenerHoraActual(){
        Calendar calendario = Calendar.getInstance();
        int hora =calendario.get(Calendar.HOUR_OF_DAY);
        int minutos = calendario.get(Calendar.MINUTE);
        return (hora+":"+minutos);
    }
    
    /*Método para agregar mensajes a la colección establecida*/
    public void agregarMensaje(String mensaje){
        mensajes.add(mensaje);
    }
     /*Método Get para manipulación de Array desde clase externa*/
    public ArrayList<String> getMensajes() {
        return mensajes;
    }
    
    /*Metodo para guardar la hora que asigno el usuario en alarma*/
    public void setHora(String hora){
        this.hora=hora;
    }
    
}
