
package Dao;

import Model.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class UsuarioJPA {
    
    public static Usuario validarUsuario (Usuario u) {
        
        EntityManager manager = ConexaoJPA.conectar();
        
        try {
            Query consulta = 
                    manager.createQuery("SELECT u FROM Usuario u WHERE u.login = :login AND u.senha = :senha");
                    consulta.setParameter("login", u.getLogin());
                    consulta.setParameter("senha", u.getSenha());
                    
                    List<Usuario> lista = consulta.getResultList();
                    
                    if(!lista.isEmpty()){
                        return lista.get(0);
                    }
        }catch(Exception e) {
            manager.getTransaction().rollback();
        }
        return null;
    }
    
    
    public static void cadastrar(Usuario u){
        EntityManager manager = ConexaoJPA.conectar();
        try{
            manager.getTransaction().begin();
            manager.persist(u);
            manager.getTransaction().commit();
        }catch(Exception e){
            manager.getTransaction().rollback();
        }
    }
    
    public static List<Usuario> filtrarTodos(){
        EntityManager manager = ConexaoJPA.conectar();
        
        return manager.createQuery("FROM Usuario", Usuario.class).getResultList();
        
        
    }
    
    public static List<Usuario> filtrarPorPesquisa(String nome) {
        EntityManager manager = ConexaoJPA.conectar();

        try {
       
        String jpql = "FROM Usuario u WHERE u.login LIKE :termo "
                + "OR u.senha LIKE :termo "
                + "OR u.cargo LIKE :termo ";
        
        String termoFormatado = "%" + nome + "%";
        
        return manager.createQuery(jpql, Usuario.class)
                .setParameter("termo", termoFormatado) 
                .getResultList();
    } catch (Exception e) {
        e.printStackTrace();
        return new ArrayList<>(); 
    } 
    }
    
     public static void removerProduto(int id) {
        EntityManager manager = ConexaoJPA.conectar();

        try {
            manager.getTransaction().begin(); 

            Usuario u = manager.find(Usuario.class, id); 

            if (u != null) { 
                manager.remove(u); 
            }

            manager.getTransaction().commit(); 
        } catch (Exception e) {
            manager.getTransaction().rollback();
        }
    }
     
      public void atualizarUsuarioJPQL(int id, String login, String senha, String cargo) {
        EntityManager manager = ConexaoJPA.conectar();

    try {
        manager.getTransaction().begin();

        Usuario usuario = manager.find(Usuario.class, id);
       
           
            usuario.setLogin(login);
            usuario.setSenha(senha);
            usuario.setCargo(cargo);
            

            manager.getTransaction().commit();
            System.out.println("atualizado com sucesso!");
            
           

    } catch (Exception x) {
         System.out.println("Erro ao atualizar: " + x.getMessage());
    
        if (manager.getTransaction().isActive()) {
            manager.getTransaction().rollback();
        }
    } finally {
        if (manager != null && manager.isOpen()) {
            manager.close();
        }
    }
    }
    
}
