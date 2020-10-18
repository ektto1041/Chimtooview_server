package com.yeon.chimtooview.Configuration;

import com.yeon.chimtooview.Entity.VideoThumbnail;
import com.yeon.chimtooview.Repository.VideoThumbnailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitService implements CommandLineRunner {
    @Autowired
    private VideoThumbnailRepository videoThumbnailRepository;

    @Override
    public void run(String... args) throws Exception {
    }
}
