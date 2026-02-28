CREATE TABLE product (
    id    BIGSERIAL PRIMARY KEY,
    name  VARCHAR(100)     NOT NULL,
    price DOUBLE PRECISION NOT NULL
);

CREATE TABLE raw_material (
    id             BIGSERIAL PRIMARY KEY,
    name           VARCHAR(100)     NOT NULL,
    stock_quantity DOUBLE PRECISION NOT NULL
);

CREATE TABLE product_material (
    id                BIGSERIAL PRIMARY KEY,
    product_id        BIGINT           NOT NULL,
    raw_material_id   BIGINT           NOT NULL,
    material_quantity DOUBLE PRECISION NOT NULL,

    CONSTRAINT fk_product_material
        FOREIGN KEY (product_id)
        REFERENCES product (id)
        ON DELETE CASCADE,
    CONSTRAINT fk_material_product
        FOREIGN KEY (raw_material_id)
        REFERENCES raw_material (id)
        ON DELETE CASCADE,
    CONSTRAINT unique_product_material UNIQUE (product_id, raw_material_id)
);