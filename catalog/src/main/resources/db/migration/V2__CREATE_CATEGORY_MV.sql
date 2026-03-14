CREATE MATERIALIZED VIEW category_mv AS
SELECT c.id,
       c.name,
       c.display_order,
       c.active,
       c.dtype,
       p.name AS parent_name,
       c.parent_id
FROM categories c
         LEFT JOIN categories p ON p.id = c.parent_id;

CREATE UNIQUE INDEX idx_category_mv_id ON category_mv (id);
CREATE INDEX idx_category_mv_dtype ON category_mv (dtype);
CREATE INDEX idx_category_mv_dtype_active ON category_mv (dtype, active);