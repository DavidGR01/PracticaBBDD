package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import db.ConnectionManager;
import db.DBCategoria;
import db.DBCliente;
import db.DBColor;
import db.DBDevolucion;
import db.DBRecogida;
import db.DBReserva;
import db.DBTipoCombustible;
import db.DBVehiculo;
import model.Categoria;
import model.Cliente;
import model.Color;
import model.Devolucion;
import model.Recogida;
import model.Reserva;
import model.TipoCombustible;
import model.Vehiculo;

public class MainScreen extends JFrame {

	private JPanel contentPane;
	private JTextField txtDNI;
	private JTextField dniNuevoCliente;
	private JTextArea textArea;
	private JTextField nombreNuevoCliente;
	private JTextField apellido1NuevoCliente;
	private JTextField apellido2NuevoCliente;
	private JTextField telefonoNuevoCliente;
	private JTextField idPoblacionNuevoCliente;
	private JComboBox cbCategoria, cbTtipoComb;
	private List<Categoria> categorias;
	private List<TipoCombustible> combustibles;
	private JTextField txtDNIReserva;
	private JTextField txtIdVehiculo;
	private String separador = "------------------------------------------";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		ConnectionManager.init("localhost", "3306", "practica_bbdd_2020", "root", "hola123");

		/**
		 * Set L&F
		 */
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainScreen frame = new MainScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainScreen() {

		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/db.png")));
		setTitle("Practica 2 BBDD");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1393, 799);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 17));

		JPanel panelTareasVarias = new JPanel();
		tabbedPane.addTab("Tareas varias", null, panelTareasVarias, null);
		panelTareasVarias.setLayout(null);

		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(265, 43, 2, 623);
		panelTareasVarias.add(separator_1);

		JLabel lblListados = new JLabel("LISTADOS");
		lblListados.setHorizontalAlignment(SwingConstants.LEFT);
		lblListados.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblListados.setBounds(293, 233, 105, 30);
		panelTareasVarias.add(lblListados);

		JLabel lblObtenernrVehculo = new JLabel("OBTENER VEHÍCULO");
		lblObtenernrVehculo.setHorizontalAlignment(SwingConstants.LEFT);
		lblObtenernrVehculo.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblObtenernrVehculo.setBounds(293, 27, 223, 63);
		panelTareasVarias.add(lblObtenernrVehculo);

		JLabel lblNuevocliente = new JLabel("NUEVO CLIENTE ");
		lblNuevocliente.setHorizontalAlignment(SwingConstants.CENTER);
		lblNuevocliente.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNuevocliente.setBounds(57, 27, 144, 63);
		panelTareasVarias.add(lblNuevocliente);

		JLabel lblNuevaReserva = new JLabel("NUEVA RESERVA");
		lblNuevaReserva.setHorizontalAlignment(SwingConstants.LEFT);
		lblNuevaReserva.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNuevaReserva.setBounds(293, 568, 209, 42);
		panelTareasVarias.add(lblNuevaReserva);

		JButton btnColores = new JButton("Colores");
		btnColores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				List<Color> colores = DBColor.listadoColores();
				printList(colores);

			}
		});
		btnColores.setBounds(303, 280, 75, 25);
		panelTareasVarias.add(btnColores);

		JButton btnCombustibles = new JButton("<html>Tipos de<br>Combustible</html>");
		btnCombustibles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				List<TipoCombustible> combustibles = DBTipoCombustible.listadoTiposCombustible();
				printList(combustibles);

			}
		});
		btnCombustibles.setBounds(495, 272, 97, 33);
		panelTareasVarias.add(btnCombustibles);

		JButton btnCategorias = new JButton("Categorias");
		btnCategorias.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				List<Categoria> categorias = DBCategoria.listadoCategorias();
				printList(categorias);

			}
		});
		btnCategorias.setBounds(390, 280, 93, 25);
		panelTareasVarias.add(btnCategorias);

		JLabel lblObtenercliente = new JLabel("OBTENER CLIENTE");
		lblObtenercliente.setHorizontalAlignment(SwingConstants.LEFT);
		lblObtenercliente.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblObtenercliente.setBounds(286, 401, 223, 51);
		panelTareasVarias.add(lblObtenercliente);

		txtDNI = new JTextField();
		txtDNI.setColumns(10);
		txtDNI.setBounds(296, 476, 116, 22);
		panelTareasVarias.add(txtDNI);

		dniNuevoCliente = new JTextField();
		dniNuevoCliente.setColumns(10);
		dniNuevoCliente.setBounds(57, 115, 144, 22);
		panelTareasVarias.add(dniNuevoCliente);

		JButton button_3 = new JButton("");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Cargamos los atributos de los textarea
				int dni = Integer.parseInt(dniNuevoCliente.getText());
				String nombre = nombreNuevoCliente.getText();
				String apellido1 = apellido1NuevoCliente.getText();
				String apellido2 = apellido2NuevoCliente.getText();
				String telefono = telefonoNuevoCliente.getText();
				int idPob = Integer.parseInt(idPoblacionNuevoCliente.getText());

				// Creamos el objeto cliente
				Cliente c = new Cliente(dni, nombre, apellido1, apellido2, telefono, idPob);

				// Lo añadimos a la BD
				c = DBCliente.insertarCliente(c);
				if (c != null) {
					textArea.append(c.toString());
					textArea.append("\n" + separador);
					textArea.append("\n");
				} else {
					textArea.append("Fallo de insercion: comprueba que el id de la población existe \n");
				}

			}
		});
		button_3.setToolTipText("Añadir Cliente");
		button_3.setIcon(new ImageIcon(MainScreen.class.getResource("/resources/newClient.png")));
		button_3.setBounds(57, 465, 144, 85);
		panelTareasVarias.add(button_3);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(611, 13, 737, 681);
		panelTareasVarias.add(scrollPane);

		textArea = new JTextArea();
		textArea.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));
		scrollPane.setViewportView(textArea);

		JLabel lblDni = new JLabel("DNI");
		lblDni.setBounds(57, 90, 56, 16);
		panelTareasVarias.add(lblDni);

		nombreNuevoCliente = new JTextField();
		nombreNuevoCliente.setColumns(10);
		nombreNuevoCliente.setBounds(57, 175, 144, 22);
		panelTareasVarias.add(nombreNuevoCliente);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(57, 150, 56, 16);
		panelTareasVarias.add(lblNombre);

		JLabel lblPrimerApellido = new JLabel("Primer Apellido");
		lblPrimerApellido.setBounds(57, 208, 110, 16);
		panelTareasVarias.add(lblPrimerApellido);

		apellido1NuevoCliente = new JTextField();
		apellido1NuevoCliente.setColumns(10);
		apellido1NuevoCliente.setBounds(57, 233, 144, 22);
		panelTareasVarias.add(apellido1NuevoCliente);

		JLabel lblSegundoApellido = new JLabel("Segundo Apellido");
		lblSegundoApellido.setBounds(57, 268, 110, 16);
		panelTareasVarias.add(lblSegundoApellido);

		apellido2NuevoCliente = new JTextField();
		apellido2NuevoCliente.setColumns(10);
		apellido2NuevoCliente.setBounds(57, 293, 144, 22);
		panelTareasVarias.add(apellido2NuevoCliente);

		JLabel lblTelfono = new JLabel("Teléfono");
		lblTelfono.setBounds(57, 331, 110, 16);
		panelTareasVarias.add(lblTelfono);

		telefonoNuevoCliente = new JTextField();
		telefonoNuevoCliente.setColumns(10);
		telefonoNuevoCliente.setBounds(57, 356, 144, 22);
		panelTareasVarias.add(telefonoNuevoCliente);

		JLabel lblIdPoblacin = new JLabel("Id Población");
		lblIdPoblacin.setBounds(57, 391, 110, 16);
		panelTareasVarias.add(lblIdPoblacin);

		idPoblacionNuevoCliente = new JTextField();
		idPoblacionNuevoCliente.setColumns(10);
		idPoblacionNuevoCliente.setBounds(57, 416, 144, 22);
		panelTareasVarias.add(idPoblacionNuevoCliente);

		cbTtipoComb = new JComboBox();
		cbTtipoComb.setToolTipText("Combustible");
		cbTtipoComb.setBounds(307, 127, 110, 25);
		panelTareasVarias.add(cbTtipoComb);

		JLabel lblCombustible = new JLabel("Combustible");
		lblCombustible.setBounds(307, 103, 91, 16);
		panelTareasVarias.add(lblCombustible);

		JLabel lblCategora = new JLabel("Categoría");
		lblCategora.setToolTipText("Categoría");
		lblCategora.setBounds(453, 103, 91, 16);
		panelTareasVarias.add(lblCategora);

		cbCategoria = new JComboBox();
		cbCategoria.setToolTipText("Combustible");
		cbCategoria.setBounds(453, 127, 110, 25);
		panelTareasVarias.add(cbCategoria);

		JButton btnMostrarVehiculo = new JButton("MOSTRAR");
		btnMostrarVehiculo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// Buscamos el tipo de combustible

				TipoCombustible tc = combustibles.get(cbTtipoComb.getSelectedIndex());

				// Buscamos la categoria

				Categoria c = categorias.get(cbCategoria.getSelectedIndex());

				// Hacemos la peticion a la BD

				List<Vehiculo> vs = DBVehiculo.getVehiculos(tc, c);

				printList(vs);
			}
		});
		btnMostrarVehiculo.setBounds(495, 43, 104, 42);
		panelTareasVarias.add(btnMostrarVehiculo);

		JLabel lblDni_1 = new JLabel("DNI");
		lblDni_1.setBounds(296, 455, 56, 16);
		panelTareasVarias.add(lblDni_1);

		JButton btnMostrarCliente = new JButton("MOSTRAR");
		btnMostrarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Cogemos el DNI del area de texto
				int dni = Integer.parseInt(txtDNI.getText());

				// Acceso a DB para obtener cliente
				Cliente c = DBCliente.obtenerClientePorDNI(dni);

				// Mostramos en la "consola", saltamos la linea y ponemos un separador
				textArea.append(c + "\n" + separador + "\n");

			}
		});
		btnMostrarCliente.setBounds(488, 434, 104, 42);
		panelTareasVarias.add(btnMostrarCliente);

		JLabel label = new JLabel("DNI");
		label.setBounds(293, 623, 56, 16);
		panelTareasVarias.add(label);

		txtDNIReserva = new JTextField();
		txtDNIReserva.setColumns(10);
		txtDNIReserva.setBounds(293, 644, 116, 22);
		panelTareasVarias.add(txtDNIReserva);

		JLabel lblIdVehculo = new JLabel("Id Vehículo");
		lblIdVehculo.setBounds(441, 623, 93, 16);
		panelTareasVarias.add(lblIdVehculo);

		txtIdVehiculo = new JTextField();
		txtIdVehiculo.setColumns(10);
		txtIdVehiculo.setBounds(441, 644, 116, 22);
		panelTareasVarias.add(txtIdVehiculo);

		JButton btnGenerarReserva = new JButton("GENERAR");
		btnGenerarReserva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Obtener dni del textarea
				int dni = Integer.parseInt(txtDNIReserva.getText());

				// Obtener id_vehiculo del textarea
				int idVehiculo = Integer.parseInt(txtIdVehiculo.getText());

				// Busqueda de los objetos
				Cliente c = DBCliente.obtenerClientePorDNI(dni);
				Vehiculo v = DBVehiculo.getVehiculoById(idVehiculo);

				// Creacion de la reserva
				Reserva r = DBReserva.nuevaReserva(v, c);

				// Obtencion de recogida y devolucion
				Recogida re = DBRecogida.getRecogidaById(r.getIdReserva());
				Devolucion d = DBDevolucion.getDevolucionById(r.getIdReserva());

				// Los añadimos a la "consola"
				textArea.append("Creada la reserva: " + r + "\n");

				textArea.append("Se crea también una recogida: " + re + "\n");
				textArea.append("Y una devolución: " + d + "\n" + separador + "\n");

			}
		});
		btnGenerarReserva.setBounds(488, 568, 104, 42);
		panelTareasVarias.add(btnGenerarReserva);

		JSeparator separator = new JSeparator();
		separator.setBounds(291, 195, 301, 2);
		panelTareasVarias.add(separator);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(293, 366, 301, 2);
		panelTareasVarias.add(separator_2);

		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(291, 531, 301, 2);
		panelTareasVarias.add(separator_3);

		JPanel panelEstadisticas = new JPanel();
		tabbedPane.addTab("Estadísticas", null, panelEstadisticas, null);
		panelEstadisticas.setLayout(null);

		JLabel lblNewLabel = new JLabel("Número de vehículos existentes en la base de datos");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(65, 80, 489, 31);
		panelEstadisticas.add(lblNewLabel);

		JLabel lblMarcaDeVehculos = new JLabel("Marca de vehículos que más kilómetros acumula");
		lblMarcaDeVehculos.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMarcaDeVehculos.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblMarcaDeVehculos.setBounds(65, 148, 489, 31);
		panelEstadisticas.add(lblMarcaDeVehculos);

		JLabel lblVehculosConMantenimiento = new JLabel("Vehículos con mantenimiento más caro entre dos años");
		lblVehculosConMantenimiento.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblVehculosConMantenimiento.setBounds(65, 221, 489, 31);
		panelEstadisticas.add(lblVehculosConMantenimiento);

		JLabel lblPrecioMedioDe = new JLabel("Precio medio de mantenimiento por kilómetro recorrido para un vehículo");
		lblPrecioMedioDe.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPrecioMedioDe.setBounds(65, 286, 851, 31);
		panelEstadisticas.add(lblPrecioMedioDe);

		JLabel lblVehculosQueNo = new JLabel("Vehículos que no han recibido ningún mantenimiento");
		lblVehculosQueNo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblVehculosQueNo.setBounds(65, 356, 851, 31);
		panelEstadisticas.add(lblVehculosQueNo);

		cargarCombos();
	}

	private void cargarCombos() {

		combustibles = DBTipoCombustible.listadoTiposCombustible();
		categorias = DBCategoria.listadoCategorias();

		for (TipoCombustible tipoCombustible : combustibles)
			cbTtipoComb.addItem(tipoCombustible.getNombre());
		for (Categoria c : categorias)
			cbCategoria.addItem(c.getNombre());

	}

	private void printList(List<?> lista) {
		for (Object e : lista)
			textArea.append(e.toString() + "\n");

		textArea.append(separador + "\n");
	}
}
