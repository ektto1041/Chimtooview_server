package com.yeon.chimtooview.Service;

import com.yeon.chimtooview.Entity.Reload;
import com.yeon.chimtooview.Repository.ReloadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReloadService {
    @Autowired
    private ReloadRepository reloadRepository;

    public LocalDateTime getReloadTime() {
        List<Reload> reloadList = reloadRepository.findAll();

        LocalDateTime time = null;
        if(reloadList.size() != 0) {
            time = reloadList.get(0).getTime();
        }

        return time;
    }

    public void postReload() {
        reloadRepository.deleteAll();

        Reload reload = new Reload();
        reload.setTime(LocalDateTime.now());

        reloadRepository.save(reload);
    }
}
