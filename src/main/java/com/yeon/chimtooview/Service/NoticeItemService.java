package com.yeon.chimtooview.Service;

import com.yeon.chimtooview.Entity.NoticeItem;
import com.yeon.chimtooview.Entity.QNoticeItem;
import com.yeon.chimtooview.Exceptions.ExceptionMessage;
import com.yeon.chimtooview.Exceptions.NotFoundNoticeItemException;
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

    /**
     *  GET
     */

    /**
     * 모든 공지사항의 개수를 가져오는 API
     * @return
     * long
     */
    public long getNoticeAllCount() {
        return noticeItemRepository.count();
    }

    /**
     * 모든 공지사항을 OrderBy Paging 해서 가져옴
     * @param pageCurrent
     * int
     * @return
     * NoticeItem List
     */
    public List<NoticeItem> getNoticeAllOrderByPaging(int pageCurrent) {
        QNoticeItem qNoticeItem = QNoticeItem.noticeItem;

        List<NoticeItem> noticeItemList = from(qNoticeItem).orderBy(qNoticeItem.publishedAt.desc()).offset(10 * (pageCurrent-1)).limit(10).fetch();

        return noticeItemList;
    }

    /**
     * 가장 최신의 공지사항 가져옴
     * @return
     * NoticeItem
     * @throws NotFoundNoticeItemException
     */
    public NoticeItem getNoticeItemNew() throws NotFoundNoticeItemException {
        QNoticeItem qNoticeItem = QNoticeItem.noticeItem;

        NoticeItem noticeItem = from(qNoticeItem).orderBy(qNoticeItem.publishedAt.desc()).limit(1).fetchFirst();
        if(noticeItem == null) throw new NotFoundNoticeItemException(ExceptionMessage.NF_NOTICE_ITEM);

        return noticeItem;
    }

    /**
     *  POST
     */

    /**
     * 공지사항 쓰기
     * @param newNoticeItem
     * String List 0: title, 1: content, 2: type
     * @return
     * NoticeItem
     */
    public NoticeItem postNoticeItem(List<String> newNoticeItem) {
        NoticeItem noticeItem = new NoticeItem();
        noticeItem.setTitle(newNoticeItem.get(0));
        noticeItem.setContent(newNoticeItem.get(1));
        noticeItem.setType(newNoticeItem.get(2));
        noticeItem.setPublishedAt(LocalDateTime.now().plusHours(9));

        return noticeItemRepository.save(noticeItem);
    }
}
