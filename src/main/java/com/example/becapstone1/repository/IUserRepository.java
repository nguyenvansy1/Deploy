package com.example.becapstone1.repository;

import com.example.becapstone1.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface IUserRepository extends JpaRepository<User,Long> {

    Page<User> findAll(Pageable pageable);

    @Query(value = "Select * from user where user_code like :name or user_name like BINARY :name", nativeQuery = true)
    Page<User> findByCodeContainingOrNameContaining(@Param("name") String name, Pageable pageable);

    @Query(value = "select month(event_end_time) as Month , count(event_end_time) as Times  from event_user\n" +
            "left join event on event_user.event_id = event.event_id\n" +
            "where year(event_end_time) = year(curdate())\n" +
            "group by month(event_end_time)\n" +
            "order by month(event_end_time);", nativeQuery = true)
    Integer[][] getDataUser();

    @Query(value = "select count(user_id) as Amount_Student from event_user\n" +
            "group by user_id;", nativeQuery = true)
    Integer[] getAmountUser();

    @Modifying
    @Query(value = "update account set account_flag = 0 where account_id = :id", nativeQuery = true)
    void blockUser(@Param("id") Integer id);

    @Modifying
    @Query(value = "update account set account_flag = 1 where account_id = :id", nativeQuery = true)
    void unBlockUser(@Param("id") Integer id);

    @Query(value = "SELECT user_code , user_address,user_birth_day,user_gender,user_identity_card,user_name,user_phone ,user_since,user_account_id,user_class_id,user_course_id,user_majors_id,user_status   FROM user join event_user on user.user_code = event_user.user_id\n" +
            "where event_user.event_user_status = 1 and event_user.event_id = :id\n" +
            "group by user_code;", nativeQuery = true)
    List<User> findUserCheckinByEventId(@Param("id") Long id);

    @Query(value = "select user.user_code, user.user_address, user.user_birth_day,user.user_gender, user.user_identity_card, user.user_name , user.user_phone , user.user_since, user.user_account_id, user.user_class_id, user.user_course_id, user.user_majors_id, user.user_avatar  from user\n" +
            "join event_user on user.user_code = event_user.user_id \n" +
            "where event_user.event_id = :id and event_user.event_user_status = 1", nativeQuery = true)
    List<User> getListUserByEvent(@Param("id") Long id);

    @Modifying
    @Query(value = "UPDATE user SET user_avatar = :avatar WHERE user_code = :code", nativeQuery = true)
    void updateAvatar(@Param("avatar") String avatar ,@Param("code") Long code );

}


