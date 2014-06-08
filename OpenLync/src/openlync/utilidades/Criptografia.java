package openlync.utilidades;

import javax.swing.JOptionPane;

public class Criptografia {

	/**
	 * @return mensagem criptografada
	 */
	public String criptografarMensagem(String mensagem) {

		String mensagemCript = "";

		for (int i = 0; i < mensagem.length(); i++) { // Vare linha
			char c = mensagem.charAt(i); // Captura letra na linha
			int x = (int) c; // Captura codigo ASC da letra
			int letraCript = (((x * 8) + 3) * 39); // Realiza calculo de
													// criptografia
			String letraCriptStr = ("" + letraCript); // Converte int para STR
			
			//Valida tamanho do codigo gerado
			if ((letraCriptStr.length() != 5) && ((!letraCriptStr.equals("2925")) && (!letraCriptStr.equals("3237")))) {
				letraCriptStr = "";
				JOptionPane.showMessageDialog(null, "O caracter "+c+" não é suportado!", "Erro de criptografia", 1);
			}
			
			mensagemCript = mensagemCript + letraCriptStr;
		}

		return mensagemCript;
	}

	/**
	 * @return mensagem descriptografada
	 */
	public String descriptografarMensagem(String mensagem) {

		String mensagemDescript = "";

		try {
			for (int z = 0; z < mensagem.length(); z++) { // Varre da linha
				String carac = "";
	
				int contador = (z + 5); // Seta tamanho do codigo asc a ser
										// descript.
				for (int i = z; i < contador; i++) { // Captura numero asc a ser
														// calculado
					carac = (carac + mensagem.charAt(i));
					
					// -- RESTRI��ES
					if (carac.equals("2925")) { 		//Se for TAB
						i++;
					} else if (carac.equals("3237")) { 	//Se for ENTER
						i++;
					}
					
					if (i != (contador - 1)) { // Se n�o for ultimo numero do codigo
						z++; // Incrementa contador geral de caracter na linha
					}
				}
	
				int asc = (((Integer.parseInt(carac) / 39) - 3) / 8); // Calculo
																		// inverso
																		// de
																		// descriptografia
				char x = (char) asc; // Converte asc para letra
	
				mensagemDescript = mensagemDescript + x;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ocorreu um erro ao descriptografar uma mensagem!", "Erro de Criptografia", 0);
			return null;
		}

		return mensagemDescript;
	}
}