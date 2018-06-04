package model;

public class Evento {
	private int id;
	private String descricao;
	
	public Evento(String descricao, int id) {
		this.descricao = descricao;
		this.id = id;
	}
	
	/* - Cadatrar ingresso*/
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}
