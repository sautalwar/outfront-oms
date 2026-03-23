-- =============================================================================
-- Seed data for OutFront OMS
-- =============================================================================

-- Suppliers
INSERT INTO suppliers (name, contact_email, contact_phone, address, active, created_at) VALUES
    ('DisplayTech Solutions', 'sales@displaytech.example.com', '555-0101', '100 Industrial Blvd, Newark, NJ', true, '2024-01-15'),
    ('BrightSign Electronics', 'orders@brightsign.example.com', '555-0202', '200 Tech Park Dr, San Jose, CA', true, '2024-02-01'),
    ('SteelMount Industries', 'info@steelmount.example.com', '555-0303', '50 Fabrication Way, Dallas, TX', true, '2024-03-10'),
    ('PowerGrid Supply Co', 'support@powergrid.example.com', '555-0404', '75 Energy Lane, Houston, TX', true, '2024-04-20');

-- Inventory Items
INSERT INTO inventory_items (sku, name, description, category, quantity, reorder_level, unit_price, location, last_updated) VALUES
    ('DSU-9648', 'Digital Screen Unit 96x48', 'High-brightness 96x48 inch digital billboard screen, weatherproof IP65', 'Displays', 25, 10, 4500.00, 'Warehouse A — Newark, NJ', '2024-11-01'),
    ('LED-7236', 'LED Panel 72x36', 'Full-color LED panel for urban signage, 72x36 inches', 'Displays', 50, 15, 2800.00, 'Warehouse A — Newark, NJ', '2024-11-03'),
    ('TDM-2412', 'Transit Display Module', 'Compact transit shelter display, 24x12 inches, anti-glare', 'Displays', 30, 10, 1200.00, 'Warehouse B — Los Angeles, CA', '2024-10-28'),
    ('SKD-3220', 'Smart Kiosk Display', 'Interactive touch kiosk with 32-inch screen, built-in sensors', 'Kiosks', 12, 5, 3200.00, 'Warehouse A — Newark, NJ', '2024-11-05'),
    ('SBC-001', 'Solar Billboard Controller', 'Solar-powered controller unit for remote billboard sites', 'Controllers', 40, 15, 850.00, 'Warehouse C — Dallas, TX', '2024-11-02'),
    ('MNT-UNI', 'Universal Mounting Bracket', 'Adjustable steel mounting bracket for panels up to 96 inches', 'Hardware', 100, 25, 120.00, 'Warehouse A — Newark, NJ', '2024-10-15'),
    ('CAB-HDMI50', 'HDMI Cable 50ft', 'Industrial-grade 50-foot HDMI cable, weatherproof connectors', 'Cables', 200, 50, 45.00, 'Warehouse B — Los Angeles, CA', '2024-10-20'),
    ('PSU-500W', 'Power Supply Unit 500W', '500W weatherproof power supply for outdoor digital displays', 'Power', 75, 20, 180.00, 'Warehouse C — Dallas, TX', '2024-11-01'),
    ('CTL-MEDIA', 'Media Player Controller', 'ARM-based media player for content scheduling and playback', 'Controllers', 35, 10, 420.00, 'Warehouse A — Newark, NJ', '2024-11-07'),
    ('SEN-AMB', 'Ambient Light Sensor', 'Auto-brightness ambient light sensor for dynamic display adjustment', 'Sensors', 60, 20, 65.00, 'Warehouse B — Los Angeles, CA', '2024-11-04'),
    ('DSU-5530', 'Digital Screen Unit 55x30', 'Mid-range 55x30 inch digital billboard screen', 'Displays', 8, 10, 3200.00, 'Warehouse A — Newark, NJ', '2024-11-10'),
    ('WTH-SEAL', 'Weatherproofing Seal Kit', 'Complete seal kit for outdoor display installations', 'Hardware', 5, 15, 35.00, 'Warehouse C — Dallas, TX', '2024-11-08');

-- Orders
INSERT INTO orders (customer_name, product_name, quantity, status, order_date, shipping_address, notes, created_at, updated_at) VALUES
    ('Clear Channel NYC', 'Digital Screen Unit 96x48', 4, 'CONFIRMED', '2024-11-01', '1515 Broadway, New York, NY 10036', 'Install at Times Square locations', '2024-11-01', '2024-11-02'),
    ('JCDecaux Transit', 'LED Panel 72x36', 10, 'PENDING', '2024-11-05', '1370 Avenue of the Americas, New York, NY', 'Awaiting site survey approval', '2024-11-05', '2024-11-05'),
    ('Lamar Advertising', 'Transit Display Module', 6, 'SHIPPED', '2024-10-20', '5321 Industrial Oaks Blvd, Austin, TX', 'Shipped via freight — ETA Nov 15', '2024-10-20', '2024-10-25'),
    ('Outfront Media HQ', 'Smart Kiosk Display', 2, 'PENDING', '2024-11-10', '405 Lexington Ave, New York, NY 10174', 'Internal demo units for trade show', '2024-11-10', '2024-11-10'),
    ('Adams Outdoor', 'Solar Billboard Controller', 8, 'CONFIRMED', '2024-11-08', '100 Highway Corridor, Raleigh, NC', 'Rural highway deployment — Phase 1', '2024-11-08', '2024-11-09'),
    ('Intersection Co', 'Media Player Controller', 15, 'PENDING', '2024-11-12', '233 Broadway, New York, NY 10279', 'LinkNYC kiosk upgrade program', '2024-11-12', '2024-11-12'),
    ('Clear Channel LA', 'LED Panel 72x36', 20, 'PENDING', '2024-11-14', '5901 W Century Blvd, Los Angeles, CA', 'Sunset Blvd digital conversion project', '2024-11-14', '2024-11-14');

-- Purchase Orders
INSERT INTO purchase_orders (supplier_id, inventory_item_id, quantity_ordered, status, order_date, expected_delivery_date, notes) VALUES
    (1, 11, 20, 'SUBMITTED', '2024-11-10', '2024-12-01', 'Restock DSU-5530 — running low'),
    (3, 12, 50, 'APPROVED', '2024-11-08', '2024-11-25', 'Urgent: weatherproofing seals depleted'),
    (2, 9, 30, 'DRAFT', '2024-11-15', '2024-12-15', 'Q1 2025 pre-order for media controllers');
