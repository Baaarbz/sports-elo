CREATE TABLE race_results
(
    id                    VARCHAR(255) PRIMARY KEY,
    race_id               VARCHAR(255) REFERENCES races (id),
    driver_id             VARCHAR(255) REFERENCES drivers (id),
    position              INT                      NOT NULL,
    points                FLOAT                    NOT NULL,
    constructor_id        VARCHAR(255) REFERENCES constructors (id),
    grid                  INT                      NOT NULL,
    laps                  INT                      NOT NULL,
    status                VARCHAR(255)             NOT NULL,
    time_in_millis        BIGINT                   NOT NULL,
    fastest_lap_in_millis BIGINT,
    average_speed         FLOAT,
    average_speed_unit    VARCHAR(3),
    updated_at            TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_race_id ON race_results (race_id);
CREATE INDEX idx_driver_id ON race_results (driver_id);
CREATE INDEX idx_constructor_id ON race_results (constructor_id);