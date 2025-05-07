DROP TABLE IF EXISTS directory_value;
DROP TABLE IF EXISTS directory;
CREATE TABLE IF NOT EXISTS directory
(
    id SERIAL PRIMARY KEY,
    name VARCHAR,
    parent_id INTEGER REFERENCES directory(id) ON DELETE CASCADE,
    category VARCHAR
);

CREATE TABLE IF NOT EXISTS directory_value
(
    id SERIAL PRIMARY KEY,
    name VARCHAR,
    directory_id INTEGER REFERENCES directory(id) ON DELETE CASCADE
);

