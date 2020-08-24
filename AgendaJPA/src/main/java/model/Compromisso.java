package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name="compromisso")
public class Compromisso implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_compromisso")
    private Integer id;
    
    @Column(name="data_hora")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date data;
    
    @Column(name="local_compromisso")
    private String local;
    
    @Column(name="descricao")
    private String descricao;
    
    @ManyToOne
    @JoinColumn(name="id_usuario")
    private Usuario usuario;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name="participante", 
            joinColumns = {@JoinColumn(name="id_compromisso")}, 
            inverseJoinColumns = {@JoinColumn(name="id_contato")})
    private List<Contato> lista = new ArrayList<>();

    public Compromisso(Integer id, Date data, String local, String descricao, Usuario usuario, List<Contato> lista) {
        this.id = id;
        this.data = data;
        this.local = local;
        this.descricao = descricao;
        this.usuario = usuario;
        this.lista = lista;
    }

    public Compromisso() {}
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public List<Contato> getLista() {
        return lista;
    }

    public void setLista(List<Contato> lista) {
        this.lista = lista;
    }
}    