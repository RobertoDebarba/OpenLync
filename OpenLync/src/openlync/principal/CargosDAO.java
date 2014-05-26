package openlync.principal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import openlync.utilidades.MySQLConection;

public class CargosDAO {

	public List<Cargos> listaCargos = new ArrayList<Cargos>();

	public List<Cargos> getListaCargos() {
		return listaCargos;
	}

	/**
	 * Pré carrega listaCargos
	 */
	public CargosDAO() {
		carregarListaCargos();
	}

	/**
	 * Carrega objetos com todos os registros de tb_cargos
	 */
	public void carregarListaCargos() {

		listaCargos.removeAll(listaCargos);

		try {
			Statement st = MySQLConection.getStatementMySQL();

			String SQL = "CALL sp_getCargos()";

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

	/**
	 * Verificar se nome do cargo (desc) ja está cadastrado
	 */
	public boolean verificarDispDesc(String cargo) throws SQLException {

		boolean resultado = false;
		if (!cargo.equals("")) { //Se cargo não estiver vazio
			Statement st = MySQLConection.getStatementMySQL();

			String SQL = "SELECT fc_verificarDispDesc('"+cargo+"');";

			ResultSet rs = st.executeQuery(SQL);
			rs.next();
			resultado = rs.getBoolean(1);
		}
		
		return resultado;
	}

	/**
	 * @return próximo codigo utilizavel
	 */
	public int getNovoCodigo() {

		int resultado = 0;
		try {
			Statement st = MySQLConection.getStatementMySQL();

			String SQL = "SELECT fc_getNovoCodigoCargo()";

			ResultSet rs = st.executeQuery(SQL);
			rs.next();
			resultado = rs.getInt(1);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultado;
	}

	/**
	 * Adiciona novo Cargo
	 * 
	 * @param cargo
	 */
	public void adicionar(Cargos cargo) {

		try {
			String SQL = "CALL sp_adicionarCargo(?, ?)";

			PreparedStatement pst = MySQLConection.getPreparedStatementMySQL(SQL);
			
			pst.setInt(1, cargo.getCodigo());
			pst.setString(2, cargo.getDesc());
			
			pst.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Edita cargo
	 * 
	 * @param cargo
	 */
	public void editar(Cargos cargo) {

		try {
			String SQL = "CALL sp_editarCargo(?, ?)";

			PreparedStatement pst = MySQLConection.getPreparedStatementMySQL(SQL);
			
			pst.setInt(1, cargo.getCodigo());
			pst.setString(2, cargo.getDesc());
			
			pst.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Remove cargo
	 * 
	 * @param cargo
	 * @return boolean com resultado da operação
	 */
	public boolean apagar(Cargos cargo) { //TODO

		boolean resultado = false;
		try {
			Statement st = MySQLConection.getStatementMySQL();
			ResultSet rs;
			String SQL;

			//Usar try
			// Procura por usuarios cadastrados com esse cargo
			SQL = "SELECT count(codigo_cargo) FROM tb_usuarios WHERE codigo_cargo = "
					+ cargo.getCodigo() + ";";
			rs = st.executeQuery(SQL);

			rs.next();
			
			//0 - Sim / 1 - Não
			if (JOptionPane.showConfirmDialog(null, "Há "+rs.getInt(1)+
						" usuário(s) cadastrado(s) com esse cargo."+
						" Deseja continuar a exclusão e alterar seus cargo para 'Nulo'?",
						"Alerta de exclusão", 0, 1) == 0) {
				
				SQL = "CALL sp_apagarCargo("+cargo.getCodigo()+")";
				st.execute(SQL);
				
				resultado = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Erro inesperado ao apagar Cargo!", "Erro", 1);
			resultado = false;
		}

		return resultado;
	}
}
