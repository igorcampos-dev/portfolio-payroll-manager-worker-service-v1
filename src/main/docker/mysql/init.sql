-- create databases

CREATE DATABASE dev;
CREATE DATABASE hml;
CREATE DATABASE prd;
CREATE DATABASE locale;
CREATE DATABASE rh;

-- Cria a tabela e insere dados da database rh

USE rh;

CREATE TABLE employees (
    id VARCHAR(36) PRIMARY KEY,
    phone_number VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(100) UNIQUE NOT NULL,
    profession VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    age INT,
    code INT NOT NULL,
    hire_year INT
);

INSERT INTO employees (id, phone_number, name, profession, cpf, email, age, code, hire_year) VALUES
('1', '51987654321', 'João Silva', 'CAIXA', '12345678901', 'joao.silva@example.com', 30, 123253, 2015),
('2', '51987654322', 'Maria Santos', 'ATENDENTE_DE_BALCAO', '93591582085', 'maria.santos@example.com', 28, 233424, 2016),
('3', '51987654323', 'Carlos Oliveira', 'REPOSITOR', '37999816086', 'carlos.oliveira@example.com', 25, 345580, 2017),
('4', '51987654324', 'Ana Souza', 'FISCAL_DE_LOJA', '21191783057', 'ana.souza@example.com', 33, 456157, 2018),
('5', '51987654325', 'Pedro Costa', 'AUXILIAR_DE_LIMPEZA', '52686952075', 'pedro.costa@example.com', 40, 567963, 2019),
('6', '51987654326', 'Juliana Pereira', 'ACOUGUEIRO', '29820320070', 'juliana.pereira@example.com', 35, 678258, 2020),
('7', '51987654327', 'Lucas Santos', 'PADEIRO', '48435315029', 'lucas.santos@example.com', 29, 789353, 2021),
('8', '51987654328', 'Mariana Lima', 'CONFEITEIRO', '63227620014', 'mariana.lima@example.com', 27, 890964, 2017),
('9', '51987654329', 'Rafael Oliveira', 'GERENTE_DE_LOJA', '92405162040', 'rafael.oliveira@example.com', 38, 901135, 2016),
('10', '51987654330', 'Fernanda Silva', 'ASSISTENTE_DE_COMPRAS', '51863009000', 'fernanda.silva@example.com', 31, 121642, 2015),
('11', '51987654331', 'Gabriel Costa', 'ESTOQUISTA', '99149882023', 'gabriel.costa@example.com', 26, 123356, 2019),
('12', '51987654332', 'Amanda Pereira', 'ENCARREGADO_DE_SETOR', '23213826006', 'amanda.pereira@example.com', 34, 234136, 2020),
('13', '51987654333', 'Luciana Santos', 'VENDEDOR', '33076441060', 'luciana.santos@example.com', 32, 345974, 2018),
('14', '51987654334', 'Gustavo Oliveira', 'AUXILIAR_ADMINISTRATIVO', '58505767039', 'gustavo.oliveira@example.com', 36, 456357, 2017),
('15', '51987654335', 'Patrícia Costa', 'OPERADOR_DE_EMPILHADEIRA', '74609382032', 'patricia.costa@example.com', 37, 567975, 2021),
('16', '51987654336', 'Rodrigo Pereira', 'AUXILIAR_DE_LOGISTICA', '74013616041', 'rodrigo.pereira@example.com', 30, 678534, 2015),
('17', '51987654337', 'Carla Santos', 'ENTREGADOR', '40840242018', 'carla.santos@example.com', 28, 789357, 2016),
('18', '51987654338', 'Paulo Oliveira', 'SEGURANCA', '92088830006', 'paulo.oliveira@example.com', 35, 890435, 2018),
('19', '51987654339', 'Vanessa Lima', 'AUXILIAR_DE_CAIXA', '05340447006', 'vanessa.lima@example.com', 27, 901357, 2019),
('20', '51987654340', 'Bruno Silva', 'AUXILIAR_DE_COZINHA', '73702040064', 'bruno.silva@example.com', 33, 127865, 2020);