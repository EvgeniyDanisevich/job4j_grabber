CREATE TABLE company
(
    id integer NOT NULL,
    name character varying,
    CONSTRAINT company_pkey PRIMARY KEY (id)
);

CREATE TABLE person
(
    id integer NOT NULL,
    name character varying,
    company_id integer,
    CONSTRAINT person_pkey PRIMARY KEY (id)
);

insert into company(id, name) values (1, 'google');
insert into company(id, name) values (2, 'facebook');
insert into company(id, name) values (3, 'yandex');
insert into company(id, name) values (4, 'twitter');
insert into company(id, name) values (5, 'youtube');

insert into person(id, name, company_id) values (1, 'Иван', 1);
insert into person(id, name, company_id) values (2, 'Сергей', 1);
insert into person(id, name, company_id) values (3, 'Андрей', 1);
insert into person(id, name, company_id) values (4, 'Евгений', 1);
insert into person(id, name, company_id) values (5, 'Марк', 2);
insert into person(id, name, company_id) values (6, 'Анатолий', 2);
insert into person(id, name, company_id) values (7, 'Денис', 2);
insert into person(id, name, company_id) values (8, 'Александр', 3);
insert into person(id, name, company_id) values (9, 'Виктор', 4);
insert into person(id, name, company_id) values (10, 'Инокентий', 5);

select p.name as Имя, c.name as Компания from person as p join company as c on c.id = p.company_id where company_id != 5;

select company.name as Компания, count(person.company_id) as Количество
from person join company  on person.company_id = company.id
group by company.name;

with my_table as (
    select company.name as Компания, count(p) as Количество from company join person p on company.id = p.company_id
    group by company.name
)
select * from my_table where my_table.Количество = (select max(my_table.Количество) from my_table);