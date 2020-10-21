package com.yeon.chimtooview.Controller;

import com.yeon.chimtooview.Service.ReloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class ReloadController {
    @Autowired
    private ReloadService reloadService;

    @RequestMapping(
            value = "/getReloadTime",
            method = RequestMethod.GET
    )
    public ResponseEntity getReloadTime() {
        LocalDateTime result = reloadService.getReloadTime();

        return ResponseEntity.ok(result);
    }
}
