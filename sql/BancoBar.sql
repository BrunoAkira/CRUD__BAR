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

create trigger trg_onInsertLote on Lote
for INSERT
AS
	Update Produto 
	Set QtdTotal = QtdTotal + (select Quantidade from inserted)
	Where ID = (select IdProd from inserted);
	


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
	
	
	
	
alter trigger trg_onInsertItemVenda on ItemVenda
for INSERT
AS
DECLARE @qtdins as int
DECLARE @idProdins as int

DECLARE ins_cursor CURSOR FOR
    SELECT d.idprod,d.qtd
    FROM inserted d

OPEN ins_cursor;

FETCH NEXT FROM ins_cursor INTO @idProdins,@qtdins;

WHILE @@FETCH_STATUS=0
BEGIN
	Update Produto 
	Set QtdTotal = QtdTotal - @qtdins,
	QtdVendida = QtdVendida + @qtdins
	Where ID = @idProdins;
FETCH NEXT FROM ins_cursor INTO @idProdins,@qtdins;
END
CLOSE ins_cursor
DEALLOCATE ins_cursor

	


alter trigger trg_onDeleteItemVenda on ItemVenda
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
	

alter trigger trg_onUpdateItemVenda on ItemVenda
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
	
	

select * from produto

select * from lote
select * from ItemVenda

delete from lote
delete from produto
delete from Venda
delete from ItemVenda
insert into lote
values(12,'Russia','18/09/2018',1,12);

insert into produto
values('Coca Cola','Gerante','Refri',0,0,12.05);


select top 5 nomeproduto,qtdVendida from Produto where qtdVendida <> 0