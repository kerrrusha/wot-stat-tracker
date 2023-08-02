CREATE SCHEMA IF NOT EXISTS wot_stat_tracker;
USE wot_stat_tracker;

CREATE TABLE IF NOT EXISTS player (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    nickname nvarchar(24) NOT NULL UNIQUE,
    account_id nvarchar(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS stat (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    avg_damage FLOAT(53),
    avg_experience FLOAT(53),
    battles INTEGER,
    draws INTEGER,
    global_rating INTEGER,
    wtr INTEGER,
    losses INTEGER,
    trees_cut INTEGER,
    wins INTEGER,
    wn7 FLOAT(53),
    wn8 FLOAT(53),
    last_battle_time TIMESTAMP(6),
    player_id BIGINT,
    FOREIGN KEY (player_id) REFERENCES player(id) ON DELETE CASCADE
);
