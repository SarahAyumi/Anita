
package Model;

import jakarta.persistence.*;

@Entity 
@Table(name = "usuarios") 
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;
    
    
    private String login;
    private String senha;
    private String cargo;

    public Usuario() {
    }

    public Usuario(int idUsuario, String login, String senha, String cargo) {
        this.idUsuario = idUsuario;
        this.login = login;
        this.senha = senha;
        this.cargo = cargo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public String getCargo() {
        return cargo;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    
    
    
}
