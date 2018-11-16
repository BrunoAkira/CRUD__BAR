create database Bar

use Bar;

set dateformat dmy;
insert into Usuario(Nome,DataNascimento,DataAdmissao,Cargo,RG,Sexo,Login,Senha)
  values('João','21/05/1998','15/06/2018','Faxineiro','38.534.712-1','M','Login','Senha');
  select * from Usuario
create table Produto(
ID int primary key identity,
NomeProduto varchar(50) unique not null,
Descricao varchar(50),
TipoProduto varchar(50),
QtdTotal int default 0,
QtdVendida int default 0,
PrecoUnit float default 0
)

create table Lote (
IdLote int primary key identity,
IdProd int,
Fornecedor varchar(50) not null,
DataCompra varchar(50),
Quantidade int,
CustoUnit float,

CONSTRAINT fk_CodProd FOREIGN KEY (IdProd) REFERENCES Produto (Id)
)

create table Usuario (
ID int primary key identity,
Nome varchar(50) not null,
DataNascimento varchar(50) not null,
DataAdmissao varchar(50) not null,
Cargo varchar(50) not null,
RG varchar(50) not null,
Sexo varchar(50) not null,
Login varchar(50) not null,
Senha varchar(50) not null
)



create table Venda (
Id int primary key identity,
Comanda int not null,
Data varchar(50),
PrecoTotal float,
)


create table ItemVenda(
Id int primary key identity,
idvenda int foreign key references venda(Id),
idprod int foreign key references produto(Id), 
qtd int
);

