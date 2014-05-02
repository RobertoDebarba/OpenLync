
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import java.awt.Font;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

import java.awt.image.BufferedImage;
import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.UIManager;

import Biblioteca.MDIDesktopPane;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FormIncial extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	
	private static JLabel labelNome;
	private static JLabel labelCargo;
	public static MDIDesktopPane jdpUsuarios;
	public static JCheckBox checkOnline;
	public static int indexBtn;
	private JLabel labelFoto;
	private static JLabel lblContatos;
	private static JLabel lblTodos;
	
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
		scrollPane.setBounds(17, 98, 324, 445);
		getContentPane().add(scrollPane);
		
		jdpUsuarios = new MDIDesktopPane();
		jdpUsuarios.setBorder(null);
		jdpUsuarios.setBackground(UIManager.getColor("Button.background"));
		scrollPane.setViewportView(jdpUsuarios);
		
		checkOnline = new JCheckBox("Online");
		checkOnline.setBounds(270, 75, 83, 26);
		getContentPane().add(checkOnline);
		
		lblTodos = new JLabel("Todos");
		lblTodos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Se a aba selecionada atualmente não for a clicada
				if (indexBtn != 0) {
					setBtnAba(0);
				}
			}
		});
		lblTodos.setHorizontalAlignment(SwingConstants.CENTER);
		lblTodos.setBounds(17, 80, 97, 18);
		lblTodos.setOpaque(true);
		lblTodos.setBackground(new Color(210, 210, 210));
		getContentPane().add(lblTodos);
		
		lblContatos = new JLabel("Contatos");
		lblContatos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Se a aba selecionada atualmente não for a clicada
				if (indexBtn != 1) {
					setBtnAba(1);
				}
			}
		});
		lblContatos.setHorizontalAlignment(SwingConstants.CENTER);
		lblContatos.setOpaque(true);
		lblContatos.setBounds(114, 80, 97, 18);
		getContentPane().add(lblContatos);
		
		//Timer para atualizar lista de contatos online
		Timer t = new Timer();
		t.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
					Contatos.atualizarListaPrincipal();
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }
        }, 1000, 5000);
	}
	
	public static void setBtnAba(int indexBtn) {
		if (indexBtn == 0) { //Todos
			lblContatos.setBackground(new Color(238, 238, 238));
			lblTodos.setBackground(new Color(210, 210, 210));
			FormIncial.indexBtn = indexBtn;
			try {
				Contatos.atualizarListaPrincipal();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (indexBtn == 1) { //Contatos
			
			//Limpa jdp e listas
			jdpUsuarios.removeAll();
			Contatos.setContadorChat(0);
			FormUsuarioLista.setContadorPosicaousuario(0);
			int i = 0;
			while (i < 100) {
				Contatos.listaInternalFrames[i] = null;
				i++;
			}
			
			lblTodos.setBackground(new Color(238, 238, 238));
			lblContatos.setBackground(new Color(210, 210, 210));
			FormIncial.indexBtn = indexBtn;
			try {
				Contatos.atualizarListaPrincipal();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
