package ProyectoBitcoin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Esta clase contiene todos los métodos relacionados
 * con el archvivo JSON, como escritura y lectura
 */

public class ArchivoJSON {
	
	/**
	 * Este método se encarga de leer los datos del archivo JSON,
	 * posteriormente los vacía en un ArrayList de tipo usuario
	 * y devuelve este ArrayList
	 * @return
	 */
	public static ArrayList<Usuario>  LeerData() {
		JSONParser parser = new JSONParser();
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
	
		try {
			JSONParser parse = new JSONParser();
			Object obj = parser.parse(new FileReader("usuarios.json"));
			JSONArray array = (JSONArray) obj;
			for(int i=0;i<array.size();i++)
		    {
				ArrayList<MonedasDeUsuario> monedas = new ArrayList<MonedasDeUsuario>();
				JSONObject jsonObject = (JSONObject) array.get(i);
				String correo = (String) jsonObject.get("correo");
				String director = new Usuario().Desencriptar((String) jsonObject.get("password"));
				boolean autenticacion = (boolean) jsonObject.get("autenticacion");
				String ip = (String) jsonObject.get("direccionIP");
				String fecha = (String) jsonObject.get("fecha");
				String hora = (String) jsonObject.get("hora");
				double dinero = (double) jsonObject.get("dinero");
				JSONArray array2 = (JSONArray) jsonObject.get("monedas");

					for(int j=0 ; j < array2.size() ; j++){
						JSONObject jsonObject2 = (JSONObject) array2.get(j);
						String nombre = (String) jsonObject2.get("nombre");
				 		double cantidad = (double) jsonObject2.get("cantidad");
				 		double gasto = (double) jsonObject2.get("gasto");
				 		double cantidadInvertida = (double) jsonObject2.get("cantidadInvertida");
				 		monedas.add(new MonedasDeUsuario(nombre,cantidad,gasto,cantidadInvertida));
				 		
					}
					
				Usuario usuario = new Usuario(correo,director,autenticacion,ip,hora,fecha,dinero,monedas);
				usuarios.add(usuario);
		    }
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
 catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return usuarios;
	}
	
	
	/**
	 * Este método vacía la informacion contenida del ArrayList
	 * de tipo usuario en el archivo de JSON para poder conservarla
	 * y poder leerla cada vez que se inicia el programa. Además implementa
	 * los métodos Encriptar y Desencriptar de la clase Usuario para evitar
	 * filtración de información
	 *  
	 */
	public static void EscribirData() {
		ObjectMapper om = new ObjectMapper();
		//ObjectMapper om1 = new ObjectMapper();
			try {
				new Usuario().Encriptar(Principal.usuarios);
				 om.writerWithDefaultPrettyPrinter().writeValue(new File("usuarios.json"), Principal.usuarios);
				 for ( int i=0 ; i<Principal.usuarios.size() ; i++ ) {
					
					 Principal.usuarios.get(i).setPassword(new Usuario().Desencriptar(Principal.usuarios.get(i).getPassword()));
					 
					}
		//--------
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}
