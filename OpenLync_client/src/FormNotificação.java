import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class FormNotificação extends JFrame {

	private static final long serialVersionUID = 1L;

	private String mensagem;
	private BufferedImage imagem;
	private int indexFrameUsuario;

	/** Creates new form FormNotificação */
	public FormNotificação(String mensagem, BufferedImage imagem,
			int indexFrameUsuario) {
		initComponents();

		// Seta tema
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		SwingUtilities.updateComponentTreeUI(this);

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension scrnsize = toolkit.getScreenSize();

		int scrnHeight = (int) scrnsize.getHeight();
		int scrnWidth = (int) scrnsize.getWidth();

		setLocation((scrnWidth - 420), (scrnHeight - 650));

		this.mensagem = mensagem;
		this.imagem = imagem;
		this.indexFrameUsuario = indexFrameUsuario;

	}

	/**
	 * Atualiza componentes; Apos 7s o form é fechado
	 */
	@Override
	public void setVisible(boolean b) {

		labelFoto.setIcon(new ImageIcon(this.imagem));
		labelMensagem.setText(this.mensagem);

		super.setVisible(b);

		try {
			new Thread();
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		dispose();

	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		labelFoto = new javax.swing.JLabel();
		labelTitulo = new javax.swing.JLabel();
		labelMensagem = new javax.swing.JLabel();

		setDefaultCloseOperation(3);
		setUndecorated(true);

		jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				jPanel1MouseEntered(evt);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				jPanel1MouseExited(evt);
			}

			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jPanel1MouseClicked(evt);
			}
		});

		labelTitulo.setFont(new java.awt.Font("Ubuntu", 1, 15));
		labelTitulo.setText("OpenLync");

		labelMensagem.setText("Mensagem de Ana Maria");

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
										.addContainerGap()
										.addComponent(
												labelFoto,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												57,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																labelTitulo)
														.addComponent(
																labelMensagem,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																293,
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
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addComponent(
																				labelTitulo)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				labelMensagem,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				33,
																				Short.MAX_VALUE))
														.addComponent(
																labelFoto,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																57,
																Short.MAX_VALUE))
										.addContainerGap()));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.PREFERRED_SIZE));

		pack();
	}// </editor-fold>
		// GEN-END:initComponents

	private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {

		Contatos.listaFormChat.get(indexFrameUsuario).setState(JFrame.NORMAL);
		Contatos.listaFormChat.get(indexFrameUsuario).toFront();

		dispose();
	}

	private void jPanel1MouseExited(java.awt.event.MouseEvent evt) {

		setOpacity(1F);
	}

	private void jPanel1MouseEntered(java.awt.event.MouseEvent evt) {

		setOpacity(0.4F);
	}

	// GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JPanel jPanel1;
	private javax.swing.JLabel labelFoto;
	private javax.swing.JLabel labelMensagem;
	private javax.swing.JLabel labelTitulo;
	// End of variables declaration//GEN-END:variables

}