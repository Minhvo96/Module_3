-- Tính số lượng đề tài mà một giáo viên đang thực hiện. 

drop procedure if exists sp_TinhSoLuongDeTai;
delimiter //
create procedure sp_TinhSoLuongDeTai (
	IN pMaGV VARCHAR(3) 
)
begin
	if (pMaGV is null or pMaGV = '') then
		select gv.magv, gv.hoten, count(*) as soluongdetai
		from giaovien as gv join thamgiadt as tgdt on gv.magv = tgdt.magv
		group by gv.magv;
    else
		select gv.magv, gv.hoten, count(*) as soluongdetai
		from giaovien as gv join thamgiadt as tgdt on gv.magv = tgdt.magv
        where pMaGV = gv.magv
        group by gv.magv, gv.hoten;
	end if;
end; //

-- Thực hiện thêm một phân công cho giáo viên thực hiện một công việc của đề tài:
-- Kiểm tra thông tin đầu vào hợp lệ: giáo viên phải tồn tại,
-- công việc phải tồn tại, thời gian tham gia phải > 0
-- Giáo viên chỉ tham gia đề tài cùng bộ môn với giáo viên làm chủ nhiệm đề tài đó..

-- Kiểm tra giáo viên có tồn tại không
delimiter // 
create procedure sp_KiemTraGiaoVien (
	IN pMaGV VARCHAR(3),
    OUT existGV boolean 
)
begin
	set existGV = exists (select gv.magv from giaovien as gv where pMaGV = gv.magv);
end; //

-- Kiểm tra công việc có tồn tại không
delimiter //
create procedure sp_KiemTraCongViec (
	IN pMaDT VARCHAR (3),
	IN pMaCV VARCHAR (1),
    OUT existCV boolean
)
begin
	set existCV = exists (
			select *
            from congviec as cv
            where pMaDT = cv.madt and pMaCV = cv.sott
            );
end; //

delimiter //
create procedure ssp_KiemTraCongViec (
	IN pMaDT VARCHAR (3),
	IN pMaCV VARCHAR (1),
    OUT existCV boolean
)
begin
	set existCV = exists (select cv.madt from congviec as cv where cv.madt = pMaDT) and exists (select cv.sott from congviec as cv where cv.sott = pMaCV);
end; //

-- Kiểm tra thời gian tham gia
delimiter //
create procedure sp_KiemTraThoiGian (
	IN pNgayBatDau DATE,
    IN pMaDT VARCHAR(3),
    IN pMaCV VARCHAR(1),
    OUT checkTime boolean
)
begin
	set @pNgayKetThuc = (select cv.ngaykt from congviec as cv where cv.madt = pMaDT and cv.sott = pMaCV);
	set @test = DATEDIFF (@pNgayKetThuc,pNgayBatDau);
    if (@test > 0) then 
		set checkTime = true;
	else 
		set checkTime = false;
    end if;
end; //


