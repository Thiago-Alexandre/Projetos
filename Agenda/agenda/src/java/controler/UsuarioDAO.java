package controler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Usuario;
import util.ConnectionFactory;

public class UsuarioDAO {
    
    private ConnectionFactory conexao;

    public UsuarioDAO() {
        conexao = new ConnectionFactory();
    }
    
    public void salvar(Usuario usuario){
        String sql = "INSERT INTO usuario(login, senha) VALUES (?,?)";
        try(Connection connection = conexao.recuperarConexao();PreparedStatement stm = connection.prepareStatement(sql)){
            stm.setString(1, usuario.getLogin());
            stm.setString(2, usuario.getSenha());
            stm.execute();
        } catch (SQLException ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
    }

    public Usuario buscar(Integer id){
        Usuario usuario = new Usuario();
        String sql = "SELECT id_user,login,senha FROM usuario WHERE id_user=?";
        try(Connection connection = conexao.recuperarConexao();PreparedStatement stm = connection.prepareStatement(sql)){
            stm.setInt(1, id);
            stm.execute();
            try(ResultSet rst = stm.getResultSet()){
                rst.next();
                usuario = new Usuario(
                    rst.getInt("id_user"),
                    rst.getString("login"),
                    rst.getString("senha"));
            }
        } catch (SQLException ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
        return usuario;
    }
    
    public Usuario existe(Usuario usuario){
        String sql = "SELECT id_user FROM usuario WHERE login=? AND senha=?";
        int id = 0;
        try(Connection connection = conexao.recuperarConexao();PreparedStatement stm = connection.prepareStatement(sql)){
            stm.setString(1, usuario.getLogin());
            stm.setString(2, usuario.getSenha());
            stm.execute();
            try(ResultSet rst = stm.getResultSet()){
                rst.next();
                id = rst.getInt("id_user");
                if (id > 0) {
                    return buscar(id);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
        return null;
    }
    
}