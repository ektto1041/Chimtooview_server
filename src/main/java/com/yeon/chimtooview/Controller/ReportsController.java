package com.yeon.chimtooview.Controller;

import com.yeon.chimtooview.Entity.Reports;
import com.yeon.chimtooview.Service.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class ReportsController {
    @Autowired
    private ReportsService reportsService;

    /**
     *  POST
     */

    @RequestMapping(
            value = "/postReports",
            method = RequestMethod.POST
    )
    public ResponseEntity postReports(@RequestBody HashMap<String, String> newReports) {
        Reports reports = reportsService.postReports(newReports);

        return ResponseEntity.ok().build();
    }
}
