package Venda;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import Produto.ProdutoDAO;
import Produto.ProdutoVO;

public class VendaController implements Initializable {
	
	VendaDAO vDAO = new VendaDAO();
	double total = 0;
	
    @FXML
    private TableView<ItemVendaVO> tbProduto;

    @FXML
    private TextField txtData;

    @FXML
    private TextField txtQtd;

    @FXML
    private Button btnRegistar;

    @FXML
    private TextField txtComanda;

    @FXML
    private Button btnAddProduto;

    @FXML
    private Button btnCancelar;

    @FXML
    private ComboBox<String> cbProduto;

    @FXML
    private TextField txtIdVenda;

    @FXML
    private TextField txtTotal;

    @FXML
    private Button btnBuscar;
    
    @FXML
    private Button btnRemoveProduto;
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void initialize(URL url, ResourceBundle rb) {
		txtTotal.setText(String.valueOf(total));
		txtData.setText(String.valueOf(LocalDate.now()));
		
		TableColumn colunaNome = new TableColumn("Produto");
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
		colunaNome.setEditable(true);

		TableColumn colunaQuantidade = new TableColumn("Quantidade");
		colunaQuantidade.setCellValueFactory(new PropertyValueFactory<>("qtd"));
		colunaQuantidade.setEditable(true);
		
		TableColumn colunaPreco = new TableColumn("Preço Unitario");
		colunaPreco.setCellValueFactory(new PropertyValueFactory<>("PrecoUnit"));
		colunaPreco.setEditable(true);
		
		tbProduto.getColumns().clear();
		tbProduto.getColumns().addAll(colunaNome, colunaQuantidade, colunaPreco);
		tbProduto.setEditable(true);
		colunaNome.setPrefWidth(200);
		colunaPreco.setPrefWidth(100);
                
		ObservableList<String> ul = FXCollections.observableArrayList();
		ProdutoDAO prodDAO = new ProdutoDAO();
		try {
			ul = prodDAO.ConsultaNomeProduto();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (ul != null)
		{
			cbProduto.getItems().clear();	
			for (String t : ul) {
				cbProduto.getItems().add(t);
			}			
			cbProduto.getSelectionModel().select(0);
		}
    }
    
    VendaVO GerarVenda()
    {
    	VendaDAO vDAO = new VendaDAO();

    	ProdutoDAO prodDAO = new ProdutoDAO();
    	
    	int id = 0;
    	if(txtIdVenda.getText().isEmpty())
    	{
    		
    		try {
				id = vDAO.ProximoID();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	else
    	{
    		id = Integer.parseInt(txtIdVenda.getText());
    	}

    	int comanda = Integer.parseInt(txtComanda.getText());
    	String data = txtData.getText();
    	float total = Float.parseFloat(txtTotal.getText());
    	

    	VendaVO v = new VendaVO(id,comanda,data,total);
    	for (ItemVendaVO row : tbProduto.getItems()) {
	    	
	    	ItemVendaVO iv = new ItemVendaVO();

	    	ProdutoVO p = new ProdutoVO();
	    	p.setNomeProduto(row.getNomeProduto());
	    	
	    	
	    	iv.setIdprod(prodDAO.BuscarID(p).getId());
	    	iv.setIdvenda(id);
	    	iv.setQtd(row.getQtd());
	    	
	    	v.lista.add(iv);
	    }

    	return v;
    }

    @FXML
    void addTable(ActionEvent event) {
		
		ItemVendaVO ivVO = new ItemVendaVO();
		ProdutoDAO prodDAO = new ProdutoDAO();
		ProdutoVO prodVO = new ProdutoVO();

		
        if(!cbProduto.getItems().isEmpty())
        {
    		ivVO.setNomeProduto(cbProduto.getValue().toString());
    		prodVO.setNomeProduto(cbProduto.getValue().toString());
    		prodVO = prodDAO.BuscarID(prodVO);
    		ivVO.setIdprod(prodVO.getId());
    		ivVO.setPrecoUnit(prodVO.getPrecoUnit());
        }
    	if(!txtQtd.getText().isEmpty())	
    		ivVO.setQtd(Integer.parseInt(txtQtd.getText()));
    	
			tbProduto.getItems().add(ivVO);
		total = 0;	
		for(ItemVendaVO iv : tbProduto.getItems())
			total += iv.getPrecoUnit() * iv.getQtd();
		txtTotal.setText(String.valueOf(total));
	}
    
    @FXML
    void RemoveTable(ActionEvent event) {
		
    	int i = tbProduto.getSelectionModel().getSelectedIndex();
    	tbProduto.getItems().remove(i);

		total = 0;	
		for(ItemVendaVO iv : tbProduto.getItems())
			total += iv.getPrecoUnit() * iv.getQtd();
		txtTotal.setText(String.valueOf(total));
	}

    @FXML
    void CadastrarVenda(ActionEvent event) {
    	VendaVO v =  GerarVenda();
    	//System.out.println(v.getId());
    	//System.out.println(v.getComanda());
    	//System.out.println(v.getData());
    	//System.out.println(v.getPrecoTotal());
    	
    	//for(ItemVendaVO iv : v.lista)
    	//{
    	//	System.out.println(iv.getId());
    	//	System.out.println(iv.getIdvenda());
    	//	System.out.println(iv.getIdprod());
    	//	System.out.println(iv.getQtd());
    	//}
    	vDAO.AddVenda(v);
    	
    	LimparTela(event);
    }
    
    @FXML
    void AlterarVenda(ActionEvent event) {

    	VendaVO v =  GerarVenda();

    	vDAO.UpdateVenda(v);
    		
        LimparTelaAlterar(event);
    }
    

    @FXML
    void DeletarVenda(ActionEvent event) {

    	VendaVO v =  GerarVenda();

    	vDAO.DeleteVenda(v);
	
		LimparTelaAlterar(event);
    }

    @FXML
    void LimparTela(ActionEvent event) {
    	
    	tbProduto.getItems().clear();

        txtData.clear();

        txtQtd.clear();

        txtComanda.clear();
        
        if(!cbProduto.getItems().isEmpty())
        	cbProduto.getSelectionModel().select(0);

        txtIdVenda.clear();
        
        total = 0;
        txtTotal.setText(String.valueOf(total));
    }
    
    @FXML
    void LimparTelaAlterar(ActionEvent event) {
    	tbProduto.setDisable(true);

    	txtData.setDisable(true);
    	
    	txtQtd.setDisable(true);

    	txtComanda.setDisable(true);

    	cbProduto.setDisable(true);
    	
    	txtTotal.setDisable(true);

    	txtIdVenda.setDisable(false);

        btnAddProduto.setDisable(true);
        
        btnRemoveProduto.setDisable(true);
    	
    	btnBuscar.setDisable(false);
    	
    	btnRegistar.setDisable(true);
    	
    	btnCancelar.setDisable(true);
    	
    	LimparTela(event);
    }

    @FXML
    void BuscarId(ActionEvent event) {
    	VendaVO v = new VendaVO();
    	v.setId(Integer.parseInt(txtIdVenda.getText()));
    	VendaVO aux = vDAO.BuscarVenda(v);
    	if(aux != null)
    	{	
    		txtComanda.setText(String.valueOf(aux.getComanda()));
    		  
    		txtData.setText(aux.getData());
    		
			total = 0;
    		tbProduto.getItems().clear();
    		
    		for(ItemVendaVO iv : aux.lista)
    		{
    			tbProduto.getItems().add(iv);
    			total += iv.getPrecoUnit() * iv.getQtd();
    		}
    		txtTotal.setText(String.valueOf(total));
    	
    	tbProduto.setDisable(false);

    	txtData.setDisable(false);
    	
    	txtQtd.setDisable(false);

    	txtComanda.setDisable(false);

    	cbProduto.setDisable(false);
    	
    	txtTotal.setDisable(false);

    	txtIdVenda.setDisable(true);

        btnAddProduto.setDisable(false);
        
        btnRemoveProduto.setDisable(false);
    	
    	btnBuscar.setDisable(true);
    	
    	btnRegistar.setDisable(false);
    	
    	btnCancelar.setDisable(false);
    	}
    }

}
