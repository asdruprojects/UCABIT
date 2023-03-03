package ProyectoBitcoin;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;
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
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.GridLayout;

/**Esta clase contiene el constructor de la interfaz gráfica
 * del portafolio del usuario
 * @author AaronP
 *
 */
public class Portafolio extends JFrame {

	private JPanel interfaz;
	private JTable tablaCriptosUsuario;
	private String contenidoCriptosUsuario[][] = new String[10][2];
	JComboBox<String> comboCriptos;
	Timer timer = new Timer();
/**
 * Declaracion del constructor de la interfaz (y sus componentes)para el 
 * portafolio y la compra, venta y reset de Cripto monedas
 */
	public Portafolio() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("addons/icon.jpg"));
		setFont(new Font("Arial", Font.PLAIN, 18));
		setResizable(false);
		setTitle("Portafolio | UCABIT");
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 613, 500);
		
		this.addWindowListener(new WindowAdapter(){
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
		menuUsuario.add(portafolio);
		
		JMenuItem monitorBC = new JMenuItem("Mostrar el monitor Bitcoin");
		monitorBC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				MostrarMonitorBitcoin frame = new MostrarMonitorBitcoin();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
		
		JMenuItem inversion = new JMenuItem("Estadisticas de Inversion");
        inversion.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		EstadísticasUsuario frame = new EstadísticasUsuario();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        menuUsuario.add(inversion);
		
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
		boCerrar.setBounds(473, 401, 89, 23);
		interfaz.add(boCerrar);
		
		JLabel icon = new JLabel("");
		icon.setIcon(new ImageIcon("addons/bit-logo.png"));
		icon.setHorizontalAlignment(SwingConstants.CENTER);
		icon.setBounds(10, 29, 80, 80);
		interfaz.add(icon);
		
		JLabel titulo = new JLabel("Portafolio personal");
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
			
		JButton boCerrarSesion = new JButton("Cerrar Sesion");
		boCerrarSesion.setFont(new Font("Arial", Font.PLAIN, 12));
		boCerrarSesion.setBounds(24, 401, 118, 23);
		interfaz.add(boCerrarSesion);
		
		inicializartabla();
		
		JButton boAgregarCripto = new JButton("Agregar Cripto");
		boAgregarCripto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!comboCriptos.getSelectedItem().equals("")) {
					for ( int i=0 ; i < Principal.usuarios.size() ; i++) {
						if (Principal.usuarioActual.getCorreo().equals(Principal.usuarios.get(i).getCorreo())) {
							if((Principal.usuarios.get(i).monedas.size() != 10) && (new MonedasDeUsuario().verificarMoneda(Principal.usuarios.get(i).monedas, String.valueOf(comboCriptos.getSelectedItem())))) {
								Principal.usuarios.get(i).monedas.add(new MonedasDeUsuario(String.valueOf(comboCriptos.getSelectedItem()),0,0,0));
								llenarTabla();
								JOptionPane.showMessageDialog(null, "Se agrego la cripto moneda con exito");
							}else {
								JOptionPane.showMessageDialog(null, "No se pudo agregar esta moneda, por favor revise si: \n-Su portafolio tiene espacio para mas monedas\n-La moneda que intenta ingresar ya existe en su portafolio");
							}
						}
					}	
				}else {
					JOptionPane.showMessageDialog(null, "Debe selecionar una Cripto moneda");
				}
			}
		});
		boAgregarCripto.setFont(new Font("Arial", Font.PLAIN, 12));
		boAgregarCripto.setBounds(10, 120, 118, 23);
		interfaz.add(boAgregarCripto);
		
		comboCriptos = new JComboBox<String>();
		comboCriptos.setToolTipText("Selecione una moneda para agregarla o eliminarla del portafolio");
		comboCriptos.setFont(new Font("Arial", Font.PLAIN, 12));
		comboCriptos.setBounds(266, 120, 107, 22);
		interfaz.add(comboCriptos);
		
		tablaCriptosUsuario = new JTable();
		tablaCriptosUsuario.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 154, 366, 184);
		interfaz.add(scrollPane);
		scrollPane.setViewportView(tablaCriptosUsuario);
		
	    JLabel dineroDisponibles = new JLabel("Dinero:");
		dineroDisponibles.setHorizontalAlignment(SwingConstants.RIGHT);
		dineroDisponibles.setFont(new Font("Arial", Font.BOLD, 13));
		dineroDisponibles.setBounds(350, 11, 54, 18);
		interfaz.add(dineroDisponibles);
		
		JLabel lblUsdt = new JLabel("");
		lblUsdt.setHorizontalAlignment(SwingConstants.LEFT);
		lblUsdt.setForeground(new Color(0, 128, 0));
		lblUsdt.setFont(new Font("Arial", Font.BOLD, 13));
		lblUsdt.setBounds(412, 11, 273, 18);
		interfaz.add(lblUsdt);
		lblUsdt.setText(String.valueOf(Principal.usuarioActual.getDinero()) + " " + "USDT");
		
		JButton btnNewButton = new JButton("RESETEAR");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ResetearSaldo = JOptionPane.showInputDialog("Cual es el monto para resetear el saldo disponible?");
				int Saldo = Integer.parseInt(ResetearSaldo.trim());
				Principal.usuarioActual.setDinero(Saldo);
				lblUsdt.setText(String.valueOf(Principal.usuarioActual.getDinero()));
				for ( int f=0 ; f < Principal.usuarioActual.monedas.size() ; f++ ) {
					try {
						Principal.usuarioActual.monedas.get(f).setCantidad(0);
					}catch(Exception arg) {
					}
				}
				llenarTabla();
			}
		});
		btnNewButton.setBackground(Color.RED);
		btnNewButton.setBounds(462, 86, 130, 23);
		interfaz.add(btnNewButton);
		
		JPanel panelBoCompra = new JPanel();
		panelBoCompra.setBackground(SystemColor.inactiveCaption);
		panelBoCompra.setBounds(386, 173, 107, 168);
		interfaz.add(panelBoCompra);
		panelBoCompra.setLayout(new GridLayout(10, 1, 0, 0));
		
		JButton btnNewButton_1 = new JButton("Comprar");
		panelBoCompra.add(btnNewButton_1);
		
		JButton btnNewButton_1_1 = new JButton("Comprar");
		panelBoCompra.add(btnNewButton_1_1);
		
		JButton btnNewButton_1_4 = new JButton("Comprar");
		panelBoCompra.add(btnNewButton_1_4);
		
		JButton btnNewButton_1_6 = new JButton("Comprar");
		panelBoCompra.add(btnNewButton_1_6);
		
		JButton btnNewButton_1_8 = new JButton("Comprar");
		panelBoCompra.add(btnNewButton_1_8);
		
		JButton btnNewButton_1_10 = new JButton("Comprar");
		panelBoCompra.add(btnNewButton_1_10);
		
		JButton btnNewButton_1_12 = new JButton("Comprar");
		panelBoCompra.add(btnNewButton_1_12);
		
		JButton btnNewButton_1_14 = new JButton("Comprar");
		panelBoCompra.add(btnNewButton_1_14);
		
		JButton btnNewButton_1_16 = new JButton("Comprar");
		panelBoCompra.add(btnNewButton_1_16);
		
		JButton btnNewButton_1_18 = new JButton("Comprar");
		panelBoCompra.add(btnNewButton_1_18);
		
		JPanel panelBoVenta = new JPanel();
		panelBoVenta.setBackground(SystemColor.inactiveCaption);
		panelBoVenta.setBounds(503, 173, 89, 168);
		interfaz.add(panelBoVenta);
		panelBoVenta.setLayout(new GridLayout(10, 1, 0, 0));
		
		JButton btnNewButton_1_2 = new JButton("Vender");
		panelBoVenta.add(btnNewButton_1_2);
		btnNewButton_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(contenidoCriptosUsuario[0][0]!="") {
				Comercializar frame = new Comercializar(0,posicionUSUARIO(contenidoCriptosUsuario[0][0]));
				frame.setVisible(true);
				} else {
				  JOptionPane.showMessageDialog(null, "No ha agregado la criptomoneda al portafolio");
				}
			}
		});
		btnNewButton_1_2.setForeground(Color.RED);
		
		JButton btnNewButton_1_2_1 = new JButton("Vender");
		panelBoVenta.add(btnNewButton_1_2_1);
		btnNewButton_1_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(contenidoCriptosUsuario[1][0]!="") {
				Comercializar frame = new Comercializar(0,posicionUSUARIO(contenidoCriptosUsuario[1][0]));
				frame.setVisible(true);
				} else {
					  JOptionPane.showMessageDialog(null, "No ha agregado la criptomoneda al portafolio");
				}
			}
		});
		btnNewButton_1_2_1.setForeground(Color.RED);
		
		JButton btnNewButton_1_2_2 = new JButton("Vender");
		panelBoVenta.add(btnNewButton_1_2_2);
		btnNewButton_1_2_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(contenidoCriptosUsuario[2][0]!="") {
				Comercializar frame = new Comercializar(0,posicionUSUARIO(contenidoCriptosUsuario[2][0]));
				frame.setVisible(true);
				} else {
					  JOptionPane.showMessageDialog(null, "No ha agregado la criptomoneda al portafolio");
				}
			}
		});
		btnNewButton_1_2_2.setForeground(Color.RED);
		
		JButton btnNewButton_1_2_3 = new JButton("Vender");
		panelBoVenta.add(btnNewButton_1_2_3);
		btnNewButton_1_2_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(contenidoCriptosUsuario[3][0]!="") {
				Comercializar frame = new Comercializar(0,posicionUSUARIO(contenidoCriptosUsuario[3][0]));
				frame.setVisible(true);
				} else {
					  JOptionPane.showMessageDialog(null, "No ha agregado la criptomoneda al portafolio");
				}
			}
		});
		btnNewButton_1_2_3.setForeground(Color.RED);
		
		JButton btnNewButton_1_2_4 = new JButton("Vender");
		panelBoVenta.add(btnNewButton_1_2_4);
		btnNewButton_1_2_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(contenidoCriptosUsuario[4][0]!="") {
				Comercializar frame = new Comercializar(0,posicionUSUARIO(contenidoCriptosUsuario[4][0]));
				frame.setVisible(true);
				} else {
					  JOptionPane.showMessageDialog(null, "No ha agregado la criptomoneda al portafolio");
				}
			}
		});
		btnNewButton_1_2_4.setForeground(Color.RED);
		
		JButton btnNewButton_1_2_5 = new JButton("Vender");
		panelBoVenta.add(btnNewButton_1_2_5);
		btnNewButton_1_2_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(contenidoCriptosUsuario[5][0]!="") {
				Comercializar frame = new Comercializar(0,posicionUSUARIO(contenidoCriptosUsuario[5][0]));
				frame.setVisible(true);
				} else {
					  JOptionPane.showMessageDialog(null, "No ha agregado la criptomoneda al portafolio");
				}
			}
		});
		btnNewButton_1_2_5.setForeground(Color.RED);
		
		JButton btnNewButton_1_2_6 = new JButton("Vender");
		panelBoVenta.add(btnNewButton_1_2_6);
		btnNewButton_1_2_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(contenidoCriptosUsuario[6][0]!="" ) {
				Comercializar frame = new Comercializar(0,posicionUSUARIO(contenidoCriptosUsuario[6][0]));
				frame.setVisible(true);
				} else {
					  JOptionPane.showMessageDialog(null, "No ha agregado la criptomoneda al portafolio");
				}
			}
		});
		btnNewButton_1_2_6.setForeground(Color.RED);
		
		JButton btnNewButton_1_2_7 = new JButton("Vender");
		panelBoVenta.add(btnNewButton_1_2_7);
		btnNewButton_1_2_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(contenidoCriptosUsuario[7][0]!="") {
				Comercializar frame = new Comercializar(0,posicionUSUARIO(contenidoCriptosUsuario[7][0]));
				frame.setVisible(true);
				} else {
					  JOptionPane.showMessageDialog(null, "No ha agregado la criptomoneda al portafolio");
				}
			}
		});
		btnNewButton_1_2_7.setForeground(Color.RED);
		
		JButton btnNewButton_1_2_8 = new JButton("Vender");
		panelBoVenta.add(btnNewButton_1_2_8);
		btnNewButton_1_2_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(contenidoCriptosUsuario[8][0]!="") {
				Comercializar frame = new Comercializar(0,posicionUSUARIO(contenidoCriptosUsuario[8][0]));
				frame.setVisible(true);
				} else {
					  JOptionPane.showMessageDialog(null, "No ha agregado la criptomoneda al portafolio");
				}
			}
		});
		btnNewButton_1_2_8.setForeground(Color.RED);
		
		JButton btnNewButton_1_2_9 = new JButton("Vender");
		panelBoVenta.add(btnNewButton_1_2_9);
		btnNewButton_1_2_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(contenidoCriptosUsuario[9][0]!="") {
				Comercializar frame = new Comercializar(0,posicionUSUARIO(contenidoCriptosUsuario[9][0]));
				frame.setVisible(true);
				} else {
					  JOptionPane.showMessageDialog(null, "No ha agregado la criptomoneda al portafolio");
				}
			}
		});
		btnNewButton_1_2_9.setForeground(Color.RED);
		
		JButton boEliminarCripto = new JButton("Eliminar Cripto");
		boEliminarCripto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!comboCriptos.getSelectedItem().equals("")) {
					for ( int i=0 ; i < Principal.usuarios.size() ; i++) {
						if (Principal.usuarioActual.getCorreo().equals(Principal.usuarios.get(i).getCorreo())) {
							for( int j=0 ; j < Principal.usuarios.get(i).monedas.size() ; j++ ) {
								if( comboCriptos.getSelectedItem().equals(Principal.usuarios.get(i).monedas.get(j).getNombre()) ) {
									for( int g=0 ; g < 30 ; g++ ) {
										if(Principal.cryptoMonedas[g].moneda.equals(comboCriptos.getSelectedItem())) {
											Principal.usuarios.get(i).setDinero(Principal.usuarios.get(i).getDinero() + Principal.usuarios.get(i).monedas.get(j).getCantidad()*Principal.cryptoMonedas[g].precioActual);
											Principal.usuarios.get(i).monedas.remove(j);
											llenarTabla();
											JOptionPane.showMessageDialog(null, "Se elimino la cripto moneda con exito");
										}
									}
								}
							}
						}
					}	
				}else {
					JOptionPane.showMessageDialog(null, "Debe selecionar una Cripto moneda");
				}
			}
		});
		boEliminarCripto.setFont(new Font("Arial", Font.PLAIN, 12));
		boEliminarCripto.setBounds(138, 120, 118, 23);
		interfaz.add(boEliminarCripto);
		btnNewButton_1_18.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(contenidoCriptosUsuario[9][0]!="") {
				Comercializar frame = new Comercializar(1,posicionUSUARIO(contenidoCriptosUsuario[9][0]));
				frame.setVisible(true);
				} else {
					  JOptionPane.showMessageDialog(null, "No ha agregado la criptomoneda al portafolio");
				}
			}
		});
		btnNewButton_1_16.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(contenidoCriptosUsuario[8][0]!="") {
				Comercializar frame = new Comercializar(1,posicionUSUARIO(contenidoCriptosUsuario[8][0]));
				frame.setVisible(true);
				} else {
					  JOptionPane.showMessageDialog(null, "No ha agregado la criptomoneda al portafolio");
				}
			}
		});
		btnNewButton_1_14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(contenidoCriptosUsuario[7][0]!="") {
				Comercializar frame = new Comercializar(1,posicionUSUARIO(contenidoCriptosUsuario[7][0]));
				frame.setVisible(true);
				} else {
					  JOptionPane.showMessageDialog(null, "No ha agregado la criptomoneda al portafolio");
				}
			}
		});
		btnNewButton_1_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(contenidoCriptosUsuario[6][0]!="") {
				Comercializar frame = new Comercializar(1,posicionUSUARIO(contenidoCriptosUsuario[6][0]));
				frame.setVisible(true);
				} else {
					  JOptionPane.showMessageDialog(null, "No ha agregado la criptomoneda al portafolio");
				}
			}
		});
		btnNewButton_1_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(contenidoCriptosUsuario[5][0]!="") {
				Comercializar frame = new Comercializar(1,posicionUSUARIO(contenidoCriptosUsuario[5][0]));
				frame.setVisible(true);
				} else {
					  JOptionPane.showMessageDialog(null, "No ha agregado la criptomoneda al portafolio");
				}
			}
		});
		btnNewButton_1_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(contenidoCriptosUsuario[4][0]!="") {
				Comercializar frame = new Comercializar(1,posicionUSUARIO(contenidoCriptosUsuario[4][0]));
				frame.setVisible(true);
				} else {
					  JOptionPane.showMessageDialog(null, "No ha agregado la criptomoneda al portafolio");
				}
			}
		});
		btnNewButton_1_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(contenidoCriptosUsuario[3][0]!="") {
				Comercializar frame = new Comercializar(1,posicionUSUARIO(contenidoCriptosUsuario[3][0]));
				frame.setVisible(true);
				} else {
					  JOptionPane.showMessageDialog(null, "No ha agregado la criptomoneda al portafolio");
				}
			}
		});
		btnNewButton_1_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(contenidoCriptosUsuario[2][0]!="") {
				Comercializar frame = new Comercializar(1,posicionUSUARIO(contenidoCriptosUsuario[2][0]));
				frame.setVisible(true);
				} else {
					  JOptionPane.showMessageDialog(null, "No ha agregado la criptomoneda al portafolio");
				}
			}
		});
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(contenidoCriptosUsuario[1][0]!="") {
				Comercializar frame = new Comercializar(1,posicionUSUARIO(contenidoCriptosUsuario[1][0]));
				frame.setVisible(true);
				} else {
					  JOptionPane.showMessageDialog(null, "No ha agregado la criptomoneda al portafolio");
				}
			}
		});
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(contenidoCriptosUsuario[0][0]!="") {
				Comercializar frame = new Comercializar(1,posicionUSUARIO(contenidoCriptosUsuario[0][0]));
				frame.setVisible(true);
				lblUsdt.setText(String.valueOf(Principal.usuarioActual.getDinero()));
				} else {
					  JOptionPane.showMessageDialog(null, "No ha agregado la criptomoneda al portafolio");
				}
			}
		});
		llenarTabla();
		llenarComboBox();
		TimerTask actualizar = new TimerTask() {
			@Override
			public void run() {
				lblUsdt.setText(String.valueOf(Principal.usuarioActual.getDinero()) + " " + "USDT");
				llenarTabla();
			}
			
		};
		timer.schedule(actualizar,10,10000);
	}
	/**
	 * Este procedimiento se encarga de llenar las ComboBox
	 * con los datos de las monedas proporcionados por la API
	 */
	public void llenarComboBox() {
		for ( int i=0 ; i < 30 ; i++ ) {
			comboCriptos.addItem(Principal.cryptoMonedas[i].moneda);
		}
	}
	/**
	 * Este procedimiento se encarga de inicializar los espacios
	 * del arreglo con informacion vacia
	 */
	public void inicializartabla() {
		for ( int f=0 ; f < 10 ; f++ ) {
			contenidoCriptosUsuario[f][0]="";
		}
	}
	/**
	 * Este metodo busca la posicion de la moneda
	 * solicitada en el arreglo de monedas del usuario
	 * @param nombre - nombre de la moneda solicitada
	 * @return - entero con la posicion de la moneda dentro del arreglo
	 */
	public int posicionUSUARIO(String nombre) {
		for ( int f=0 ; f < 10 ; f++ ) {
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
	/**
	 * Este procedimiento se encarga de llenar la tabla con el contenido del
	 * portafolio del usuario
	 */
	public void llenarTabla() {
		for ( int i=0 ; i < Principal.usuarios.size() ; i++) {
			if (Principal.usuarioActual.getCorreo().equals(Principal.usuarios.get(i).getCorreo())) {
					for ( int f=0 ; f < Principal.usuarios.get(i).monedas.size() ; f++ ) {
						contenidoCriptosUsuario[f][0] = Principal.usuarios.get(i).monedas.get(f).getNombre();
						contenidoCriptosUsuario[f][1] = String.valueOf(Principal.usuarios.get(i).monedas.get(f).getCantidad());
					  if(Principal.cryptoMonedas[posicion(Principal.usuarios.get(i).monedas.get(f).getNombre())].precioActual!=0) {	
						Principal.usuarios.get(i).monedas.get(f).precio=Principal.cryptoMonedas[posicion(Principal.usuarios.get(i).monedas.get(f).getNombre())].precioActual;
					  }
					}
				break;
			}
		}
		tablaCriptosUsuario.setModel(new DefaultTableModel(
				contenidoCriptosUsuario,
			new String[] {
				"Nombre", "Cantidad"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tablaCriptosUsuario.getColumnModel().getColumn(0).setResizable(false);
		tablaCriptosUsuario.getColumnModel().getColumn(1).setResizable(false);
		tablaCriptosUsuario.getColumnModel().getColumn(1).setPreferredWidth(91);
		tablaCriptosUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
	}
}
