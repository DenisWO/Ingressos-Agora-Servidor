package model;

public class FormaPagamento{
	private String descricao;
	private int prazo;
	private int id;
	
	public FormaPagamento(int prazo, String desc, int id) {
		this.prazo = prazo;
		this.descricao = desc;
		this.id = id;
	}
	/* - Não possui responsabilidades*/
	public int getPrazo() {
		return prazo;
	}

	public void setPrazo(int prazo) {
		this.prazo = prazo;
	}

	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
