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

    public void atualizarUsuario(Usuario usuario){

        if (usuario == null){
            throw new IllegalArgumentException("Usuário não pode ser nulo.");
        }

        if (usuario.getIdUsuario() == null){
            throw new IllegalArgumentException("Id de usuário não pode ser nulo.");
        }

        if (usuario.getNome() == null || usuario.getNome().isBlank()){
            throw new IllegalArgumentException("Nome de usuário não pode ser vazio.");
        }

        if (usuario.getEmail() == null || usuario.getEmail().isBlank()){
            throw new IllegalArgumentException("Email de usuário não pode ser vazio.");
        }

        if (usuario.getSenha() == null || usuario.getSenha().isBlank()){
            throw new IllegalArgumentException("Senha de usuário não pode ser vazia.");
        }

        this.usuarioDAO.atualizar(usuario);
    }

    public void excluirUsuario(int idUsuario){
        this.usuarioDAO.deletar(idUsuario);
    }
}
