package ar.com.flexia.restaurant.api.dto;

public class NewDelivery {

	private String direccion;
    
	private int telefono;
	
	private Double porcentajeDescuento = 0.0;

    public NewDelivery() {}

    public NewDelivery(String direccion, int telefono, Double porcentajeDescuento) {
        this.direccion = direccion;
        this.telefono = telefono;
        this.setPorcentajeDescuento(porcentajeDescuento);
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
    
    public Double getPorcentajeDescuento() {
		return porcentajeDescuento;
	}

	public void setPorcentajeDescuento(Double porcentajeDescuento) {
		this.porcentajeDescuento = porcentajeDescuento;
	}
    
}