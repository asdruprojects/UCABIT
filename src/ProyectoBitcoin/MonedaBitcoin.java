package ProyectoBitcoin;

import java.text.DecimalFormat;

/**
 * En esta clase se encuentra el  constructor del objeto de
 * tipo MonedaBitcoin, además del método para imprimir objetos
 * de este tipo
 * @author gabop
 *
 */
public class MonedaBitcoin {
	
	protected String moneda;
	protected String ticked;
	protected double precioActual;
	protected double marketCap;
	protected double volumenUltima24Horas;
	protected Number cantidadMonedasCirculantes;
	protected double margenPerdidaGanancia24Horas;
	protected double margenPerdidaGanancia1hora;
	protected double margenPerdidaGanancia7dias;
	
	/**
	 * Este es un constructor vacío de MonedaBitcoin
	 */
	public MonedaBitcoin() {
	}
	
	/**
	 * Este es el constructor de MonedaBitcoin que recibe parámetros 
	 * para inicializar los valores de las instancias
	 * 
	 * @param moneda - nombre de la moneda 
	 * @param ticked - ticked de la moneda 
	 * @param precioActual - precio actual de la moneda
	 * @param marketCap - capitalizacion en el mercado de la moneda
	 * @param volumenUltima24Horas - volumen de la moneda en las ultimas 24 horas
	 * @param cantidadMonedasCirculantes - cantidad de monedas circulantes 
	 * @param margenPerdidaGanancia24Horas - margen de perdida/ganancia de la moneda
	 */
	public MonedaBitcoin(String moneda, String ticked,double precioActual,double marketCap,double volumenUltima24Horas,int cantidadMonedasCirculantes,double margenPerdidaGanancia24Horas) {
		this.moneda = moneda;
		this.ticked = ticked;
		this.precioActual = precioActual;
		this.marketCap = marketCap;
		this.volumenUltima24Horas = volumenUltima24Horas;
		this.cantidadMonedasCirculantes = cantidadMonedasCirculantes;
		this.margenPerdidaGanancia24Horas=margenPerdidaGanancia24Horas;
	}
	
	/**
	 * Constructor más básico de MonedaBitcoin
	 * @param moneda - nombre de la moneda
	 * @param ticked - ticked de la moneda
	 */
	public MonedaBitcoin(String moneda, String ticked) {
		this.moneda = moneda;
		this.ticked = ticked;
	}
	
	
	/**
	 * Este procedimiento imprime las criptomonedas 
	 * con sus respectivos valores
	 */
	public void ImprimirMoneda() {
		DecimalFormat formato = new DecimalFormat("#.00000");
		DecimalFormat formato1 = new DecimalFormat("0.000");
		System.out.println("Moneda: "+moneda);
		System.out.println("Ticked: "+ticked);
		System.out.println("Precio Actual: "+ precioActual +"$");
		System.out.println("Capitalización de Mercado: "+formato.format(marketCap)+"$");
		System.out.println("Volumen (24 Horas): "+formato.format(volumenUltima24Horas)+"$");
		System.out.println("Monedas Circulantes: "+cantidadMonedasCirculantes+" "+ticked);
		System.out.println("Porcentaje de Perdida/Ganancia (24 Horas): "+formato1.format(margenPerdidaGanancia24Horas)+"%");
	}
}