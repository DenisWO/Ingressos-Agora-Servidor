package model;

public class Ingresso {
	private int id;
	private double preco;
	private Evento evento;
	
	public Ingresso(double preco, Evento evento, int id) {
		this.id = id;
		this.preco = preco;
		this.evento = evento;
	}
	public Ingresso() {
		this.id = 0;
		this.preco = 0;
		this.evento = null;
	}
	/*Não tem responsabilidades*/
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getPreco() {
		return preco;
	}
	public void setPreco(float preco) {
		this.preco = preco;
	}
	public Evento getEvento() {
		return evento;
	}
	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	
	
	
	
	
}
