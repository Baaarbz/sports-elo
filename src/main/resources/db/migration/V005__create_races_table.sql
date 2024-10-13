CREATE TABLE races
(
    id          VARCHAR(255) PRIMARY KEY,
    season      INT                      NOT NULL,
    round       INT                      NOT NULL,
    info_url    VARCHAR(255)             NOT NULL,
    name        VARCHAR(255)             NOT NULL,
    circuit_id  VARCHAR(255) REFERENCES circuits (id),
    occurred_on DATE                     NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_circuit_id ON races (circuit_id);