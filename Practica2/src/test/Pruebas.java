package test;

import db.ConnectionManager;
import db.DBCliente;
import db.DBReserva;
import model.Cliente;
import model.Vehiculo;

public class Pruebas {

	public static void main(String[] args) {
//
		ConnectionManager.init("localhost", "3306", "practica_bbdd_2020", "root", "hola123");
////		
//		DBVehiculo.importarCSV("vehiculos.csv");
//		

//		DBTrabajo_Mantenimiento.importarCSV("trabajo_mantenimiento.csv", 50000);


		Cliente c = new Cliente(11,5442, "Oscar", "Jadfj", "sdfsdv", "644", 2163);
		
		Vehiculo v = new Vehiculo(2, "LQY9436", 2000, 1, 5, 5, 573, 4, 6);
		
		System.out.println(DBCliente.insertarCliente(c).getIdCliente());
		
		System.out.println(DBReserva.nuevaReserva(v,c));
		
		
		//
	}

}
