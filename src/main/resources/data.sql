INSERT INTO users (id, user_role, role, status, name, surname, email, password, phone, street, number, city, country, profile_image, bio)
VALUES
    (1, 'ADMIN', 0, 1, 'Marko', 'Marković', 'admin@gmail.com', 'admin', '+38169423143', 'Bulevar cara Dušana', 34, 'Novi Sad', 'Serbia', null, null),
    (2, 'HOST', 1, 1, 'Petar', 'Petrović', 'petar@gmail.com', 'petar', '+38163748021', 'Andrije Jevremovića', 22, 'Zlatibor', 'Serbia', null, 'Živim u Kruševcu i izdajem 2 smeštaja.'),
    (3, 'HOST', 1, 1, 'Nevena', 'Nevenić', 'nevena@gmail.com', 'nevena', '+38165900314', 'Jovana Ristića', 87, 'Niš', 'Serbia', null, 'Iz Niš sam i izdajem 3 apartmana.'),
    (4, 'HOST', 1, 1, 'Milica', 'Milić', 'milica@gmail.com', 'milica', '+38163984039', 'Bulevar vojvode Mišića', 74, 'Beograd', 'Serbia', null, 'Živim u Beogradu i posedujem nekoliko smeštaja.'),
    (5, 'GUEST', 2, 1, 'Dragan', 'Draganić', 'dragan@gmail.com', 'dragan', '+38169774829', 'Bulevar vojvode Mišića', 74, 'Beograd', 'Serbia', null, null),
    (6, 'GUEST', 2, 3, 'Marija', 'Marijanović', 'marija@gmail.com', 'marija', '+38163756640', 'Slobodana Jovanovića', 8, 'Kruševac', 'Serbia', null, null),
    (7, 'GUEST', 2, 0, 'Miloš', 'Milošević', 'milos@gmail.com', 'milos', '+381651859940', 'Svetozara Radojčića', 17, 'Beograd', 'Serbia', null, null);

INSERT INTO accommodations (id, host_id, type, title, street, number, city, country, pricing, default_price, automatic_approval, cancellation_due, max_guests, min_guests, description)
VALUES
    (1, 2, 1, 'Soba sa 3 kreveta', 'Rujanska', 23, 'Zlatibor', 'Serbia', 0, 4100.0, false, 172800000, 4, 1, 'Soba sa pogledom na šetalište.'),
    (2, 2, 2, 'Kuća na brdu', 'Bore Vasiljevića', 14, 'Vrnjačka Banja', 'Serbia', 0, 26000.0, false, 172800000, 5, 2, 'Kuća na sprat sa 2 terase.'),
    (3, 3, 0, 'Mali apartman', 'Bulevar Nemanjića', 44, 'Niš', 'Serbia', 0, 4500.0, true, 172800000, 2, 1, 'Mali apartman u strogom centru grada.'),
    (4, 3, 0, 'Veliki apartman', 'Bulevar Nemanjića', 44, 'Niš', 'Serbia', 0, 9000.0, true, 172800000, 4, 1, 'Veliki apartman u strogom centru grada.'),
    (5, 3, 0, 'Delux apartman', 'Bulevar Nemanjića', 45, 'Niš', 'Serbia', 1, 13000.0, false, 172800000, 2, 1, 'Apartman u strogom centru grada.'),
    (6, 4, 1, 'Soba sa bračnim ležajem', 'Južni bulevar', 89, 'Beograd', 'Serbia', 1, 6300.0, true, 172800000, 3, 1, 'Soba sa pogledom na Južni bulevar.'),
    (7, 4, 1, 'Soba sa 3 ležaja', 'Južni bulevar', 40, 'Beograd', 'Serbia', 1, 9700.0, true, 172800000, 5, 2, 'Soba sa pogledom sa 3 ležaja.'),
    (8, 4, 0, 'Veliki apartman', 'Karađorđeva', 92, 'Beograd', 'Serbia', 1, 12000.0, true, 172800000, 7, 3, 'Apartman blizu Kalemegdana sa pogledom na ušće.');

-- INSERT INTO reservations (id, status, accommodation_id, guest_id, guest_number, price, request_date, reservation_date, start_date, end_date)
-- VALUES
--     ();