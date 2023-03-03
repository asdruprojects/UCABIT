package ProyectoBitcoin;

import java.awt.EventQueue;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Esta clase contiene el método main, es la clase principal 
 * del programa. Además, contiene los métodos relacionados a la API
 * @author gabop
 *
 */

public class Principal {

		protected static ArrayList<Usuario> usuarios = new ArrayList<Usuario>(); 
		protected static Usuario usuarioActual = new Usuario();
		protected static MonedaBitcoin cryptoMonedas[] = new MonedaBitcoin[30];
		
		
	/**
	 * Este metodo es el encargado de inicializar
	 * la aplicacion
	 * @param args
	 */
	public static void main(String[] args) {  
		  usuarios=ArchivoJSON.LeerData();
		 		  
		  EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						EjecutarSegundoPlano API = new EjecutarSegundoPlano();
						API.execute();
				//		SoloMonedasCirculantes();
						RegistrarUsuario frame = new RegistrarUsuario();
						frame.setLocationRelativeTo(null);
						frame.setVisible(true);			
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
       }
	  

	/**
	 * Este metodo guarda el orden de las monedas de una API
	 * para luego consultar otro tipo de informacion en otra API,
	 * manteniendo siempre el primer orden
	 * @param iD2 
	 * @param id
	 * @return
	 */
	public static int ID(Long[] iD2, long id) {
		  for(int i=0 ; i<30 ; i++) {
			  if(iD2[i]==id) {
				  return i;
			  } 
		  }
		return -1;
	  }
	  
	  
	 /**
	 * Este método consulta la información de la API
	 * cada vez que se requiere, es la que proporciona
	 * toda la información de las monedas
	 */
	public static void API() {
			
		 Long[ ] ID = new  Long[30];
		 try {
			  String inline = "";
			  URL url = new URL("https://api.paaksing.com/crypto/v1/info"); 
			  HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			  conn.setRequestMethod("GET");
		      conn.connect();
		      int responsecode = conn.getResponseCode();
		      if(responsecode != 200)
					throw new RuntimeException("HttpResponseCode: " +responsecode);
				else
				{
					//Scanner functionality will read the JSON data from the stream
					Scanner sc = new Scanner(url.openStream());
					while(sc.hasNext())
					{
						inline+=sc.nextLine();
					}
					sc.close();
				}
		      
		      JSONParser parse = new JSONParser();
			  JSONArray jobj = (JSONArray)parse.parse(inline); 
			  
			  for(int i=0 ; i<30 ; i++)
			    {
				  JSONObject jsonarr_2 = (JSONObject) jobj.get(i);
				  String moneda =(String) jsonarr_2.get("name");
				  String ticked =(String) jsonarr_2.get("symbol");
				  Long Id = (Long) jsonarr_2.get("id");
				  ID[i]=Id;
				  cryptoMonedas[i]=new MonedaBitcoin(moneda,ticked);
			    } 
		 }
		 catch(Exception e)
	     {
					e.printStackTrace();
		 }
		
		try {
			  String inline = "";
			  URL url = new URL("https://api.paaksing.com/crypto/v1/market"); 
			  HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			  conn.setRequestMethod("GET");
		      conn.connect();
		      int responsecode = conn.getResponseCode();
		      if(responsecode != 200)
					throw new RuntimeException("HttpResponseCode: " +responsecode);
				else
				{
					//Scanner functionality will read the JSON data from the stream
					Scanner sc = new Scanner(url.openStream());
					while(sc.hasNext())
					{
						inline+=sc.nextLine();
					}
					sc.close();
				}
		      
		      JSONParser parse = new JSONParser();
			  JSONArray jobj = (JSONArray)parse.parse(inline); 
			  int j=0;
			  
			  for(int i=0 ; i<jobj.size(); i++)
			    {
				  JSONObject jsonarr_2 = (JSONObject) jobj.get(i);
				   Long Id = (Long) jsonarr_2.get("id");
				   j=ID(ID,Id);
				
				  if(j!=-1) {
				 Number ScantidadMonedasCirculantes =(Number) jsonarr_2.get("totalSupply");
				 Double precioActual = (Double) jsonarr_2.get("price");
				 Double marketCap =(Double) jsonarr_2.get("marketCap");
				 Double volumenUltima24Horas =(Double) jsonarr_2.get("volume");
				 Double margenPerdidaGanancia24Horas =(Double) jsonarr_2.get("price24hpc");
				 Double margenPerdidaGanancia1hora =(Double) jsonarr_2.get("price1hpc");
				 Double margenPerdidaGanancia7dias =(Double) jsonarr_2.get("price7dpc");
				 cryptoMonedas[j].precioActual=precioActual;
		    	 cryptoMonedas[j].marketCap= marketCap;
				 cryptoMonedas[j].volumenUltima24Horas=volumenUltima24Horas;
				 cryptoMonedas[j].cantidadMonedasCirculantes= ScantidadMonedasCirculantes;
				 cryptoMonedas[j].margenPerdidaGanancia1hora=margenPerdidaGanancia1hora;
				 cryptoMonedas[j].margenPerdidaGanancia7dias=margenPerdidaGanancia7dias;
				 cryptoMonedas[j].margenPerdidaGanancia24Horas=margenPerdidaGanancia24Horas;
				  }
			    } 
		 }
		 catch(Exception e)
	     {
					e.printStackTrace();
		 }  
	 }
}