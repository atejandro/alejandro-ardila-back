package com.atejandro.examples.repository;

import com.atejandro.examples.model.Cube;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Created by atejandro on 13/06/17.
 */
@Repository
@Transactional
public class CubeRepository extends AbstractRepository {

    @Autowired
    public CubeRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Long save(Cube cube){
        cube.refreshCubeContent();
        return (Long) session().save(cube);
    }

    public Optional<Cube> findById(Long id){
        return session().byId(Cube.class).loadOptional(id);
    }

    public List<Cube> list(Long userId){
        Query query =
                session().createQuery("select from Cube c where c.user.id = :userId");
        query.setParameter("userId", userId);
        return query.list();
    }

    public void update(Cube cube){
        cube.refreshCubeContent();
        session().update(cube);
    }

    public void delete(Cube cube) {
        session().delete(cube);
    }
}
