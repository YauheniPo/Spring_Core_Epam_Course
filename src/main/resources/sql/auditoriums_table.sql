CREATE TABLE IF NOT EXISTS public.auditoriums
(
    id SERIAL PRIMARY KEY,
    name text COLLATE pg_catalog."default" NOT NULL,
    number_of_seats integer NOT NULL,
    vip_seats smallint[] NOT NULL,
    CONSTRAINT auditoriums_name_key UNIQUE (name)
);