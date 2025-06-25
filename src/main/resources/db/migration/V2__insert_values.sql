INSERT INTO clients (id, first_name, last_name, middle_name, iin)
VALUES (1, 'Alice', 'Smith', 'Marie', '880123450011') ,
    (2, 'Bob', 'Brown', NULL, '870212450022')
    ,
    (3, 'Charlie', 'White', 'John', '860303450033');

INSERT INTO categories (id, name)
VALUES
    (1, 'Electronics'),
(2, 'Books'),
(3, 'Clothing'),
(4, 'Home & Kitchen'),
(5, 'Toys');

INSERT INTO products (category_id, name, description, price)
VALUES (1, 'Smartphone', 'Latest model smartphone with high resolution display', 699.99) ,
    (1, 'Laptop', 'Lightweight laptop with 16GB RAM and SSD', 1199.99)
    ,
    (2, 'Novel', 'Bestselling fiction novel', 14.99)
    ,
    (3, 'T-shirt', '100% cotton t-shirt with print', 19.99)
    ,
    (4, 'Blender', 'Multi-speed kitchen blender', 49.99)
    ,
    (5, 'LEGO Set', 'Classic 500-piece LEGO building set', 29.99);

INSERT INTO actions (name, begin_date, end_date, product_id)
VALUES
    ('Summer Sale', DATEADD('DAY', -2, CURRENT_TIMESTAMP), DATEADD('DAY', 10, CURRENT_TIMESTAMP), 1),
('Back to School', CURRENT_TIMESTAMP, DATEADD('DAY', 7, CURRENT_TIMESTAMP), 2),
('Autumn Deals', DATEADD('DAY', 3, CURRENT_TIMESTAMP), DATEADD('DAY', 15, CURRENT_TIMESTAMP), 3),
('Black Friday', DATEADD('DAY', 30, CURRENT_TIMESTAMP), DATEADD('DAY', 33, CURRENT_TIMESTAMP), 1),
('Spring Clearance', DATEADD('DAY', -20, CURRENT_TIMESTAMP), DATEADD('DAY', -10, CURRENT_TIMESTAMP), 4),
('New Year Sale', DATEADD('DAY', -90, CURRENT_TIMESTAMP), DATEADD('DAY', -85, CURRENT_TIMESTAMP), 5);

