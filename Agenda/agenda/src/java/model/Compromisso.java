package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Compromisso {
    
    private Integer id;
    private Date data;
    private String local;
    private String descricao;
    private Usuario usuario;
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