CREATE TABLE public.users
(
    id integer NOT NULL DEFAULT nextval('users_id_seq'::regclass),
    firstname character(100) COLLATE pg_catalog."default" NOT NULL,
    lastname character(100) COLLATE pg_catalog."default" NOT NULL,
    email character(200) COLLATE pg_catalog."default" NOT NULL,
    birthday date,
    CONSTRAINT users_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.users
    OWNER to postgres;