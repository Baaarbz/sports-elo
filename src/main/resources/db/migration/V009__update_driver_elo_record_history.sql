DROP TABLE drivers_elo_history;

CREATE TABLE drivers_elo_history
(
    id VARCHAR(255) PRIMARY KEY,
    driver_id   VARCHAR(255) REFERENCES drivers (id),
    elo         INT                      NOT NULL,
    occurred_on DATE                     NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_driver_id ON drivers_elo_history (driver_id);