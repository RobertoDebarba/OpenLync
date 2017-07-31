package openlync.forms;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import openlync.principal.Usuario;
import openlync.principal.UsuarioDAO;
import openlync.utilidades.MySQLConection;

import org.imgscalr.Scalr;

public class FormUsuario extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;

	private int estado = 0; // Define modo da tela / 0 = neutro / 1 = Novo / 2 =
							// Editando
	private BufferedImage fotoPerfil = null;
	private UsuarioDAO dao;

	/** Creates new form FormUsuario */
	public FormUsuario() {

		initComponents();

		setLocationRelativeTo(null);
		setResizable(false);

		dao = new UsuarioDAO(true);
	}

	// -----------------------------------------------------------------------------------------------------
	
	/**
	 * Atualiza dados da Grid e Combo
	 */
	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
		
		dao.carregarListaUsuario();
		carregarGridUsuarios();
		atualizarComboCargos();
		carregarCampos(0);

		editOFF();

		// Personaliza Grid
		tableUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableUsuarios.addRowSelectionInterval(0, 0);
		tableUsuarios.setRowHeight(25);
	}
	
	/**
	 * Desabilitar Edição
	 */
	private void editOFF() {

		editNome.setFocusable(false);
		comboCargo.setEnabled(false);
		EditLogin.setFocusable(false);
		editSenha.setFocusable(false);

		tableUsuarios.setEnabled(true);
		BtnNovoCargo.setEnabled(false);
		BtnFoto.setEnabled(false);
		checkAdmin.setEnabled(false);

		BtnNovo.setEnabled(true);
		BtnEditar.setEnabled(true);
		BtnApagar.setEnabled(true);
		BtnSalvar.setEnabled(false);
		BtnCancelar.setEnabled(false);
		BtnVoltar.setEnabled(true);
	}

	/**
	 * Habilita edição
	 */
	private void editON() {

		editNome.setFocusable(true);
		comboCargo.setEnabled(true);
		EditLogin.setFocusable(true);
		editSenha.setFocusable(true);

		tableUsuarios.setEnabled(false);
		BtnNovoCargo.setEnabled(true);
		BtnFoto.setEnabled(true);
		checkAdmin.setEnabled(true);

		BtnNovo.setEnabled(false);
		BtnEditar.setEnabled(false);
		BtnApagar.setEnabled(false);
		BtnSalvar.setEnabled(true);
		BtnCancelar.setEnabled(true);
		BtnVoltar.setEnabled(false);
	}

	/**
	 * Abre tela de seleção de arquivo (para seleção de Foto de perfil)
	 */
	private File escolherArquivo() {
		File arquivo = null;
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Escolha o arquivo...");
		fc.setDialogType(JFileChooser.OPEN_DIALOG);
		fc.setApproveButtonText("OK");
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		// Adiciona filtro para apenas selecionar imagens
		fc.setAcceptAllFileFilterUsed(false);
		fc.setFileFilter(new FileNameExtensionFilter("Image files", ImageIO
				.getReaderFileSuffixes()));

		fc.setMultiSelectionEnabled(false);
		int resultado = fc.showOpenDialog(fc);
		if (resultado == JFileChooser.CANCEL_OPTION) {
			return null;
		}

		arquivo = fc.getSelectedFile();

		return arquivo;
	}

	/**
	 * Select todos registros do tb_usuario e carrega para grid
	 */
	private void carregarGridUsuarios() {

		// DEfine colunas da GRID ---------------------------------------

		String[] colunasGridUsuarios = new String[] { "Código", "Nome",
				"Cargo", "Login", "Senha" };

		// Carrega lista para Array -------------------------------------
		int i = 0;

		String[][] listaGridusuarios = new String[dao.listaUsuarios.size()][5];
		while (i < dao.listaUsuarios.size()) {
			listaGridusuarios[i][0] = dao.listaUsuarios.get(i).getCodigo() + "";
			listaGridusuarios[i][1] = dao.listaUsuarios.get(i).getNome();
			listaGridusuarios[i][2] = dao.listaUsuarios.get(i).getCargo();
			listaGridusuarios[i][3] = dao.listaUsuarios.get(i).getLogin();
			listaGridusuarios[i][4] = dao.listaUsuarios.get(i).getSenha();
			i++;
		}

		// Cria grig preenchendo os campos e colunas com listaGrigUsuarios e
		// colunasGridUsuarios
		// Cria novo modelo defaul sobreescrevendo o modo isCellEditable para
		// desabilitar a edição
		tableUsuarios.setModel(new DefaultTableModel(listaGridusuarios,
				colunasGridUsuarios) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int col) {
				return false;
			}
		});

		tableUsuarios.getColumn("Código").setPreferredWidth(40);
		tableUsuarios.getColumn("Nome").setPreferredWidth(150);
		tableUsuarios.getColumn("Cargo").setPreferredWidth(110);
	}

	/**
	 * Carrega campos com base na listaUsuarios
	 */
	private void carregarCampos(int numeroRegistro) {

		editCodigo.setText(dao.listaUsuarios.get(numeroRegistro).getCodigo()
				+ "");
		editNome.setText(dao.listaUsuarios.get(numeroRegistro).getNome());
		EditLogin.setText(dao.listaUsuarios.get(numeroRegistro).getLogin());
		editSenha.setText(dao.listaUsuarios.get(numeroRegistro).getSenha());

		checkAdmin.setSelected(dao.listaUsuarios.get(numeroRegistro).isAdmin());

		comboCargo.setSelectedItem(dao.listaUsuarios.get(numeroRegistro)
				.getCargo());

		// Seta foto
		// Se houver foto -> seta Icon do label
		if (dao.listaUsuarios.get(numeroRegistro).getFoto() != null) {
			BufferedImage imgMaior = Scalr.resize(
					dao.listaUsuarios.get(numeroRegistro).getFoto(), 70, 70);
			labelFoto.setIcon(new ImageIcon(imgMaior));

			fotoPerfil = dao.listaUsuarios.get(numeroRegistro).getFoto();

			// Se não houver foto -> limpa Icon do label
		} else {
			labelFoto.setIcon(null);
			fotoPerfil = null;
		}
	}

	/**
	 * Atualiza itens do comboBox Cargos
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void atualizarComboCargos() {

		String[] listaCargos = null;
		try {
			Statement st = MySQLConection.getStatementMySQL();

			String SQL = "SELECT desc_cargo FROM tb_cargos;";

			ResultSet rs = st.executeQuery(SQL);

			// Conta o numero de registros retornados
			rs.last();
			int quantidadeRegistros = rs.getRow();
			rs.beforeFirst();

			listaCargos = new String[quantidadeRegistros];
			int i = 0;
			while (rs.next()) {

				listaCargos[i] = rs.getString("desc_cargo");
				i++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		comboCargo.setModel(new javax.swing.DefaultComboBoxModel(listaCargos));
	}

	// -------------------------------------------------------------------------------------------------

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		BtnNovo = new javax.swing.JButton();
		BtnEditar = new javax.swing.JButton();
		BtnApagar = new javax.swing.JButton();
		BtnSalvar = new javax.swing.JButton();
		BtnCancelar = new javax.swing.JButton();
		BtnVoltar = new javax.swing.JButton();
		jPanel2 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		jLabel6 = new javax.swing.JLabel();
		editCodigo = new javax.swing.JTextField();
		editNome = new javax.swing.JTextField();
		EditLogin = new javax.swing.JTextField();
		editSenha = new javax.swing.JTextField();
		scroolTable = new javax.swing.JScrollPane();
		tableUsuarios = new javax.swing.JTable();
		comboCargo = new javax.swing.JComboBox();
		labelFoto = new javax.swing.JLabel();
		BtnNovoCargo = new javax.swing.JButton();
		jLabel8 = new javax.swing.JLabel();
		checkAdmin = new javax.swing.JCheckBox();
		BtnFoto = new javax.swing.JButton();

		setTitle("OpenLync | Usu\u00e1rios");

		BtnNovo.setText("Novo");
		BtnNovo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				BtnNovoActionPerformed(evt);
			}
		});

		BtnEditar.setText("Editar");
		BtnEditar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				BtnEditarActionPerformed(evt);
			}
		});

		BtnApagar.setText("Apagar");
		BtnApagar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				BtnApagarActionPerformed(evt);
			}
		});

		BtnSalvar.setText("Salvar");
		BtnSalvar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				BtnSalvarActionPerformed(evt);
			}
		});

		BtnCancelar.setText("Cancelar");
		BtnCancelar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				BtnCancelarActionPerformed(evt);
			}
		});

		BtnVoltar.setText("Voltar");
		BtnVoltar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				BtnVoltarActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(
				jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout
				.setHorizontalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																BtnNovo,
																javax.swing.GroupLayout.Alignment.TRAILING,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																105,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																BtnEditar,
																javax.swing.GroupLayout.Alignment.TRAILING,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																105,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																BtnApagar,
																javax.swing.GroupLayout.Alignment.TRAILING,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																105,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																BtnSalvar,
																javax.swing.GroupLayout.Alignment.TRAILING,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																105,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																BtnCancelar,
																javax.swing.GroupLayout.Alignment.TRAILING,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																105,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																BtnVoltar,
																javax.swing.GroupLayout.Alignment.TRAILING,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																105,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap()));
		jPanel1Layout
				.setVerticalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												BtnNovo,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												40,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												BtnEditar,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												40,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												BtnApagar,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												40,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												BtnSalvar,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												40,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												BtnCancelar,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												40,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												200, Short.MAX_VALUE)
										.addComponent(
												BtnVoltar,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												40,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap()));

		jPanel2.setLayout(null);

		jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 30));
		jLabel1.setText("Gerenciamento de Usu\u00e1rios");
		jPanel2.add(jLabel1);
		jLabel1.setBounds(20, 10, 390, 40);

		jLabel2.setText("C\u00f3digo:");
		jPanel2.add(jLabel2);
		jLabel2.setBounds(20, 75, 70, 18);

		jLabel3.setText("Nome:");
		jPanel2.add(jLabel3);
		jLabel3.setBounds(20, 105, 70, 18);

		jLabel4.setText("Cargo:");
		jPanel2.add(jLabel4);
		jLabel4.setBounds(20, 135, 70, 18);

		jLabel5.setText("Login:");
		jPanel2.add(jLabel5);
		jLabel5.setBounds(20, 165, 70, 18);

		jLabel6.setText("Senha:");
		jPanel2.add(jLabel6);
		jLabel6.setBounds(20, 195, 70, 18);

		editCodigo.setFocusable(false);
		jPanel2.add(editCodigo);
		editCodigo.setBounds(100, 70, 170, 25);

		jPanel2.add(editNome);
		editNome.setBounds(100, 100, 420, 25);

		EditLogin.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				EditLoginFocusLost(evt);
			}
		});
		jPanel2.add(EditLogin);
		EditLogin.setBounds(100, 160, 420, 25);

		jPanel2.add(editSenha);
		editSenha.setBounds(100, 190, 420, 25);

		tableUsuarios.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null },
						{ null, null, null, null }, { null, null, null, null },
						{ null, null, null, null } }, new String[] { "Title 1",
						"Title 2", "Title 3", "Title 4" }));
		tableUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tableUsuariosMouseClicked(evt);
			}
		});

		tableUsuarios.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				tableUsuariosKeyReleased(evt);
			}
		});
		scroolTable.setViewportView(tableUsuarios);

		jPanel2.add(scroolTable);
		scroolTable.setBounds(20, 260, 500, 220);

		comboCargo.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
				"Item 1", "Item 2", "Item 3", "Item 4" }));
		jPanel2.add(comboCargo);
		comboCargo.setBounds(100, 130, 340, 27);
		jPanel2.add(labelFoto);
		labelFoto.setBounds(440, 15, 70, 70);

		BtnNovoCargo.setText("Novo");
		BtnNovoCargo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				BtnNovoCargoActionPerformed(evt);
			}
		});
		jPanel2.add(BtnNovoCargo);
		BtnNovoCargo.setBounds(450, 130, 70, 25);

		jLabel8.setText("Foto:");
		jPanel2.add(jLabel8);
		jLabel8.setBounds(20, 225, 36, 18);

		checkAdmin.setText("Administrador");
		jPanel2.add(checkAdmin);
		checkAdmin.setBounds(390, 220, 130, 26);

		BtnFoto.setText("Escolher");
		jPanel2.add(BtnFoto);
		BtnFoto.setBounds(100, 220, 100, 25);
		BtnFoto.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				BtnFotoActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addComponent(jPanel2,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										524, Short.MAX_VALUE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jPanel1,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
						488, Short.MAX_VALUE));

		pack();
	}// </editor-fold>
		// GEN-END:initComponents

	private void BtnFotoActionPerformed(java.awt.event.ActionEvent evt) {
		File foto = escolherArquivo();

		// Se o arquivo não for null (se usuario não cancelou a escolha)
		if (foto != null) {
			try {
				fotoPerfil = ImageIO.read(foto); // Carrega foto para
													// BufferedImage
			} catch (IOException e) {
				e.printStackTrace();
			}
			;

			if ((fotoPerfil.getHeight() == 57) && (fotoPerfil.getWidth() == 57)) {

				BufferedImage imgMaior = Scalr.resize(fotoPerfil, 70, 70);
				labelFoto.setIcon(new ImageIcon(imgMaior));

			} else {
				JOptionPane.showMessageDialog(null,
						"O tamanho da imagem deve ser 57 x 57px!",
						"Imagem inválida", 1);
			}
			;
		}
	}

	private void BtnNovoCargoActionPerformed(java.awt.event.ActionEvent evt) {
		FormInicial.frmCargos.setVisible(true);
	}

	private void tableUsuariosKeyReleased(java.awt.event.KeyEvent evt) {
		if ((evt.getKeyCode() == KeyEvent.VK_UP)
				|| (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
			if (estado == 0) {
				carregarCampos(tableUsuarios.getSelectedRow());
			}
		}
	}

	private void EditLoginFocusLost(java.awt.event.FocusEvent evt) { // Sair do
																		// campo
																		// Login

		// Se estiver inserindo um novo registro
		if (estado == 1) {

			try {
				if (!dao.verificarDispLogin(EditLogin.getText())) { // Se estiver
									
					JOptionPane.showMessageDialog(null,
							"Usuário já cadastrado!", "Usuário Inválido", 1);
					EditLogin.setForeground(new Color(210, 0, 0));
					EditLogin.requestFocus();// disponivel
					
				} else {
					EditLogin.setForeground(new Color(0, 0, 0));
				}
				;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			;
			// Se estiver editando um registro existente
		} else if (estado == 2) { // Editar
			// Se o codigo digitado for diferente do original -> executar
			// verificação
			if (!EditLogin.getText().equals(
					dao.listaUsuarios.get(tableUsuarios.getSelectedRow())
							.getLogin())) {
				try {
					if (!dao.verificarDispLogin(EditLogin.getText())) { // Se
																		// estiver
																		// disponivel
						JOptionPane
						.showMessageDialog(null,
								"Usuário já cadastrado!",
								"Usuário Inválido", 1);
						EditLogin.setForeground(new Color(210, 0, 0));
						EditLogin.requestFocus();
					} else {
						EditLogin.setForeground(new Color(0, 0, 0));
					}
					;
				} catch (SQLException e) {
					e.printStackTrace();
				}
				;
			} else {
				// Se não verificou reseta cor para garantir ações anteriores
				EditLogin.setForeground(new Color(0, 0, 0));
			}
		}
	}

	private void BtnEditarActionPerformed(java.awt.event.ActionEvent evt) { // Btn
																			// Editar
		editON();
		estado = 2; // editando
	}

	private void BtnCancelarActionPerformed(java.awt.event.ActionEvent evt) { // Btn
																				// Cancelar

		carregarCampos(tableUsuarios.getSelectedRow());
		editOFF();
		estado = 0; // neutro
	}

	private void BtnApagarActionPerformed(java.awt.event.ActionEvent evt) { // Btn
																			// Apagar
		if (JOptionPane.showConfirmDialog(null, "Apagar resgitro?",
				"Apagar registro", 2) == 0) {// 0 = OK

			dao.apagar(dao.listaUsuarios.get(tableUsuarios.getSelectedRow()));
			dao.listaUsuarios.remove(tableUsuarios.getSelectedRow());
			carregarGridUsuarios();
			tableUsuarios.addRowSelectionInterval(0, 0);
			carregarCampos(0);

		}
	}

	private void BtnSalvarActionPerformed(java.awt.event.ActionEvent evt) { // Btn
																			// Salvar

		if (estado == 1) { // Novo
			// Adquire novo codigo e coloca no edit
			int codigo = dao.getNovoCodigo();
			editCodigo.setText(codigo + "");

			// Cria novo usuario e preenche seus atributos
			Usuario usuario = new Usuario();

			usuario.setCodigo(codigo);
			usuario.setNome(editNome.getText());
			usuario.setCargo(comboCargo.getSelectedItem() + "");
			usuario.setLogin(EditLogin.getText());
			usuario.setSenha(editSenha.getText());
			usuario.setAdmin(checkAdmin.isSelected());
			usuario.setFoto(fotoPerfil);

			// Adiciona usuario à lista
			dao.listaUsuarios.add(usuario);

			// Chama comando SQL
			dao.adicionar(usuario);

			// Desabilita Edição
			editOFF();

			// Atualiza Grid
			carregarGridUsuarios();

			// Seta posição da seleção para incio
			tableUsuarios.addRowSelectionInterval(0, 0);
			carregarCampos(0);

			estado = 0; // neutro

		} else if (estado == 2) { // Editar

			int registroSelecionado = tableUsuarios.getSelectedRow();

			dao.listaUsuarios.get(tableUsuarios.getSelectedRow()).setNome(
					editNome.getText());
			dao.listaUsuarios.get(tableUsuarios.getSelectedRow()).setCargo(
					comboCargo.getSelectedItem() + "");
			dao.listaUsuarios.get(tableUsuarios.getSelectedRow()).setLogin(
					EditLogin.getText());
			dao.listaUsuarios.get(tableUsuarios.getSelectedRow()).setSenha(
					editSenha.getText());
			dao.listaUsuarios.get(tableUsuarios.getSelectedRow()).setAdmin(
					checkAdmin.isSelected());
			dao.listaUsuarios.get(tableUsuarios.getSelectedRow()).setFoto(
					fotoPerfil);

			dao.editar(dao.listaUsuarios.get(tableUsuarios.getSelectedRow()));

			carregarGridUsuarios();

			editOFF();

			// Seta posição da seleção para incio
			tableUsuarios.addRowSelectionInterval(registroSelecionado,
					registroSelecionado);
			carregarCampos(registroSelecionado);

			estado = 0; // neutro
		}
	}

	private void BtnNovoActionPerformed(java.awt.event.ActionEvent evt) { // Btn
																			// Novo
		editON();
		editCodigo.setText("");
		editNome.setText("");
		EditLogin.setText("");
		editSenha.setText("");
		checkAdmin.setSelected(false);
		labelFoto.setIcon(null);
		fotoPerfil = null;
		estado = 1;

		editNome.requestFocus();
	}

	private void tableUsuariosMouseClicked(java.awt.event.MouseEvent evt) { // Click
																			// GRID
		if (estado == 0) {
			carregarCampos(tableUsuarios.getSelectedRow());
		}
	}

	private void BtnVoltarActionPerformed(java.awt.event.ActionEvent evt) { // Btn
																			// Voltar
		dispose();
	}

	// GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JButton BtnApagar;
	private javax.swing.JButton BtnCancelar;
	private javax.swing.JButton BtnEditar;
	private javax.swing.JButton BtnFoto;
	private javax.swing.JButton BtnNovo;
	private javax.swing.JButton BtnNovoCargo;
	private javax.swing.JButton BtnSalvar;
	private javax.swing.JButton BtnVoltar;
	private javax.swing.JTextField EditLogin;
	private javax.swing.JCheckBox checkAdmin;
	@SuppressWarnings("rawtypes")
	private javax.swing.JComboBox comboCargo;
	private javax.swing.JTextField editCodigo;
	private javax.swing.JTextField editNome;
	private javax.swing.JTextField editSenha;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JLabel labelFoto;
	private javax.swing.JScrollPane scroolTable;
	private javax.swing.JTable tableUsuarios;
	// End of variables declaration//GEN-END:variables

}