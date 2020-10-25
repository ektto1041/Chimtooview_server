package com.yeon.chimtooview.Repository;

import com.yeon.chimtooview.Entity.BoardItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardItemRepository extends JpaRepository<BoardItem, Long> {
}
