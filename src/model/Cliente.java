package model;

import java.util.ArrayList;
import java.util.Scanner;

public class Cliente {
	private int id;
	private String nome;
	private String email;
	private String senha;
	private ArrayList <Ingresso> ingressos= new ArrayList<>();
	
	/*Funções do cliente: 
	 * - comprar ingresso
	 * - desfazer compra de ingresso
	 * - ver os ingressos comprados
	 * - Salva no banco os dados da compra
	 * - Cadastra evento
	 * - Altera informações no seu usuário
	 * */
	public Cliente(String nome, String email, String senha, int id) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.id = id;
	}
	public void realizarCompra(String idIngresso) {
		
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
}
