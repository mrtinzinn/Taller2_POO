package taller2;

public class Comida {

	private String tipo;
	private String consumidor;
	private double litros;
	private String fecha;
	private int largo;
	private int ancho;
	private double kcal;
	
	
//TERREMOTOS
	public Comida(String tipo, String consumidor, double litros, String fecha) {
		this.tipo = tipo;
		this.consumidor = consumidor;
		this.litros = litros;
		this.fecha = fecha;
		this.kcal = (litros* 10) / 3;
	}

//CHORIPANES
	public Comida(String tipo, String consumidor, int largo, int ancho,String fecha) {
		this.tipo = tipo;
		this.consumidor = consumidor;
		this.fecha = fecha;
		this.largo = largo;
		this.ancho = ancho;
		this.kcal = (largo * ancho ) / 3;
	}

	public String getTipo() {
		return tipo;
	}

	public String getConsumidor() {
		return consumidor;
	}

	public double getLitros() {
		return litros;
	}

	public String getFecha() {
		return fecha;
	}

	public int getLargo() {
		return largo;
	}

	public int getAncho() {
		return ancho;
	}

	public double getKcal() {
		return kcal;
	}
	

}
