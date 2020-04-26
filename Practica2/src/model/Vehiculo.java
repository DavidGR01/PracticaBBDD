package model;

public class Vehiculo {

	private int idVehiculo = -1;
	private String matricula;
	private int anoCatriculacion, idColor, numPlazas;
	private float km;
	private int idModelo, idCategoria, idTipoCombustible;

	public Vehiculo(int idVehiculo, String matricula, int anoCatriculacion, int idColor, int numPlazas, float km,
			int idModelo, int idCategoria, int idTipoCombustible) {
		this.idVehiculo = idVehiculo;
		this.anoCatriculacion = anoCatriculacion;
		this.idColor = idColor;
		this.numPlazas = numPlazas;
		this.idModelo = idModelo;
		this.idCategoria = idCategoria;
		this.idTipoCombustible = idTipoCombustible;
		this.km = km;
		this.matricula = matricula;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public int getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(int idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public int getAnoCatriculacion() {
		return anoCatriculacion;
	}

	public void setAnoCatriculacion(int anoCatriculacion) {
		this.anoCatriculacion = anoCatriculacion;
	}

	public int getIdColor() {
		return idColor;
	}

	public void setIdColor(int idColor) {
		this.idColor = idColor;
	}

	public int getNumPlazas() {
		return numPlazas;
	}

	public void setNumPlazas(int numPlazas) {
		this.numPlazas = numPlazas;
	}

	public int getIdModelo() {
		return idModelo;
	}

	public void setIdModelo(int idModelo) {
		this.idModelo = idModelo;
	}

	public int getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public int getIdTipoCombustible() {
		return idTipoCombustible;
	}

	public void setIdTipoCombustible(int idTipoCombustible) {
		this.idTipoCombustible = idTipoCombustible;
	}

	public float getKm() {
		return km;
	}

	public void setKm(float km) {
		this.km = km;
	}

	@Override
	public String toString() {
		return "Vehiculo [idVehiculo=" + idVehiculo + ", matricula=" + matricula + ", anoCatriculacion="
				+ anoCatriculacion + ", idColor=" + idColor + ", numPlazas=" + numPlazas + ", km=" + km + ", idModelo="
				+ idModelo + ", idCategoria=" + idCategoria + ", idTipoCombustible=" + idTipoCombustible + "]";
	}

}
