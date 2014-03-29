
import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

public class FormIncial extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	public static JDesktopPane jdpUsuarios;
	
	private static JLabel labelNome;
	private static JLabel labelCargo;
	private static JPanel panelFoto;		
	
	public FormIncial(String nome, String cargo) { //FIXME foto
		setBorder(null);
		setBounds(100, 100, 370, 570);
		
		jdpUsuarios = new JDesktopPane();
		jdpUsuarios.setBounds(22, 85, 321, 414);
		jdpUsuarios.setBorder(null);
		getContentPane().setLayout(null);
		getContentPane().add(jdpUsuarios);
		
		panelFoto = new JPanel();
		panelFoto.setBackground(Color.GREEN);
		panelFoto.setBounds(22, 12, 61, 61);
		getContentPane().add(panelFoto);
		
		labelNome = new JLabel(nome);
		labelNome.setFont(new Font("Dialog", Font.BOLD, 17));
		labelNome.setBounds(96, 18, 247, 15);
		getContentPane().add(labelNome);
		
		labelCargo = new JLabel(cargo);
		labelCargo.setFont(new Font("Dialog", Font.PLAIN, 14));
		labelCargo.setBounds(96, 48, 247, 15);
		getContentPane().add(labelCargo);

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
        }, 1000, 7000);

	}
	
}
