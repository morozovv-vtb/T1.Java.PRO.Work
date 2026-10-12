-- Создание таблицы users
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE
);

-- Создание таблицы products
CREATE TABLE IF NOT EXISTS products (
    id BIGSERIAL PRIMARY KEY,
    account_number VARCHAR(255) NOT NULL,
    balance DECIMAL(15,2) NOT NULL,
    product_type VARCHAR(50) NOT NULL, -- 'Счет', 'Карта'
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);