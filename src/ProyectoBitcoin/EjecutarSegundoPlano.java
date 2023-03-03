package ProyectoBitcoin;

import javax.swing.SwingWorker;

/**
 * Esta clase contiene un m�todo que funciona como contador 
 * para actualizar los datosde las monedas con la API
 * @author gabop
 *
 */

public class EjecutarSegundoPlano extends SwingWorker<Void,Void>{

	/**
	 *Este m�todo se encarga de llamar a la funci�n que 
	 *solicita los datos a la API, cada 5 segundos
	 */
	@Override
	protected Void doInBackground() throws Exception {
		while(!isDone()) {
			Principal.API();
			try {
				Thread.sleep(5000);   
			  } catch(InterruptedException ie) {}  
		}
		// TODO Auto-generated method stub
		return null;
	}
}
