
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class CargosDAO {

	private Connection conexao = MySQLConection.getMySQLConnection();
	public List<Cargos> listaCargos = new ArrayList<Cargos>();
	
	public List<Cargos> getListaCargos() {
		return listaCargos;
	}
	
	public CargosDAO() {
		carregarListaCargos();
	}
	
	/*
	 * Carrega objetos com todos os registros de tb_cargos
	 */
	public void carregarListaCargos() {

		listaCargos.removeAll(listaCargos);

		try {
			java.sql.Statement st = conexao.createStatement();

			String SQL = "SELECT codigo_cargo, desc_cargo" + " FROM tb_cargos;";

			ResultSet rs = st.executeQuery(SQL);

			while (rs.next()) {
				Cargos cargo = new Cargos();

				cargo.setCodigo(rs.getInt("codigo_cargo"));
				cargo.setDesc(rs.getString("desc_cargo"));

				listaCargos.add(cargo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Verificar se nome do cargo (desc) ja está cadastrado
	 */
	public boolean verificarDispDesc(String cargo) throws SQLException {
		
		if (!cargo.equals("")) {	//Se login não estiver vazio
			ResultSet rs;
	
			java.sql.Statement st = conexao.createStatement();
			
			String SQL = "SELECT 1 FROM tb_cargos" +
						 " WHERE desc_cargo = '"+cargo+"';";
			
			rs = st.executeQuery(SQL);
			
			if (rs.next()) {
				return false;
			} else {
				return true;
			}
		};
		
		return true;
	}
	
	/*
	 * Retorna proximo codigo utilizavel;
	 */
	public int getNovoCodigo() {
		
		int ultimoCodigo = 0;
		try {
			java.sql.Statement st = conexao.createStatement();
			
			String SQL = "SELECT codigo_cargo FROM tb_cargos;";
			
			ResultSet rs = st.executeQuery(SQL);
			
			while (rs.next()) {
				if (ultimoCodigo < rs.getInt("codigo_cargo")) {
					ultimoCodigo = rs.getInt("codigo_cargo");
				}
			}
			st.close();
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		return ultimoCodigo + 1;
	}
	
	public void adicionar(Cargos cargo) {
		
		try {
			java.sql.Statement st = conexao.createStatement();
							
			String SQL = "INSERT INTO tb_cargos (codigo_cargo,"+
											" desc_cargo)"+
					 " VALUES ("+cargo.getCodigo()+
					 			", '"+cargo.getDesc()+
					 			"');";
			
			st.execute(SQL);			
			st.close();
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void editar(Cargos cargo) {
		
		try {
			java.sql.Statement st = conexao.createStatement();
			
			String SQL = "UPDATE tb_cargos SET" +
						 " desc_cargo = '"+cargo.getDesc()+
						 "' WHERE codigo_cargo = "+ cargo.getCodigo() +";";
			st.execute(SQL);
			
			st.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean apagar(Cargos cargo) {
		
		boolean resultado = false;
		try {
			java.sql.Statement st = conexao.createStatement();
			ResultSet rs;
			String SQL;
			
			//Procura por usuarios cadastrados com esse cargo
			SQL = "SELECT 1 FROM tb_usuarios WHERE codigo_cargo = "+cargo.getCodigo()+";";
			rs = st.executeQuery(SQL);
			
			//Se encontrou nenhum usuario com esse cargo
			if (rs.next()) {
				JOptionPane.showMessageDialog(null, "Você não pode apagar esse cargo. Existem usuários cadastrados nesse registro.", "Erro ao apagar", 1);
				resultado = false;
				
			//Se não encontrou --> apaga registro
			} else {
				
				SQL = "DELETE FROM tb_cargos WHERE codigo_cargo = "+cargo.getCodigo()+";";
				st.execute(SQL);
				resultado = true;
				
				st.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro inesperado ao apagar Cargo!", "Erro", 1);
			resultado = false;
		}	
		
		return resultado;
	}
}
