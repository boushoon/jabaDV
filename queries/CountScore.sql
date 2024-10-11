SELECT s.id as stud_id, s.name as stud_name, s.surname as stud_surname,
r.id as rev_id, r.surname as rev_surname, COUNT(sol.id) as rev_count from solutions sol
JOIN students s ON sol.studentID = s.id
JOIN reviewers r ON sol.reviewerID = r.id
GROUP BY s.id, r.id
ORDER BY COUNT(sol.id) DESC;