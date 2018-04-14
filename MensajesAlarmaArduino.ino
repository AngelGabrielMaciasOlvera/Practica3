/* -----------------Alarma digitital panatlla ilustradora de mensajes, tempertuara y hora------------------
 *  
 *  El Siguiente trabajo en Arduino es una práctica que ejemplifica el trabajo de una pantalla ilsutradora 
 *  de mensajes para un destinatario. Dicha práctica consta de de una transición de mensajes ciclica, en la 
 *  cual se muestran emensajes por default del sistmea y pueden agregarse más al mismo, mediante la una 
 *  interfaz gráfica desarrollada en con la tecnología Java; además de que muestra la hora y la tempertura 
 *  en tiempo real. Cabe a destacar, que el usuario puede navegar a travez de los mensajes y emisiones gráficas 
 *  de la panatlla LCD mediante dos botones físicos los cuales tiene la funcionalidad de establcer un flujo 
 *  "hacia adelante" y "hacia atrás". Finalmente, el sistema digital desarrollado cuenta con la funcioanlidad 
 *  extra de poder establecer una alarma desde la aplicación Java a una hora determinada o deseada por el usuario, 
 *  y cuando el tiempo de planteamiento se cumpla se emitirá una melodía de "Feliz Cumpleaños" con el objetivo de 
 *  notificar al usuario que la alarma se ha cumplido.
 *  
 *  Archivo para consulta y modificación orientado al público en general
*/



/*Inclusión de librerias para 
impresión gráfica en pantalla LCD*/
#include <LiquidCrystal_I2C.h>
#include<Wire.h>

/*varable del tipo LiquidCrystal_I2C paraimpresión*/ 
LiquidCrystal_I2C lcd(0x3F,16,2);
//Mensaje o cadena a mostrar
String Mensaje="";
//Definición de pines de los botones y buzzer utilizado
#define botonSig 3
#define botonAtr 2
#define botonSig 3
#define altavoz 6
/*Definición de constantes para emsión de número 
musical "Feliz Cumpleaños con buzzer"*/
int Si2 =1975;
int LaS2=1864;
int La2= 1760;
int SolS2=1661;
int Sol2=1567;
int FaS2=1479;
int Fa2= 1396;
int Mi2= 1318;
int ReS2=1244;
int Re2= 1174;
int DoS2=1108;
int Do2= 1046;
//octava baja
int Si = 987;
int LaS= 932;
int La = 880;
int SolS=830;
int Sol= 783;
int FaS= 739;
int Fa=  698;
int Mi=  659;
int ReS= 622;
int Re = 587;
int DoS =554;
int Do = 523;
//definimos las notas
int redonda=0;
int redondap=0;
int blanca= 0;
int blancap=0;
int negra=0;  
int negrap=0;
int corchea=0;
int corcheap =0;
int semicorchea=0;
int semicorcheap=0;
//definimos la variable del tiempo
int bpm= 120;

void setup() {
  //Inicio de comunicación serial
  Serial.begin(9600);
  Wire.begin();
  /*Preparación de panntalla LCD para 
  visualización de resultados*/
  prepararLCD();
  //Estabelcer el modo de trabajo de los pines
  establecerRolPines();
  //Asigancion de   interrupciones
  prepararVariablesMusicales();
  
}
/*Método qe prepara la pantalla LCD en primera instancia para impresión de resultados*/
void prepararLCD(){
   /*inicialización de variable 
  LCD indicando los segmentos*/
  lcd.begin(16,2);
  //Limpieza de pantalla LCD
  lcd.clear();
  //Acrivación de luz de fonfo en LCD
  lcd.backlight();
}

/*Método que establece el modo de trabajo de los pines de arduino a trabjar*/
void establecerRolPines(){
  /*Colocación del Pin 6 como salida*/
  pinMode(altavoz,OUTPUT);
  /*Prepara y/o define las interrupciones y el tipo de interrupción a usar*/
  pinMode(botonSig,INPUT_PULLUP);
  pinMode(botonAtr,INPUT_PULLUP);
  attachInterrupt(digitalPinToInterrupt(botonSig),interrupcionSig,RISING);
  attachInterrupt(digitalPinToInterrupt(botonAtr),interrupcionAtr,RISING);
 }

void prepararVariablesMusicales(){
  negra= 60000/bpm; 
  negrap=negra*1.5;
  blanca= negra*2;
  blancap=blanca*1.5;
  redonda= negra*4;
  redondap= redonda*1.5;
  corchea= negra/2;
  corcheap=corchea*1.5;
  semicorchea= negra/4;
  semicorcheap=semicorchea*1.5;
}

void loop(){
  //Variable para detectar el numero de caracteres de la cadena a mostrar
  int caracteres=0; 

  /*Condición para detectar si se envía la palabra clave "ALARMA" y 
  activar alrma de acuerdo a lo estbalveido con interfaz en Java*/
  if (Mensaje.equalsIgnoreCase("ALARMA")){
      reproduceMusica();
      }
          
  if (Mensaje.equalsIgnoreCase("TEMP")){
    
      String stringOne =  String(obetnerTemperatura(), 2);
      Mensaje="Temperatura:    "+stringOne+"";
    }
    //Leer los datos recibidos por el serial
    leerYconviertirCadena();
    //Mostrar Mensaje
    imprimirCadenaEnLCD();
}

/*Método para regresar char de acuerdo al valor 
ingresado en ASCII*/ 
char Decimal_to_ASCII(int entrada){
  char salida=' ';
  switch(entrada){
    case 32: 
      salida=' '; 
    break; 
    case 33: 
      salida='!'; 
    break; 
    case 34: 
      salida='"'; 
    break; 
    case 35: 
      salida='#'; 
    break; 
    case 36: 
      salida='$'; 
    break; 
    case 37: 
      salida='%'; 
    break; 
    case 38: 
      salida='&'; 
    break; 
    case 39: 
      salida=' '; 
    break; 
    case 40: 
     salida='('; 
    break; 
    case 41: 
      salida=')'; 
    break; 
    case 42: 
      salida='*'; 
    break; 
    case 43: 
      salida='+'; 
    break; 
    case 44: 
      salida=','; 
    break; 
    case 45: 
      salida='-'; 
    break; 
    case 46: 
      salida='.'; 
    break; 
    case 47: 
      salida='/'; 
    break; 
    case 48: 
      salida='0';   
    break; 
    case 49: 
      salida='1'; 
    break; 
    case 50: 
      salida='2'; 
    break; 
    case 51: 
      salida='3'; 
    break; 
    case 52: 
      salida='4'; 
    break; 
    case 53: 
      salida='5'; 
    break; 
    case 54: 
      salida='6'; 
    break; 
    case 55: 
      salida='7'; 
    break; 
    case 56: 
      salida='8'; 
    break; 
    case 57: 
      salida='9'; 
    break; 
    case 58: 
      salida=':'; 
    break; 
    case 59: 
      salida=';'; 
    break; 
    case 60: 
      salida='<'; 
    break; 
    case 61: 
      salida='='; 
    break; 
    case 62: 
      salida='>'; 
    break; 
    case 63: 
      salida='?'; 
    break; 
    case 64: 
      salida='@'; 
    break; 
    case 65: 
      salida='A'; 
    break; 
    case 66: 
      salida='B'; 
    break; 
    case 67: 
      salida='C'; 
    break; 
    case 68: 
      salida='D'; 
    break; 
    case 69: 
      salida='E'; 
    break; 
    case 70: 
      salida='F'; 
    break; 
    case 71: 
      salida='G'; 
    break; 
    case 72: 
      salida='H'; 
    break; 
    case 73: 
      salida='I'; 
    break; 
    case 74: 
      salida='J'; 
    break; 
    case 75: 
      salida='K'; 
    break; 
    case 76: 
      salida='L'; 
    break; 
    case 77: 
      salida='M'; 
    break; 
    case 78: 
      salida='N'; 
    break; 
    case 79: 
      salida='O'; 
    break; 
    case 80: 
      salida='P'; 
    break; 
    case 81: 
      salida='Q'; 
    break; 
    case 82: 
      salida='R'; 
    break; 
    case 83: 
      salida='S'; 
    break; 
    case 84: 
      salida='T'; 
    break; 
    case 85: 
      salida='U'; 
    break; 
    case 86: 
      salida='V'; 
    break; 
    case 87: 
      salida='W'; 
    break; 
    case 88: 
      salida='X'; 
    break; 
    case 89: 
      salida='Y'; 
    break; 
    case 90: 
      salida='Z'; 
    break; 
    case 91: 
      salida='['; 
    break; 
    case 92: 
      salida=' '; 
    break; 
    case 93: 
      salida=']'; 
    break; 
    case 94: 
      salida='^'; 
    break; 
    case 95: 
      salida='_'; 
    break; 
    case 96: 
      salida='`'; 
    break; 
    case 97: 
      salida='a'; 
    break; 
    case 98: 
      salida='b'; 
    break; 
    case 99: 
      salida='c'; 
    break; 
    case 100: 
      salida='d'; 
    break; 
    case 101: 
      salida='e'; 
    break; 
    case 102: 
      salida='f'; 
    break; 
    case 103: 
      salida='g'; 
    break; 
    case 104: 
      salida='h'; 
    break; 
    case 105: 
      salida='i'; 
    break; 
    case 106: 
      salida='j'; 
    break; 
    case 107: 
      salida='k'; 
    break; 
    case 108: 
      salida='l'; 
   break; 
  case 109: 
    salida='m'; 
  break; 
  case 110: 
    salida='n'; 
  break; 
  case 111: 
    salida='o'; 
  break; 
  case 112: 
    salida='p'; 
  break; 
  case 113: 
   salida='q'; 
  break; 
  case 114: 
    salida='r'; 
  break; 
  case 115: 
    salida='s'; 
  break; 
  case 116: 
    salida='t'; 
  break; 
  case 117: 
    salida='u'; 
  break; 
  case 118: 
    salida='v'; 
  break; 
  case 119: 
    salida='w'; 
  break; 
  case 120: 
    salida='x'; 
  break; 
  case 121: 
    salida='y'; 
  break; 
  case 122: 
    salida='z'; 
  break; 
  case 123: 
    salida='{'; 
  break; 
  case 124: 
    salida='|'; 
  break; 
  case 125: 
    salida='}'; 
  break; 
  case 126: 
    salida='~'; 
  break; 
 }
  return salida;
}

//Método para calcular la temperatura en Grados Centigradps
float obetnerTemperatura(){
  int dato;
  float c;
  //Lectura de lo ingresado en A0 (Ahí se encuentra conectado sensór de temperatura)
  dato=analogRead(A0);
  c=(500.0*dato)/1023;
  return c;
}

//Interrupción para mandar a Javala indicación de mandar al siguiente mensaje
void interrupcionSig(){
  Serial.println("1");
  delay(10000);
}

//Interrupción para mandar a Javala indicación de mandar al mensaje anterior
void interrupcionAtr(){
Serial.println("2");
delay(10000);
}

//Metdodo para imprimir en panatalla LCD
void imprimirCadenaEnLCD(){
  int caracteres=Mensaje.length(); //Se obtiene la longitud de la cadena
  //Condición para saber si la longitud de la cadena es mayor a 16
  if (caracteres>16){ 
    if (Mensaje!=""){
      //Limpieza de la pantalla LCD 
      lcd.clear();
      //Impresión de los primeros 16 caracteres en el primer renglon
      lcd.print(Mensaje.substring(0,16));
       //Cambio al segundo reglón 
      lcd.setCursor(0,1);
      //impresión de los caracterres en el segundo renglon
      lcd.print(Mensaje.substring(16,caracteres)); 
  }
}
  //Flujo para cuando la cadena es menor a 16 caracteres
  else{
    //Condición para saber si se trata d euna cadena no vacia  
    if (Mensaje!=""){
      lcd.clear();
      lcd.print(Mensaje);
    }
  } 

  //Espera de 1 segundo
  delay(1000);
  //Limpieza de la variable que se imprime
  Mensaje=""; 
}

/*Método para reproducción de melodía en alarma*/
void reproduceMusica(){
  //octava alta
  tone(altavoz,Do,corchea);
  delay(corchea+50); 
  tone(altavoz,Do,corchea);
  delay(corchea+50);
  //compas2
  tone(altavoz,Re,negra);
  delay(negra+50);
  tone(altavoz,Do,negra);
  delay(negra+50);
  tone(altavoz,Fa,negra);
  delay(negra+50);
  //compas3
  tone(altavoz,Mi,blanca);
  delay(blanca+50);
  tone(altavoz,Do,corchea);
  delay(corchea+50); 
  tone(altavoz,Do,corchea);
  delay(corchea+50);
  //compas4
  tone(altavoz,Re,negra);
  delay(negra+50);
  tone(altavoz,Do,negra);
  delay(negra+50);
  tone(altavoz,Sol,negra);
  delay(negra+50);
  //compas5
  tone(altavoz,Fa,blanca);
  delay(blanca+50);
  tone(altavoz,Do,corchea);
  delay(corchea+50); 
  tone(altavoz,Do,corchea);
  delay(corchea+50);
  //compas6
  tone(altavoz,Do2,negra);
  delay(negra+50);
  tone(altavoz,La,negra);
  delay(negra+50);
  tone(altavoz,Fa,negra);
  delay(negra+50);
  //compas7
  tone(altavoz,Mi,negra);
  delay(negra+50);
  tone(altavoz,Re,blanca);
  delay(blanca+50);
  //compas8
  tone(altavoz,LaS,corchea);
  delay(corchea+50);
  tone(altavoz,LaS,corchea);
  delay(corchea+50);
  tone(altavoz,La,negra);
  delay(negra+50);
  tone(altavoz,Fa,negra);
  delay(negra+50);
  //compas9
  tone(altavoz,Sol,negra);
  delay(negra+50);
  tone(altavoz,Fa,blanca);
  delay(blanca+50);
}

/*Método para establevcer la lectura de caracteres que se reciben por el puerto serial
y conversión a cadena*/
void leerYconviertirCadena(){
    /*Bucle para leer y convertir los caracteres que se envian por el puerto 
   * serial y mostrar dicho mensaje en pantalla LCD
   NOTA: Serial.availablr() retorna la cantidad de cactres por leer en un momento dado*/
  while (Serial.available()>0) 
    Mensaje=Mensaje+Decimal_to_ASCII(Serial.read()); //Se lee el caracter de entrada, se transforma desde ASCII
    //Se acumulan los caracteres en la variable mensaje
}
