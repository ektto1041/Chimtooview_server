package com.yeon.chimtooview.Service;

import com.yeon.chimtooview.Entity.Reports;
import com.yeon.chimtooview.Repository.ReportsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ReportsService {
    @Autowired
    private ReportsRepository reportsRepository;

    /**
     *  POST
     */

    /**
     * 신고하기
     * @param newReports
     * Object {type, itemId, content} - String
     * @return
     * Reports
     */
    public Reports postReports(HashMap<String, String> newReports) {
        Reports reports = new Reports();
        reports.setType(newReports.get("type"));
        reports.setItemId(Long.parseLong(newReports.get("itemId")));
        reports.setContent(newReports.get("content"));

        return reportsRepository.save(reports);
    }
}
