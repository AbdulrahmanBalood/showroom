
CREATE TABLE showroom (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    commercial_registration_number VARCHAR(255) UNIQUE NOT NULL,
    manager_name VARCHAR(100),
    phone_number BIGINT CHECK (phone_number <= 999999999999999),
    address VARCHAR(255),
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    updated_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    deleted_at TIMESTAMP,
    uuid VARCHAR(255) UNIQUE
);


CREATE TABLE car (
    id BIGSERIAL PRIMARY KEY,
    vin VARCHAR(25) NOT NULL,
    maker VARCHAR(25) NOT NULL,
    model VARCHAR(25) NOT NULL,
    model_year VARCHAR(25) NOT NULL,
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


