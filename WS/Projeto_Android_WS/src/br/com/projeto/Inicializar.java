package br.com.projeto;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import br.com.projeto.conexao.Conexao;
import br.com.projeto.utils.ThreadDesativarData;

public class Inicializar implements ServletContextListener{
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private Thread threadDD = null;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
		System.out.println(dateFormat.format(new Date()));

		Conexao.getInstance().fecharConexao();
		
		System.out.println("O servidor foi desligado.");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		System.out.println(dateFormat.format(new Date()));
		
		Sistema.PATH_CONFIG = arg0.getServletContext().getRealPath("WEB-INF\\classes\\br\\com\\projeto\\projeto.config.ini"); //Classes
		
		inicializarParametros();
		inicializarThreadDesativarData();
		
		System.out.println("O servidor foi iniciado.");
	}

	public void inicializarParametros(){
		Sistema objParametros = new Sistema();
		objParametros.carregar();
	}

	public void inicializarThreadDesativarData(){
		ThreadDesativarData thread = new ThreadDesativarData();
		threadDD = new Thread(thread);
		threadDD.start();
	}
}