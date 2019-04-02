CREATE TABLE matchmaking_match (
  id                    BIGSERIAL PRIMARY KEY,
  created_at            TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  first_user_id         BIGINT REFERENCES user_account (id),
  second_user_id        BIGINT REFERENCES user_account (id),
  first_user_choice_id  BIGINT REFERENCES matchmaking_choice (id),
  second_user_choice_id BIGINT REFERENCES matchmaking_choice (id),
  type                  VARCHAR(255)                NOT NULL
);
