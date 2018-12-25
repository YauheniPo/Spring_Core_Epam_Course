CREATE TABLE IF NOT EXISTS public.tickets
(
    id SERIAL PRIMARY KEY,
    user_id integer NOT NULL,
    event_id integer NOT NULL,
    datetime timestamp without time zone NOT NULL,
    seat smallint NOT NULL
);