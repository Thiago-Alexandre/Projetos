package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import model.Contato;
import util.JPAConnectionFactory;

public class ContatoDAO {

    public ContatoDAO() {}
    
    public String adicionar(Contato contato) {
        EntityManager em = JPAConnectionFactory.getEntityManager();
        em.getTransaction().begin();
        em.persist(contato);
        em.getTransaction().commit();
        em.close();
        return "Contato cadastrado com sucesso!";
    }

    public String remover(Contato contato) {
        EntityManager em = JPAConnectionFactory.getEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(contato));
        em.getTransaction().commit();
        em.close();
        return "Contato removido com sucesso!";
    }

    public String atualizar(Contato contato) {
        EntityManager em = JPAConnectionFactory.getEntityManager();
        em.getTransaction().begin();
        em.merge(contato);
        em.getTransaction().commit();
        em.close();
        return "Contato alterado com sucesso!";
    }

    public List<Contato> listar() {
        EntityManager em = JPAConnectionFactory.getEntityManager();
        String jpql = "select c from Contato c order by c.nome";
        TypedQuery<Contato> query = em.createQuery(jpql, Contato.class);
        List<Contato> lista;
        try {
            lista =  query.getResultList();
	} catch (NoResultException ex) {
            System.out.println("Erro! " + ex);
            lista = null;
	} finally{
            em.close();
        }
        return lista;
    }

    public Contato buscarPorId(Integer id) {
        Contato contato = null;
        EntityManager em = JPAConnectionFactory.getEntityManager();
        String jpql = "select c from Contato c where c.id = :pId";
	TypedQuery<Contato> query = em.createQuery(jpql, Contato.class);
	query.setParameter("pId", id);
	try {
            contato =  query.getSingleResult();
	} catch (NoResultException ex) {
            System.out.println("Erro! " + ex);
	} finally{
            em.close();
        }
        return contato;
    }
}