-- Tabela de salas
CREATE TABLE tb_rooms
(
    room_id         BINARY(16) PRIMARY KEY,
    room_name       VARCHAR(255) NOT NULL,
    row_quantity    INT          NOT NULL,
    column_quantity INT          NOT NULL
);

-- Tabela de assentos
CREATE TABLE tb_seats
(
    seat_id       BINARY(16) PRIMARY KEY,
    seat_name     VARCHAR(255) NOT NULL,
    seat_row    INT          NOT NULL,
    seat_column INT          NOT NULL,
    room_id       BINARY(16) NOT NULL,

    CONSTRAINT fk_seat_room
        FOREIGN KEY (room_id) REFERENCES tb_rooms (room_id),

    CONSTRAINT uk_seat_position
        UNIQUE (room_id, seat_row, seat_column)
);

-- Tabela de reservas
CREATE TABLE tb_reservations
(
    reservation_id   BINARY(16) PRIMARY KEY,
    status           VARCHAR(50) NOT NULL,
    reservation_date DATETIME    NOT NULL,
    expiration_time  DATETIME    NOT NULL
);

-- Tabela intermediária (SeatReservation)
CREATE TABLE tb_seat_reservations
(
    seat_reservation_id BINARY(16) PRIMARY KEY,
    reservation_id      BINARY(16) NOT NULL,
    seat_id             BINARY(16) NOT NULL,

    CONSTRAINT fk_sr_reservation
        FOREIGN KEY (reservation_id) REFERENCES tb_reservations (reservation_id),

    CONSTRAINT fk_sr_seat
        FOREIGN KEY (seat_id) REFERENCES tb_seats (seat_id),

    CONSTRAINT uk_seat_reservation
        UNIQUE (reservation_id, seat_id)
);