create table products(
  id int not null primary key AUTO_INCREMENT,
	name varchar(100) not null,
  num_in_stock int not null,
  category varchar(50)
) ENGINE=InnoDB;

insert into products(name, num_in_stock, category) values
    ('INTEL Core i3-8100', 500, 'CPU'),
    ('AMD Ryzen 1600', 1000, 'CPU'),
    ('Gigabyte B350', 300, 'Motherboard'),
    ('Asus H110', 400, 'Motherboard');

DELIMITER //
	CREATE PROCEDURE GetAllProducts()
	BEGIN
	SELECT * FROM products;
	END //
DELIMITER ;
