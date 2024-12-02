-- Table: floorMaps
CREATE TABLE floorMaps (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    image TEXT
);
-- Generate some floorMaps records
INSERT INTO floorMaps (id, name, image) VALUES
(1, 'FloorMap944', 'https://picsum.photos/id/944/500/500'),
(2, 'FloorMap343', 'https://picsum.photos/id/343/500/500'),
(3, 'FloorMap69', 'https://picsum.photos/id/69/500/500'),
(4, 'FloorMap879', 'https://picsum.photos/id/879/500/500'),
(5, 'FloorMap333', 'https://picsum.photos/id/333/500/500');
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
