
package Model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity 
@Table(name = "cliente") 

public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCliente;
    
    
    private String nome;
    private String celular;
    private String email;
    
    @Column(name = "data_nascimento") // coloque o nome exato da coluna do seu SQL
    private LocalDate dataNascimento; 
    //private String dataNascimento;

    public Cliente() {
    }

    public Cliente(int idCliente, String nome, String celular, String email, LocalDate dataNascimento) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.celular = celular;
        this.email = email;
        this.dataNascimento = dataNascimento;
    }

   

    public int getIdCliente() {
        return idCliente;
    }

    public String getNome() {
        return nome;
    }

    public String getCelular() {
        return celular;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    
       @Override
    public String toString() {
    return this.nome; 
    }
    
    
}
