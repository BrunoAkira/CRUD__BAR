package Padrao;

import java.io.IOException;

import Usuario.UsuarioDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PadraoController {

	
	UsuarioDAO usuadao = new UsuarioDAO();
	
    @FXML
    private BorderPane BorderPane;

    @FXML
    private Button btnApaga;

    @FXML
    private Button btnGrafico;

    @FXML
    private Button btnVenda;

    @FXML
    private Button btnSair;

    @FXML
    private Button btnAltera;

    @FXML
    private Button btnUsuario;

    @FXML
    private Button btnCadastro;

    @FXML
    private Button btnSobre;

    @FXML
    private Pane PaneGuia;

    @FXML
    private Button btnEstoque;

    @FXML
    private Button btnConsulta;
    
    @FXML
    private Button btnLote;
    
    @FXML
    private Button btnProduto;
    
    char tela;
    
    @FXML
    void AbrirTelaUsuario(ActionEvent event) {
    	try {
    		btnCadastro.setText("Cadastrar usuário");
			btnAltera.setText("Alterar usuário");
			btnApaga.setText("Excluir usuário");
			btnConsulta.setText("Consultar usuários");
			btnSair.setVisible(true);
			BorderPane.setCenter(FXMLLoader.load(getClass().getClassLoader().getResource("Usuario/ConsultaFuncionario.fxml")));
			tela = 'U';
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void AbrirTelaLote(ActionEvent event) {
    	try {
    		btnCadastro.setText("Cadastrar lote");
			btnAltera.setText("Alterar lote");
			btnApaga.setText("Excluir lote");
			btnConsulta.setText("Consultar lote");
			tela = 'L';
			BorderPane.setCenter(FXMLLoader.load(getClass().getClassLoader().getResource("Lote/ConsultaLote.fxml")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @FXML
    void AbrirTelaProduto(ActionEvent event) {
    	try {
    		btnCadastro.setText("Cadastrar produto");
			btnAltera.setText("Alterar produto");
			btnApaga.setText("Excluir produto");
			btnConsulta.setText("Consultar produto");
			tela = 'P';
			BorderPane.setCenter(FXMLLoader.load(getClass().getClassLoader().getResource("Produto/ConsultaProduto.fxml")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void AbrirTelaVenda(ActionEvent event) {
    	try {
    		btnCadastro.setText("Cadastrar venda");
			btnAltera.setText("Alterar venda");
			btnApaga.setText("Excluir venda");
			btnConsulta.setText("Consultar venda");
			tela = 'V';
			BorderPane.setCenter(FXMLLoader.load(getClass().getClassLoader().getResource("Venda/ConsultaVenda.fxml")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void AbrirTelaGrafico(ActionEvent event) {
    	try {
    		btnCadastro.setText("Lucro mensal");
			btnAltera.setText("Estoque");
			btnApaga.setText("Top 5 produtos");
			btnConsulta.setText("");
			tela = 'G';
			BorderPane.setCenter(FXMLLoader.load(getClass().getClassLoader().getResource("Grafico/Top5.fxml")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void AbrirTelaSobre(ActionEvent event) {
    	try {
    		btnCadastro.setText("");
			btnAltera.setText("");
			btnApaga.setText("");
			btnConsulta.setText("");
    		tela = 'S';
			BorderPane.setCenter(FXMLLoader.load(getClass().getClassLoader().getResource("Sobre/TelaSobre.fxml")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @FXML
    void AbrirTelaCadastro(ActionEvent event) {
    	if(tela == 'U')
    	{
    		try {
				BorderPane.setCenter(FXMLLoader.load(getClass().getClassLoader().getResource("Usuario/TelaFuncionarioCadastro.fxml")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	else if(tela == 'L')
    	{
    		try {
				BorderPane.setCenter(FXMLLoader.load(getClass().getClassLoader().getResource("Lote/TelaLoteCadastro.fxml")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	else if(tela == 'P')
    	{
    		try {
				BorderPane.setCenter(FXMLLoader.load(getClass().getClassLoader().getResource("Produto/TelaProdutoCadastro.fxml")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	else if(tela == 'V')
    	{
    		try {
				BorderPane.setCenter(FXMLLoader.load(getClass().getClassLoader().getResource("Venda/TelaVendaCadastro.fxml")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	else if(tela == 'G')
    	{
    		try {
				BorderPane.setCenter(FXMLLoader.load(getClass().getClassLoader().getResource("Grafico/LucroMensal.fxml")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    @FXML
    void AbrirTelaAltera(ActionEvent event) {
    	if(tela == 'U')
    	{
    		try {
				BorderPane.setCenter(FXMLLoader.load(getClass().getClassLoader().getResource("Usuario/TelaFuncionarioAlteração.fxml")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	else if(tela == 'L')
    	{
    		try {
				BorderPane.setCenter(FXMLLoader.load(getClass().getClassLoader().getResource("Lote/TelaLoteAlteração.fxml")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	else if(tela == 'P')
    	{
    		try {
				BorderPane.setCenter(FXMLLoader.load(getClass().getClassLoader().getResource("Produto/TelaProdutoAlteração.fxml")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	else if(tela == 'V')
    	{
    		try {
				BorderPane.setCenter(FXMLLoader.load(getClass().getClassLoader().getResource("Venda/TelaVendaAlteração.fxml")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	else if(tela == 'G')
    	{
    		try {
				BorderPane.setCenter(FXMLLoader.load(getClass().getClassLoader().getResource("Grafico/Estoque.fxml")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    @FXML
    void AbrirTelaApaga(ActionEvent event) {
    	if(tela == 'U')
    	{
    		try {
				BorderPane.setCenter(FXMLLoader.load(getClass().getClassLoader().getResource("Usuario/TelaFuncionarioApagar.fxml")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	else if(tela == 'L')
    	{
    		try {
				BorderPane.setCenter(FXMLLoader.load(getClass().getClassLoader().getResource("Lote/TelaLoteApagar.fxml")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	else if(tela == 'P')
    	{
    		try {
				BorderPane.setCenter(FXMLLoader.load(getClass().getClassLoader().getResource("Produto/TelaProdutoApagar.fxml")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	else if(tela == 'V')
    	{
    		try {
				BorderPane.setCenter(FXMLLoader.load(getClass().getClassLoader().getResource("Venda/TelaVendaApagar.fxml")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	else if(tela == 'G')
    	{
    		try {
				BorderPane.setCenter(FXMLLoader.load(getClass().getClassLoader().getResource("Grafico/Top5.fxml")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    @FXML
    void AbrirTelaConsulta(ActionEvent event) {
    	if(tela == 'U')
    	{
    		try {
				BorderPane.setCenter(FXMLLoader.load(getClass().getClassLoader().getResource("Usuario/ConsultaFuncionario.fxml")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	else if(tela == 'L')
    	{
    		try {
				BorderPane.setCenter(FXMLLoader.load(getClass().getClassLoader().getResource("Lote/ConsultaLote.fxml")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	else if(tela == 'P')
    	{
    		try {
				BorderPane.setCenter(FXMLLoader.load(getClass().getClassLoader().getResource("Produto/ConsultaProduto.fxml")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	else if(tela == 'V')
    	{
    		try {
				BorderPane.setCenter(FXMLLoader.load(getClass().getClassLoader().getResource("Venda/ConsultaVenda.fxml")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    Stage Login;
    @FXML
    void Sair(ActionEvent event) throws IOException {
		Stage tela = (Stage)btnSair.getScene().getWindow();
		tela.close();
		
		
    }
}
