package br.com.projeto.idioma;

import java.util.LinkedList;

public class Manutencao {
	
	public static final byte OPERACAO_INCLUSAO = 0;
	public static final byte OPERACAO_EDICAO = 1;
	public static final byte OPERACAO_EXCLUSAO = 2;
	
	public static final byte USAR_IDIOMA = 0;
	public static final byte USAR_IDIOMA_EN = 1;
	public static final byte USAR_FLUENCIA = 2;
	public static final byte USAR_FLUENCIA_EN = 3;
	
	private int codigo;
	private String descricao;
	private String descricaoTraducao;
	
	private LinkedList<Manutencao> listaIdioma;
	private LinkedList<Manutencao> listaIdiomaTraducao;
	private LinkedList<Manutencao> listaFluencia;
	private LinkedList<Manutencao> listaFluenciaTraducao;
	
	private byte tipoOperacao;
	private byte tipoUtilizacao;
	
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricaoTraducao() {
		return descricaoTraducao;
	}

	public void setDescricaoTraducao(String descricaoTraducao) {
		this.descricaoTraducao = descricaoTraducao;
	}

	public LinkedList<Manutencao> getListaIdioma() {
		return listaIdioma;
	}

	public void setListaIdioma(LinkedList<Manutencao> listaIdioma) {
		this.listaIdioma = listaIdioma;
	}

	public LinkedList<Manutencao> getListaIdiomaTraducao() {
		return listaIdiomaTraducao;
	}

	public void setListaIdiomaTraducao(LinkedList<Manutencao> listaIdiomaTraducao) {
		this.listaIdiomaTraducao = listaIdiomaTraducao;
	}

	public LinkedList<Manutencao> getListaFluencia() {
		return listaFluencia;
	}

	public void setListaFluencia(LinkedList<Manutencao> listaFluencia) {
		this.listaFluencia = listaFluencia;
	}

	public LinkedList<Manutencao> getListaFluenciaTraducao() {
		return listaFluenciaTraducao;
	}

	public void setListaFluenciaTraducao(LinkedList<Manutencao> listaFluenciaTraducao) {
		this.listaFluenciaTraducao = listaFluenciaTraducao;
	}

	public byte getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(byte tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	public byte getTipoUtilizacao() {
		return tipoUtilizacao;
	}

	public void setTipoUtilizacao(byte tipoUtilizacao) {
		this.tipoUtilizacao = tipoUtilizacao;
	}

	@Override
	public String toString() {
		switch (getTipoUtilizacao()){
		case USAR_IDIOMA:
		case USAR_FLUENCIA:
			return getDescricao();
		case USAR_IDIOMA_EN:
		case USAR_FLUENCIA_EN:
			return getDescricaoTraducao();
		default:
			return "Não encontrado";
		}
	}
}