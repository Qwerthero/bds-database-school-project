CREATE TABLE IF NOT EXISTS login (
  login_id INT(8) NOT NULL AUTO_INCREMENT,
  email VARCHAR(45) NOT NULL,
  password VARCHAR(45) NOT NULL,
  created TIMESTAMP NOT NULL,
  nickname VARCHAR(45) NULL,
  PRIMARY KEY (login_id),
  UNIQUE INDEX login_id_UNIQUE (login_id ASC),
  UNIQUE INDEX username_UNIQUE (email ASC))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS passenger (
  passenger_id INT(8) UNSIGNED NOT NULL AUTO_INCREMENT,
  passenger_name VARCHAR(60) NOT NULL,
  passenger_surname VARCHAR(60) NOT NULL,
  title VARCHAR(30) NULL,
  login_id INT(8) NOT NULL,
  PRIMARY KEY (passenger_id, login_id),
  INDEX fk_passanger_login1_idx (login_id ASC),
  CONSTRAINT fk_passanger_login1
    FOREIGN KEY (login_id)
    REFERENCES login (login_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS airline (
  airline_id INT(8) UNSIGNED NOT NULL AUTO_INCREMENT,
  airline_name VARCHAR(45) NOT NULL,
  airline_code INT(45) NOT NULL,
  PRIMARY KEY (airline_id),
  UNIQUE INDEX airline_id_UNIQUE (airline_id ASC))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS airplane (
  airplane_id INT(8) UNSIGNED NOT NULL AUTO_INCREMENT,
  model_number INT(45) NOT NULL,
  name VARCHAR(45) NOT NULL,
  capacity INT(45) NOT NULL,
  number_of_crew INT(45) NOT NULL,
  checked TINYINT NOT NULL,
  airline_id INT(8) UNSIGNED NOT NULL,
  PRIMARY KEY (airplane_id, airline_id),
  INDEX fk_airplane_airline1_idx (airline_id ASC),
  UNIQUE INDEX airplane_id_UNIQUE (airplane_id ASC),
  CONSTRAINT fk_airplane_airline1
    FOREIGN KEY (airline_id)
    REFERENCES airline (airline_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS flight_places (
  flight_places_id INT(8) UNSIGNED NOT NULL AUTO_INCREMENT,
  departure_place VARCHAR(45) NOT NULL,
  destination_place VARCHAR(45) NOT NULL,
  count_of_layover INT(8) NULL,
  PRIMARY KEY (flight_places_id),
  UNIQUE INDEX flight_places_id_UNIQUE (flight_places_id ASC))
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS flight (
  flight_id INT(8) UNSIGNED NOT NULL AUTO_INCREMENT,
  departure_time TIME NOT NULL,
  arrival_time TIME NOT NULL,
  departure_date DATE NOT NULL,
  arrival_date DATE NOT NULL,
  airplane_id INT(8) UNSIGNED NOT NULL,
  flight_places_id INT(8) UNSIGNED NOT NULL,
  PRIMARY KEY (flight_id, airplane_id, flight_places_id),
  UNIQUE INDEX flight_id_UNIQUE (flight_id ASC),
  INDEX fk_flight_airplane1_idx (airplane_id ASC),
  INDEX fk_flight_flight_places1_idx (flight_places_id ASC),
  CONSTRAINT fk_flight_airplane1
    FOREIGN KEY (airplane_id)
    REFERENCES airplane (airplane_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_flight_flight_places1
    FOREIGN KEY (flight_places_id)
    REFERENCES flight_places (flight_places_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS ticket (
  ticket_id INT(8) UNSIGNED NOT NULL AUTO_INCREMENT,
  prize DECIMAL UNSIGNED NOT NULL,
  class VARCHAR(45) NOT NULL,
  seat INT(8) UNSIGNED NOT NULL,
  food_on_board TINYINT NULL,
  place_for_legs TINYINT NULL,
  passenger_id INT(8) UNSIGNED NOT NULL,
  flight_places_id INT(8) UNSIGNED NOT NULL,
  PRIMARY KEY (ticket_id, passenger_id, flight_places_id),
  UNIQUE INDEX ticket_id_UNIQUE (ticket_id ASC),
  INDEX fk_ticket_passanger1_idx (passenger_id ASC),
  INDEX fk_ticket_flight_places1_idx (flight_places_id ASC),
  CONSTRAINT fk_ticket_passanger1
    FOREIGN KEY (passenger_id)
    REFERENCES passenger (passenger_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_ticket_flight_places1
    FOREIGN KEY (flight_places_id)
    REFERENCES flight_places (flight_places_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS boarding_pass (
  boarding_pass_id INT(8) UNSIGNED NOT NULL AUTO_INCREMENT,
  gate INT(8) UNSIGNED NOT NULL,
  chacked_in TINYINT NOT NULL,
  boarded TINYINT NOT NULL,
  flight_id INT(8) UNSIGNED NOT NULL,
  ticket_id INT(8) UNSIGNED NOT NULL,
  PRIMARY KEY (boarding_pass_id, flight_id, ticket_id),
  UNIQUE INDEX boarding_pass_id_UNIQUE (boarding_pass_id ASC),
  INDEX fk_boarding_pass_flight1_idx (flight_id ASC),
  INDEX fk_boarding_pass_ticket1_idx (ticket_id ASC),
  CONSTRAINT fk_boarding_pass_flight1
    FOREIGN KEY (flight_id)
    REFERENCES flight (flight_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_boarding_pass_ticket1
    FOREIGN KEY (ticket_id)
    REFERENCES ticket (ticket_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS passport (
  passport_id INT(8) UNSIGNED NOT NULL AUTO_INCREMENT,
  number_of_passport BIGINT NOT NULL,
  issue_date DATE NOT NULL,
  expiration_date DATE NOT NULL,
  date_of_birth DATE NOT NULL,
  place_of_birth VARCHAR(45) NOT NULL,
  sex VARCHAR(10) NOT NULL,
  nationality VARCHAR(45) NOT NULL,
  passenger_id INT(8) UNSIGNED NOT NULL,
  PRIMARY KEY (passport_id, passenger_id),
  UNIQUE INDEX passport_id_UNIQUE (passport_id ASC),
  UNIQUE INDEX number_of_passport_UNIQUE (number_of_passport ASC),
  INDEX fk_passport_passenger1_idx (passenger_id ASC),
  CONSTRAINT fk_passport_passenger1
    FOREIGN KEY (passenger_id)
    REFERENCES passenger (passenger_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS flight_crew (
  flight_crew_id INT(8) UNSIGNED NOT NULL AUTO_INCREMENT,
  flight_crew_first_name VARCHAR(45) NOT NULL,
  flight_crew_surname VARCHAR(45) NOT NULL,
  phone VARCHAR(45) NULL,
  flight_id INT(8) UNSIGNED NOT NULL,
  PRIMARY KEY (flight_crew_id, flight_id),
  UNIQUE INDEX pilot_id_UNIQUE (flight_crew_id ASC),
  INDEX fk_flight_crew_flight1_idx (flight_id ASC),
  UNIQUE INDEX phone_UNIQUE (phone ASC),
  CONSTRAINT fk_flight_crew_flight1
    FOREIGN KEY (flight_id)
    REFERENCES flight (flight_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS assignment (
  assignment_id INT(8) UNSIGNED NOT NULL AUTO_INCREMENT,
  type VARCHAR(70) NOT NULL,
  UNIQUE INDEX role_id_UNIQUE (assignment_id ASC),
  PRIMARY KEY (assignment_id))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS assignment_has_flight_crew (
  flight_crew_id INT(8) UNSIGNED NOT NULL,
  assignment_id INT(8) UNSIGNED NOT NULL,
  assignment_start TIMESTAMP NOT NULL,
  assignment_end TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (flight_crew_id, assignment_id),
  INDEX fk_flight_crew_has_role_role1_idx (assignment_id ASC),
  INDEX fk_flight_crew_has_role_flight_crew1_idx (flight_crew_id ASC),
  CONSTRAINT fk_flight_crew_has_role_flight_crew1
    FOREIGN KEY (flight_crew_id)
    REFERENCES flight_crew (flight_crew_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_flight_crew_has_role_role1
    FOREIGN KEY (assignment_id)
    REFERENCES assignment (assignment_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS baggage (
  baggage_id INT(8) UNSIGNED NOT NULL AUTO_INCREMENT,
  weight_kg DECIMAL UNSIGNED NOT NULL,
  ticket_id INT(8) UNSIGNED NOT NULL,
  PRIMARY KEY (baggage_id, ticket_id),
  INDEX fk_baggage_ticket1_idx (ticket_id ASC),
  UNIQUE INDEX baggage_id_UNIQUE (baggage_id ASC),
  CONSTRAINT fk_baggage_ticket1
    FOREIGN KEY (ticket_id)
    REFERENCES ticket (ticket_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

