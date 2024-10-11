 SELECT reviewers.id as rev_id, reviewers.surname as rev_surname,
    students.id as stud_id, students.name as stud_name, students.surname as stud_surname, AVG(solutions.score) as avg
 FROM solutions
 JOIN students ON solutions.studentID = students.id
 JOIN reviewers ON solutions.reviewerID = reviewers.id
 GROUP BY reviewers.id, students.id;