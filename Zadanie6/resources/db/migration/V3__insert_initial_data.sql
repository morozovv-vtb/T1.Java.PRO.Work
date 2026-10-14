-- Вставляем пользователей
INSERT INTO users (username) VALUES
('Vladimir'),
('Vladislav'),
('Yaroslav'),
('Kseniya'),
('Juliya');

-- Вставляем продукты для каждого пользователя (id 1–5)
INSERT INTO products (account_number, balance, product_type, user_id) VALUES
('ACC1001', 5000.00, 'Счет', 1),
('CARD2001', 2500.00, 'Карта', 1),
('ACC1001', 5000.00, 'Счет', 2),
('CARD2001', 2500.00, 'Карта', 2),
('ACC1001', 5000.00, 'Счет', 3),
('CARD2001', 2500.00, 'Карта', 3),
('ACC1001', 5000.00, 'Счет', 4),
('CARD2001', 2500.00, 'Карта', 4),
('ACC1001', 5000.00, 'Счет', 5),
('CARD2001', 2500.00, 'Карта', 5);