package model;

public class Contato {
    
    private Integer id;
    private String nome;
    private String email;
    private String fone;
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
    
}