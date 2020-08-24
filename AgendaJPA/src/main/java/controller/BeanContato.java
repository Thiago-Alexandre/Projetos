package controller;

import service.ContatoDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import model.Contato;
import model.Usuario;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class BeanContato implements Serializable{
    
    private Contato contato = new Contato();
    private List<Contato> contatos = new ArrayList<>();
    
    @PostConstruct
    public void init(){
        FacesContext context = FacesContext.getCurrentInstance();
        contato.setUsuario((Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado"));
        contatos = new ContatoDAO().listar(contato.getUsuario());
    }
    
    public void salvar(){
        FacesContext context = FacesContext.getCurrentInstance();
        if (contato.getId() == null) {
            context.getExternalContext().getSessionMap().put("mensagem", new ContatoDAO().adicionar(contato));
        }else{
            context.getExternalContext().getSessionMap().put("mensagem", new ContatoDAO().atualizar(contato));
        }
        contato = new Contato();
        contato.setUsuario((Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado"));
        contatos = new ContatoDAO().listar(contato.getUsuario());
    }
    
    public void excluir(Contato selecionado){
        contato = selecionado;
        FacesContext context = FacesContext.getCurrentInstance();
        if (contato.getId() != null) {
            context.getExternalContext().getSessionMap().put("mensagem", new ContatoDAO().remover(contato));
            contato = new Contato();
            contato.setUsuario((Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado"));
            contatos = new ContatoDAO().listar(contato.getUsuario());
        }
        contato = new Contato();
        contato.setUsuario((Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado"));
        contatos = new ContatoDAO().listar(contato.getUsuario());
    }
    
    public List<Contato> listar(){
        contatos = new ArrayList<>();
        if (contato.getNome() != null) {
            if (!contato.getNome().equals("")) {
                contatos = new ContatoDAO().listar(contato.getUsuario(), contato.getNome());
            } else{
                contatos = new ContatoDAO().listar(contato.getUsuario());
            }    
        } else{
            contatos = new ContatoDAO().listar(contato.getUsuario());
        }
        return contatos;
    }
    
    public void onRowSelect(SelectEvent event) {
    	contato = (Contato) event.getObject();
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().remove("mensagem");
    }
    
    public void limpar(){
        FacesContext context = FacesContext.getCurrentInstance();
        contato = new Contato();
        contato.setUsuario((Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado"));
        context.getExternalContext().getSessionMap().remove("mensagem");
    }

    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

    public List<Contato> getContatos() {
        return contatos;
    }

    public void setContatos(List<Contato> contatos) {
        this.contatos = contatos;
    }
}