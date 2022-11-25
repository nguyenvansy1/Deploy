package com.example.becapstone1.repository;

import com.example.becapstone1.model.event.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
public interface IEventRepository extends JpaRepository<Event,Long> {
    @Query(value = "select * from event where event_flag = 1",nativeQuery = true)
    Page<Event> findAll(Pageable pageable);

    @Query(value = "select month(event_start_time) as Month , count(month(event_start_time)) as Times from event\n" +
            "where year(event_start_time) = year(curdate())\n" +
            "group by month(event_start_time)\n" +
            "order by month(event_start_time);", nativeQuery = true)
    Integer[][] getDataEvent();

    @Query(value = "select * from event where event_end_time < STR_TO_DATE(NOW(), '%Y-%m-%d %H:%i:%s');", nativeQuery = true)
    List<Event> getListEventFinished();

    @Query(value = "select * from event where event_end_time > STR_TO_DATE(NOW(), '%Y-%m-%d %H:%i:%s');", nativeQuery = true)
    List<Event> getListEventUpcoming();

    @Modifying
    @Query(value = "update event set event_flag=0 where event_id=:id", nativeQuery = true)
    void deleteEventByFlag(@Param("id") Long id);

    @Query(value = "select * from event where event_customer_id = :id and event_start_time < current_timestamp() and event_end_time > current_timestamp();", nativeQuery = true)
    Event getEventCheckin(@Param("id") Long id);
}
