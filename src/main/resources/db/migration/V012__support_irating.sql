CREATE TABLE drivers_irating_history
(
    driver_id   VARCHAR(255) REFERENCES drivers (id),
    irating     INT                      NOT NULL,
    occurred_on DATE                     NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    PRIMARY KEY (driver_id, irating, occurred_on)
);

CREATE INDEX idx_irating_history_driver_id ON drivers_irating_history (driver_id);
CREATE INDEX idx_elo_history_driver_id ON drivers_elo_history (driver_id);
DROP INDEX idx_driver_id;

ALTER TABLE drivers
    ADD COLUMN current_irating INT NOT NULL DEFAULT 1000;
ALTER TABLE drivers
    ADD COLUMN current_irating_occurred_on DATE;

UPDATE drivers d
SET current_irating_occurred_on = (
    SELECT MIN(deh.occurred_on)
    FROM drivers_elo_history deh
    WHERE deh.driver_id = d.id
);

ALTER TABLE drivers
    ALTER COLUMN current_irating_occurred_on SET NOT NULL;

CREATE INDEX idx_driver_current_irating ON drivers (current_irating);
CREATE INDEX idx_driver_current_irating_occurred_on ON drivers (current_irating_occurred_on);