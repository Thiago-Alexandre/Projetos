package controler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Contato;
import util.ConnectionFactory;

public class ContatoDAO {
    
    private ConnectionFactory conexao;

    public ContatoDAO() {
        conexao = new ConnectionFactory();
    }
    
    public void salvar(Contato contato){
        String sql = "INSERT INTO contato(nome, email, fone, id_usuario) VALUES (?,?,?,?)";
        try(Connection connection = conexao.recuperarConexao();PreparedStatement stm = connection.prepareStatement(sql)){
            stm.setString(1, contato.getNome());
            stm.setString(2, contato.getEmail());
            stm.setString(3, contato.getFone());
            stm.setInt(4, contato.getUsuario().getId());
            stm.execute();
        } catch (SQLException ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
    }
    
    public void atualizar(Contato contato){
        String sql = "UPDATE contato SET nome=?, email=?, fone=? WHERE id_contato=?";
        
        try(Connection connection = conexao.recuperarConexao();PreparedStatement stm = connection.prepareStatement(sql)){
            stm.setString(1, contato.getNome());
            stm.setString(2, contato.getEmail());
            stm.setString(3, contato.getFone());
            stm.setInt(4, contato.getId());
            stm.execute();
        } catch (SQLException ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
    }
    
    public void excluir(Contato contato){
        if (!verificar(contato)) {
            String sql = "DELETE FROM contato WHERE id_contato=?";
            try(Connection connection = conexao.recuperarConexao();PreparedStatement stm = connection.prepareStatement(sql)){
                stm.setInt(1, contato.getId());
                stm.execute();
            } catch (SQLException ex) {
                System.err.println("Erro: " + ex.getMessage());
            }    
        }
    }
    
    public boolean verificar(Contato contato){
        String sql = "SELECT EXISTS"
                + "(SELECT p.id_contato FROM participante p INNER JOIN compromisso c ON p.id_compromisso=c.id_compromisso "
                + "AND c.id_usuario=? AND p.id_contato=?) AS 'existe'";
        int aux = 0;
        try(Connection connection = conexao.recuperarConexao();PreparedStatement stm = connection.prepareStatement(sql)){
            stm.setInt(1, contato.getUsuario().getId());
            stm.setInt(2, contato.getId());
            stm.execute();
            try(ResultSet rst = stm.getResultSet()){
                rst.next();
                aux = rst.getInt("existe");
                if (aux == 1) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
        return false;
    }
    
    public Contato buscar(Integer id){
        Contato contato = new Contato();
        UsuarioDAO daoUsuario = new UsuarioDAO();
        String sql = "SELECT id_contato,nome,email,fone,id_usuario FROM contato WHERE id_contato=?";
        try(Connection connection = conexao.recuperarConexao();PreparedStatement stm = connection.prepareStatement(sql)){
            stm.setInt(1, id);
            stm.execute();
            try(ResultSet rst = stm.getResultSet()){
                rst.next();
                contato = new Contato(
                    rst.getInt("id_contato"),
                    rst.getString("nome"),
                    rst.getString("email"),
                    rst.getString("fone"),
                    daoUsuario.buscar(rst.getInt("id_usuario")));
            }
        } catch (SQLException ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
        return contato;
    }
    
    public List<Contato> listar(String nome, Integer user){
        if (nome == null) {
            nome = "";
        }
        UsuarioDAO daoUsuario = new UsuarioDAO();
        List<Contato> lista = new ArrayList<>();
        String sql = "SELECT id_contato,nome,email,fone,id_usuario FROM contato WHERE id_usuario=? AND nome LIKE '%" + nome + "%' ORDER BY nome";
        try(Connection connection = conexao.recuperarConexao();PreparedStatement stm = connection.prepareStatement(sql)){
                stm.setInt(1, user);
                stm.execute();
                try(ResultSet rst = stm.getResultSet()){
                    while (rst.next()) {
                        lista.add(new Contato(
                        rst.getInt("id_contato"),
                        rst.getString("nome"),
                        rst.getString("email"),
                        rst.getString("fone"),
                        daoUsuario.buscar(rst.getInt("id_usuario"))));
                    }
                }
        } catch (SQLException ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
        return lista;
    }
}