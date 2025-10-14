INSERT INTO repair_services (service_name, service_category, service_price_sek) VALUES ("Screen replacement", "ELECTRONICS", 1190.99);
INSERT INTO repair_services (service_name, service_category, service_price_sek) VALUES ("Battery replacement", "ELECTRONICS", 499.99);
INSERT INTO repair_services (service_name, service_category, service_price_sek) VALUES ("Tire swap", "MECHANIC", 539.99);
INSERT INTO repair_services (service_name, service_category, service_price_sek) VALUES ("Headlight fluid swap", "MECHANIC", 1539.99);
INSERT INTO repair_services (service_name, service_category, service_price_sek) VALUES ("Grunka", "MECHANIC", 139.99);

INSERT INTO technicians (tech_name, expertise) VALUES ("Mulle Meck", "MECHANIC");
INSERT INTO technicians (tech_name, expertise) VALUES ("Maurice Moss", "ELECTRONICS");
INSERT INTO technicians (tech_name, expertise) VALUES ("Louis Rossmann", "APPLIANCES");

INSERT INTO tech_service (service_id, tech_id) VALUES (1,3)
INSERT INTO tech_service (service_id, tech_id) VALUES (1,4)
INSERT INTO tech_service (service_id, tech_id) VALUES (1,5)
INSERT INTO tech_service (service_id, tech_id) VALUES (2,1)
INSERT INTO tech_service (service_id, tech_id) VALUES (2,2)

INSERT INTO repair_tickets (customer, service_id, is_canceled, total_sek, date) VALUES ('anna.andersson@mail.se', 1, false, 1190.99, 1759867493)
INSERT INTO repair_tickets (customer, service_id, is_canceled, total_sek, date) VALUES ('erik.eriksson@mail.se', 2, true, 499.99, 1759867493)
INSERT INTO repair_tickets (customer, service_id, is_canceled, total_sek, date) VALUES ('maria.malm@mail.se', 2, false, 499.99, 1759869493)
