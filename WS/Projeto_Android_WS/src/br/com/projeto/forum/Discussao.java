package br.com.projeto.forum;

import java.util.Date;
import java.util.Vector;

public class Discussao {
	
    private long idDiscussao;
    private String titulo;
    private String descricao;
    private Date dataHora;
    private String dataHoraCustom;
    private String autor;
    private byte buscaEspecifica;
    private long contRespostas;
    private Vector<Resposta> listaRespostas;

    public long getIdDiscussao() {
        return idDiscussao;
    }

    public void setIdDiscussao(long idDiscussao) {
        this.idDiscussao = idDiscussao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }
    
    public String getDataHoraCustom() {
		return dataHoraCustom;
	}

	public void setDataHoraCustom(String dataHoraCustom) {
		this.dataHoraCustom = dataHoraCustom;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}
	
	public byte getBuscaEspecifica() {
		return buscaEspecifica;
	}

	public void setBuscaEspecifica(byte buscaEspecifica) {
		this.buscaEspecifica = buscaEspecifica;
	}

	public long getContRespostas() {
        return contRespostas;
    }

    public void setContRespostas(long contRespostas) {
        this.contRespostas = contRespostas;
    }

    public Vector<Resposta> getListaRespostas() {
        return listaRespostas;
    }

    public void setListaRespostas(Vector<Resposta> listaRespostas) {
        this.listaRespostas = listaRespostas;
    }
}
