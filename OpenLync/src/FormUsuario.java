import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.imgscalr.Scalr;

public class FormUsuario extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;

	// Editavel
	private Connection conexao = MySQLConection.getMySQLConnection();
	private List<Usuarios> listaUsuarios;
	private int estado = 0; //Define modo da tela / 0 = neutro / 1 = Novo / 2 = Editando
	BufferedImage fotoPerfil = null;

	/** Creates new form FormUsuario */
	public FormUsuario() {

		initComponents();

		//Carregar informações iniciais
		carregarListaUsuario();
		carregarGridUsuarios();
		atualizarComboCargos();
		carregarCampos(0);

		editOFF();

		//Personaliza Grid
		tableUsuarios.addRowSelectionInterval(0, 0);
		tableUsuarios.setRowHeight(25);
		tableUsuarios.getColumn("Código").setPreferredWidth(40);
		tableUsuarios.getColumn("Nome").setPreferredWidth(150);
		tableUsuarios.getColumn("Cargo").setPreferredWidth(110);
		
		//TODO verificar apagar e editar
	}

	//-----------------------------------------------------------------------------------------------------

	/*
	 * Desabilitar Edição
	 */
	private void editOFF() {

		editNome.setFocusable(false);
		comboCargo.setEnabled(false);
		EditLogin.setFocusable(false);
		editSenha.setFocusable(false);

		tableUsuarios.setEnabled(true);
		BtnFoto.setEnabled(false);

		BtnNovo.setEnabled(true);
		BtnEditar.setEnabled(true);
		BtnApagar.setEnabled(true);
		BtnSalvar.setEnabled(false);
		BtnCancelar.setEnabled(false);
		BtnVoltar.setEnabled(true);
	}

	/*
	 * Habilita edição
	 */
	private void editON() {

		editNome.setFocusable(true);
		comboCargo.setEnabled(true);
		EditLogin.setFocusable(true);
		editSenha.setFocusable(true);

		tableUsuarios.setEnabled(false);
		BtnFoto.setEnabled(true);

		BtnNovo.setEnabled(false);
		BtnEditar.setEnabled(false);
		BtnApagar.setEnabled(false);
		BtnSalvar.setEnabled(true);
		BtnCancelar.setEnabled(true);
		BtnVoltar.setEnabled(false);
	}

	/*
	 * Abre tela de seleção de arquivo (para seleção de Foto de perfil)
	 */
	private File escolherArquivo() {
		File arquivo = null;
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Escolha o arquivo...");
		fc.setDialogType(JFileChooser.OPEN_DIALOG);
		fc.setApproveButtonText("OK");
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		//Adiciona filtro para apenas selecionar imagens
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

	/*
	 * Carrega objetos com todos os registros de tb_usuarios
	 */
	private void carregarListaUsuario() {
		Criptografia cript = new Criptografia();

		//Carrega dados dos usuarios para uma lista
		//Lista do resultado
		listaUsuarios = new ArrayList<Usuarios>();
		try {
			java.sql.Statement st = conexao.createStatement();

			String SQL = "SELECT codigo_usuario, nome_usuario, login_usuario, senha_usuario, foto_usuario, tb_cargos.desc_cargo"
					+ " FROM tb_usuarios, tb_cargos"
					+ " WHERE tb_cargos.codigo_cargo = tb_usuarios.codigo_cargo;";

			ResultSet rs = st.executeQuery(SQL);

			while (rs.next()) {
				Usuarios usuario = new Usuarios();

				usuario.setCodigo(rs.getInt("codigo_usuario"));
				usuario.setNome(rs.getString("nome_usuario"));
				usuario.setCargo(rs.getString("tb_cargos.desc_cargo"));
				usuario.setLogin(cript.descriptografarMensagem(rs
						.getString("login_usuario")));
				usuario.setSenha(cript.descriptografarMensagem(rs
						.getString("senha_usuario")));
				Blob blobImage = rs.getBlob("foto_usuario");
				try {
					if (blobImage != null) {
						usuario.setFoto(ImageIO.read(blobImage.getBinaryStream()));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				listaUsuarios.add(usuario);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Select todos registros do tb_usuario e carrega para grid
	 */
	private void carregarGridUsuarios() {

		//DEfine colunas da GRID ---------------------------------------

		String[] colunasGridUsuarios = new String[] { "Código", "Nome",
				"Cargo", "Login", "Senha" };

		//Carrega lista para Array -------------------------------------
		int i = 0;

		String[][] listaGridusuarios = new String[listaUsuarios.size()][5];
		while (i < listaUsuarios.size()) {
			listaGridusuarios[i][0] = listaUsuarios.get(i).getCodigo() + "";
			listaGridusuarios[i][1] = listaUsuarios.get(i).getNome();
			listaGridusuarios[i][2] = listaUsuarios.get(i).getCargo();
			listaGridusuarios[i][3] = listaUsuarios.get(i).getLogin();
			listaGridusuarios[i][4] = listaUsuarios.get(i).getSenha();
			i++;
		}

		//Cria grig preenchendo os campos e colunas com listaGrigUsuarios e colunasGridUsuarios
		//Cria novo modelo defaul sobreescrevendo o modo isCellEditable para desabilitar a edição
		tableUsuarios.setModel(new DefaultTableModel(listaGridusuarios,
				colunasGridUsuarios) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int col) {
				return false;
			}
		});
	}

	/*
	 * Carrega campos com base na listaUsuarios
	 */
	private void carregarCampos(int numeroRegistro) {

		editCodigo.setText(listaUsuarios.get(numeroRegistro).getCodigo() + "");
		editNome.setText(listaUsuarios.get(numeroRegistro).getNome());
		EditLogin.setText(listaUsuarios.get(numeroRegistro).getLogin());
		editSenha.setText(listaUsuarios.get(numeroRegistro).getSenha());

		comboCargo.setSelectedItem(listaUsuarios.get(numeroRegistro).getCargo());
		
		//Seta foto
		//Se houver foto -> seta Icon do label
		if (listaUsuarios.get(numeroRegistro).getFoto() != null) {
			BufferedImage imgMaior = Scalr.resize(listaUsuarios.get(numeroRegistro).getFoto(), 70, 70);
			labelFoto.setIcon(new ImageIcon(imgMaior));
		//Se não houver foto -> limpa Icon do label
		} else {
			labelFoto.setIcon(null);
		}
	}

	/*
	 * Atualiza itens do comboBox Cargos
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void atualizarComboCargos() {

		String[] listaCargos = null;
		try {
			Statement st = conexao.createStatement();

			String SQL = "SELECT desc_cargo FROM tb_cargos;";

			ResultSet rs = st.executeQuery(SQL);

			//Conta o numero de registros retornados
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

	//-------------------------------------------------------------------------------------------------

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
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
		jLabel7 = new javax.swing.JLabel();
		editFoto = new javax.swing.JTextField();
		labelFoto = new javax.swing.JLabel();
		BtnFoto = new javax.swing.JButton();

		setTitle("OpenLync | Usu\u00e1rios");

		BtnNovo.setText("Novo");
		BtnNovo.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				BtnNovoMouseClicked(evt);
			}
		});
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
		BtnApagar.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				BtnApagarMouseClicked(evt);
			}
		});
		BtnApagar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				BtnApagarActionPerformed(evt);
			}
		});

		BtnSalvar.setText("Salvar");
		BtnSalvar.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				BtnSalvarMouseClicked(evt);
			}
		});
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
		BtnVoltar.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				BtnVoltarMouseClicked(evt);
			}
		});
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
		editCodigo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editCodigoActionPerformed(evt);
			}
		});
		jPanel2.add(editCodigo);
		editCodigo.setBounds(100, 70, 170, 25);

		editNome.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editNomeActionPerformed(evt);
			}
		});
		jPanel2.add(editNome);
		editNome.setBounds(100, 100, 420, 25);

		EditLogin.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				EditLoginActionPerformed(evt);
			}
		});
		EditLogin.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				EditLoginFocusLost(evt);
			}
		});
		jPanel2.add(EditLogin);
		EditLogin.setBounds(100, 160, 420, 25);

		editSenha.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editSenhaActionPerformed(evt);
			}
		});
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
		scroolTable.setViewportView(tableUsuarios);

		jPanel2.add(scroolTable);
		scroolTable.setBounds(20, 260, 500, 220);

		comboCargo.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
				"Item 1", "Item 2", "Item 3", "Item 4" }));
		jPanel2.add(comboCargo);
		comboCargo.setBounds(100, 130, 420, 27);

		jLabel7.setText("Foto:");
		jPanel2.add(jLabel7);
		jLabel7.setBounds(20, 225, 36, 18);

		editFoto.setFocusable(false);
		jPanel2.add(editFoto);
		editFoto.setBounds(100, 220, 370, 25);
		jPanel2.add(labelFoto);
		labelFoto.setBounds(440, 15, 70, 70);

		BtnFoto.setText("...");
		BtnFoto.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				BtnFotoMouseClicked(evt);
			}
		});
		BtnFoto.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				BtnFotoActionPerformed(evt);
			}
		});
		jPanel2.add(BtnFoto);
		BtnFoto.setBounds(475, 220, 43, 25);

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
	//GEN-END:initComponents

	private void EditLoginFocusLost(java.awt.event.FocusEvent evt) {	//Sair do campo Login
		UsuariosDAO dao = new UsuariosDAO();
		
		//Se estiver inserindo um novo registro
		if (estado == 1) {			
			
			try {
				if (dao.verificarDispLogin(EditLogin.getText())) {	//Se estiver disponivel 
					EditLogin.setForeground(new Color(0, 0, 0));
				} else {
					JOptionPane.showMessageDialog(null, "Usuário já cadastrado!", "Usuário Inválido", 1);
					EditLogin.setForeground(new Color(210, 0, 0));
					EditLogin.requestFocus();
				};
			} catch (SQLException e) {
				e.printStackTrace();
			};
		//Se estiver editando um registro existente
		} else if (estado == 2) {	//Editar
			//Se o codigo digitado for diferente do original -> executar verificação
			if (!EditLogin.getText().equals(listaUsuarios.get(tableUsuarios.getSelectedRow()).getLogin())) {
				try {
					if (dao.verificarDispLogin(EditLogin.getText())) {	//Se estiver disponivel 
						EditLogin.setForeground(new Color(0, 0, 0));
					} else {
						JOptionPane.showMessageDialog(null, "Usuário já cadastrado!", "Usuário Inválido", 1);
						EditLogin.setForeground(new Color(210, 0, 0));
						EditLogin.requestFocus();
					};
				} catch (SQLException e) {
					e.printStackTrace();
				};
			} else {
				//Se não verificou reseta cor para garantir ações anteriores
				EditLogin.setForeground(new Color(0, 0, 0));
			}
		}
	}

	private void BtnEditarActionPerformed(java.awt.event.ActionEvent evt) { //Btn Editar
		editON();
		estado = 2; //editando
	}

	private void BtnCancelarActionPerformed(java.awt.event.ActionEvent evt) { //Btn Cancelar

		carregarCampos(tableUsuarios.getSelectedRow());
		editOFF();
		estado = 0; //neutro
	}

	private void BtnApagarMouseClicked(java.awt.event.MouseEvent evt) {
		// TODO add your handling code here:
	}

	private void BtnApagarActionPerformed(java.awt.event.ActionEvent evt) { //Btn Apagar
		if (JOptionPane.showConfirmDialog(null, "Apagar resgitro?",
				"Apagar registro", 2) == 0) {//0 = OK
			UsuariosDAO dao = new UsuariosDAO();

			dao.apagar(listaUsuarios.get(tableUsuarios.getSelectedRow()));
			listaUsuarios.remove(tableUsuarios.getSelectedColumn());
			carregarGridUsuarios();
			carregarCampos(0);
			
		}
	}

	private void BtnSalvarActionPerformed(java.awt.event.ActionEvent evt) { //Btn Salvar
		UsuariosDAO dao = new UsuariosDAO();

		if (estado == 1) { //Novo
			//Adquire novo codigo e coloca no edit
			int codigo = dao.getNovoCodigo();
			editCodigo.setText(codigo + "");

			//Cria novo usuario e preenche seus atributos
			Usuarios usuario = new Usuarios();

			usuario.setCodigo(codigo);
			usuario.setNome(editNome.getText());
			usuario.setCargo(comboCargo.getSelectedItem() + "");
			usuario.setLogin(EditLogin.getText());
			usuario.setSenha(editSenha.getText());
			usuario.setFoto(fotoPerfil);

			//Adiciona usuario à lista
			listaUsuarios.add(usuario);

			//Chama comando SQL
			dao.adicionar(usuario);

			//Desabilita Edição
			editOFF();

			//Atualiza Grid
			carregarGridUsuarios();

			estado = 0; //neutro

		} else if (estado == 2) { //Editar

			listaUsuarios.get(Integer.parseInt(editCodigo.getText())).setNome(
					editNome.getText());
			listaUsuarios.get(Integer.parseInt(editCodigo.getText())).setCargo(
					comboCargo.getSelectedItem() + "");
			listaUsuarios.get(Integer.parseInt(editCodigo.getText())).setLogin(
					EditLogin.getText());
			listaUsuarios.get(Integer.parseInt(editCodigo.getText())).setSenha(
					editSenha.getText());
			listaUsuarios.get(Integer.parseInt(editCodigo.getText())).setFoto(
					fotoPerfil);

			dao.editar(listaUsuarios.get(Integer.parseInt(editCodigo.getText())));

			carregarGridUsuarios();

			editOFF();

			estado = 0; //neutro
		}
	}

	private void BtnFotoActionPerformed(java.awt.event.ActionEvent evt) { //Btn escolher foto
		File foto = escolherArquivo();

		//Se o arquivo não for null (se usuario não cancelou a escolha)
		if (foto != null) {
			try {
				fotoPerfil = ImageIO.read(foto); //Carrega foto para BufferedImage
			} catch (IOException e) {
				e.printStackTrace();
			}
			;

			if ((fotoPerfil.getHeight() == 57) && (fotoPerfil.getWidth() == 57)) {

				editFoto.setText(foto.getAbsolutePath()); //Localização

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

	private void BtnNovoActionPerformed(java.awt.event.ActionEvent evt) { //Btn Novo
		editON();
		editCodigo.setText("");
		editNome.setText("");
		EditLogin.setText("");
		editSenha.setText("");
		editFoto.setText("");
		labelFoto.setIcon(null);
		estado = 1;
	}

	private void BtnFotoMouseClicked(java.awt.event.MouseEvent evt) {

	}

	private void BtnSalvarMouseClicked(java.awt.event.MouseEvent evt) {

	}

	private void BtnNovoMouseClicked(java.awt.event.MouseEvent evt) {

	}

	private void tableUsuariosMouseClicked(java.awt.event.MouseEvent evt) { //Click GRID
		if (estado == 0) {
			carregarCampos(tableUsuarios.getSelectedRow());
		}
	}

	private void BtnVoltarMouseClicked(java.awt.event.MouseEvent evt) {

	}

	private void BtnVoltarActionPerformed(java.awt.event.ActionEvent evt) { //Btn Voltar
		dispose();
	}

	private void editNomeActionPerformed(java.awt.event.ActionEvent evt) {

	}

	void editSenhaActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	void EditLoginActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	void editCodigoActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JButton BtnApagar;
	private javax.swing.JButton BtnCancelar;
	private javax.swing.JButton BtnEditar;
	private javax.swing.JButton BtnFoto;
	private javax.swing.JButton BtnNovo;
	private javax.swing.JButton BtnSalvar;
	private javax.swing.JButton BtnVoltar;
	private javax.swing.JTextField EditLogin;
	private javax.swing.JComboBox comboCargo;
	private javax.swing.JTextField editCodigo;
	private javax.swing.JTextField editFoto;
	private javax.swing.JTextField editNome;
	private javax.swing.JTextField editSenha;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JLabel labelFoto;
	private javax.swing.JScrollPane scroolTable;
	private javax.swing.JTable tableUsuarios;
	// End of variables declaration//GEN-END:variables

}