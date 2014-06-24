package openlync.forms;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import openlync.principal.Cargo;
import openlync.principal.CargoDAO;

public class FormCargo extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;

	//Editavel
	private int estado = 0; //Define modo da tela / 0 = neutro / 1 = Novo / 2 = Editando
	private CargoDAO dao;
	
	/** Creates new form FormCargos */
	public FormCargo() {
		initComponents();
		
		setLocationRelativeTo(null);
		setResizable(false);

		//Carregar informações iniciais
		dao = new CargoDAO();
		carregarGridCargos();
		carregarCampos(0);

		editOFF();

		//Personaliza Grid
		tableCargos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableCargos.addRowSelectionInterval(0, 0);
		tableCargos.setRowHeight(25);
	}

	/**
	 * Desabilitar Edição
	 */
	private void editOFF() {

		editDesc.setFocusable(false);

		tableCargos.setEnabled(true);

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

		editDesc.setFocusable(true);

		tableCargos.setEnabled(false);

		BtnNovo.setEnabled(false);
		BtnEditar.setEnabled(false);
		BtnApagar.setEnabled(false);
		BtnSalvar.setEnabled(true);
		BtnCancelar.setEnabled(true);
		BtnVoltar.setEnabled(false);
	}

	/**
	 * Cria Array<String> da listaCargos e carrega para grid
	 */
	private void carregarGridCargos() {

		//DEfine colunas da GRID ---------------------------------------

		String[] colunasGridCargos = new String[] { "Código", "Descrição" };

		//Carrega lista para Array -------------------------------------
		int i = 0;
		String[][] listaGridCargos = new String[dao.listaCargos.size()][2];
		while (i < dao.listaCargos.size()) {
			listaGridCargos[i][0] = dao.listaCargos.get(i).getCodigo() + "";
			listaGridCargos[i][1] = dao.listaCargos.get(i).getDesc();
			i++;
		}

		//Cria grig preenchendo os campos e colunas com listaGrigCargos e colunasGridCargos
		//Cria novo modelo defaul sobreescrevendo o modo isCellEditable para desabilitar a edição
		tableCargos.setModel(new DefaultTableModel(listaGridCargos,
				colunasGridCargos) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int col) {
				return false;
			}
		});

		tableCargos.getColumn("Código").setPreferredWidth(20);
		tableCargos.getColumn("Descrição").setPreferredWidth(200);
	}

	/**
	 * Carrega campos com base na listaUsuarios
	 */
	private void carregarCampos(int numeroRegistro) {

		editCodigo.setText(dao.listaCargos.get(numeroRegistro).getCodigo() + "");
		editDesc.setText(dao.listaCargos.get(numeroRegistro).getDesc());

	}

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
		editDesc = new javax.swing.JTextField();
		editCodigo = new javax.swing.JTextField();
		scroolTable = new javax.swing.JScrollPane();
		tableCargos = new javax.swing.JTable();

		setTitle("OpenLync | Cargos");
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosed(java.awt.event.WindowEvent evt) {
				formWindowClosed(evt);
			}
		});

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
												116, Short.MAX_VALUE)
										.addComponent(
												BtnVoltar,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												40,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap()));

		jPanel2.setLayout(null);

		jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 30));
		jLabel1.setText("Gerenciamento de Cargos");
		jPanel2.add(jLabel1);
		jLabel1.setBounds(20, 10, 390, 40);

		jLabel2.setText("C\u00f3digo:");
		jPanel2.add(jLabel2);
		jLabel2.setBounds(20, 75, 70, 18);

		jLabel3.setText("Descri\u00e7\u00e3o:");
		jPanel2.add(jLabel3);
		jLabel3.setBounds(20, 105, 80, 18);

		editDesc.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editDescActionPerformed(evt);
			}
		});
		editDesc.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				editDescFocusLost(evt);
			}
		});
		jPanel2.add(editDesc);
		editDesc.setBounds(100, 100, 350, 25);

		editCodigo.setFocusable(false);
		editCodigo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editCodigoActionPerformed(evt);
			}
		});
		jPanel2.add(editCodigo);
		editCodigo.setBounds(100, 70, 170, 25);

		tableCargos.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null },
						{ null, null, null, null }, { null, null, null, null },
						{ null, null, null, null } }, new String[] { "Title 1",
						"Title 2", "Title 3", "Title 4" }));
		tableCargos.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tableCargosMouseClicked(evt);
			}
		});
		tableCargos
				.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
					public void propertyChange(
							java.beans.PropertyChangeEvent evt) {
						tableCargosPropertyChange(evt);
					}
				});
		tableCargos.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				tableCargosKeyPressed(evt);
			}

			public void keyReleased(java.awt.event.KeyEvent evt) {
				tableCargosKeyReleased(evt);
			}
		});
		scroolTable.setViewportView(tableCargos);

		jPanel2.add(scroolTable);
		scroolTable.setBounds(20, 140, 430, 250);

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
										461, Short.MAX_VALUE)
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
						404, Short.MAX_VALUE));

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	private void formWindowClosed(java.awt.event.WindowEvent evt) {
		FormInicial.frmUsuarios.atualizarComboCargos();
	}

	private void tableCargosKeyReleased(java.awt.event.KeyEvent evt) {
		if ((evt.getKeyCode() == KeyEvent.VK_UP)
				|| (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
			if (estado == 0) {
				carregarCampos(tableCargos.getSelectedRow());
			}
		}
	}

	private void tableCargosKeyPressed(java.awt.event.KeyEvent evt) {

	}

	private void tableCargosPropertyChange(java.beans.PropertyChangeEvent evt) {

	}

	private void editDescFocusLost(java.awt.event.FocusEvent evt) {
		//Se estiver inserindo um novo registro
		if (estado == 1) {

			try {
				if (dao.verificarDispDesc(editDesc.getText())) { //Se estiver disponivel 
					editDesc.setForeground(new Color(0, 0, 0));
				} else {
					JOptionPane.showMessageDialog(null, "Cargo já cadastrado!",
							"Cargo Inválido", 1);
					editDesc.setForeground(new Color(210, 0, 0));
					editDesc.requestFocus();
				}
				;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			;
			//Se estiver editando um registro existente
		} else if (estado == 2) { //Editar
			//Se o codigo digitado for diferente do original -> executar verificação
			if (!editDesc.getText().equals(
					dao.listaCargos.get(tableCargos.getSelectedRow()).getDesc())) {
				try {
					if (dao.verificarDispDesc(editDesc.getText())) { //Se estiver disponivel 
						editDesc.setForeground(new Color(0, 0, 0));
					} else {
						JOptionPane.showMessageDialog(null,
								"Cargo já cadastrado!", "Cargo Inválido", 1);
						editDesc.setForeground(new Color(210, 0, 0));
						editDesc.requestFocus();
					}
					;
				} catch (SQLException e) {
					e.printStackTrace();
				}
				;
			} else {
				//Se não verificou reseta cor para garantir ações anteriores
				editDesc.setForeground(new Color(0, 0, 0));
			}
		}
	}

	private void editCodigoActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void editDescActionPerformed(java.awt.event.ActionEvent evt) {

	}

	private void BtnVoltarActionPerformed(java.awt.event.ActionEvent evt) {
		//Btn Voltar
		dispose();
	}

	private void BtnVoltarMouseClicked(java.awt.event.MouseEvent evt) {

	}

	private void BtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {
		carregarCampos(tableCargos.getSelectedRow());
		editOFF();
		estado = 0; //neutro
	}

	private void BtnSalvarActionPerformed(java.awt.event.ActionEvent evt) {
		if (estado == 1) { //Novo
			//Adquire novo codigo e coloca no edit
			int codigo = dao.getNovoCodigo();
			editCodigo.setText(codigo + "");

			//Cria novo usuario e preenche seus atributos
			Cargo cargo = new Cargo();

			cargo.setCodigo(codigo);
			cargo.setDesc(editDesc.getText());

			//Adiciona usuario à lista
			dao.listaCargos.add(cargo);

			//Chama comando SQL
			dao.adicionar(cargo);

			//Desabilita Edição
			editOFF();

			//Atualiza Grid
			carregarGridCargos();

			//Seta posição da seleção para incio
			tableCargos.addRowSelectionInterval(0, 0);
			carregarCampos(0);

			estado = 0; //neutro

		} else if (estado == 2) { //Editar

			int registroSelecionado = tableCargos.getSelectedRow();

			dao.listaCargos.get(tableCargos.getSelectedRow()).setDesc(
					editDesc.getText());

			dao.editar(dao.listaCargos.get(tableCargos.getSelectedRow()));

			carregarGridCargos();

			editOFF();

			//Seta posição da seleção para incio
			tableCargos.addRowSelectionInterval(registroSelecionado,
					registroSelecionado);
			carregarCampos(registroSelecionado);

			estado = 0; //neutro
		}
	}

	private void BtnSalvarMouseClicked(java.awt.event.MouseEvent evt) {

	}

	private void BtnApagarActionPerformed(java.awt.event.ActionEvent evt) {
		if (JOptionPane.showConfirmDialog(null, "Apagar resgitro?",
				"Apagar registro", 2) == 0) {//0 = OK

			// Se apagar() retornar bem sucedido conclui alterações
			if (dao.apagar(dao.listaCargos.get(tableCargos.getSelectedRow()))) {
				dao.listaCargos.remove(tableCargos.getSelectedRow());
				carregarGridCargos();
				tableCargos.addRowSelectionInterval(0, 0);
				carregarCampos(0);
			}
		}
	}

	private void BtnApagarMouseClicked(java.awt.event.MouseEvent evt) {
		// TODO add your handling code here:
	}

	private void BtnEditarActionPerformed(java.awt.event.ActionEvent evt) {
		editON();
		estado = 2; //editando
	}

	private void BtnNovoActionPerformed(java.awt.event.ActionEvent evt) {
		editON();
		editCodigo.setText("");
		editDesc.setText("");
		estado = 1;

		editDesc.requestFocus();
	}

	private void BtnNovoMouseClicked(java.awt.event.MouseEvent evt) {

	}

	private void tableCargosMouseClicked(java.awt.event.MouseEvent evt) { //Click GRID
		if (estado == 0) {
			carregarCampos(tableCargos.getSelectedRow());
		}
	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JButton BtnApagar;
	private javax.swing.JButton BtnCancelar;
	private javax.swing.JButton BtnEditar;
	private javax.swing.JButton BtnNovo;
	private javax.swing.JButton BtnSalvar;
	private javax.swing.JButton BtnVoltar;
	private javax.swing.JTextField editCodigo;
	private javax.swing.JTextField editDesc;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JScrollPane scroolTable;
	private javax.swing.JTable tableCargos;
	// End of variables declaration//GEN-END:variables

}