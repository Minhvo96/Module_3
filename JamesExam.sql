-- select * from student;

-- select * from student
-- where status = true;

-- select * from subject
-- where Credit < 10;

-- select s.StudentID, s.StudentName, c.ClassName
-- from Student as s join Class as c on s.ClassID = c.ClassID
-- where c.ClassName = 'A1';

-- select s.StudentID, s.StudentName, sub.SubName, M.Mark
-- from Student as s join Mark as M on s.StudentID = M.StudentID
-- join Subject as sub on M.SubID = sub.SubID
-- where sub.SubName = 'CF';

-- select * from student
-- where StudentName like 'h%';

-- select * from class
-- where MONTH (StartDate) = 12; 

-- select * from subject
-- where Credit between 3 and 5;

-- update `quanlysinhvien`.`student`
-- set ClassID = '2'
-- where StudentName = 'Hung';

select s.StudentName, sub.SubName, m.Mark
from Student as s join mark as m on s.StudentID = m.StudentID 
join subject as sub on sub.SubID = m.SubID
order by Mark DESC, StudentName;

-- Hiển thị tất cả các thông tin môn học (bảng subject) có credit lớn nhất.
select s.subname, s.credit
from subject as s 
where s.credit = (select max(credit) from subject);

-- Hiển thị các thông tin môn học có điểm thi lớn nhất.
select s.subID, s.subName, m.mark as diemthilonnhat 
from subject as s join mark as m on s.SubID = m.SubID
where m.mark = (select max(mark) from mark);

select s.subID, s.subname, max(m.mark)
from subject as s join mark as m on s.SubID = m.SubID
group by s.subID, s.subname;

-- Hiển thị các thông tin sinh viên và điểm trung bình của mỗi sinh viên, xếp hạng theo thứ tự điểm giảm dần
select s.studentID, s.StudentName, avg(m.mark) as DTB
from student as s join mark as m on s.studentID = m.studentID
group by s.studentID
order by DTB DESC;

