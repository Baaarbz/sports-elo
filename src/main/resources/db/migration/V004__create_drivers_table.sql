CREATE TABLE drivers
(
    id                      VARCHAR(255) PRIMARY KEY,
    given_name              VARCHAR(255)             NOT NULL,
    family_name             VARCHAR(255)             NOT NULL,
    code                    VARCHAR(255),
    permanent_number        VARCHAR(255),
    birth_date              DATE                     NOT NULL,
    nationality             VARCHAR(255)             NOT NULL,
    info_url                VARCHAR(255)             NOT NULL,
    current_elo             INT                      NOT NULL,
    current_elo_occurred_on DATE                     NOT NULL,
    highest_elo             INT                      NOT NULL,
    highest_elo_occurred_on DATE                     NOT NULL,
    lowest_elo              INT                      NOT NULL,
    lowest_elo_occurred_on DATE                     NOT NULL,
    updated_at              TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_driver_given_name ON drivers (given_name);
CREATE INDEX idx_driver_family_name ON drivers (family_name);
CREATE INDEX idx_driver_code ON drivers (code);
CREATE INDEX idx_driver_permanent_number ON drivers (permanent_number);
CREATE INDEX idx_driver_nationality ON drivers (nationality);
CREATE INDEX idx_driver_current_elo ON drivers (current_elo);
CREATE INDEX idx_driver_current_elo_occurred_on ON drivers (current_elo_occurred_on);