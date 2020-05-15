package model;

public class Cliente {
	private int idCliente = -1, dniCliente;
	private String nombre, apellido1, apellido2, telefonoContacto;
	private int idPoblacion;

	public Cliente(int idCliente, int dniCliente, String nombre, String apellido1, String apellido2,
			String telefonoContacto, int idPoblacion) {
		this.idCliente = idCliente;
		this.dniCliente = dniCliente;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.telefonoContacto = telefonoContacto;
		this.idPoblacion = idPoblacion;
	}

	// Constructor para crear clientes antes de insertarlos
	public Cliente(int dniCliente, String nombre, String apellido1, String apellido2, String telefonoContacto,
			int idPoblacion) {
		this.dniCliente = dniCliente;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.telefonoContacto = telefonoContacto;
		this.idPoblacion = idPoblacion;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public int getDniCliente() {
		return dniCliente;
	}

	public void setDniCliente(int dniCliente) {
		this.dniCliente = dniCliente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getTelefonoContacto() {
		return telefonoContacto;
	}

	public void setTelefonoContacto(String telefonoContacto) {
		this.telefonoContacto = telefonoContacto;
	}

	public int getIdPoblacion() {
		return idPoblacion;
	}

	public void setIdPoblacion(int idPoblacion) {
		this.idPoblacion = idPoblacion;
	}

	@Override
	public String toString() {
		return "Cliente [idCliente=" + idCliente + ", DNI=" + dniCliente + ", nombre=" + nombre + ", apellido1="
				+ apellido1 + ", apellido2=" + apellido2 + ", telefonoContacto=" + telefonoContacto + ", idPoblacion="
				+ idPoblacion + "]";
	}

}
