-- Tabela de salas
CREATE TABLE tb_rooms
(
    room_id         BINARY(16) PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    rows_quantity    INT          NOT NULL,
    columns_quantity INT          NOT NULL
);

-- Tabela de filmes
CREATE TABLE tb_movies
(
    movie_id     BINARY(16)   PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    duration    INT          NOT NULL,
    movie_genre VARCHAR(50)  NOT NULL,
    age_rating  VARCHAR(10)  NOT NULL
);

-- Tabela de sessões
CREATE TABLE tb_sessions
(
    session_id     BINARY(16) PRIMARY KEY,
    movie_id       BINARY(16) NOT NULL,
    room_id        BINARY(16) NOT NULL,
    start_time     DATETIME    NOT NULL,
    end_time       DATETIME    NOT NULL,
    session_type   VARCHAR(10) NOT NULL,
    session_format VARCHAR(10) NOT NULL,

    CONSTRAINT fk_session_movie
        FOREIGN KEY (movie_id) REFERENCES tb_movies (movie_id),

    CONSTRAINT fk_session_room
        FOREIGN KEY (room_id) REFERENCES tb_rooms (room_id)
);

-- Tabela de assentos
CREATE TABLE tb_seats
(
    seat_id     BINARY(16) PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    seat_row    INT          NOT NULL,
    seat_column INT          NOT NULL,
    room_id     BINARY(16) NOT NULL,

    CONSTRAINT fk_seat_room
        FOREIGN KEY (room_id) REFERENCES tb_rooms (room_id),

    CONSTRAINT uk_seat_position
        UNIQUE (room_id, seat_row, seat_column)
);

-- Tabela de reservas
CREATE TABLE tb_reservations
(
    reservation_id   BINARY(16) PRIMARY KEY,
    session_id       BINARY(16) NOT NULL,
    status           VARCHAR(50) NOT NULL,
    reservation_date DATETIME    NOT NULL,
    expiration_time  DATETIME    NOT NULL,

    CONSTRAINT fk_reservation_session
        FOREIGN KEY (session_id) REFERENCES tb_sessions (session_id)

);

-- Tabela intermediária (SeatReservation)
CREATE TABLE tb_seat_reservations
(
    seat_reservation_id BINARY(16) PRIMARY KEY,
    reservation_id      BINARY(16) NOT NULL,
    seat_id             BINARY(16) NOT NULL,
    session_id          BINARY(16) NOT NULL,
    status              VARCHAR(50) NOT NULL,

    CONSTRAINT fk_sr_reservation
        FOREIGN KEY (reservation_id) REFERENCES tb_reservations (reservation_id),

    CONSTRAINT fk_sr_seat
        FOREIGN KEY (seat_id) REFERENCES tb_seats (seat_id),

    CONSTRAINT fk_sr_session
        FOREIGN KEY (session_id) REFERENCES tb_sessions (session_id),

    CONSTRAINT uk_seat_reservation
        UNIQUE (reservation_id, seat_id, session_id)
);