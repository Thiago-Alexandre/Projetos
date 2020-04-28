package controler;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import model.Compromisso;
import model.Contato;
import model.Usuario;

@ManagedBean
@ViewScoped
public class BeanCompromisso {
    
    private Compromisso compromisso = new Compromisso();
    private CompromissoDAO daoCompromisso = new CompromissoDAO();
    private ContatoDAO daoContato = new ContatoDAO();
    private List<Compromisso> compromissos = new ArrayList<>();
    private List<Contato> novos = new ArrayList<>();
    private Integer novoContato;
    
    @PostConstruct
    public void init(){
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getExternalContext().getSessionMap().get("compromisso") != null) {
            compromisso = ((Compromisso) context.getExternalContext().getSessionMap().get("compromisso"));
            compromisso.setUsuario((Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado"));
            compromisso.setLista(daoCompromisso.carregarParticipantes(compromisso.getId()));
        } else{
            compromisso = new Compromisso();
            compromisso.setUsuario((Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado"));
        }
    }
    
    public Compromisso getCompromisso() {
        return compromisso;
    }
    
    public void setCompromisso(Compromisso compromisso) {
        this.compromisso = compromisso;
    }

    public List<Compromisso> getCompromissos() {
        return compromissos;
    }

    public void setCompromissos(List<Compromisso> compromissos) {
        this.compromissos = compromissos;
    }
    
    public List<Contato> getNovos() {
        return novos;
    }

    public void setNovos(List<Contato> novos) {
        this.novos = novos;
    }

    public Integer getNovoContato() {
        return novoContato;
    }

    public void setNovoContato(Integer novoContato) {
        this.novoContato = novoContato;
    }
    
    /*Página index*/
   
    public List<Compromisso> listar(){
        compromissos = daoCompromisso.listar(compromisso.getUsuario().getId());
        return compromissos;
    }
    
    public String novoCompromisso(){
        return "compromisso?faces-redirect=true";
    }
    
    public String contatos(){
        return "contato?faces-redirect=true";
    }
    
    public String verDetalhes(Compromisso compromisso){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put("compromisso", compromisso);
        return "compromisso?faces-redirect=true";
    }
    
    /*Página compromisso*/
    
    public String salvar(){
        System.out.println(compromisso.getData() + "");
        if (compromisso.getId() == null) {
            daoCompromisso.salvar(compromisso);
        }else{
            daoCompromisso.atualizar(compromisso);
        }
        return "index?faces-redirect=true";
    }
    
    public String excluir(){
        if (compromisso.getId() != null) {
            daoCompromisso.excluir(compromisso);
            compromisso = new Compromisso();
            return "index?faces-redirect=true";    
        }
        return "compromisso?faces-redirect=true";
    }
    
    public List<Contato> listarParticipantes(){
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getExternalContext().getSessionMap().get("compromisso") != null) {
            compromisso.setLista(daoCompromisso.carregarParticipantes(compromisso.getId()));
        } else{
            compromisso = new Compromisso();
        }
        return  compromisso.getLista();
    }
    
    public List<Contato> novosParticipantes(){
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getExternalContext().getSessionMap().get("compromisso") != null) {
            novos = daoCompromisso.novosParticipantes(compromisso);
        } else{
            novos = daoContato.listar("", compromisso.getUsuario().getId());
        }
        return novos;
    }
    
    public void removerParticipante(Contato contato){
        daoCompromisso.excluirParticipante(compromisso.getId(), contato.getId());
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getExternalContext().getSessionMap().get("compromisso") != null) {
            novos = daoCompromisso.novosParticipantes(compromisso);
        } else{
            novos = daoContato.listar("", compromisso.getUsuario().getId());
        }
        
    }
    
    public void adicionarParticipante(){
        FacesContext context = FacesContext.getCurrentInstance();
        if (novoContato != null && compromisso.getId() != null) {
            if (context.getExternalContext().getSessionMap().get("contatoErro") != null) {
                context.getExternalContext().getSessionMap().remove("contatoErro");
            }
            daoCompromisso.adicionarParticipante(compromisso.getId(), novoContato);
        } else if (compromisso.getId() == null) {
            context.getExternalContext().getSessionMap().put("contatoErro", true);
        }
    }
    
}