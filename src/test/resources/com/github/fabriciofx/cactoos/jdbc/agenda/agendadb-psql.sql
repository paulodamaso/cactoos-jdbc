CREATE TABLE contact (
  id UUID NOT NULL,
  name VARCHAR(50) NOT NULL,
  CONSTRAINT pk_contact PRIMARY KEY(id)
);

CREATE TABLE phone (
  seq SERIAL NOT NULL,
  contact_id UUID NOT NULL,
  number VARCHAR(10) NOT NULL,
  carrier VARCHAR(10) NOT NULL,
  CONSTRAINT fk_phone_contact FOREIGN KEY(contact_id) REFERENCES contact(id),
  CONSTRAINT pk_phone PRIMARY KEY(seq, contact_id)
);

INSERT INTO contact(id, name)
  VALUES ('2D1EBC5B7D2741979CF0E84451C5BBB1', 'Joseph Klimber');
INSERT INTO contact(id, name)
  VALUES ('2A1EBC5B7D2741990CF0E84451C5BBB2', 'Maria Souza');
INSERT INTO contact(id, name)
  VALUES ('3B1EBC5B7D2741990CF0E85551C5BBB3', 'Jeff Duham');
