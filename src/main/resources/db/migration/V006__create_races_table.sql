CREATE TABLE races
(
    id          VARCHAR(255) PRIMARY KEY,
    round       INT                      NOT NULL,
    info_url    VARCHAR(255)             NOT NULL,
    name        VARCHAR(255)             NOT NULL,
    circuit_id  VARCHAR(255) REFERENCES circuits (id),
    season_id   VARCHAR(255)             NOT NULL,
    occurred_on DATE                     NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    FOREIGN KEY (season_id) REFERENCES seasons (id),
    FOREIGN KEY (circuit_id) REFERENCES circuits (id)
);