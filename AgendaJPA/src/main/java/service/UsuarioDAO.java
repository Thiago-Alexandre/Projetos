package service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import model.Usuario;
import util.JPAConnectionFactory;

public class UsuarioDAO {
    
    public UsuarioDAO(){}
    
    public String adicionar(Usuario usuario) {
        Usuario aux = buscarPorLogin(usuario.getLogin());
        if (aux == null) {
            EntityManager em = JPAConnectionFactory.getEntityManager();
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
            em.close();
            return "Usuário cadastrado com sucesso!";
        }
        return "Erro: Login existente!";
    }

    public void remover(Usuario usuario) {
        EntityManager em = JPAConnectionFactory.getEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(usuario));
        em.getTransaction().commit();
        em.close();
    }

    public String atualizar(Usuario usuario) {
        Usuario aux = buscarPorId(usuario.getId());
        if (aux.getLogin().equals(usuario.getLogin())) {
            EntityManager em = JPAConnectionFactory.getEntityManager();
            em.getTransaction().begin();
            em.merge(usuario);
            em.getTransaction().commit();
            em.close();
            return "Usuário alterado com sucesso!";
        }
        aux = buscarPorLogin(usuario.getLogin());
        if (!aux.getLogin().equals(usuario.getLogin())) {
            EntityManager em = JPAConnectionFactory.getEntityManager();
            em.getTransaction().begin();
            em.merge(usuario);
            em.getTransaction().commit();
            em.close();
            return "Usuário alterado com sucesso!";
        }
        return "Login existente!";
    }

    public List<Usuario> listar() {
        EntityManager em = JPAConnectionFactory.getEntityManager();
        String jpql = "select u from Usuario u order by u.login";
        TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
        List<Usuario> lista;
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
    
    public List<Usuario> listar(String login) {
        EntityManager em = JPAConnectionFactory.getEntityManager();
        String jpql = "select u from Usuario u where u.login like :pLogin order by u.login";
        TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
        query.setParameter("pLogin", "%"+login+"%");
        List<Usuario> lista;
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

    public Usuario buscarPorId(Integer id) {
        Usuario usuario = null;
        EntityManager em = JPAConnectionFactory.getEntityManager();
        String jpql = "select u from Usuario u where u.id = :pId";
	TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
	query.setParameter("pId", id);
	try {
            usuario =  query.getSingleResult();
	} catch (NoResultException ex) {
            System.out.println("Erro! " + ex);
	} finally{
            em.close();
        }
        return usuario;
    }
    
    public Usuario buscarPorLogin(String login) {
        Usuario usuario = null;
        EntityManager em = JPAConnectionFactory.getEntityManager();
        String jpql = "select u from Usuario u where u.login = :pLogin";
	TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
	query.setParameter("pLogin", login);
	try {
            usuario =  query.getSingleResult();
	} catch (NoResultException ex) {
            System.out.println("Erro! " + ex);
	} finally{
            em.close();
        }
        return usuario;
    }
    
    public Usuario verificar(Usuario usuario) {
        Usuario logado = null;
        String jpql = "select u from Usuario u where u.login = :pLogin and u.senha = :pSenha";
        EntityManager em = JPAConnectionFactory.getEntityManager();
	TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
	query.setParameter("pLogin", usuario.getLogin());
	query.setParameter("pSenha", usuario.getSenha());
	try {
            logado =  query.getSingleResult();
	} catch (NoResultException ex) {
            System.out.println("Erro ao Logar! " + ex);
	} finally{
            em.close();
        }
        return logado;
    } 
}