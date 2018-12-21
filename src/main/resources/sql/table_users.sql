CREATE TABLE IF NOT EXISTS public.users
(
    id SERIAL PRIMARY KEY,
    firstname text COLLATE pg_catalog."default" NOT NULL,
    lastname text COLLATE pg_catalog."default" NOT NULL,
    email text COLLATE pg_catalog."default" NOT NULL,
    birthday date
);