package model;

public class Categoria {
	private int idCategoria = -1;
	private String nombre;
	private float precioDiario, ratioMantenimiento;

	public Categoria(int idCategoria, String nombre, float precioDiario, float ratioMantenimiento) {
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

	public float getPrecioDiario() {
		return precioDiario;
	}

	public void setPrecioDiario(float precioDiario) {
		this.precioDiario = precioDiario;
	}

	public float getRatioMantenimiento() {
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
