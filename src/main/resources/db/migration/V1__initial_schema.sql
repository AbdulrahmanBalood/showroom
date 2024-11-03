-- Create showroom table
CREATE TABLE showroom (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    commercial_registration_number VARCHAR(15) NOT NULL UNIQUE,
    manager_name VARCHAR(100),
    phone_number VARCHAR(15) NOT NULL,
    address VARCHAR(255),
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    updated_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    deleted_at TIMESTAMP,
    uuid VARCHAR(255) UNIQUE,
    CONSTRAINT chk_commercial_registration_number CHECK (commercial_registration_number ~ '^[0-9]{1,15}$'),
    CONSTRAINT chk_phone_number CHECK (phone_number ~ '^[0-9]{1,15}$')
);

-- Create car table
CREATE TABLE car (
    id BIGSERIAL PRIMARY KEY,
    vin VARCHAR(25) NOT NULL,
    maker VARCHAR(25) NOT NULL,
    model VARCHAR(25) NOT NULL,
    model_year CHAR(4) NOT NULL CHECK (model_year ~ '^[0-9]{4}$'),
    price DOUBLE PRECISION NOT NULL,
    showroom_id BIGINT REFERENCES showroom(id) ON DELETE SET NULL,
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    updated_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    deleted_at TIMESTAMP,
    uuid VARCHAR(255) UNIQUE
);
