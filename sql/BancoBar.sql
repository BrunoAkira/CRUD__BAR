C:\Users\Programador\Desktop\CRUD__BAR-master
----------------------------CRIA BANCO DE DADOS------------------------------------------------------
create database Bar

use Bar;
-----------------------------------------------------------------------------------------------------

----------------------------------------CRIA AS TABELAS----------------------------------------------
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
idprod int, 
qtd int,
Preco float
);
------------------------INSERE USUARIO PRA PODER LOGAR--------------------------------------------
set dateformat dmy;
insert into Usuario(Nome,DataNascimento,DataAdmissao,Cargo,RG,Sexo,Login,Senha)
  values('João','21/05/1998','15/06/2018','Faxineiro','38.534.712-1','M','Login','Senha');

--------------------------------------------------------------------------------------------------

------------------------CRIA AS TRIGGERS----------------------------------------------------------
create trigger trg_onInsertLote on Lote
for INSERT
AS
	Update Produto 
	Set QtdTotal = QtdTotal + (select Quantidade from inserted)
	Where ID = (select IdProd from inserted);
	

----------------------------------------------------
create trigger trg_onDeleteLote on Lote
for Delete
AS
	Update Produto 
	Set QtdTotal = QtdTotal - (select Quantidade from deleted)
	Where ID = (select IdProd from deleted);

create trigger trg_onUpdateLote on Lote
for Update
AS
	Update Produto 
	Set QtdTotal = QtdTotal + (select Quantidade from inserted) - (select Quantidade from deleted)
	Where ID = (select IdProd from inserted);

------------------------------------------------		
create trigger trg_onInsertItemVenda on ItemVenda
for INSERT
AS

DECLARE @idins as int
DECLARE @qtdins as int
DECLARE @idProdins as int

DECLARE ins_cursor CURSOR FOR
    SELECT d.id,d.idprod,d.qtd
    FROM inserted d

OPEN ins_cursor;

FETCH NEXT FROM ins_cursor INTO @idins,@idProdins,@qtdins;

WHILE @@FETCH_STATUS=0
BEGIN
	Update Produto 
	Set QtdTotal = QtdTotal - @qtdins,
	QtdVendida = QtdVendida + @qtdins
	Where ID = @idProdins;
	Update ItemVenda
	Set Preco = (Select PrecoUnit from produto where id = @idProdins) 
	Where ID = @idins;
FETCH NEXT FROM ins_cursor INTO @idins,@idProdins,@qtdins;
END
CLOSE ins_cursor
DEALLOCATE ins_cursor
------------------------------------------------------------------

create trigger trg_onDeleteItemVenda on ItemVenda
for Delete
AS
DECLARE @qtddel as int
DECLARE @idProddel as int

DECLARE del_cursor CURSOR FOR
    SELECT d.idprod,d.qtd
    FROM deleted d

OPEN del_cursor;

FETCH NEXT FROM del_cursor INTO  @idProddel,@qtddel;

WHILE @@FETCH_STATUS=0
BEGIN
    Update Produto 
	Set QtdTotal = QtdTotal + @qtddel,
	QtdVendida = QtdVendida - @qtddel
	Where ID = @idProddel;
FETCH NEXT FROM del_cursor INTO @idProddel,@qtddel;
END
CLOSE del_cursor
DEALLOCATE del_cursor
-----------------------------------------------------------------------	

create trigger trg_onUpdateItemVenda on ItemVenda
for Update
AS
DECLARE @qtdins as int
DECLARE @idProdins as int
DECLARE @qtddel as int
DECLARE @idProddel as int

DECLARE upd_cursor CURSOR FOR
    SELECT i.idprod,i.qtd,d.idprod,d.qtd
    FROM inserted i inner join
    deleted d
    on i.id = d.id

OPEN upd_cursor;

FETCH NEXT FROM upd_cursor INTO  @idProdins,@qtdins,@idProddel,@qtddel;

WHILE @@FETCH_STATUS=0
BEGIN
    Update Produto 
	Set QtdTotal = QtdTotal - @qtdins + @qtddel,
	QtdVendida = QtdVendida + @qtdins - @qtddel
	Where ID =  @idProdins ;
FETCH NEXT FROM upd_cursor INTO  @idProdins,@qtdins,@idProddel,@qtddel;
END
CLOSE upd_cursor
DEALLOCATE upd_cursor
	
-----------------------------------------------------------------------------	

insert into Lote
values(12,'Russia','18/09/2018',15,12);

insert into produto
values('Coca Cola','Gerante','Refri',0,0,12.05);



