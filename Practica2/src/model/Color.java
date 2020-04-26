package model;

public class Color {

	private int idColor = -1;
	private String descripcion;

	public Color(int idColor, String descripcion) {
		this.idColor = idColor;
		this.descripcion = descripcion;
	}

	public int getIdColor() {
		return idColor;
	}

	public void setIdColor(int idColor) {
		this.idColor = idColor;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "Color [idColor=" + idColor + ", descripcion=" + descripcion + "]";
	}

}
