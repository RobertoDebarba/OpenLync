package openlync.utilidades;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

import javax.swing.JOptionPane;

import openlync.principal.Configuracao;

/*
 * http://www.devmedia.com.br/criando-uma-conexao-java-mysql-server/16753	
 * http://alvinalexander.com/java/java-mysql-select-query-example
 */

public class MySQLConection {

	private static boolean status = false;
	private static Connection conexao = null;
	
	/**
	 * Retorna o status da conexão com o MySQL
	 * @return
	 */
	public static boolean getStatusMySQL() {
		return status;
	}

	/**
	 * Retorna novo Statement para execução de comandos SQL
	 * @return
	 * @throws SQLException
	 */
	public static Statement getStatementMySQL() throws SQLException {
		return conexao.createStatement();
	}
	
	/**
	 * Retorna novo PreparedStatement do SQL informado
	 * @param SQL
	 * @return
	 * @throws SQLException
	 */
	public static PreparedStatement getPreparedStatementMySQL(String SQL) throws SQLException {
		return conexao.prepareStatement(SQL);
	}

	/**
	 * Abre a conexão unica com o Banco de Dados
	 */
	public static void abrirConexaoMySQL() {

		conexao = getMySQLConnection();
		
		if (conexao == null) {
			JOptionPane.showMessageDialog(null, "Falha ao conectar ao Banco de Dados!", "Erro", 1);
		}
	}

	/**
	 * Fecha a conexão unica com o Banco de Dados
	 * @return
	 */
	public static boolean fecharConexaoMySQL() {

		status = false;
		try {
			getMySQLConnection().close();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * Reinicia a conexao com o Banco de Dados
	 */
	public static void reiniciarConexaoMySQL() {

		fecharConexaoMySQL();
		conexao = getMySQLConnection();
	}

	/**
	 * Verifica se conexão com Banco de Dados é possivel
	 */
	public static boolean verificarConexaoMySQL() {
		Connection conexao = MySQLConection.getMySQLConnection();

		if (conexao != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Conecta ao Banco de Dados e retorna uma conexão
	 * @return
	 */
	private static java.sql.Connection getMySQLConnection() {

		Connection connection = null;
		try {
			// Carregando o JDBC Driver padrão
			String driverName = "com.mysql.jdbc.Driver";
			Class.forName(driverName);

			String url = "jdbc:mysql://" + Configuracao.getIpServidorDB() + "/OpenLync";
			connection = DriverManager.getConnection(url, Configuracao.getUserDB(), Configuracao.getPassDB());

			// Testar Conexão
			if (connection != null) {
				status = true;
			} else {
				status = false;
			}

			return connection;

		} catch (ClassNotFoundException e) {
			System.out.println("O driver expecificado nao foi encontrado.");
			return null;
		} catch (SQLException e) {
			System.out.println("Nao foi possivel conectar ao Banco de Dados.");
			return null;
		}
	}
}
