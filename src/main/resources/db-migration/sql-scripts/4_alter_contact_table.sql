alter table if exists contact
    add column if not exists user_id UUID ;