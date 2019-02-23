ALTER TABLE user_account
  ADD COLUMN gender_preference VARCHAR(255),
  ADD COLUMN age_preference_min INT,
  ADD COLUMN age_preference_max INT;
