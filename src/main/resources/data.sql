-- Users
insert into user(name, password, username, user_type, active, premium_customer)
values ('Test Testsson', 'Password', 'test', 'ADMIN', true, false);

-- Products
insert into product(base_price, description, product_name, image_path)
values (100, 'Blabla', 'kaffe', '/test')