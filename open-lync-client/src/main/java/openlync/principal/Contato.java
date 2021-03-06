package openlync.principal;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import openlync.forms.FormChat;
import openlync.forms.FormIncial;
import openlync.forms.FormLogin;
import openlync.forms.FormUsuarioLista;

public class Contato {

	public static List<FormChat> listaFormChat = new ArrayList<FormChat>();
	public static List<FormUsuarioLista> listaFormUsuarioLista = new ArrayList<FormUsuarioLista>();
	
	/**
	 * Atualiza contatos (listaFormChat) com base em UsuariosDAO.listaUsuarios
	 * Antes de iniciar atualiza lista de Usuarios
	 */
	public void atualizarContatos() {
				
		UsuarioDAO dao = new UsuarioDAO(true);

		//Remove usuarios não presentes em listaUsuarios
//		//Varre todos InternalFrames para comparar com Usuarios
//		int a = 0;
//		while (a < listaFormUsuarioLista.size()) {
//			
//			int b = 0;
//			boolean achou = false;
//			while ((b < dao.listaUsuarios.size()) && (!achou)) {
//				
//				//Se InternalFrame corresponde à algum usuário
//				if (listaFormUsuarioLista.get(a).getUsuario().getCodigo() == dao.listaUsuarios.get(b).getCodigo()) {
//					achou = true;
//				}
//				b++;
//			}
//			
//			//Se não achou nenhum usuario correspondente = remove
//			if (!achou) {
//				
//				removerFormUsuarioLista(listaFormUsuarioLista.get(a).getUsuario());
//			}	
//			a++;
//		}
		
		/*
		 * Varre listaFormUsuarioLista e dao.ListaUsuarios comparando os valores
		 * Se encontrou atualiza o status
		 * Se nao encontrou cria novo FormUsuarioLista 
		 */
		
		//Varre todos Usuarios para comparar com InternalFrames
		for (int i = 0; i < dao.listaUsuarios.size(); i++) {
		
			//Se usuario for o que efetuou login = ignora
			if (!dao.listaUsuarios.get(i).getLogin().equals(FormLogin.getUsuarioLogin().getLogin())) {
				
				//Varre InternalFrames ate achar o que corresponde ao usuario
				boolean achou = false;
				int z = 0;
				while (z < listaFormUsuarioLista.size()) {
					
					if (listaFormUsuarioLista.get(z).getUsuario().getCodigo() == dao.listaUsuarios.get(i).getCodigo()) {
						achou = true;
						break;
					}
					
					z++;
				}
				
				//Se achou
				if (achou) {
					//Atualiza status
					listaFormUsuarioLista.get(z).setStatus(dao.listaUsuarios.get(i).getStatus(), dao.listaUsuarios.get(i).getIp());
				//Se não achou
				} else {
					
					// Verificação de Abas e opção Online -------------------------------------------
					boolean adicionarIF = false;
					
					//Aba "Todos"
					if (FormIncial.indiceAba == 0) {
					
						// Considera opção "Apenas Online" do FormIncial
						if (FormIncial.checkOnline.isSelected()) {
							
							//Se estiver online = adiciona
							if (dao.listaUsuarios.get(i).getStatus()) {
								adicionarIF = true;
							}
						} else {
							adicionarIF = true;
						}
						
					//Aba "Amigos"
					} else if (FormIncial.indiceAba == 1) {
						
						// Considera opção "Apenas Online" do FormIncial
						if (FormIncial.checkOnline.isSelected()) {
							
							//Se estiver online = adiciona
							if (dao.listaUsuarios.get(i).getStatus()) {
								
								//Se for amigo
								if (dao.verificarAmizade(FormLogin.getUsuarioLogin(), dao.listaUsuarios.get(i))) {
									adicionarIF = true;
								}
							}
						} else {
							//Se for amigo
							if (dao.verificarAmizade(FormLogin.getUsuarioLogin(), dao.listaUsuarios.get(i))) {
								adicionarIF = true;
							}
						}
					}
					
					if (adicionarIF) {
						adicionarFormUsuarioLista(dao.listaUsuarios.get(i));
					}
					//-------------------------------------------------------------------------------
				}
			}
		}
	}
	
	/**
	 * Abre novo FormChat e adiciona na listaFormChat
	 * @param usuario: Usuario do chat
	 * @param estado: 0 - normal / 1 - minimizado
	 * @param modo: 0 - normal / 1 - mensagens não lidas / 2 - historico de mensagens 
	 */
	public void adicionarFormChat(Usuario usuario, int estado, int modo) {
		
		FormChat frmChat = new FormChat(usuario, modo);
		
		frmChat.setVisible(true);
		
		if (estado == 1) {
			frmChat.setState(JFrame.ICONIFIED);
			frmChat.toFront();
		}
		
		listaFormChat.add(frmChat);
	}
	
	/**
	 * Fecha FormChat e remove da listaFormChat
	 */
	public void removerFormChat(Usuario usuario) {
		
		for (int i = 0; i < listaFormChat.size(); i++) {
			
			//Varre lista ate achar usuario correto
			if (listaFormChat.get(i).getUsuario().getCodigo() == usuario.getCodigo()) {
				listaFormChat.get(i).setVisible(false);
				listaFormChat.remove(i);
				break;
			}
		}
	}
	
	/**
	 * Abre novo FormUsuarioLista, adiciona na listaFormUsuarioLista e no JDP
	 */
	public void adicionarFormUsuarioLista(Usuario usuario) {
		
		FormUsuarioLista frmUsuarioLista = new FormUsuarioLista(usuario);
		frmUsuarioLista.setLocation(0, listaFormUsuarioLista.size() * 60);
		frmUsuarioLista.setStatus(usuario.getStatus(), usuario.getIp());
		frmUsuarioLista.setVisible(true);
		
		FormIncial.jdpUsuarios.add(frmUsuarioLista);
		listaFormUsuarioLista.add(frmUsuarioLista);
	}
	
	/**
	 * Remove FormUsuarioLista da listaFormUsuarioLista e do JDP
	 */
	public void removerFormUsuarioLista(Usuario usuario) {
		
		for (int i = 0; i < listaFormUsuarioLista.size(); i++) {
			
			//Varre lista ate achar usuario correto
			if (listaFormUsuarioLista.get(i).getUsuario().getCodigo() == usuario.getCodigo()) {
				FormIncial.jdpUsuarios.remove(listaFormUsuarioLista.get(i));

				for (int z = i; z < listaFormUsuarioLista.size(); z++) {
					
					listaFormUsuarioLista.get(i).setLocation(0, (z - 1) * 60);
				}
				
				listaFormUsuarioLista.remove(i);
				break;
			}
		}
	}
	
	/**
	 * Remove todos FormUsuarioLista
	 * Limpa listaFormUsuariosLista
	 * Limpa JDP
	 */
	public void removerTodosFormUsuarioLista() {
		listaFormUsuarioLista.removeAll(listaFormUsuarioLista);
		FormIncial.jdpUsuarios.removeAll();
		FormIncial.jdpUsuarios.repaint();
	}
	
	/**
	 * Retorna quantidade de objetos na listaFormChat
	 * @return
	 */
	public static int getSizeListaFormChat() {
		return listaFormChat.size();
	}
	
	/**
	 * Retorna quantidade de objetos na listaFormUsuariosChat
	 * @return
	 */
	public static int getSizeListaFormUsuariosLista() {
		return listaFormUsuarioLista.size();
	}
}
