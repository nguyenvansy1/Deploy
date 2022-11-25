package com.example.becapstone1.service;

import com.example.becapstone1.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IUserService {
    Page<User> getAllUser(Integer page, Integer size);

    void updateUser(User user);

    void blockUser(Integer accountId);

    void unBlockUser(Integer accountId);

    User findUserByCode(Long code);
    
    Page<User> getByCodeOrName(String name, Integer page, Integer size);

    Integer[] getDataUser();

    Integer getAmountUser();

    List<User> findUserCheckinByEventId(Long id);

    List<User> getListUserByEvent(Long id);

    void updateAvatar(String avatar , Long code );
}
