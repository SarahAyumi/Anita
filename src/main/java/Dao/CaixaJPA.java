
package Dao;

import Model.*;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class CaixaJPA { 
    
    public static void cadastrar(Caixa c){
        EntityManager manager = ConexaoJPA.conectar();
        try{
            manager.getTransaction().begin();
            manager.persist(c);
            manager.getTransaction().commit();
        }catch(Exception e){
            manager.getTransaction().rollback();
        }
    }
    
    public static List<Caixa> filtrarTodos(){
        EntityManager manager = ConexaoJPA.conectar();
        
        return manager.createQuery("FROM Caixa", Caixa.class).getResultList();
        
    }
    
    public static List<Caixa> filtrarPorPesquisa(String nome) {
        EntityManager manager = ConexaoJPA.conectar();

        try {
        String jpql = "SELECT DISTINCT c FROM Caixa c "
        + " LEFT JOIN FETCH c.produto p "     
        + " LEFT JOIN FETCH c.cliente cl " 
        + " WHERE CAST(c.precoTotal AS string) LIKE :termo "
        + " OR c.metodoPago LIKE :termo "
        + " OR c.cliente.nome LIKE :termo "
        + " OR p.nome LIKE :termo";
        
        
        String termoFormatado = "%" + nome + "%";
        
        return manager.createQuery(jpql, Caixa.class)
                .setParameter("termo", termoFormatado) 
                .getResultList();
    } catch (Exception e) {
        e.printStackTrace();
        return new ArrayList<>(); 
    } finally {
        
        if (manager != null && manager.isOpen()) {
            manager.close();
        }
    }
    }

    public static void removerCaixa(int id) {
        EntityManager manager = ConexaoJPA.conectar();

        try {
            manager.getTransaction().begin(); 

            Caixa c = manager.find(Caixa.class, id); 

            if (c != null) { 
                manager.remove(c); 
            }

            manager.getTransaction().commit(); 
        } catch (Exception e) {
            manager.getTransaction().rollback();
        }
    }

    

    public void atualizarCaixaJPQL(int idCaixa, double precoTot, String metodoPagamento, String NomeC, String nomeProd) {
    EntityManager manager = ConexaoJPA.conectar();

    try {
        manager.getTransaction().begin();

        Caixa caixa = manager.find(Caixa.class, idCaixa);
        
        
            
            if (caixa.getProduto() != null && !caixa.getProduto().isEmpty()) {
                Produto p = caixa.getProduto().get(0);
                p.setNome(nomeProd); 
            }

           
            if (caixa.getCliente() != null) {
                caixa.getCliente().setNome(NomeC); 
            
            
            caixa.setMetodoPago(metodoPagamento);
            caixa.setPrecoTotal(precoTot); 

            manager.getTransaction().commit();
            System.out.println("atualizado com sucesso!");
            
            }else {
            System.out.println("Nenhum caixa encontrado com o ID informado: " + idCaixa);
            }

    } catch (Exception x) {
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