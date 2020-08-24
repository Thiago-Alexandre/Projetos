package controller;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import model.Compromisso;
import model.Contato;
import model.Usuario;
import org.primefaces.event.SelectEvent;
import service.CompromissoDAO;
import service.ContatoDAO;

@ManagedBean
@ViewScoped
public class BeanCompromisso {
    
    private Compromisso compromisso = new Compromisso();
    private List<Compromisso> compromissos = new ArrayList<>();
    private List<Contato> novos = new ArrayList<>();
    private List<Contato> participantes = new ArrayList<>();
    private Integer novoContato;
    
    @PostConstruct
    public void init(){
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getExternalContext().getSessionMap().get("compromisso") != null) {
            compromisso = ((Compromisso) context.getExternalContext().getSessionMap().get("compromisso"));
            compromisso.setLista(new CompromissoDAO().carregarParticipantes(compromisso));
            
        }
        compromisso.setUsuario((Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado"));
    }
    
    //Página index
   
    public List<Compromisso> listar(){
        compromissos = new ArrayList<>();
        compromissos = new CompromissoDAO().listar(compromisso.getUsuario());
        return compromissos;
    }
    
    public String novoCompromisso(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().remove("mensagem");
        return "compromisso?faces-redirect=true";
    }
    
    public String contatos(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().remove("mensagem");
        return "contato?faces-redirect=true";
    }
    
    public String verDetalhes(Compromisso selecionado) {
    	FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().remove("mensagem");
        context.getExternalContext().getSessionMap().put("compromisso", selecionado);
        return "compromisso?faces-redirect=true";
    }
    
    //Página compromisso
    
    public String salvar(){
        FacesContext context = FacesContext.getCurrentInstance();
        if (compromisso.getId() == null) {
            context.getExternalContext().getSessionMap().put("mensagem", new CompromissoDAO().adicionar(compromisso));
        }else{
            context.getExternalContext().getSessionMap().put("mensagem", new CompromissoDAO().atualizar(compromisso));
        }
        context.getExternalContext().getSessionMap().put("compromisso", compromisso);
        return "compromisso?faces-redirect=true";
    }
    
    public void limpar(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().remove("compromisso");
        compromisso = new Compromisso();
        compromisso.setUsuario((Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado"));
    }
    
    public String excluir(){
        FacesContext context = FacesContext.getCurrentInstance();
        if (compromisso.getId() != null) {
            context.getExternalContext().getSessionMap().put("mensagem", new CompromissoDAO().remover(compromisso));
        } else{
            context.getExternalContext().getSessionMap().remove("mensagem");
        }
        return "index?faces-redirect=true";
    }
    
    public void excluir(Compromisso selecionado){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put("mensagem", new CompromissoDAO().remover(selecionado));
    }
    
    public List<Contato> listarParticipantes(){
        compromisso.setLista(new CompromissoDAO().carregarParticipantes(compromisso));
        participantes = compromisso.getLista();
        return  participantes;
    }
    
    public List<Contato> novosParticipantes(){
        compromisso.setLista(new CompromissoDAO().carregarParticipantes(compromisso));
        novos = new CompromissoDAO().novosParticipantes(compromisso);
        return novos;
    }
    
    public void removerParticipante(Contato contato){
        compromisso.getLista().remove(contato);
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put("mensagem", new CompromissoDAO().excluirParticipante(compromisso));
    }
    
    public void adicionarParticipante(){
        FacesContext context = FacesContext.getCurrentInstance();
        if (compromisso.getId() != null) {
            if (novoContato != null) {
                Contato contato = new ContatoDAO().buscarPorId(novoContato);
                compromisso.getLista().add(contato);
                context.getExternalContext().getSessionMap().put("mensagem", new CompromissoDAO().adicionarParticipante(compromisso));
            }    
        } else{
            context.getExternalContext().getSessionMap().put("mensagem", "É necessário salvar o compromisso antes de adicionar participantes!");
        }
    }
    
    //Getters Setters
    
    public Compromisso getCompromisso() {
        return compromisso;
    }
    
    public void setCompromisso(Compromisso compromisso) {
        this.compromisso = compromisso;
    }

    public List<Compromisso> getCompromissos() {
        return compromissos;
    }
    
    public List<Contato> getNovos() {
        return novos;
    }

    public Integer getNovoContato() {
        return novoContato;
    }

    public void setNovoContato(Integer novoContato) {
        this.novoContato = novoContato;
    }

    public List<Contato> getParticipantes() {
        return participantes;
    }
}