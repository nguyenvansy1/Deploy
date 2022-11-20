package com.example.becapstone1.repository;

import com.example.becapstone1.model.account.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IAccountRoleRepository extends JpaRepository<AccountRole,Long> {
    @Modifying
    @Query(value = "insert into account_role(account_id,role_id) values (:id,2)", nativeQuery = true)
    void addAccountRole(@Param("id") Long id);

}
