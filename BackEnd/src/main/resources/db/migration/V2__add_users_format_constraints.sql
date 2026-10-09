alter table "Users"
    add constraint users_phone_digits check (phone ~ '^[0-9]{10,11}$'),
    add constraint users_email_format check (email ~ '^[^@\s]+@[^@\s]+$');