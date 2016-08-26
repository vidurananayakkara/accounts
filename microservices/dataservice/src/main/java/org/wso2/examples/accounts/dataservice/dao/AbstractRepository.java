/*
*  Copyright (c) 2005-2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/

package org.wso2.examples.accounts.dataservice.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

/**
 * Abstract Repository class.
 *
 * @param <T>
 */
public abstract class AbstractRepository<T> {

    /**
     * Entity manager factory reference.
     */
    private EntityManagerFactory emf;

    /**
     * AbstractRepository constructor.
     *
     * @param entityManagerFactory EntityManagerFactory
     */
    public AbstractRepository(final EntityManagerFactory entityManagerFactory) {

        this.emf = entityManagerFactory;
    }

    /**
     * Get entity manager.
     *
     * @return EntityManager
     */
    protected final EntityManager getEntityManager() {

        return emf.createEntityManager();
    }

    /**
     * Save entity.
     *
     * @param t entity
     */
    protected void create(final T t) {

        EntityManager manager = beginTransactionAndGetEntityManager();
        manager.persist(t);
        commitTransaction(manager);
    }

    /**
     * Remove entity from database.
     *
     * @param t entity
     */
    protected void remove(final T t) {

        EntityManager manager = beginTransactionAndGetEntityManager();
        manager.remove(manager.contains(t) ? t : manager.merge(t));
        commitTransaction(manager);
    }

    /**
     * Find a specific entity from database.
     *
     * @param classType type of the entity
     * @param id        id integer
     * @return T of entity type
     */
    protected T find(final Class<T> classType, final int id) {
        return getEntityManager().find(classType, id);
    }

    /**
     * Find the entity by a specific field and field value.
     *
     * @param entityClass entity class type
     * @param field       String
     * @param fieldValue  String
     * @return
     */
    public List<T> findByField(final Class<T> entityClass, String field, Object fieldValue) {

        EntityManager manager = getEntityManager();
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);
        criteriaQuery.select(root);

        ParameterExpression<Object> params = criteriaBuilder.parameter(Object.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get(field), params));

        TypedQuery<T> query = manager.createQuery(criteriaQuery);
        query.setParameter(params, fieldValue);

        return query.getResultList();
    }

    /**
     * Find all entities from database.
     *
     * @param classType type of the class
     * @return List<T> results
     */
    protected List<T> findAll(final Class<T> classType) {

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(classType);
        Root<T> rootEntry = cq.from(classType);
        CriteriaQuery<T> all = cq.select(rootEntry);
        TypedQuery<T> allQuery = getEntityManager().createQuery(all);

        return allQuery.getResultList();
    }

    /**
     * Begin entity manage transaction.
     *
     * @return EntityManager
     */
    private EntityManager beginTransactionAndGetEntityManager() {

        EntityManager manager = getEntityManager();
        manager.getTransaction().begin();

        return manager;
    }

    /**
     * Commit an entity manager transaction.
     *
     * @param manager EntityManager
     */
    private void commitTransaction(EntityManager manager) {

        manager.getTransaction().commit();
        manager.close();
    }
}
