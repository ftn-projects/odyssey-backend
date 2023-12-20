INSERT INTO roles (name) VALUES
    ('ADMIN'), ('HOST'), ('GUEST'), ('USER');

INSERT INTO users (user_role, status, name, surname, email, password, phone, street, number, city, country, profile_image, bio, created) VALUES
    ('USER', 1, 'Marko', 'Marković', 'admin@gmail.com', '$2a$12$ojx4gaAU/odqpcwD7JTHMe0GUCITBrDKe/llCaUVIAmdkaJAr1KjO', '(+381)69423143', 'Bulevar cara Dušana', 34, 'Novi Sad', 'Serbia', 'profile.png', null, '2023-01-01 11:00:00'),
    ('HOST', 1, 'Petar', 'Petrović', 'petar@gmail.com', '$2a$12$xygRv8VL.sBMKEgrrioX7OS81Zn4V5gHrLNnVHJ1HLP6ISXzzZf2G', '(+381)63748021', 'Slobodana Jovanovića', 8, 'Kruševac', 'Serbia', 'profile.png', 'Živim u Kruševcu i izdajem 2 smeštaja.', '2023-02-10 12:00:00'),
    ('HOST', 2, 'Nevena', 'Nevenić', 'nevena@gmail.com', '$2a$12$vA7WOGDUHdulgxXcSjUyf.ZcbWqFyeMLvGWPsD0.yUZ3dxvbHGoyi', '(+381)65900314', 'Jovana Ristića', 87, 'Niš', 'Serbia', 'profile.png', 'Iz Niš sam i izdajem 3 apartmana.', '2023-03-21 10:00:00'),
    ('HOST', 1, 'Milica', 'Milić', 'milica@gmail.com', '$2a$12$c7B8GKMgOd3bhCznV07Qq.9I20RHUrJ2eoC9ba2ySopngikxJtlHW', '(+381)63984039', 'Bulevar vojvode Mišića', 74, 'Beograd', 'Serbia', 'profile.png', 'Živim u Beogradu i posedujem nekoliko smeštaja.', '2023-06-08 09:00:00'),
    ('GUEST', 1, 'Dragan', 'Draganić', 'dragan@gmail.com', '$2a$12$3o.HIAcVhoE60mjT8cCtYuGXsHwlu.filb3tB77lKTVGWtuOOhzie', '(+381)69774829', 'Bulevar vojvode Mišića', 74, 'Beograd', 'Serbia', 'profile.png', null, '2023-08-11 13:00:00'),
    ('GUEST', 3, 'Marija', 'Marijanović', 'marija@gmail.com', '$2a$12$x5kr/gYepaBqSumUrGKFP.MpG9sMO0PmmsG5zu5AdSSBPaFHWz3RC', '(+381)63756640', 'Stražilovska', 3, 'Novi Sad', 'Serbia', 'profile.png', null, '2021=09-17 15:00:00'),
    ('GUEST', 0, 'Miloš', 'Milošević', 'milos@gmail.com', '$2a$12$ltjGQndex32s0vfh9NGqauwuvdQuJ1a1fdgUYVbJKMnk7z1N9w7Wi', '(+381)651859940', 'Svetozara Radojčića', 17, 'Beograd', 'Serbia', 'profile.png', null, '2023-10-29 20:00:00');

INSERT INTO user_roles (user_id, role_id) VALUES
    (1, 1), (2, 2), (3, 2), (4, 2), (5, 3), (6, 3), (7, 3),
    (1, 4), (2, 4), (3, 4), (4, 4), (5, 4), (6, 4), (7, 4);

INSERT INTO accommodations (host_id, type, title, description, street, number, city, country, pricing, default_price, automatic_approval, cancellation_due, min_guests, max_guests) VALUES
    (2, 1, 'Soba sa 3 kreveta', 'Soba sa pogledom na šetalište.', 'Rujanska', 23, 'Zlatibor', 'Serbia', 0, 4100.0, false, 364, 1, 4),
    (2, 2, 'Kuća na brdu', 'Kuća na sprat sa 2 terase.', 'Bore Vasiljevića', 14, 'Vrnjačka Banja', 'Serbia', 0, 26000.0, false, 563, 2, 5),
    (3, 0, 'Mali apartman', 'Mali apartman u strogom centru grada.', 'Bulevar Nemanjića', 44, 'Niš', 'Serbia', 0, 4500.0, true, 1265, 1, 2),
    (3, 0, 'Veliki apartman', 'Veliki apartman u strogom centru grada.', 'Bulevar Nemanjića', 44, 'Niš', 'Serbia', 0, 9000.0, true, 2446, 1, 4),
    (3, 0, 'Delux apartman', 'Apartman u strogom centru grada.', 'Bulevar Nemanjića', 45, 'Niš', 'Serbia', 1, 13000.0, false, 125, 1, 2),
    (4, 1, 'Soba sa bračnim ležajem', 'Soba sa pogledom na Južni bulevar.', 'Južni bulevar', 89, 'Beograd', 'Serbia', 1, 6300.0, true, 76, 1, 3),
    (4, 1, 'Soba sa 3 ležaja', 'Soba sa pogledom sa 3 ležaja.', 'Južni bulevar', 40, 'Beograd', 'Serbia', 1, 9700.0, true, 552, 2, 5),
    (4, 0, 'Veliki apartman', 'Apartman blizu Kalemegdana sa pogledom na ušće.', 'Karađorđeva', 92, 'Beograd', 'Serbia', 1, 12000.0, true, 4155, 3, 7);

INSERT INTO amenities (title) VALUES
    ('TV'), ('WiFi'), ('Kitchen'), ('Free parking'), ('Beach access'), ('Washer'), ('Spa'), ('Air conditioning'), ('King bed'), ('Smoking room');

INSERT INTO accommodation_has_amenity (accommodation_id, amenity_id) VALUES
    (1, 1), (1, 2), (1, 4), (1, 6), (1, 7), (1, 9),
    (2, 1), (2, 2), (2, 5), (2, 7), (2, 10),
    (3, 2), (3, 4), (3, 6), (3, 8), (3, 9), (3, 10),
    (4, 1), (4, 2), (4, 3), (4, 4), (4, 6), (4, 7), (4, 9),
    (5, 2), (5, 4), (5, 5), (5, 7), (5, 8),
    (6, 1), (6, 2), (6, 3), (6, 5), (6, 7), (6, 9),
    (7, 2), (7, 3), (7, 7), (7, 8),
    (8, 1), (8, 2), (8, 3), (8, 5), (8, 7), (8, 9);

INSERT INTO accommodation_available_slots (accommodation_id, price, start_date, end_date) VALUES
    (1, 5200.0, '2023-12-20', '2023-01-05'), (1, 4500.0, '2023-01-10 ', '2023-01-20 '), (1, 4300.0, '2023-01-25', '2023-02-10'),
    (2, 34000.0, '2023-12-20', '2023-01-05'), (2, 30000.0, '2023-01-10 ', '2023-01-20 '), (2, 320000.0, '2023-01-25', '2023-02-10'),
    (3, 5600.0, '2023-12-20', '2023-01-05'), (3, 5100.0, '2023-01-10 ', '2023-01-20 '), (3, 4200.0, '2023-01-25', '2023-02-10'),
    (4, 14000.0, '2023-12-20', '2023-01-05'), (4, 11000.0, '2023-01-10 ', '2023-01-20 '), (4, 9500.0, '2023-01-25', '2023-02-10'),
    (5, 18000.0, '2023-12-20', '2023-01-05'), (5, 15000.0, '2023-01-10 ', '2023-01-20 '), (5, 14000.0, '2023-01-25', '2023-02-10'),
    (6, 8100.0, '2023-12-20', '2023-01-05'), (6, 7600.0, '2023-01-10 ', '2023-01-20 '), (6, 7000.0, '2023-01-25', '2023-02-10'),
    (7, 18000.0, '2023-12-20', '2023-01-05'), (7, 11000.0, '2023-01-10 ', '2023-01-20 '), (7, 8000.0, '2023-01-25', '2023-02-10'),
    (8, 16000.0, '2023-12-20', '2023-01-05'), (8, 15000.0, '2023-01-10 ', '2023-01-20 '), (8, 14000.0, '2023-01-25', '2023-02-10');

INSERT INTO accommodation_requests (type, status, submission_date, host_id, accommodation_id, new_accommodation_type, new_title, new_description, street, number, city, country, new_pricing, new_default_price, new_automatic_approval, new_cancellation_due, new_min_guests, new_max_guests) VALUES
    (0, 2, '2023-11-4 12:00:00', 2, null, 1, 'Soba', '', 'Dušana Radovića', 40, 'Vrnjačka Banja', 'Serbia', 0, 4500.0, true, 125, 1, 3),
    (0, 0, '2023-11-6 12:00:00', 3, null, 0, 'Duplex apartman', 'Apartman na periferiji.', 'Bulevar Oslobođenja', 34, 'Novi Sad', 'Serbia', 1, 6400.0, false, 1265, 1, 4),
    (1, 2, '2023-11-8 12:00:00', 2, 2, 2, 'Neki apartman', 'Kuća na sprat sa 2 terase.', 'Bore Vasiljevića', 14, 'Vrnjačka', '', 0, 26000.0, false, 563, 2, 5),
    (1, 1, '2023-11-10 12:00:00', 3, 4, 0, 'Veliki apartman', 'Veliki apartman u strogom centru grada.', 'Bulevar Nemanjića', 44, 'Niš', 'Serbia', 0, 9000.0, true, 2446, 1, 4),
    (1, 0, '2023-11-12 12:00:00', 4, 6, 1, 'Soba sa velikim ležajem', 'Soba sa terasom i pogledom na Južni bulevar.', 'Južni bulevar', 89, 'Beograd', 'Serbia', 1, 8100.0, true, 76, 1, 3);

INSERT INTO accommodation_request_has_amenity (request_id, amenity_id) VALUES
    (1, 1), (1, 2), (1, 4), (1, 8),
    (2, 1), (2, 2), (2, 5), (2, 7), (2, 8), (2, 10),
    (3, 2), (3, 4), (3, 5), (3, 8), (3, 10),
    (4, 1), (4, 2), (4, 3), (4, 4), (4, 6), (4, 7), (4, 9),
    (5, 2), (5, 4), (5, 5), (5, 7), (5, 8), (5, 9);

-- INSERT INTO reservations (id, status, accommodation_id, guest_id, guest_number, price, request_date, reservation_date, start_date, end_date)
-- VALUES
--     ();
