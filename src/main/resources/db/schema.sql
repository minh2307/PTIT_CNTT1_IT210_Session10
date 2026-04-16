CREATE TABLE admins (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(30) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT chk_admin_role CHECK (role IN ('SUPER_ADMIN', 'LAB_ADMIN')),
    CONSTRAINT chk_admin_status CHECK (status IN ('ACTIVE', 'INACTIVE'))
);

CREATE TABLE devices (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    device_code VARCHAR(30) NOT NULL UNIQUE,
    device_name VARCHAR(120) NOT NULL,
    category VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    image_url VARCHAR(255),
    quantity_in_stock INT NOT NULL,
    quantity_available INT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT chk_device_stock CHECK (quantity_in_stock >= 0),
    CONSTRAINT chk_device_available CHECK (
        quantity_available >= 0
        AND quantity_available <= quantity_in_stock
    ),
    CONSTRAINT chk_device_status CHECK (
        status IN ('AVAILABLE', 'LOW_STOCK', 'OUT_OF_STOCK', 'MAINTENANCE')
    )
);

CREATE TABLE lab_rooms (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    room_code VARCHAR(20) NOT NULL UNIQUE,
    room_name VARCHAR(100) NOT NULL,
    location VARCHAR(120) NOT NULL,
    description VARCHAR(255),
    image_url VARCHAR(255),
    capacity INT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT chk_room_capacity CHECK (capacity > 0),
    CONSTRAINT chk_room_status CHECK (
        status IN ('AVAILABLE', 'IN_USE', 'MAINTENANCE', 'CLOSED')
    )
);

CREATE TABLE borrow_requests (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    request_code VARCHAR(30) NOT NULL UNIQUE,
    request_type VARCHAR(20) NOT NULL,
    student_name VARCHAR(100) NOT NULL,
    student_code VARCHAR(20) NOT NULL,
    student_email VARCHAR(120) NOT NULL,
    device_id BIGINT,
    lab_room_id BIGINT,
    quantity INT NOT NULL,
    start_date DATE NOT NULL,
    expected_return_date DATE NOT NULL,
    actual_return_date DATE,
    reason VARCHAR(500) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    admin_id BIGINT,
    admin_note VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_request_device FOREIGN KEY (device_id) REFERENCES devices(id),
    CONSTRAINT fk_request_room FOREIGN KEY (lab_room_id) REFERENCES lab_rooms(id),
    CONSTRAINT fk_request_admin FOREIGN KEY (admin_id) REFERENCES admins(id),
    CONSTRAINT chk_request_type CHECK (request_type IN ('DEVICE', 'LAB_ROOM')),
    CONSTRAINT chk_request_status CHECK (
        status IN ('PENDING', 'APPROVED', 'REJECTED', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED')
    ),
    CONSTRAINT chk_request_dates CHECK (expected_return_date > start_date),
    CONSTRAINT chk_actual_return_date CHECK (
        actual_return_date IS NULL OR actual_return_date >= start_date
    ),
    CONSTRAINT chk_request_target CHECK (
        (request_type = 'DEVICE' AND device_id IS NOT NULL AND lab_room_id IS NULL)
        OR
        (request_type = 'LAB_ROOM' AND lab_room_id IS NOT NULL AND device_id IS NULL)
    ),
    CONSTRAINT chk_request_quantity CHECK (
        (request_type = 'DEVICE' AND quantity > 0)
        OR
        (request_type = 'LAB_ROOM' AND quantity = 1)
    )
);

CREATE INDEX idx_devices_status ON devices(status);
CREATE INDEX idx_rooms_status ON lab_rooms(status);
CREATE INDEX idx_requests_status ON borrow_requests(status);
CREATE INDEX idx_requests_device ON borrow_requests(device_id);
CREATE INDEX idx_requests_room ON borrow_requests(lab_room_id);
CREATE INDEX idx_requests_admin ON borrow_requests(admin_id);
