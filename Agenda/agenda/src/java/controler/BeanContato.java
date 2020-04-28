package controler;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import model.Contato;
import model.Usuario;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class BeanContato {
    
    private Contato contato = new Contato();
    private ContatoDAO dao = new ContatoDAO();
    private List<Contato> contatos = new ArrayList<>();
    
    public void salvar(){
        FacesContext context = FacesContext.getCurrentInstance();
        contato.setUsuario((Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado"));
        if (contato.getId() == null) {
            dao.salvar(contato);
        }else{
        System.out.println(contato.getNome());
        System.out.println(contato.getId());
            dao.atualizar(contato);
        }
        contato = new Contato();
    }
    
    public void excluir(){
        if (contato.getId() != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            contato.setUsuario((Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado"));
            dao.excluir(contato);
            contato = new Contato();    
        }
    }
    
    public List<Contato> listar() {
        FacesContext context = FacesContext.getCurrentInstance();
        contato.setUsuario((Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado"));
        contatos = dao.listar(contato.getNome(), contato.getUsuario().getId());
        return contatos;
    }
    
    public void onRowSelect(SelectEvent event) {
    	contato = (Contato) event.getObject();
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
    
    public ContatoDAO getDao() {
        return dao;
    }

    public void setDao(ContatoDAO dao) {
        this.dao = dao;
    }
    
}