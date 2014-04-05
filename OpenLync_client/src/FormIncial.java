
import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;
import java.awt.Image;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import java.awt.Color;

public class FormIncial extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	public static JDesktopPane jdpUsuarios;
	
	private static JLabel labelNome;
	private static JLabel labelCargo;
	private JLabel labelFoto;
	
	public FormIncial(String nome, String cargo, Image foto) {
		
		setBorder(null);
		setBounds(100, 100, 370, 570);
		
		jdpUsuarios = new JDesktopPane();
		jdpUsuarios.setBackground(Color.WHITE);
		jdpUsuarios.setBounds(22, 85, 321, 414);
		jdpUsuarios.setBorder(null);
		getContentPane().setLayout(null);
		getContentPane().add(jdpUsuarios);
		
		labelNome = new JLabel(nome);
		labelNome.setFont(new Font("Dialog", Font.BOLD, 17));
		labelNome.setBounds(96, 18, 247, 15);
		getContentPane().add(labelNome);
		
		labelCargo = new JLabel(cargo);
		labelCargo.setFont(new Font("Dialog", Font.PLAIN, 14));
		labelCargo.setBounds(96, 48, 247, 15);
		getContentPane().add(labelCargo);
		
		labelFoto = new JLabel("");
		labelFoto.setIcon(new ImageIcon(foto));
		labelFoto.setBounds(22, 12, 57, 57);
		getContentPane().add(labelFoto);

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
