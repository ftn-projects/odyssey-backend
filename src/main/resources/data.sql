-- ----------------------------- USERS ------------------------------ --

INSERT INTO roles (name)
VALUES ('ADMIN'), ('HOST'), ('GUEST'), ('USER');

INSERT INTO users (user_role, status, name, surname, email, password, phone, address_street, address_city, address_country, profile_image, bio, created, reservation_requested, reservation_accepted, reservation_declined, reservation_cancelled, profile_reviewed, accommodation_reviewed)
VALUES
    ('USER', 1, 'Marko', 'Marković', 'admin@gmail.com', '$2a$12$ojx4gaAU/odqpcwD7JTHMe0GUCITBrDKe/llCaUVIAmdkaJAr1KjO', '(+381)69423143', 'Bulevar cara Dušana 34', 'Novi Sad', 'Serbia', 'profile.png', null, '2023-01-01 11:00:00', null, null, null, null, true, true),
    ('HOST', 1, 'Petar', 'Petrović', 'petar@gmail.com', '$2a$12$xygRv8VL.sBMKEgrrioX7OS81Zn4V5gHrLNnVHJ1HLP6ISXzzZf2G', '(+381)63748021', 'Slobodana Jovanovića 8', 'Kruševac', 'Serbia', 'profile.png', 'Živim u Kruševcu i izdajem 2 smeštaja.', '2023-02-10 12:00:00', true, null, null, true, true, true),
    ('HOST', 1, 'Nevena', 'Nevenić', 'nevena@gmail.com', '$2a$12$AxVIzXc9jiBsVPppHOTTPesNsqUp4gvIl2sSK9FtXdDdlSbXMl1ea', '(+381)65900314', 'Jovana Ristića 87', 'Niš', 'Serbia', 'profile.png', 'Iz Niš sam i izdajem 3 apartmana.', '2023-03-21 10:00:00', true, null, null, true, true, true),
    ('HOST', 2, 'Milica', 'Milić', 'milica@gmail.com', '$2a$12$b2qhCMoji6RCk0mGZxaNROJMDUHmC.0s4chAqIqiClMbpUcV/qjNq', '(+381)63984039', 'Bulevar vojvode Mišića 74', 'Beograd', 'Serbia', 'profile.png', 'Živim u Beogradu i posedujem nekoliko smeštaja.', '2023-06-08 09:00:00', true, null, null, true, true, true),
    ('GUEST', 1, 'Dragan', 'Draganić', 'dragan@gmail.com', '$2a$12$KmFHBhd8NSsRcA7ThwY6eOMTru7w/RQqoCXoWFQmxpUvyrUu0pAWy', '(+381)69774829', 'Bulevar vojvode Mišića 64', 'Beograd', 'Serbia', 'profile.png', null, '2023-08-11 13:00:00', true, true, true, null, null, null),
    ('GUEST', 3, 'Marija', 'Marijanović', 'marija@gmail.com', '$2a$12$jiT6J4jdbtxjBZpZFahyY.DDlqGjLmNhssjhw.s79/8LBMeKsCcZO', '(+381)63756640', 'Stražilovska 3', 'Novi Sad', 'Serbia', 'profile.png', null, '2021=09-17 15:00:00', true, true, true, null, null, null),
    ('GUEST', 1, 'Miloš', 'Milošević', 'milos@gmail.com', '$2a$12$3ZFBD2qXIFeHtXji.PwF6udh67B6i8SccPsXKs6BbqhrHcgkBS3L6', '(+381)651859940', 'Svetozara Radojčića 17', 'Beograd', 'Serbia', 'profile.png', null, '2023-10-29 20:00:00', true, true, true, null, null, null),
    ('GUEST', 0, 'Novi', 'Nalog', 'novi@gmail.com', '$2a$12$3ZFBD2qXIFeHtXji.PwF6udh67B6i8SccPsXKs6BbqhrHcgkBS3L6', '(+381)0000000', 'Neka Ulica 99', 'Beograd', 'Serbia', 'profile.png', null, '2023-12-21 08:00:00', true, true, true, null, null, null);

INSERT INTO user_roles (user_id, role_id)
VALUES
    (1, 1), (2, 2), (3, 2), (4, 2), (5, 3), (6, 3), (7, 3),
    (1, 4), (2, 4), (3, 4), (4, 4), (5, 4), (6, 4), (7, 4);

-- ------------------------- ACCOMMODATIONS ------------------------- --

INSERT INTO accommodations (host_id, type, title, description, address_street, address_city, address_country, pricing, default_price, automatic_approval, cancellation_due, min_guests, max_guests)
VALUES
    (2, 1, 'Soba sa 3 kreveta', 'Soba sa pogledom na šetalište.', 'Rujanska 23', 'Zlatibor', 'Serbia', 0, 410.0, false, 4.32e14, 1, 4),
    (2, 2, 'Kuća na brdu', 'Kuća na sprat sa 2 terase.', 'Bore Vasiljevića 14', 'Vrnjačka Banja', 'Serbia', 0, 260.0, false, 8.64e14, 2, 5),
    (3, 0, 'Mali apartman', 'Mali apartman u strogom centru grada.', 'Bulevar Nemanjića 44', 'Niš', 'Serbia', 0, 450.0, true, 6.048e14, 1, 2),
    (3, 0, 'Veliki apartman', 'Veliki apartman u strogom centru grada.', 'Bulevar Nemanjića 44', 'Niš', 'Serbia', 0, 900.0, true, 1.2096e15, 1, 4),
    (3, 0, 'Delux apartman', 'Apartman u strogom centru grada.', 'Bulevar Nemanjića 45', 'Niš', 'Serbia', 1, 130.0, false, 2.592e14, 1, 2),
    (4, 1, 'Soba sa bračnim ležajem', 'Soba sa pogledom na Južni bulevar.', 'Južni bulevar 89', 'Beograd', 'Serbia', 1, 630.0, true, 7.776e14, 1, 3),
    (4, 1, 'Soba sa 3 ležaja', 'Soba sa pogledom sa 3 ležaja.', 'Južni bulevar 40', 'Beograd', 'Serbia', 1, 9700.0, true, 1.728e15, 2, 5),
    (4, 0, 'Veliki apartman', 'Apartman blizu Kalemegdana sa pogledom na ušće.', 'Karađorđeva 92', 'Beograd', 'Serbia', 1, 10000.0, true, 2.592e15, 3, 7);

INSERT INTO accommodation_available_slots (accommodation_id, price, start_date, end_date)
VALUES
    (1, 520.0, '2023-12-20', '2024-01-05'), (1, 450.0, '2024-01-10 ', '2024-01-20 '), (1, 430.0, '2024-01-25', '2024-02-10'),
    (2, 340.0, '2023-12-20', '2024-01-05'), (2, 300.0, '2024-01-10 ', '2024-01-20 '), (2, 320.0, '2024-01-25', '2024-02-10'),
    (3, 560.0, '2023-12-20', '2024-01-05'), (3, 51.0, '2024-01-10 ', '2024-01-20 '), (3, 420.0, '2024-01-25', '2024-02-10'),
    (4, 940.0, '2023-12-20', '2024-01-05'), (4, 910.0, '2024-01-10 ', '2024-01-20 '), (4, 850.0, '2024-01-25', '2024-02-10'),
    (5, 180.0, '2023-12-20', '2024-01-05'), (5, 150.0, '2024-01-10 ', '2024-01-20 '), (5, 140.0, '2024-01-25', '2024-02-10'),
    (6, 810.0, '2023-12-20', '2024-01-05'), (6, 760.0, '2024-01-10 ', '2024-01-20 '), (6, 700.0, '2024-01-25', '2024-02-10'),
    (7, 580.0, '2023-12-20', '2024-01-05'), (7, 510.0, '2024-01-10 ', '2024-01-20 '), (7, 500.0, '2024-01-25', '2024-02-10'),
    (8, 1600.0, '2023-12-20', '2024-01-05'), (8, 1500.0, '2024-01-10 ', '2024-01-20 '), (8, 1400.0, '2024-01-25', '2024-02-10');

INSERT INTO accommodation_images (accommodation_id, images)
VALUES
    (1, 'tropical1.webp'), (1, 'tropical2.webp'), (1, 'tropical3.webp'), (1, 'tropical4.webp'), (1, 'tropical5.webp'), (1, 'tropical6.webp'),
    (2, '1.webp'), (2, '2.webp'), (2, '3.webp'), (2, '4.webp'), (2, '5.webp'), (2, '6.webp'),
    (3, '1.webp'), (3, '2.webp'), (3, '3.webp'), (3, '4.webp'), (3, '5.webp'), (3, '6.webp'),
    (4, '1.webp'), (4, '2.webp'), (4, '3.webp'), (4, '4.webp'), (4, '5.webp'), (4, '6.webp'),
    (5, '1.webp'), (5, '2.webp'), (5, '3.webp'), (5, '4.webp'), (5, '5.webp'), (5, '6.webp'),
    (6, '1.webp'), (6, '2.webp'), (6, '3.webp'), (6, '4.webp'), (6, '5.webp'), (6, '6.webp'),
    (7, '1.webp'), (7, '2.webp'), (7, '3.webp'), (7, '4.webp'), (7, '5.webp'), (7, '6.webp'),
    (8, '1.webp'), (8, '2.webp'), (8, '3.webp'), (8, '4.webp'), (8, '5.webp'), (8, '6.webp');

-- --------------------- ACCOMMODATION REQUESTS --------------------- --

INSERT INTO accommodation_requests (type, status, submission_date, host_id, accommodation_id, new_accommodation_type, new_title, new_description, address_street, address_city, address_country, new_pricing, new_default_price, new_automatic_approval, new_cancellation_due, new_min_guests, new_max_guests)
VALUES
    (0, 2, '2023-11-4 12:00:00', 2, null, 1, 'Soba', '', 'Dušana Radovića 40', 'Vrnjačka Banja', 'Serbia', 0, 4500.0, true, 125, 1, 3),
    (0, 0, '2023-11-6 12:00:00', 3, null, 0, 'Duplex apartman', 'Apartman na periferiji.', 'Bulevar Oslobođenja 34', 'Novi Sad', 'Serbia', 1, 6400.0, false, 1265, 1, 4),
    (1, 2, '2023-11-8 12:00:00', 2, 2, 2, 'Neki apartman', 'Kuća na sprat sa 2 terase.', 'Bore Vasiljevića 14', 'Vrnjačka', '', 0, 26000.0, false, 563, 2, 5),
    (1, 1, '2023-11-10 12:00:00', 3, 4, 0, 'Veliki apartman', 'Veliki apartman u strogom centru grada.', 'Bulevar Nemanjića 44', 'Niš', 'Serbia', 0, 9000.0, true, 2446, 1, 4),
    (1, 0, '2023-11-12 12:00:00', 4, 6, 1, 'Soba sa velikim ležajem', 'Soba sa terasom i pogledom na Južni bulevar.', 'Južni bulevar 89', 'Beograd', 'Serbia', 1, 8100.0, true, 76, 1, 3);

INSERT INTO accommodation_request_new_available_slots (accommodation_request_id, price, start_date, end_date)
VALUES
    (1, 210.0, '2023-12-20', '2024-01-05'), (1, 19.0, '2024-01-10 ', '2024-01-20 '), (1, 175.0, '2024-01-25', '2024-02-10'),
    (2, 530.0, '2023-12-20', '2024-01-05'), (2, 480.0, '2024-01-10 ', '2024-01-20 '), (2, 460.0, '2024-01-25', '2024-02-10'),
    (3, 999.0, '2023-12-20', '2024-01-05'), (3, 999.0, '2024-01-10 ', '2024-01-20 '), (3, 999.0, '2024-01-25', '2024-02-10'),
    (4, 590.0, '2023-12-20', '2024-01-05'), (4, 480.0, '2024-01-10 ', '2024-01-20 '), (4, 450.0, '2024-01-25', '2024-02-10'),
    (5, 410.0, '2023-12-20', '2024-01-05'), (5, 395.0, '2024-01-10 ', '2024-01-20 '), (5, 350.0, '2024-01-25', '2024-02-10');

INSERT INTO accommodation_request_new_images (accommodation_request_id, new_images)
VALUES
    (1, 'small-accommodation.webp'), (2, 'small-accommodation.webp'), (3, 'small-accommodation.webp'), (5, 'small-accommodation.webp'),
    (4, '1.webp'), (4, '2.webp'), (4, '3.webp'), (4, '4.webp'), (4, '5.webp'), (4, '6.webp');

-- --------------------------- AMENITIES ---------------------------- --

INSERT INTO amenities (title)
VALUES ('TV'), ('WiFi'), ('Kitchen'), ('Free parking'), ('Beach access'), ('Washer'), ('Spa'), ('Air conditioning'), ('King bed'), ('Smoking room');

INSERT INTO accommodation_has_amenity (accommodation_id, amenity_id)
VALUES
    (1, 1), (1, 2), (1, 4), (1, 6), (1, 7), (1, 9),
    (2, 1), (2, 2), (2, 5), (2, 7), (2, 10),
    (3, 2), (3, 4), (3, 6), (3, 8), (3, 9), (3, 10),
    (4, 1), (4, 2), (4, 3), (4, 4), (4, 6), (4, 7), (4, 9),
    (5, 2), (5, 4), (5, 5), (5, 7), (5, 8),
    (6, 1), (6, 2), (6, 3), (6, 5), (6, 7), (6, 9),
    (7, 2), (7, 3), (7, 7), (7, 8),
    (8, 1), (8, 2), (8, 3), (8, 5), (8, 7), (8, 9);

INSERT INTO accommodation_request_has_amenity (request_id, amenity_id)
VALUES
    (1, 1), (1, 2), (1, 4), (1, 8),
    (2, 1), (2, 2), (2, 5), (2, 7), (2, 8), (2, 10),
    (3, 2), (3, 4), (3, 5), (3, 8), (3, 10),
    (4, 1), (4, 2), (4, 3), (4, 4), (4, 6), (4, 7), (4, 9),
    (5, 2), (5, 4), (5, 5), (5, 7), (5, 8), (5, 9);


-- ------------------------- GUEST FAVORITES --------------------------- --
INSERT INTO guest_favourited(accommodation_id, user_id)
VALUES
    (1,5), (2,5), (3,5), (4,5),
    (3,6), (7,6),
    (8,7);

-- ------------------------- RESERVATIONS --------------------------- --

INSERT INTO reservations (status, accommodation_id, guest_id, guest_number, price, request_date, reservation_date, start_date, end_date)
VALUES
    (1, 4, 5, 2, 520.0 * 2 * 2, '2023-12-22', null, '2023-12-22', '2023-12-23'),
    (1, 2, 7, 2, 430.0 * 4 * 2, '2023-12-22', null, '2023-12-26', '2023-12-30'),
    (1, 3, 7, 3, 300.0 * 5 * 3, '2023-12-24', '2023-12-26', '2024-01-13', '2024-01-18'),
    (2, 4, 7, 2, 150.0 * 6, '2023-12-25', null, '2024-01-14', '2024-01-20'),
    (3, 5, 7, 3, 450.0 * 4 * 3, '2023-12-27', null, '2024-01-11', '2024-01-15'),
    (1, 1, 7, 1, 430.0 * 3 * 1, '2023-12-20', '2023-12-26', '2024-01-01', '2024-01-04'),
    (1, 1, 7, 3, 300.0 * 5 * 3, '2024-01-1', '2024-01-01', '2024-06-13', '2024-06-18'),
    (0, 1, 7, 2, 520.0 * 2 * 2, '2023-12-22', null, '2024-01-29', '2024-01-30'),
    (1, 2, 7, 2, 430.0 * 4 * 2, '2023-12-22', '2023-12-22', '2023-12-26', '2023-12-30'),
    (2, 1, 7, 3, 300.0 * 5 * 3, '2023-12-24', null, '2024-01-13', '2024-01-18'),
    (3, 2, 7, 2, 150.0 * 6, '2023-12-25', null, '2024-01-14', '2024-01-20'),
    (4, 2, 7, 3, 450.0 * 4 * 3, '2023-12-27', null, '2024-01-11', '2024-01-15'),
    (0, 1, 6, 1, 430.0 * 3 * 1, '2023-12-20', null, '2024-01-28', '2024-01-30'),
    (1, 1, 7, 2, 430.0 * 4 * 2, '2023-12-22', '2023-12-22', '2024-01-22', '2024-01-24');

-- ---------------------------- REVIEWS ----------------------------- --

INSERT INTO reviews (accommodation_id, host_id, submitter_id, rating, comment, submission_date, status, type)
VALUES
    (1, null, 7, 5, 'Soba je odlična, sve je bilo super.', '2023-01-05 20:00:00', 2, 'AR'),
    (2, null, 7, 4, 'Kuća je odlična, sve je bilo super.', '2023-01-20 20:00:00', 2, 'AR'),
    (3, null, 7, 3, 'Apartman je odličan, sve je bilo super.', '2024-01-01 20:00:00', 2, 'AR'),
    (4, null, 7, 2, 'Apartman je odličan, sve je bilo super.', '2023-02-10 20:00:00', 2, 'AR'),
    (5, null, 7, 1, 'Soba je odlična, sve je bilo super.', '2023-01-05 20:00:00', 2, 'AR'),
    (6, null, 7, 5, 'Kuća je odlična, sve je bilo super.', '2023-01-20 20:00:00', 2, 'AR'),
    (7, null, 7, 4, 'Apartman je odličan, sve je bilo super.', '2024-01-04 20:00:00', 2, 'AR'),
    (7, null, 6, 3, 'Apartman je odličan, sve je bilo super.', '2023-02-10 20:00:00', 2, 'AR'),
    (null, 2, 7, 3, 'BRT MI SMO NAAACI.', '2023-02-10 20:00:00', 2, 'HR');

-- ------------------------- HOST REVIEWS --------------------------- --

INSERT INTO reviews (host_id, submitter_id, rating, comment, submission_date, status, type)
VALUES
    (2, 7, 5, 'Host odlican.', '2023-01-05 20:00:00', 2, 'HR'),
    (3, 7, 4, 'Vlasnik veoma kulturan.', '2023-01-20 20:00:00', 2, 'HR'),
    (4, 6, 3, 'Uzas Bozji, majko mila kakva budala od vlasnika.', '2024-01-24 20:00:00', 1, 'HR'),
    (2, 5, 2, 'Amazing. Totally wasnt paid to write this', '2023-02-10 20:00:00', 0, 'HR');

-- ------------------------- NOTIFICATIONS -------------------------- --

INSERT INTO notifications (type, receiver_id, read, date, title, description, notification_type, accommodation_review_id, host_review_id, reservation_id)
VALUES
    (0, 2, true, '2023-02-10 12:00:00', 'Welcome to Odyssey', 'You can visit home page to browse accommodations of our hosts. Have a great stay :)', 'NOTIFICATION', null, null, null),
    (0, 3, false, '2023-03-21 10:00:00', 'Welcome to Odyssey', 'You can visit home page to browse accommodations of our hosts. Have a great stay :)', 'NOTIFICATION', null, null, null),
    (0, 4, false, '2023-06-08 09:00:00', 'Welcome to Odyssey', 'You can visit home page to browse accommodations of our hosts. Have a great stay :)', 'NOTIFICATION', null, null, null),
    (0, 5, false, '2023-08-11 13:00:00', 'Welcome to Odyssey', 'You can visit home page to browse accommodations of our hosts. Have a great stay :)', 'NOTIFICATION', null, null, null),
    (0, 6, false, '2021=09-17 15:00:00', 'Welcome to Odyssey', 'You can visit home page to browse accommodations of our hosts. Have a great stay :)', 'NOTIFICATION', null, null, null),
    (0, 7, false, '2023-10-29 20:00:00', 'Welcome to Odyssey', 'You can visit home page to browse accommodations of our hosts. Have a great stay :)', 'NOTIFICATION', null, null, null);
