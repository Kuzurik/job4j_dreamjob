CREATE TABLE post (
   id SERIAL PRIMARY KEY,
   name TEXT,
   visible boolean,
   description TEXT,
   city int,
   created TIMESTAMP
);