package br.com.projeto.idioma;

import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class TelaManutencao {

	private JFrame frame;
	private JTextField txtDescricao;
	private JTextField txtTraducao;

	private ManutencaoDAO objManutencaoDAO;

	public TelaManutencao(byte tipoOperacao, byte tipoUtilizacao) {
		objManutencaoDAO = new ManutencaoDAO();
		objManutencaoDAO.setTipoOperacao(tipoOperacao);
		objManutencaoDAO.setTipoUtilizacao(tipoUtilizacao);
		
		initialize();
	}

	private String getTitulo(){
		
		String titulo = "Wiser - ";
		
		switch (objManutencaoDAO.getTipoOperacao()){
		case Manutencao.OPERACAO_INCLUSAO:
			titulo += "Inclusão";
			break;
		case Manutencao.OPERACAO_EDICAO:
			titulo += "Edição";
			break;
		}
		
		switch (objManutencaoDAO.getTipoUtilizacao()){
		case Manutencao.USAR_IDIOMA:
			titulo += " de Idioma";
			break;
		case Manutencao.USAR_FLUENCIA:
			titulo += " de Fluência";
			break;
		}
		
		return titulo;
	}
	
	private void initialize() {
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(TelaManutencao.class.getResource("/br/com/projeto/idioma/logo_wiser_2.png")));
		frame.setBounds(100, 100, 426, 143);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		frame.setTitle(getTitulo());
		
		JLabel lblDescricao = new JLabel("Descri\u00E7\u00E3o");
		lblDescricao.setBounds(12, 13, 78, 16);
		frame.getContentPane().add(lblDescricao);
		
		txtDescricao = new JTextField();
		txtDescricao.setBounds(82, 10, 116, 22);
		frame.getContentPane().add(txtDescricao);
		txtDescricao.setColumns(10);
		
		JLabel lblTraducao = new JLabel("Tradu\u00E7\u00E3o");
		lblTraducao.setBounds(210, 13, 56, 16);
		frame.getContentPane().add(lblTraducao);
		
		txtTraducao = new JTextField();
		txtTraducao.setBounds(278, 10, 116, 22);
		frame.getContentPane().add(txtTraducao);
		txtTraducao.setColumns(10);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvar();
			}
		});
		btnSalvar.setBounds(154, 58, 97, 25);
		frame.getContentPane().add(btnSalvar);
	}
	
	public void setCodigo(int codigo){
		objManutencaoDAO.setCodigo(codigo);
	}
	
	public void setDescricao(String descricao){
		objManutencaoDAO.setDescricao(descricao);
	}
	
	public void setTraducao(String traducao){
		objManutencaoDAO.setDescricaoTraducao(traducao);
	}
	
	public void recarregarEdicao(){
		txtDescricao.setText(objManutencaoDAO.getDescricao());
		txtTraducao.setText(objManutencaoDAO.getDescricaoTraducao());
	}
	
	public void salvar(){
		
		if(txtDescricao.getText().trim().isEmpty() || txtTraducao.getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "Descrição e Tradução precisam estar preenchidos!");
			return;
		}
		
		objManutencaoDAO.setDescricao(txtDescricao.getText().trim());
		objManutencaoDAO.setDescricaoTraducao(txtTraducao.getText().trim());
		
		if(objManutencaoDAO.salvar()){
			JOptionPane.showMessageDialog(null, "Dados salvos com sucesso!");
			
			frame.dispose();
			
			try {
				TelaPrincipal.initialize();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			JOptionPane.showMessageDialog(null, "Não foi possível salvar!");
		}
	}
}