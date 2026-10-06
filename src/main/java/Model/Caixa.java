
package Model;

import jakarta.persistence.*;
import java.util.*;

@Entity 
@Table(name = "caixa") 

public class Caixa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCaixa;
    
    @Column(name = "preco_total")
    private double precoTotal;
    @Column(name = "metodo_pago")
    private String metodoPago;
    
    @ManyToOne
    @JoinColumn(name = "idcliente", nullable = true) 
    private Cliente cliente;
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
    name = "produto_has_caixa", 
    joinColumns = @JoinColumn(name = "idcaixa"), 
    inverseJoinColumns = @JoinColumn(name = "idproduto") 
    )
    private List<Produto> produto = new ArrayList<>();

    public Caixa() {
    }

    public Caixa(int idCaixa, double precoTotal, String metodoPago, Cliente cliente, Produto produto) {
        this.idCaixa = idCaixa;
        this.precoTotal = precoTotal;
        this.metodoPago = metodoPago;
        this.cliente = cliente;
    }

   

    public int getIdCaixa() {
        return idCaixa;
    }

    public double getPrecoTotal() {
        return precoTotal;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public Cliente getCliente() {
        return cliente;
    }


    public void setIdCaixa(int idCaixa) {
        this.idCaixa = idCaixa;
    }

    public void setPrecoTotal(double precoTotal) {
        this.precoTotal = precoTotal;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public List<Produto> getProduto() {
    return this.produto;
    }


    public void setProduto(List<Produto> novosItens) {
    this.produto.clear(); 
    if (novosItens != null) {
        for (Produto produto : novosItens) {
            this.adicionarProduto(produto); 
            }
        }
    }
    public void adicionarProduto(Produto produto) {
    this.produto.add(produto);
    produto.setCaixa(this); 
    }
    
    @Override
    public String toString() {
    return this.metodoPago; // Faz o combo box exibir o NOME do cliente na tela!
}
    
}
