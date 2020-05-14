package test;

import db.ConnectionManager;
import db.DBTrabajo_Mantenimiento;
import db.DBVehiculo;

public class Pruebas {

	public static void main(String[] args) {

		ConnectionManager.init("localhost", "3306", "practica_bbdd_2020", "root", "hola123");
//		
		DBVehiculo.importarCSV("vehiculos.csv");
//		

//		DBTrabajo_Mantenimiento.importarCSV("trabajo_mantenimiento.csv", 50000);

	}

}
