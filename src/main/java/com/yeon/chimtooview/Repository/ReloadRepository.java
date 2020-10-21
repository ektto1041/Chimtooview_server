package com.yeon.chimtooview.Repository;

import com.yeon.chimtooview.Entity.Reload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReloadRepository extends JpaRepository<Reload, Long> {
}
