CREATE TABLE theoretical_constructor_performance
(
    id                      VARCHAR(255) PRIMARY KEY,
    constructor_id          VARCHAR(255) NOT NULL,
    theoretical_performance FLOAT          NOT NULL,
    season_id               VARCHAR(255) NOT NULL,
    is_season_analyzed      BOOLEAN      NOT NULL,
    FOREIGN KEY (constructor_id) REFERENCES constructors (id),
    FOREIGN KEY (season_id) REFERENCES seasons (id)
);