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
    @Query(value = "select * from events where event_flag = 1",nativeQuery = true)
    Page<Event> findAll(Pageable pageable);

    @Query(value = "select month(event_start_time) as Month , count(month(event_start_time)) as Times from events\n" +
            "where year(event_start_time) = year(curdate())\n" +
            "group by month(event_start_time)\n" +
            "order by month(event_start_time);", nativeQuery = true)
    Integer[][] getDataEvent();

    @Query(value = "select * from events where event_end_time <= CONVERT_TZ(now(),'+00:00','+7:00');", nativeQuery = true)
    List<Event> getListEventFinished();

    @Query(value = "select * from events where event_end_time >= CONVERT_TZ(now(),'+00:00','+7:00');", nativeQuery = true)
    List<Event> getListEventUpcoming();

    @Modifying
    @Query(value = "update events set event_flag=0 where event_id=:id", nativeQuery = true)
    void deleteEventByFlag(@Param("id") Long id);

    @Query(value = "select * from events where event_customer_id = :id and event_start_time <= CONVERT_TZ(now(),'+00:00','+7:00') and event_end_time >= CONVERT_TZ(now(),'+00:00','+7:00');", nativeQuery = true)
    Event getEventCheckin(@Param("id") Long id);

    @Query(value = "select * from events order by event_start_time DESC;", nativeQuery = true)
    List<Event> getListEvent();
}
