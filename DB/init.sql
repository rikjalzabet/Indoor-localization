-- Table: floorMaps
CREATE TABLE floorMaps (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    image TEXT
);
-- Table: assets
CREATE TABLE assets (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    x DECIMAL(10, 6),
    y DECIMAL(10, 6),
    lastSync TIMESTAMP,
    floorMapId INT NOT NULL,
    active BOOLEAN,
    FOREIGN KEY (floorMapId) REFERENCES floorMaps(id)
);
-- Table: zones
CREATE TABLE zones (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    points JSON
);
-- Table: assetPositionHistory
CREATE TABLE assetPositionHistory (
    id SERIAL PRIMARY KEY,
    assetId INT NOT NULL,
    x DECIMAL(10, 6),
    y DECIMAL(10, 6),
    dateTime TIMESTAMP,
    floorMapId INT NOT NULL,
    FOREIGN KEY (assetId) REFERENCES assets(id),
    FOREIGN KEY (floorMapId) REFERENCES floorMaps(id)
);
-- Table: assetZoneHistory
CREATE TABLE assetZoneHistory (
    id SERIAL PRIMARY KEY,
    assetId INT NOT NULL,
    zoneId INT NOT NULL,
    enterDateTime TIMESTAMP,
    exitDateTime TIMESTAMP,
    retentionTime INTERVAL,
    FOREIGN KEY (assetId) REFERENCES assets(id),
    FOREIGN KEY (zoneId) REFERENCES zones(id)
);
