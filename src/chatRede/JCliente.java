package chatRede;

import java.awt.Color;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class JCliente extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("rawtypes")
	public JList ListUser;
	public static JButton  LimparVisor;
	private static PrintWriter escrever;
	private BufferedReader leitor;
	private TextArea areaVisor;
	private TextArea areaEditor;
	@SuppressWarnings("rawtypes")
	private JList areaList;
	  String[] usuarios =new String[]{"-Vazio-"};
	   
	
	
	public void Tela(){
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("CHAT  - Desenvolvido Por Aldair Cerdeira \u00a9 2018");
		setSize(800,500);
		setLocationRelativeTo(null);
		setResizable(false);// para nao ser redimencionado 
		setVisible(false);
		setLayout(null);
		getContentPane().setBackground(Color.lightGray);// cor de fundo
	}
	
	@SuppressWarnings("rawtypes")
	public void AreaTexto(){
		Font Ft1 = new Font("Sans Serif",Font.BOLD,20);	

		areaVisor = new TextArea();
	    areaVisor.setLocation(50,50);
	    areaVisor.setSize(500,300);
	    areaVisor.setFont(Ft1);
	    areaVisor.setEditable(false);
	    areaVisor.setBackground(Color.white);
	
	    getContentPane().add(areaVisor);
	    
	    areaEditor = new TextArea();
	    areaEditor.setLocation(50,380);
	    areaEditor.setSize(500,70);
	    areaEditor.setText("DIGITE SUA MENSAGEM AQUI");
	    areaEditor.setFont(Ft1);
	    areaEditor.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent arg0) {areaEditor.setText("");}
			});
	    areaEditor.setBackground(Color.white);
	    getContentPane().add(areaEditor);
	    
	    areaList =new JList();
	    areaList.setLocation(580,50);
	    areaList.setSize(180,300);
	    areaList.setBackground(Color.white);
	    getContentPane().add(areaList);
	    }
  
	/*private void Butoes() {
	
		
	  LimparVisor = new JButton("LIMPAR VISOR");
	  LimparVisor.setLocation(405,10);
	  LimparVisor.setSize(145,30);
	  LimparVisor.addActionListener(new ActionListener()
		{public void actionPerformed(ActionEvent arg0)
			{
		areaVisor.setText("");
	
			}});
	  getContentPane().add(LimparVisor);
		
	}*/
	
	@SuppressWarnings("unchecked")
	private void PrencerListar(String[] usuarios) {
		 /*lista dos usuarios*/
	  
	     @SuppressWarnings("rawtypes")
		DefaultListModel ModeloLista = new DefaultListModel();
	     areaList.setModel(ModeloLista);
	     for(String usuario: usuarios){
	    	 ModeloLista.addElement(usuario);
	    } ;
		
	}	
	/*AÇÃO DOS EVENTOS*/
	private void Eventos() {
	  areaEditor.addKeyListener(new KeyListener() {
		  @Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {
			
			}
		
			@Override
			public void keyPressed(KeyEvent e) {
//				areaEditor.getText();
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
						/*---------------*/
						if(areaVisor.getText().isEmpty()){
							return;
						}
						Object usuario = areaList.getSelectedValue();
						if(usuario != null){
							/*ADCIONADO A MSG NO VISOR*/
							areaVisor.append("Eu: "+areaEditor.getText()+"\n");
							/*ecreve para servi*/
							escrever.println(Constantes.mensag+usuario);
							escrever.println(areaEditor.getText());
							areaEditor.setText("");/*limpar*/
							e.consume();// limpsr
						}else{/*para fecha o cliente*/
							if(areaVisor.getText().equalsIgnoreCase(Constantes.Sair)){
								System.exit(0);
							}/*fecha clie*/
							JOptionPane.showMessageDialog(JCliente.this, "Selecione o Usuario");// null ou Jcliente.this
							
						return;
						}		
					}
			}
		});
	       
	   
	}

	public void Main_cliente(String[] args) {
	
		/*Socket cliente;*/
		try {
		@SuppressWarnings("resource")
		final Socket cliente = new Socket("127.0.0.1",8888);
			 escrever = new PrintWriter(cliente.getOutputStream(),true); 
			leitor = new BufferedReader(new InputStreamReader(cliente.getInputStream())) ;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"erro ao conectar no servidor");
			e.printStackTrace();
		}
	}
	
	private void IniLeitor() {
		/*metodo para ler mgm*/
		   try{
		        	while(true){
						String menssagem = leitor.readLine();
						if(menssagem == null || menssagem.isEmpty())
							continue;
						/* parte da verificação da lista| recebe o texto*/
						if(menssagem.equals(Constantes.ListaDeUser)){
							String[] usuarios = leitor.readLine().split(",");
							PrencerListar(usuarios);		
						}
						/*fim da v lista*/
						
						/*ação para o usuario por seu nome*/
						else if(menssagem.equals(Constantes.Nome_login )){
							String LOGIN = JOptionPane.showInputDialog("INFORME SEU NOME");
								escrever.println(LOGIN);
							}
							/*fim da ação do nome*/
						else if(menssagem.equals(Constantes.LoginNegado )){
							JOptionPane.showMessageDialog(JCliente.this,"Login Negado");;	
						}
						else if(menssagem.equals(Constantes.LoginAceito)){
							AtuaLista();
							System.out.println("testlogin");
						}
						
						else{
						areaVisor.append(menssagem+"\n");
						/*System.out.println("o servidor disse: "+ menssagem);*/
					}
		        	}
		   } catch (Exception e) {
				JOptionPane.showMessageDialog(null,"erro ao ler mensagem");
				e.printStackTrace();
		}			
	}//fim leitor
	
	private void AtuaLista() {
		escrever.println(Constantes.ListaDeUser);
		  
	}
	
	public JCliente(){
	  Tela();
	  AreaTexto();
	//  Butoes();
	  PrencerListar(usuarios);
	  setVisible(true);
	  
  }


	public static void main(String[] args) {
		//JCliente JanelaCliente = new JCliente();
		//new JCliente().Main_cliente(args);
		final JCliente Cliente = new JCliente();
		Cliente.Main_cliente(args);
		Cliente.Eventos();
		Cliente.IniLeitor();
		
	}
}
