
package com.libracoreteam.libracore.bus;

import com.libracoreteam.libracore.dao.ThongKeMuonTraDAO;
import java.time.LocalDate;
import java.util.List;

public class ThongKeMuonTraBUS {
    private final ThongKeMuonTraDAO dao = new ThongKeMuonTraDAO();

    public List<Object[]> getThongKeMuonTra(LocalDate from, LocalDate to) {
        return dao.getThongKeMuonTra(from, to);
    }
}   