insert into genre(type)
values('ACTION'), ('COMEDY'), ('DRAMA'), ('HORROR'), ('THRILLER'), ('ROMANCE'), ('SF'), ('DOCUMENTARY');

insert into user(login_id, name, email)
values('test123', '홍길동', 'test@test.com');

insert into movie(title, sub_title, description, release_date, duration_minutes, poster_url, age_rating, director, screening_type, standard_price)
values ('아마존 활명수', 'AMAZON BULLSEYE',  '어서와, 아마존은 처음이지', '2024.10.28', '112', 'test@test.com', 'AGE_12', '김창주', 'TWO_D', 10000),
       ('베놈:라스트 댄스', 'Venom: The Last Dance',  '죽음이 우리를 갈라놓을 때까지', '2024.10.23', '109', 'test@test.com', 'AGE_15', '켈리 마르셀', 'TWO_D', 11000),
       ('글래디에이터 Ⅱ', 'GladiatorⅡ',  '로마의 영웅이자 최고의 검투사였던 ‘막시무스’가 콜로세움에서 죽음을 맞이한 뒤', '2024.10.23', '147', 'test@test.com', 'AGE_19', '리들리 스콧', 'TWO_D', 12000),
       ('극장판 고래와 나', 'WHALES AND I',  '“고래의 바다, 인간의 땅, 모두의 지구, 우리는 늘 함께였다”', '2024.10.23', '96', 'test@test.com', 'ALL', '', 'TWO_D', 13000),
       ('오후 네시', '4 PM',  '“예전보다 더 그가 싫어졌다. 죽이고 싶도록!”', '2024.10.23', '111', 'test@test.com', 'AGE_12', '송정우', 'TWO_D', 14000);

insert into movie_genre(movie_id, genre_id)
values('1', '2'),
      ('2', '7'),
      ('2', '5'),
      ('2', '1'),
      ('3', '1'),
      ('4', '8'),
      ('5', '3');

insert into movie_actor(movie_id, name)
values ('1', '류승룡'),
       ('1', '진선규'),
       ('1', '이고르 페드로소'),
       ('2', '톰 하디'),
       ('2', '치웨텔 에지오포'),
       ('2', '주노 템플'),
       ('3', '폴 메스칼'),
       ('3', '패드로 파스칼'),
       ('5', '오달수'),
       ('5', '장영남'),
       ('5', '김홍파');

insert into theater(name, address, region)
values ('강남', '', 'SEOUL'),
       ('강동', '', 'SEOUL'),
       ('군자', '', 'SEOUL'),
       ('더 부티크 목동현대백화점', '', 'SEOUL'),
       ('고양스타필드', '', 'GYEONGGI'),
       ('광명ak플라자', '', 'GYEONGGI'),
       ('광명소하', '', 'GYEONGGI'),
       ('금정ak플라자', '', 'GYEONGGI'),
       ('김포한강신도시', '', 'GYEONGGI'),
       ('제주삼화', '', 'JEJU');

insert into hall(theater_id, name, screening_type, hall_type_modifier)
    values ('1', '1관', 'IMAX', 3000),
           ('1', '2관', 'THREE_D', 2000),
           ('1', '3관', 'TWO_D', 0),
           ('2', '1관', 'TWO_D', 0),
           ('5', '5관 [Laser]', 'TWO_D', 0);

insert into seat(hall_id, seat_number)
values('1', 'A1'),
      ('1', 'A2'),
      ('1', 'B1'),
      ('1', 'B2');

insert into screening(movie_id, hall_id, start_time, end_time, total_price)
values ('1', '3', '2024-11-15T17:00:00', '2024-11-15T19:02:00', 10000),
       ('1', '3', '2024-11-15T19:20:00', '2024-11-15T21:22:00', 10000),
       ('1', '3', '2024-11-15T20:40:00', '2024-11-15T22:42:00', 10000),
       ('1', '3', '2024-11-15T21:40:00', '2024-11-15T23:42:00', 10000),
       ('1', '4', '2024-11-15T09:20:00', '2024-11-15T11:22:00', 10000),
       ('1', '4', '2024-11-15T10:40:00', '2024-11-15T12:42:00', 10000),
       ('2', '1', '2024-11-15T10:40:00', '2024-11-15T12:42:00', 10000),
       ('2', '1', '2024-11-15T13:00:00', '2024-11-15T15:02:00', 10000),
       ('2', '5', '2024-11-16T09:10:00', '2024-11-16T11:09:00', 10000),
       ('2', '5', '2024-11-16T10:20:00', '2024-11-16T12:19:00', 10000),
       ('3', '1', '2024-11-15T15:20:00', '2024-11-15T16:50:00', 10000),
       ('3', '1', '2024-11-15T17:00:00', '2024-11-15T18:50:00', 10000);

