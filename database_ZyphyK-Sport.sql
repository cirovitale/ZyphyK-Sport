DROP SCHEMA IF EXISTS zyphykSport;
CREATE SCHEMA IF NOT EXISTS zyphykSport;
USE zyphykSport;

CREATE TABLE IF NOT EXISTS gestoriCatalogo(
	username varchar(20) NOT NULL,
  	name varchar(20) NOT NULL,
  	surname varchar(20) NOT NULL,
  	email varchar(50) NOT NULL,
  	pass_word varchar(50) NOT NULL,
  	ral int NOT NULL,

	PRIMARY KEY(username)
); 
 
CREATE TABLE IF NOT EXISTS gestoriOrdini(
	username varchar(20) NOT NULL,
  	name varchar(20) NOT NULL,
  	surname varchar(20) NOT NULL,
  	email varchar(50) NOT NULL,
  	pass_word varchar(50) NOT NULL,
  	ral int NOT NULL,

	PRIMARY KEY(username)
); 

 CREATE TABLE IF NOT EXISTS carts(
	id int NOT NULL,
  	amount int DEFAULT 0,

	PRIMARY KEY(id)
); 
 
 CREATE TABLE IF NOT EXISTS clienti(
	username varchar(20) NOT NULL,
	cartId int NOT NULL,
  	name varchar(20) NOT NULL,
  	surname varchar(20) NOT NULL,
  	email varchar(50) NOT NULL,
  	pass_word varchar(50) NOT NULL,
  	birthDate DATE NOT NULL,


	PRIMARY KEY(username),
	FOREIGN KEY(cartId) REFERENCES carts(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS products(
	id int NOT NULL,
	name varchar(30) NOT NULL,
	sport varchar(20) NOT NULL,
	brand varchar(20) NOT NULL,
	price int NOT NULL,
	removed boolean DEFAULT false,

	PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS sizes(
	value int NOT NULL,
	productId int NOT NULL,

	PRIMARY KEY(value, productId),
	FOREIGN KEY(productId) REFERENCES products(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS cartsContainsProds(
	cartId int NOT NULL,
	productId int NOT NULL,

	PRIMARY KEY(cartId,productId),
	FOREIGN KEY(productId) REFERENCES products(id) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(cartId) REFERENCES carts(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS managesProds(
	gestCatUsername varchar(20) NOT NULL,
	productId int NOT NULL,
	tipologia int NOT NULL,

	PRIMARY KEY(gestCatUsername,productId),
	FOREIGN KEY(productId) REFERENCES products(id) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(gestCatUsername) REFERENCES gestoriCatalogo(username) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS orders(
	id int NOT NULL,
	clienteUsername varchar(20) NOT NULL,
	gestOrdUsername varchar(20) NOT NULL,
	dateTime DATETIME NOT NULL,
	shippingAddress varchar(100) NOT NULL,
	paymentMethod varchar(30) NOT NULL,
	amount int NOT NULL DEFAULT 0,
	sent boolean NOT NULL DEFAULT false,

	PRIMARY KEY(id),
	FOREIGN KEY(clienteUsername) REFERENCES clienti(username) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(gestOrdUsername) REFERENCES gestoriOrdini(username) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS ordersContainsProds(
	orderId int NOT NULL,
	productId int NOT NULL,

	PRIMARY KEY(orderId,productId),
	FOREIGN KEY(productId) REFERENCES products(id) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(orderId) REFERENCES orders(id) ON UPDATE CASCADE ON DELETE CASCADE
);
