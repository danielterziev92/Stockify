CREATE MATERIALIZED VIEW product_mv AS
SELECT pv.id,
       pv.sku,
       pv.active,
       pb.name || CASE
                      WHEN COUNT(pva.id) > 0
                          THEN ' ' || STRING_AGG(pav.value, ' ' ORDER BY pva.order)
                      ELSE ''
           END                             AS full_name,
       pb.base_price + pv.additional_price AS final_price,
       pi.url,
       pm.unit                             AS measure_unit,
       pm.precision                        AS measure_precision,
       pm.scale                            AS measure_scale,
       pc.id                               AS category_id,
       pc.name                             AS category_name,
       pt.id                               AS type_id,
       pt.name                             AS type_name
FROM product_variants pv
         JOIN product_base_infos pb ON pb.id = pv.product_id
         JOIN product_measures pm ON pm.id = pb.measure_id
         JOIN product_types pt ON pt.id = pb.type_id
         LEFT JOIN categories pc ON pc.id = pb.category_id
         LEFT JOIN product_images pi ON pi.product_id = pb.id AND pi.is_primary = true
         LEFT JOIN product_variant_attributes pva ON pva.variant_id = pv.id
         LEFT JOIN product_attribute_values pav ON pav.id = pva.attribute_value_id
GROUP BY pv.id, pv.sku, pv.active, pv.additional_price,
         pb.name, pb.base_price,
         pi.url,
         pm.unit, pm.precision, pm.scale,
         pc.id, pc.name,
         pt.id, pt.name;

CREATE UNIQUE INDEX idx_product_mv_id ON product_mv (id);
CREATE INDEX idx_product_mv_active ON product_mv (active);
CREATE INDEX idx_product_mv_category ON product_mv (category_id);
CREATE INDEX idx_product_mv_type ON product_mv (type_id);
CREATE INDEX idx_product_mv_sku ON product_mv (sku);