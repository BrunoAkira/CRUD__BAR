package Lote;

import java.math.BigDecimal;

public class LoteVO {

	private int idLote;
	private int idProd;
	private String fornecedor;
	private String dataCompra;
	private int quantidade;
	private BigDecimal custoUnit;
	
	private String nomeProd;
	
	public int getIdLote() {
		return idLote;
	}
	public void setIdLote(int idLote) {
		this.idLote = idLote;
	}
	public int getIdProd() {
		return idProd;
	}
	public void setIdProd(int idProd) {
		this.idProd = idProd;
	}
	public String getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}
	public String getDataCompra() {
		return dataCompra;
	}
	public void setDataCompra(String dataCompra) {
		this.dataCompra = dataCompra;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public BigDecimal getCustoUnit() {
		return custoUnit;
	}
	public void setCustoUnit(BigDecimal custoUnit) {
		this.custoUnit = custoUnit;
	}
	
	public String getNomeProd() {
		return nomeProd;
	}
	public void setNomeProd(String nomeProd) {
		this.nomeProd = nomeProd;
	}
	public LoteVO(){}
	
	public LoteVO(int idLote, int idProd, String fornecedor, 
			String dataCompra, int quantidade, BigDecimal custoUnit)
	{
		setIdLote(idLote);
		setIdProd(idProd);
		setFornecedor(fornecedor);
		setDataCompra(dataCompra);
		setQuantidade(quantidade);
		setCustoUnit(custoUnit);
	}
}
