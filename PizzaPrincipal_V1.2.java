import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JToolBar;
import javax.swing.JTextField;
import javax.swing.JScrollBar;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.border.CompoundBorder;
import javax.swing.JTextPane;

public class PizzaPrincipal {

	private JFrame frmControleDePedidos;
	private JTextField textNome;
	private JTextField textRua;
	private JTextField textBairro;
	private JTextField textTelefone;
	private JTextField textQtd;
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/devpizza";
	private String user = "root";
	private String password = "password for database";
	private int selecMenu = 0;
	private JTextField textIDpizza;
	private JTextField textSabor;
	private JTextField textTamanho;
	private JTextField textValor;
	private JTable table_1;
	private JTable table;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PizzaPrincipal window = new PizzaPrincipal();
					window.frmControleDePedidos.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public void Dropfield() {                             //Função para limpar os campos cliente.
		
		textNome.setText("");
		textRua.setText("");
		textTelefone.setText("");
		textBairro.setText("");
		textIDpizza.setText("");
		textQtd.setText("");
		textSabor.setText("");
		textTamanho.setText("");
		textValor.setText("");
		
	}
	
	public void editableField() {                         //Função para desabilitar a edição dos campos
		textNome.setEditable(false);
		textRua.setEditable(false);
		textBairro.setEditable(false);
		textTelefone.setEditable(false);
	}
	
	
	/*public String [][] PedidosEfetuados(){
		String [][] dados = new String[100][5];
				try {
					Class.forName(driver);
					String query = "SELECT * FROM PedidosEfetuados;";
					try {
						Connection con = DriverManager.getConnection(url, user, password);
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery(query);
						int i = 0;
						while(rs.next()) {
							for(int j = 0;j < 5; j++) {
								dados[i][j]=rs.getString(j+1);
							}
							i= i+1;
						}
						st.close();
						con.close();
						
					} catch (SQLException e) {
						System.out.println(e);
					}
					
				} catch (Exception e) {
					System.out.println(e);
				}
				return dados;
	}*/
	
	
	private String[] buscarTelefone(String valor)       //Conexão com o BD e busca por telefone
	{
		String[] localiza = null;
		try {
			Class.forName(driver);
			String query = "call devpizza.BuscaCliente('"+ valor +"');";
			try {
				Connection con = DriverManager.getConnection(url, user, password);
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(query);
				int numcol = rs.getMetaData().getColumnCount();
				localiza = new String [numcol];
				if (rs.next())
				{
					for (int i = 0; i < numcol; i++)
					{
						localiza[i] = rs.getString(i + 1).toString();
					}
				}				
				st.close();
				con.close();
				
			} catch (SQLException e) {
				System.out.println(e);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return localiza;
	}
	
	private String[] buscarCodSabor(int idsabor)       //Conexão com o BD e busca sabor
	{
		String[] localiza = null;
		try {
			Class.forName(driver);
			String query = "SELECT * FROM devpizza.cardapio where Cod_Sabor ="+idsabor+";";
			try {
				Connection con = DriverManager.getConnection(url, user, password);
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(query);
				int numcol = rs.getMetaData().getColumnCount();
				localiza = new String [numcol];
				if (rs.next())
				{
					for (int i = 0; i < numcol; i++)
					{
						localiza[i] = rs.getString(i + 1).toString();
					}
				}				
				st.close();
				con.close();
				
			} catch (SQLException e) {
				System.out.println(e);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return localiza;
	}
	
	private int deletar(int ID) {
		int retorno = 0;
		try {
			Class.forName(driver);
			String query = "call devpizza.DeletaCliente"+ID+";";
			try {
				Connection con = DriverManager.getConnection(url, user, password);
				Statement st = con.createStatement();
				retorno = st.executeUpdate(query);
				st.close();
				con.close();
				
			} catch (SQLException e) {
				System.out.println(e);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return retorno;
	}	
	private int incluindo(String nome, String telefone, String rua, String bairro) {
		int retorno = 0;
		try {
			Class.forName(driver);
			String query = "call devpizza.InserirCliente('"+nome+"','"+telefone+"','"+rua+"','"+bairro+"');";
			try {
				Connection con = DriverManager.getConnection(url, user, password);
				Statement st = con.createStatement();
				retorno = st.executeUpdate(query);
				st.close();
				con.close();
				
			} catch (SQLException e) {
				System.out.println(e);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return retorno;
	}
	
	private int NovoPedido(int codcliente, int codsabor, int qtd, Float totalpagar) {
		int retorno = 0;
		try {
			Class.forName(driver);
			String query = "call devpizza.InserirPedido("+codcliente+","+codsabor+","+qtd+","+totalpagar+","+codcliente+","+codsabor+");";
			try {
				Connection con = DriverManager.getConnection(url, user, password);
				Statement st = con.createStatement();
				retorno = st.executeUpdate(query);
				st.close();
				con.close();
				
			} catch (SQLException e) {
				System.out.println(e);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return retorno;
	}
	
	private int editando(int ID ,String nome, String telefone, String rua, String bairro) {
		int retorno = 0;
		try {
			Class.forName(driver);
			String query = "call devpizza.AtualizarCliente("+ID+",'"+nome+"','"+telefone+"','"+rua+"','"+bairro+"');";
			try {
				Connection con = DriverManager.getConnection(url, user, password);
				Statement st = con.createStatement();
				retorno = st.executeUpdate(query);
				st.close();
				con.close();
				
			} catch (SQLException e) {
				System.out.println(e);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return retorno;
	}
	
	public PizzaPrincipal() {
		initialize();
	}
	


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmControleDePedidos = new JFrame();
		frmControleDePedidos.setTitle("Controle de Pedidos");
		frmControleDePedidos.setBounds(0, 0, 1080, 620);
		frmControleDePedidos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmControleDePedidos.getContentPane().setLayout(null);
		
		JLabel lblTitulo = new JLabel("Developer\u00B4s da Pizza");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
		lblTitulo.setBounds(243, 0, 267, 31);
		frmControleDePedidos.getContentPane().add(lblTitulo);
		
		textNome = new JTextField();
		textNome.setEditable(false);
		textNome.setBounds(65, 53, 234, 20);
		frmControleDePedidos.getContentPane().add(textNome);
		textNome.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(10, 56, 70, 14);
		frmControleDePedidos.getContentPane().add(lblNome);
		
		JLabel lblRua = new JLabel("Rua:");
		lblRua.setBounds(329, 56, 70, 14);
		frmControleDePedidos.getContentPane().add(lblRua);
		
		JLabel lblBairro = new JLabel("Bairro:");
		lblBairro.setBounds(329, 90, 70, 14);
		frmControleDePedidos.getContentPane().add(lblBairro);
		
		textRua = new JTextField();
		textRua.setEditable(false);
		textRua.setColumns(10);
		textRua.setBounds(383, 52, 234, 20);
		frmControleDePedidos.getContentPane().add(textRua);
		
		textBairro = new JTextField();
		textBairro.setEditable(false);
		textBairro.setColumns(10);
		textBairro.setBounds(383, 86, 234, 20);
		frmControleDePedidos.getContentPane().add(textBairro);
		
		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setBounds(10, 90, 70, 14);
		frmControleDePedidos.getContentPane().add(lblTelefone);
		
		textTelefone = new JTextField();
		textTelefone.setEditable(false);
		textTelefone.setColumns(10);
		textTelefone.setBounds(75, 84, 174, 20);
		frmControleDePedidos.getContentPane().add(textTelefone);
		
		JLabel lblPedidosGrid = new JLabel("Pedidos:");
		lblPedidosGrid.setFont(new Font("Arial", Font.BOLD, 14));
		lblPedidosGrid.setBounds(10, 309, 70, 14);
		frmControleDePedidos.getContentPane().add(lblPedidosGrid);
		
		JLabel lblCodCliente = new JLabel("Cod_cliente:");
		lblCodCliente.setBounds(10, 24, 76, 14);
		frmControleDePedidos.getContentPane().add(lblCodCliente);
		
		JLabel lblShowCodClient = new JLabel("000");
		lblShowCodClient.setFont(new Font("Arial", Font.BOLD, 15));
		lblShowCodClient.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblShowCodClient.setBounds(86, 19, 50, 23);
		frmControleDePedidos.getContentPane().add(lblShowCodClient);
		
		JLabel lblQtd = new JLabel("Quantidade:");
		lblQtd.setBounds(10, 179, 76, 14);
		frmControleDePedidos.getContentPane().add(lblQtd);
		
		textQtd = new JTextField();
		textQtd.setEnabled(false);
		textQtd.setBounds(173, 176, 50, 20);
		frmControleDePedidos.getContentPane().add(textQtd);
		textQtd.setColumns(10);
		
		JButton btnConcluirCadastro = new JButton("Concluir");
		btnConcluirCadastro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch (selecMenu){
					case 1:
						incluindo(textNome.getText(), textTelefone.getText(), textRua.getText(), textBairro.getText());
						Dropfield();
						editableField();
						btnConcluirCadastro.setVisible(false);
						break;
					case 2:
						editando(Integer.parseInt(lblShowCodClient.getText()),textNome.getText(), textTelefone.getText(), textRua.getText(), textBairro.getText());
						Dropfield();
						editableField();
						btnConcluirCadastro.setVisible(false);
				}
				
			}


		});
		btnConcluirCadastro.setVisible(false);
		btnConcluirCadastro.setBounds(10, 112, 120, 31);
		frmControleDePedidos.getContentPane().add(btnConcluirCadastro);
		
		JButton btnAtualizarView = new JButton("Atualizar");
		btnAtualizarView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		btnAtualizarView.setBounds(80, 306, 89, 23);
		frmControleDePedidos.getContentPane().add(btnAtualizarView);
		
		
		
		
		JLabel lblNewLabel = new JLabel("Escolha o codigo do Sabor:");
		lblNewLabel.setBounds(10, 154, 159, 14);
		frmControleDePedidos.getContentPane().add(lblNewLabel);
		
		textIDpizza = new JTextField();
		textIDpizza.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					int idpizza = Integer.parseInt(textIDpizza.getText());

					String [] resultadoSabor = buscarCodSabor(idpizza);
					if (resultadoSabor[0] != null)
					{
						textIDpizza.setText(resultadoSabor[3]);
						textSabor.setText(resultadoSabor[4]);
						textTamanho.setText(resultadoSabor[1]);
						textValor.setText(resultadoSabor[2]);
						textQtd.setEnabled(true);
						textQtd.setEditable(true);
					}
					else
					{
						textIDpizza.setText("");
						JOptionPane.showMessageDialog(btnAtualizarView,"Nenhum sabor encontrado!");
					}

					//chamar função de pedido  aqui!!!!!
				}
				}
		
			
		});
		textIDpizza.setBounds(173, 151, 76, 20);
		frmControleDePedidos.getContentPane().add(textIDpizza);
		textIDpizza.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Total a Pagar:");
		lblNewLabel_1.setBounds(10, 204, 126, 14);
		frmControleDePedidos.getContentPane().add(lblNewLabel_1);
		
		JLabel lblValorTotal = new JLabel("");
		lblValorTotal.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblValorTotal.setBounds(174, 200, 46, 18);
		frmControleDePedidos.getContentPane().add(lblValorTotal);
		
		JLabel lblNewLabel_3 = new JLabel("Sabor:");
		lblNewLabel_3.setBounds(273, 154, 46, 14);
		frmControleDePedidos.getContentPane().add(lblNewLabel_3);
		
		textSabor = new JTextField();
		textSabor.setBounds(349, 151, 135, 20);
		frmControleDePedidos.getContentPane().add(textSabor);
		textSabor.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Tamanho:");
		lblNewLabel_4.setBounds(273, 179, 56, 14);
		frmControleDePedidos.getContentPane().add(lblNewLabel_4);
		
		textTamanho = new JTextField();
		textTamanho.setBounds(349, 176, 135, 20);
		frmControleDePedidos.getContentPane().add(textTamanho);
		textTamanho.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Valor:");
		lblNewLabel_5.setBounds(273, 204, 46, 14);
		frmControleDePedidos.getContentPane().add(lblNewLabel_5);
		
		textValor = new JTextField();
		textValor.setBounds(349, 201, 86, 20);
		frmControleDePedidos.getContentPane().add(textValor);
		textValor.setColumns(10);
		
		table_1 = new JTable();
		table_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
				{"Calabresa", "Pequena", "Cebola, Calabresa, Mussarela", "18,00"},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
			},
			new String[] {
				"Sabor", "Tamanho", "Ingredientes", "Valor"
			}
		));
		table_1.getColumnModel().getColumn(2).setPreferredWidth(194);
		table_1.setBounds(643, 56, 411, 307);
		frmControleDePedidos.getContentPane().add(table_1);
		
		JLabel lblNewLabel_2 = new JLabel("Card\u00E1pio:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_2.setBounds(792, 18, 89, 23);
		frmControleDePedidos.getContentPane().add(lblNewLabel_2);
		
		JButton btnPedidoConcluir = new JButton("Concluir Pedido.");
		btnPedidoConcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				float qtd = 0, valor = 0;
				float total = 0;
				qtd =  Float.parseFloat(textQtd.getText());
				valor = Float.parseFloat(textValor.getText());
				total = qtd * valor;
				String tot = Float.toString(total);
				lblValorTotal.setText(tot);
				NovoPedido(Integer.parseInt(lblShowCodClient.getText()), Integer.parseInt(textIDpizza.getText()),
						   Integer.parseInt(textQtd.getText()), total);
				Dropfield();
				lblValorTotal.setText("");
				JOptionPane.showMessageDialog(btnPedidoConcluir, "Pedido incluso com sucesso!");
			}
		});
		btnPedidoConcluir.setBounds(153, 225, 135, 23);
		frmControleDePedidos.getContentPane().add(btnPedidoConcluir);
		
		table = new JTable();
		table.setBounds(20, 334, 523, 208);
		frmControleDePedidos.getContentPane().add(table);
		//String[] column= {"Pedido","Nome","Sabor","quantidade","Total Pagar"};
		//String[][] busca = new String[100][5];
		//busca = PedidosEfetuados();
		//table.addRowSelectionInterval(100, 5);
		JMenuBar menuBar = new JMenuBar();
		frmControleDePedidos.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Arquivo");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Novo cliente");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textNome.setEditable(true);
				textRua.setEditable(true);
				textBairro.setEditable(true);
				textTelefone.setEditable(true);
				btnConcluirCadastro.setVisible(true);
				selecMenu = 1;
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Alterar Cliente");
		mntmNewMenuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selecMenu = 2;
				String telefone = null;
				telefone = JOptionPane.showInputDialog("Digite o telefone do cliente");
				String [] resultado = buscarTelefone(telefone);
				if (resultado[0] != null)
				{
					lblShowCodClient.setText(resultado[0]);
					textNome.setText(resultado[1]);
					textTelefone.setText(resultado[2]);
					textRua.setText(resultado[3]);
					textBairro.setText(resultado[4]);
					textNome.setEditable(true);
					textRua.setEditable(true);
					textBairro.setEditable(true);
					textTelefone.setEditable(true);
					btnConcluirCadastro.setVisible(true);
					
				}
				else
				{
					textNome.setText("");
					JOptionPane.showMessageDialog(mntmNewMenuItem_5, "Nenhum nome encontrado!");
				}
				
			}
		});
		mnNewMenu.add(mntmNewMenuItem_5);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Novo Pedido");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selecMenu = 3;
				String telefone = null;
				telefone = JOptionPane.showInputDialog("Digite o telefone do cliente");
				String [] resultado = buscarTelefone(telefone);
				if (resultado[0] != null)
				{
					lblShowCodClient.setText(resultado[0]);
					textNome.setText(resultado[1]);
					textTelefone.setText(resultado[2]);
					textRua.setText(resultado[3]);
					textBairro.setText(resultado[4]);
					textQtd.setEnabled(true);
					textQtd.setEditable(true);
				}
				else
				{
					textNome.setText("");
					JOptionPane.showMessageDialog(mntmNewMenuItem_1, "Nenhum nome encontrado!");
				}
				
				/*int idpizza = Integer.parseInt(textIDpizza.getText());

				String [] resultadoSabor = buscarCodSabor(idpizza);
				if (resultadoSabor[0] != null)
				{
					textIDpizza.setText(resultadoSabor[0]);
					textSabor.setText(resultadoSabor[1]);
					textTamanho.setText(resultadoSabor[2]);
					textValor.setText(resultadoSabor[3]);
					textQtd.setEnabled(true);
					textQtd.setEditable(true);
				}
				else
				{
					textIDpizza.setText("");
					JOptionPane.showMessageDialog(mntmNewMenuItem_1, "Nenhum sabor encontrado!");
				}*/

				//chamar função de pedido  aqui!!!!!
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenu mnNewMenu_1 = new JMenu("Manuten\u00E7\u00E3o");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_6 = new JMenuItem("Excluir Cliente");
		mntmNewMenuItem_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String telefone = null;
				telefone = JOptionPane.showInputDialog("Digite o telefone do cliente");
				String [] resultado = buscarTelefone(telefone);
				if (resultado[0] != null)
				{
					lblShowCodClient.setText(resultado[0]);
					textNome.setText(resultado[1]);
					textTelefone.setText(resultado[2]);
					textRua.setText(resultado[3]);
					textBairro.setText(resultado[4]);
					textQtd.setEnabled(true);
					textQtd.setEditable(true);
				}
				else
				{
					textNome.setText("");
					JOptionPane.showMessageDialog(mntmNewMenuItem_1, "Nenhum nome encontrado!");
				}
				
			}
			
		});
		mnNewMenu_1.add(mntmNewMenuItem_6);
		
		JMenuItem mntmNewMenuItem_7 = new JMenuItem("Excluir Pedido");
		mnNewMenu_1.add(mntmNewMenuItem_7);
 	}
}
