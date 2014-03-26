CREATE DATABASE OpenLync;
USE OpenLync;

CREATE TABLE tb_usuarios (
	codigo_usuario INT(3) PRIMARY KEY AUTO_INCREMENT,
	nome_usuario VARCHAR(45) NOT NULL,
	cargo_usuario VARCHAR(45) NOT NULL,
	login_usuario VARCHAR(10) NOT NULL,
	senha_usuario VARCHAR(10) NOT NULL,
	status_usuario boolean,
	ip_usuario VARCHAR(15),
	foto_usuario BLOB
);

INSERT INTO tb_usuarios(nome_usuario, cargo_usuario, login_usuario, senha_usuario, status_usuario, ip_usuario) VALUES ('Roberto Luiz Debarba', 'Programador de Sistemas', 'roberto', '123', true, '192.168.152.1');
INSERT INTO tb_usuarios(nome_usuario, cargo_usuario, login_usuario, senha_usuario, status_usuario, ip_usuario) VALUES ('Maria da Silva', 'Analista', 'maria', '123', true, '192.168.152.128');
INSERT INTO tb_usuarios(nome_usuario, cargo_usuario, login_usuario, senha_usuario, status_usuario, ip_usuario) VALUES ('Joao Felipe', 'Secretaria', 'joao', '123', true, '8.8.8.8');