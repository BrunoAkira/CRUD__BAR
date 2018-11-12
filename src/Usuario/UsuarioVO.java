package Usuario;

public class UsuarioVO {
	
	public UsuarioVO (int id, String nome, String datanasc, String dataadmissao, String cargo, String RG,String sexo, String Login, String Senha){
		this.Id = id;
		this.Nome = nome;
		this.Datanasc = datanasc;
		this.Dataadmissao = dataadmissao; 
		this.Cargo = cargo;
		this.RG = RG;
		this.Sexo = sexo;
		this.Login = Login;
		this.Senha = Senha;
	}
	
	public UsuarioVO (){}
	
	/**
	 * @return the nome
	 */
	public String getNome() {
		return Nome;
	}
	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.Nome = nome;
	}
	/**
	 * @return the dataadmissao
	 */
	public String getDataadmissao() {
		return Dataadmissao;
	}
	/**
	 * @param dataadmissao the dataadmissao to set
	 */
	public void setDataadmissao(String dataadmissao) {
		this.Dataadmissao = dataadmissao;
	}
	/**
	 * @return the cargo
	 */
	public String getCargo() {
		return Cargo;
	}
	/**
	 * @param cargo the cargo to set
	 */
	public void setCargo(String cargo) {
		this.Cargo = cargo;
	}
	/**
	 * @return the rG
	 */
	public String getRG() {
		return RG;
	}
	/**
	 * @param rG the rG to set
	 */
	public void setRG(String rG) {
		RG = rG;
	}
	/**
	 * @return the usuario
	 */
	public String getLogin() {
		return Login;
	}
	/**
	 * @param usuario the usuario to set
	 */
	public void setLogin(String login) {
		Login = login;
	}
	
	/**
	 * @return the senha
	 */
	public String getSenha() {
		return Senha;
	}
	/**
	 * @param usuario the usuario to set
	 */
	public void setSenha(String senha) {
		Senha = senha;
	}
	
	
	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getDatanasc() {
		return Datanasc;
	}

	public void SetDatanasc(String datanasc) {
		Datanasc = datanasc;
	}

	public String getSexo() {
		return Sexo;
	}

	public void setSexo(String sexo) {
		Sexo = sexo;
	}
	
	
	int Id;
	String Nome;
	String Datanasc;
	String Dataadmissao; 
	String Cargo;
	String RG;
	String Sexo;
	String Login;
	String Senha;
}
