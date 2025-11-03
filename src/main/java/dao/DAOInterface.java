package dao;

import java.sql.SQLException;
import java.util.List;

public interface DAOInterface<T> {
    int them(T t) throws SQLException;      // thêm đối tượng T vào DB
    int capNhat(T t);                       // cập nhật đối tượng T
    int xoa(T t);                           // xóa đối tượng T
    List<T> getAllList();                   // lấy toàn bộ danh sách
    List<T> chon_AllList();                 // chọn danh sách theo điều kiện (tùy định nghĩa)
    T selectById(T t);                      // lấy ra đúng 1 đối tượng theo ID
}
