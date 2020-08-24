package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import model.Compromisso;
import model.Contato;
import model.Usuario;
import util.JPAConnectionFactory;

public class CompromissoDAO {

    public CompromissoDAO() {}
    
    public String adicionar(Compromisso compromisso){
        Compromisso aux = buscarPorData(compromisso.getData(), compromisso.getUsuario());
        if (aux == null) {
            EntityManager em = JPAConnectionFactory.getEntityManager();
            em.getTransaction().begin();
            em.persist(compromisso);
            em.getTransaction().commit();
            em.close();
            return "Compromisso cadastrado com sucesso!";    
        }
        return "Já existe um compromisso nesta Data e Hora!";
    }
    
    public String atualizar(Compromisso compromisso){
        Compromisso aux = buscarPorId(compromisso.getId());
        if (compromisso.getData().equals(aux.getData())) {
            return alterar(compromisso);    
        }
        aux = buscarPorData(compromisso.getData(), compromisso.getUsuario());
        if (aux == null) {
            return alterar(compromisso);
        }
        return "Já existe um compromisso nesta Data e Hora!";
    }
    
    private String alterar(Compromisso compromisso){
        EntityManager em = JPAConnectionFactory.getEntityManager();
        em.getTransaction().begin();
        em.merge(compromisso);
        em.getTransaction().commit();
        em.close();
        return "Compromisso alterado com sucesso!";
    }
    
    public String remover(Compromisso compromisso){
        EntityManager em = JPAConnectionFactory.getEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(compromisso));
        em.getTransaction().commit();
        em.close();
        return "Compromisso removido com sucesso!";
    }
    
    public List<Compromisso> listar(Usuario usuario){
        EntityManager em = JPAConnectionFactory.getEntityManager();
        String jpql = "select c from Compromisso c where c.usuario = :pUser order by c.data";
        TypedQuery<Compromisso> query = em.createQuery(jpql, Compromisso.class);
        query.setParameter("pUser", usuario);
        List<Compromisso> lista;
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
    
    public Compromisso buscarPorId(Integer id) {
        Compromisso compromisso = null;
        EntityManager em = JPAConnectionFactory.getEntityManager();
        String jpql = "select c from Compromisso c where c.id = :pId";
	TypedQuery<Compromisso> query = em.createQuery(jpql, Compromisso.class);
	query.setParameter("pId", id);
	try {
            compromisso =  query.getSingleResult();
	} catch (NoResultException ex) {
            System.out.println("Erro! " + ex);
	} finally{
            em.close();
        }
        return compromisso;
    }
    
    public Compromisso buscarPorData(Date data, Usuario usuario) {
        Compromisso compromisso = null;
        EntityManager em = JPAConnectionFactory.getEntityManager();
        String jpql = "select c from Compromisso c where c.data = :pData and c.usuario = :pUser";
	TypedQuery<Compromisso> query = em.createQuery(jpql, Compromisso.class);
	query.setParameter("pData", data);
        query.setParameter("pUser", usuario);
	try {
            compromisso =  query.getSingleResult();
	} catch (NoResultException ex) {
            System.out.println("Erro! " + ex);
	} finally{
            em.close();
        }
        return compromisso;
    }
    
    public String excluirParticipante(Compromisso compromisso){
        alterar(compromisso);
        return "Contato removido do compromisso!";
    }
    
    public String adicionarParticipante(Compromisso compromisso){
        alterar(compromisso);
        return "Contato adicionado ao compromisso!";
    }
    
    public List<Contato> carregarParticipantes(Compromisso compromisso){
        if (compromisso.getId() != null) {
            EntityManager em = JPAConnectionFactory.getEntityManager();
            compromisso = em.find(Compromisso.class, compromisso.getId());
            Collections.sort(compromisso.getLista());
            em.close();   
        }
        return compromisso.getLista();
    }
    
    public List<Contato> novosParticipantes(Compromisso compromisso){
        List<Contato> todos = new ContatoDAO().listar(compromisso.getUsuario());
        if (!compromisso.getLista().isEmpty()) {
            List<Contato> repetidos = new ArrayList();
            for(Contato contato : todos){
                for(Contato participante : compromisso.getLista()){
                    if (contato.getId() == participante.getId()) {
                        repetidos.add(contato);
                    }
                }
            }
            todos.removeAll(repetidos);    
        }
        return todos;
    }
}