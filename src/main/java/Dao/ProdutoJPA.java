
package Dao;
import Model.*;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
public class ProdutoJPA {
    
    public static void cadastrar(Produto p){
        EntityManager manager = ConexaoJPA.conectar();
        try{
            manager.getTransaction().begin();
            manager.persist(p);
            manager.getTransaction().commit();
        }catch(Exception e){
            manager.getTransaction().rollback();
        }
    }
    
    public static List<Produto> filtrarTodos(){
        EntityManager manager = ConexaoJPA.conectar();
        
        return manager.createQuery("FROM Produto", Produto.class).getResultList();
        
        
    }
    
    public static List<Produto> filtrarPorPesquisa(String nome) {
        EntityManager manager = ConexaoJPA.conectar();

        try {
       
        String jpql = "FROM Produto p WHERE p.nome LIKE :termo "
                + "OR p.artista LIKE :termo "
                + "OR p.preco LIKE :termo "
                + "OR p.categoria LIKE :termo" ;
        
        String termoFormatado = "%" + nome + "%";
        
        return manager.createQuery(jpql, Produto.class)
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

            Produto p = manager.find(Produto.class, id); 

            if (p != null) { 
                manager.remove(p); 
            }

            manager.getTransaction().commit(); 
        } catch (Exception e) {
            manager.getTransaction().rollback();
        }
    }


    public static void atualizarProdutosJPQL(int id, String nome, String artista, Double preco, String categoria, int estoque) {
        EntityManager manager = ConexaoJPA.conectar();

    try {
        manager.getTransaction().begin();

        Produto produto = manager.find(Produto.class, id);
       
           
            produto.setNome(nome);
            produto.setArtista(artista);
            produto.setPreco(preco);
            produto.setCategoria(categoria);
            produto.setEstoqueP(estoque);
            

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
