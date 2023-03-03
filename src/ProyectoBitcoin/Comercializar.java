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
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;

/**
 * Esta clase contiene el constructor de la interfaz
* gráfica del comercio con Cripto monedas y sus respectivas
* funciones para comprar y vender
 * @author AaronP
 *
 */
public class Comercializar extends JFrame {
	
	private JPanel interfaz;

	public Comercializar(int ComprarVender,int PosicionUsuario) {
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage("addons/icon.jpg"));
		setFont(new Font("Arial", Font.PLAIN, 18));
		setResizable(false);
		setTitle("Comercializar | UCABIT");
		setBackground(Color.WHITE);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 447, 303);
		
		DecimalFormat formato = new DecimalFormat("#.00000000");
		
		interfaz = new JPanel();
		interfaz.setForeground(Color.BLACK);
		interfaz.setBackground(SystemColor.inactiveCaption);
		interfaz.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(interfaz);
		interfaz.setLayout(null);
		
		JLabel icon = new JLabel("");
		icon.setIcon(new ImageIcon("addons/bit-logo.png"));
		icon.setHorizontalAlignment(SwingConstants.CENTER);
		icon.setBounds(10, 29, 80, 80);
		interfaz.add(icon);
		
		JLabel dineroDisponibles = new JLabel("Dinero:");
		dineroDisponibles.setHorizontalAlignment(SwingConstants.RIGHT);
		dineroDisponibles.setFont(new Font("Arial", Font.BOLD, 13));
		dineroDisponibles.setBounds(274, 11, 54, 18);
		interfaz.add(dineroDisponibles);
		
		JLabel lblUsdt1 = new JLabel("");
		lblUsdt1.setHorizontalAlignment(SwingConstants.LEFT);
		lblUsdt1.setForeground(new Color(0, 128, 0));
		lblUsdt1.setFont(new Font("Arial", Font.BOLD, 13));
		lblUsdt1.setBounds(336, 11, 273, 18);
		interfaz.add(lblUsdt1);
		lblUsdt1.setText(String.valueOf(Principal.usuarioActual.getDinero()) + " " + "USDT");
		
		JLabel titulo = new JLabel("Comercializar Cripto Monedas");
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
		
		JLabel PrecioMoneda= new JLabel();
		PrecioMoneda.setForeground(Color.BLACK);
		PrecioMoneda.setHorizontalAlignment(SwingConstants.RIGHT);
		PrecioMoneda.setFont(new Font("Arial", Font.PLAIN, 14));
		PrecioMoneda.setBounds(199, 101, 102, 18);
		PrecioMoneda.setText(String.valueOf(formato.format(Principal.usuarioActual.monedas.get(PosicionUsuario).precio)));
		interfaz.add(PrecioMoneda);
		
		JLabel lblIngreseMonto = new JLabel("Monto:");
		lblIngreseMonto.setForeground(Color.BLACK);
		lblIngreseMonto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblIngreseMonto.setFont(new Font("Arial", Font.BOLD, 15));
		lblIngreseMonto.setBounds(174, 130, 80, 14);
		interfaz.add(lblIngreseMonto);
		
		JTextField lblIngreseMonto_1 = new JTextField();
		lblIngreseMonto_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblIngreseMonto_1.setFont(new Font("Arial", Font.BOLD, 15));
		lblIngreseMonto_1.setBounds(149, 157, 152, 23);
		interfaz.add(lblIngreseMonto_1);
		
		JLabel lblPrecio = new JLabel("Precio actual:");
		lblPrecio.setForeground(Color.BLACK);
		lblPrecio.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrecio.setFont(new Font("Arial", Font.BOLD, 15));
		lblPrecio.setBounds(86, 103, 113, 14);
		interfaz.add(lblPrecio);
		
		JLabel lblUsdt = new JLabel("USDT");
		lblUsdt.setForeground(Color.BLACK);
		lblUsdt.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsdt.setFont(new Font("Arial", Font.BOLD, 15));
		lblUsdt.setBounds(319, 103, 43, 14);
		interfaz.add(lblUsdt);
		
		JLabel lblUsdt_1 = new JLabel("USDT");
		lblUsdt_1.setForeground(Color.BLACK);
		lblUsdt_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsdt_1.setFont(new Font("Arial", Font.BOLD, 15));
		lblUsdt_1.setBounds(316, 162, 43, 14);
		interfaz.add(lblUsdt_1);
       if(ComprarVender==1) {
		JButton btnNewButton = new JButton("Comprar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if((lblIngreseMonto_1.getText().trim().equals("")) || (Integer.parseInt(lblIngreseMonto_1.getText())>Principal.usuarioActual.getDinero())) {
					JOptionPane.showMessageDialog(null, "Su Saldo es insuficiente o no ha colocado un monto valido");
				} else {
				double total = formatearDecimales(Integer.parseInt(lblIngreseMonto_1.getText())/(Principal.usuarioActual.monedas.get(PosicionUsuario).precio),8);
				JOptionPane.showMessageDialog(null,"COMPRA EXITOSA!"+ "\nHa adquirido un total de: " + formato.format(total) );
				Principal.usuarioActual.setDinero(Principal.usuarioActual.getDinero()-Integer.parseInt(lblIngreseMonto_1.getText()));
				Principal.usuarioActual.monedas.get(PosicionUsuario).setCantidad(Principal.usuarioActual.monedas.get(PosicionUsuario).getCantidad()+total);
				Principal.usuarioActual.monedas.get(PosicionUsuario).setGasto(Principal.usuarioActual.monedas.get(PosicionUsuario).getGasto()+Integer.parseInt(lblIngreseMonto_1.getText()));
				Principal.usuarioActual.monedas.get(PosicionUsuario).setCantidadInvertida(Principal.usuarioActual.monedas.get(PosicionUsuario).getCantidadInvertida()+total);
				lblUsdt1.setText(String.valueOf(Principal.usuarioActual.getDinero()) + " " + "USDT");
				}
			}
		});
		btnNewButton.setBackground(Color.GREEN);
		btnNewButton.setBounds(174, 220, 96, 23);
		interfaz.add(btnNewButton);
       } else {
    	   	JButton btnNewButton = new JButton("Vender");
   			btnNewButton.addActionListener(new ActionListener() {
   			public void actionPerformed(ActionEvent e) {
   				if((Integer.parseInt(lblIngreseMonto_1.getText())==0)||(lblIngreseMonto_1.getText().trim().equals("")) || (Integer.parseInt(lblIngreseMonto_1.getText())>((Principal.usuarioActual.monedas.get(PosicionUsuario).getCantidad()*Principal.usuarioActual.monedas.get(PosicionUsuario).precio)))) {
   					JOptionPane.showMessageDialog(null, "Su Saldo es insuficiente o no ha colocado un monto valido");
   				} else {
   				double total = formatearDecimales(Integer.parseInt(lblIngreseMonto_1.getText())/(Principal.usuarioActual.monedas.get(PosicionUsuario).precio),4);
   				JOptionPane.showMessageDialog(null,"VENTA EXITOSA!"+ "\nHa vendido un total de: " + formato.format(total) );
   				Principal.usuarioActual.setDinero(Principal.usuarioActual.getDinero()+Integer.parseInt(lblIngreseMonto_1.getText()));
   				Principal.usuarioActual.monedas.get(PosicionUsuario).setCantidad(Principal.usuarioActual.monedas.get(PosicionUsuario).getCantidad()-total);
   				lblUsdt1.setText(String.valueOf(Principal.usuarioActual.getDinero()) + " " + "USDT");
   				}
   			}
   		 });
   		btnNewButton.setBackground(Color.RED);
		btnNewButton.setBounds(174, 220, 96, 23);
		interfaz.add(btnNewButton);
       }
	}
/**
 * Este procedimiento se encarga de redondear un numero
 * @param numero nuemro a redondear
 * @param numeroDecimales cantidad de decimales que se quiere ver
 * @return
 */
	public static Double formatearDecimales(Double numero, Integer numeroDecimales) {
		return Math.round(numero * Math.pow(10, numeroDecimales)) / Math.pow(10, numeroDecimales);
		}
}
