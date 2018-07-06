#!/bin/bash
echo "HELLO - Lets initialy configure the reLocation Coordinator (loco) DB"
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
\set VERBOSITY verbose
\echo 'Creating users...';
CREATE USER xrobotx WITH PASSWORD 'xrobotx' LOGIN;
CREATE USER xgrzsza WITH PASSWORD 'xgrzsza' LOGIN;
CREATE USER xluksza WITH PASSWORD 'xluksza' LOGIN;

--
-- DATABASE
--

\echo 'Creating DB...'
CREATE DATABASE loco
WITH
OWNER = xgrzsza
ENCODING = 'UTF8'
LC_COLLATE = 'en_US.utf8'
LC_CTYPE = 'en_US.utf8'
TABLESPACE = pg_default
CONNECTION LIMIT = -1;

GRANT ALL PRIVILEGES ON DATABASE loco TO xrobotx;
GRANT ALL PRIVILEGES ON DATABASE loco TO xgrzsza;
GRANT ALL PRIVILEGES ON DATABASE loco TO xluksza;

\connect loco


--
-- TABLE BUILDS
--

\echo 'Creating table users...'
CREATE TABLE public.users
(
    user_id bigint,
    timestamp_created_ms bigint,
    timestamp_changed_ms bigint,
    name character varying(255) COLLATE pg_catalog."default",
    surname character varying(32) COLLATE pg_catalog."default",
    nickname character varying(32) COLLATE pg_catalog."default"
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.users
    OWNER to xrobotx;

GRANT ALL ON TABLE public.users TO xrobotx;
GRANT ALL ON TABLE public.users TO xgrzsza;
GRANT ALL ON TABLE public.users TO xluksza;

-- Index: users_idx

-- DROP INDEX public.users_idx;

CREATE UNIQUE INDEX users_idx
    ON public.users USING btree
    (user_id)
    TABLESPACE pg_default;

-- Index: created_idx

-- DROP INDEX public.created_idx;

CREATE INDEX created_idx
    ON public.users USING btree
    (timestamp_created_ms)
    TABLESPACE pg_default;

\echo 'PSQL done...'
EOSQL
echo 'Initializing DB done'
