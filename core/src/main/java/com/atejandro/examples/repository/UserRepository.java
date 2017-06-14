package com.atejandro.examples.repository;

import com.atejandro.examples.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by atejandro on 13/06/17.
 */
@Repository
@Transactional
public class UserRepository extends AbstractRepository {

    @Autowired
    public UserRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Long save(User user){
        return (Long) session().save(user);
    }

    public Optional<User> findById(Long id){
        return session().byId(User.class).loadOptional(id);
    }

    public void update(User user){
        session().update(user);
    }

    public void delete(User user){
        session().delete(user);
    }

    public Optional<User> findByNaturalId(String email){
        return session().byNaturalId(User.class)
                .using("email", email)
                .loadOptional();
    }
}
