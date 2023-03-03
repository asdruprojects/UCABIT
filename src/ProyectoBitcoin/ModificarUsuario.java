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
import javax.swing.JPasswordField;

/**
 * Esta clase contiene el constructor de la interfaz gráfica
 * de la modificación de contraseña del usuario
 * @author gabop
 *
 */

public class ModificarUsuario extends JFrame {

	private JPanel interfaz;
	private JPasswordField campoPasswordVieja;
	private JPasswordField campoPasswordNueva;
	private JPasswordField campoPasswordConf;
	/**
	  Declaracion del constructor de la interfaz (y sus componentes) de 
	  modificar usuario, además, tiene los procedimientos que se necesitan
	  para modificar la contraseña del usuario.
	**/
	public ModificarUsuario() {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("addons/icon.jpg"));
		setFont(new Font("Arial", Font.PLAIN, 18));
		setResizable(false);
		setTitle("Modificar los datos de acceso | UCABIT");
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 389, 500);
		
		this.addWindowListener(new WindowAdapter(){
		    @Override
		    public void windowClosing(WindowEvent arg0) {
		    	Object [] opciones ={"Aceptar","Cancelar"};
				 int eleccion = JOptionPane.showOptionDialog(null,"Desea salir de la aplicacion","ADVERTENCIA",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null,opciones,"Aceptar");
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
		
		JMenuItem portafolio = new JMenuItem("Mostrar Portafolio");
		portafolio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				Portafolio frame = new Portafolio();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
		menuUsuario.add(portafolio);
		
		JMenuItem calculadora = new JMenuItem("Calculadora de conversi\u00F3n");
        calculadora.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Calculadora frame = new Calculadora();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        menuUsuario.add(calculadora);
		
		JMenuItem monitorBC = new JMenuItem("Mostrar el monitor Bitcoin");
		monitorBC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				MostrarMonitorBitcoin frame = new MostrarMonitorBitcoin();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
		menuOpciones.add(monitorBC);
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
		
		JLabel titulo = new JLabel("Modificar los datos de acceso");
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
		
		JLabel newPassword = new JLabel("Contrase\u00F1a nueva");
		newPassword.setFont(new Font("Arial", Font.BOLD, 13));
		newPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		newPassword.setBounds(24, 167, 142, 14);
		interfaz.add(newPassword);
		
		JLabel password = new JLabel("Contrase\u00F1a vieja");
		password.setHorizontalAlignment(SwingConstants.RIGHT);
		password.setFont(new Font("Arial", Font.BOLD, 13));
		password.setBounds(36, 247, 130, 14);
		interfaz.add(password);
		
		campoPasswordVieja = new JPasswordField();
		campoPasswordVieja.setFont(new Font("Dialog", Font.PLAIN, 13));
		campoPasswordVieja.setBounds(174, 243, 180, 20);
		interfaz.add(campoPasswordVieja);
		
		campoPasswordNueva = new JPasswordField();
		campoPasswordNueva.setFont(new Font("Arial", Font.PLAIN, 13));
		campoPasswordNueva.setBounds(176, 163, 178, 20);
		interfaz.add(campoPasswordNueva);
		
		JButton boModificar = new JButton("Modificar");
		boModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
		//Desarrollo del codigo para modificar los datos de acceso de un usuario cuando el evento del boton se accione			
				
				if( (String.copyValueOf(campoPasswordNueva.getPassword()).equals("")) || ((String.copyValueOf(campoPasswordVieja.getPassword()).equals(""))) || ((String.copyValueOf(campoPasswordConf.getPassword()).equals(""))) ){
					JOptionPane.showMessageDialog(null, "Debe llenar ambos campos");
				}else {
					Usuario usuario = new Usuario();
					if (usuario.validarPassword((String.valueOf(campoPasswordNueva.getPassword())))) {
						if ( (String.valueOf(campoPasswordNueva.getPassword()).equals((String.valueOf(campoPasswordConf.getPassword())))) ) {
							if(usuario.modificarPassword(Principal.usuarios,Principal.usuarioActual.getCorreo(),(String.valueOf(campoPasswordVieja.getPassword())),(String.valueOf(campoPasswordNueva.getPassword())))) {
								JOptionPane.showMessageDialog(null, "Datos de acceso modificados con exito");
							}else {
								JOptionPane.showMessageDialog(null, "Su contraseña vieja no coincide con los datos guardados");
							}
						}else {
							JOptionPane.showMessageDialog(null, "Los campos de las contraseñas no coinciden");
						}
					}else {
						JOptionPane.showMessageDialog(null, "Contraseña nueva no valida: "
								+ "\n-Debe ser mayor a ocho caracteres" 
								+ "\n-Debe incluir al menos un número, una letra mayúscula, una letra minuscula y un carácter especial");
					}
				}
			}
		});
		//----
		boModificar.setFont(new Font("Arial", Font.PLAIN, 12));
		boModificar.setBounds(152, 299, 89, 23);
		interfaz.add(boModificar);
		
		JButton boCerrarSesion = new JButton("Cerrar Sesion");
		boCerrarSesion.setFont(new Font("Arial", Font.PLAIN, 12));
		boCerrarSesion.setBounds(24, 401, 118, 23);
		interfaz.add(boCerrarSesion);
		
		boCerrarSesion.setVisible(false);
		if (Principal.usuarioActual.isAutenticacion()) {
			boCerrarSesion.setVisible(true);
			boCerrarSesion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Principal.usuarioActual = new Usuario();
					new Usuario().establecerFalso(Principal.usuarios);
					setVisible(false);
					AutenticarUsuario frame = new AutenticarUsuario();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				}
			});
		}
		
		campoPasswordConf = new JPasswordField();
		campoPasswordConf.setFont(new Font("Dialog", Font.PLAIN, 13));
		campoPasswordConf.setBounds(174, 204, 180, 20);
		interfaz.add(campoPasswordConf);
		
		JLabel passwordConf = new JLabel("Confirmar Contrase\u00F1a");
		passwordConf.setHorizontalAlignment(SwingConstants.RIGHT);
		passwordConf.setFont(new Font("Arial", Font.BOLD, 13));
		passwordConf.setBounds(10, 207, 154, 14);
		interfaz.add(passwordConf);
	}
}