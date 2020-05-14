package model;

public class Categoria {
	private int idCategoria = -1;
	private String nombre;
	private double precioDiario, ratioMantenimiento;

	public Categoria(int idCategoria, String nombre, double precioDiario, double ratioMantenimiento) {
		this.idCategoria = idCategoria;
		this.nombre = nombre;
		this.precioDiario = precioDiario;
		this.ratioMantenimiento = ratioMantenimiento;
	}

	public int getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecioDiario() {
		return precioDiario;
	}

	public void setPrecioDiario(float precioDiario) {
		this.precioDiario = precioDiario;
	}

	public double getRatioMantenimiento() {
		return ratioMantenimiento;
	}

	public void setRatioMantenimiento(float ratioMantenimiento) {
		this.ratioMantenimiento = ratioMantenimiento;
	}

	@Override
	public String toString() {
		return "Categoria [idCategoria=" + idCategoria + ", nombre=" + nombre + ", precioDiario=" + precioDiario
				+ ", ratioMantenimiento=" + ratioMantenimiento + "]";
	}

}
