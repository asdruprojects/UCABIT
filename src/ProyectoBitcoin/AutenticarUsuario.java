package ProyectoBitcoin;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

/**
 * Declaración del constructor de la interfaz gráfica
 * de la autenticación de usuarios
 * @author gabop
 *
 */

public class AutenticarUsuario extends JFrame {

	private JPanel interfaz;
	private JTextField campoUsuario;
	private JPasswordField campoPassword;
	
	/**
	 * Constructor de la interfaz (y sus componentes) de autenticación del 
	 * usuario. Contiene también los procedimientos necesario 
	 * para la autenticación del usuario
	 */
	public AutenticarUsuario() {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("addons/icon.jpg"));
		setFont(new Font("Arial", Font.PLAIN, 18));
		setResizable(false);
		setTitle("Autenticar un usuario | UCABIT");
		setBackground(Color.WHITE);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 389, 500);
		
		addWindowListener(new WindowAdapter(){
		    public void windowClosing(WindowEvent arg0) {
		    	Object [] opciones ={"Aceptar","Cancelar"};
				 int eleccion = JOptionPane.showOptionDialog(null,"¿Esta seguro que desea salir de la aplicacion?","UCABIT",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null,opciones,"Aceptar");
				 if (eleccion == JOptionPane.YES_OPTION){
					new Usuario().establecerFalso(Principal.usuarios);
					ArchivoJSON.EscribirData();
					System.exit(0);		 
				 }
		    }	                 
		});
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuOpciones = new JMenu("Menu de opciones");
		menuBar.add(menuOpciones);
		
		JMenu menuUsuario = new JMenu("Opciones del usuario");
		menuOpciones.add(menuUsuario);
		
		JMenuItem registroUsuario = new JMenuItem("Registrar un nuevo usuario");
		registroUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				RegistrarUsuario frame = new RegistrarUsuario();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
		menuUsuario.add(registroUsuario);
		interfaz = new JPanel();
		interfaz.setBackground(SystemColor.inactiveCaption);
		interfaz.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(interfaz);
		interfaz.setLayout(null);
		
		JButton boCerrar = new JButton("Cerrar");
		boCerrar.setFont(new Font("Arial", Font.PLAIN, 12));
		boCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new Usuario().establecerFalso(Principal.usuarios);
				System.exit(0);
			}
		});
		boCerrar.setBounds(271, 401, 89, 23);
		interfaz.add(boCerrar);
		
		JLabel icon = new JLabel("");
		icon.setIcon(new ImageIcon("addons/bit-logo.png"));
		icon.setHorizontalAlignment(SwingConstants.CENTER);
		icon.setBounds(10, 29, 80, 80);
		interfaz.add(icon);
		
		JLabel titulo = new JLabel("Autenticar Usuario");
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
		
		campoUsuario = new JTextField();
		campoUsuario.setFont(new Font("Arial", Font.PLAIN, 15));
		campoUsuario.setBounds(127, 162, 221, 20);
		interfaz.add(campoUsuario);
		campoUsuario.setColumns(10);
		
		JLabel correo = new JLabel("Correo:");
		correo.setFont(new Font("Arial", Font.BOLD, 15));
		correo.setHorizontalAlignment(SwingConstants.RIGHT);
		correo.setBounds(12, 165, 105, 14);
		interfaz.add(correo);
		
		JLabel password = new JLabel("Contrase\u00F1a");
		password.setHorizontalAlignment(SwingConstants.RIGHT);
		password.setFont(new Font("Arial", Font.BOLD, 15));
		password.setBounds(12, 207, 105, 14);
		interfaz.add(password);
		
		campoPassword = new JPasswordField();
		campoPassword.setFont(new Font("Arial", Font.PLAIN, 15));
		campoPassword.setBounds(127, 205, 221, 20);
		interfaz.add(campoPassword);
		
		JButton boIngresar = new JButton("Ingresar");
		boIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
		//Desarrollo del codigo para la autenticacion de un usuario cuando el evento del boton se accione	
				
				if((campoUsuario.getText().trim().equals("")) || ((String.valueOf(campoPassword.getPassword()).equals(""))) ) {
					JOptionPane.showMessageDialog(null, "Debe llenar ambos campos");
				}else {	
					if( new Usuario().validarUsuario(Principal.usuarios,campoUsuario.getText(),(String.valueOf(campoPassword.getPassword()))) ) {
						
						new Usuario().reemplazarIP(Principal.usuarios);	
						new Usuario().reemplazarHoraFecha(Principal.usuarios);
						
						sesionActual.setText(Principal.usuarioActual.getCorreo());
						JOptionPane.showMessageDialog(null, "Usuario autenticado con exito" + "\nSesion iniciada");		
						
						setVisible(false);
						MostrarMonitorBitcoin frame = new MostrarMonitorBitcoin();
						frame.setLocationRelativeTo(null);
						frame.setVisible(true);
						
						Principal.usuarioActual.cambioDeUsuario(Principal.usuarios);
						ArchivoJSON .EscribirData();
						
					}else {
						JOptionPane.showMessageDialog(null, "El usuario o la contraseña no son validos");
					}
				}
			}
		});
		//----
		boIngresar.setFont(new Font("Arial", Font.PLAIN, 12));
		boIngresar.setBounds(146, 260, 89, 23);
		interfaz.add(boIngresar);
	}
}