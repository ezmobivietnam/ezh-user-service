package vn.com.ezmobi.ezhealth.ezhuserservice.repositories;

import vn.com.ezmobi.ezhealth.ezhuserservice.domain.User;
import vn.com.ezmobi.framework.repositories.BaseEntityRepository;

import java.util.List;

public interface UserRepository extends BaseEntityRepository<User, Integer> {

    List<User> findAllByEmail(String email);
}