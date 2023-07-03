CREATE SCHEMA food_app;
GO

IF (NOT EXISTS (SELECT * 
                 FROM INFORMATION_SCHEMA.TABLES 
                 WHERE TABLE_SCHEMA = 'food_app' 
                 AND  TABLE_NAME = 'customer'))
BEGIN
    CREATE TABLE food_app.customer (
        id int NOT NULL IDENTITY(1,1), 
        name varchar(255) NOT NULL, 
        address VARCHAR(255), 
        phone VARCHAR(255),
        PRIMARY KEY (id)
        );
END

IF (NOT EXISTS (SELECT * 
                 FROM INFORMATION_SCHEMA.TABLES 
                 WHERE TABLE_SCHEMA = 'food_app' 
                 AND  TABLE_NAME = 'restaurant'))
BEGIN
    CREATE TABLE food_app.restaurant (
        id int NOT NULL IDENTITY(1,1), 
        name varchar(255) NOT NULL, 
        address VARCHAR(255), 
        status VARCHAR(255),
        PRIMARY KEY (id)
    );
END


IF (NOT EXISTS (SELECT * 
                 FROM INFORMATION_SCHEMA.TABLES 
                 WHERE TABLE_SCHEMA = 'food_app' 
                 AND  TABLE_NAME = 'delivery_agent'))
BEGIN
    CREATE TABLE food_app.delivery_agent (
        id int NOT NULL IDENTITY(1,1), 
        name varchar(255) NOT NULL, 
        phone VARCHAR(255), 
        status VARCHAR(255),
        PRIMARY KEY (id)
        );
END

IF (NOT EXISTS (SELECT * 
                 FROM INFORMATION_SCHEMA.TABLES 
                 WHERE TABLE_SCHEMA = 'food_app' 
                 AND  TABLE_NAME = 'menu'))
BEGIN
    CREATE TABLE food_app.menu (
        id int NOT NULL IDENTITY(1,1), 
        restaurant_id int,
        menu_name varchar(255) NOT NULL, 
        PRIMARY KEY (id),
        CONSTRAINT fk_menu_restuarant FOREIGN KEY (restaurant_id)
        REFERENCES food_app.restaurant (id)
        );
END

IF (NOT EXISTS (SELECT * 
                 FROM INFORMATION_SCHEMA.TABLES 
                 WHERE TABLE_SCHEMA = 'food_app' 
                 AND  TABLE_NAME = 'menu_category'))
BEGIN
    CREATE TABLE food_app.menu_category (
        id int NOT NULL IDENTITY(1,1), 
        menu_id int,
        category_name varchar(255) NOT NULL, 
        PRIMARY KEY (id),
        CONSTRAINT fk_menu_category_id FOREIGN KEY (menu_id)
        REFERENCES food_app.menu (id)
        );
END

IF (NOT EXISTS (SELECT * 
                 FROM INFORMATION_SCHEMA.TABLES 
                 WHERE TABLE_SCHEMA = 'food_app' 
                 AND  TABLE_NAME = 'menu_item'))
BEGIN    
    CREATE TABLE food_app.menu_item (
        id int NOT NULL IDENTITY(1,1), 
        menu_id int,
        category_id int,
        item_name varchar(255) NOT NULL, 
        price float,
        status VARCHAR(255) NOT NULL,
        PRIMARY KEY (id),
        CONSTRAINT fk_menu_item FOREIGN KEY (menu_id)
        REFERENCES food_app.menu (id),
        CONSTRAINT fk_menu_item_category FOREIGN KEY (category_id)
        REFERENCES food_app.menu_category (id)
        );
END

IF (NOT EXISTS (SELECT * 
                 FROM INFORMATION_SCHEMA.TABLES 
                 WHERE TABLE_SCHEMA = 'food_app' 
                 AND  TABLE_NAME = 'food_order'))
BEGIN
    CREATE TABLE food_app.food_order (
        id varchar(255) NOT NULL, 
        restaurant_id int,
        customer_id int,
        delivery_agent_id int,
        order_time varchar(255),
        delivery_time varchar(255),
        delivery_address varchar(255) , 
        order_price float,
        order_status VARCHAR(255),
        PRIMARY KEY (id),
        CONSTRAINT fk_food_order_restaurant FOREIGN KEY (restaurant_id)
        REFERENCES food_app.restaurant (id),
        CONSTRAINT fk_food_order_customer FOREIGN KEY (customer_id)
        REFERENCES food_app.customer (id),
        CONSTRAINT fk_food_order_delivery_agent FOREIGN KEY (delivery_agent_id)
        REFERENCES food_app.delivery_agent (id)
        );  
END

IF (NOT EXISTS (SELECT * 
                 FROM INFORMATION_SCHEMA.TABLES 
                 WHERE TABLE_SCHEMA = 'food_app' 
                 AND  TABLE_NAME = 'food_order_item'))
BEGIN
    CREATE TABLE food_app.food_order_item (
        id int NOT NULL IDENTITY(1,1), 
        item_id int,
        category_id int,
        order_id varchar(255),
        count int,
        item_price float,
        PRIMARY KEY (id),
        CONSTRAINT fk_order_item FOREIGN KEY (item_id)
        REFERENCES food_app.menu_item (id),
        CONSTRAINT fk_order_item_category FOREIGN KEY (category_id)
        REFERENCES food_app.menu_category (id),
        CONSTRAINT fk_order_item_order_id FOREIGN KEY (order_id)
        REFERENCES food_app.food_order (id),
        );        
END

insert 
into
    food_app.customer
    (address, name, phone) 
    values
    ('Dublin 1', 'Nithin', '1232123')
insert 
into
    food_app.customer
    (address, name, phone) 
    values
    ('Dublin 2', 'Bharath', '1784541')
insert
into
    food_app.customer
(address, name, phone)
values
    ('Dublin 3', 'Praveen', '9279812')
insert 
into
    food_app.delivery_agent
    (name, phone, status) 
    values
    ('Agent1', '951159', 'AVAILABLE')
insert 
into
    food_app.delivery_agent
    (name, phone, status) 
    values
    ('Agent2', '753357', 'AVAILABLE')
insert
into
    food_app.delivery_agent
(name, phone, status)
values
    ('Agent3', '098234', 'AVAILABLE')
insert 
into
    food_app.restaurant
    (address, name, status) 
    values
    ('Dublin 1', 'Temple Bar', 'ACTIVE')
insert 
into
    food_app.restaurant
    (address, name, status) 
    values
    ('Dublin 3', 'McDonalds', 'ACTIVE')
insert
into
    food_app.restaurant
(address, name, status)
values
    ('Dublin 5', 'Dominos', 'ACTIVE')
insert 
into
    food_app.menu
    (restaurant_id, menu_name) 
    values
    (1, 'Dinner')
insert 
into
    food_app.menu
    (restaurant_id, menu_name) 
    values
    (2, 'Breakfast')
insert
into
    food_app.menu
(restaurant_id, menu_name)
values
    (3, 'Pizza Mania')

insert 
into
    food_app.menu_category
    (menu_id, category_name) 
    values
    (1, 'Vegan')
insert 
into
    food_app.menu_category
    (menu_id, category_name) 
    values
    (1, 'Non-Veg')
insert 
into
    food_app.menu_category
    (menu_id, category_name) 
    values
    (2, 'Veg')
insert 
into
    food_app.menu_category
    (menu_id, category_name) 
    values
    (2, 'Egg')

insert
into
    food_app.menu_category
(menu_id, category_name)
values
    (3, 'Veg')
insert
into
    food_app.menu_category
(menu_id, category_name)
values
    (3, 'Non-Veg')
insert 
into
    food_app.menu_item
    (menu_id, category_id, item_name, price, status) 
    values
    (1, 1, 'Vegan Roll', 3, 'ACTIVE')
insert 
into
    food_app.menu_item
    (menu_id, category_id, item_name, price, status) 
    values
    (1, 1, 'Sandwich', 2, 'ACTIVE')
insert 
into
    food_app.menu_item
    (menu_id, category_id, item_name, price, status) 
    values
    (1, 2, 'Chicken Wings', 8, 'ACTIVE')
insert 
into
    food_app.menu_item
    (menu_id, category_id, item_name, price, status) 
    values
    (1, 2, 'Prawn Fry', 12, 'ACTIVE')

insert 
into
    food_app.menu_item
    (menu_id, category_id, item_name, price, status) 
    values
    (2, 3, 'Pizza with vegan cheese', 13, 'ACTIVE')
insert 
into
    food_app.menu_item
    (menu_id, category_id, item_name, price, status) 
    values
    (2, 3, 'Sub', 8, 'ACTIVE')
insert 
into
    food_app.menu_item
    (menu_id, category_id, item_name, price, status) 
    values
    (2, 4, 'Omlet', 3, 'ACTIVE')
insert 
into
    food_app.menu_item
    (menu_id, category_id, item_name, price, status) 
    values
    (2, 4, 'Egg Burji', 4, 'ACTIVE')
GO




insert
into
    food_app.menu_item
(menu_id, category_id, item_name, price, status)
values
    (3, 5, 'Double Cheese Margherita', 25, 'ACTIVE')
insert
into
    food_app.menu_item
(menu_id, category_id, item_name, price, status)
values
    (3, 5, 'Veggie Loaded', 36, 'ACTIVE')
insert
into
    food_app.menu_item
(menu_id, category_id, item_name, price, status)
values
    (3, 6, 'Chicken Tikka Pizza', 18, 'ACTIVE')
insert
into
    food_app.menu_item
(menu_id, category_id, item_name, price, status)
values
    (3, 6, 'Mutton Pizza', 14, 'ACTIVE')
    GO

CREATE VIEW food_app.restaurant_menu_view 
AS
SELECT mi.id, r.id AS restaurant_id,r.name AS restaurant_name, m.id AS menu_id, m.menu_name, mc.id AS category_id, mc.category_name, mi.item_name, mi.price 
FROM food_app.restaurant AS r, food_app.menu AS m, food_app.menu_category AS mc, food_app.menu_item AS mi 
WHERE r.[status] = 'ACTIVE' AND r.id = m.restaurant_id AND m.id = mc.menu_id AND mc.id = mi.category_id AND mc.menu_id = m.id AND mi.status = 'ACTIVE';
GO

CREATE VIEW food_app.food_order_menu_item_view 
AS
SELECT foi.id, mi.id AS item_id, mi.item_name, foi.order_id, foi.count 
FROM food_app.food_order_item AS foi, food_app.menu_item AS mi
WHERE foi.item_id=mi.id
GO

CREATE VIEW food_app.food_order_view 
AS
SELECT fo.id, fo.restaurant_id, r.name AS restaurant_name, fo.customer_id, c.name AS customer_name, fo.order_time, fo.delivery_time, fo.delivery_address, fo.order_price, fo.order_status
FROM food_app.food_order AS fo, food_app.restaurant AS r, food_app.customer AS c
WHERE fo.restaurant_id = r.id AND fo.customer_id = c.id
GO

CREATE VIEW food_app.delivery_agent_orders_view
AS
SELECT fo.id,r.name AS restaurant,c.name AS customer_name,fo.delivery_time,fo.delivery_address,fo.order_price,fo.order_status,fo.delivery_agent_id
FROM food_app.restaurant as r, food_app.food_order as fo, food_app.customer as c
WHERE r.id = fo.restaurant_id AND c.id = fo.customer_id
GO
