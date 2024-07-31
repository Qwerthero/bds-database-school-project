CREATE TABLE IF NOT EXISTS login (
  login_id BIGSERIAL NOT NULL UNIQUE,
  email VARCHAR(45) NOT NULL UNIQUE,
  password VARCHAR(45) NOT NULL,
  created TIMESTAMP NOT NULL,
  nickname VARCHAR(45) NULL,
  PRIMARY KEY (login_id));

CREATE TABLE IF NOT EXISTS passenger (
  passenger_id BIGSERIAL NOT NULL UNIQUE,
  passenger_name VARCHAR(60) NOT NULL,
  passenger_surname VARCHAR(60) NOT NULL,
  title VARCHAR(30) NULL,
  passport_id BIGSERIAL NOT NULL,
  login_id BIGSERIAL NOT NULL,
  PRIMARY KEY (passenger_id, login_id),
  CONSTRAINT fk_passanger_login1
    FOREIGN KEY (login_id)
    REFERENCES login (login_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS passport (
  passport_id BIGSERIAL NOT NULL UNIQUE,
  number_of_passport BIGINT NOT NULL UNIQUE,
  issue_date DATE NOT NULL,
  expiration_date DATE NOT NULL,
  date_of_birth DATE NOT NULL,
  place_of_birth VARCHAR(45) NOT NULL,
  sex VARCHAR(10) NOT NULL,
  nationality VARCHAR(45) NOT NULL,
  passenger_id BIGSERIAL NOT NULL,
  PRIMARY KEY (passport_id, passenger_id),
  CONSTRAINT fk_passport_passenger1
    FOREIGN KEY (passenger_id)
    REFERENCES passenger (passenger_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


CREATE TABLE IF NOT EXISTS airline (
  airline_id BIGSERIAL NOT NULL UNIQUE,
  airline_name VARCHAR(45) NOT NULL,
  airline_code INT NOT NULL,
  PRIMARY KEY (airline_id));

CREATE TABLE IF NOT EXISTS airplane (
  airplane_id BIGSERIAL NOT NULL UNIQUE,
  model_number INT NOT NULL,
  name VARCHAR(45) NOT NULL,
  capacity INT NOT NULL,
  number_of_crew INT NOT NULL,
  checked BOOLEAN NOT NULL,
  airline_id BIGSERIAL NOT NULL,
  PRIMARY KEY (airplane_id, airline_id),
  CONSTRAINT fk_airplane_airline1
    FOREIGN KEY (airline_id)
    REFERENCES airline (airline_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS flight_places (
  flight_places_id BIGSERIAL NOT NULL,
  departure_place VARCHAR(45) NOT NULL,
  destination_place VARCHAR(45) NOT NULL,
  count_of_layover INT NULL,
  PRIMARY KEY (flight_places_id));

CREATE TABLE IF NOT EXISTS flight (
  flight_id BIGSERIAL NOT NULL UNIQUE,
  departure_time TIME NOT NULL,
  arrival_time TIME NOT NULL,
  departure_date DATE NOT NULL,
  arrival_date DATE NOT NULL,
  airplane_id BIGSERIAL NOT NULL,
  flight_places_id BIGSERIAL NOT NULL,
  PRIMARY KEY (flight_id, airplane_id, flight_places_id),
  CONSTRAINT fk_flight_airplane1
    FOREIGN KEY (airplane_id)
    REFERENCES airplane (airplane_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_flight_flight_places1
    FOREIGN KEY (flight_places_id)
    REFERENCES flight_places (flight_places_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS ticket (
  ticket_id BIGSERIAL NOT NULL UNIQUE,
  prize DECIMAL NOT NULL,
  class VARCHAR(45) NOT NULL,
  seat SERIAL NOT NULL,
  food_on_board BOOLEAN NULL,
  place_for_legs BOOLEAN NULL,
  passenger_id BIGSERIAL NOT NULL,
  flight_places_id BIGSERIAL NOT NULL,
  PRIMARY KEY (ticket_id, passenger_id, flight_places_id),
  CONSTRAINT fk_ticket_passanger1
    FOREIGN KEY (passenger_id)
    REFERENCES passenger (passenger_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_ticket_flight_places1
    FOREIGN KEY (flight_places_id)
    REFERENCES flight_places (flight_places_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS boarding_pass (
  boarding_pass_id BIGSERIAL NOT NULL UNIQUE,
  gate INT NOT NULL,
  chacked_in BOOLEAN NOT NULL,
  boarded BOOLEAN NOT NULL,
  flight_id BIGSERIAL NOT NULL,
  ticket_id BIGSERIAL NOT NULL,
  PRIMARY KEY (boarding_pass_id, flight_id, ticket_id),
  CONSTRAINT fk_boarding_pass_flight1
    FOREIGN KEY (flight_id)
    REFERENCES flight (flight_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_boarding_pass_ticket1
    FOREIGN KEY (ticket_id)
    REFERENCES ticket (ticket_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS flight_crew (
  flight_crew_id BIGSERIAL NOT NULL UNIQUE,
  flight_crew_first_name VARCHAR(45) NOT NULL,
  flight_crew_surname VARCHAR(45) NOT NULL,
  phone VARCHAR(45) NULL,
  flight_id BIGSERIAL NOT NULL,
  PRIMARY KEY (flight_crew_id, flight_id),
  CONSTRAINT fk_flight_crew_flight1
    FOREIGN KEY (flight_id)
    REFERENCES flight (flight_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS assignment (
  assignment_id BIGSERIAL NOT NULL UNIQUE,
  type VARCHAR(70) NOT NULL,
  PRIMARY KEY (assignment_id));


CREATE TABLE IF NOT EXISTS assignment_has_flight_crew (
  flight_crew_id BIGSERIAL NOT NULL UNIQUE,
  assignment_id BIGSERIAL NOT NULL,
  assigment_start TIMESTAMP NOT NULL,
  assigment_end TIMESTAMP NOT NULL,
  PRIMARY KEY (flight_crew_id, assignment_id),
  CONSTRAINT fk_flight_crew_has_role_flight_crew1
    FOREIGN KEY (flight_crew_id)
    REFERENCES flight_crew (flight_crew_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_flight_crew_has_role_role1
    FOREIGN KEY (assignment_id)
    REFERENCES assignment (assignment_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS baggage (
  baggage_id BIGSERIAL NOT NULL UNIQUE,
  weight_kg DECIMAL NOT NULL,
  ticket_id BIGSERIAL NOT NULL,
  PRIMARY KEY (baggage_id, ticket_id),
  CONSTRAINT fk_baggage_ticket1
    FOREIGN KEY (ticket_id)
    REFERENCES ticket (ticket_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


