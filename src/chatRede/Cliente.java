package chatRede;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Cliente {

	public static void main(String[] args) {
	try {
		final Socket cliente = new Socket("127.0.0.1",8888);
		/*metodo para enviar e receber msg ao mesmo tempo*/
		/*metodo para ler mgm*/
		 new Thread() {
		        @Override
		        public void run() {
		        try {
					BufferedReader leitor = new BufferedReader(new InputStreamReader(cliente.getInputStream())) ;
					while(true){
						String menssagem = leitor.readLine();
						if(menssagem == null || menssagem.isEmpty())
							continue;
						System.out.println(menssagem);
						/*System.out.println("o servidor disse: "+ menssagem);*/
					}
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null,"erro ao ler mgm");
					e.printStackTrace();
				}     
		      }
		    }.start();
		    /*metodo para enviar mgs*/
			PrintWriter escrever = new PrintWriter(cliente.getOutputStream(),true);
			BufferedReader leitor02 = new BufferedReader(new InputStreamReader(System.in));
			String mensagemEnv="";
			while(true){
				mensagemEnv = leitor02.readLine();
				if(mensagemEnv == null ||mensagemEnv.length()==0){
					continue;
				}
				escrever.println(mensagemEnv);
				/*para fecha o cliente*/
				if(mensagemEnv.equalsIgnoreCase("sair")){
					System.exit(0);
				}
				/*fecha clie*/
			}
			
			/*while(true){
				String messagemEnv = leitor02.readLine();
				escrever.println(messagemEnv);
				}
				*/
			
		/*fim do metodo*/
	} catch (IOException e) {
		JOptionPane.showMessageDialog(null,"erro ao conectar no servidor");
		e.printStackTrace();
	} 

	}

	

}
