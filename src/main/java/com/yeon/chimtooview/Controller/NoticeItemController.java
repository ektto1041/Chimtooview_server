package com.yeon.chimtooview.Controller;

import com.yeon.chimtooview.Dto.NoticeItemDto;
import com.yeon.chimtooview.Entity.NoticeItem;
import com.yeon.chimtooview.Exceptions.NotFoundNoticeItemException;
import com.yeon.chimtooview.Service.NoticeItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class NoticeItemController {
    @Autowired
    private NoticeItemService noticeItemService;

    /**
     *  GET
     */

    /**
     * 모든 공지사항의 개수를 가져오는 API
     * @return
     * long
     */
    @RequestMapping(
            value = "/getNoticeAllCount",
            method = RequestMethod.GET
    )
    public ResponseEntity getNoticeAllCount() {
        long result = noticeItemService.getNoticeAllCount();

        return ResponseEntity.ok(result);
    }

    /**
     * 모든 공지사항을 OrderBy Paging 해서 가져오는 API
     * @param pageCurrent
     * int
     * @return
     * NoticeItem List
     */
    @RequestMapping(
            value = "/getNoticeAllOrderByPaging/{pageCurrent}",
            method = RequestMethod.GET
    )
    public ResponseEntity getNoticeAllOrderByPaging(@PathVariable("pageCurrent") int pageCurrent) {
        List<NoticeItem> noticeItemList = noticeItemService.getNoticeAllOrderByPaging(pageCurrent);

        List<NoticeItemDto> result = new ArrayList<>();
        for(NoticeItem noticeItem : noticeItemList) {
            NoticeItemDto noticeItemDto = noticeItem.toDto();

            result.add(noticeItemDto);
        }

        return ResponseEntity.ok(result);
    }

    /**
     * 가장 최신의 공지사항 가져옴
     * @return
     * NoticeItem
     */
    @RequestMapping(
            value = "/getNoticeItemNew",
            method = RequestMethod.GET
    )
    public ResponseEntity getNoticeItemNew() {
        try {
            NoticeItem noticeItem = noticeItemService.getNoticeItemNew();

            NoticeItemDto result = noticeItem.toDto();

            return ResponseEntity.ok(result);
        } catch(NotFoundNoticeItemException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
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
    @RequestMapping(
            value = "/postNoticeItem",
            method = RequestMethod.POST
    )
    public ResponseEntity postNoticeItem(@RequestBody List<String> newNoticeItem) {
        NoticeItem noticeItem = noticeItemService.postNoticeItem(newNoticeItem);

        NoticeItemDto result = noticeItem.toDto();

        return ResponseEntity.ok(result);
    }
}
