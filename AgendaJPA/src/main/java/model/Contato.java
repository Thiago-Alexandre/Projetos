package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="contato")
public class Contato implements Serializable,Comparable<Contato>{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_contato")
    private Integer id;
    
    @Column(name="nome")
    private String nome;
    
    @Column(name="email")
    private String email;
    
    @Column(name="fone")
    private String fone;
    
    @ManyToOne
    @JoinColumn(name="id_usuario")
    private Usuario usuario;

    public Contato(Integer id, String nome, String email, String fone, Usuario usuario) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.fone = fone;
        this.usuario = usuario;
    }
    
    public Contato(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    } 

    @Override
    public int compareTo(Contato outro) {
        return this.nome.compareTo(outro.nome);
    }
}