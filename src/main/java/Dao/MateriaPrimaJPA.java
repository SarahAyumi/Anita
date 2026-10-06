
package Dao;

import Model.*;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class MateriaPrimaJPA {
    public static void cadastrar(MateriaPrima m){
        EntityManager manager = ConexaoJPA.conectar();
        try{
            manager.getTransaction().begin();
            manager.persist(m);
            manager.getTransaction().commit();
        }catch(Exception e){
            manager.getTransaction().rollback();
        }
    }
    
    public static List<MateriaPrima> filtrarTodos(){
        EntityManager manager = ConexaoJPA.conectar();
        
        return manager.createQuery("FROM MateriaPrima", MateriaPrima.class).getResultList();
        
        
    }
    
    public static List<MateriaPrima> filtrarPorPesquisa(String nome) {
        EntityManager manager = ConexaoJPA.conectar();

        try {
       
        String jpql = "FROM MateriaPrima m WHERE m.nome LIKE :termo "
                + "OR CAST(m.estoque_M AS string) LIKE :termo "
                + "OR m.marca LIKE :termo "
                + "OR CAST (m.precoUn AS string) LIKE :termo";
        
        String termoFormatado = "%" + nome + "%";
        
        return manager.createQuery(jpql, MateriaPrima.class)
                .setParameter("termo", termoFormatado) 
                .getResultList();
    } catch (Exception e) {
        e.printStackTrace();
        return new ArrayList<>(); 
    } 
    }

    public static void removerMateriaPrima(int id) {
        EntityManager manager = ConexaoJPA.conectar();

        try {
            manager.getTransaction().begin(); 

            MateriaPrima m = manager.find(MateriaPrima.class, id); 

            if (m != null) { 
                manager.remove(m); 
            }

            manager.getTransaction().commit(); 
        } catch (Exception e) {
            manager.getTransaction().rollback();
        }
    }

   

    public static void atualizarMateriaPrimaJPQL(int id, String nome, int estoque, String marca, double preco) {
        EntityManager manager = ConexaoJPA.conectar();

    try {
        manager.getTransaction().begin();

        MateriaPrima materia = manager.find(MateriaPrima.class, id);
       
           
            materia.setNome(nome);
            
            materia.setEstoque_M(estoque);
            materia.setMarca(marca);
            materia.setPrecoUn(preco);
            

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
