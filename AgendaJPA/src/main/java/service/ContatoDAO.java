package service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import model.Contato;
import model.Usuario;
import model.Compromisso;
import util.JPAConnectionFactory;

public class ContatoDAO<T> {

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
        if (!verificar(contato)) {
            EntityManager em = JPAConnectionFactory.getEntityManager();
            em.getTransaction().begin();
            em.remove(em.merge(contato));
            em.getTransaction().commit();
            em.close();
            return "Contato removido com sucesso!";
        }
        return "Não foi possível remover! Contato marcado em Compromisso!";
    }

    public String atualizar(Contato contato) {
        EntityManager em = JPAConnectionFactory.getEntityManager();
        em.getTransaction().begin();
        em.merge(contato);
        em.getTransaction().commit();
        em.close();
        return "Contato alterado com sucesso!";
    }

    public List<Contato> listar(Usuario usuario) {
        EntityManager em = JPAConnectionFactory.getEntityManager();
        String jpql = "select c from Contato c where c.usuario = :pUser order by c.nome";
        TypedQuery<Contato> query = em.createQuery(jpql, Contato.class);
        query.setParameter("pUser", usuario);
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
    
    public List<Contato> listar(Usuario usuario, String nome) {
        EntityManager em = JPAConnectionFactory.getEntityManager();
        String jpql = "select c from Contato c where c.usuario = :pUser and c.nome like :pNome order by c.nome";
        TypedQuery<Contato> query = em.createQuery(jpql, Contato.class);
        query.setParameter("pUser", usuario);
        query.setParameter("pNome", "%"+nome+"%");
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
    
    private boolean verificar(Contato contato){
        List<Compromisso> compromissos = new CompromissoDAO().listar(contato.getUsuario());
        for(Compromisso compromisso : compromissos){
            for(Contato teste : compromisso.getLista()){
                if (teste.getId() == contato.getId()) {
                    return true;
                }
            }
            
        }
        return false;
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