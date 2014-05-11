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
	codigo_cargo INT(3) NOT NULL,
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
	conteudo_mensagem VARCHAR(150) NOT NULL,
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

INSERT INTO tb_usuarios(nome_usuario, login_usuario, senha_usuario, ip_usuario, codigo_cargo, foto_usuario)
VALUES ('Roberto Luiz Debarba', '35685347493069331629356853630934749'/*roberto*/, '154051571716029'/*123*/, 'null', 1, LOAD_FILE('/home/roberto/roberto.jpg'));

INSERT INTO tb_usuarios(nome_usuario, login_usuario, senha_usuario, ip_usuario, codigo_cargo, foto_usuario)
VALUES ('Maria da Silva', '3412530381356853287730381'/*maria*/, '154051571716029'/*123*/, 'null', 2, LOAD_FILE('/home/roberto/maria.jpg'));

INSERT INTO tb_usuarios(nome_usuario, login_usuario, senha_usuario, ip_usuario, codigo_cargo, foto_usuario)
VALUES ('João Felipe Figueiro', '33189347493038134749'/*joao*/, '154051571716029'/*123*/, 'null', 3, LOAD_FILE('/home/roberto/joao.jpg'));

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
VALUES ('Mensagem de teste0001. Hello World!', '2014-04-08 12:24:36', true, 1, 2);

INSERT INTO tb_mensagens(conteudo_mensagem, data_mensagem, lido_mensagem, codigo_remet_mensagem, codigo_dest_mensagem)
VALUES ('Mensagem de teste0002. Hello World!', '2014-04-08 12:24:36', true, 1, 2);

INSERT INTO tb_mensagens(conteudo_mensagem, data_mensagem, lido_mensagem, codigo_remet_mensagem, codigo_dest_mensagem)
VALUES ('Mensagem de teste0003. Hello World!', '2014-04-08 12:24:36', true, 1, 3);

INSERT INTO tb_mensagens(conteudo_mensagem, data_mensagem, lido_mensagem, codigo_remet_mensagem, codigo_dest_mensagem)
VALUES ('Mensagem de teste0004. Hello World!', '2014-04-08 12:24:36', true, 2, 1);