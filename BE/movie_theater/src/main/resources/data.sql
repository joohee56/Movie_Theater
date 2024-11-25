insert into genre(type)
values('ACTION'), ('COMEDY'), ('DRAMA'), ('HORROR'), ('THRILLER'), ('ROMANCE'), ('SF'), ('DOCUMENTARY');

insert into user(login_id, password, name, email)
values('test123', '1234', '홍길동', 'test@test.com'),
      ('admin', '1234', '이주희', 'joohuii96@gmail.com');

insert into movie(title, sub_title, description, release_date, duration_minutes, poster_url, age_rating, director, screening_type, standard_price)
values ('아마존 활명수', 'AMAZON BULLSEYE',  '어서와, 아마존은 처음이지', '2024.10.28', '6720000000000', 'https://movie-theater-files.s3.ap-northeast-2.amazonaws.com/images/upload-10872114635701600232%EC%95%84%EB%A7%88%EC%A1%B4%ED%99%9C%EB%AA%85%EC%88%98_%ED%8F%AC%EC%8A%A4%ED%84%B0.png', 'AGE_12', '김창주', 'TWO_D', 10000),
       ('베놈:라스트 댄스', 'Venom: The Last Dance',  '죽음이 우리를 갈라놓을 때까지', '2024.10.23', '6540000000000', 'https://movie-theater-files.s3.ap-northeast-2.amazonaws.com/images/upload-13851946119050865616%EB%B2%A0%EB%86%88_%ED%8F%AC%EC%8A%A4%ED%84%B0.jpg', 'AGE_15', '켈리 마르셀', 'TWO_D', 11000),
       ('글래디에이터 Ⅱ', 'GladiatorⅡ',  '로마의 영웅이자 최고의 검투사였던 ‘막시무스’가 콜로세움에서 죽음을 맞이한 뒤', '2024.10.23', '8820000000000', 'https://movie-theater-files.s3.ap-northeast-2.amazonaws.com/images/upload-14872800562717044265%EA%B8%80%EB%9E%98%EB%94%94%EC%97%90%EC%9D%B4%ED%84%B0_%ED%8F%AC%EC%8A%A4%ED%84%B0.png', 'AGE_19', '리들리 스콧', 'TWO_D', 12000),
       ('극장판 고래와 나', 'WHALES AND I',  '“고래의 바다, 인간의 땅, 모두의 지구, 우리는 늘 함께였다”', '2024.10.23', '5760000000000', 'https://movie-theater-files.s3.ap-northeast-2.amazonaws.com/images/upload-14391945350701185861%EA%B3%A0%EB%9E%98%EC%99%80%EB%82%98_%ED%8F%AC%EC%8A%A4%ED%84%B0.jpg', 'ALL', '', 'TWO_D', 13000),
       ('오후 네시', '4 PM',  '“예전보다 더 그가 싫어졌다. 죽이고 싶도록!”', '2024.10.23', '6660000000000', 'https://movie-theater-files.s3.ap-northeast-2.amazonaws.com/images/upload-4992739267478120582%EC%98%A4%ED%9B%84%EB%84%A4%EC%8B%9C_%ED%8F%AC%EC%8A%A4%ED%84%B0.jpg', 'AGE_12', '송정우', 'TWO_D', 14000),
       ('청설', 'Hear Me: Our Summer',  '“손으로 설렘을 말하고!”', '2024.11.06', '6660000000000', 'https://movie-theater-files.s3.ap-northeast-2.amazonaws.com/images/upload-17617889614220861774%EC%B2%AD%EC%84%A4_%ED%8F%AC%EC%8A%A4%ED%84%B0.jpg', 'ALL', '데이브 데릭 주니어', 'TWO_D', 12000),
       ('모아나2', 'MOANA 2',  '“<모아나 2>는 선조들로부터 예기치 못한 부름을 받은 ‘모아나’가 부족의 파괴를 막기 위해 전설 속 영웅 ‘마우이’와 새로운 선원들과 함께 숨겨진 고대 섬의 저주를 깨러 떠나는 위험천만한 모험을 담은 스펙터클 오션 어드벤처”', '2024.11.27', '6660000000000', 'https://movie-theater-files.s3.ap-northeast-2.amazonaws.com/images/upload-7493603452636021844%EB%AA%A8%EC%95%84%EB%82%982_%ED%8F%AC%EC%8A%A4%ED%84%B0.jpg', 'ALL', '조선호', 'TWO_D', 12000),
       ('위키드', 'Wicked',  '“자신의 진정한 힘을 미처 발견하지 못한 ''엘파바''(신시아 에리보) 자신의 진정한 본성을 아직 발견하지 못한 ‘글린다''(아리아나 그란데)”', '2024.11.20', '6660000000000', 'https://movie-theater-files.s3.ap-northeast-2.amazonaws.com/images/upload-13267012735281715205%EC%9C%84%ED%82%A4%EB%93%9C_%ED%8F%AC%EC%8A%A4%ED%84%B0.jpg', 'ALL', '존 추', 'TWO_D', 13000);

insert into movie_genre(movie_id, genre_id)
values('1', '2'),
      ('2', '7'),
      ('2', '5'),
      ('2', '1'),
      ('3', '1'),
      ('4', '8'),
      ('5', '3'),
      ('6', '3'),
      ('7', '3'),
      ('8', '3');

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
       ('5', '김홍파'),
       ('6', '홍경'),
       ('7', '아우이 크라발호'),
       ('8', '아리아나 그란데');

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

insert into seat(hall_id, section, seat_row, is_booked)
values('1', 'A', '1', false),
      ('1', 'A', '2', false),
      ('1', 'A', '3', false),
      ('1', 'A', '4', false),
      ('1', 'A', '5', false),
      ('1', 'A', '6', false),
      ('1', 'A', '7', false),
      ('1', 'A', '8', false),
      ('1', 'A', '9', false),
      ('1', 'A', '10', false),
      ('1', 'A', '11', false),
      ('1', 'A', '12', false),
      ('1', 'A', '13', false),
      ('1', 'A', '14', false),
      ('1', 'A', '15', false),
      ('1', 'A', '16', false),
      ('1', 'B', '1', false),
      ('1', 'B', '2', false),
      ('1', 'B', '3', false),
      ('1', 'B', '4', false),
      ('1', 'B', '5', false),
      ('1', 'B', '6', false),
      ('1', 'B', '7', false),
      ('1', 'B', '8', false),
      ('1', 'B', '9', false),
      ('1', 'B', '10', false),
      ('1', 'B', '11', false),
      ('1', 'B', '12', false),
      ('1', 'B', '13', false),
      ('1', 'B', '14', false),
      ('1', 'B', '15', false),
      ('1', 'B', '16', false),
      ('1', 'C', '1', false),
      ('1', 'C', '2', false),
      ('1', 'C', '3', false),
      ('1', 'C', '4', false),
      ('1', 'C', '5', false),
      ('1', 'C', '6', false),
      ('1', 'C', '7', false),
      ('1', 'C', '8', false),
      ('1', 'C', '9', false),
      ('1', 'C', '10', false),
      ('1', 'C', '11', false),
      ('1', 'C', '12', false),
      ('1', 'C', '13', false),
      ('1', 'C', '14', false),
      ('1', 'C', '15', false),
      ('1', 'C', '16', false),
      ('1', 'D', '1', false),
      ('1', 'D', '2', false),
      ('1', 'D', '3', false),
      ('1', 'D', '4', false),
      ('1', 'D', '5', false),
      ('1', 'D', '6', false),
      ('1', 'D', '7', false),
      ('1', 'D', '8', false),
      ('1', 'D', '9', false),
      ('1', 'D', '10', false),
      ('1', 'D', '11', false),
      ('1', 'D', '12', false),
      ('1', 'D', '13', false),
      ('1', 'D', '14', false),
      ('1', 'D', '15', false),
      ('1', 'D', '16', false),
      ('1', 'E', '1', false),
      ('1', 'E', '2', false),
      ('1', 'E', '3', false),
      ('1', 'E', '4', false),
      ('1', 'E', '5', false),
      ('1', 'E', '6', false),
      ('1', 'E', '7', false),
      ('1', 'E', '8', false),
      ('1', 'E', '9', false),
      ('1', 'E', '10', false),
      ('1', 'E', '11', false),
      ('1', 'E', '12', false),
      ('1', 'E', '13', false),
      ('1', 'E', '14', false),
      ('1', 'E', '15', false),
      ('1', 'E', '16', false),
      ('1', 'F', '1', false),
      ('1', 'F', '2', false),
      ('1', 'F', '3', false),
      ('1', 'F', '4', false),
      ('1', 'F', '5', false),
      ('1', 'F', '6', false),
      ('1', 'F', '7', false),
      ('1', 'F', '8', false),
      ('1', 'F', '9', false),
      ('1', 'F', '10', false),
      ('1', 'F', '11', false),
      ('1', 'F', '12', false),
      ('1', 'F', '13', false),
      ('1', 'F', '14', false),
      ('1', 'F', '15', false),
      ('1', 'F', '16', false),
      ('1', 'G', '1', false),
      ('1', 'G', '2', false),
      ('1', 'G', '3', false),
      ('1', 'G', '4', false),
      ('1', 'G', '5', false),
      ('1', 'G', '6', false),
      ('1', 'G', '7', false),
      ('1', 'G', '8', false),
      ('1', 'G', '9', false),
      ('1', 'G', '10', false),
      ('1', 'G', '11', false),
      ('1', 'G', '12', false),
      ('1', 'G', '13', false),
      ('1', 'G', '14', false),
      ('1', 'G', '15', false),
      ('1', 'G', '16', false),
      ('1', 'H', '1', false),
      ('1', 'H', '2', false),
      ('1', 'H', '3', false),
      ('1', 'H', '4', false),
      ('1', 'H', '5', false),
      ('1', 'H', '6', false),
      ('1', 'H', '7', false),
      ('1', 'H', '8', false),
      ('1', 'H', '9', false),
      ('1', 'H', '10', false),
      ('1', 'H', '11', false),
      ('1', 'H', '12', false),
      ('1', 'H', '13', false),
      ('1', 'H', '14', false),
      ('1', 'H', '15', false),
      ('1', 'H', '16', false),
      ('1', 'I', '1', false),
      ('1', 'I', '2', false),
      ('1', 'I', '3', false),
      ('1', 'I', '4', false),
      ('1', 'I', '5', false),
      ('1', 'I', '6', false),
      ('1', 'I', '7', false),
      ('1', 'I', '8', false),
      ('1', 'I', '9', false),
      ('1', 'I', '10', false),
      ('1', 'I', '11', false),
      ('1', 'I', '12', false),
      ('1', 'I', '13', false),
      ('1', 'I', '14', false),
      ('1', 'I', '15', false),
      ('1', 'I', '16', false),
      ('1', 'J', '1', false),
      ('1', 'J', '2', false),
      ('1', 'J', '3', false),
      ('1', 'J', '4', false),
      ('1', 'J', '5', false),
      ('1', 'J', '6', false),
      ('1', 'J', '7', false),
      ('1', 'J', '8', false),
      ('1', 'J', '9', false),
      ('1', 'J', '10', false),
      ('1', 'J', '11', false),
      ('1', 'J', '12', false),
      ('1', 'J', '13', false),
      ('1', 'J', '14', false),
      ('1', 'J', '15', false),
      ('1', 'J', '16', false);

insert into screening(movie_id, hall_id, start_time, end_time, total_price)
values ('1', '3', '2024-11-29T17:00:00', '2024-11-29T19:02:00', 10000),
       ('1', '3', '2024-11-29T19:20:00', '2024-11-29T21:22:00', 10000),
       ('1', '3', '2024-11-29T20:40:00', '2024-11-29T22:42:00', 10000),
       ('1', '3', '2024-11-29T21:40:00', '2024-11-29T23:42:00', 10000),
       ('1', '4', '2024-11-29T09:20:00', '2024-11-29T11:22:00', 10000),
       ('1', '4', '2024-11-29T10:40:00', '2024-11-29T12:42:00', 10000),
       ('2', '1', '2024-11-29T10:40:00', '2024-11-29T12:42:00', 10000),
       ('2', '1', '2024-11-29T13:00:00', '2024-11-29T15:02:00', 10000),
       ('2', '5', '2024-11-30T09:10:00', '2024-11-30T11:09:00', 10000),
       ('2', '5', '2024-11-30T10:20:00', '2024-11-30T12:19:00', 10000),
       ('3', '1', '2024-11-29T15:20:00', '2024-11-29T16:50:00', 10000),
       ('3', '1', '2024-11-29T17:00:00', '2024-11-29T18:50:00', 10000);

insert into payment_history(amount, pay_time, user_id, imp_id, currency, pay_method, pay_status)
values ('10000', '2024-11-01T15:00:00', '1', '1', 'KRW', 'CARD', 'COMPLETED'),
       ('10000', '2024-11-01T16:00:00', '1', '2', 'KRW', 'CARD', 'COMPLETED');

insert into booking(user_id, screening_id, payment_history_id, booking_status, booking_number, created_at, updated_at, booking_time)
values ('1', '7',  '1', 'CONFIRMED', '1234-123-12345', '2024-11-01T15:00:00','2024-11-01T15:00:00', '2024-11-01T15:00:00'),
       ('1', '7',  '2', 'CANCELED', '5678-567-56789', '2024-11-02T15:00:00', '2024-11-02T15:00:00', '2024-11-02T15:00:00');

insert into booking_seat(booking_id, seat_id, created_at, updated_at)
values ('1', '1', '2024-11-01T15:00:00', '2024-11-01T15:00:00'),
       ('1', '2', '2024-11-01T15:00:00', '2024-11-01T15:00:00'),
       ('2', '3', '2024-11-02T15:00:00', '2024-11-02T15:00:00');