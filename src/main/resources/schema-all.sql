DROP TABLE autobot IF EXISTS;

CREATE TABLE autobot  (
    autobot_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    id VARCHAR(50),
    variavel1 VARCHAR(50),
    variavel2 VARCHAR(50),
    variavel3 VARCHAR(50),
    totalVendedor float
);


CREATE TABLE item  (
    autobot_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    id VARCHAR(50),
    quantidade Int,
    preco float,
    valorTotal float,
    idVenda int
);