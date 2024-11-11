CREATE TABLE IF NOT EXISTS products (
    id SERIAL PRIMARY KEY,
    product_id VARCHAR(255) NOT NULL,
    status VARCHAR(255),
    fulfillment_center VARCHAR(255),
    quantity INT,
    value DECIMAL(10,2),
    is_deleted BOOLEAN
)