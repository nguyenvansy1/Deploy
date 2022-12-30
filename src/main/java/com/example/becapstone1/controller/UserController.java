package com.example.becapstone1.controller;

import com.example.becapstone1.model.user.Class;
import com.example.becapstone1.model.user.Course;
import com.example.becapstone1.model.user.Majors;
import com.example.becapstone1.model.user.User;
import com.example.becapstone1.payload.response.ResponseMessage;
import com.example.becapstone1.repository.IUserRepository;
import com.example.becapstone1.service.Impl.ClassService;
import com.example.becapstone1.service.Impl.CourseService;
import com.example.becapstone1.service.Impl.MajorsService;
import com.example.becapstone1.service.Impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UserController
 *
 * <p>Version 1.0
 *
 * <p>Date: 06-09-2022
 *
 * <p>Copyright
 *
 * <p>Modification Logs:
 * DATE             AUTHOR      DESCRIPTION
 * ----------------------------------------
 * 06-09-2022       SyNguyen     Create
 */

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private MajorsService majorsService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ClassService classService;


    /** Get list user. */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<Page<User>> getAllUser(@RequestParam("page") Integer page,
                                                 @RequestParam("size") Integer size) {
        try {
            Page<User> userList = userService.getAllUser(page, size);
            return new ResponseEntity<>(userList,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    /** Update user. */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/update")
    public ResponseEntity<ResponseMessage> updateUser( @RequestBody User user) {
            User user1 = userRepository.findUserByIdCard(user.getIdentityCard(),user.getCode());
            if (user1 == null) {
                userService.updateUser(user);
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(new ResponseMessage("Identity card already exists!"),HttpStatus.BAD_REQUEST);
            }
    }



    /** Block user by account id. */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/block/{id}")
    public ResponseEntity<?> blockUser(@PathVariable("id") Integer accountId) {
        try {
            userService.blockUser(accountId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /** Un Block user by account id. */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/unblock/{id}")
    public ResponseEntity<?> unBlockUser(@PathVariable("id") Integer accountId) {
        try {
            userService.unBlockUser(accountId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /** Find user by id. */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @GetMapping("/find/{code}")
    public ResponseEntity<?> findUserByCode(@PathVariable("code") Long code) {
        try {
            User user =  userService.findUserByCode(code);
            return new ResponseEntity<>(user,HttpStatus.OK);
        }catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /** Get list course **/
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/course")
    public ResponseEntity<List<Course>> getAllCourse() {
        List<Course> courseList = courseService.findAllCourse();
        return new ResponseEntity<>(courseList,HttpStatus.OK);
    }

    /** Get list majors **/
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/majors")
    public ResponseEntity<List<Majors>> getAllMajors() {
        List<Majors> majorsList = majorsService.findAllMajors();
        return new ResponseEntity<>(majorsList,HttpStatus.OK);
    }

    /** Get list class **/
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/class")
    public ResponseEntity<List<Class>> getAllClass() {
        List<Class> classList = classService.findAllClass();
        return new ResponseEntity<>(classList,HttpStatus.OK);
    }

    /** Find user by name or code. */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/filter")
    public ResponseEntity<Page<User>> getAllUserByCodeOrName(@RequestParam("page") Integer page,
                                                             @RequestParam("size") Integer size , @RequestParam("name") String name) {
        Page<User> users = userService.getByCodeOrName(name,page,size);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /** Get data user join event by month. */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/dataUser")
    public ResponseEntity<?> getData() {
        Integer[] data = userService.getDataUser();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    /** Get data amount user. */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/amountUser")
    public ResponseEntity<?> getAmountUser() {
        Integer amountUser = userService.getAmountUser();
        return new ResponseEntity<>(amountUser, HttpStatus.OK);
    }

    /** Get list user checkin successfully by event. */
    @GetMapping("/userCheckin")
    public ResponseEntity<?> getListUserCheckin(@RequestParam("id") Long id) {
        try {
            List<User> userList = userService.findUserCheckinByEventId(id);
            return new ResponseEntity<>(userList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    /** Update avatar user. */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/updateAvatar")
    public ResponseEntity<?> updateAvatar(@RequestParam("avatar") String avatar,
                                          @RequestParam("code") Long code) {
        try {
            userService.updateAvatar(avatar, code);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
//    /** Find user by code ANDROID. */
//    @GetMapping("/find1/{code}")
//    public ResponseEntity<?> findUserByCode1(@PathVariable("code") Long code) {
//        try {
//            User user =  userService.findUserByCode(code);
//            return new ResponseEntity<>(user,HttpStatus.OK);
//        }catch (Exception e)
//        {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
}