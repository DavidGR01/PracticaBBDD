package model;

public class Cliente {
	int id_cliente, dni_cliente, id_poblacion;
	String nombre, apellido1, apellido2, telefono_contacto;

	public Cliente(int id_cliente, int dni_cliente, int id_poblacion, String nombre, String apellido1,
			String apellido2, String telefono_contacto) {
		this.id_cliente = id_cliente;
		this.dni_cliente = dni_cliente;
		this.id_poblacion = id_poblacion;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.telefono_contacto = telefono_contacto;
	}

	public int getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}

	public int getDni_cliente() {
		return dni_cliente;
	}

	public void setDni_cliente(int dni_cliente) {
		this.dni_cliente = dni_cliente;
	}

	public int getId_poblacion() {
		return id_poblacion;
	}

	public void setId_poblacion(int id_poblacion) {
		this.id_poblacion = id_poblacion;
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

	public String getTelefono_contacto() {
		return telefono_contacto;
	}

	public void setTelefono_contacto(String telefono_contacto) {
		this.telefono_contacto = telefono_contacto;
	}

	@Override
	public String toString() {
		return "DBCliente [id_cliente=" + id_cliente + ", dni_cliente=" + dni_cliente + ", id_poblacion=" + id_poblacion
				+ ", nombre=" + nombre + ", apellido1=" + apellido1 + ", apellido2=" + apellido2
				+ ", telefono_contacto=" + telefono_contacto + "]";
	}

}
