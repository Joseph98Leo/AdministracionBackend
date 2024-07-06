drop database if exists royal_paws;
create database royal_paws;
use royal_paws;

-- tables
create table tb_rol(
cod_rol int primary key auto_increment,
nom_rol varchar(45)
);

create table tb_cliente(
cod_cli int primary key auto_increment,
nom_cli varchar(50),
ape_cli varchar(50),
dni_cli int,
ema_cli varchar(50),
tel_cli int,
dir_cli varchar(150),
user_cli varchar(70),
clave varchar(70),
cod_rol int
);

create table tb_vendedor(
cod_ven int primary key auto_increment,
nom_ven varchar(50),
ape_ven varchar(50),
dni_ven int,
ema_ven varchar(50),
tel_ven int,
fec_nac date,
user_ven varchar(70),
clave varchar(70),
cod_rol int
);

create table tb_proveedor(
cod_prov int primary key auto_increment,
nom_prov varchar(169)
);


create table tb_marca(
cod_marca int primary key auto_increment,
nom_marca varchar(30),
pai_marca varchar(50)
);

create table tb_producto(
cod_prod int primary key auto_increment,
nom_prod varchar(80),
des_prod varchar(255),
cat_prod varchar(30),
stock_prod int,
precio_compra decimal(5,2),
img_prod longblob,
nom_archivo varchar(75),
cod_prov int,
cod_marca int
);

create table tb_compra(
cod_compra int primary key auto_increment,
fec_compra date,
monto_compra decimal(5,2),
mpago varchar(10),
cod_cli int,
cod_ven int
);

create table tb_detalle_compra(
cod_det_compra int primary key auto_increment,
precio decimal(5,2),
cantidad int,
cod_prod int,
cod_compra int
);

alter table tb_cliente
add constraint pk_cod_rol_1 foreign key(cod_rol) references tb_rol(cod_rol);

alter table tb_vendedor
add constraint pk_cod_rol foreign key(cod_rol) references tb_rol(cod_rol);

alter table tb_compra
add constraint pk_cod_usu foreign key(cod_cli) references tb_cliente(cod_cli);

alter table tb_compra
add constraint pk_cod_adm foreign key(cod_ven) references tb_vendedor(cod_ven);

alter table tb_producto
add constraint pk_cod_proveedor foreign key(cod_prov) references tb_proveedor(cod_prov);

alter table tb_producto
add constraint pk_cod_marca foreign key(cod_marca) references tb_marca(cod_marca);

alter table tb_detalle_compra
add constraint pk_cod_producto foreign key(cod_prod) references tb_producto(cod_prod);

alter table tb_detalle_compra
add constraint pk_cod_compra foreign key(cod_compra) references tb_compra(cod_compra);


-- inserts
insert into tb_rol values
(1, 'VENDEDOR'),
(2, 'CLIENTE');

insert into tb_cliente values
(1, 'Brandon', 'Garcia Sanchez', 74141917, 'bgarcia2698@gmail.com', 933672257, 'Tumay 196 - Barranco', 'brandon', 'admin', 2),
(2, 'Jose', 'Merino Mendieta', 71787872, 'josemm@gmail.com', 912923195, 'Comandante Espinar 123 - Miraflores', 'jose', '1010', 2),
(3, 'Abby', 'Mendez Ezeta', 78787875, 'abbym@gmail.com', 912934821, 'Las Mimosas 194 - Barranco', 'abby', '2020', 2),
(4, 'Ana Maria', 'Sanchez Davila', 10329110, 'anamaria10@gmail.com', 998063786, 'Enrique Barron 521 . Barranco', 'anita', 'kira', 2),
(5, 'Kira', 'Satoru Guzman', 78356870, 'kirara@gmail.com', 963782922, 'Santiago Figueredo 130 - Miraflores', 'kirara', 'miau', 2),
(6, 'Giuliana', 'Bazo Diaz', 73305589, 'vale@gmail.com', 912934844, 'Isidro alcibar 135 - San Miguel', 'vale', '101112', 2),
(7, 'Gonzalo', 'Hinostroza Juarez', 28787871, 'gonzalito12@gmail.com', 912934855, 'Av. Arequipa 265 - Cercado', 'gonza', '1234', 2),
(8, 'Lucia', 'Rodriguez Aparicio', 73481710, 'lucianna@gmail.com', 965165555, 'Av. Paseo de la Republica 789 - Miraflores', 'lucia', '4321', 2),
(9, 'Johan', 'Gomez Vaca', 78787879, 'pedro@gmail.com', 912934877, 'Av. Primavera 234 - Surquillo', 'johan', 'b00bs', 2),
(10, 'Michelle', 'Seguil Vilchez', 78787868, 'michi@gmail.com', 912934888, 'Santa Rosa 665 - Surquillo', 'michi', '6789', 2);

insert into tb_vendedor values
(1, 'Brandon', 'Garcia Sanchez', 72787540, 'bgarcia@gmail.com', 933672257, '1998-06-26', 'brandon', 'admin', 1),
(2, 'Alejandra', 'Morata Peña', 71782345, 'aleja@gmail.com', 924912336, '1996-01-23', 'aleja', '1234', 1),
(3, 'Jhonny', 'Connor Smith', 71787545, 'jhonny@gmail.com', 934212336, '1992-05-10', 'jhonny', 'abcd', 1),
(4, 'Luisa', 'Seminario Berdejo', 77787987, 'nani@gmail.com', 914515347, '1996-03-15', 'nani', '4321', 1),
(5, 'Enrique', 'Magot Luna', 71787959, 'enriquegot@gmail.com', 912941238, '2002-05-20', 'enrique', 'dcba', 1),
(6, 'Silvia', 'Seminario Berdejo', 71737654, 'shibi@gmail.com', 912911945, '1991-01-11', 'shibi', '6789', 1),
(7, 'Mario', 'Salinas Ríos', 71727999, 'salinasm@gmail.com', 912953347, '2003-08-21', 'mario', 'jklm', 1),
(8, 'Alejandro', 'Vergara Moreno', 71717992, 'alejo23@gmail.com', 912911245, '1999-07-18', 'alejo', '5678', 1);

insert into tb_proveedor values
(1, 'REPRESENTACIONES DURAND S.A.C.'),
(2, 'HALLMARK S.A.'),
(3, 'MARCEBEL & CIA EIRL'),
(4, 'IMPORTACIONES LUDIPEK PERU S.A.C.'),
(5, 'MARFAC S.A.C.');

insert into tb_marca values
(1, 'Matisse', 'Brasil'),
(2, 'Nutram', 'EEUU'),
(3, 'Brit Care', 'República Checa'),
(4, 'Gigwi', 'China'),
(5, 'Cats Best', 'España'),
(6, 'Bravecto', 'EEUU'),
(7, 'Advantage', 'EEUU'),
(8, 'Beaphar', 'EEUU');

insert into tb_producto values
(1, 'Matisse Adulto Castrado 7kg', 'Matisse Castrados de Salmon es un alimento completo y balanceado desarrollado especialmente para atender las necesidades de los gatos castrados.','Alimento Seco',7, 171.90, '', 'Matisse Adulto Castrado 7kg.jpg', '3', '1'),
(2, 'Nutram Cachorro S2 11.4kg', 'Receta de carne deshidratada de pollo y huevos enteros. S2 Nutram Sound Balanced Wellness® Puppy Food es una receta multi-beneficiosa y rica en nutrientes, que mejora el bienestar de su mascota de adentro hacia afuera.','Alimento Seco',4, 338.00, '', 'Nutram Cachorro S2 11.4kg.jpg', '2', '2'),
(3, 'Brit Care Weight Loss 12kg', 'Fórmula hipoalergénica Conejo y arroz para perros adultos de todas las razas con problemas de sobrepeso. Alimento completo para perros.','Alimento Seco',2, 321.00, '', 'Brit Care Weight Loss 12kg.jpg', '2', '3'),
(4, 'Gigwi Melody Chaser Bird', 'El Juguete Melody Chaser Pájaro Gigwi es un divertido juguete para gatos que producen sonido real una vez activado. Satisfacen el instinto de caza de los gatos y los mantiene activos y al acecho. Perfectos para el juego interactivo en interiores.','Juguetes',6, 27.90, '', 'Gigwi Melody Chaser Bird.jpg', '4','4'),
(5, 'Gigwi Suppa Puppa Dino', 'Los juguetes Suppa Puppa de GiGwi están especialmente diseñados para cachorros y perros pequeños. Son interactivos y perfectos para el juego en el exterior.Hecho de  plástico TPR de gran calidad.','Juguetes',2, 24.90, '', 'Gigwi Suppa Puppa Dino.jpg', '4','4'),
(6, 'Cats Best Original 8.6kg', 'Cats Best Original utiliza la fuerza natural de las fibras de madera activas refinadas. Absorbe eficazmente el líquido y el olor y los sella en el interior – hasta 7 veces su volumen.','Arena',3, 133.50, '', 'Cats Best Original 8.6kg.jpg', '1','5'),
(7, 'Cats Best Smart Pellets 10kg', 'Cats Best Smart Pellets es la primera arena para gatos en forma de mini-pellets aglomerantes. La forma especial de los pellets reduce la adherencia sobre el pelo y las patas disminuyendo notablemente la cantidad que sale de la caja del gato.','Arena',2, 162.50, '', 'Cats Best Smart Pellets 10kg.jpg', '1','5'),
(8, 'Bravecto Perros de 5 a 10kg', 'Para el tratamiento y prevención de infestaciones con pulgas y garrapatas en perros. Este medicamento de acción sistémica actúa en forma inmediata y persistente durante 12 semanas.','Antipulgas',1, 159.50, '', 'Bravecto Perros de 5 a 10kg.jpg', '5','6'),
(9, 'Bravecto Perros de 10 a 20kg', 'Para el tratamiento y prevención de infestaciones con pulgas y garrapatas en perros. Este medicamento de acción sistémica actúa en forma inmediata y persistente durante 12 semanas.','Antipulgas',1, 168.10, '', 'Bravecto Perros de 10 a 20kg.jpg', '5','6'),
(10, 'Advantage Gatos hasta 4kg', 'Protege a los gatos eliminando el 100% de las pulgas durante las primeras 12 horas. Posee acción larvicida.','Antipulgas',4, 35.00, '', 'Advantage Gatos hasta 4kg.jpg', '2','7'),
(11, 'Advantage Gatos de 4 a 8kg', 'Protege a los gatos eliminando el 100% de las pulgas durante las primeras 12 horas. Posee acción larvicida.','Antipulgas',5, 48.50, '', 'Advantage Gatos de 4 a 8kg.jpg', '2','7'),
(12, 'Beaphar Lactol Puppy Milk 250g', 'Un sustituto completo de la leche para cachorros recién nacidos y huérfanos. Contiene DHA, vitaminas y minerales esenciales.','Suplementos',3, 64.60, '', 'Beaphar Lactol Puppy Milk 250g.jpg', '3','8'),
(13, 'Beaphar Lactol Kitten Milk 250g', 'Un sustituto completo de la leche para cachorros recién nacidos y huérfanos. Contiene DHA, vitaminas y minerales esenciales.','Suplementos',5, 70.00, '', 'Beaphar Lactol Kitten Milk 250g.jpg', '3','8');

insert into tb_compra values
(1, '2023-10-12', 171.90,'Efectivo', 2, 1),
(2, '2023-10-15', 162.50,'Tarjeta',8, 3),
(3, '2023-11-05',97.00,'PLIN',6, 1),
(4, '2023-11-20',140.00,'YAPE',4, 6);

insert into tb_detalle_compra values
(1, 171.90, 1, 1, 1),
(2, 162.50, 1, 7, 2),
(3, 48.50,2,11, 3),
(4, 70.00,2,13, 4);

select * from tb_rol;
select * from tb_cliente;
select * from tb_vendedor;
select * from tb_proveedor;
select * from tb_marca;
select * from tb_producto;
select * from tb_compra;
select * from tb_detalle_compra;
