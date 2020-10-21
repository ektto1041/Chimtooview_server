package com.yeon.chimtooview.Service;

import com.yeon.chimtooview.Entity.NoticeItem;
import com.yeon.chimtooview.Entity.QNoticeItem;
import com.yeon.chimtooview.Repository.NoticeItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoticeItemService extends QuerydslRepositorySupport {
    public NoticeItemService() {super(NoticeItem.class);}

    @Autowired
    private NoticeItemRepository noticeItemRepository;

    public long getNoticeAllCount() {
        return noticeItemRepository.count();
    }

    public List<NoticeItem> getNoticeAllOrderByPaging(int pageCurrent) {
        QNoticeItem qNoticeItem = QNoticeItem.noticeItem;

        List<NoticeItem> noticeItemList = from(qNoticeItem).orderBy(qNoticeItem.publishedAt.desc()).offset(10 * (pageCurrent-1)).limit(10).fetch();

        return noticeItemList;
    }

    public NoticeItem postNoticeItem(List<String> newNoticeItem) {
        NoticeItem noticeItem = new NoticeItem();
        noticeItem.setTitle(newNoticeItem.get(0));
        noticeItem.setContent(newNoticeItem.get(1));
        noticeItem.setType(newNoticeItem.get(2));
        noticeItem.setPublishedAt(LocalDateTime.now());

        return noticeItemRepository.save(noticeItem);
    }
}
