
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.swing.JScrollPane;
import Biblioteca.MDIDesktopPane;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
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
	
	public static int indiceAba = 0;
	public static JCheckBox checkOnline;
	public static MDIDesktopPane jdpUsuarios;
	
	private static Contatos contatos = new Contatos();
	
	
	public FormIncial(String nome, String cargo, BufferedImage foto) {		
		getContentPane().setBackground(new Color(238, 238, 238));
		
		setBorder(null);
		setBounds(100, 100, 370, 570);
		getContentPane().setLayout(null);
		
		labelNome = new JLabel(nome);
		labelNome.setFont(new Font("Dialog", Font.BOLD, 17));
		labelNome.setBounds(96, 18, 247, 15);
		getContentPane().add(labelNome);
		
		labelCargo = new JLabel(cargo);
		labelCargo.setFont(new Font("Dialog", Font.PLAIN, 14));
		labelCargo.setBounds(96, 48, 247, 15);
		getContentPane().add(labelCargo);
		
		labelFoto = new JLabel("");
		labelFoto.setBackground(new Color(255, 255, 255));
		labelFoto.setIcon(new ImageIcon(foto));
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
				//Limpa jdp e listas
				contatos.removerTodosFormUsuarioLista();
				//Atualiza lista
				contatos.atualizarContatos();
			}
		});
		checkOnline.setBounds(270, 73, 75, 23);
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
		lblSobre.setIcon(new ImageIcon(FormIncial.class.getResource("/Imagens/sobreIcon.png")));
		lblSobre.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {	//Btn Sobre
				FormSobre frmSobre = new FormSobre();
				frmSobre.setVisible(true);
			}
		});
		lblSobre.setBounds(336, 4, 20, 18);
		getContentPane().add(lblSobre);
		
		/*
		 * Timer para atualizar lista de contatos online		
		 */
		Timer t = new Timer();
		t.schedule(new TimerTask() {
            @Override
            public void run() {
				contatos.atualizarContatos();
            }
        }, 1000, 5000);
	}
	
	/**
	 * Seta aba ativa e chama metodo para atualizar lista
	 * @param indiceAba
	 */
	public static void setBtnAba(int indiceAba) {
		if (indiceAba == 0) { // Todos
			//Atualiza aba
			lblContatos.setBackground(new Color(238, 238, 238));
			lblTodos.setBackground(new Color(210, 210, 210));
			
			FormIncial.indiceAba = indiceAba;
		} else if (indiceAba == 1) { //Contatos
			//Atualiza aba
			lblTodos.setBackground(new Color(238, 238, 238));
			lblContatos.setBackground(new Color(210, 210, 210));
			
			FormIncial.indiceAba = indiceAba;
		}
		
		//Limpa jdp e listas
		contatos.removerTodosFormUsuarioLista();
		//Atualiza lista
		contatos.atualizarContatos();
	}
}
