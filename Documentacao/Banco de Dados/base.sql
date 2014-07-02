DROP DATABASE IF EXISTS OpenLync;
CREATE DATABASE OpenLync;
USE OpenLync;

CREATE TABLE tb_cargos (
	codigo_cargo INT(3) PRIMARY KEY AUTO_INCREMENT,
	desc_cargo VARCHAR(45) NOT NULL
);

CREATE TABLE tb_usuarios (
	codigo_usuario INT(3) PRIMARY KEY AUTO_INCREMENT,
	nome_usuario VARCHAR(45) NOT NULL,
	login_usuario VARCHAR(50) NOT NULL,
	senha_usuario VARCHAR(50) NOT NULL,
	ip_usuario VARCHAR(15) NOT NULL,
	codigo_cargo INT(3),
	admin_usuario boolean NOT NULL,
	foto_usuario BLOB,

	CONSTRAINT codigo_cargo_fk FOREIGN KEY (codigo_cargo)
	REFERENCES tb_cargos(codigo_cargo)
);

CREATE TABLE tb_amigos (
	codigo_usuario_amigo INT(3),
	codigo_amigo_amigo INT(3),

	CONSTRAINT codigo_usuario_amigo_fk FOREIGN KEY (codigo_usuario_amigo)
	REFERENCES tb_usuarios(codigo_usuario),

	CONSTRAINT codigo_amigo_amigo_fk FOREIGN KEY (codigo_amigo_amigo)
	REFERENCES tb_usuarios(codigo_usuario),

	PRIMARY KEY (codigo_usuario_amigo, codigo_amigo_amigo)
);

CREATE TABLE tb_mensagens (
	codigo_mensagem INT(3) AUTO_INCREMENT,
	conteudo_mensagem VARCHAR(500) NOT NULL,
	data_mensagem DATETIME,
	lido_mensagem boolean NOT NULL,
	codigo_remet_mensagem INT(3),
	codigo_dest_mensagem INT(3),

	CONSTRAINT codigo_remet_mensagem_fk FOREIGN KEY (codigo_remet_mensagem)
	REFERENCES tb_usuarios(codigo_usuario),

	CONSTRAINT codigo_dest_mensagem_fk FOREIGN KEY (codigo_dest_mensagem)
	REFERENCES tb_usuarios(codigo_usuario),

	PRIMARY KEY (codigo_mensagem)
);

/* Cargos */

INSERT INTO tb_cargos(desc_cargo)
VALUES ('Programador de Sistemas');

INSERT INTO tb_cargos(desc_cargo)
VALUES ('Analista de Sistemas');

INSERT INTO tb_cargos(desc_cargo)
VALUES ('Diretor de Desenvolvimento');

/* Usuarios */

INSERT INTO tb_usuarios(nome_usuario, login_usuario, senha_usuario, ip_usuario, codigo_cargo, admin_usuario, foto_usuario)
VALUES ('Roberto Luiz Debarba', '35685347493069331629356853630934749'/*roberto*/, '154051571716029'/*123*/, 'null', 1, true, LOAD_FILE('/home/roberto/roberto.jpg'));

INSERT INTO tb_usuarios(nome_usuario, login_usuario, senha_usuario, ip_usuario, codigo_cargo, admin_usuario, foto_usuario)
VALUES ('Maria da Silva', '3412530381356853287730381'/*maria*/, '154051571716029'/*123*/, 'null', 2, false, LOAD_FILE('/home/roberto/maria.jpg'));

INSERT INTO tb_usuarios(nome_usuario, login_usuario, senha_usuario, ip_usuario, codigo_cargo, admin_usuario, foto_usuario)
VALUES ('João Felipe Figueiro', '33189347493038134749'/*joao*/, '154051571716029'/*123*/, 'null', 3, false, LOAD_FILE('/home/roberto/joao.jpg'));

/* Amigos */

INSERT INTO tb_amigos(codigo_usuario_amigo, codigo_amigo_amigo)
VALUES (1, 2);

INSERT INTO tb_amigos(codigo_usuario_amigo, codigo_amigo_amigo)
VALUES (1, 3);

INSERT INTO tb_amigos(codigo_usuario_amigo, codigo_amigo_amigo)
VALUES (2, 1);

INSERT INTO tb_amigos(codigo_usuario_amigo, codigo_amigo_amigo)
VALUES (3, 1);

/* Mensagens */

INSERT INTO tb_mensagens(conteudo_mensagem, data_mensagem, lido_mensagem, codigo_remet_mensagem, codigo_dest_mensagem)
VALUES ('3630931629359973630931629', '2014-04-08 12:24:36', true, 1, 2);

INSERT INTO tb_mensagens(conteudo_mensagem, data_mensagem, lido_mensagem, codigo_remet_mensagem, codigo_dest_mensagem)
VALUES ('3630931629359973630931629', '2014-04-08 12:24:36', false, 1, 2);

INSERT INTO tb_mensagens(conteudo_mensagem, data_mensagem, lido_mensagem, codigo_remet_mensagem, codigo_dest_mensagem)
VALUES ('3630931629359973630931629', '2014-04-08 12:24:36', true, 1, 3);

INSERT INTO tb_mensagens(conteudo_mensagem, data_mensagem, lido_mensagem, codigo_remet_mensagem, codigo_dest_mensagem)
VALUES ('3630931629359973630931629', '2014-04-08 12:24:36', false, 2, 1);

/* Functions, Stored Procedures e Triggers */

DELIMITER $$

-- Cliente

CREATE PROCEDURE sp_getMensagensNaoLidas(IN codigo_usuario_login INT, IN codigo_usuario_remetente INT)
BEGIN
	-- Carrega mensagens
	SELECT conteudo_mensagem, data_mensagem
	FROM tb_mensagens 
	WHERE lido_mensagem = FALSE
		AND codigo_remet_mensagem = codigo_usuario_remetente
		AND codigo_dest_mensagem = codigo_usuario_login;

	-- Define mensagens como lidas
	UPDATE tb_mensagens
	SET lido_mensagem = TRUE
	WHERE codigo_remet_mensagem = codigo_usuario_remetente
		AND codigo_dest_mensagem = codigo_usuario_login;
END $$


CREATE PROCEDURE sp_getHistoricoMensagens(IN codigo_usuario_login INT, IN codigo_usuario_remetente INT)
BEGIN
	-- Carrega mensagens
	SELECT conteudo_mensagem, data_mensagem, codigo_remet_mensagem
	FROM tb_mensagens
	WHERE (codigo_remet_mensagem = codigo_usuario_remetente
		AND codigo_dest_mensagem = codigo_usuario_login)
		OR (codigo_remet_mensagem = codigo_usuario_login
		AND codigo_dest_mensagem = codigo_usuario_remetente);

	-- Define mensagens como lidas
	UPDATE tb_mensagens
	SET lido_mensagem = TRUE
	WHERE (codigo_remet_mensagem = codigo_usuario_remetente
		AND codigo_dest_mensagem = codigo_usuario_login)
		OR (codigo_remet_mensagem = codigo_usuario_login
		AND codigo_dest_mensagem = codigo_usuario_remetente);
END $$


CREATE PROCEDURE sp_adicionarMensagem(IN codigo_remetente INT, IN codigo_destinatario INT, IN mensagem VARCHAR(500),
									IN data DATETIME)
BEGIN
	SELECT ip_usuario INTO @ipDest
	FROM tb_usuarios
	WHERE codigo_usuario = codigo_destinatario;

	IF (@ipDest = 'null') THEN
		SET @lidoMensagem = FALSE;
	ELSE
		SET @lidoMensagem = TRUE;
	END IF;

	INSERT INTO tb_mensagens(
		conteudo_mensagem,
		data_mensagem,
		lido_mensagem,
		codigo_remet_mensagem,
		codigo_dest_mensagem)
	VALUES (mensagem,
			data,
			@lidoMensagem,
			codigo_remetente,
			codigo_destinatario);
END $$


CREATE PROCEDURE sp_getQuantidadeMensagensNaoLidas(IN codigo_usuario_login INT)
BEGIN
	SELECT DISTINCT codigo_remet_mensagem
	FROM tb_mensagens
	WHERE codigo_dest_mensagem = codigo_usuario_login
		AND lido_mensagem = FALSE;
END $$


CREATE PROCEDURE sp_setIpUsuario(IN codigo INT, IN ip VARCHAR(15))
BEGIN
	UPDATE tb_usuarios
	SET ip_usuario = ip
	WHERE codigo_usuario = codigo;
END $$


CREATE PROCEDURE sp_adicionarAmizade(IN codigo_usuario INT, IN codigo_amigo INT)
BEGIN
	INSERT INTO tb_amigos (
		codigo_usuario_amigo,
		codigo_amigo_amigo)
	VALUES (codigo_usuario,
			codigo_amigo);
END $$


CREATE PROCEDURE sp_removerAmizade(IN codigo_usuario INT, IN codigo_amigo INT)
BEGIN
	DELETE FROM tb_amigos
	WHERE codigo_usuario_amigo = codigo_usuario
		AND codigo_amigo_amigo = codigo_amigo;
END $$


CREATE FUNCTION fc_verificarAmizade(codigo_usuario INT, codigo_amigo INT) RETURNS BOOLEAN
BEGIN
	IF (SELECT 1 FROM tb_amigos
		WHERE codigo_usuario_amigo = codigo_usuario
			AND codigo_amigo_amigo = codigo_amigo) THEN
		RETURN TRUE;
	ELSE
		RETURN FALSE;
	END IF;
END $$


CREATE FUNCTION fc_getIpUsuario(codigo INT) RETURNS VARCHAR(15)
BEGIN
	RETURN (
		SELECT ip_usuario
		FROM tb_usuarios
		WHERE codigo_usuario = codigo
	);
END $$


CREATE FUNCTION fc_verificarLogin(login VARCHAR(50), senha VARCHAR(50), admin BOOLEAN, verificarLogado BOOLEAN,
								verificarAdmin BOOLEAN) RETURNS BOOLEAN
BEGIN
	IF ((verificarLogado) AND (verificarAdmin)) THEN
		RETURN (SELECT 1 
		FROM tb_usuarios
		WHERE login_usuario = login
			AND senha_usuario = senha
			AND ip_usuario = 'null'
			AND admin_usuario = admin);
	ELSEIF ((verificarLogado) AND (verificarAdmin = FALSE)) THEN
		RETURN (SELECT 1 
		FROM tb_usuarios
		WHERE login_usuario = login
			AND senha_usuario = senha
			AND ip_usuario = 'null');
	ELSEIF ((verificarLogado = FALSE) AND (verificarAdmin)) THEN
		RETURN (SELECT 1 
		FROM tb_usuarios
		WHERE login_usuario = login
			AND senha_usuario = senha
			AND admin_usuario = admin);
	ELSE
		RETURN (SELECT 1 
		FROM tb_usuarios
		WHERE login_usuario = login
			AND senha_usuario = senha);
	END IF;
END $$


-- Server

CREATE PROCEDURE sp_adicionarCargo(IN codigo INT, IN descricao VARCHAR(45))
BEGIN
	INSERT INTO tb_cargos (codigo_cargo,
		desc_cargo)
	VALUES (codigo,
			descricao);
END $$


CREATE PROCEDURE sp_editarCargo(IN codigo INT, IN descricao VARCHAR(45))
BEGIN
	UPDATE tb_cargos
	SET desc_cargo = descricao
	WHERE codigo_cargo = codigo;
END $$


CREATE PROCEDURE sp_apagarCargo(IN codigo INT)
BEGIN
	DELETE FROM tb_cargos
	WHERE codigo_cargo = codigo;
END $$


CREATE PROCEDURE sp_adicionarUsuario(IN codigo INT, IN nome VARCHAR(45), IN cargo VARCHAR(45), IN login VARCHAR(50),
									IN senha VARCHAR(50), IN ip VARCHAR(15), IN admin BOOLEAN, IN foto BLOB)
BEGIN
	IF (foto = NULL) THEN
		INSERT INTO tb_usuarios (codigo_usuario,
			nome_usuario,
			codigo_cargo,
			login_usuario,
			senha_usuario,
			ip_usuario,
			admin_usuario)
		VALUES (codigo,
				nome,
				(SELECT codigo_cargo FROM tb_cargos WHERE desc_cargo = cargo),
				login,
				senha,
				ip,
				admin);
	ELSE
		INSERT INTO tb_usuarios (codigo_usuario,
			nome_usuario,
			codigo_cargo,
			login_usuario,
			senha_usuario,
			ip_usuario,
			admin_usuario,
			foto_usuario)
		VALUES (codigo,
				nome,
				(SELECT codigo_cargo FROM tb_cargos WHERE desc_cargo = cargo),
				login,
				senha,
				ip,
				admin,
				foto);
	END IF;
END $$


CREATE PROCEDURE sp_editarUsuario(IN codigo INT, IN nome VARCHAR(45), IN cargo VARCHAR(45), IN login VARCHAR(50),
								IN senha VARCHAR(50), IN ip VARCHAR(15), IN admin BOOLEAN, IN foto BLOB)
BEGIN
	IF (foto = NULL) THEN
		UPDATE tb_usuarios SET
			codigo_usuario = codigo,
			nome_usuario = nome,
			codigo_cargo = (SELECT codigo_cargo FROM tb_cargos WHERE desc_cargo = cargo),
			login_usuario = login,
			senha_usuario = senha,
			ip_usuario = ip,
			admin_usuario = admin
		WHERE codigo_usuario = codigo;
	ELSE
		UPDATE tb_usuarios SET
			codigo_usuario = codigo,
			nome_usuario = nome,
			codigo_cargo = (SELECT codigo_cargo FROM tb_cargos WHERE desc_cargo = cargo),
			login_usuario = login,
			senha_usuario = senha,
			ip_usuario = ip,
			admin_usuario = admin,
			foto_usuario = foto
		WHERE codigo_usuario = codigo;
	END IF;
END $$


CREATE PROCEDURE sp_apagarUsuario(IN codigo INT)
BEGIN
	DELETE FROM tb_usuarios WHERE codigo_usuario = codigo;
END $$


CREATE PROCEDURE sp_getCargos()
BEGIN
	SELECT * FROM tb_cargos;
END $$


CREATE PROCEDURE sp_getUsuarios()
BEGIN
	SELECT * FROM vw_usuarios;
END $$


CREATE FUNCTION fc_verificarDispDesc(descricao VARCHAR(45)) RETURNS BOOLEAN
BEGIN
	IF (SELECT 1 
			FROM tb_cargos 
			WHERE desc_cargo = descricao) THEN
		RETURN FALSE;
	ELSE
		RETURN TRUE;
	END IF;
END $$


CREATE FUNCTION fc_verificarDispLogin(login VARCHAR(50)) RETURNS BOOLEAN
BEGIN
	IF (SELECT 1 
			FROM tb_usuarios 
			WHERE login_usuario = login) THEN
		RETURN FALSE;
	ELSE
		RETURN TRUE;
	END IF;
END $$


CREATE FUNCTION fc_getNovoCodigoCargo() RETURNS INT
BEGIN
	RETURN ((
		SELECT codigo_cargo 
		FROM tb_cargos
		ORDER BY (codigo_cargo) DESC LIMIT 1)
		+ 1
	);
END $$


CREATE FUNCTION fc_getNovoCodigoUsuario() RETURNS INT
BEGIN
	RETURN ((
		SELECT codigo_usuario 
		FROM tb_usuarios 
		ORDER BY (codigo_usuario) DESC LIMIT 1)
		+ 1
	);
END $$


CREATE OR REPLACE VIEW vw_usuarios AS
	SELECT codigo_usuario,
		nome_usuario,
		login_usuario,
		senha_usuario,
		foto_usuario,
		ip_usuario,
		admin_usuario,
		tb_cargos.desc_cargo
	FROM tb_usuarios
		LEFT JOIN tb_cargos
		ON tb_usuarios.codigo_cargo = tb_cargos.codigo_cargo;


CREATE TRIGGER tri_apaga_cargo_before BEFORE DELETE
ON tb_cargos FOR EACH ROW
BEGIN
	UPDATE tb_usuarios
	SET codigo_cargo = NULL
	WHERE codigo_cargo = OLD.codigo_cargo;
END $$


CREATE TRIGGER tri_apaga_usuario_before BEFORE DELETE
ON tb_usuarios FOR EACH ROW
BEGIN
	DELETE FROM tb_amigos
	WHERE codigo_usuario_amigo = OLD.codigo_usuario
		OR codigo_amigo_amigo = OLD.codigo_usuario;

	DELETE FROM tb_mensagens
	WHERE codigo_remet_mensagem = OLD.codigo_usuario
		OR codigo_dest_mensagem = OLD.codigo_usuario;
END $$