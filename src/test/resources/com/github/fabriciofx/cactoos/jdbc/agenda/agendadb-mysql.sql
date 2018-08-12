CREATE TABLE contact (
  id BINARY(16) NOT NULL,
  name VARCHAR(50) NOT NULL,
  CONSTRAINT pk_contact_id PRIMARY KEY(id)
);

CREATE TABLE phone (
  seq INT NOT NULL AUTO_INCREMENT,
  contact BINARY(16) NOT NULL,
  number VARCHAR(10) NOT NULL,
  operator VARCHAR(10) NOT NULL,
  CONSTRAINT fk_phone_contact FOREIGN KEY(contact) REFERENCES contact(id),
  CONSTRAINT pk_phone_contact_seq PRIMARY KEY(seq, contact)
);

-- MySQL doesn't allow use a function as default value of a field. So, we MUST
-- create a trigger to do it.
DROP TRIGGER IF EXISTS tr_contact_id;

DELIMITER \\
CREATE TRIGGER `tg_contact_id`
BEFORE INSERT ON `contact` FOR EACH ROW
BEGIN
  IF new.id IS NULL THEN
    SET new.id = UNHEX(REPLACE(UUID(), "-",""));
  END IF;
END\\
DELIMITER ;

INSERT INTO contact(name) VALUES ('Joseph Klimber');
INSERT INTO contact(name) VALUES ('Maria Souza');
INSERT INTO contact(name) VALUES ('Jeff Duham');