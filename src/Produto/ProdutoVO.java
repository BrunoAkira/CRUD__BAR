package Produto;

public class ProdutoVO {
	
	private int Id;
	private String NomeProduto;
	private String Descricao;
	private String TipoProduto;
	private int QtdTotal;
	private int QtdVendida;
	private float PrecoUnit;
	
	public ProdutoVO (int id, String NomeProduto, String Descricao, String TipoProduto, int QtdTotal,  int QtdVendida, float PrecoUnit){
		this.Id = id;
		this.NomeProduto = NomeProduto;
		this.Descricao = Descricao;
		this.TipoProduto = TipoProduto; 
		this.QtdTotal = QtdTotal;
		this.QtdVendida = QtdVendida;
		this.PrecoUnit = PrecoUnit;
	}
	
	public ProdutoVO (){}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getNomeProduto() {
		return NomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		NomeProduto = nomeProduto;
	}

	public String getDescricao() {
		return Descricao;
	}

	public void setDescricao(String descricao) {
		Descricao = descricao;
	}

	public String getTipoProduto() {
		return TipoProduto;
	}

	public void setTipoProduto(String tipoProduto) {
		TipoProduto = tipoProduto;
	}

	public int getQtdTotal() {
		return QtdTotal;
	}

	public void setQtdTotal(int qtdTotal) {
		QtdTotal = qtdTotal;
	}

	public int getQtdVendida() {
		return QtdVendida;
	}

	public void setQtdVendida(int qtdVendida) {
		QtdVendida = qtdVendida;
	}

	public float getPrecoUnit() {
		return PrecoUnit;
	}

	public void setPrecoUnit(float precoUnit) {
		PrecoUnit = precoUnit;
	}

}
