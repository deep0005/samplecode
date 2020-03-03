package com.mindgeek.samplecode.repository;

import com.mindgeek.samplecode.domainobject.User;
import com.mindgeek.samplecode.domainvalue.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByUsernameAndTypeAndIsDeleted(String username, UserType type, Boolean isDeleted);

    User findByUsernameAndIsDeleted(String username, Boolean isDeleted);

    User findByIdAndIsDeleted(Long Id, Boolean isDeleted);

    User findByIsDeletedAndTypeAndId(Boolean isDeleted, UserType type, Long id);
}
