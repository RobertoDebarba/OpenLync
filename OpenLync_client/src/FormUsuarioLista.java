import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;


public class FormUsuarioLista extends JInternalFrame {

	JLabel labelNome;
	JLabel labelCargo;
	JPanel panelFoto;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormUsuarioLista frame = new FormUsuarioLista();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void carregarInformacoes(String nome, String cargo) { //FIXME foto
		labelNome.setText(nome);
		labelCargo.setText(cargo);
	}
	
	public FormUsuarioLista() {
		setBorder(null);
		getContentPane().setBackground(Color.LIGHT_GRAY);
		setBounds(100, 100, 321, 65);
		
		panelFoto = new JPanel();
		panelFoto.setBounds(8, 5, 42, 42);
		panelFoto.setBackground(Color.GREEN);
		
		labelNome = new JLabel("Roberto Luiz Debarba");
		labelNome.setFont(new Font("Dialog", Font.BOLD, 14));
		labelNome.setBounds(65, 10, 174, 15);
		getContentPane().setLayout(null);
		getContentPane().add(panelFoto);
		getContentPane().add(labelNome);
		
		labelCargo = new JLabel("Programador de Sistema");
		labelCargo.setFont(new Font("Dialog", Font.PLAIN, 12));
		labelCargo.setBounds(65, 30, 174, 15);
		getContentPane().add(labelCargo);

	}
}
