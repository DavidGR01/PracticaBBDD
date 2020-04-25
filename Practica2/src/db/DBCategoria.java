package db;

public class DBCategoria {

	private int id_categoria = -1;
	private float ratio_mantenimiento, precio_diario;
	private String nombre;

	public DBCategoria(int id_categoria, float ratio_mantenimiento, float precio_diario, String nombre) {
		this.id_categoria = id_categoria;
		this.ratio_mantenimiento = ratio_mantenimiento;
		this.precio_diario = precio_diario;
		this.nombre = nombre;
	}

	public int getId_categoria() {
		return id_categoria;
	}

	public void setId_categoria(int id_categoria) {
		this.id_categoria = id_categoria;
	}

	public float getRatio_mantenimiento() {
		return ratio_mantenimiento;
	}

	public void setRatio_mantenimiento(float ratio_mantenimiento) {
		this.ratio_mantenimiento = ratio_mantenimiento;
	}

	public float getPrecio_diario() {
		return precio_diario;
	}

	public void setPrecio_diario(float precio_diario) {
		this.precio_diario = precio_diario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "DBCategoria [id_categoria=" + id_categoria + ", ratio_mantenimiento=" + ratio_mantenimiento
				+ ", precio_diario=" + precio_diario + ", nombre=" + nombre + "]";
	}
}
