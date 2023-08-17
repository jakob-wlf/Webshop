create table order_positions
(
    id         varchar(36) not null,
    order_id   varchar(36) not null,
    product_id varchar(36) not null,
    quantity   bigint      not null
);

create table orders
(
    id           varchar(36)                                                                                not null,
    customer_id  varchar(36)                                                                                not null,
    order_time   timestamp(6)                                                                               not null,
    order_status varchar(255) check (order_status in ('NEW', 'CONFIRMED', 'SENT', 'DELIVERED', 'CANCELED')) not null,
    primary key (id)
);

alter table if exists order_positions
    add constraint order_positions_order_id_fk
    foreign key (order_id)
    references orders;

alter table if exists order_positions
    add constraint order_positions_product_id_fk
    foreign key (product_id)
    references products;

alter table if exists orders
    add constraint orders_customer_id_fk
    foreign key (customer_id)
    references customers;
