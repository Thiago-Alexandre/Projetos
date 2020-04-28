package controler;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import model.Usuario;

@ManagedBean
@ViewScoped
public class BeanUsuario {
    
    private Usuario usuario = new Usuario();
    private UsuarioDAO dao = new UsuarioDAO();
    
    public String salvar(){
        dao.salvar(usuario);
        return "login?faces-redirect=true";
    }
    
    public String cadastrar(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put("usuarioLogado", usuario);
        return "usuario?faces-redirect=true";
    }
    
    public String efetuarLogin(){
        FacesContext context = FacesContext.getCurrentInstance();
        Usuario existe = dao.existe(usuario);
        if(existe != null) {
            usuario = existe;
            context.getExternalContext().getSessionMap().put("usuarioLogado", usuario);
            if (context.getExternalContext().getSessionMap().get("erro") != null) {
                context.getExternalContext().getSessionMap().remove("erro");
            }
            return "index?faces-redirect=true";
        }
        context.getExternalContext().getSessionMap().put("erro", true);
        return "login?faces-redirect=true";
    }
    
    public String deslogar() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().remove("usuarioLogado");
        return "login?faces-redirect=true";
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}