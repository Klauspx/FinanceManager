package service;

import dao.UsuarioDAO;
import modelo.Usuario;

import java.util.List;

public class UsuarioService {

    private UsuarioDAO usuarioDAO;

    public UsuarioService(UsuarioDAO usuarioDAO){
        this.usuarioDAO = usuarioDAO;
    }

    public void salvarUsuario(Usuario usuario){
        usuarioDAO.salvar(usuario);
    }

    public void listarUsuarios(){

        List<Usuario> lista = usuarioDAO.listarTodos();

        System.out.println("\n--- Usuários cadastrados ---");

        for(Usuario usuario : lista){
            System.out.println("ID: " + usuario.getIdUsuario() +
                    " | Nome: " + usuario.getNome() +
                    " | Email: " + usuario.getEmail());
        }
    }
}
