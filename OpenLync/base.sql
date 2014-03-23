CREATE DATABASE OpenLync;
USE OpenLync;

CREATE TABLE tb_usuarios (
	codigo_usuario INT(3) PRIMARY KEY AUTO_INCREMENT,
	nome_usuario VARCHAR(45) NOT NULL,
	login_usuario VARCHAR(10) NOT NULL,
	senha_usuario VARCHAR(10) NOT NULL,
	status_usuario boolean,
	ip_usuario VARCHAR(15)
);

INSERT INTO tb_usuarios(nome_usuario, login_usuario, senha_usuario, status_usuario) VALUES ('Roberto Luiz Debarba', 'roberto', '123', false);
INSERT INTO tb_usuarios(nome_usuario, login_usuario, senha_usuario, status_usuario) VALUES ('Maria da Silva', 'maria', '123', false);
INSERT INTO tb_usuarios(nome_usuario, login_usuario, senha_usuario, status_usuario) VALUES ('Joao Felipe', 'joao', '123', false);