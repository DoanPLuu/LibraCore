
package com.libracoreteam.libracore.bus;


import com.libracoreteam.libracore.dao.ThongKeTienPhatDAO;
import java.time.LocalDate;
import java.util.List;

public class ThongKeTienPhatBUS {
    
    private final ThongKeTienPhatDAO dao = new ThongKeTienPhatDAO();

    public List<Object[]> getThongKeTienPhat(LocalDate from, LocalDate to) {
        return dao.getThongKeTienPhat(from, to);
    }
}
