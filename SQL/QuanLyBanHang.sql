-- INSERT INTO `quanlybanhang`.`customer` (`cID`, `cName`, `cAge`) 
-- VALUES ('1', 'Minh Quan', '10');
-- INSERT INTO `quanlybanhang`.`customer` (`cID`, `cName`, `cAge`) 
-- VALUES ('2', 'Ngoc Oanh', '20');
-- INSERT INTO `quanlybanhang`.`customer` (`cID`, `cName`, `cAge`) 
-- VALUES ('3', 'Hong Ha', '50');

-- INSERT INTO `quanlybanhang`.`order` (`oID`, `cID`, `oDate`) 
-- VALUES ('1', '1', '2006/3/21');
-- INSERT INTO `quanlybanhang`.`order` (`oID`, `cID`, `oDate`) 
-- VALUES ('2', '2', '2006/3/23');
-- INSERT INTO `quanlybanhang`.`order` (`oID`, `cID`, `oDate`) 
-- VALUES ('3', '1', '2006/3/16');

-- INSERT INTO `quanlybanhang`.`product` (`pID`, `pName`, `pPrice`) 
-- VALUES ('1', 'May Giat', '3');
-- INSERT INTO `quanlybanhang`.`product` (`pID`, `pName`, `pPrice`) 
-- VALUES ('2', 'Tu Lanh', '5');
-- INSERT INTO `quanlybanhang`.`product` (`pID`, `pName`, `pPrice`) 
-- VALUES ('3', 'Dieu Hoa', '7');
-- INSERT INTO `quanlybanhang`.`product` (`pID`, `pName`, `pPrice`) 
-- VALUES ('4', 'Quat', '1');
-- INSERT INTO `quanlybanhang`.`product` (`pID`, `pName`, `pPrice`) 
-- VALUES ('5', 'Bep Dien', '2');

-- INSERT INTO `quanlybanhang`.`orderdetail` (`oID`, `pID`, `odQTY`) 
-- VALUES ('1', '1', '3');
-- INSERT INTO `quanlybanhang`.`orderdetail` (`oID`, `pID`, `odQTY`) 
-- VALUES ('1', '3', '7');
-- INSERT INTO `quanlybanhang`.`orderdetail` (`oID`, `pID`, `odQTY`) 
-- VALUES ('1', '4', '2');
-- INSERT INTO `quanlybanhang`.`orderdetail` (`oID`, `pID`, `odQTY`) 
-- VALUES ('2', '1', '1');
-- INSERT INTO `quanlybanhang`.`orderdetail` (`oID`, `pID`, `odQTY`) 
-- VALUES ('3', '1', '8');
-- INSERT INTO `quanlybanhang`.`orderdetail` (`oID`, `pID`, `odQTY`) 
-- VALUES ('2', '5', '4');
-- INSERT INTO `quanlybanhang`.`orderdetail` (`oID`, `pID`, `odQTY`) 
-- VALUES ('2', '3', '3');

-- Hiển thị các thông tin gồm oID, oDate, oPrice của tất cả các hóa đơn trong bảng Order
-- select oID, oDate, oTotalPrice
-- from `quanlybanhang`.`orders`;

-- Hiển thị danh sách các khách hàng đã mua hàng, và danh sách sản phẩm được mua bởi các khách
-- select c.cID, c.cName, p.pName, odQTY
-- from customer as c join orders on c.cID = orders.cID
-- join orderdetail as ot on ot.oID = orders.oID
-- join product as p on p.pID = ot.pID;

-- Hiển thị tên những khách hàng không mua bất kỳ một sản phẩm nào
-- select cName, cID 
-- from customer as c
-- where NOT EXISTS (select cID from orders where c.cID = orders.cID);

-- Hiển thị mã hóa đơn, ngày bán và giá tiền của từng hóa đơn (giá một hóa đơn được tính bằng tổng giá bán của từng loại mặt hàng xuất hiện trong hóa đơn. 
-- Giá bán của từng loại được tính = odQTY*pPrice)
select o.oID, o.oDate, sum(od.odQTY * p.pPrice) as price
from orders as o
join orderdetail as od on o.oID = od.oID
join product as p on od.pID = p.pID
group by od.oID;

