package com.yeon.chimtooview.Repository;

import com.yeon.chimtooview.Entity.CommentItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentItemRepository extends JpaRepository<CommentItem, Long> {
}
