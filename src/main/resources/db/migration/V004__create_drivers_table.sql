CREATE TABLE drivers
(
    id                      VARCHAR(255) PRIMARY KEY,
    full_name               VARCHAR(255)             NOT NULL,
    code                    VARCHAR(255),
    permanent_number        VARCHAR(255),
    birth_date              DATE                     NOT NULL,
    nationality             VARCHAR(255)             NOT NULL,
    info_url                VARCHAR(255)             NOT NULL,
    current_elo             INT                      NOT NULL,
    current_elo_occurred_on DATE                     NOT NULL,
    updated_at              TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);