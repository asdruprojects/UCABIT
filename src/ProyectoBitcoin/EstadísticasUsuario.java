package ProyectoBitcoin;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
/**
* Esta clase contiene el constructor de la interfaz
* gráfica de estadisticas del usuario, asi como también 
* los métodos necesarios para llenar la informacion
* @author gabop
*
*/
public class EstadísticasUsuario extends JFrame {

	private JPanel interfaz;
	JComboBox<String> comboCriptos;
	DecimalFormat formato = new DecimalFormat("$#.000");
	/**
	 * Este es el constructor de la interfaz (y sus componentes)
	 * de la interfaz gráfica de las estadisticas del usuario
	 */
	public EstadísticasUsuario() {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("addons/icon.jpg"));
		setFont(new Font("Arial", Font.PLAIN, 18));
		setResizable(false);
		setTitle("Estad\u00EDsticas de las inversiones | UCABIT");
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 389, 267);
		interfaz = new JPanel();
		interfaz.setBackground(SystemColor.inactiveCaption);
		interfaz.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(interfaz);
		interfaz.setLayout(null);
		
		JLabel icon = new JLabel("");
		icon.setIcon(new ImageIcon("addons/bit-logo.png"));
		icon.setHorizontalAlignment(SwingConstants.CENTER);
		icon.setBounds(10, 29, 80, 80);
		interfaz.add(icon);
		
		JLabel titulo = new JLabel("Estadisticas de Inversion ");
		titulo.setForeground(Color.BLACK);
		titulo.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 18));
		titulo.setHorizontalAlignment(SwingConstants.LEFT);
		titulo.setBounds(86, 56, 273, 23);
		interfaz.add(titulo);
		
		JLabel sesion = new JLabel("Sesion actual:");
		sesion.setFont(new Font("Arial", Font.BOLD, 12));
		sesion.setHorizontalAlignment(SwingConstants.RIGHT);
		sesion.setBounds(10, 11, 80, 18);
		interfaz.add(sesion);
		
		JLabel sesionActual = new JLabel("No hay ninguna sesion iniciada");
		sesionActual.setForeground(Color.BLUE);
		sesionActual.setHorizontalAlignment(SwingConstants.LEFT);
		sesionActual.setFont(new Font("Arial", Font.ITALIC, 12));
		sesionActual.setBounds(100, 13, 273, 18);
		if (!Principal.usuarioActual.getCorreo().isBlank()){
			sesionActual.setText(Principal.usuarioActual.getCorreo()); 
		}
		interfaz.add(sesionActual);
		
		
		
		comboCriptos = new JComboBox<String>();
		comboCriptos.setToolTipText("Selecione una moneda para agregarla o eliminarla del portafolio");
		comboCriptos.setFont(new Font("Arial", Font.PLAIN, 12));
		comboCriptos.setBounds(142, 111, 107, 22);
		interfaz.add(comboCriptos);
		
		String Nombre=String.valueOf(comboCriptos.getSelectedItem());
		int i = posicionUSUARIO(Nombre);
		int f= posicion(Nombre);
		DecimalFormat formato1 = new DecimalFormat("%#.000");
		JButton btnNewButton = new JButton("Calculo Inversion");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			  if(Principal.usuarioActual.monedas.size()!=0) {	
				double inversionActual=Principal.usuarioActual.monedas.get(i).getCantidadInvertida()*Principal.cryptoMonedas[f].precioActual;
				double difereciaInversion = inversionActual-Principal.usuarioActual.monedas.get(i).getGasto();
				double porcentajeInversion= (difereciaInversion*100)/Principal.usuarioActual.monedas.get(i).getGasto();
				JOptionPane.showMessageDialog(null, "Su porcentaje de inversion ha sido: "+formato1.format(porcentajeInversion));
			  }	
			}
		});
		btnNewButton.setBounds(40, 164, 137, 34);
		interfaz.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Total");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Nombre=String.valueOf(comboCriptos.getSelectedItem());
				int i = posicionUSUARIO(Nombre);
				int f= posicion(Nombre);
				if(Principal.usuarioActual.monedas.size()!=0) {	
					double inversionActual=Principal.usuarioActual.monedas.get(i).getCantidadInvertida()*Principal.cryptoMonedas[f].precioActual;
					JOptionPane.showMessageDialog(null, "Su inversion total ha sido: "+formato.format(inversionActual));
				}
			}
		});
		btnNewButton_1.setBounds(211, 164, 124, 34);
		interfaz.add(btnNewButton_1);
		llenarComboBox();
	}
	/**
	 * Este procedimiento se encarga de llenar las ComboBox
	 * con los datos de las monedas que posee el usuario
	 */
	public void llenarComboBox() {
		for ( int i=0 ; i < Principal.usuarioActual.monedas.size() ; i++ ) {
			comboCriptos.addItem(Principal.usuarioActual.monedas.get(i).getNombre());
		}
	}
	/**
	 * Este metodo busca la posicion de la moneda
	 * solicitada en el arreglo de monedas del usuario
	 * @param nombre - nombre de la moneda solicitada
	 * @return - entero con la posicion de la moneda dentro del arreglo
	 */
	public int posicionUSUARIO(String nombre) {
		for ( int f=0 ; f < Principal.usuarioActual.monedas.size() ; f++ ) {
			if(nombre.equals(Principal.usuarioActual.monedas.get(f).getNombre())) {
				return f;
			}
		}
		return 0;
	}
	/**
	 * Este metodo busca la posicion de la moneda
	 * solicitada en el arreglo de monedas creado
	 * a partir de la API (30 mejor valuadas)
	 * @param nombre - nombre de la moneda solicitada
	 * @return - entero con la posicion de la moneda dentro del arreglo (retorna 0 si no la consigue)
	 */
	public int posicion(String nombre) {
		for ( int f=0 ; f < 30 ; f++ ) {
			if(nombre.equals(Principal.cryptoMonedas[f].moneda)) {
				return f;
			}
		}
		return 0;
	}
}
