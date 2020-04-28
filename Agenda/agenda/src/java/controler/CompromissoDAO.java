package controler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Compromisso;
import model.Contato;
import model.Usuario;
import util.ConnectionFactory;

public class CompromissoDAO {
    
    private ConnectionFactory conexao;

    public CompromissoDAO() {
        conexao = new ConnectionFactory();
    }
    
    public void salvar(Compromisso compromisso){
        String sql = "INSERT INTO compromisso (data_hora, local_compromisso, descricao, id_usuario) "
                + "VALUES (?,?,?,?)";
        try(Connection connection = conexao.recuperarConexao();PreparedStatement stm = connection.prepareStatement(sql)){
                stm.setTimestamp(1, new java.sql.Timestamp(compromisso.getData().getTime()));
                stm.setString(2, compromisso.getLocal());
                stm.setString(3, compromisso.getDescricao());
                stm.setInt(4, compromisso.getUsuario().getId());
                stm.execute();
        } catch (SQLException ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
    }
    
    public void atualizar(Compromisso compromisso){
        String sql = "UPDATE compromisso SET data_hora=?, local_compromisso=?, descricao=?"
                + " WHERE id_compromisso=?";
        try(Connection connection = conexao.recuperarConexao();PreparedStatement stm = connection.prepareStatement(sql)){
            stm.setTimestamp(1, new java.sql.Timestamp(compromisso.getData().getTime()));
            stm.setString(2, compromisso.getLocal());
            stm.setString(3, compromisso.getDescricao());
            stm.setInt(4, compromisso.getId());
            stm.execute();
        } catch (SQLException ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
    }
    
    public void excluirParticipante(Integer compromisso, Integer contato){
        String sql = "DELETE FROM participante WHERE id_compromisso=? AND id_contato=?";
        try(Connection connection = conexao.recuperarConexao();PreparedStatement stm = connection.prepareStatement(sql)){
            stm.setInt(1, compromisso);
            stm.setInt(2, contato);
            stm.execute();
        } catch (SQLException ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
    }
    
    public void adicionarParticipante(Integer compromisso, Integer contato){
        String sql = "INSERT INTO participante (id_compromisso, id_contato) VALUES (?,?)";
        try(Connection connection = conexao.recuperarConexao();PreparedStatement stm = connection.prepareStatement(sql)){
            stm.setInt(1, compromisso);
            stm.setInt(2, contato);
            stm.execute();
        } catch (SQLException ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
    }
    
    public void excluir(Compromisso compromisso){
        excluirParticipantes(compromisso.getId());
        String sql = "DELETE FROM compromisso WHERE id_compromisso=?";
        try(Connection connection = conexao.recuperarConexao();PreparedStatement stm = connection.prepareStatement(sql)){
            stm.setInt(1, compromisso.getId());
            stm.execute();
        } catch (SQLException ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
    }
    
    private void excluirParticipantes(Integer compromisso){
        String sql = "DELETE FROM participante WHERE id_compromisso=?";
        try(Connection connection = conexao.recuperarConexao();PreparedStatement stm = connection.prepareStatement(sql)){
            stm.setInt(1, compromisso);
            stm.execute();
        } catch (SQLException ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
    }
    
    public List<Compromisso> listar(Integer idUser){
        List<Compromisso> lista = new ArrayList<>();
        UsuarioDAO dao = new UsuarioDAO();
        String sql = "SELECT id_compromisso,data_hora,local_compromisso,descricao,id_usuario FROM compromisso "
                + "WHERE id_usuario=? ORDER BY data_hora";
        try(Connection connection = conexao.recuperarConexao();PreparedStatement stm = connection.prepareStatement(sql)){
            stm.setInt(1, idUser);
            stm.execute();
            try(ResultSet rst = stm.getResultSet()){
                while (rst.next()) {
                    Integer id = rst.getInt("id_compromisso");
                    Date data = rst.getTimestamp("data_hora");
                    String local = rst.getString("local_compromisso");
                    String descricao = rst.getString("descricao");
                    Usuario user = dao.buscar(rst.getInt("id_usuario"));
                    List<Contato> contatos = carregarParticipantes(id);
                    lista.add(new Compromisso(id,data,local,descricao,user,contatos));
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
        return lista;
    }
    
    public List<Contato> carregarParticipantes(Integer compromisso){
        List<Contato> participantes = new ArrayList<>();
        ContatoDAO dao = new ContatoDAO();
        String sql = "SELECT p.id_contato FROM participante p, contato c "
                + "WHERE id_compromisso=? AND p.id_contato=c.id_contato ORDER BY c.nome";
        try(Connection connection = conexao.recuperarConexao();PreparedStatement stm = connection.prepareStatement(sql)){
            stm.setInt(1, compromisso);
            stm.execute();
            try(ResultSet rst = stm.getResultSet()){
                while (rst.next()) {
                    Contato contato = dao.buscar(rst.getInt("id_contato"));
                    participantes.add(contato);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
        return participantes;
    }
    
    public List<Contato> novosParticipantes(Compromisso compromisso){
        List<Contato> participantes = new ArrayList<>();
        ContatoDAO dao = new ContatoDAO();
        String sql = "SELECT id_contato FROM contato WHERE id_usuario=? AND "
                + "id_contato NOT IN (SELECT p.id_contato FROM participante p "
                + "INNER JOIN contato c ON p.id_contato=c.id_contato WHERE p.id_compromisso=?) ORDER BY nome";
        try(Connection connection = conexao.recuperarConexao();PreparedStatement stm = connection.prepareStatement(sql)){
            stm.setInt(1, compromisso.getUsuario().getId());
            stm.setInt(2, compromisso.getId());
            stm.execute();
            try(ResultSet rst = stm.getResultSet()){
                while (rst.next()) {
                    Contato contato = dao.buscar(rst.getInt("id_contato"));
                    participantes.add(contato);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
        return participantes;
    }
    
}