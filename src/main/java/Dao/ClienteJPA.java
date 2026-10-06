
package Dao;

import Model.*;
import jakarta.persistence.EntityManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class ClienteJPA {
    
    public static void cadastrar(Cliente c){
        EntityManager manager = ConexaoJPA.conectar();
        try{
            manager.getTransaction().begin();
            manager.persist(c);
            manager.getTransaction().commit();
        }catch(Exception e){
            manager.getTransaction().rollback();
        }
    }
    
    public static List<Cliente> filtrarTodos(){
        EntityManager manager = ConexaoJPA.conectar();
        
        return manager.createQuery("FROM Cliente", Cliente.class).getResultList();
        
        
    }
    
    public static List<Cliente> filtrarPorPesquisa(String nome) {
        EntityManager manager = ConexaoJPA.conectar();

        try {
        String jpql = "FROM Cliente c WHERE c.nome LIKE :termo "
                + " OR c.celular LIKE :termo "
                + " OR c.email LIKE :termo "
                + " OR c.dataNascimento LIKE :termo" ;
        
        String termoFormatado = "%" + nome + "%";
        
        return manager.createQuery(jpql, Cliente.class)
                .setParameter("termo", termoFormatado) 
                .getResultList();
    } catch (Exception e) {
        e.printStackTrace();
        return new ArrayList<>(); 
    } 
    }

    public static void removerCliente(int id) {
        EntityManager manager = ConexaoJPA.conectar();

        try {
            manager.getTransaction().begin(); 

            Cliente c = manager.find(Cliente.class, id); 

            
            if (c != null) {
            
            List<Caixa> caixasDoCliente = manager.createQuery(
                " SELECT c FROM Caixa c WHERE c.cliente = :cliente ", Caixa.class)
                .setParameter("cliente", c)
                .getResultList();
            
            for (Caixa caixa : caixasDoCliente) {
                caixa.setCliente(null); 
            }
            
           
            
            manager.flush(); 
            
            manager.remove(c);
            
            manager.getTransaction().commit();
            System.out.println("Cliente desvinculado e removido com sucesso!");
        } else {
            System.out.println("Cliente não encontrado para o ID: " + id);
            manager.getTransaction().rollback();

            }
        } catch (Exception e) {
            e.printStackTrace();  
            if (manager.getTransaction().isActive()) {
            manager.getTransaction().rollback();
    }
        }
    }


    public void atualizarClienteJPQL(int id, String nomeC, String celularC, String emailC, String dataNascimento) {
        EntityManager manager = ConexaoJPA.conectar();

        try {
            manager.getTransaction().begin();
        Cliente cliente = manager.find(Cliente.class, id);
        
        
        if (cliente != null) {
            cliente.setIdCliente(id);
            cliente.setNome(nomeC);
            cliente.setCelular(celularC);
            cliente.setEmail(emailC);
            LocalDate dataConvertida = LocalDate.parse(dataNascimento);
            cliente.setDataNascimento(dataConvertida);
            

            manager.getTransaction().commit();
            System.out.println("Cadastro do cliente atualizado com sucesso!");
        } else {
            System.out.println("Cliente não encontrado para o ID: " + id);
            manager.getTransaction().rollback();
        }

        } catch (Exception x) {
        if (manager.getTransaction().isActive()) {
            manager.getTransaction().rollback();
        }
        
        }finally {
        if (manager != null && manager.isOpen()) {
            manager.close();
        }
        
}

    }
    
     public static String converterParaSQL(String dataAntiga) {
        
         if (dataAntiga.contains("-")) {
        return dataAntiga;
    }
        
        String[] partesData = dataAntiga.split("/");
        String dataNova = partesData[2] + "-" + partesData[1] + "-" + partesData[0];
        return dataNova;
    }
    
    public static String converterParaJava(String dataAntiga) {
        
        if (dataAntiga.contains("/")) {
        return dataAntiga;
    }
       
        
        String[] partesData = dataAntiga.split("-");
        String dataNova = partesData[2] + "/" + partesData[1] + "/" + partesData[0];
        return dataNova;
    }
    
}

