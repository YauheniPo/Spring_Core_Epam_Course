CREATE TABLE IF NOT EXISTS public.events
(
    id SERIAL PRIMARY KEY,
	name text COLLATE pg_catalog."default" NOT NULL,
    base_price real,
    rating text COLLATE pg_catalog."default",
    CONSTRAINT events_name_key UNIQUE (name)
);