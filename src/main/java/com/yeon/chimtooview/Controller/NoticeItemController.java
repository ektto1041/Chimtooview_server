package com.yeon.chimtooview.Controller;

import com.yeon.chimtooview.Dto.NoticeItemDto;
import com.yeon.chimtooview.Entity.NoticeItem;
import com.yeon.chimtooview.Service.NoticeItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class NoticeItemController {
    @Autowired
    private NoticeItemService noticeItemService;

    @RequestMapping(
            value = "/getNoticeAllCount",
            method = RequestMethod.GET
    )
    public ResponseEntity getNoticeAllCount() {
        long result = noticeItemService.getNoticeAllCount();

        return ResponseEntity.ok(result);
    }

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
