package control;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.Cliente;
import model.Ingresso;

public class Servidor {

	private static int portaServidor = 8999;
	private static ServerSocket socket;
	private static Dados dados = new Dados();

	public static String getPortaServidor(){
		return String.valueOf(portaServidor);
	}
	public static void inicializaServidor() throws IOException, SQLException {
		dados.buscaTodosDados();
		connectionServer();
	}
	public static String criptografaSaida(String entrada){
		char[] alfabeto = new char[128];
		for(int i=0; i<128; i++){
			alfabeto[i] = (char) i;
		}
		String saida = "";
		for(int i=0; i<entrada.length(); i++){
			for(int j=0; j<128; j++){
				if(entrada.charAt(i) == alfabeto[j]){
					saida += alfabeto[j+3];
				}
			}
		}
		return saida;
	}
	public static String descriptografaEntrada(String saida){
		char[] alfabeto = new char[128];
		for(int i=0; i<128; i++){
			alfabeto[i] = (char) i;
		}
		String entrada = "";
		for(int i=0; i<saida.length(); i++){
			for(int j=0; j<128; j++){
				if(saida.charAt(i) == alfabeto[j]){
					entrada += alfabeto[j-3];
				}
			}
		}
		return entrada;
	}
	public static String realizarLogin(String login) {
		ArrayList<String> array = new ArrayList<>();
		int it = 0;
		for(int i=0; i<login.length(); i++) {
			if(login.charAt(i) == '/'){
				array.add(login.substring(it, i));
				it = i+1;
			}
		}

		Cliente c = new Cliente("", array.get(1), array.get(2), 0);
		for (Cliente cliente : dados.getClientes()) {
			if(cliente.getEmail().equals(c.getEmail()) && cliente.getSenha().equals(c.getSenha())) {
				return new String("0001" + "/" + cliente.getId() + "/" + cliente.getNome() + "/"
						+ cliente.getEmail()+ "/" + cliente.getSenha() + "/");
			}
		}
		JOptionPane.showMessageDialog(null, "Não foi encontrado usuário\n Falha de login");
		return null;
	}
	public static String verIngressoCliente(String recebido) throws SQLException {
		ArrayList<String> array = new ArrayList<>();
		int it=0;
		for(int i=0; i<recebido.length(); i++) {
			if(recebido.charAt(i) == '/') {
				array.add(recebido.substring(it, i));
				it = i+1;
			}
		}
		ArrayList<Ingresso> ing = dados.buscaTodosIngressosCliente(Integer.parseInt(array.get(1)));
		String saida = "0010/";
		for (Ingresso ingresso : ing) {
			saida += ingresso.getId() + "|" + ingresso.getPreco() + "|" + ingresso.getEvento().getDescricao() +"|" + "/";
		}
		return saida;
	}
	public static String verTodosIngressos() {
		String saida = "0100/";
		for (Ingresso i : dados.getIngressos()) {
			saida += i.getId() + "|" + i.getPreco() + "|" + i.getEvento().getDescricao() + "|"+ "/";
		}
		return saida;
	}
	public static String realizaCompra(String recebido) throws SQLException {
		String saida = "1000/";
		int it=0;
		ArrayList<String> array = new ArrayList<>();
		for(int i=0; i<recebido.length(); i++) {
			if(recebido.charAt(i) == '/') {
				array.add(recebido.substring(it, i));
				it = i+1;
			}
		}
		Ingresso resp = dados.realizaCompra(Integer.parseInt(array.get(1)), Integer.parseInt(array.get(2)), Integer.parseInt(array.get(3)));
		saida += resp.getId() + "/" + resp.getPreco() + "/" + resp.getEvento().getDescricao();
		return saida;
	}

	public static void connectionServer() throws IOException, SQLException {
		socket = new ServerSocket(portaServidor);
		int opcao = JOptionPane.YES_NO_OPTION;
		while(true)
		{
			//JOptionPane.showMessageDialog(null, "Servidor online \nPorta usada: " + getPortaServidor());
			//Efetua a primitiva accept
			
			Socket conexao = socket.accept();
			//System.out.println("Aguardando recebimento de pacotes!\n");
			//Efetua a primitiva receive
			
			BufferedReader entrada =  new BufferedReader(new InputStreamReader(conexao.getInputStream()));

			//Operacao com os dados recebidos e preparacao dos a serem enviados
			String str = descriptografaEntrada(entrada.readLine());
			//System.out.println("Received: " + str + "\n");
			String controle = "";
			controle = str.substring(0, 4);

			if(controle.equals("0001")) {
				//JOptionPane.showMessageDialog(null, "Realizando login: ");
				str = realizarLogin(str);
			}
			else if(controle.equals("0010")) {
				//JOptionPane.showMessageDialog(null, "Buscando ingressos comprados: ");
				str = verIngressoCliente(str);
			}
			else if(controle.equals("0100")) {
				//JOptionPane.showMessageDialog(null, "Buscando todos os ingressos: ");
				str = verTodosIngressos();
			}
			else if(controle.equals("1000")) {
				//JOptionPane.showMessageDialog(null, "Realizando compra: ");
				str = realizaCompra(str);
			}
			else if(controle.equals("1111")) {
				JOptionPane.showMessageDialog(null, "Fechando servidor");
				System.runFinalization();
				System.exit(0);
			}
			//System.out.println("Executando o envio do pacote:" + str);
			//Efetua a primitiva send
			DataOutputStream saida = new DataOutputStream(conexao.getOutputStream());
			//System.out.println("Saida: " + saida + "Saida criptografada: " + criptografaSaida(str));
			saida.writeBytes(criptografaSaida(str) + '\n');
			//System.out.println("Enviado!\n\n");

		}
		
	}


}
