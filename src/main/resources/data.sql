INSERT INTO roles (id, name)
VALUES
    (1,'ADMIN'),
    (2,'HOST'),
    (3,'GUEST');

INSERT INTO users (id, user_role, status, name, surname, email, password, phone, street, number, city, country, profile_image, bio)
VALUES
    (1, 'ADMIN', 1, 'Marko', 'Marković', 'admin@gmail.com', '$2a$12$ojx4gaAU/odqpcwD7JTHMe0GUCITBrDKe/llCaUVIAmdkaJAr1KjO', '+38169423143', 'Bulevar cara Dušana', 34, 'Novi Sad', 'Serbia', null, null),
    (2, 'HOST', 1, 'Petar', 'Petrović', 'petar@gmail.com', '$2a$12$xygRv8VL.sBMKEgrrioX7OS81Zn4V5gHrLNnVHJ1HLP6ISXzzZf2G', '+38163748021', 'Slobodana Jovanovića', 8, 'Kruševac', 'Serbia', null, 'Živim u Kruševcu i izdajem 2 smeštaja.'),
    (3, 'HOST', 2, 'Nevena', 'Nevenić', 'nevena@gmail.com', '$2a$12$vA7WOGDUHdulgxXcSjUyf.ZcbWqFyeMLvGWPsD0.yUZ3dxvbHGoyi', '+38165900314', 'Jovana Ristića', 87, 'Niš', 'Serbia', null, 'Iz Niš sam i izdajem 3 apartmana.'),
    (4, 'GUEST', 1, 'Dragan', 'Draganić', 'dragan@gmail.com', '$2a$12$3o.HIAcVhoE60mjT8cCtYuGXsHwlu.filb3tB77lKTVGWtuOOhzie', '+38169774829', 'Bulevar vojvode Mišića', 74, 'Beograd', 'Serbia', null, null),
    (5, 'GUEST', 3, 'Marija', 'Marijanović', 'marija@gmail.com', '$2a$12$x5kr/gYepaBqSumUrGKFP.MpG9sMO0PmmsG5zu5AdSSBPaFHWz3RC', '+38163756640', 'Stražilovska', 3, 'Novi Sad', 'Serbia', null, null),
    (6, 'GUEST', 0, 'Miloš', 'Milošević', 'milos@gmail.com', '$2a$12$ltjGQndex32s0vfh9NGqauwuvdQuJ1a1fdgUYVbJKMnk7z1N9w7Wi', '+381651859940', 'Svetozara Radojčića', 17, 'Beograd', 'Serbia', null, null);

INSERT INTO user_roles (user_id, role_id)
VALUES
    (1,1),
    (2,2),
    (3,2),
    (4,3),
    (5,3),
    (6,3);

INSERT INTO accommodations (id, host_id, type, title, street, number, city, country, pricing, default_price, automatic_approval, cancellation_due, max_guests, min_guests, description)
VALUES
    (1, 2, 1, 'Soba sa 3 kreveta', 'Slobodana Jovanovića', 8, 'Kruševac', 'Serbia', 0, 1800.0, false, 172800000, 4, 1, 'Soba sa pogledom na šetalište.'),
    (2, 3, 0, 'Delux apartman', 'Bulevar Nemanjića', 45, 'Niš', 'Serbia', 1, 4000.0, true, 172800000, 2, 1, 'Apartman u strogom centru grada.');
