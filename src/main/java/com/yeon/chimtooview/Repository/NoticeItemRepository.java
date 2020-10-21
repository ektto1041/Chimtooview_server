package com.yeon.chimtooview.Repository;

import com.yeon.chimtooview.Entity.NoticeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeItemRepository extends JpaRepository<NoticeItem, Long> {
}
