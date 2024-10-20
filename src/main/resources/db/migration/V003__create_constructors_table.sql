CREATE TABLE constructors
(
    id          VARCHAR(255) PRIMARY KEY,
    name        VARCHAR(255)             NOT NULL,
    nationality VARCHAR(255)             NOT NULL,
    info_url    VARCHAR(255)             NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_constructor_nationality ON constructors (nationality);
CREATE INDEX idx_constructor_name ON constructors (name);