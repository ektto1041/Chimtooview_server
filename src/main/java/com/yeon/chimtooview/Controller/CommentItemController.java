package com.yeon.chimtooview.Controller;

import com.yeon.chimtooview.Dto.CommentItemDto;
import com.yeon.chimtooview.Entity.BoardItem;
import com.yeon.chimtooview.Entity.CommentItem;
import com.yeon.chimtooview.Exceptions.NotFoundBoardItemException;
import com.yeon.chimtooview.Exceptions.NotFoundCommentItemException;
import com.yeon.chimtooview.Service.BoardItemService;
import com.yeon.chimtooview.Service.CommentItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class CommentItemController {
    @Autowired
    private CommentItemService commentItemService;

    @Autowired
    private BoardItemService boardItemService;

    /**
     *  GET
     */

    @RequestMapping(
            value = "/getCommentItemAllOrderByPaging/{boardItemId}/{pageCurrent}",
            method = RequestMethod.GET
    )
    public ResponseEntity getCommentItemAllOrderByPaging(@PathVariable("boardItemId") long boardItemId, @PathVariable("pageCurrent") int pageCurrent) {
        try {
            BoardItem boardItem = boardItemService.getBoardItemById(boardItemId);

            List<CommentItem> commentItemList = commentItemService.getCommentItemAllOrderByPaging(boardItem, pageCurrent);

            List<CommentItemDto> result = new ArrayList<>();
            for(CommentItem commentItem : commentItemList) {
                CommentItemDto commentItemDto = commentItem.toDto();

                result.add(commentItemDto);
            }

            return ResponseEntity.ok(result);
        } catch(NotFoundBoardItemException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



    /**
     *  POST
     */

    /**
     * 댓글 쓰기
     * @param newCommentItem
     * Object {userId, userPw, content, salt, boardItemId} - String
     * @return
     * CommentItem
     */
    @RequestMapping(
            value = "/postCommentItem",
            method = RequestMethod.POST
    )
    public ResponseEntity postCommentItem(@RequestBody HashMap<String, String> newCommentItem) {
        try {
            BoardItem boardItem = boardItemService.getBoardItemById(Long.parseLong(newCommentItem.get("boardItemId")));

            CommentItem commentItem = commentItemService.postCommentItem(newCommentItem, boardItem);

            CommentItemDto result = commentItem.toDto();

            return ResponseEntity.ok(result);
        } catch(NotFoundBoardItemException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     *  DELETE
     */

    /**
     * 댓글 삭제
     * @param id
     * long
     * @return
     * void
     */
    @RequestMapping(
            value = "/deleteCommentItemById/{id}",
            method = RequestMethod.DELETE
    )
    public ResponseEntity deleteCommentItemById(@PathVariable("id") long id) {
        try {
            commentItemService.deleteCommentItemById(id);

            return ResponseEntity.ok().build();
        } catch(NotFoundCommentItemException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
