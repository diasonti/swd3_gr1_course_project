CREATE TABLE chat_message (
  id          BIGSERIAL PRIMARY KEY,
  sent_at     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  sender_id   BIGINT REFERENCES user_account(id),
  receiver_id BIGINT REFERENCES user_account(id),
  text        TEXT,
  match_id    BIGINT REFERENCES matchmaking_match(id)
);
