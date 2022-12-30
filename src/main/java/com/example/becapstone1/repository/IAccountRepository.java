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
    @Query(value = "SELECT * FROM accounts join account_roles on accounts.account_id = account_roles.account_id WHERE accounts.account_username =?1 and account_flag = 1", nativeQuery = true)
    Account findAccountByUsername(String username);


    @Query(value = "SELECT * FROM accounts WHERE accounts.account_username =?1 ", nativeQuery = true)
    Account findAccountByUsername1(String username);

    @Modifying
    @Query(value = "UPDATE accounts SET account_password = ?2 WHERE account_id = ?1", nativeQuery = true)
    void changePassword(Long id,String password);

    @Query(value = "SELECT * FROM accounts WHERE account_id = :id AND account_flag = 1", nativeQuery = true)
    Optional<Account> findAccountById(@Param("id") Long id);

    Boolean existsByUsername(String username);

    @Query(value = "select * from accounts where account_username = ?1",nativeQuery = true)
    Optional<Account> findByUsername(String username);

    @Modifying
    @Query(value ="update accounts set verification_code =?1 where account_username =?2",nativeQuery = true)
    void addVerificationCode(String code, String username);

    Account findAccountByVerificationCode(String code);

    @Modifying
    @Query(value = "update accounts set account_password =?1 where verification_code=?2 ",nativeQuery = true)
    void saveNewPassword(String password, String code);


    @Query(value = "SELECT * FROM accounts join account_roles on accounts.account_id = account_roles.account_id WHERE accounts.account_username =?1 and account_roles.role_id = 2", nativeQuery = true)
    Optional<Account> findByUsername1(String username);
}
