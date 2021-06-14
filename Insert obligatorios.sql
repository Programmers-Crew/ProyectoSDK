/*Insert into civilstatus(statusDesc) values('Soltero'),( 'Casado'),('Divorciado');

insert into typedocuments(typeDesc) values ('DPI'),('Licencia');

insert into typesex(sexDesc) values ('Masculino'),('Soltero');
*/

/*
DELIMITER $$
	create procedure Sp_AddData(id int(30), fn varchar(50), sn varchar(50), fl varchar(50), sl varchar(50), bd date, age int(5), bp varchar(50), expeDate date, expiDate date, addres varchar(50), nati varchar(50), shipper varchar(50), sex int(5), civil int(5), MRX varchar(50), ofo varchar(50), ofs varchar(50), imgF varchar(50), imgL varchar(50), document int(5))
		begin 
			insert into documentdata(documentId,firstName,secondName,firstLasName,secondLasName,birthDate,age,birthPlace,expeditionDate,expirationDate,address,nationality,shipper,sex,civilStatus,textMRX,optionalFieldOne,optionalFieldSecond,imgFront,imgLater,documentTyoe)
				values(id, fn, sn, fl, sl, bd, age, bp, expeDate, expiDate, addres, nati, shipper, sex, civil, MRX, ofo, ofs, imgF, imgL, document);
        end $$
DELIMITER ;
*/


DELIMITER $$
	create procedure Sp_ListSexType(idBuscado int(5))
		begin 
			select sexId , sexDesc
				from typesex
					where sexId = idBuscado;
        end $$
DELIMITER ;

#call Sp_AddData(3047, 'Diego', 'Fernando', 'Gonzalez', 'Gonzalez', '2003-04-11', 18, 'Guatemala, San miguel', '2021-04-16', '2031-04-15', 'Guatemala zona 10', 'Guatemalteco', 'GTM', 1, 1, '1459>>>><<<<', 'oPTIONAL1', 'oPTIONAL2', 'jasdhnjkashdnja.img', 'jasdhnjkashdnja.img', 1)