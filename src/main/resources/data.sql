-- Insert a sample host
INSERT INTO users (id, role, status, name, email, password, phone)
VALUES (1, 'HOST', 'ACTIVE', 'Sample Host', 'host@example.com', 'host123', '+123456789');

-- Insert a sample accommodation
INSERT INTO accommodations (id, title, description, type, pricing, default_price, automatic_approval, min_guests, max_guests, host_id)
VALUES (1, 'Cozy Cabin', 'A comfortable cabin in the woods.', 'HOUSE', 'PER_PERSON', 150.0, true, 2, 4, 1);

-- Insert sample amenities
INSERT INTO amenities (id, title)
VALUES (1, 'WiFi'), (2, 'Parking');

-- Link amenities to accommodation
INSERT INTO accommodation_has_amenity (accommodation_id, amenity_id) VALUES (1, 1), (1, 2);

-- Insert sample availability slots
INSERT INTO availability_slots (price, start_date, end_date, accommodation_id)
VALUES (150.0, '2023-12-10 08:00:00', '2023-12-17 20:00:00', 1),
       (180.0, '2023-12-25 10:00:00', '2024-01-09 18:00:00', 1);

-- Insert sample address
INSERT INTO addresses (id, city, country, street, number, accommodation_id)
VALUES (1, 'Woodland', 'Natureland', '123 Forest Lane', 23, 1);
