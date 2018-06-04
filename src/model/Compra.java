package model;

public class Compra {
	private Cliente cliente;
	private Ingresso ingresso;
	private FormaPagamento pgto;
	private int id;
	
	public Compra(int id) {
		this.id = id;
	}
	/* - Escolhe uma forma de pgto
	 * - Linka cliente e ingresso
	 * - Salva no banco os dados da compra*/
	
	
	public FormaPagamento getPgto() {
		return pgto;
	}
	public void setPgto(FormaPagamento pgto) {
		this.pgto = pgto;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Ingresso getIngresso() {
		return ingresso;
	}
	public void setIngresso(Ingresso ingresso) {
		this.ingresso = ingresso;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
