-- Вставляем пользователей
INSERT INTO users (username) VALUES
('Vladimir'),
('Vladislav'),
('Yaroslav'),
('Kseniya'),
('Juliya');

-- Вставляем по 3 продукта на пользователя (RKO, CRED, DEPOSIT)
INSERT INTO products (account_number, balance, product_type, user_id) VALUES
('ACC1001', 5000.00, 'RKO', 1),
('CARD2001', 2500.00, 'CRED', 1),
('DEP3001', 10000.00, 'DEPOSIT', 1),

('ACC1002', 6000.00, 'RKO', 2),
('CARD2002', 3000.00, 'CRED', 2),
('DEP3002', 12000.00, 'DEPOSIT', 2),

('ACC1003', 7000.00, 'RKO', 3),
('CARD2003', 3500.00, 'CRED', 3),
('DEP3003', 15000.00, 'DEPOSIT', 3),

('ACC1004', 4000.00, 'RKO', 4),
('CARD2004', 2000.00, 'CRED', 4),
('DEP3004', 8000.00, 'DEPOSIT', 4),

('ACC1005', 5500.00, 'RKO', 5),
('CARD2005', 2800.00, 'CRED', 5),
('DEP3005', 11000.00, 'DEPOSIT', 5);