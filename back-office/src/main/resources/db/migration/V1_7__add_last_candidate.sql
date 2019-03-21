ALTER TABLE user_account ADD COLUMN last_candidate_id BIGINT REFERENCES user_account(id);
