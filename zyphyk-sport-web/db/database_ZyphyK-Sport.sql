DROP SCHEMA IF EXISTS zyphyk_sport;
CREATE SCHEMA IF NOT EXISTS zyphyk_sport;
USE zyphyk_sport;

CREATE TABLE IF NOT EXISTS gestori_catalogo(
	username varchar(20) NOT NULL,
  	name varchar(20) NOT NULL,
  	surname varchar(20) NOT NULL,
  	email varchar(50) NOT NULL,
  	pass_word varchar(50) NOT NULL,
  	ral int NOT NULL,
  	roles varchar(8) DEFAULT 'gestCat',

	PRIMARY KEY(username)
); 
 
CREATE TABLE IF NOT EXISTS gestori_ordini(
	username varchar(20) NOT NULL,
  	name varchar(20) NOT NULL,
  	surname varchar(20) NOT NULL,
  	email varchar(50) NOT NULL,
  	pass_word varchar(50) NOT NULL,
  	ral int NOT NULL,
  	roles varchar(8) DEFAULT 'gestOrd',

	PRIMARY KEY(username)
); 

 CREATE TABLE IF NOT EXISTS carts(
	id int NOT NULL,
  	amount int DEFAULT 0,

	PRIMARY KEY(id)
); 
 
 CREATE TABLE IF NOT EXISTS clienti(
	username varchar(20) NOT NULL,
	cart_id int NOT NULL,
  	name varchar(20) NOT NULL,
  	surname varchar(20) NOT NULL,
  	email varchar(50) NOT NULL,
  	pass_word varchar(50) NOT NULL,
  	birth_date DATE NOT NULL,
  	roles varchar(8) DEFAULT 'cliente',


	PRIMARY KEY(username),
	FOREIGN KEY(cart_id) REFERENCES carts(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS products(
	id VARCHAR(5) NOT NULL,
	name varchar(30) NOT NULL,
	sport varchar(20) NOT NULL,
	brand varchar(20) NOT NULL,
	price int NOT NULL,
	removed boolean DEFAULT false,

	PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS sizes(
	value int,
	product_id varchar(5),
    count int NOT NULL auto_increment,

	PRIMARY KEY(count),
	FOREIGN KEY(product_id) REFERENCES products(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS carts_contains_prods(
	cart_id int NOT NULL,
	product_id varchar(5) NOT NULL,
    quantity int,

	PRIMARY KEY(cart_id,product_id),
	FOREIGN KEY(product_id) REFERENCES products(id) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(cart_id) REFERENCES carts(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS manages_prods(
	gest_cat_username varchar(20) NOT NULL,
	product_id varchar(5) NOT NULL,
	tipologia int NOT NULL,

	PRIMARY KEY(gest_cat_username,product_id),
	FOREIGN KEY(product_id) REFERENCES products(id) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(gest_cat_username) REFERENCES gestori_catalogo(username) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS orders(
	id int NOT NULL,
	cliente_username varchar(20) NOT NULL,
	gest_ord_username varchar(20),
	date_time DATETIME NOT NULL,
	shipping_address varchar(100) NOT NULL,
	payment_method varchar(30) NOT NULL,
	amount int NOT NULL DEFAULT 0,
	sent boolean NOT NULL,

	PRIMARY KEY(id),
	FOREIGN KEY(cliente_username) REFERENCES clienti(username) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(gest_ord_username) REFERENCES gestori_ordini(username) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS orders_contains_prods(
	order_id int NOT NULL,
	product_id varchar(5) NOT NULL,
    quantity int,
    
	PRIMARY KEY(order_id,product_id),
	FOREIGN KEY(product_id) REFERENCES products(id) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(order_id) REFERENCES orders(id) ON UPDATE CASCADE ON DELETE CASCADE
);
