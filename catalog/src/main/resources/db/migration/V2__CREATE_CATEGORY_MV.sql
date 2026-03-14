CREATE MATERIALIZED VIEW category_mv AS
SELECT c.id,
       c.name,
       c.display_order,
       c.active,
       c.dtype,
       c.parent_id,
       COALESCE(
                       JSON_AGG(
                       JSON_BUILD_OBJECT('id', ch.id, 'name', ch.name)
                       ORDER BY ch.display_order
                               ) FILTER (WHERE ch.id IS NOT NULL),
                       '[]'
       ) AS children
FROM categories c
         LEFT JOIN categories ch ON ch.parent_id = c.id AND ch.dtype = c.dtype
GROUP BY c.id, c.name, c.display_order, c.active, c.dtype, c.parent_id;

CREATE UNIQUE INDEX idx_category_mv_id ON category_mv (id);
CREATE INDEX idx_category_mv_dtype ON category_mv (dtype);
CREATE INDEX idx_category_mv_dtype_active ON category_mv (dtype, active);
CREATE INDEX idx_category_mv_parent ON category_mv (parent_id);