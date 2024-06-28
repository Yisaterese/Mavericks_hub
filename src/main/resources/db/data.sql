truncate table users cascade;
truncate table media cascade;

insert into users(id, email,password,time_created) values
(200,'teresejosephyisa@gmail.com','password','2024-06-04T15:03:03.7992009700' ),
(201,'yisajoseph98@gmail.com','password','2024-06-04T15:03:03.7992009700' ),
(202,'victormsonter@gmail.com','password','2024-06-04T15:03:03.7992009700');

insert into media (id, category,description,url,time_created,uploader_id_id) values
(100, 'ROMANCE', 'media 1', 'https://cloudinary.com/media1','2024-06-04T15:03:03.7992009700',200),
(101, 'ACTION', 'media 2', 'https://cloudinary.com/media2','2024-06-04T15:03:03.7992009700',201),
(102, 'ROMANCE', 'media 3', 'https://cloudinary.com/media3','2024-06-04T15:03:03.7992009700',200),
(103, 'SCI_FI', 'media  4', 'https://cloudinary.com/media4','2024-06-04T15:03:03.7992009700',201);