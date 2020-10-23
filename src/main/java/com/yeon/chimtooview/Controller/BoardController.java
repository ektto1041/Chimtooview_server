package com.yeon.chimtooview.Controller;

import com.yeon.chimtooview.Dto.BoardItemDto;
import com.yeon.chimtooview.Entity.BoardItem;
import com.yeon.chimtooview.Exceptions.NotFoundBoardItemException;
import com.yeon.chimtooview.Service.BoardItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class BoardController {
    @Autowired
    private BoardItemService boardItemService;

    /**
     *  GET
     */

    /**
     * 게시판 모든 글 개수 가져오기
     * @param searchWord
     * 검색어
     * @return
     * long
     */
    @RequestMapping(
            value = "/getBoardItemAllCountByFilter/{searchWord}",
            method = RequestMethod.GET
    )
    public ResponseEntity getBoardItemAllCountByFilter(@PathVariable("searchWord") String searchWord) {
        long result = boardItemService.getBoardItemAllCountByFilter(searchWord);

        return ResponseEntity.ok(result);
    }

    /**
     * 게시판 모든 글 가져오기
     * @param searchWord
     * 검색어
     * @param pageCurrent
     * 현재 페이지
     * @return
     * BoardItem List
     */
    @RequestMapping(
            value = "/getBoardItemAllOrderByPaging/{searchWord}/{pageCurrent}",
            method = RequestMethod.GET
    )
    public ResponseEntity getBoardItemAllOrderByPaging(@PathVariable("searchWord") String searchWord, @PathVariable("pageCurrent") int pageCurrent) {
        List<BoardItem> boardItemList = boardItemService.getBoardItemAllOrderByPaging(searchWord, pageCurrent);

        List<BoardItemDto> result = new ArrayList<>();
        for(BoardItem boardItem : boardItemList) {
            BoardItemDto boardItemDto = boardItem.toDto();

            result.add(boardItemDto);
        }

        return ResponseEntity.ok(result);
    }

    /**
     * 게시판 id로 글 하나 가져오기
     * @param id
     * 글 id
     * @return
     * BoardItem
     */
    @RequestMapping(
            value = "/getBoardItemById/{id}",
            method = RequestMethod.GET
    )
    public ResponseEntity getBoardItemById(@PathVariable("id") long id) {
        try {
            BoardItem boardItem = boardItemService.getBoardItemById(id);

            BoardItemDto result = boardItem.toDto();

            return ResponseEntity.ok(result);
        } catch(NotFoundBoardItemException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



    /**
     *  POST
     */

    /**
     * 게시판 글쓰기
     * @param newBoardItem
     * object {userId, userPw, salt, title, content} - String
     * @return
     * BoardItem
     */
    @RequestMapping(
            value = "/postBoardItem",
            method = RequestMethod.POST
    )
    public ResponseEntity postBoardItem(@RequestBody HashMap<String, String> newBoardItem) {
        BoardItem boardItem = boardItemService.postBoardItem(newBoardItem);

        BoardItemDto result = boardItem.toDto();

        return ResponseEntity.ok(result);
    }
}
