/*------------Clase MensajeAlarmaView.java-------------------
Clase dediacada para la creación o construcción de Interfaz gráfica en Java,
con la cual el usuario podrá entrar en comunicación e interacción con el 
sistema digital mediante la agregación de mensajes y establecimiento de Alarma 
en un determinado horario*/

package View;

import Connection.ConexionArduino;
import Data.VistaMensajeAlarmaData;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class MensajeAlarmaView extends JFrame{
    //Componentes que formarán parte de la interfaz gráfica mostrada al usuario
    private JLabel lblAlarma, lblMensaje;
    private JTextArea areaMensaje;
    private JTextField txtAlarma, txtAlarmaEstablecida;
    private JButton btnAgregarMensaje, btnEstablecerAlarma;
    private JTable tblMensajes;
    private DefaultTableModel dtmMensaje;
    private JScrollPane scrollTabla;
    
    /*Variable del tipo VistaMensajeAlarmaData donde se plasma la logica de 
    trabajo del sistema y se implementa la clase ActionListener*/
    private VistaMensajeAlarmaData datos;
    
    public MensajeAlarmaView(){
        //Instanciamiento de los componentes mnecesarios
        inicializaComponentes();
        //Establecimiento de carcateristicas básica del frame
        establecerCaracteristicasFrame();
        //Establecimiento de detalles secundarios al frame
        estabelcerDetallesComponentes();
        //Distribuación de los componentes a lo largo de la interfaz gráfica
        distribuyeComponentes();
        //Registro de oyentes a botones de la interfaz
        agregarOyente();
        //Hacer visible el frame
        this.setVisible(true);
    }
    
    /*Método para establecer las caractersiticas básica y minimas del JFrame
    como tamaño, Layout, localización, etc*/
    public void establecerCaracteristicasFrame(){
        //Titulo del frame
        this.setTitle("Alarma y Mensajes en Arduino");
        //Asiganción de Layout nulo
        this.setLayout(null);
        //Tamaño del frame
        this.setSize(753,593);
        //Localización del frame al centro de la pantall
        this.setLocationRelativeTo(null);
        //Termino de la aplicación al cerrar el frame
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    /*Método para establecer las caractersiticas básica y minimas del JFrame
    como tamaño, Layout, localización, etc*/
    public void inicializaComponentes(){
        lblAlarma=new JLabel("Alarma");
        lblMensaje= new JLabel ("Mensaje");
        txtAlarma= new JTextField();
        txtAlarmaEstablecida=new JTextField();
        areaMensaje= new JTextArea();
        btnAgregarMensaje= new JButton("Agregar Mensaje");
        btnEstablecerAlarma=new JButton("Añadir");
        tblMensajes= new JTable();
        dtmMensaje= new DefaultTableModel();
        scrollTabla= new JScrollPane();
        datos = new VistaMensajeAlarmaData(this);
    }
    
    public void estabelcerDetallesComponentes(){
        //Asiganación del modelo a la tabla
        tblMensajes.setModel(dtmMensaje);
        //Asiganacion de tabla a scroll
        scrollTabla.setViewportView(tblMensajes);
        /*Asiganación de no editable al campo de información si la tabla se 
        encuentra establecida y su valor por defecto*/
        txtAlarmaEstablecida.setEditable(false);
        txtAlarmaEstablecida.setText("No establecida");
        /*Asignación de propiedad a JTextArea para que el texto ingresado se 
        visto siempre por el usuario*/
        areaMensaje.setLineWrap(true);
        /*Agregación de columnas al DefaultTableModel de la tabla*/
        dtmMensaje.addColumn("Numero de Mensaje");
        dtmMensaje.addColumn("Detalle del Mensaje");  
    }
    
    /*Meétodo para distribución d elos componentes a lo largo del JFrame con
    setBounds; indicando localización en x,y, largo y ancho*/
    public void distribuyeComponentes(){
        lblAlarma.setBounds(10, 50, 100, 20);
        this.add(lblAlarma);
        txtAlarma.setBounds(130,41,130,26);
        this.add(txtAlarma);
        btnEstablecerAlarma.setBounds(303,48, 75,22);
        this.add(btnEstablecerAlarma);
        txtAlarmaEstablecida.setBounds(431, 41,130,26);
        this.add(txtAlarmaEstablecida);
        lblMensaje.setBounds(22, 133, 100,20);
        this.add(lblMensaje);
        areaMensaje.setBounds(22, 150, 344, 120);
        this.add(areaMensaje);
        btnAgregarMensaje.setBounds(427,241, 177,33);
        this.add(btnAgregarMensaje);
        scrollTabla.setBounds(22, 333, 663,200);
        this.add(scrollTabla);
    }
    
    /*Registro de oyente a los botones del JFrame*/
    public void agregarOyente(){
        btnAgregarMensaje.addActionListener(datos);
        btnEstablecerAlarma.addActionListener(datos);
    }
    
    //Métodos Get de los componentes para poder ser manipulados por otra clase
    public JTextArea getAreaMensaje() {
        return areaMensaje;
    }

    public JTextField getTxtAlarma() {
        return txtAlarma;
    }

    public JTextField getTxtAlarmaEstablecida() {
        return txtAlarmaEstablecida;
    }

    public JButton getBtnAgregarMensaje() {
        return btnAgregarMensaje;
    }

    public JButton getBtnEstablecerAlarma() {
        return btnEstablecerAlarma;
    }

    public DefaultTableModel getDtmMensaje() {
        return dtmMensaje;
    }
      
}
