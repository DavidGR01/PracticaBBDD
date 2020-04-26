package model;

public class TipoCombustible {

	private int idTipoCombustible = -1;
	private String nombre, descripcion;

	public TipoCombustible(int idTipoCombustible, String nombre, String descripcion) {
		this.idTipoCombustible = idTipoCombustible;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	public int getIdTipoCombustible() {
		return idTipoCombustible;
	}

	public void setIdTipoCombustible(int idTipoCombustible) {
		this.idTipoCombustible = idTipoCombustible;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "Tipo_combustible [idTipoCombustible=" + idTipoCombustible + ", nombre=" + nombre + ", descripcion="
				+ descripcion + "]";
	}

}
