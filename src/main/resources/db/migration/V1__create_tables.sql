CREATE TABLE product (
    id    SERIAL PRIMARY KEY,
    name  VARCHAR(100)     NOT NULL,
    price DOUBLE PRECISION NOT NULL
);

CREATE TABLE raw_material (
    id             SERIAL PRIMARY KEY,
    name           VARCHAR(100)     NOT NULL,
    stock_quantity DOUBLE PRECISION NOT NULL
);

CREATE TABLE product_material (
    id                SERIAL PRIMARY KEY,
    product_id        INTEGER           NOT NULL,
    raw_material_id   INTEGER           NOT NULL,
    material_quantity DOUBLE PRECISION NOT NULL,

    CONSTRAINT fk_product_material FOREIGN KEY (product_id) REFERENCES product (id),
    CONSTRAINT fk_material_product FOREIGN KEY (raw_material_id) REFERENCES raw_material (id),
    CONSTRAINT unique_product_material UNIQUE (product_id, raw_material_id)
);