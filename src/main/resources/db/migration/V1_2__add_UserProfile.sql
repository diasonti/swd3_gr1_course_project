ALTER TABLE user_account
  ADD COLUMN name VARCHAR(255),
  ADD COLUMN gender VARCHAR(255),
  ADD COLUMN age INT,
  ADD COLUMN location VARCHAR(255),
  ADD COLUMN description TEXT;