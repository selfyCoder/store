CREATE TABLE clients (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       first_name VARCHAR(255) NOT NULL,
                       last_name VARCHAR(255),
                       middle_name VARCHAR(255),
                       iin CHAR(12) NOT NULL UNIQUE
);

CREATE TABLE categories (
                            id BIGINT PRIMARY KEY AUTO_INCREMENT,
                            name VARCHAR(255) NOT NULL
);

CREATE TABLE products (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          category_id BIGINT NOT NULL,
                          name VARCHAR(255) NOT NULL,
                          description TEXT,
                          price DECIMAL(10,2) NOT NULL,

                          CONSTRAINT fk_products_category FOREIGN KEY (category_id)
                              REFERENCES categories(id)
                              ON DELETE CASCADE
);
CREATE INDEX idx_products_description ON products(description);
CREATE INDEX idx_products_name_description ON products(name, description);

CREATE TABLE actions (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         name VARCHAR(255) NOT NULL,
                         begin_date TIMESTAMP NOT NULL,
                         end_date TIMESTAMP NOT NULL,
                         product_id BIGINT NOT NULL,

                         CONSTRAINT fk_action_product FOREIGN KEY (product_id)
                             REFERENCES products(id)
                             ON DELETE CASCADE
);