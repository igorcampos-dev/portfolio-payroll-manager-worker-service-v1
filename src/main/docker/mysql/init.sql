-- create databases

CREATE DATABASE dev;
CREATE DATABASE hml;
CREATE DATABASE prd;
CREATE DATABASE locale;
CREATE DATABASE rh;

-- Cria a tabela e insere dados da database rh

USE rh;

CREATE TABLE employees_rh (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone_number VARCHAR(20) UNIQUE NOT NULL,
    profession VARCHAR(50) NOT NULL
);

INSERT INTO employees_rh (id, phone_number, name, cpf, email, profession) VALUES
('1', '51987654321', 'João Silva', '12345678901', 'joao.silva@example.com', 'CAIXA'),
('2', '51987654322', 'Maria Santos', '93591582085', 'maria.santos@example.com', 'ATENDENTE_DE_BALCAO'),
('3', '51987654323', 'Carlos Oliveira', '37999816086', 'carlos.oliveira@example.com', 'REPOSITOR'),
('4', '51987654324', 'Ana Souza', '21191783057', 'ana.souza@example.com', 'FISCAL_DE_LOJA'),
('5', '51987654325', 'Pedro Costa', '52686952075', 'pedro.costa@example.com', 'AUXILIAR_DE_LIMPEZA'),
('6', '51987654326', 'Juliana Pereira', '29820320070', 'juliana.pereira@example.com', 'ACOUGUEIRO'),
('7', '51987654327', 'Lucas Santos', '48435315029', 'lucas.santos@example.com', 'PADEIRO'),
('8', '51987654328', 'Mariana Lima', '63227620014', 'mariana.lima@example.com', 'CONFEITEIRO'),
('9', '51987654329', 'Rafael Oliveira', '92405162040', 'rafael.oliveira@example.com', 'GERENTE_DE_LOJA'),
('10', '51987654330', 'Fernanda Silva', '51863009000', 'fernanda.silva@example.com', 'ASSISTENTE_DE_COMPRAS'),
('11', '51987654331', 'Gabriel Costa', '99149882023', 'gabriel.costa@example.com', 'ESTOQUISTA'),
('12', '51987654332', 'Amanda Pereira', '23213826006', 'amanda.pereira@example.com', 'ENCARREGADO_DE_SETOR'),
('13', '51987654333', 'Luciana Santos', '33076441060', 'luciana.santos@example.com', 'VENDEDOR'),
('14', '51987654334', 'Gustavo Oliveira', '58505767039', 'gustavo.oliveira@example.com', 'AUXILIAR_ADMINISTRATIVO'),
('15', '51987654335', 'Patrícia Costa', '74609382032', 'patricia.costa@example.com', 'OPERADOR_DE_EMPILHADEIRA'),
('16', '51987654336', 'Rodrigo Pereira', '74013616041', 'rodrigo.pereira@example.com', 'AUXILIAR_DE_LOGISTICA'),
('17', '51987654337', 'Carla Santos', '40840242018', 'carla.santos@example.com', 'ENTREGADOR'),
('18', '51987654338', 'Paulo Oliveira', '92088830006', 'paulo.oliveira@example.com', 'SEGURANCA'),
('19', '51987654339', 'Vanessa Lima', '05340447006', 'vanessa.lima@example.com', 'AUXILIAR_DE_CAIXA'),
('20', '51987654340', 'Bruno Silva', '73702040064', 'bruno.silva@example.com', 'AUXILIAR_DE_COZINHA');