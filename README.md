# Hệ thống Quản lý Mượn/Trả Thiết bị IT & Phòng Lab

## Giới thiệu dự án

Dự án này là một Web Portal nội bộ cho phép sinh viên xem danh sách thiết bị/phòng Lab hiện có và điền form đăng ký mượn/sử dụng trực tuyến. Dự án được xây dựng theo mô hình MVC (Model - View - Controller) sử dụng Pure Spring Web MVC 7.x.

## Công nghệ sử dụng

- **Framework**: Spring Web MVC 7.x (Pure Spring, không sử dụng Spring Boot)
- **View Template**: Thymeleaf 3.1.3.RELEASE
- **Validation**: Hibernate Validator 8.0.1.Final (JSR-380)
- **Build Tool**: Gradle
- **Frontend**: Bootstrap 5.3.0, Font Awesome 6.0.0
- **Java Version**: 21

## Cấu trúc dự án

```
src/main/java/com/example/ss10/
├── config/
│   ├── AppConfig.java          # Cấu hình Spring MVC
│   └── AppInit.java           # Khởi tạo DispatcherServlet
├── controller/
│   ├── MainController.java    # Controller trang chủ
│   ├── StudentController.java # Controller cho sinh viên
│   └── AdminController.java   # Controller cho quản lý
├── model/
│   ├── Equipment.java         # Model thiết bị
│   ├── LabRoom.java          # Model phòng lab
│   └── Booking.java          # Model đăng ký mượn
├── service/
│   ├── EquipmentService.java  # Service quản lý thiết bị
│   ├── LabRoomService.java   # Service quản lý phòng lab
│   └── BookingService.java   # Service quản lý đăng ký
└── validation/
    ├── CheckDateCollision.java     # Custom annotation
    └── DateCollisionValidator.java # Custom validator

src/main/webapp/WEB-INF/views/
├── fragments/
│   ├── header.html           # Header fragment
│   └── footer.html           # Footer fragment
├── student/
│   ├── home.html             # Trang chủ sinh viên
│   ├── equipment-list.html   # Danh sách thiết bị
│   ├── lab-room-list.html    # Danh sách phòng lab
│   └── booking-form.html     # Form đăng ký mượn
├── admin/
│   ├── dashboard.html         # Dashboard quản lý
│   ├── equipment-list.html   # Quản lý thiết bị
│   ├── equipment-form.html    # Form thêm/sửa thiết bị
│   ├── lab-room-list.html    # Quản lý phòng lab
│   ├── lab-room-form.html    # Form thêm/sửa phòng lab
│   └── booking-list.html     # Danh sách yêu cầu
└── index.html                # Trang chủ chính
```

## Tính năng

### Module Sinh viên (REQ-S01, REQ-S02)
- ✅ Xem danh mục thiết bị/phòng Lab với thông tin chi tiết
- ✅ Form đăng ký mượn với các trường thông tin đầy đủ
- ✅ Validation dữ liệu đầu vào theo yêu cầu

### Module Quản lý Lab (REQ-A01, REQ-A02)
- ✅ Quản lý danh sách thiết bị (Thêm, Sửa, Xóa)
- ✅ Quản lý danh sách phòng Lab (Thêm, Sửa, Xóa)
- ✅ Xem và xử lý danh sách yêu cầu mượn (Phê duyệt/Từ chối)

### Validation (VAL-01 đến VAL-05)
- ✅ VAL-01: Tất cả trường không được để trống
- ✅ VAL-02: Logic thời gian (Ngày nhận phải là ngày tương lai, Ngày trả phải sau ngày nhận)
- ✅ VAL-03: Logic số lượng (Không vượt quá tồn kho)
- ✅ VAL-04: Định dạng dữ liệu (Email, Mã sinh viên)
- ✅ VAL-05: Xử lý lỗi view (Giữ lại thông tin người dùng, hiển thị lỗi tiếng Việt)

### Giao diện (UI-01, UI-02)
- ✅ UI-01: Sử dụng Thymeleaf Fragment cho giao diện chung
- ✅ UI-02: Giao diện responsive với Bootstrap 5

### Tính năng mở rộng (Bonus)
- ✅ Bonus 1: Custom Validator kiểm tra trùng lịch phòng Lab
- ✅ Bonus 2: RedirectAttributes (Flash Attributes) cho thông báo thành công

## Cách chạy dự án

### Yêu cầu
- Java 21+
- Gradle 7+
- Servlet Container (Apache Tomcat 10+)

### Các bước thực hiện

1. **Clone hoặc download dự án**
   ```bash
   git clone <repository-url>
   cd SS10
   ```

2. **Build dự án**
   ```bash
   ./gradlew build
   ```

3. **Deploy WAR file**
   - File WAR sẽ được tạo trong `build/libs/SS10-1.0-SNAPSHOT.war`
   - Deploy file này vào Apache Tomcat

4. **Truy cập ứng dụng**
   - Mở trình duyệt và truy cập: `http://localhost:8080/SS10/`

## URL Mapping

### Trang chính
- `/` - Trang chủ chính

### Module Sinh viên
- `/student` - Trang chủ sinh viên
- `/student/equipment` - Danh sách thiết bị
- `/student/lab-rooms` - Danh sách phòng lab
- `/student/booking/equipment/{id}` - Form mượn thiết bị
- `/student/booking/lab-room/{id}` - Form đăng ký phòng lab
- `/student/booking` - Xử lý submit form đăng ký

### Module Quản lý
- `/admin` - Dashboard quản lý
- `/admin/equipment` - Danh sách thiết bị
- `/admin/equipment/add` - Thêm thiết bị mới
- `/admin/equipment/edit/{id}` - Sửa thiết bị
- `/admin/equipment/delete/{id}` - Xóa thiết bị
- `/admin/lab-rooms` - Danh sách phòng lab
- `/admin/lab-rooms/add` - Thêm phòng lab mới
- `/admin/lab-rooms/edit/{id}` - Sửa phòng lab
- `/admin/lab-rooms/delete/{id}` - Xóa phòng lab
- `/admin/bookings` - Danh sách tất cả yêu cầu
- `/admin/bookings/pending` - Danh sách yêu cầu đang chờ
- `/admin/bookings/approve/{id}` - Phê duyệt yêu cầu
- `/admin/bookings/reject/{id}` - Từ chối yêu cầu

## Dữ liệu mẫu

Hệ thống được khởi tạo với dữ liệu mẫu:

### Thiết bị
- Màn hình rời 24 inch (10 chiếc)
- Cáp HDMI (25 chiếc)
- Cáp USB-C (30 chiếc)
- Board mạch Arduino (15 chiếc)
- Loa di động (8 chiếc)

### Phòng Lab
- Phòng Lab 101 (30 chỗ)
- Phòng Lab 102 (30 chỗ)
- Phòng Lab 201 (25 chỗ)
- Phòng Lab 202 (20 chỗ)

## Lưu ý quan trọng

1. **Không sử dụng Spring Boot**: Dự án sử dụng Pure Spring Web MVC với cấu hình thủ công
2. **Validation**: Tất cả validation được thực hiện bằng Hibernate Validator và custom validators
3. **Data Storage**: Hiện tại sử dụng in-memory storage (ArrayList) để đơn giản hóa
4. **Responsive Design**: Giao diện được thiết kế responsive trên Bootstrap 5
5. **Security**: Chưa có cơ chế xác thực người dùng (có thể mở rộng)

## Tác giả

Dự án được thực hiện bởi sinh viên Khóa học Java Web - Học viện Công nghệ Bưu chính Viễn thông (PTIT).

## License

Dự án này chỉ phục vụ mục đích học tập và thực hành.
