package services;

import com.google.gson.Gson;
import dao.ContatoDAO;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import model.Contato;

@Path("contato")
public class ContatoResource {

    @Context
    private UriInfo context;

    public ContatoResource() {}

    @GET
    @Path("{contato}")
    @Produces(MediaType.APPLICATION_JSON)
    public String pesquisar(@PathParam("contato")String content){
        Integer id = Integer.parseInt(content);
        ContatoDAO dao = new ContatoDAO();
        Contato contato = dao.buscarPorId(id);
        Gson g = new Gson();
        return g.toJson(contato);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String listar(){
        ContatoDAO dao = new ContatoDAO();
        List<Contato> lista = dao.listar();
        Gson gson = new Gson();
        return gson.toJson(lista);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String adicionar(String content){
        Gson gson = new Gson();
        Contato contato = gson.fromJson(content, Contato.class);
        ContatoDAO dao = new ContatoDAO();
        return dao.adicionar(contato);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String atualizar(String content){
        Gson gson = new Gson();
        Contato contato = gson.fromJson(content, Contato.class);
        ContatoDAO dao = new ContatoDAO();
        return dao.atualizar(contato);
    }

    @DELETE
    @Path("{contato}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String remover(@PathParam("contato")String content) {
        Integer id = Integer.parseInt(content);
        ContatoDAO dao = new ContatoDAO();
        Contato contato = dao.buscarPorId(id);
        return dao.remover(contato);
    }
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public String removerContato(String content) {
        Gson g = new Gson();
        Contato contato = g.fromJson(content, Contato.class);
        ContatoDAO dao = new ContatoDAO();
        return dao.remover(contato);
    }
}