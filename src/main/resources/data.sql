-- Users
insert into user(name, password, username, user_type, active, premium_customer)
values ('Test Testsson', 'reg', 'reg','CUSTOMER', true, false),
('Test2 Testsson2', 'admin', 'admin','ADMIN', true, false),
 ('kund1', 'Pass', 'kund1', 'CUSTOMER', true, false),
 ('kund2', 'premium', 'premium', 'CUSTOMER', true, true);

-- Products
insert into product(base_price, description, product_name, image_path)
values (100, 'Blabla', 'kaffe', 'Static/image/Coffee-Beans-Shop-Packaging-PNG-daily-roasted-coffee-beans.png'),
(101, 'Blabla', 'kaffe1', 'Static/image/Coffee-Beans-Shop-Packaging-PNG-daily-roasted-coffee-beans.png'),
 (102, 'Blabla', 'kaffe2', 'Static/image/Coffee-Beans-Shop-Packaging-PNG-daily-roasted-coffee-beans.png'),
 (103, 'Blabla', 'kaffe3', 'Static/image/Coffee-Beans-Shop-Packaging-PNG-daily-roasted-coffee-beans.png');

-- Purchase
INSERT into purchase(order_number, status, customer_id)
values ('2f903cef-3696-47a1-8664-5da0e187c9be', 'COMPLETED', 3),
('4cdf6e4c-9849-11ea-bb37-0242ac130002', 'COMPLETED', 3),
 ('4cdf6a78-9849-11ea-bb37-0242ac130002', 'COMPLETED', 4);

-- Purchase_entry
insert into purchase_entry(product_id, purchase_id, current_price, quantity)
values (1, 1, 100, 2),
 (1, 2, 100, 2),
 (1, 3, 100, 2);