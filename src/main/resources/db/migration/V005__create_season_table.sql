CREATE TABLE seasons
(
    id         VARCHAR(255) PRIMARY KEY,
    year       INT                      NOT NULL,
    info_url   VARCHAR(255),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_season_year ON seasons (year);