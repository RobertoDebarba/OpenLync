
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * http://www.devmedia.com.br/criando-uma-conexao-java-mysql-server/16753	
 * http://alvinalexander.com/java/java-mysql-select-query-example
 */

/*
// da um select
Statement st = dbConnection.createStatement();
ResultSet rs = st.executeQuery("select * from usuarios;");

while (rs.next()) {
    int codigo = rs.getInt("codigo_usuario");
    String nome = rs.getString("nome_usuario");
    String usuario = rs.getString("login_usuario");
    String senha = rs.getString("senha_usuario");
    
    System.out.println(codigo+" "+nome+" "+usuario+" "+senha);
}*/
public class MySQLConection {
	
	private static String status = "Não houve tentativa de conexão!";

	public static String getStatusMySQL() {
		return status;
	}
	
	public static java.sql.Connection getMySQLConnection() {
		
		Connection connection = null;
		try {
			// Carregando o JDBC Driver padrão
			String driverName = "com.mysql.jdbc.Driver";                        
			Class.forName(driverName);

			String url = "jdbc:mysql://192.168.152.1/OpenLync";
            connection = DriverManager.getConnection(url, "open@%", "123");

            // Testar Conexão
            if (connection != null) {
            	status = "MySQL concetado!";
            } else {
            	status = "MySQL não conectado!";
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
