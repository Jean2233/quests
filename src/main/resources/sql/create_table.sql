CREATE TABLE IF NOT EXISTS `quest_accounts` (
    id CHAR(36) NOT NULL PRIMARY KEY,
    quest VARCHAR(64) NOT NULL,
    progress INT NOT NULL,
    finished_quests TEXT NOT NULL
);