package com.libracoreteam.libracore.bus;

import com.libracoreteam.libracore.dao.DocGiaDAO;
import com.libracoreteam.libracore.model.DocGia;

import javax.print.Doc;
import java.util.List;

public class DocGiaBUS {
    private final DocGiaDAO docGiaDAO=new DocGiaDAO();
    public List<DocGia> getAllDocGia(){
        return docGiaDAO.SelectAll();
    }
    public String addDocGia(DocGia docGia){
        if(docGia.getTenDocGia()==null || docGia.getTenDocGia().trim().isEmpty()){
            return "Tên độc giả không được để trống";
        }
        if (docGia.getNgaySinh().isAfter(java.time.LocalDate.now())) {
            return "Ngày sinh không được ở tương lai!";
        }
        if(docGia.getSdt()==null || !docGia.getSdt().matches("\\d{10,11}")) return "Số điện thoại không đúng mẫu";
        if (docGia.getEmail() != null && !docGia.getEmail().contains("@")) {
            return "Email không đúng định dạng!";
        }
        if(docGiaDAO.insert(docGia)){
            return "Thêm thành công";
        }else return "Thêm thất bại, lỗi hệ thống!";
    }

    public String delete(int idDocGia){
        if(docGiaDAO.delete(idDocGia)) return "Xóa thành công";
        else return "Xóa thất bại, lỗi hệ thống!";
    }

    public String updateDocGia(DocGia docGia) {
        if(docGia.getTenDocGia()==null || docGia.getTenDocGia().trim().isEmpty()){
            return "Tên độc giả không được để trống";
        }
        if (docGia.getSdt() != null && !docGia.getSdt().trim().isEmpty() && !docGia.getSdt().matches("\\d{10,11}")) {
            return "Số điện thoại không đúng mẫu";
        }
        if (docGiaDAO.update(docGia)) {
            return "Cập nhật thành công!";
        } else {
            return "Cập nhật thất bại, có thể độc giả không tồn tại!";
        }
    }
}
