package chatRede;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


@SuppressWarnings("serial")
public class Servidor extends JFrame {
	
	private JButton Iniciar_Servidor;
	private JButton Fechar_Servidor;
	Font Ft1 = new Font("Sans Serif",Font.BOLD,16);
	private void tela() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("SERVIDOR - Desenvolvido Por Aldair Cerdeira \u00a9 2018");
		setSize(450,300);
		setLocationRelativeTo(null);
		setResizable(false);// para nao ser redimencionado 
		setVisible(false);
		setLayout(null);
		getContentPane().setBackground(Color.lightGray);// cor de fundo
		
	}
	private void Botoes() {
		this.Iniciar_Servidor = new JButton("INICIAR SERVIDOR");
		Iniciar_Servidor.setLocation(125,50);
		Iniciar_Servidor.setSize(200,50);
		Iniciar_Servidor.setFont(Ft1);
		Iniciar_Servidor.addActionListener(new ActionListener()
		{public void actionPerformed(ActionEvent arg0)
			{	
		   IniciarServer();
			}});
		getContentPane().add(Iniciar_Servidor);
		
	    Fechar_Servidor = new JButton("FECHAR SERVIDOR");
		Fechar_Servidor.setLocation(125,150);
		Fechar_Servidor.setSize(200,50);
		Fechar_Servidor.setFont(Ft1);
		Fechar_Servidor.addActionListener(new ActionListener()
		{public void actionPerformed(ActionEvent arg0)
		{	
		  
			}});
		getContentPane().add(Fechar_Servidor);
		
	}
	
	
	private void IniciarServer() {
		ServerSocket  ServerChat = null;
		
  try {
	 ServerChat = new ServerSocket(8888);/* precisa instanciar ao menos a porta a ser usada */
	 JOptionPane.showMessageDialog(null,"servidor iniciado");
	
	 
while(true){
	Socket cliente = ServerChat.accept();
	new GerenClientes(cliente);/*para ser criado novas conec d clientes*/
}

	 
} catch (IOException e) {
	JOptionPane.showMessageDialog(null,"erro ao iniciar o servidor, a porta deve está em uso");
	
	try {
		if(ServerChat != null)
		ServerChat.close();/*caso o servidor ja estiver aberto então sera fechado*/
	} catch (IOException e1) {}
	
	JOptionPane.showMessageDialog(null,"erro ao tentar fechar a porta");
	e.printStackTrace();
}
		
	}
	
	

	Servidor(){
		tela();
		Botoes();
		setVisible(true);
	}

}
