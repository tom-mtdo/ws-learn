package mtdo.learn.jee.addressbook.ejb;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public abstract class AbstractFacade<T> {
    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    public abstract EntityManager getEntityManager();
    
    public void create(T entity){
        getEntityManager().persist(entity);
    }
    
    public void edit(T entity){
        getEntityManager().merge(entity);
    }
    
    public void remove(T entity){
        getEntityManager().remove(entity);
    }
    
    public T find (Object id){
        return getEntityManager().find(entityClass, id);
    }
    
    public List<T> findAll(){
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));        
        return getEntityManager().createQuery(cq).getResultList();
    }
    
    public List<T> findRange(int[] range){
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));        
        Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1]-range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
    
    public int count(){
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<T> root = cq.from(entityClass);
        cq.select(cb.count(root));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
        
//        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
//        Root<T> root = cq.from(entityClass);
//        cq.select(getEntityManager().getCriteriaBuilder().count(root));
//        Query q = getEntityManager().createQuery(cq);
//        return ((Long) q.getSingleResult()).intValue();
    }
}
