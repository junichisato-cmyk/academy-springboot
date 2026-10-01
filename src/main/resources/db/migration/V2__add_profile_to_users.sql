ALTER TABLE users
ADD COLUMN introduction VARCHAR(200),
ADD COLUMN profile_image BYTEA,
ADD COLUMN profile_image_content_type VARCHAR(100);