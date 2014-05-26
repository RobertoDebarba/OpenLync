package openlync.forms;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Font;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import openlync.principal.Contatos;
import openlync.principal.Mensagens;
import openlync.principal.Usuarios;
import openlync.principal.UsuariosDAO;
import openlync.utilidades.MDIDesktopPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FormIncial extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	private static JLabel labelNome;
	private static JLabel labelCargo;
	private static JLabel lblContatos;
	private static JLabel lblTodos;
	private JLabel lblSobre;
	private JLabel labelFoto;
	private JLabel labelMensagens;
	private JPopupMenu popMenu;

	public static int indiceAba = 0;
	public static JCheckBox checkOnline;
	public static MDIDesktopPane jdpUsuarios;

	private static Contatos contatos = new Contatos();

	public FormIncial(Usuarios usuario) {
		getContentPane().setBackground(new Color(238, 238, 238));

		setBorder(null);
		setBounds(100, 100, 370, 570);
		getContentPane().setLayout(null);

		labelNome = new JLabel(usuario.getNome());
		labelNome.setFont(new Font("Dialog", Font.BOLD, 17));
		labelNome.setBounds(96, 18, 247, 15);
		getContentPane().add(labelNome);

		labelCargo = new JLabel(usuario.getCargo());
		labelCargo.setFont(new Font("Dialog", Font.PLAIN, 14));
		labelCargo.setBounds(96, 48, 247, 15);
		getContentPane().add(labelCargo);

		labelFoto = new JLabel("");
		labelFoto.setBackground(new Color(255, 255, 255));
		labelFoto.setIcon(new ImageIcon(usuario.getFoto()));
		labelFoto.setBounds(22, 12, 57, 57);
		getContentPane().add(labelFoto);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(17, 98, 324, 430);
		getContentPane().add(scrollPane);

		jdpUsuarios = new MDIDesktopPane();
		jdpUsuarios.setBorder(null);
		jdpUsuarios.setBackground(new Color(238, 238, 238));
		scrollPane.setViewportView(jdpUsuarios);

		checkOnline = new JCheckBox("Online");
		checkOnline.setBackground(new Color(238, 238, 238));
		checkOnline.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Limpa jdp e listas
				contatos.removerTodosFormUsuarioLista();
				// Atualiza lista
				contatos.atualizarContatos();
			}
		});
		checkOnline.setBounds(270, 530, 75, 23);
		getContentPane().add(checkOnline);

		lblTodos = new JLabel("Todos");
		lblTodos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Se a aba selecionada atualmente não for a clicada
				if (indiceAba != 0) {
					setBtnAba(0);
				}
			}
		});
		lblTodos.setHorizontalAlignment(SwingConstants.CENTER);
		lblTodos.setBounds(17, 80, 97, 18);
		lblTodos.setOpaque(true);
		lblTodos.setBackground(new Color(210, 210, 210));
		getContentPane().add(lblTodos);

		lblContatos = new JLabel("Amigos");
		lblContatos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Se a aba selecionada atualmente não for a clicada
				if (indiceAba != 1) {
					setBtnAba(1);
				}
			}
		});
		lblContatos.setHorizontalAlignment(SwingConstants.CENTER);
		lblContatos.setOpaque(true);
		lblContatos.setBackground(new Color(238, 238, 238));
		lblContatos.setBounds(114, 80, 97, 18);
		getContentPane().add(lblContatos);

		lblSobre = new JLabel("");
		lblSobre.setIcon(new ImageIcon(FormIncial.class
				.getResource("/openlync/imagens/sobreIcon.png")));
		lblSobre.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) { // Btn Sobre
				FormSobre frmSobre = new FormSobre();
				frmSobre.setVisible(true);
			}
		});
		lblSobre.setBounds(336, 4, 20, 18);
		getContentPane().add(lblSobre);

		popMenu = new JPopupMenu();

		labelMensagens = new JLabel("");
		labelMensagens.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				popMenu.show(labelMensagens, 25, 18);
			}
		});
		labelMensagens.setBounds(317, 79, 20, 15);
		getContentPane().add(labelMensagens);
		
		// Seta tema
		try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	        UIManager.put("DesktopPaneUI","javax.swing.plaf.basic.BasicDesktopPaneUI"); // Remove barra inferior
	    } catch (ClassNotFoundException | InstantiationException
	            | IllegalAccessException | UnsupportedLookAndFeelException e) {
	        e.printStackTrace();
	    }
	    SwingUtilities.updateComponentTreeUI(this);
	    
	    // Retira bordas
 		((BasicInternalFrameUI)this.getUI()).setNorthPane(null); //retirar o painel superior  
 		setBorder(null);//retirar bordas 
	    
	    //Seta centro
	    setLocation(0, 0);
	    

		/*
		 * Timer para atualizar lista de contatos online
		 */
		Timer t1 = new Timer();
		t1.schedule(new TimerTask() {
			@Override
			public void run() {
				contatos.atualizarContatos();
			}
		}, 1000, 5000);

		/*
		 * Timer para atualizar icone de mensagens não lidas
		 */
		Timer t2 = new Timer();
		t2.schedule(new TimerTask() {
			@Override
			public void run() {
				carregarPopUpMenu();
			}
		}, 1000, 7000);
	}

	/**
	 * Seta aba ativa e chama metodo para atualizar lista
	 * 
	 * @param indiceAba
	 */
	public static void setBtnAba(int indiceAba) {
		if (indiceAba == 0) { // Todos
			// Atualiza aba
			lblContatos.setBackground(new Color(238, 238, 238));
			lblTodos.setBackground(new Color(210, 210, 210));

			FormIncial.indiceAba = indiceAba;
		} else if (indiceAba == 1) { // Contatos
			// Atualiza aba
			lblTodos.setBackground(new Color(238, 238, 238));
			lblContatos.setBackground(new Color(210, 210, 210));

			FormIncial.indiceAba = indiceAba;
		}

		// Limpa jdp e listas
		contatos.removerTodosFormUsuarioLista();
		// Atualiza lista
		contatos.atualizarContatos();
	}

	/**
	 * Atualizador de icone de novas mensagens: Atualiza icone; Carrega
	 * PopUpMenu com lista de novas mensagens; Se não houver mensagens, seta
	 * menu Default
	 */
	private void carregarPopUpMenu() {

		Mensagens mens = new Mensagens();

		final List<Integer> listUsuariosMensagens = mens
				.getListMensagensNaoLidas(FormLogin.getUsuarioLogin());

		if (listUsuariosMensagens.size() == 0) {
			labelMensagens.setIcon(new ImageIcon(FormIncial.class
					.getResource("/openlync/imagens/novaMensagem_OFF.png")));

			popMenu.removeAll();

			JMenuItem menuItemDefault = new JMenuItem("Não há novas mensagens!");
			popMenu.add(menuItemDefault);

		} else {

			labelMensagens.setIcon(new ImageIcon(FormIncial.class
					.getResource("/openlync/imagens/novaMensagem_ON.png")));

			popMenu.removeAll();

			UsuariosDAO dao = new UsuariosDAO(true);
			final Contatos contatos = new Contatos();

			for (int i = 0; i < listUsuariosMensagens.size(); i++) {

				final Usuarios user = dao
						.procurarUsuarioCodigo(listUsuariosMensagens.get(i));

				final JMenuItem menuItem = new JMenuItem(
						"Mensagem não lida de " + user.getNome());
				menuItem.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {

						listUsuariosMensagens.remove(user);
						popMenu.remove(menuItem);
						contatos.adicionarFormChat(user, 0, 1);
					}
				});
				popMenu.add(menuItem);
			}
		}
	}
}
