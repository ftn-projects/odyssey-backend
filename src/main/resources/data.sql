INSERT INTO roles (name)
VALUES ('ADMIN'), ('HOST'), ('GUEST'), ('USER');

INSERT INTO users (user_role, status, name, surname, email, password, phone, street, number, city, country, profile_image, bio, created)
VALUES
    ('USER', 1, 'Marko', 'Marković', 'admin@gmail.com', '$2a$12$ojx4gaAU/odqpcwD7JTHMe0GUCITBrDKe/llCaUVIAmdkaJAr1KjO', '+38169423143', 'Bulevar cara Dušana', 34, 'Novi Sad', 'Serbia', 'profile.png', null, '2023-01-01 11:00:00'),
    ('HOST', 1, 'Petar', 'Petrović', 'petar@gmail.com', '$2a$12$xygRv8VL.sBMKEgrrioX7OS81Zn4V5gHrLNnVHJ1HLP6ISXzzZf2G', '+38163748021', 'Slobodana Jovanovića', 8, 'Kruševac', 'Serbia', 'profile.png', 'Živim u Kruševcu i izdajem 2 smeštaja.', '2023-02-10 12:00:00'),
    ('HOST', 2, 'Nevena', 'Nevenić', 'nevena@gmail.com', '$2a$12$vA7WOGDUHdulgxXcSjUyf.ZcbWqFyeMLvGWPsD0.yUZ3dxvbHGoyi', '+38165900314', 'Jovana Ristića', 87, 'Niš', 'Serbia', 'profile.png', 'Iz Niš sam i izdajem 3 apartmana.', '2023-03-21 10:00:00'),
    ('HOST', 1, 'Milica', 'Milić', 'milica@gmail.com', '$2a$12$c7B8GKMgOd3bhCznV07Qq.9I20RHUrJ2eoC9ba2ySopngikxJtlHW', '+38163984039', 'Bulevar vojvode Mišića', 74, 'Beograd', 'Serbia', 'profile.png', 'Živim u Beogradu i posedujem nekoliko smeštaja.', '2023-06-08 09:00:00'),
    ('GUEST', 1, 'Dragan', 'Draganić', 'dragan@gmail.com', '$2a$12$3o.HIAcVhoE60mjT8cCtYuGXsHwlu.filb3tB77lKTVGWtuOOhzie', '+38169774829', 'Bulevar vojvode Mišića', 74, 'Beograd', 'Serbia', 'profile.png', null, '2023-08-11 13:00:00'),
    ('GUEST', 3, 'Marija', 'Marijanović', 'marija@gmail.com', '$2a$12$x5kr/gYepaBqSumUrGKFP.MpG9sMO0PmmsG5zu5AdSSBPaFHWz3RC', '+38163756640', 'Stražilovska', 3, 'Novi Sad', 'Serbia', 'profile.png', null, '2021=09-17 15:00:00'),
    ('GUEST', 0, 'Miloš', 'Milošević', 'milos@gmail.com', '$2a$12$ltjGQndex32s0vfh9NGqauwuvdQuJ1a1fdgUYVbJKMnk7z1N9w7Wi', '+381651859940', 'Svetozara Radojčića', 17, 'Beograd', 'Serbia', 'profile.png', null, '2023-10-29 20:00:00');

INSERT INTO user_roles (user_id, role_id)
VALUES (1, 1), (2, 2), (3, 2), (4, 2), (5, 3), (6, 3), (7, 3),
       (1, 4), (2, 4), (3, 4), (4, 4), (5, 4), (6, 4), (7, 4);

INSERT INTO accommodations (host_id, type, title, street, number, city, country, pricing, default_price, automatic_approval, cancellation_due, max_guests, min_guests, description)
VALUES
    (2, 1, 'Soba sa 3 kreveta', 'Rujanska', 23, 'Zlatibor', 'Serbia', 0, 4100.0, false, 172800000, 4, 1, 'Soba sa pogledom na šetalište.'),
    (2, 2, 'Kuća na brdu', 'Bore Vasiljevića', 14, 'Vrnjačka Banja', 'Serbia', 0, 26000.0, false, 172800000, 5, 2, 'Kuća na sprat sa 2 terase.'),
    (3, 0, 'Mali apartman', 'Bulevar Nemanjića', 44, 'Niš', 'Serbia', 0, 4500.0, true, 172800000, 2, 1, 'Mali apartman u strogom centru grada.'),
    (3, 0, 'Veliki apartman', 'Bulevar Nemanjića', 44, 'Niš', 'Serbia', 0, 9000.0, true, 172800000, 4, 1, 'Veliki apartman u strogom centru grada.'),
    (3, 0, 'Delux apartman', 'Bulevar Nemanjića', 45, 'Niš', 'Serbia', 1, 13000.0, false, 172800000, 2, 1, 'Apartman u strogom centru grada.'),
    (4, 1, 'Soba sa bračnim ležajem', 'Južni bulevar', 89, 'Beograd', 'Serbia', 1, 6300.0, true, 172800000, 3, 1, 'Soba sa pogledom na Južni bulevar.'),
    (4, 1, 'Soba sa 3 ležaja', 'Južni bulevar', 40, 'Beograd', 'Serbia', 1, 9700.0, true, 172800000, 5, 2, 'Soba sa pogledom sa 3 ležaja.'),
    (4, 0, 'Veliki apartman', 'Karađorđeva', 92, 'Beograd', 'Serbia', 1, 12000.0, true, 172800000, 7, 3, 'Apartman blizu Kalemegdana sa pogledom na ušće.');

-- INSERT INTO reservations (id, status, accommodation_id, guest_id, guest_number, price, request_date, reservation_date, start_date, end_date)
-- VALUES
--     ();
