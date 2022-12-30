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

    @Query(value = "Select * from users where user_code like :name or user_name like BINARY :name", nativeQuery = true)
    Page<User> findByCodeContainingOrNameContaining(@Param("name") String name, Pageable pageable);

    @Query(value = "select month(event_end_time) as Month , count(event_end_time) as Times  from event_users\n" +
            "left join events on event_users.event_id = events.event_id\n" +
            "where year(event_end_time) = year(curdate())\n" +
            "group by month(event_end_time)\n" +
            "order by month(event_end_time);", nativeQuery = true)
    Integer[][] getDataUser();

    @Query(value = "select count(user_id) as Amount_Student from event_users\n" +
            "group by user_id;", nativeQuery = true)
    Integer[] getAmountUser();

    @Modifying
    @Query(value = "update accounts set account_flag = 0 where account_id = :id", nativeQuery = true)
    void blockUser(@Param("id") Integer id);

    @Modifying
    @Query(value = "update accounts set account_flag = 1 where account_id = :id", nativeQuery = true)
    void unBlockUser(@Param("id") Integer id);

    @Query(value = "SELECT user_code , user_address,user_birth_day,user_gender,user_identity_card,user_name,user_phone ,user_since,user_account_id,user_class_id,user_course_id,user_majors_id,user_status   FROM users join event_users on users.user_code = event_users.user_id\n" +
            "where event_users.event_user_status = 1 and event_users.event_id = :id\n" +
            "group by user_code;", nativeQuery = true)
    List<User> findUserCheckinByEventId(@Param("id") Long id);

    @Query(value = "select users.user_code, users.user_address, users.user_birth_day,users.user_gender, users.user_identity_card, users.user_name , users.user_phone , users.user_since, users.user_account_id, users.user_class_id, users.user_course_id, users.user_majors_id, users.user_avatar  from users\n" +
            "join event_users on users.user_code = event_users.user_id \n" +
            "where event_users.event_id = :id and event_users.event_user_status = 1", nativeQuery = true)
    List<User> getListUserByEvent(@Param("id") Long id);

    @Modifying
    @Query(value = "UPDATE users SET user_avatar = :avatar WHERE user_code = :code", nativeQuery = true)
    void updateAvatar(@Param("avatar") String avatar ,@Param("code") Long code );

    @Query(value = "select * from users where user_identity_card = :id && user_code != :code", nativeQuery = true)
    User findUserByIdCard(@Param("id") Integer id, @Param("code") Long code);
}


