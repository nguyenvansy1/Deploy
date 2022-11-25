package com.example.becapstone1.service.Impl;

import com.example.becapstone1.exception.UserNotFoundException;
import com.example.becapstone1.model.user.User;
import com.example.becapstone1.repository.IUserRepository;
import com.example.becapstone1.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;


    @Override
    public Page<User> getAllUser(Integer page, Integer size) {
        Pageable paging = PageRequest.of(page, size, Sort.by("code").descending());
        return userRepository.findAll(paging);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void blockUser(Integer accountId) {
        userRepository.blockUser(accountId);
    }

    @Override
    public void unBlockUser(Integer accountId) {
        userRepository.unBlockUser(accountId);
    }

    @Override
    public User findUserByCode(Long code) {
        return userRepository.findById(code).orElseThrow(() -> new UserNotFoundException("User by id " + code + " was not found"));
    }

    @Override
    public Page<User> getByCodeOrName( String name, Integer page, Integer size) {
        Pageable paging = PageRequest.of(page, size);
        return userRepository.findByCodeContainingOrNameContaining("%"+name+"%",paging);
    }

    @Override
    public Integer[] getDataUser() {
        Integer[][] data = userRepository.getDataUser();
        Integer[] arr = new Integer[12];
        for (int i = 1 ; i<=12; i++){
            for(int row = 0; row < data.length; row++) {
                for(int column = 0; column < data[row].length-1; column++) {
                    if (i == data[row][column]) {
                        arr[i-1] = data[row][column+1];
                    }
                }
            }
        }
        for (int i = 0 ; i<12; i++) {
            if (arr[i] == null){
                arr[i] = 0;
            }
        }
        return arr;
    }

    @Override
    public Integer getAmountUser() {
        Integer amountUser = userRepository.getAmountUser().length;
        return amountUser;
    }

    @Override
    public List<User> findUserCheckinByEventId(Long id) {
        return userRepository.findUserCheckinByEventId(id);
    }

    @Override
    public List<User> getListUserByEvent(Long id) {
        return userRepository.getListUserByEvent(id);
    }

    @Override
    public void updateAvatar(String avatar, Long code) {
        userRepository.updateAvatar(avatar, code);
    }
}
