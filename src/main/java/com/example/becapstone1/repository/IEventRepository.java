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

    @Query(value = "select month(event_date) as Month , count(month(event_date)) as Times from event\n" +
            "where year(event_date) = year(curdate())\n" +
            "group by month(event_date)\n" +
            "order by month(event_date);", nativeQuery = true)
    Integer[][] getDataEvent();

    @Query(value = "select * from event \n" +
            "where IF(event_date = date(now()) , event_end_time < curtime() , event_date < date(now()));", nativeQuery = true)
    List<Event> getListEventFinished();

    @Query(value = "select * from event \n" +
            "where IF(event_date = date(now()) , event_end_time > curtime() , event_date > date(now()));", nativeQuery = true)
    List<Event> getListEventUpcoming();

    @Modifying
    @Query(value = "update event set event_flag=0 where event_id=:id", nativeQuery = true)
    void deleteEventByFlag(@Param("id") Long id);

}
