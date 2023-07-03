--   CREATE TABLE `quanlydiemthi`.`monhoc` (
--   `MaMH` VARCHAR(20) PRIMARY KEY,
--   `TenMH` VARCHAR(50)
--   );
  
--   CREATE TABLE `bangdiem` (
--   `MaHS` varchar(20),
--   `MaMH` varchar(20),
--   `DiemThi` INT,
--   `NgayKT` datetime,
--   PRIMARY KEY (MaHS, MaMH),
--   FOREIGN KEY (MaHS) REFERENCES hocsinh(MaHS),
--   FOREIGN KEY (MaMH) REFERENCES monhoc(MaMH)
--   );

CREATE TABLE `giaovien` (
MaGV VARCHAR(20) PRIMARY KEY,
TenGV VARCHAR(20),
SDT VARCHAR(20)
);

ALTER TABLE MonHoc ADD MaGV VARCHAR(20);
ALTER TABLE MonHoc ADD CONSTRAINT FK_MaGV FOREIGN KEY (MaGV) REFERENCES giaovien(MaGV);
  
  