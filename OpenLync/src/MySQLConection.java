
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * http://www.devmedia.com.br/criando-uma-conexao-java-mysql-server/16753	
 * http://alvinalexander.com/java/java-mysql-select-query-example
 */


public class MySQLConection {
	
	private static boolean status = false;
	private static String ipServidor = "192.168.229.1";

	public static String getIpServidor() {
		return ipServidor;
	}

	public static void setIpServidor(String ipServidor) {
		MySQLConection.ipServidor = ipServidor;
	}

	public static boolean getStatusMySQL() {
		return status;
	}
	
	public static java.sql.Connection getMySQLConnection() {
		
		Connection connection = null;
		try {
			// Carregando o JDBC Driver padrão
			String driverName = "com.mysql.jdbc.Driver";                        
			Class.forName(driverName);

			String url = "jdbc:mysql://"+ipServidor+"/OpenLync";
            connection = DriverManager.getConnection(url, "open@%", "123");

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
	
	public static boolean fecharConexaoMySQL() {
		
		try {
			getMySQLConnection().close();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
	public static java.sql.Connection getReiniciarMySQLConnection() {
		
		fecharConexaoMySQL();
		return getMySQLConnection();
	}
	
}
