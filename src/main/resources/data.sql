-- Users
insert into user(name, password, username, user_type, role, active, premium_customer)
values ('Test Testsson', 'Password', 'test', 'A','ADMIN', true, false);
values ('Test2 Testsson2', 'Password2', 'test2', 'A','ADMIN', true, false);

-- Products
insert into product(base_price, description, product_name, image_path)
values (100, 'Blabla', 'kaffe', '/test')