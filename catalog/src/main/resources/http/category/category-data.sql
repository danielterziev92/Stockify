-- Product Categories
INSERT INTO categories (name, version, active, display_order, parent_id, dtype) VALUES
                                                                                    ('Electronics', 0, true, 1, null, 'PRODUCT'),
                                                                                    ('Computers & Laptops', 0, true, 2, null, 'PRODUCT'),
                                                                                    ('Smartphones', 0, true, 3, null, 'PRODUCT'),
                                                                                    ('Audio & Headphones', 0, true, 4, null, 'PRODUCT'),
                                                                                    ('Cameras', 0, true, 5, null, 'PRODUCT'),
                                                                                    ('Gaming', 0, true, 6, null, 'PRODUCT'),
                                                                                    ('Home Appliances', 0, true, 7, null, 'PRODUCT'),
                                                                                    ('Furniture', 0, true, 8, null, 'PRODUCT'),
                                                                                    ('Clothing', 0, true, 9, null, 'PRODUCT'),
                                                                                    ('Footwear', 0, true, 10, null, 'PRODUCT'),
-- Children of Electronics (id=1)
                                                                                    ('Televisions', 0, true, 1, 1, 'PRODUCT'),
                                                                                    ('Monitors', 0, true, 2, 1, 'PRODUCT'),
-- Children of Computers (id=2)
                                                                                    ('Gaming Laptops', 0, false, 1, 2, 'PRODUCT'),
                                                                                    ('Accessories', 0, true, 2, 2, 'PRODUCT'),
-- Children of Smartphones (id=3)
                                                                                    ('Android Phones', 0, true, 1, 3, 'PRODUCT'),
                                                                                    ('iPhones', 0, true, 2, 3, 'PRODUCT'),
-- Children of Gaming (id=6)
                                                                                    ('Consoles', 0, true, 1, 6, 'PRODUCT'),
                                                                                    ('PC Gaming', 0, true, 2, 6, 'PRODUCT'),
-- Children of Clothing (id=9)
                                                                                    ('Men Clothing', 0, true, 1, 9, 'PRODUCT'),
                                                                                    ('Women Clothing', 0, true, 2, 9, 'PRODUCT');

-- Partner Categories
INSERT INTO categories (name, version, active, display_order, parent_id, dtype) VALUES
                                                                                    ('Manufacturers', 0, true, 1, null, 'PARTNER'),
                                                                                    ('Distributors', 0, true, 2, null, 'PARTNER'),
                                                                                    ('Retailers', 0, true, 3, null, 'PARTNER'),
                                                                                    ('Wholesalers', 0, true, 4, null, 'PARTNER'),
                                                                                    ('Logistics', 0, true, 5, null, 'PARTNER'),
                                                                                    ('IT Services', 0, true, 6, null, 'PARTNER'),
                                                                                    ('Consultants', 0, true, 7, null, 'PARTNER'),
                                                                                    ('Marketing', 0, true, 8, null, 'PARTNER'),
                                                                                    ('Finance', 0, true, 9, null, 'PARTNER'),
                                                                                    ('Legal', 0, true, 10, null, 'PARTNER'),
-- Children of Manufacturers (id=21)
                                                                                    ('Electronics Manufacturers', 0, true, 1, 21, 'PARTNER'),
                                                                                    ('Textile Manufacturers', 0, true, 2, 21, 'PARTNER'),
-- Children of Distributors (id=22)
                                                                                    ('Regional Distributors', 0, true, 1, 22, 'PARTNER'),
                                                                                    ('Global Distributors', 0, true, 2, 22, 'PARTNER'),
-- Children of Logistics (id=25)
                                                                                    ('Freight', 0, true, 1, 25, 'PARTNER'),
                                                                                    ('Last Mile Delivery', 0, true, 2, 25, 'PARTNER'),
-- Children of IT Services (id=26)
                                                                                    ('Cloud Providers', 0, true, 1, 26, 'PARTNER'),
                                                                                    ('Software Vendors', 0, true, 2, 26, 'PARTNER'),
-- Children of Marketing (id=28)
                                                                                    ('Digital Marketing', 0, true, 1, 28, 'PARTNER'),
                                                                                    ('Traditional Marketing', 0, false, 2, 28, 'PARTNER');