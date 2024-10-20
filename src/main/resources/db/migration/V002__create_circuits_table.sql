CREATE TABLE circuits
(
    id         VARCHAR(255) PRIMARY KEY,
    name       VARCHAR(255)             NOT NULL,
    latitude   VARCHAR(255)             NOT NULL,
    longitude  VARCHAR(255)             NOT NULL,
    country    VARCHAR(255)             NOT NULL,
    locality   VARCHAR(255)             NOT NULL,
    info_url   VARCHAR(255)             NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_circuit_name ON circuits (name);
CREATE INDEX idx_circuit_country ON circuits (country);
