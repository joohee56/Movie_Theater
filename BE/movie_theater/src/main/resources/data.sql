insert into genre(type)
values('ACTION'), ('COMEDY'), ('DRAMA'), ('HORROR'), ('THRILLER'), ('ROMANCE'), ('SF'), ('DOCUMENTARY');

insert into user(login_id, name, email)
values('test123', '홍길동', 'test@test.com');

insert into movie(title, sub_title, description, release_date, duration_minutes, poster_url, age_rating, director, screening_type)
values ('아마존 활명수', 'AMAZON BULLSEYE',  '어서와, 아마존은 처음이지', '2024.10.28', '112', 'test@test.com', 'AGE_12', '김창주', 'TWO_D'),
       ('베놈:라스트 댄스', 'Venom: The Last Dance',  '죽음이 우리를 갈라놓을 때까지', '2024.10.23', '109', 'test@test.com', 'AGE_15', '켈리 마르셀', 'TWO_D'),
       ('글래디에이터 Ⅱ', 'GladiatorⅡ',  '로마의 영웅이자 최고의 검투사였던 ‘막시무스’가 콜로세움에서 죽음을 맞이한 뒤', '2024.10.23', '147', 'test@test.com', 'AGE_19', '리들리 스콧', 'TWO_D'),
       ('극장판 고래와 나', 'WHALES AND I',  '“고래의 바다, 인간의 땅, 모두의 지구, 우리는 늘 함께였다”', '2024.10.23', '96', 'test@test.com', 'ALL', '', 'TWO_D'),
       ('오후 네시', '4 PM',  '“예전보다 더 그가 싫어졌다. 죽이고 싶도록!”', '2024.10.23', '111', 'test@test.com', 'AGE_12', '송정우', 'TWO_D');

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
       ('광명소하', '', 'GYEONGGI');