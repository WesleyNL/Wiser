package br.com.projeto.idioma;

import java.awt.EventQueue;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Toolkit;

public class TelaPrincipal {

	private static JFrame frmManutencaoIdiomas;
	private static JLabel lblIdioma;
	private static JComboBox cmbIdioma;
	private static JLabel lblIdiomaTraducao;
	private static JComboBox cmbIdiomaTraducao;
	private static JLabel lblFluencia;
	private static JComboBox cmbFluencia;
	private static JLabel lblFluenciaTraducao;
	private static JComboBox cmbFluenciaTraducao;
	
	private static ManutencaoDAO objManutencaoDAO;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal window = new TelaPrincipal();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TelaPrincipal() {
		try {
			initialize();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void initialize() throws SQLException {

		if(frmManutencaoIdiomas != null){
			frmManutencaoIdiomas.dispose();
		}
		
		objManutencaoDAO = new ManutencaoDAO();

		frmManutencaoIdiomas = new JFrame();
		frmManutencaoIdiomas.setResizable(false);
		frmManutencaoIdiomas.setIconImage(Toolkit.getDefaultToolkit().getImage(TelaPrincipal.class.getResource("/br/com/projeto/idioma/logo_wiser_2.png")));
		frmManutencaoIdiomas.setTitle("Wiser - Manuten\u00E7\u00E3o Idiomas");
		frmManutencaoIdiomas.setBounds(100, 100, 426, 256);
		frmManutencaoIdiomas.setLocationRelativeTo(null);
		frmManutencaoIdiomas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmManutencaoIdiomas.getContentPane().setLayout(null);
		frmManutencaoIdiomas.setVisible(true);
		objManutencaoDAO.setTipoUtilizacao(Manutencao.USAR_IDIOMA);
		objManutencaoDAO.pesquisarIdiomaFluencia();
		
		lblIdioma = new JLabel("Idioma");
		lblIdioma.setBounds(12, 13, 56, 16);
		frmManutencaoIdiomas.getContentPane().add(lblIdioma);
		
		cmbIdioma = new JComboBox(new DefaultComboBoxModel(objManutencaoDAO.getListaIdioma().toArray()));
		cmbIdioma.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				for(int i=0; i<cmbIdiomaTraducao.getItemCount(); i++){
					if(((Manutencao)cmbIdiomaTraducao.getItemAt(i)).getCodigo() == ((Manutencao)cmbIdioma.getSelectedItem()).getCodigo()){
						cmbIdiomaTraducao.setSelectedItem(cmbIdiomaTraducao.getItemAt(i));
						break;
					}
				}
			}
		});
		cmbIdioma.setBounds(80, 10, 116, 22);
		frmManutencaoIdiomas.getContentPane().add(cmbIdioma);

		lblIdiomaTraducao = new JLabel("Em ingl\u00EAs");
		lblIdiomaTraducao.setBounds(12, 48, 56, 16);
		frmManutencaoIdiomas.getContentPane().add(lblIdiomaTraducao);
		
		cmbIdiomaTraducao = new JComboBox(new DefaultComboBoxModel(objManutencaoDAO.getListaIdiomaTraducao().toArray()));
		cmbIdiomaTraducao.setBounds(80, 45, 116, 22);
		cmbIdiomaTraducao.setEnabled(false);
		frmManutencaoIdiomas.getContentPane().add(cmbIdiomaTraducao);
		
		objManutencaoDAO.setTipoUtilizacao(Manutencao.USAR_FLUENCIA);
		objManutencaoDAO.pesquisarIdiomaFluencia();
		
		lblFluencia = new JLabel("Flu\u00EAncia");
		lblFluencia.setBounds(212, 13, 56, 16);
		frmManutencaoIdiomas.getContentPane().add(lblFluencia);
		
		cmbFluencia = new JComboBox(new DefaultComboBoxModel(objManutencaoDAO.getListaFluencia().toArray()));
		cmbFluencia.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				for(int i=0; i<cmbFluenciaTraducao.getItemCount(); i++){
					if(((Manutencao)cmbFluenciaTraducao.getItemAt(i)).getCodigo() == ((Manutencao)cmbFluencia.getSelectedItem()).getCodigo()){
						cmbFluenciaTraducao.setSelectedItem(cmbFluenciaTraducao.getItemAt(i));
						break;
					}
				}
			}
		});
		cmbFluencia.setBounds(280, 10, 116, 22);
		frmManutencaoIdiomas.getContentPane().add(cmbFluencia);
		
		JLabel lblFluenciaTraducao = new JLabel("Em ingl\u00EAs");
		lblFluenciaTraducao.setBounds(212, 48, 56, 16);
		frmManutencaoIdiomas.getContentPane().add(lblFluenciaTraducao);
		
		cmbFluenciaTraducao = new JComboBox(new DefaultComboBoxModel(objManutencaoDAO.getListaFluenciaTraducao().toArray()));
		cmbFluenciaTraducao.setBounds(280, 45, 116, 22);
		cmbFluenciaTraducao.setEnabled(false);
		frmManutencaoIdiomas.getContentPane().add(cmbFluenciaTraducao);
		
		JButton btnEditarIdioma = new JButton("Editar Idioma");
		btnEditarIdioma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Manutencao objManutencao = (Manutencao) cmbIdioma.getSelectedItem();
				
				TelaManutencao tela = new TelaManutencao(Manutencao.OPERACAO_EDICAO, Manutencao.USAR_IDIOMA);
				tela.setCodigo(objManutencao.getCodigo());
				tela.setDescricao(objManutencao.getDescricao());
				tela.setTraducao(objManutencao.getDescricaoTraducao());
				tela.recarregarEdicao();
			}
		});
		btnEditarIdioma.setBounds(53, 133, 129, 25);
		btnEditarIdioma.setEnabled(cmbIdioma.getItemCount() != 0);
		frmManutencaoIdiomas.getContentPane().add(btnEditarIdioma);
		
		JButton btnIncluirIdioma = new JButton("Novo Idioma");
		btnIncluirIdioma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new TelaManutencao(Manutencao.OPERACAO_INCLUSAO, Manutencao.USAR_IDIOMA);
			}
		});
		btnIncluirIdioma.setBounds(53, 95, 129, 25);
		frmManutencaoIdiomas.getContentPane().add(btnIncluirIdioma);
		
		JButton btnExcluirIdioma = new JButton("Excluir Idioma");
		btnExcluirIdioma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(confirmar()){
					Manutencao objManutencao = (Manutencao) cmbIdioma.getSelectedItem();
					objManutencaoDAO.setCodigo(objManutencao.getCodigo());
					objManutencaoDAO.setTipoUtilizacao(Manutencao.USAR_IDIOMA);
					
					if(objManutencaoDAO.excluir()){
						JOptionPane.showMessageDialog(null, "Idioma excluído com sucesso!");
					}else{
						JOptionPane.showMessageDialog(null, "Não foi possível excluir!");
					}
					
					try {
						initialize();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnExcluirIdioma.setBounds(53, 171, 129, 25);
		btnExcluirIdioma.setEnabled(cmbIdioma.getItemCount() != 0);
		frmManutencaoIdiomas.getContentPane().add(btnExcluirIdioma);
		
		JButton btnIncluirFluencia = new JButton("Nova Flu\u00EAncia");
		btnIncluirFluencia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TelaManutencao(Manutencao.OPERACAO_INCLUSAO, Manutencao.USAR_FLUENCIA);
			}
		});
		btnIncluirFluencia.setBounds(230, 95, 129, 25);
		frmManutencaoIdiomas.getContentPane().add(btnIncluirFluencia);
		
		JButton btnEditarFluencia = new JButton("Editar Flu\u00EAncia");
		btnEditarFluencia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Manutencao objManutencao = (Manutencao) cmbFluencia.getSelectedItem();
				
				TelaManutencao tela = new TelaManutencao(Manutencao.OPERACAO_EDICAO, Manutencao.USAR_FLUENCIA);
				tela.setCodigo(objManutencao.getCodigo());
				tela.setDescricao(objManutencao.getDescricao());
				tela.setTraducao(objManutencao.getDescricaoTraducao());
				tela.recarregarEdicao();
			}
		});
		btnEditarFluencia.setBounds(230, 133, 129, 25);
		btnEditarFluencia.setEnabled(cmbFluencia.getItemCount() != 0);
		frmManutencaoIdiomas.getContentPane().add(btnEditarFluencia);
		
		JButton btnExcluirFluencia = new JButton("Excluir Flu\u00EAncia");
		btnExcluirFluencia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(confirmar()){
					Manutencao objManutencao = (Manutencao) cmbFluencia.getSelectedItem();
					objManutencaoDAO.setCodigo(objManutencao.getCodigo());
					objManutencaoDAO.setTipoUtilizacao(Manutencao.USAR_FLUENCIA);
					
					if(objManutencaoDAO.excluir()){
						JOptionPane.showMessageDialog(null, "Fluência excluída com sucesso!");
					}else{
						JOptionPane.showMessageDialog(null, "Não foi possível excluir!");
					}
					
					try {
						initialize();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnExcluirFluencia.setBounds(230, 171, 129, 25);
		btnExcluirFluencia.setEnabled(cmbFluencia.getItemCount() != 0);
		frmManutencaoIdiomas.getContentPane().add(btnExcluirFluencia);
	}
	
	private static boolean confirmar() {
		int opcao = JOptionPane.showOptionDialog(null, "Confirmar exclusão?", "Excluir", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		
		if(opcao == JOptionPane.CANCEL_OPTION || opcao == JOptionPane.CLOSED_OPTION){
			return false;
		}else{
			return true;
		}
	}
}