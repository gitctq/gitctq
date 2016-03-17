/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package referencia.persistencia.core;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import lombok.extern.slf4j.Slf4j;
import referencia.dominio.core.BaseEntity;

/**
 *
 * @author 140200
 */
@Slf4j
public class BaseRepositorio<T extends BaseEntity> extends AbstractRepositorio<T> {

    @PersistenceContext(unitName = "br.gov.sp.prodesp.namb")
    private EntityManager eManager;

    public void setEntityManager(EntityManager em) {

        log.debug("entityManager({})", em);

        eManager = em;
    }

    /**
     * @return EntityManager EntityManager
     */
    @Override
    public EntityManager getEntityManager() {

        log.debug("entityManager({})", eManager);

        return eManager;
    }
}
