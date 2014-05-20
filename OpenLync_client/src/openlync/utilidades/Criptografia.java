package openlync.utilidades;
public class Criptografia {

	/**
	 * Retorna mensagem criptografada
	 */
	public String criptografarMensagem(String mensagem) {

		String mensagemCript = "";

		for (int i = 0; i < mensagem.length(); i++) { // Vare linha
			char c = mensagem.charAt(i); // Captura letra na linha
			int x = (int) c; // Captura codigo ASC da letra
			int letraCript = (((x * 8) + 3) * 39); // Realiza calculo de
													// criptografia
			String letraCriptStr = ("" + letraCript); // Converte int para STR
			mensagemCript = mensagemCript + letraCriptStr;
		}

		return mensagemCript;
	}

	/**
	 * Retorna mensagem descriptografada
	 */
	public String descriptografarMensagem(String mensagem) {

		String mensagemDescript = "";

		for (int z = 0; z < mensagem.length(); z++) { // Varre da linha
			String carac = "";

			int contador = (z + 5); // Seta tamanho do codigo asc a ser
									// descript.
			for (int i = z; i < contador; i++) { // Captura numero asc a ser
													// calculado
				carac = (carac + mensagem.charAt(i));
				if (i != (contador - 1)) { // Se não for ultimo numero do codigo
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

		return mensagemDescript;
	}
}
