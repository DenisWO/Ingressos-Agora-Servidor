package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import connection.Conexao;
import model.Cliente;
import model.Compra;
import model.Evento;
import model.FormaPagamento;
import model.Ingresso;

public class Dados {
	private ArrayList<Cliente> clientes = new ArrayList<>();
	private ArrayList<Compra> compras = new ArrayList<>();
	private ArrayList<Evento> eventos = new ArrayList<>();
	private ArrayList<Ingresso> ingressos = new ArrayList<>();
	private ArrayList<FormaPagamento> formas = new ArrayList<>();
	
	public void buscaTodosDados() throws SQLException {
		Connection con = new Conexao().getConnection();
		buscaClientes(con);
		buscaEventos(con);
		buscaFormaPagamento(con);
		buscaIngresso(con);
		buscaCompra(con);		
		con.close();
	}
	public Cliente receberLogin(String dadosRecebidos) {
		String nome = "";
		String email = "";
		String senha = "";
		
		for (Cliente cliente : clientes) {
			if(cliente.getEmail().equals(email) && cliente.getSenha().equals(senha)) {
				return cliente;
			}
		}
		return null;
		
	}
	public Ingresso realizaCompra(int idCliente, int idIngresso, int idForma) throws SQLException {
		Connection con = new Conexao().getConnection();
		String insert = "INSERT INTO Compra (idCliente, idForma, idIngresso) VALUES (?, ?, ?)";
		PreparedStatement ps =  con.prepareStatement(insert);
		ps.setInt(1, idCliente);
		ps.setInt(2, idForma);
		ps.setInt(3, idIngresso);
		ps.execute();
		
		for (Ingresso ingresso : ingressos) {
			if(ingresso.getId() == idIngresso) {
				return ingresso;
			}
		}
		//Terminar a insercao dos dados no resultset
		
		return null;
		
	}
	public void buscaClientes(Connection con) throws SQLException {
		String select = "SELECT * FROM Cliente";
		ResultSet rs = con.prepareStatement(select).executeQuery();
		
		while(rs.next()) {
			Cliente c = new Cliente(rs.getString("nome"), rs.getString("email"), rs.getString("senha"), rs.getInt("idCliente"));
			this.clientes.add(c);
		}
	}
	public void buscaEventos(Connection con) throws SQLException {
		String select = "SELECT * FROM Evento";
		ResultSet rs = con.prepareStatement(select).executeQuery();
		
		while(rs.next()) {
			Evento e = new Evento(rs.getString("descricao"), rs.getInt("idEvento"));
			this.eventos.add(e);
		}
	}
	public void buscaIngresso(Connection con) throws SQLException {
		String select = "SELECT * FROM Ingresso";
		ResultSet rs = con.prepareStatement(select).executeQuery();
		
		while(rs.next()) {
			Ingresso i = null;
			for (Evento evento : eventos) {
				if(evento.getId() == rs.getInt("idEvento")) {
					i = new Ingresso(rs.getDouble("preco"), evento, rs.getInt("idIngresso"));
				}
			}
			this.ingressos.add(i);
		}
	}
	public ArrayList<Ingresso> buscaTodosIngressosCliente(int idCliente) throws SQLException {
		Connection con = new Conexao().getConnection();
		String select = "SELECT * FROM Ingresso WHERE idIngresso IN"
				+ "(SELECT idIngresso FROM Compra WHERE idCliente = ?)";
		PreparedStatement ps =  con.prepareStatement(select);
		ps.setInt(1, idCliente);
		ResultSet rs = ps.executeQuery();
		
		ArrayList<Ingresso> ing = new ArrayList<>();
		Ingresso i;
		while(rs.next()) {
			i = new Ingresso();
			i.setId(rs.getInt("idIngresso"));
			i.setPreco((float) rs.getDouble("preco"));
			for (Evento evento : eventos) {
				if(evento.getId() == rs.getInt("idEvento")) {
					i.setEvento(evento);
				}
			}
			ing.add(i);
		}
		con.close();
		return ing;
	}
	public void buscaFormaPagamento(Connection con) throws SQLException {
		String select = "SELECT * FROM FormaPagamento";
		ResultSet rs = con.prepareStatement(select).executeQuery();
		
		while(rs.next()) {
			FormaPagamento fm = new FormaPagamento(rs.getInt("prazo"), rs.getString("descricao"), rs.getInt("idForma"));
			this.formas.add(fm);
		}
	}
	public void buscaCompra(Connection con) throws SQLException {
		String select = "SELECT * FROM Compra";
		ResultSet rs = con.prepareStatement(select).executeQuery();
		
		while(rs.next()) {
			Compra c = new Compra(rs.getInt("idCompra"));
			for (Cliente cliente : clientes) {
				if(cliente.getId() == rs.getInt("idCliente")) {
					c.setCliente(cliente);
				}
			}
			for (FormaPagamento formaPagamento : formas) {
				if(formaPagamento.getId() == rs.getInt("idForma")) {
					c.setPgto(formaPagamento);
				}
			}
			for (Ingresso ingresso : ingressos) {
				if(ingresso.getId() == rs.getInt("idIngresso")) {
					c.setIngresso(ingresso);
				}
			}
			this.compras.add(c);
		}
	}
	
	
	public ArrayList<Cliente> getClientes() {
		return clientes;
	}
	public void setClientes(ArrayList<Cliente> clientes) {
		this.clientes = clientes;
	}
	public ArrayList<Compra> getCompras() {
		return compras;
	}
	public void setCompras(ArrayList<Compra> compras) {
		this.compras = compras;
	}
	public ArrayList<Evento> getEventos() {
		return eventos;
	}
	public void setEventos(ArrayList<Evento> eventos) {
		this.eventos = eventos;
	}
	public ArrayList<Ingresso> getIngressos() {
		return ingressos;
	}
	public void setIngressos(ArrayList<Ingresso> ingressos) {
		this.ingressos = ingressos;
	}
	public ArrayList<FormaPagamento> getFormas() {
		return formas;
	}
	public void setFormas(ArrayList<FormaPagamento> formas) {
		this.formas = formas;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
