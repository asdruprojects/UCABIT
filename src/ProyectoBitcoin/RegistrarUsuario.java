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
 * Esta clase contiene el constructor de la interfaz gráfica
 * del registro de usuarios
 * @author gabop
 *
 */

public class RegistrarUsuario extends JFrame {

	private JPanel interfaz;
	private JTextField campoUsuario;
	private JPasswordField campoPassword;
	private JPasswordField campoPasswordConf;

	/**
	 *Constructor de la interfaz (y sus componentes) del registro
	 * de usuarios. Contiene también los procedimientos necesarios 
	 * para el registro de usuarios 
	 */
	public RegistrarUsuario() {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("addons/icon.jpg"));
		setFont(new Font("Arial", Font.PLAIN, 18));
		setResizable(false);
		setTitle("Registrar un nuevo usuario");
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
		
		JMenuItem autenticarUsuario = new JMenuItem("Autenticar un usuario");
		autenticarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				AutenticarUsuario frame = new AutenticarUsuario();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
		menuUsuario.add(autenticarUsuario);
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
		
		JLabel titulo = new JLabel("Bienvenidos al monitor Bitcoin ");
		titulo.setForeground(Color.BLACK);
		titulo.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 18));
		titulo.setHorizontalAlignment(SwingConstants.CENTER);
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
		campoUsuario.setFont(new Font("Arial", Font.PLAIN, 13));
		campoUsuario.setBounds(156, 152, 204, 20);
		interfaz.add(campoUsuario);
		campoUsuario.setColumns(10);
		
		JLabel correo = new JLabel("Correo:");
		correo.setHorizontalAlignment(SwingConstants.RIGHT);
		correo.setFont(new Font("Arial", Font.BOLD, 13));
		correo.setBounds(84, 156, 62, 14);
		interfaz.add(correo);
		
		JLabel password = new JLabel("Contrase\u00F1a:");
		password.setHorizontalAlignment(SwingConstants.RIGHT);
		password.setFont(new Font("Arial", Font.BOLD, 13));
		password.setBounds(47, 205, 99, 14);
		interfaz.add(password);
		
		campoPassword = new JPasswordField();
		campoPassword.setFont(new Font("Arial", Font.PLAIN, 13));
		campoPassword.setBounds(156, 201, 204, 20);
		interfaz.add(campoPassword);
		
		JButton boRegistrarse = new JButton("Registrarse");
		boRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				
				if((campoUsuario.getText().trim().equals("")) || ((String.valueOf(campoPassword.getPassword()).equals(""))) || ((String.valueOf(campoPasswordConf.getPassword()).equals(""))) ) {
					JOptionPane.showMessageDialog(null, "Debe llenar ambos campos");
				}else {
					Usuario usuario = new Usuario();
					if ( usuario.validarCorreo(campoUsuario.getText()) ) {
						if (usuario.validarPassword((String.valueOf(campoPassword.getPassword())))) {
							if(usuario.validarExistencia(Principal.usuarios,campoUsuario.getText())) {
								if( (String.valueOf(campoPassword.getPassword()).equals((String.valueOf(campoPasswordConf.getPassword())))) ) {
									usuario = new Usuario(campoUsuario.getText(),(String.valueOf(campoPassword.getPassword())),false," "," "," ",500);
									Principal.usuarioActual = usuario;
									Principal.usuarios.add(usuario);
									JOptionPane.showMessageDialog(null, "Usuario registrado con exito");
									
									setVisible(false);
									MostrarMonitorBitcoin frame = new MostrarMonitorBitcoin();
									frame.setLocationRelativeTo(null);
									frame.setVisible(true);
									
									ArchivoJSON.EscribirData();
								}else {
									JOptionPane.showMessageDialog(null, "Los campos de las contraseñas no coinciden");
								}
								
							}else {
								JOptionPane.showMessageDialog(null, "El correo ingresado esta asociado a otra cuenta");
							}
						}else {
							JOptionPane.showMessageDialog(null, "Contraseña no valida: "
																+ "\n-Debe ser mayor a ocho caracteres" 
																+ "\n-Debe incluir al menos un número, una letra mayúscula, una letra minuscula y un carácter especial");
						}
					}else {
						JOptionPane.showMessageDialog(null, "Correo no valido:" 
															+ "\n-Recuerde que debe ser sintácticamente valido");
					}
				}
			}
		});

		boRegistrarse.setFont(new Font("Arial", Font.PLAIN, 12));
		boRegistrarse.setBounds(138, 311, 99, 23);
		interfaz.add(boRegistrarse);
		
		campoPasswordConf = new JPasswordField();
		campoPasswordConf.setFont(new Font("Arial", Font.PLAIN, 13));
		campoPasswordConf.setBounds(156, 253, 204, 20);
		interfaz.add(campoPasswordConf);
		
		JLabel passwordConf = new JLabel("Confirmar contrase\u00F1a:");
		passwordConf.setHorizontalAlignment(SwingConstants.RIGHT);
		passwordConf.setFont(new Font("Arial", Font.BOLD, 13));
		passwordConf.setBounds(0, 257, 146, 14);
		interfaz.add(passwordConf);
	}
}