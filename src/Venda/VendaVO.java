package Venda;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class VendaVO {

	public VendaVO(int id, int comanda, String data, float precoTotal) {
		Id = id;
		Comanda = comanda;
		Data = data;
		PrecoTotal = precoTotal;
	}
	

	public VendaVO() {}


	private int Id;
	private int Comanda;
	private String Data;
	private float PrecoTotal; 
	
	ObservableList<ItemVendaVO> lista = FXCollections.observableArrayList();

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getComanda() {
		return Comanda;
	}

	public void setComanda(int comanda) {
		Comanda = comanda;
	}

	public String getData() {
		return Data;
	}

	public void setData(String data) {
		Data = data;
	}

	public float getPrecoTotal() {
		return PrecoTotal;
	}

	public void setPrecoTotal(float precoTotal) {
		PrecoTotal = precoTotal;
	}
}
