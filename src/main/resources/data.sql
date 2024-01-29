insert into user(name,email,is_active,passwd) values ('123','123@mail.ru', true,'$2a$10$Fk6el1hs4jU6rePfDMPFCu0fciwbPn8sKMYe3tlzhoPoQLx1V9iYq');
insert into user(name,email,is_active,passwd) values ('111','111@mail.ru', true,'$2a$10$Y8.PcF7EMSkv4TsnmcYhX.oeDNqaElPXQkWBFoVDEywvBU9QQPuG6');

insert into user_role(user_name,roles) values('123','USER');
insert into user_role(user_name,roles) values('123','ADMIN');
insert into user_role(user_name,roles) values('111','USER');

insert into algorithm(id,name) values(1,'Trial'),(2,'Fermat'),(3,'Miller-Rabin'),(4,'Solovay-Strassen');