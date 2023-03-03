package ProyectoBitcoin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Esta clase contiene los constructores de Usuario, así 
 * como también todos los métodos relacionados con el mismo
 * @author gabop
 *
 */

public class Usuario {
	
	private String correo;
	private String password;
	private boolean autenticacion;
	private String hora;
	private String fecha;
	private String direccionIP;
	private double dinero;
	public ArrayList<MonedasDeUsuario> monedas = new ArrayList<MonedasDeUsuario>();
	
	/**
	 * Este es el constructor de usuario sin paso de parametros
	 */
	public Usuario() {
		this.correo = "";
		this.password = "";
		this.autenticacion = false;
		this.direccionIP = "";
		this.hora = "";
		this.fecha = "";
		this.dinero = 500;
		this.monedas = null;
	}
	/**
	 * Este constructor de usuario sí recibe informacion y 
	 * se usa al momento de crear una instancia de usuario
	 * con los datos del usuario
	 * @param correo - correo introducido por el usuario
	 * @param password - contraseña generada por el usuario
	 * @param autenticacion - establece el estado de autenticación del usuario
	 * @param ip - ip de la ultima autenticacion del usuario
	 * @param hora - hora de la ultima autenticacion del usuario
	 * @param fecha - fecha de la ultima autenticacion del usuario
	 * @param dinero - monto en USDT del usuario
	 */
	public Usuario(String correo, String password, boolean autenticacion, String ip, String hora, String fecha, double dinero) {
		this.correo = correo;
		this.password = password;
		this.autenticacion = autenticacion;
		this.direccionIP = ip;
		this.hora = hora;
		this.fecha = fecha;
		this.dinero = dinero;
	}
	/**
	 *Este constructor de usuario es más completo 
	 *que el anterior, ya que recibe un ArrayList 
	 *de las monedas del usurio
	 * @param correo - correo introducido por el usuario
	 * @param password - contraseña generada por el usuario
	 * @param autenticacion - establece el estado de autenticación del usuario
	 * @param ip - ip de la ultima autenticacion del usuario
	 * @param hora - hora de la ultima autenticacion del usuario
	 * @param fecha - fecha de la ultima autenticacion del usuario
	 * @param dinero - monto en USDT del usuario
	 * @param monedas - ArrayList de las monedas que posee el usuario
	 */
	public Usuario(String correo, String password, boolean autenticacion, String ip, String hora, String fecha, double dinero, ArrayList<MonedasDeUsuario> monedas) {
		this.correo = correo;
		this.password = password;
		this.autenticacion = autenticacion;
		this.direccionIP = ip;
		this.hora = hora;
		this.fecha = fecha;
		this.dinero = dinero;
		this.monedas = monedas;
	}
	
	/**
	 * Este metodo verifica que el correo que intente 
	 * registrar el usuario cumpla el formato de una 
	 * dirección de correo real 
	 * @param email - correo ingresado por el usuario
	 * @return - regresa un valor verdadero si y solo si el correo es válido
	 */
	public boolean validarCorreo(String email) {
        Pattern patronCorreo = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        
        Matcher ejemplo = patronCorreo.matcher(email);
        
        if (ejemplo.find() == true)
            return true;
        return false;
	}
	
	/**
	 * Este metodo verifica que la contraseña introducida 
	 * por el usuario cumpla las condiciones solicitadas 
	 * (Mayúsculas, minúsculas, números, caracteres especiales) 
	 * @param password - contraseña introducida por el usuario
	 * @return - regresa un valor verdadero si y solo si la contraseña es válida
	 */
	public boolean validarPassword(String password) {
		char ch;
	    boolean boolMayus = false;
	    boolean boolMinus = false;
	    boolean boolNumer = false;
	    boolean boolCaracterEspe = false;
	    
	    for( int i=0 ; i < password.length() ; i++ ) {
	        ch = password.charAt(i);
	        if( Character.isDigit(ch)) {
	        	boolNumer = true;
	        }
	        else if (Character.isUpperCase(ch)) {
	        	boolMayus = true;
	        } else if (Character.isLowerCase(ch)) {
	        	boolMinus = true;
	        } else if ( ((int)password.charAt(i) > 32) && ((int)password.charAt(i) <= 47)) {
	        	boolCaracterEspe = true;
	        }
	        if(boolNumer && boolMayus && boolMinus && (password.length()>8) && boolCaracterEspe)
	            return true;
	    }
	    return false;
	}
	
	
	
	/**
	 * Este metodo verifica la existencia del usuario solicitado,
	 * usado para evitar correos duplicados
	 * @param usuarios - arreglo que contiene todos los usuario
	 * @param correo - correo cuya existencia se desea verificar
	 * @return - regresa un valor verdadero si y solo si no se consigue la direccion de correo solicitada
	 */
	public boolean validarExistencia(ArrayList<Usuario> usuarios,String correo) {
		for ( int i=0 ; i<usuarios.size() ; i++ ) {
			if(usuarios.get(i).getCorreo().equals(correo))
				return false;
		}
		return true;
	}
	
	/**
	 * Este metodo verifica si el correo y la contraseña introducidos
	 * por el usuario se corresponden y existen, esto para poder
	 * autenticarlo exitosamente
	 * @param usuarios - arreglo que contiene todos los usuario
	 * @param correo - correo introducido por el usuario
	 * @param password - contraseña introducida por el usuario
	 * @return - regresa un valor verdadero si y solo si el correo existe y la contraseña es correcta
	 */
	public boolean validarUsuario(ArrayList<Usuario> usuarios,String correo,String password) {
		for ( int i=0 ; i<usuarios.size() ; i++ ) {
			if( (usuarios.get(i).getCorreo().equals(correo)) && (usuarios.get(i).getPassword().equals(password)) ) {
				Principal.usuarioActual = usuarios.get(i);
				usuarios.get(i).setAutenticacion(true);
				return true;
			}
		}
		return false;
	}

	/**
	 * Este metodo modifica la contraseña de un usuario 
	 * luego de verificar la contraseña antigua (para evitar 
	 * cambios accidentales o malintencionados) 
	 * @param usuarios - arreglo que contiene todos los usuario
	 * @param correo - correo de la sesion actual
	 * @param password - contraseña antigua del usuario (introducida por el mismo)
	 * @param newPassword - contraseña nueva del usuario (introducida por el mismo)
	 * @return - regresa un valor verdadero si y solo si el correo existe y los datos son correctos
	 */
	public boolean modificarPassword(ArrayList<Usuario> usuarios,String correo,String password,String newPassword) {
		for ( int i=0 ; i<usuarios.size() ; i++ ) {
			if( (usuarios.get(i).getCorreo().equals(correo)) && (usuarios.get(i).getPassword().equals(password)) ) {
				usuarios.get(i).setPassword(newPassword);
				return true;
			}
		}
		return false;
	}
	

	/**
	 * Este metodo sirve para reestablecer el valor de 
	 * autenticidad del usuario actual (a falso) antes
	 * de autenticar un nuevo usuario
	 * @param usuarios - arreglo que contiene todos los usuario
	 */
	public void cambioDeUsuario(ArrayList<Usuario> usuarios) {
		
		for ( int i=0 ; i<usuarios.size() ; i++ ) {
			
			if(usuarios.get(i).getCorreo().equals(Principal.usuarioActual.getCorreo())) { 
			
				usuarios.get(i).setAutenticacion(true);
				
			} else {
				
				usuarios.get(i).setAutenticacion(false);
				
			}		
		}
	}	
	
	/**
	 * Este metodo sirve para reestablecer el valor 
	 * de autenticidad de todos los usuarios (a falso) 
	 * antes de cerrar el programa
	 * @param usuarios - arreglo que contiene todos los usuario
	 */
	public void establecerFalso(ArrayList<Usuario> usuarios) {
		
		for ( int i=0 ; i<usuarios.size() ; i++ ) 

				usuarios.get(i).setAutenticacion(false);
		
		ArchivoJSON.EscribirData();
						
	}
	
	/**
	 * Este metodo busca al usuario actual en el arreglo y 
	 * reemplaza la IP de su última autenticación por la nueva IP
	 * @param usuarios - arreglo que contiene todos los usuario
	 */
	public void reemplazarIP(ArrayList<Usuario> usuarios) {
		
		for ( int i=0 ; i<usuarios.size() ; i++ ) {
			
			if(usuarios.get(i).getCorreo().equals(Principal.usuarioActual.getCorreo())) {
				
				InetAddress address;

				try {
					address = InetAddress.getLocalHost();
				    usuarios.get(i).setDireccionIP (address.getHostAddress());
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * Este metodo busca al usuario actual en el arreglo y 
	 * reemplaza la fecha y hora de su última autenticación 
	 * por la nueva fecha y hora de autenticación
	 * @param usuarios - arreglo que contiene todos los usuario
	 */
	public void reemplazarHoraFecha(ArrayList<Usuario> usuarios) {
		
		LocalDate fecha = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String formattedFecha = fecha.format(formatter);
		
		LocalTime hora = LocalTime.now();
		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("hh:mm:ss");
		String formattedHora = hora.format(formatter1);
		
			for ( int i=0 ; i<usuarios.size() ; i++ ) {
				
				if(usuarios.get(i).getCorreo().equals(Principal.usuarioActual.getCorreo())) {
					
					usuarios.get(i).setHora(formattedHora);
					usuarios.get(i).setFecha(formattedFecha);

					
				}
			}
	}
	
	
	/**
	 * Este metodo encripta las contraseñas de todos los usuario
	 * @param usuarios - arreglo que contiene todos los usuario
	 */
	public void Encriptar(ArrayList<Usuario> usuarios) {
		for ( int i=0 ; i<usuarios.size() ; i++ ) {
			
			char array[] = usuarios.get(i).getPassword().toCharArray();
			
			for(int j = 0; j < array.length; j++) {
					
					array[j] = (char)(array[j] + (char)1);

			}	
			usuarios.get(i).setPassword(String.valueOf(array));
		}
	}
	
	
	/**
	 * Este metodo desencripta solamente la contraseña solicitada
	 * @param password - contraseña del usuario 
	 * @return - string con la contraseña desencriptada
	 */
	public String Desencriptar(String password) {
		
		char array[] = password.toCharArray();		
		for(int i = 0; i < array.length; i++) {
				
				array[i] = (char)(array[i] - (char)1);

		}	
		
		return String.valueOf(array);		
	}
	
	/**
	 * Este metodo retorna el valor de correo
	 * @return - string con el valor del correo
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Este metodo modifica el valor de correo
	 * @param correo - correo del usuario
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * Este metodo retorna el valor de password
	 * @return - string con el valor de la contraseña
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Este metodo modifica el valor de password
	 * @param password - contraseña del usuario
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Este método devuelve el valor de autenticacion
	 * @return - booleano que indica el estado de autenticacion
	 */
	public boolean isAutenticacion() {
		return autenticacion;
	}

	/**
	 * Este método modifica el valor de autenticacion
	 * @param autenticacion - valor del estado de autenticacion
	 */
	public void setAutenticacion(boolean autenticacion) {
		this.autenticacion = autenticacion;
	}

	/**
	 * Este metodo retorna el valor de hora
	 * @return - string con el valor de la hora de autenticacion
	 */
	public String getHora() {
		return hora;
	}

	/**
	 * Este metodo modifica el valor de hora
	 * @param hora - hora de autenticacion
	 */
	public void setHora(String hora) {
		this.hora = hora;
	}

	/**
	 * Este metodo retorna el valor de fecha
	 * @return - string con el valor de la fecha de autenticacion
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * Este método modifica el valor de fecha
	 * @param fecha - fecha de autenticacion
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * Este metodo retorna el valor de direccionIP
	 * @return - string con el valor de la dirección IP
	 */
	public String getDireccionIP() {
		return direccionIP;
	}

	/**
	 * Este método modifica el valor de direccionIP
	 * @param direccionIP - direccion ip de autenticacion
	 */
	public void setDireccionIP(String direccionIP) {
		this.direccionIP = direccionIP;
	}

	/**
	 * Este metodo retorna el valor de dinero
	 * @return - double con el valor de la cantidad de dinero
	 */
	public double getDinero() {
		return dinero;
	}

	/**
	 * Este metodo modifica el valor de dinero
	 * @param dinero - cantidad de dinero 
	 */
	public void setDinero(double dinero) {
		this.dinero = dinero;
	}
	
}