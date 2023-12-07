INSERT INTO users (id, user_role, role, status, name, surname, email, password, phone, street, number, city, country, profile_image, bio)
VALUES
    (1, 'ADMIN', 0, 1, 'Marko', 'Marković', 'admin@gmail.com', 'admin', '+38169423143', 'Bulevar cara Dušana', 34, 'Novi Sad', 'Serbia', null, null),
    (2, 'HOST', 1, 1, 'Petar', 'Petrović', 'petar@gmail.com', 'petar', '+38163748021', 'Slobodana Jovanovića', 8, 'Kruševac', 'Serbia', null, 'Živim u Kruševcu i izdajem 2 smeštaja.'),
    (3, 'HOST', 1, 2, 'Nevena', 'Nevenić', 'nevena@gmail.com', 'nevena', '+38165900314', 'Jovana Ristića', 87, 'Niš', 'Serbia', null, 'Iz Niš sam i izdajem 3 apartmana.'),
    (4, 'GUEST', 2, 1, 'Dragan', 'Draganić', 'dragan@gmail.com', 'dragan', '+38169774829', 'Bulevar vojvode Mišića', 74, 'Beograd', 'Serbia', null, null),
    (5, 'GUEST', 2, 3, 'Marija', 'Marijanović', 'marija@gmail.com', 'marija', '+38163756640', 'Stražilovska', 3, 'Novi Sad', 'Serbia', null, null),
    (6, 'GUEST', 2, 0, 'Miloš', 'Milošević', 'milos@gmail.com', 'milos', '+381651859940', 'Svetozara Radojčića', 17, 'Beograd', 'Serbia', null, null);

INSERT INTO accommodations (id, host_id, type, title, street, number, city, country, pricing, default_price, automatic_approval, cancellation_due, max_guests, min_guests, description)
VALUES
    (1, 2, 1, 'Soba sa 3 kreveta', 'Slobodana Jovanovića', 8, 'Kruševac', 'Serbia', 0, 1800.0, false, 172800000, 4, 1, 'Soba sa pogledom na šetalište.'),
    (2, 3, 0, 'Delux apartman', 'Bulevar Nemanjića', 45, 'Niš', 'Serbia', 1, 4000.0, true, 172800000, 2, 1, 'Apartman u strogom centru grada.');