
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;


public class FormConfig extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField editIPdoServidor;

	public FormConfig() {
		getContentPane().setBackground(new Color(238, 238, 238));
		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameActivated(InternalFrameEvent e) {
				editIPdoServidor.setText(Configuracoes.getIpServidor());
			}
		});
		setBounds(100, 100, 370, 570);
		getContentPane().setLayout(null);
		
		JLabel labelIPdoSERVIDOR = new JLabel("IP do servidor");
		labelIPdoSERVIDOR.setBounds(125, 118, 104, 15);
		getContentPane().add(labelIPdoSERVIDOR);
		
		editIPdoServidor = new JTextField();
		editIPdoServidor.setBounds(92, 150, 168, 25);
		getContentPane().add(editIPdoServidor);
		editIPdoServidor.setColumns(10);
		
		JButton BtnOK = new JButton("OK");
		BtnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {		// Seta IP do SERVIDOR
				Configuracoes.setIpServidor(editIPdoServidor.getText());
				FormMain.fecharFrmConfig();
			}
		});
		BtnOK.setBounds(264, 485, 84, 41);
		getContentPane().add(BtnOK);

	}
}
