package referencia.persistencia.core;

import static javax.ejb.TransactionAttributeType.REQUIRED;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.TypedQuery;

import hammerstone.core.persistence.DaoException;
import lombok.extern.slf4j.Slf4j;
import referencia.dominio.core.BaseEntity;
import referencia.persistencia.excecoes.RepoException;

/**
 * Provider de persistencia usando JPA
 *
 * @author Sibinel
 */
@SuppressWarnings("rawtypes")
@Slf4j
public abstract class AbstractRepositorio<T extends BaseEntity> {

    private static final String ERRO = "Error: {}";

    private Class<T> clazz;

    /**
     * Construtor.
     */
    @SuppressWarnings("unchecked")
    public AbstractRepositorio() {

        log.debug("__¢ DaoJpa");

        this.clazz = (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * @return the getEntityManager
     */
    public abstract EntityManager getEntityManager();

    /** {@inheritDoc} */
    @TransactionAttribute(REQUIRED)
    public T salvar(T entity) {

        getEntityManager().persist(entity);

        return entity;

    }

    /** {@inheritDoc} */
    public void excluir(T entity) throws DaoException {

        try {

            getEntityManager().remove(entity);

        } catch (Exception e) {

            log.error(ERRO, entity, e);

            throw new DaoException(e);
        }
    }

    public void excluir(Serializable id) throws RepoException {

        try {

            T t = buscar(id);

            excluir(t);

        } catch (DaoException e) {

            throw new RepoException(e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @throws RepoException
     */
    @TransactionAttribute(REQUIRED)
    public T atualizar(T entity) throws RepoException {

        try {

            return getEntityManager().merge(entity);

        } catch (Exception e) {

            log.error(ERRO, entity, e);

            throw new RepoException(e);
        }
    }

    /** {@inheritDoc} */
    public void flushAndClean() {

        getEntityManager().flush();

        getEntityManager().clear();
    }

    /** {@inheritDoc} */
    public void fecharEntityManager() {

        if (getEntityManager() != null) {

            getEntityManager().close();
        }
    }

    public List<T> listarTodos() throws RepoException {

        return listar(clazz.getSimpleName() + ".listar", new HashMap<>());
    }

    /**
     * {@inheritDoc}
     * 
     * @throws RepoException
     */
    public List<T> listar(String namedQuery, Map<String, Object> map) throws RepoException {

        return construirQuery(namedQuery, map).getResultList();
    }

    /** {@inheritDoc} */
    public List<T> listar(String namedQuery, Map<String, Object> map, int pageNumber, int pageSize) throws RepoException {

        return construirQuery(namedQuery, map).setFirstResult(pageNumber * pageSize).setMaxResults(pageSize).getResultList();
    }

    /** {@inheritDoc} */
    public T buscar(String namedQuery, Map<String, Object> map) throws RepoException {

        return construirQuery(namedQuery, map).getSingleResult();
    }

    /** {@inheritDoc} */
    public T buscar(Serializable pk) throws RepoException {

        try {
            return getEntityManager().find(clazz, pk);
        } catch (Exception e) {
            log.error("Error: {} : {} - {}", new Object[] { clazz.getName(), pk, e });
            throw new RepoException(e);
        }
    }

    /**
     * Cria um TypedQuery a partir da named associada ao entity, e carrega os
     * parametros do mapa.
     *
     * @param <T>
     *            Type da classe do entity.
     * @param clazz
     *            classe do entity
     * @param namedQuery
     *            Named query para contrução do TypedQuery
     * @param map
     *            Mapa com parametros para Named.
     * @return TypedQuery TypedQuery relacionada a Entity
     * @throws RepoException
     *             Erro de criação.
     */
    protected TypedQuery<T> construirQuery(String namedQuery, Map<String, Object> map) throws RepoException {

        TypedQuery<T> typed = getEntityManager().createNamedQuery(namedQuery, clazz);

        if (typed == null) {

            log.error("__¢ query not found: {}", namedQuery);

            throw new RepoException("query not found: " + namedQuery);
        }

        Set<Parameter<?>> paramSet = typed.getParameters();

        if (!paramSet.isEmpty() && map == null) {

            log.error("__¢ no room (map) for paramSet.size: {}", paramSet.size());

            throw new RepoException("no room (map) for paramSet.size: " + paramSet.size());
        }

        for (Iterator<Parameter<?>> iter = paramSet.iterator(); iter.hasNext();) {

            construirParametros(map, typed, iter);
        }

        log.debug("__¢ typedQuery: {}", typed);

        return typed;
    }

    private void construirParametros(Map<String, Object> map, TypedQuery<T> typed, Iterator<Parameter<?>> iter) throws RepoException {

        Parameter<?> param = iter.next();

        String paramName = param.getName();

        log.debug("___¢ paramName: {}", paramName);

        if (!map.containsKey(paramName)) {

            log.error("__¢ parameter {} not found in Map.", paramName);

            throw new RepoException("parameter '" + paramName + "' not found in Map.");
        }

        Object obj = map.get(paramName);

        if (!param.getParameterType().isAssignableFrom(obj.getClass())) {

            log.error("__¢ paramName '{}' is '{}' but i need '{}'", paramName, obj.getClass().getName(), param.getParameterType().getName());

            throw new RepoException("paramName '" + paramName + "' has wrong type '" + param.getClass().getName() + "'.");
        }

        typed.setParameter(paramName, map.get(paramName));
    }
}
