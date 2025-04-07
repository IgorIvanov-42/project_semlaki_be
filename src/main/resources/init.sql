# Users
INSERT INTO semlaki_be.user (password, email, first_name, last_name) VALUES
                                                                         ('$2a$12$0sLaUZQQDev9GC3VZ7tNfeOp814c.rA9hdh8TR/2IY0d1yuWI/X2G', 'john.doe@example.com', 'John', 'Doe'),
                                                                         ('$2a$12$0sLaUZQQDev9GC3VZ7tNfeOp814c.rA9hdh8TR/2IY0d1yuWI/X2G', 'jane.smith@example.com', 'Jane', 'Smith'),
                                                                         ('$2a$12$0sLaUZQQDev9GC3VZ7tNfeOp814c.rA9hdh8TR/2IY0d1yuWI/X2G', 'michael.brown@example.com', 'Michael', 'Brown'),
                                                                         ('$2a$12$0sLaUZQQDev9GC3VZ7tNfeOp814c.rA9hdh8TR/2IY0d1yuWI/X2G', 'emily.johnson@example.com', 'Emily', 'Johnson'),
                                                                         ('$2a$12$0sLaUZQQDev9GC3VZ7tNfeOp814c.rA9hdh8TR/2IY0d1yuWI/X2G', 'david.wilson@example.com', 'David', 'Wilson'),
                                                                         ('$2a$12$0sLaUZQQDev9GC3VZ7tNfeOp814c.rA9hdh8TR/2IY0d1yuWI/X2G', 'sophia.miller@example.com', 'Sophia', 'Miller'),
                                                                         ('$2a$12$0sLaUZQQDev9GC3VZ7tNfeOp814c.rA9hdh8TR/2IY0d1yuWI/X2G', 'james.moore@example.com', 'James', 'Moore'),
                                                                         ('$2a$12$0sLaUZQQDev9GC3VZ7tNfeOp814c.rA9hdh8TR/2IY0d1yuWI/X2G', 'olivia.taylor@example.com', 'Olivia', 'Taylor');


# Category
INSERT INTO semlaki_be.categories (title, description, photo) VALUES
                                                                  ('Services', 'Services and announcements.', '/src/assets/services.webp'),
                                                                  ('Childrens Leisure', 'Entertainment and clubs for children.', '/src/assets/Childcare.jpg'),
                                                                  ('Announcements', 'Buy and sell items.', '/src/assets/Translation.jpg'),
                                                                  ('Education', 'Courses and educational institutions.', '/src/assets/images.jpg'),
                                                                  ('Medicine', 'Doctors and medical services.', '/src/assets/Med.jpg'),
                                                                  ('IT in Germany', 'IT innovations, job opportunities.', '/src/assets/it.jpg'),
                                                                  ('Parents and Children', 'Activities for families.', '/src/assets/Parents1.jpg'),
                                                                  ('Meet & Chat', 'Meet new people and make connections.', '/src/assets/Com.jpg');


# Services