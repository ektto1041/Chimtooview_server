package com.yeon.chimtooview.Service;

import com.yeon.chimtooview.Entity.BoardItem;
import com.yeon.chimtooview.Entity.CommentItem;
import com.yeon.chimtooview.Entity.QCommentItem;
import com.yeon.chimtooview.Exceptions.ExceptionMessage;
import com.yeon.chimtooview.Exceptions.NotFoundCommentItemException;
import com.yeon.chimtooview.Repository.CommentItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
public class CommentItemService extends QuerydslRepositorySupport {
    public CommentItemService() {super(CommentItem.class);}

    @Autowired
    private CommentItemRepository commentItemRepository;

    /**
     *  GET
     */

    /**
     * 댓글 정렬/페이징 해서 가져오기
     * @param pageCurrent
     * BoardItem, int
     * @return
     * CommentItem List
     */
    public List<CommentItem> getCommentItemAllOrderByPaging(BoardItem boardItem, int pageCurrent) {
        QCommentItem qCommentItem = QCommentItem.commentItem;

        List<CommentItem> commentItemList = from(qCommentItem).where(qCommentItem.boardItem.eq(boardItem)).orderBy(qCommentItem.publishedAt.asc()).offset(20 * (pageCurrent-1)).limit(20).fetch();

        return commentItemList;
    }


    /**
     *  POST
     */

    /**
     * 댓글 쓰기
     * @param newCommentItem
     * Object {userId, userPw, content, salt, boardItemId} - String
     * @param boardItem
     * BoardItem - Controller에서 가져오는 작업 따로 진행
     * @return
     * CommentItem
     */
    public CommentItem postCommentItem(HashMap<String, String> newCommentItem, BoardItem boardItem) {
        CommentItem commentItem = new CommentItem();

        commentItem.setUserId(newCommentItem.get("userId"));
        commentItem.setUserPw(newCommentItem.get("userPw"));
        commentItem.setContent(newCommentItem.get("content"));
        commentItem.setSalt(newCommentItem.get("salt"));
        commentItem.setPublishedAt(LocalDateTime.now().plusHours(9));
        commentItem.setBoardItem(boardItem);

        return commentItemRepository.save(commentItem);
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
    public void deleteCommentItemById(long id) throws NotFoundCommentItemException {
        CommentItem commentItem = commentItemRepository.findById(id).orElse(null);
        if(commentItem == null) throw new NotFoundCommentItemException(ExceptionMessage.NF_COMMENT_ITEM);

        commentItemRepository.delete(commentItem);
    }
}
