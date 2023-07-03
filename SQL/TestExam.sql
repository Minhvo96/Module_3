-- Câu 1: Cho biết số lượng giáo viên của toàn trường
select count(*) 
from giaovien;

-- Câu 2: Cho biết số lượng giáo viên của bộ môn HTTT
select count(MAGV) as SLGV_HTTT 
from giaovien
where MABM = 'HTTT';

-- Câu 3: Tính số lượng giáo viên có người quản lý về mặt chuyên môn
select count(GVQLCM) as SLGV_QLCM 
from giaovien;

-- Câu 4: Tính số lượng giáo viên làm nhiệm vụ quản lý chuyên môn cho giáo viên khác mà thuộc bộ môn HTTT.
select count(GVQLCM) as SLGV_QLCM_HTTT 
from giaovien
where MABM = 'HTTT';

-- Câu 5:  Tính lương trung bình của giáo viên bộ môn Hệ thống thông tin
select avg(luong) as LuongTB_HTTT 
from giaovien
where MABM = 'HTTT';

-- Câu 6: Với mỗn bộ môn cho biết bộ môn (MAMB) và số lượng giáo viên của bộ môn đó.
select MABM, Count(*) 
from giaovien
group by MABM;

-- Câu 7: Với mỗi giáo viên, cho biết MAGV và số lượng công việc mà giáo viên đó có tham gia.
select gv.magv, gv.hoten, count(*) as soluongCV 
from giaovien as gv join thamgiadt as tgdt on gv.magv = tgdt.magv
join congviec as cv on cv.madt = tgdt.madt
group by gv.magv;

-- Câu 8: Với mỗi giáo viên, cho biết MAGV và số lượng đề tài mà giáo viên đó có tham gia.
select gv.MAGV, gv.HOTEN, count(tgdt.MADT) as soluongdetai
from giaovien as gv join thamgiadt as tgdt on gv.magv = tgdt.magv
group by gv.magv;

-- Câu 9:  Với mỗi bộ môn, cho biết số đề tài mà giáo viên của bộ môn đó chủ trì.
select gv.MABM, count(*) as sodetaichutri
from giaovien as gv join detai as dt on gv.MAGV = dt.GVCNDT
group by gv.mabm;

-- Câu 10: Với mỗi bộ môn, cho biết tên bộ môn và số lượng giáo viên của bộ môn đó.
select bm.TENBM, count(gv.mabm) as slgv
from bomon as bm join giaovien as gv on bm.MABM = gv.MABM
group by gv.mabm;