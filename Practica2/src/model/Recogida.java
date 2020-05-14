package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Recogida {

	private int idReserva = -1;
	private LocalDate fechaPrevista;
	private LocalTime horaPrevista;
	private LocalDate fechaEfectiva;
	private LocalTime horaEfectiva;
	private int idOficina, idEstadoTransaccion;

	public Recogida(int idReserva, LocalDate fechaPrevista, LocalTime horaPrevista, LocalDate fechaEfectiva,
			LocalTime horaEfectiva, int idOficina, int idEstadoTransaccion) {
		this.idReserva = idReserva;
		this.fechaPrevista = fechaPrevista;
		this.horaPrevista = horaPrevista;
		this.fechaEfectiva = fechaEfectiva;
		this.horaEfectiva = horaEfectiva;
		this.idOficina = idOficina;
		this.idEstadoTransaccion = idEstadoTransaccion;
	}

	// Constructor sin id
	public Recogida(LocalDate fechaPrevista, LocalTime horaPrevista, LocalDate fechaEfectiva, LocalTime horaEfectiva,
			int idOficina, int idEstadoTransaccion) {
		this.fechaPrevista = fechaPrevista;
		this.horaPrevista = horaPrevista;
		this.fechaEfectiva = fechaEfectiva;
		this.horaEfectiva = horaEfectiva;
		this.idOficina = idOficina;
		this.idEstadoTransaccion = idEstadoTransaccion;
	}

	public int getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(int idReserva) {
		this.idReserva = idReserva;
	}

	public LocalDate getFechaPrevista() {
		return fechaPrevista;
	}

	public void setFechaPrevista(LocalDate fechaPrevista) {
		this.fechaPrevista = fechaPrevista;
	}

	public LocalTime getHoraPrevista() {
		return horaPrevista;
	}

	public void setHoraPrevista(LocalTime horaPrevista) {
		this.horaPrevista = horaPrevista;
	}

	public LocalDate getFechaEfectiva() {
		return fechaEfectiva;
	}

	public void setFechaEfectiva(LocalDate fechaEfectiva) {
		this.fechaEfectiva = fechaEfectiva;
	}

	public LocalTime getHoraEfectiva() {
		return horaEfectiva;
	}

	public void setHoraEfectiva(LocalTime horaEfectiva) {
		this.horaEfectiva = horaEfectiva;
	}

	public int getIdOficina() {
		return idOficina;
	}

	public void setIdOficina(int idOficina) {
		this.idOficina = idOficina;
	}

	public int getIdEstadoTransaccion() {
		return idEstadoTransaccion;
	}

	public void setIdEstadoTransaccion(int idEstadoTransaccion) {
		this.idEstadoTransaccion = idEstadoTransaccion;
	}

	@Override
	public String toString() {
		return "Recogida [idReserva=" + idReserva + ", fechaPrevista=" + fechaPrevista + ", horaPrevista="
				+ horaPrevista + ", fechaEfectiva=" + fechaEfectiva + ", horaEfectiva=" + horaEfectiva + ", idOficina="
				+ idOficina + ", idEstadoTransaccion=" + idEstadoTransaccion + "]";
	}

}
