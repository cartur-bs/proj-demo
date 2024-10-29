CREATE TABLE Vendas (
id UUID PRIMARY KEY UNIQUE NOT NULL,
numbers INTEGER[],  -- Usando um array para armazenar a lista de inteiros
phone_number VARCHAR(15)
);