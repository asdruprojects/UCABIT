package ProyectoBitcoin;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Esta clase contiene el constructor de la interfaz gráfica del 
 * monitor, así como la función de llenado de la tabla de monedas
 * @author gabop
 *
 */

public class MostrarMonitorBitcoin extends JFrame {

	private JPanel interfaz;
	private JTable tablaMonedas;
	private	String contenidoTabla[][] = new String[30][9];
	
	/**
	 * Declaración del constructor de la interfaz (y sus componentes) del 
	 * monitor de criptomonedas, además,llama a la funcion que genera la 
	 * tabla conlos datos
	 */
	public MostrarMonitorBitcoin() {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("addons/icon.jpg"));
		setFont(new Font("Arial", Font.PLAIN, 18));
		setResizable(false);
		setTitle("Monitor Bitcoin | UCABIT");
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1283, 506);
		
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
		autenticarUsuario.setVisible(false);
		if (!(Principal.usuarioActual.isAutenticacion())) {
			autenticarUsuario.setVisible(true);
			autenticarUsuario.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					AutenticarUsuario frame = new AutenticarUsuario();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				}
			});
		}
		menuUsuario.add(autenticarUsuario);
		
		JMenuItem modificarUsuario = new JMenuItem("Modificar los datos de acceso");
		modificarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Principal.usuarioActual.isAutenticacion()) {
					setVisible(false);
					ModificarUsuario frame = new ModificarUsuario();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				}else {
					JOptionPane.showMessageDialog(null, "Opcion solo disponible a usuarios autenticados");
				}
			}
		});
		menuUsuario.add(modificarUsuario);
		
		JMenuItem portafolio = new JMenuItem("Mostrar Portafolio");
		portafolio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Principal.usuarioActual.isAutenticacion()) {
					setVisible(false);
					Portafolio frame = new Portafolio();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				}else {
					JOptionPane.showMessageDialog(null, "Opcion solo disponible a usuarios autenticados");
				}
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
        
        JMenuItem inversion = new JMenuItem("Estadisticas de Inversion");
        inversion.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		EstadísticasUsuario frame = new EstadísticasUsuario();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        menuUsuario.add(inversion);
		
		JMenuItem monitorBC = new JMenuItem("Actualizar Monitor Bitcoin");
		monitorBC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				MostrarMonitorBitcoin frame = new MostrarMonitorBitcoin();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				JOptionPane.showMessageDialog(null, "Monitor Actualizado");
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
		boCerrar.setBounds(1178, 410, 89, 23);
		interfaz.add(boCerrar);
		
		JLabel icon = new JLabel("");
		icon.setIcon(new ImageIcon("addons/bit-logo.png"));
		icon.setHorizontalAlignment(SwingConstants.CENTER);
		icon.setBounds(10, 29, 80, 80);
		interfaz.add(icon);
		
		JLabel titulo = new JLabel("Monitor de las Top 30 Cripto Monedas");
		titulo.setForeground(Color.BLACK);
		titulo.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 18));
		titulo.setHorizontalAlignment(SwingConstants.LEFT);
		titulo.setBounds(86, 56, 378, 23);
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
		
		JButton boCerrarSesion = new JButton("Cerrar Sesion");
		boCerrarSesion.setFont(new Font("Arial", Font.PLAIN, 12));
		boCerrarSesion.setBounds(25, 410, 118, 23);
		interfaz.add(boCerrarSesion);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 116, 1215, 266);
		interfaz.add(scrollPane);
		
		tablaMonedas = new JTable();
		tablaMonedas.setBackground(Color.WHITE);
		tablaMonedas.setFont(new Font("Arial", Font.PLAIN, 12));
		scrollPane.setViewportView(tablaMonedas);

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
		actualizarMonitor();
	}

		/**
		 * Método que se encarga de actualizar los datos de las 
		 * criptomonedas en la matriz usada para la graficación
		 * de la tabla. La información se solicita a la API
		 */
		public void actualizarMonitor() {
			DecimalFormat formato = new DecimalFormat("$#.000");
			DecimalFormat formato1 = new DecimalFormat("%#.000");
			DecimalFormat formato2 = new DecimalFormat("000000.0");
			for ( int i=0 ; i < 30 ; i++ ) {
					contenidoTabla[i][0]= Principal.cryptoMonedas[i].ticked;
					contenidoTabla[i][1]= Principal.cryptoMonedas[i].moneda;
					contenidoTabla[i][2]= String.valueOf(formato.format(Principal.cryptoMonedas[i].precioActual));
					contenidoTabla[i][3]= String.valueOf(formato.format(Principal.cryptoMonedas[i].marketCap));
					contenidoTabla[i][4]= String.valueOf(formato.format(Principal.cryptoMonedas[i].volumenUltima24Horas));
					contenidoTabla[i][5]= String.valueOf(Principal.cryptoMonedas[i].cantidadMonedasCirculantes) + " " +Principal.cryptoMonedas[i].ticked;
					contenidoTabla[i][6]= String.valueOf(formato1.format(Principal.cryptoMonedas[i].margenPerdidaGanancia7dias));
					contenidoTabla[i][7]= String.valueOf(formato1.format(Principal.cryptoMonedas[i].margenPerdidaGanancia24Horas));
					contenidoTabla[i][8]= String.valueOf(formato1.format(Principal.cryptoMonedas[i].margenPerdidaGanancia1hora));
					}
			tablaMonedas.setModel(new DefaultTableModel(
					contenidoTabla,
				new String[] {
					"Ticked", " Moneda", "Precio actual", "Capitalizaci\u00F3n de mercado", "Volumen (24 Horas)" , "Monedas Circulantes", "Porcentaje de P/G (7 dias)","Porcentaje de P/G (24 Horas)","Porcentaje de P/G (Ultima Hora)"
				}
			) {
				boolean[] columnEditables = new boolean[] {
					false, false, false, false, false, false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
			tablaMonedas.getColumnModel().getColumn(0).setResizable(false);
			tablaMonedas.getColumnModel().getColumn(0).setPreferredWidth(44);
			tablaMonedas.getColumnModel().getColumn(1).setResizable(false);
			tablaMonedas.getColumnModel().getColumn(1).setPreferredWidth(98);
			tablaMonedas.getColumnModel().getColumn(2).setResizable(false);
			tablaMonedas.getColumnModel().getColumn(3).setResizable(false);
			tablaMonedas.getColumnModel().getColumn(3).setPreferredWidth(136);
			tablaMonedas.getColumnModel().getColumn(4).setResizable(false);
			tablaMonedas.getColumnModel().getColumn(4).setPreferredWidth(106);
			tablaMonedas.getColumnModel().getColumn(5).setResizable(false);
			tablaMonedas.getColumnModel().getColumn(5).setPreferredWidth(130);
			tablaMonedas.getColumnModel().getColumn(6).setResizable(false);
			tablaMonedas.getColumnModel().getColumn(6).setPreferredWidth(150);
			tablaMonedas.getColumnModel().getColumn(7).setResizable(false);
			tablaMonedas.getColumnModel().getColumn(7).setPreferredWidth(157);
			tablaMonedas.getColumnModel().getColumn(8).setResizable(false);
			tablaMonedas.getColumnModel().getColumn(8).setPreferredWidth(166);
		}
}