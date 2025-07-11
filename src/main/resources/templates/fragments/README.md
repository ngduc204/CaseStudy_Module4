# Thymeleaf Fragments - Hướng dẫn sử dụng

## Tổng quan
Thư mục này chứa các fragment Thymeleaf có thể tái sử dụng cho ứng dụng Star Movies.

## Cấu trúc Fragment

### 1. `head.html`
- **Mục đích**: Chứa thẻ `<head>` với meta tags, title và CSS links
- **Sử dụng**: `th:replace="fragments/head :: head"`
- **Tham số**: `title` (tùy chọn) - tiêu đề trang

### 2. `styles.html`
- **Mục đích**: Chứa tất cả CSS styles cho ứng dụng
- **Sử dụng**: `th:replace="fragments/styles :: styles"`
- **Lưu ý**: Bao gồm responsive design và theme variables

### 3. `navbar.html`
- **Mục đích**: Navigation bar cố định
- **Sử dụng**: `th:replace="fragments/navbar :: navbar"`
- **Tính năng**: Responsive, smooth scrolling

### 4. `footer.html`
- **Mục đích**: Footer chung cho tất cả trang
- **Sử dụng**: `th:replace="fragments/footer :: footer"`

### 5. `scripts.html`
- **Mục đích**: JavaScript cho smooth scrolling và play button
- **Sử dụng**: `th:replace="fragments/scripts :: scripts"`

### 6. `layout.html`
- **Mục đích**: Layout template chung
- **Sử dụng**: `th:replace="fragments/layout :: layout"`

### 7. `movie-card.html`
- **Mục đích**: Component hiển thị thông tin phim
- **Sử dụng**: `th:replace="fragments/movie-card :: movie-card(movie)"`
- **Tham số**: `movie` object với các thuộc tính:
  - `title`: Tên phim
  - `description`: Mô tả phim
  - `imageUrl`: URL hình ảnh
  - `rating`: Điểm đánh giá
  - `year`: Năm phát hành

## Cách sử dụng

### Trang đơn giản
```html
<!DOCTYPE html>
<html lang="vi" xmlns:th="http://www.thymeleaf.org">
<head th:replace="fragments/head :: head"></head>
<body>
    <div th:replace="fragments/navbar :: navbar"></div>
    
    <!-- Nội dung trang -->
    <main>
        <h1>Nội dung trang</h1>
    </main>
    
    <div th:replace="fragments/footer :: footer"></div>
    <div th:replace="fragments/styles :: styles"></div>
    <div th:replace="fragments/scripts :: scripts"></div>
</body>
</html>
```

### Sử dụng movie card
```html
<div th:replace="fragments/movie-card :: movie-card(${movie})"></div>
```

### Sử dụng layout
```html
<div th:replace="fragments/layout :: layout">
    <!-- Nội dung sẽ được chèn vào đây -->
</div>
```

## Lợi ích

1. **Tái sử dụng**: Tránh lặp lại code
2. **Bảo trì dễ dàng**: Chỉ cần sửa một chỗ
3. **Tính nhất quán**: Giao diện đồng nhất
4. **Hiệu suất**: Giảm kích thước file HTML
5. **Phát triển nhanh**: Tạo trang mới dễ dàng

## Lưu ý

- Đảm bảo tất cả fragment đều có namespace Thymeleaf: `xmlns:th="http://www.thymeleaf.org"`
- Khi thêm fragment mới, cập nhật README này
- Test kỹ các fragment trước khi deploy 