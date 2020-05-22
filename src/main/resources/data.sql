-- Users
insert into user(name, password, username, user_type, active, premium_customer)
values ('Test Testsson', 'Password', 'test','CUSTOMER', true, false),
('Test2 Testsson2', 'Password2', 'test2','ADMIN', true, false),
 ('kund1', 'Pass', 'kund1', 'CUSTOMER', true, false),
 ('kund2', 'Pass', 'kund2', 'CUSTOMER', true, true);

-- Products
insert into product(base_price, description, product_name, image_path)
values (100, 'Blabla', 'kaffe', 'Static/image/Coffee-Beans-Shop-Packaging-PNG-daily-roasted-coffee-beans.png'),
(101, 'Solen', 'kaffe1', 'Static/image/Coffee-Beans-Shop-Packaging-PNG-daily-roasted-coffee-beans.png'),
 (102, 'Blabla', 'kaffe2', 'Static/image/Coffee-Beans-Shop-Packaging-PNG-daily-roasted-coffee-beans.png'),
 (103, 'Blabla', 'kaffe3', 'Static/image/Coffee-Beans-Shop-Packaging-PNG-daily-roasted-coffee-beans.png');

-- Purchase
INSERT into purchase(order_number, status, customer_id)
values ('2f903cef-3696-47a1-8664-5da0e187c9be', 'COMPLETED', 3),
('4cdf6e4c-9849-11ea-bb37-0242ac130002', 'COMPLETED', 3),
('726a28fa-812e-48b3-bddd-e3fb9aa40fc9', 'IN_PROGRESS', 3),
('4cdf6a78-9849-11ea-bb37-0242ac130002', 'COMPLETED', 4);

-- Purchase_entry
insert into purchase_entry(product_id, purchase_id, current_price, quantity)
values (1, 1, 100, 2),
 (1, 2, 100, 6),
 (1, 3, 100, 3),
 (2, 3, 101, 4),
 (1, 4, 100, 5);