
package Model;

import jakarta.persistence.*;
import javax.swing.JOptionPane;

@Entity 
@Table(name = "produto") 
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProduto;
    
    @Column(name = "nome")
    private String nome;
    @Column(name = "artista")
    private String artista;
    @Column(name = "preco")
    private double preco;
    @Column(name = "categoria")
    private String categoria;
    @Column(name = "estoque_p")
    private int estoqueP;
    @Column(name = "quantidade_minimaP")
    private int quantidadeMinimaP = 5;
    
    @ManyToOne
    @JoinColumn(name = "idmateria_prima", nullable = true) 
    private MateriaPrima materiaPrima;
    
    @ManyToOne
    @JoinColumn(name = "caixa_id")
    private Caixa caixa;

    public Produto() {
    }

    public Produto(int idProduto, String nome, String artista, double preco, String categoria, int estoqueP, int quantidadeMinimaP, MateriaPrima materiaPrima, Caixa caixa) {
        this.idProduto = idProduto;
        this.nome = nome;
        this.artista = artista;
        this.preco = preco;
        this.categoria = categoria;
        this.estoqueP = estoqueP;
        this.quantidadeMinimaP = quantidadeMinimaP;
        this.materiaPrima = materiaPrima;
        this.caixa = caixa;
    }

    public int getQuantidadeMinimaP() {
        return quantidadeMinimaP;
    }
    

    public Caixa getCaixa() {
        return caixa;
    }

    public void setCaixa(Caixa caixa) {
        this.caixa = caixa;
    }
    

    public int getIdProduto() {
        return idProduto;
    }

    public String getNome() {
        return nome;
    }

    public String getArtista() {
        return artista;
    }

    public double getPreco() {
        return preco;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getEstoqueP() {
        return estoqueP;
    }

    public MateriaPrima getMateriaPrima() {
        return materiaPrima;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setEstoqueP(int estoqueP) {
        this.estoqueP = estoqueP;
    }

    public void setQuantidadeMinimaP(int quantidadeMinima) {
        this.quantidadeMinimaP = quantidadeMinimaP;
    }

    public void setMateriaPrima(MateriaPrima materiaPrima) {
        this.materiaPrima = materiaPrima;
    }
    
    public void atualizarQuantidade(int estoqueP) {
        if (estoqueP >= 0) {
            this.estoqueP = estoqueP;
        } else {
            JOptionPane.showMessageDialog(null, "Erro: A quantidade não pode ser negativa.");
        }
    }
    
    public boolean verificarEstoqueCritico() {
        return this.estoqueP <= this.quantidadeMinimaP;
    }
    
    public void consumirMaterial(int estoqueP) {
        if (estoqueP <= 0) {
            JOptionPane.showMessageDialog(null, "Erro: A quantidade a ser consumida deve ser maior que zero.");
            return;
        }

        if (this.estoqueP >= estoqueP) {
            this.estoqueP -= estoqueP; 
            
            
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
