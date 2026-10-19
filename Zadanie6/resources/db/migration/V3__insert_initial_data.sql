-- Вставляем пользователей
INSERT INTO users (username) VALUES
('Vladimir'),
('Vladislav'),
('Yaroslav'),
('Kseniya'),
('Juliya');

-- product_type: RKO, CRED, DEPOSIT
INSERT INTO products (account_number, balance, product_type, user_id) VALUES
('ACC-RKO-001', 10000.00, 'RKO', 1),
('ACC-CRED-001', 5000.00, 'CRED', 1),
('ACC-DEPOSIT-001', 20000.00, 'DEPOSIT', 1),

('ACC-RKO-002', 10000.00, 'RKO', 2),
('ACC-CRED-002', 5000.00, 'CRED', 2),
('ACC-DEPOSIT-002', 20000.00, 'DEPOSIT', 2),

('ACC-RKO-003', 10000.00, 'RKO', 3),
('ACC-CRED-003', 5000.00, 'CRED', 3),
('ACC-DEPOSIT-003', 20000.00, 'DEPOSIT', 3),

('ACC-RKO-004', 10000.00, 'RKO', 4),
('ACC-CRED-004', 5000.00, 'CRED', 4),
('ACC-DEPOSIT-004', 20000.00, 'DEPOSIT', 4),

('ACC-RKO-005', 10000.00, 'RKO', 5),
('ACC-CRED-005', 5000.00, 'CRED', 5),
('ACC-DEPOSIT-005', 20000.00, 'DEPOSIT', 5);