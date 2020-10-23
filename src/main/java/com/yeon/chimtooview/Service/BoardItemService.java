package com.yeon.chimtooview.Service;

import com.querydsl.jpa.JPQLQuery;
import com.yeon.chimtooview.Entity.BoardItem;
import com.yeon.chimtooview.Entity.QBoardItem;
import com.yeon.chimtooview.Exceptions.ExceptionMessage;
import com.yeon.chimtooview.Exceptions.NotFoundBoardItemException;
import com.yeon.chimtooview.Repository.BoardItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
public class BoardItemService extends QuerydslRepositorySupport {
    public BoardItemService() {super(BoardItem.class);}

    @Autowired
    private BoardItemRepository boardItemRepository;

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
    public long getBoardItemAllCountByFilter(String searchWord) {
        QBoardItem qBoardItem = QBoardItem.boardItem;

        JPQLQuery<BoardItem> query = from(qBoardItem);

        // 검색어가 __BLANK 가 아닐 경우의 처리 (검색어가 제대로 입력되었을 때)
        if(!searchWord.equals("__BLANK")) {
            query = query.where(qBoardItem.title.contains(searchWord));
        }

        long count = query.fetchCount();

        return count;
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
    public List<BoardItem> getBoardItemAllOrderByPaging(String searchWord, int pageCurrent) {
        QBoardItem qBoardItem = QBoardItem.boardItem;

        JPQLQuery<BoardItem> query = from(qBoardItem).orderBy(qBoardItem.publishedAt.desc());

        // 검색어가 __BLANK 가 아닐 경우의 처리 (검색어가 제대로 입력되었을 때)
        if(!searchWord.equals("__BLANK")) {
            query = query.where(qBoardItem.title.contains(searchWord));
        }

        List<BoardItem> boardItemList = query.offset(30 * (pageCurrent-1)).limit(30).fetch();

        return boardItemList;
    }

    /**
     * 게시판 id로 글 하나 가져오기
     * @param id
     * 글 id
     * @return
     * BoardItem
     */
    public BoardItem getBoardItemById(long id) throws NotFoundBoardItemException {
        BoardItem boardItem = boardItemRepository.findById(id).orElse(null);
        if(boardItem == null) throw new NotFoundBoardItemException(ExceptionMessage.NF_BOARD_ITEM);

        // 조회수 증가
        boardItem.setViewCount(boardItem.getViewCount() + 1);

        return boardItemRepository.save(boardItem);
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
    public BoardItem postBoardItem(HashMap<String, String> newBoardItem) {
        BoardItem boardItem = new BoardItem();
        boardItem.setUserId(newBoardItem.get("userId"));
        boardItem.setUserPw(newBoardItem.get("userPw"));
        boardItem.setSalt(newBoardItem.get("salt"));
        boardItem.setTitle(newBoardItem.get("title"));
        boardItem.setContent(newBoardItem.get("content"));
        boardItem.setPublishedAt(LocalDateTime.now().plusHours(9));

        return boardItemRepository.save(boardItem);
    }
}
