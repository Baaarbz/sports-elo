CREATE TABLE drivers_elo_history
(
    driver_id   VARCHAR(255) REFERENCES drivers (id),
    elo         INT                      NOT NULL,
    occurred_on DATE                     NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    PRIMARY KEY (driver_id, elo, occurred_on)
);

CREATE INDEX idx_driver_id ON drivers_elo_history (driver_id);