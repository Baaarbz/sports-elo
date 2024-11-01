CREATE TABLE race_results
(
    id                    VARCHAR(255) PRIMARY KEY,
    race_id               VARCHAR(255) REFERENCES races (id),
    driver_id             VARCHAR(255) REFERENCES drivers (id),
    number                VARCHAR(5)               NOT NULL,
    position              INT                      NOT NULL,
    points                FLOAT                    NOT NULL,
    constructor_id        VARCHAR(255) REFERENCES constructors (id),
    grid                  INT                      NOT NULL,
    laps                  INT                      NOT NULL,
    status                VARCHAR(255)             NOT NULL,
    time_in_millis        BIGINT,
    fastest_lap_in_millis BIGINT,
    average_speed         FLOAT,
    average_speed_unit    VARCHAR(3),
    updated_at            TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),

    FOREIGN KEY (race_id) REFERENCES races (id),
    FOREIGN KEY (driver_id) REFERENCES drivers (id),
    FOREIGN KEY (constructor_id) REFERENCES constructors (id)
);