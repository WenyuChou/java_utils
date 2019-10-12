package com.zhou.utils.repository;

import com.zhou.utils.repository.database.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author : Wenyu Zhou
 * @version : v1.0
 * @date : 2019/7/31
 * description : 描述
 */
public interface UserRepository extends JpaRepository<User,Long> {

}
