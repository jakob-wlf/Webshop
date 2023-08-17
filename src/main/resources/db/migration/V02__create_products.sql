create table product_entity_tags
(
    product_entity_id varchar(36) not null,
    tags              varchar(255)
);

create table products
(
    id            varchar(36)  not null,
    name          varchar(255) not null,
    description   varchar(255) not null,
    price_in_cent integer      not null,
    primary key (id)
);

alter table if exists product_entity_tags
    add constraint FKmht84mxiy3fo058vjgvlyhnrk
        foreign key (product_entity_id)
            references products;