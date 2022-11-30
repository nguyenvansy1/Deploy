package com.example.becapstone1.repository;

import com.example.becapstone1.model.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface IAccountRepository extends JpaRepository<Account,Long> {
//    and account_role.role_id = 1
    @Query(value = "SELECT * FROM account join account_role on account.account_id = account_role.account_id WHERE account.account_username =?1 and account_flag = 1", nativeQuery = true)
    Account findAccountByUsername(String username);


    @Query(value = "SELECT * FROM account WHERE account.account_username =?1 ", nativeQuery = true)
    Account findAccountByUsername1(String username);

    @Modifying
    @Query(value = "UPDATE account SET account_password = ?2 WHERE account_id = ?1", nativeQuery = true)
    void changePassword(Long id,String password);

    @Query(value = "SELECT * FROM account WHERE account_id = :id AND account_flag = 1", nativeQuery = true)
    Optional<Account> findAccountById(@Param("id") Long id);

    Boolean existsByUsername(String username);

    @Query(value = "select * from account where account_username = ?1",nativeQuery = true)
    Optional<Account> findByUsername(String username);

    @Modifying
    @Query(value ="update account set verification_code =?1 where account_username =?2",nativeQuery = true)
    void addVerificationCode(String code, String username);

    Account findAccountByVerificationCode(String code);

    @Modifying
    @Query(value = "update account set account_password =?1 where verification_code=?2 ",nativeQuery = true)
    void saveNewPassword(String password, String code);


    @Query(value = "SELECT * FROM account join account_role on account.account_id = account_role.account_id WHERE account.account_username =?1 and account_role.role_id = 2", nativeQuery = true)
    Optional<Account> findByUsername1(String username);
}
