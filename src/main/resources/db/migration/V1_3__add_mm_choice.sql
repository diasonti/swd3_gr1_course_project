CREATE TABLE matchmaking_choice (
  id BIGSERIAL PRIMARY KEY,
  source_user_id BIGINT REFERENCES user_account(id),
  target_user_id BIGINT REFERENCES user_account(id),
  decision VARCHAR(255)
);
