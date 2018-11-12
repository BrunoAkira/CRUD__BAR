package Venda;

public class ItemVendaVO {

	public ItemVendaVO(int id, int idvenda, int idprod, int qtd) {
		Id = id;
		this.idvenda = idvenda;
		this.idprod = idprod;
		this.qtd = qtd;
	}
	
	public ItemVendaVO() {}
	
	private int Id;
	private int idvenda;
	private int idprod;
	private int qtd;
	
	private String nomeProduto;
	private float precoUnit;
	
	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public float getPrecoUnit() {
		return precoUnit;
	}

	public void setPrecoUnit(float precoUnit) {
		this.precoUnit = precoUnit;
	}

	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getIdvenda() {
		return idvenda;
	}
	public void setIdvenda(int idvenda) {
		this.idvenda = idvenda;
	}
	public int getIdprod() {
		return idprod;
	}
	public void setIdprod(int idprod) {
		this.idprod = idprod;
	}
	public int getQtd() {
		return qtd;
	}
	public void setQtd(int qtd) {
		this.qtd = qtd;
	}
}
