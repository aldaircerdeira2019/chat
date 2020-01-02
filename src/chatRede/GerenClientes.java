package chatRede;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

/*gerenciador dos clientes */
public class GerenClientes extends Thread{

	private Socket cliente;
	private String NomeClient;
	private BufferedReader leitor;
	private PrintWriter escrever;
	private static final Map<String,GerenClientes>clientes = new HashMap<String,GerenClientes>();/*list dos clientes*/

	public GerenClientes(Socket cliente){
		this.cliente = cliente;
		start();
	}
	@Override
	public void run() {
		
		try {
		     leitor = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
			 escrever = new PrintWriter(cliente.getOutputStream(),true );/* true serve para a msg ser enviada autom.*/
			 MetodoLogin();
			 String mensagem;
			while(true){ /*loop para mandar mensagens*/
				mensagem = leitor.readLine();
				if(mensagem.equalsIgnoreCase(Constantes.Sair)){
					this.cliente.close();
				}
				/*escrever.println(NomeClient + " voce disse: "+ mensagem);/*para retornar a msg*/
				//continua da lista
				else if(mensagem.startsWith(Constantes.mensag)){
					String NomeDestinatario =mensagem.substring(Constantes.mensag.length(), mensagem.length());
					GerenClientes destinatario =clientes.get(NomeDestinatario);
					if(destinatario==null){
						escrever.println("o cliente não existe");
					}else{
						//escrever.println("digita uma msg para "+ destinatario.getNomeClient());//
						destinatario.getEscrever().println(this.NomeClient+": "+ leitor.readLine());
					}
				}
				/*listar  clientes conectados*/
				else if(mensagem.equalsIgnoreCase(Constantes.ListaDeUser)){
				AtualizarLista(this);
				}
				else{
					escrever.println(this.NomeClient+" : "+mensagem);// voce disse
					
				}//fim  do lista
			}
	
			
		
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"conexão recusada");
			clientes.remove(this.NomeClient);
			e.printStackTrace();
		}
	}
private void MetodoLogin() throws IOException {
	while (true){
		escrever.println(Constantes.Nome_login);
		this.NomeClient = leitor.readLine().toLowerCase().replaceAll(",","");// toLowe: para não difere. min. e maiusc e raplaceAll: para os nomes não ter , 
		if(this.NomeClient.equalsIgnoreCase("null")||this.NomeClient.isEmpty()){
			escrever.println(Constantes.LoginNegado);
		}else if(clientes.containsKey(this.NomeClient)){
			escrever.println(Constantes.LoginNegado);
			}else{
				escrever.println(Constantes.LoginAceito);
				escrever.println("seja bem vindo "+ this.NomeClient);
				clientes.put(this.NomeClient, this);/*para armazenar o nome do cliente dentro da lista*/
				/*atualizar a lista de User para novos clientes para todos*/
				for(String cliente: clientes.keySet()){
					AtualizarLista(clientes.get(cliente));
				}
				break;
				/*fim da atualização*/
			}
		}
	}
private void AtualizarLista(GerenClientes gerenClientes) {
	StringBuilder listar =new StringBuilder();
	for (String c: clientes.keySet()){
		if(gerenClientes.getNomeClient().equals(c))
			continue;
		listar.append(c);
		listar.append(",");
	}
	if(listar.length()>0)
	listar.delete(listar.length()-1, listar.length());
	gerenClientes.getEscrever().println(Constantes.ListaDeUser);
	gerenClientes.getEscrever().println(listar.toString());
		
	}
public PrintWriter getEscrever() {
	return escrever;
}
public String getNomeClient() {
	return NomeClient;
}

/*
public BufferedReader getLeitor() {
	return leitor;
}*/
}
