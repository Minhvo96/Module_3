-- Hiển thị số lượng sinh viên ở từng nơi.
select s.Address, count(s.Address) as soluongsinhvien
from student as s
group by s.Address;

-- Tính điểm trung bình các môn học của mỗi học viên, ĐTB lớn hơn 10.
select s.studentID, s.StudentName, avg(mark)
from student as s join mark as m on s.studentID = m.studentID
group by s.studentID, s.StudentName
having avg(mark) > 10;

-- Hiển thị thông tin các học viên có điểm trung bình lớn nhất.
select s.studentID, s.studentName, avg(mark)
from student as s join mark as m on s.studentID = m.studentID
group by s.studentID, s.StudentName
having avg(mark) >= all (select avg(mark) from mark group by mark.StudentID);