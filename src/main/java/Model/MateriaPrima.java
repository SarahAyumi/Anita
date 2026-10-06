
package Model;

import jakarta.persistence.*;
import javax.swing.JOptionPane;

@Entity 
@Table(name = "materia_prima") 

public class MateriaPrima {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmateria_prima")
    private int idMateriaPrima;
    @Column(name = "nome")
    private String nome;
    @Column(name = "estoque_m")
    private int estoque_M;
    @Column(name = "marca")
    private String marca;
    @Column(name = "preco_un")
    private double precoUn;
    @Column(name = "quantidade_minimaM")
    private int quantidadeMinimaM = 5;

    public MateriaPrima() {
    }

    public MateriaPrima(int idMateriaPrima, String nome, int estoque_M, String marca, double precoUn, int quantidadeMinimaM) {
        this.idMateriaPrima = idMateriaPrima;
        this.nome = nome;
        this.estoque_M = Math.max(0, estoque_M);
        this.marca = marca;
        this.precoUn = precoUn;
        this.quantidadeMinimaM = quantidadeMinimaM;
    }

    public int getQuantidadeMinimaM() {
        return quantidadeMinimaM;
    }
    
    public int getIdMateriaPrima() {
        return idMateriaPrima;
    }

    public String getNome() {
        return nome;
    }

    public int getEstoque_M() {
        return estoque_M;
    }

    public String getMarca() {
        return marca;
    }

    public double getPrecoUn() {
        return precoUn;
    }

    public void setIdMateriaPrima(int idMateriaPrima) {
        this.idMateriaPrima = idMateriaPrima;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEstoque_M(int estoque_M) {
        this.estoque_M = estoque_M;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setPrecoUn(double precoUn) {
        this.precoUn = precoUn;
    }

    public void setQuantidadeMinimaM(int quantidadeMinimaM) {
        this.quantidadeMinimaM = quantidadeMinimaM;
    }
    
    public void atualizarQuantidade(int estoque_M) {
        if (estoque_M >= 0) {
            this.estoque_M = estoque_M;
        } else {
            JOptionPane.showMessageDialog(null, "Erro: A quantidade não pode ser negativa.");
        }
    }
    
    public boolean verificarEstoqueCritico() {
        return this.estoque_M <= this.quantidadeMinimaM;
    }
    
    public void consumirMaterial(int estoque_M) {
        if (estoque_M <= 0) {
            JOptionPane.showMessageDialog(null, "Erro: A quantidade a ser consumida deve ser maior que zero.");
            return;
        }

        if (this.estoque_M >= estoque_M) {
            this.estoque_M -= estoque_M; 
            
            
            if (verificarEstoqueCritico()) {
                JOptionPane.showMessageDialog(null, "o material atingiu o nível de estoque crítico!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Erro: Estoque insuficiente.");
        }
    
    }
    
    @Override
    public String toString() {
    return this.nome;
    }
    
    
}
