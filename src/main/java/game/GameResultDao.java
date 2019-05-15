package game;


import com.google.inject.persist.Transactional;
import util.jpa.GenericJpaDao;

import java.util.List;

public class GameResultDao extends GenericJpaDao {


    public GameResultDao() {
        super(GameResult.class);
    }

    @Transactional
    public List<GameResult> findMostRecent(int n) {
        return entityManager.createQuery("SELECT r FROM GameResult r ORDER BY r.created DESC, r.point DESC", GameResult.class)
                .setMaxResults(n)
                .getResultList();
    }


}
