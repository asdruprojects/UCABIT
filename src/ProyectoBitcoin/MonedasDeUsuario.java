package ProyectoBitcoin;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Esta clase contiene los constructores de MonedasDeUsuario
 * y todos los métodos que lo involucran 
 * @author gabop
 *
 */

public class MonedasDeUsuario {

	private String nombre;
	private double cantidad;
	protected double precio;
	private double gasto;
	private double cantidadInvertida;
	
	/**
	 * Este es el constructor vacío
	 * de MonedasDeUsuario
	 */
	public MonedasDeUsuario() {
		this.nombre = "";
		this.cantidad = 0;
	}
	
	/**
	 * Este es el constructor que recibe los 
	 * parametros a la hora de crear una 
	 * instancia de MonedasDeUsuario
	 * @param nombre - nombre de la moneda
	 * @param cantidad - cantidad de monedas que posee el usuario
	 * @param gasto - almacena la cantidad de dinero que la persona invirtió en esta mmoneda
	 * @param cantidadInvertida - cantidad de inversion (en monedas) del usuario
	 */
	public MonedasDeUsuario(String nombre, double cantidad, double gasto, double cantidadInvertida ) {
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.setCantidadInvertida(cantidadInvertida);
		this.setGasto(gasto);
	}
	
	
	
	  /**
	   * Este metodo se utiliza para la venta de monedas del usuario;
	   * resta la cantidad de monedas vendidas y suma el dinero al usuario
	 * @param cantidad - cantidad que se desea vender
	 * @param nombre - nombre de la moneda a vender
	 * @param usuario - informacion del usuario que desea vender
	 */
	public void VenderMoneda(double cantidad, String nombre, Usuario usuario){
	   
		double precio = 0;
			
			for ( int i=0 ; i < 30 ; i++ ) {
			  
				if(nombre.equals(Principal.cryptoMonedas[i].moneda))
			 	
			 		 	precio = Principal.cryptoMonedas[i].precioActual;
			 
			}  
		  
	   	boolean posee = false;
	  	for (int k = 0; k < usuario.monedas.size(); k++){
	   
	   				if ((usuario.monedas.get(k).getNombre() == nombre) && (usuario.monedas.get(k).getCantidad() >= cantidad)){
	   
	   					usuario.monedas.get(k).setCantidad((usuario.monedas.get(k).getCantidad()) - cantidad);
	   					usuario.setDinero(usuario.getDinero() + (precio*cantidad));
	   					posee = true;
	  
	   				}
	   	}
	  		
	  		if (posee = false)
	  			
	  			System.out.println("La cantidad que usted posee de la cryptomoneda elegida es menor a la que desea vender");
	  
	  }
	
	/**
	 * Verifica si el usuario tiene una moneda en especifico
	 * @param monedas - arreglo de monedas del usuario
	 * @param moneda - moneda a buscar
	 * @return - retorna verdadero si o solo si el usuario no posee monedas de este tipo
	 */
	public boolean verificarMoneda(ArrayList<MonedasDeUsuario> monedas,String moneda) {
		for (int i = 0 ; i < monedas.size() ; i++ ) {
			if (monedas.get(i).getNombre().equals(moneda))
				return false;
		}
		return true;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public double getGasto() {
		return gasto;
	}

	public void setGasto(double gasto) {
		this.gasto = gasto;
	}

	public double getCantidadInvertida() {
		return cantidadInvertida;
	}

	public void setCantidadInvertida(double cantidadInvertida) {
		this.cantidadInvertida = cantidadInvertida;
	}
	

}
