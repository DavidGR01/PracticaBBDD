package model;

import java.time.LocalDateTime;

public class Reserva {

	private int idReserva = -1;
	private LocalDateTime fecha;
	private int idEstadoTransaccion, idVehiculo, idCliente;

	public Reserva(int idReserva, LocalDateTime fecha, int idEstadoTransaccion, int idVehiculo, int idCliente) {
		this.idReserva = idReserva;
		this.idEstadoTransaccion = idEstadoTransaccion;
		this.idVehiculo = idVehiculo;
		this.idCliente = idCliente;
		this.fecha = fecha;
	}

	public int getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(int idReserva) {
		this.idReserva = idReserva;
	}

	public int getIdEstadoTransaccion() {
		return idEstadoTransaccion;
	}

	public void setIdEstadoTransaccion(int idEstadoTransaccion) {
		this.idEstadoTransaccion = idEstadoTransaccion;
	}

	public int getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(int idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "Reserva [idReserva=" + idReserva + ", fecha=" + fecha + ", idEstadoTransaccion=" + idEstadoTransaccion
				+ ", idVehiculo=" + idVehiculo + ", idCliente=" + idCliente + "]";
	}

}
