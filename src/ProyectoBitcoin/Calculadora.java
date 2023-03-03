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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 * Esta clase contiene el constructor de la interfaz gráfica
 * de la Calculadora, además de un método para llenar las ComboBox
 * (lista desplegable de monedas)
 * @author gabop
 *
 */

public class Calculadora extends JFrame {

	private JPanel interfaz;
	JComboBox<String> comboCriptosCA,comboCriptosCB;
	private JTextField campoResultado;
	private JTextField campoMonto;
	
	/**
	 * Declaración del constructor de la interfaz (y sus componentes) de
     * la calculadora, además,llama a la funcion que llena las ComboBox
	 */
	
	public Calculadora() {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("addons/icon.jpg"));
		setFont(new Font("Arial", Font.PLAIN, 18));
		setResizable(false);
		setTitle("Calculadora de conversi\u00F3n | UCABIT");
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 389, 364);
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
		
		JLabel titulo = new JLabel("Calculadora de conversi\u00F3n");
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
		
		JButton boCalcular = new JButton("Calcular");
		boCalcular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					DecimalFormat formato2 = new DecimalFormat("#.0000");
					if ( (comboCriptosCA.getSelectedItem().equals("")) || (comboCriptosCB.getSelectedItem().equals("")) || (Double.parseDouble(campoMonto.getText()) == 0) ) {
						JOptionPane.showMessageDialog(null, "Debe llenar todos los campos");
					}else {
						if(Double.parseDouble(campoMonto.getText()) > -1) {
							double a,b;
							for ( int i=0 ; i < 30 ; i++ ) {
								if(comboCriptosCA.getSelectedItem().equals(Principal.cryptoMonedas[i].moneda)) {
									a= Principal.cryptoMonedas[i].precioActual;
									for( int f=0 ; f < 30 ; f++ ) {
										if(comboCriptosCB.getSelectedItem().equals(Principal.cryptoMonedas[f].moneda)) {
											b= Principal.cryptoMonedas[f].precioActual;
											a *= Double.parseDouble(campoMonto.getText());
											campoResultado.setText(String.valueOf(formato2.format(a/b)) + " " + Principal.cryptoMonedas[f].ticked);
											campoMonto.setText("");
											comboCriptosCA.setSelectedIndex(0);
											comboCriptosCB.setSelectedIndex(0);
										}
									}
								}
							}
						}else {
							JOptionPane.showMessageDialog(null, "El monto ingresado no puede ser negativo");
						}
					}
				}catch(Exception e) {
					JOptionPane.showMessageDialog(null, "El monto ingresado no es valido");
				}
			}
			
		});
		boCalcular.setFont(new Font("Arial", Font.PLAIN, 13));
		boCalcular.setBounds(145, 262, 89, 23);
		interfaz.add(boCalcular);
		
		comboCriptosCA = new JComboBox();
		comboCriptosCA.setBounds(20, 145, 146, 22);
		interfaz.add(comboCriptosCA);
		
		comboCriptosCB = new JComboBox();
		comboCriptosCB.setBounds(20, 207, 146, 22);
		interfaz.add(comboCriptosCB);
		
		JLabel combersor = new JLabel("De:");
		combersor.setHorizontalAlignment(SwingConstants.LEFT);
		combersor.setFont(new Font("Arial", Font.BOLD, 13));
		combersor.setBounds(30, 116, 31, 18);
		interfaz.add(combersor);
		
		campoResultado = new JTextField();
		campoResultado.setEditable(false);
		campoResultado.setFont(new Font("Arial", Font.PLAIN, 11));
		campoResultado.setToolTipText("Resultado de la conversion");
		campoResultado.setBounds(176, 208, 102, 20);
		interfaz.add(campoResultado);
		campoResultado.setColumns(10);
		
		JLabel convertido = new JLabel("a:");
		convertido.setHorizontalAlignment(SwingConstants.LEFT);
		convertido.setFont(new Font("Arial", Font.BOLD, 13));
		convertido.setBounds(30, 178, 24, 18);
		interfaz.add(convertido);
		
		campoMonto = new JTextField();
		campoMonto.setFont(new Font("Arial", Font.PLAIN, 11));
		campoMonto.setBounds(176, 146, 102, 20);
		interfaz.add(campoMonto);
		campoMonto.setColumns(10);
		
		llenarComboBox();
	}
	/**
	 * Este procedimiento se encarga de llenar las ComboBox
	 * con los datos de las monedas proporcionados por la API
	 */
	public void llenarComboBox() {
		comboCriptosCA.addItem("");
		comboCriptosCB.addItem("");
		for ( int i=0 ; i < 30 ; i++ ) {
			comboCriptosCA.addItem(Principal.cryptoMonedas[i].moneda);
			comboCriptosCB.addItem(Principal.cryptoMonedas[i].moneda);
		}
	}
}