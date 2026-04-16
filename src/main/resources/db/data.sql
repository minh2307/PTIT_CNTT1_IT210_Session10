INSERT INTO admins (username, full_name, email, password_hash, role, status)
VALUES
    ('admin01', 'Nguyen Van Admin', 'admin01@ptit.edu.vn', '$2a$10$samplehashadmin01', 'SUPER_ADMIN', 'ACTIVE'),
    ('labmanager', 'Tran Thi Lab', 'labmanager@ptit.edu.vn', '$2a$10$samplehashadmin02', 'LAB_ADMIN', 'ACTIVE');

INSERT INTO devices (
    device_code,
    device_name,
    category,
    description,
    image_url,
    quantity_in_stock,
    quantity_available,
    status
)
VALUES
    ('DEV001', 'External Monitor 24 inch', 'Display', 'Monitor for coding and design practice', '/images/devices/monitor-24.jpg', 12, 8, 'AVAILABLE'),
    ('DEV002', 'HDMI Cable', 'Accessory', '1.5m HDMI cable for lab and project use', '/images/devices/hdmi-cable.jpg', 30, 24, 'AVAILABLE'),
    ('DEV003', 'Arduino Uno R3', 'Board', 'Board for embedded programming lab', '/images/devices/arduino-uno.jpg', 15, 10, 'LOW_STOCK'),
    ('DEV004', 'USB Type-C Hub', 'Accessory', 'USB-C hub with HDMI and USB-A ports', '/images/devices/usb-c-hub.jpg', 10, 0, 'OUT_OF_STOCK');

INSERT INTO lab_rooms (
    room_code,
    room_name,
    location,
    description,
    image_url,
    capacity,
    status
)
VALUES
    ('LAB-A101', 'Network Lab A101', 'Building A - Floor 1', 'Lab for networking practice and extra sessions', '/images/rooms/lab-a101.jpg', 40, 'AVAILABLE'),
    ('LAB-B203', 'Embedded Lab B203', 'Building B - Floor 2', 'Lab for microcontroller and IoT practice', '/images/rooms/lab-b203.jpg', 35, 'AVAILABLE'),
    ('LAB-C305', 'Multimedia Lab C305', 'Building C - Floor 3', 'Lab for multimedia, design and presentation work', '/images/rooms/lab-c305.jpg', 45, 'MAINTENANCE');

INSERT INTO borrow_requests (
    request_code,
    request_type,
    student_name,
    student_code,
    student_email,
    device_id,
    lab_room_id,
    quantity,
    start_date,
    expected_return_date,
    actual_return_date,
    reason,
    status,
    admin_id,
    admin_note
)
VALUES
    ('REQ001', 'DEVICE', 'Le Minh Duc', 'IT220001', 'duc.it220001@ptit.edu.vn', 1, NULL, 2, '2026-04-20', '2026-04-22', NULL, 'Borrow extra monitors for team coding practice', 'APPROVED', 2, 'Prepare at front desk before 08:00'),
    ('REQ002', 'LAB_ROOM', 'Pham Thu Ha', 'IT220045', 'ha.it220045@ptit.edu.vn', NULL, 2, 1, '2026-04-23', '2026-04-23', NULL, 'Use embedded lab for final project testing', 'PENDING', NULL, NULL),
    ('REQ003', 'DEVICE', 'Nguyen Quang Huy', 'IT220078', 'huy.it220078@ptit.edu.vn', 3, NULL, 3, '2026-04-21', '2026-04-25', NULL, 'Borrow boards for IoT assignment demo', 'IN_PROGRESS', 2, 'Student must return on time'),
    ('REQ004', 'LAB_ROOM', 'Do Mai Linh', 'IT220099', 'linh.it220099@ptit.edu.vn', NULL, 1, 1, '2026-04-18', '2026-04-18', '2026-04-18', 'Extra review session before exam', 'COMPLETED', 1, 'Completed without issue');
