package controller;

import service.UsuarioDAO;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import model.Usuario;

@ManagedBean
@ViewScoped
public class BeanUsuario implements Serializable{
    
    private Usuario usuario = new Usuario();
    
    public String salvar(){
        FacesContext context = FacesContext.getCurrentInstance();
        if(usuario.getId() == null) {
            context.getExternalContext().getSessionMap().put("mensagem", new UsuarioDAO().adicionar(usuario));
	} else {
            context.getExternalContext().getSessionMap().put("mensagem", new UsuarioDAO().atualizar(usuario));
	}
        usuario = new Usuario();
        return "login?faces-redirect=true";
    }
    
    public String cadastrar(){
        usuario = new Usuario();
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().remove("mensagem");
        return "usuario?faces-redirect=true";
    }
    
    public String efetuarLogin(){
        FacesContext context = FacesContext.getCurrentInstance();
        Usuario existe = new UsuarioDAO().verificar(usuario);
        if(existe != null) {
            usuario = existe;
            context.getExternalContext().getSessionMap().put("usuarioLogado", usuario);
            context.getExternalContext().getSessionMap().put("mensagem", "Bem vindo " + usuario.getLogin());
            return "index?faces-redirect=true";
        }
        context.getExternalContext().getSessionMap().put("mensagem", "Erro ao logar: Login ou Senha incorretos!");
        return "login?faces-redirect=true";
    }
    
    public String deslogar() {
        usuario = new Usuario();
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().remove("usuarioLogado");
        context.getExternalContext().getSessionMap().remove("mensagem");
        return "login?faces-redirect=true";
    }
    
    public String inicio() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().remove("mensagem");
        return "index?faces-redirect=true";
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}