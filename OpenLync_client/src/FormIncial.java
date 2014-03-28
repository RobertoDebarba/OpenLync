
import javax.swing.JInternalFrame;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.JDesktopPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
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
	private static int contadorUsuarios = 0;
	
	private static JLabel labelNome;
	private static JLabel labelCargo;
	private static JPanel panelFoto;	
	public static FormUsuarioLista listaInternalFrames[] = new FormUsuarioLista[100]; //FIXME
	
	
	public static FormUsuarioLista getNovoFormUsuarioLista(int codigoUsuario) { //FIXME foto
		Usuarios usuario = new Usuarios();
		try {
			usuario.carregarInformacoes(codigoUsuario);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		FormUsuarioLista frmUsuario = new FormUsuarioLista(usuario.getCodigo(), usuario.getNome(), usuario.getCargo(), usuario.getIp());
		
		// Seta tema
		try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } catch (ClassNotFoundException | InstantiationException
	            | IllegalAccessException | UnsupportedLookAndFeelException e) {
	        e.printStackTrace();
	    }
	    SwingUtilities.updateComponentTreeUI(frmUsuario);
	    
	    // Retira bordas
 		((BasicInternalFrameUI)frmUsuario.getUI()).setNorthPane(null); //retirar o painel superior  
 		frmUsuario.setBorder(null);//retirar bordas  
		
		// Seta centro
		frmUsuario.setLocation(0, contadorUsuarios);
		
		contadorUsuarios = contadorUsuarios + 60;
		
		return frmUsuario;
	
	}
	
	public static void setUsuarioNaLista(FormUsuarioLista frmUsuarioLista) {
		jdpUsuarios.add(frmUsuarioLista);
		frmUsuarioLista.setVisible(true);
	}
	
//	public static void setNovoUsuarioLista(int codigoUsuario, int contador) { //FIXME foto
//		Usuarios usuario = new Usuarios();
//		try {
//			usuario.carregarInformacoes(codigoUsuario);
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//		}
//		
//		FormUsuarioLista frmUsuario = new FormUsuarioLista(usuario.getCodigo(), usuario.getNome(), usuario.getCargo(), usuario.getIp());
//		
//		// Seta tema
//		try {
//	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//	    } catch (ClassNotFoundException | InstantiationException
//	            | IllegalAccessException | UnsupportedLookAndFeelException e) {
//	        e.printStackTrace();
//	    }
//	    SwingUtilities.updateComponentTreeUI(frmUsuario);
//	    
//	    // Retira bordas
// 		((BasicInternalFrameUI)frmUsuario.getUI()).setNorthPane(null); //retirar o painel superior  
// 		frmUsuario.setBorder(null);//retirar bordas  
//		
//		// Seta centro
//		frmUsuario.setLocation(0, contadorUsuarios);
//		
//		contadorUsuarios = contadorUsuarios + 60;
		
		
//		boolean tem = false;
//		int a = 0;
//		while (a < 100) {//FIXME tamanho grid
//			
//			if (listaInternalFrames[a] != null) {
//				if (listaInternalFrames[a].getCodigoUsuario() == frmUsuario.getCodigoUsuario()) {
//					tem = true;
//				}
//			}
//			
//			a++;
//			
//		}
//		
//		if (!tem) {
//			listaInternalFrames[contador] = frmUsuario;
//			
//			jdpUsuarios.add(listaInternalFrames[contador]);
//			listaInternalFrames[contador].setVisible(true);
//		}
		
		
		
		
	
	public static void limparUsuariosLista(int quantidade) {
		int i = 0;
		boolean achou = false;
		while (i < 100) { //FIXME tamanho grid
			//TODO programar para pagar somente que estiver offline e acertar posições
			
			Usuarios userTeste = new Usuarios();
			try {
				if (listaInternalFrames[i] != null) {
					userTeste.carregarInformacoes(listaInternalFrames[i].getCodigoUsuario());
					achou = true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if ((achou) && (!userTeste.getStatus())) {
				jdpUsuarios.remove(listaInternalFrames[i]);
				jdpUsuarios.repaint();
				listaInternalFrames[i] = null; //FIXME testetesteteste
				
			}
			i++;
			achou = false;
		}
	}
	
	
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
        }, 1000, 15000);

	}
	
}
