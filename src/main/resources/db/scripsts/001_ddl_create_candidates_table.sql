CREATE TABLE candidate (
   id SERIAL PRIMARY KEY,
   name TEXT,
   visible boolean,
   description TEXT,
   city_id int,
   photo bytea,
   created TIMESTAMP
);