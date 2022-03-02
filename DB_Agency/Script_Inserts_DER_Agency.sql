use agency_db;

INSERT INTO hotels VALUES(1,"Hotel Parque", "Ciudad de Mexico");
INSERT INTO hotels VALUES(2,"Hotel Amazona", "Monterrey");
INSERT INTO hotels VALUES(3,"Hotel Arizona", "Queretaro");
INSERT INTO hotels VALUES(4,"Hotel Valle", "Michoacan");
INSERT INTO hotels VALUES(5,"Hotel Cumbres", "Sonora");
INSERT INTO hotels VALUES(6,"Hotel Leones", "Puebla");
INSERT INTO hotels VALUES(7,"Hotel Bosques", "Tabasco");
INSERT INTO hotels VALUES(8,"Hotel Central", "Veracruz");
INSERT INTO hotels VALUES(9,"Hotel Costa", "Tamaulipas");

INSERT INTO rooms VALUES(1, "king zise", 1252.22, 5);
INSERT INTO rooms VALUES(2, "Junior", 452.22, 4);
INSERT INTO rooms VALUES(3, "Simple", 852.22, 3);
INSERT INTO rooms VALUES(4, "Suite", 9252.22, 2);
INSERT INTO rooms VALUES(5, "Simple", 752.22, 1);
INSERT INTO rooms VALUES(6, "Junior", 852.22, 6);
INSERT INTO rooms VALUES(7, "Spa", 5252.22, 7);

INSERT INTO bookings VALUES(1, now(),now(), 2, 1);
INSERT INTO bookings VALUES(2, now(),now(), 1, 2);
INSERT INTO bookings VALUES(3, now(),now(), 3, 3);
INSERT INTO bookings VALUES(4, now(),now(), 4, 4);
INSERT INTO bookings VALUES(5, now(),now(), 5, 1);
INSERT INTO bookings VALUES(6, now(),now(), 1, 2);
INSERT INTO bookings VALUES(7, now(),now(), 2, 3);
INSERT INTO bookings VALUES(8, now(),now(), 3, 4);
INSERT INTO bookings VALUES(9, now(),now(), 4, 5);
INSERT INTO bookings VALUES(10, now(),now(), 5, 1);

INSERT INTO people VALUES(1, "TEST01025823", "Raul Eugenio", "Ceron Pineda", "1999-02-12", "test1@gmail.com");
INSERT INTO people VALUES(2, "TEST01025843", "Jorge Alberto", "Cantu Aguilar", "1999-01-11", "test2@gmail.com");
INSERT INTO people VALUES(3, "TEST01025846", "Israel", "Hernandez Vazquez", "1999-03-10", "test3@gmail.com");
INSERT INTO people VALUES(4, "TEST01025847", "Pedro", "Lopez Guillen", "1999-04-09", "test4@gmail.com");
INSERT INTO people VALUES(5, "TEST01025848", "Jacinto", "Nuñes Lopez", "1999-05-08", "test5@gmail.com");
INSERT INTO people VALUES(6, "TEST01025845", "Lupita", "Zalazar Perez", "1999-06-07", "test6@gmail.com");
INSERT INTO people VALUES(7, "TEST01025877", "Veronica", "Sarmiento Lunea", "1999-07-06", "test7@gmail.com");
INSERT INTO people VALUES(8, "TEST01025890", "Cristina", "Lomeli Franco", "1999-08-05", "test8@gmail.com");
INSERT INTO people VALUES(9, "TEST01025802", "Maclovio", "Aco Domingues", "1999-09-04", "test9@gmail.com");
INSERT INTO people VALUES(10, "TEST0102432", "Mario", "Cantu Lopez", "1999-10-03", "test10@gmail.com");

INSERT INTO customers VALUES(1,"Pedro Lopez");
INSERT INTO customers VALUES(2,"Romina Perez");
INSERT INTO customers VALUES(3,"Paulo Dominguez");
INSERT INTO customers VALUES(4,"Mario Cardenas");
INSERT INTO customers VALUES(5,"Angelica Rivera");
INSERT INTO customers VALUES(6,"Enrique Peña");
INSERT INTO customers VALUES(7,"Felipe Calderon");
INSERT INTO customers VALUES(8,"Vicente Fox");
INSERT INTO customers VALUES(9,"Martha Wayne");
INSERT INTO customers VALUES(10,"Andre Manuel Lopez");

INSERT INTO flights VALUES(1, "VUE123", "Monterey", "Queretaro", "2022-03-03", "2022-03-09");
INSERT INTO flights VALUES(2, "VUE234", "Cancun", "CDMX", "2022-04-03", "2022-04-09");
INSERT INTO flights VALUES(3, "VUE456", "Queretaro", "CDMX", "2022-05-03", "2022-05-09");
INSERT INTO flights VALUES(4, "VUE645", "Acapulco", "CDMX", "2022-06-03", "2022-06-09");
INSERT INTO flights VALUES(5, "VUE234", "CDMX", "Monterey", "2022-07-03", "2022-07-09");
INSERT INTO flights VALUES(6, "VUE678", "Monterey", "Tamaulipas", "2022-08-01", "2022-08-09");
INSERT INTO flights VALUES(7, "VUE809", "Tamaulipas", "Acapulco", "2022-09-08", "2022-09-15");
INSERT INTO flights VALUES(8, "VUE583", "Queretaro", "Cancun", "2022-10-07", "2022-10-18");
INSERT INTO flights VALUES(9, "VUE2151", "Saltillo", "Sonora", "2022-03-04", "2022-03-09");
INSERT INTO flights VALUES(10, "VUE2151", "Saltillo", "Sonora", "2022-08-12", "2022-08-19");

INSERT INTO classes VALUES(1, "Ordinario", 1, 1000.00, 1);
INSERT INTO classes VALUES(2, "Confort", 1, 1200.00, 2);
INSERT INTO classes VALUES(3, "Turismo", 1, 900.00, 3);
INSERT INTO classes VALUES(4, "VIP", 1, 2000.00, 4);
INSERT INTO classes VALUES(5, "VIP", 1, 2500.00, 5);
INSERT INTO classes VALUES(6, "Turismo", 1, 900.00, 1);
INSERT INTO classes VALUES(7, "Confort", 1, 1200.00, 1);
INSERT INTO classes VALUES(8, "Ordinario", 1, 800.00, 1);
INSERT INTO classes VALUES(9, "Confort", 1, 1200.00, 1);
INSERT INTO classes VALUES(10, "Turismo", 1, 900.00, 1);

INSERT INTO reservations VALUES(1,1,1);
INSERT INTO reservations VALUES(2,2,2);
INSERT INTO reservations VALUES(3,3,3);
INSERT INTO reservations VALUES(4,4,4);
INSERT INTO reservations VALUES(5,5,5);
INSERT INTO reservations VALUES(6,4,6);
INSERT INTO reservations VALUES(7,5,7);
INSERT INTO reservations VALUES(8,8,8);
INSERT INTO reservations VALUES(9,5,10);
INSERT INTO reservations VALUES(10,5,10);

INSERT INTO reservation_people VALUES(1,10,10);
INSERT INTO reservation_people VALUES(2,1,9);
INSERT INTO reservation_people VALUES(3,2,8);
INSERT INTO reservation_people VALUES(4,3,7);
INSERT INTO reservation_people VALUES(5,4,6);
INSERT INTO reservation_people VALUES(6,5,5);
INSERT INTO reservation_people VALUES(7,6,4);
INSERT INTO reservation_people VALUES(8,7,3);
INSERT INTO reservation_people VALUES(9,8,2);
INSERT INTO reservation_people VALUES(10,9,1);

INSERT INTO reservation_customer VALUES(1,10,10);
INSERT INTO reservation_customer VALUES(2,1,9);
INSERT INTO reservation_customer VALUES(3,2,8);
INSERT INTO reservation_customer VALUES(4,3,7);
INSERT INTO reservation_customer VALUES(5,4,6);
INSERT INTO reservation_customer VALUES(6,5,5);
INSERT INTO reservation_customer VALUES(7,6,4);
INSERT INTO reservation_customer VALUES(8,7,3);
INSERT INTO reservation_customer VALUES(9,8,2);
INSERT INTO reservation_customer VALUES(10,9,1);

INSERT INTO booking_customer VALUES(1,10,10);
INSERT INTO booking_customer VALUES(2,1,9);
INSERT INTO booking_customer VALUES(3,2,8);
INSERT INTO booking_customer VALUES(4,3,7);
INSERT INTO booking_customer VALUES(5,4,6);
INSERT INTO booking_customer VALUES(6,5,5);
INSERT INTO booking_customer VALUES(7,6,4);
INSERT INTO booking_customer VALUES(8,7,3);
INSERT INTO booking_customer VALUES(9,8,2);
INSERT INTO booking_customer VALUES(10,9,1);

INSERT INTO booking_people VALUES(1,1,2);
INSERT INTO booking_people VALUES(2,2,3);
INSERT INTO booking_people VALUES(3,3,4);
INSERT INTO booking_people VALUES(4,4,5);
INSERT INTO booking_people VALUES(5,5,6);
INSERT INTO booking_people VALUES(6,6,7);
INSERT INTO booking_people VALUES(7,7,8);
INSERT INTO booking_people VALUES(8,9,1);
INSERT INTO booking_people VALUES(9,10,2);
INSERT INTO booking_people VALUES(10,1,2);





