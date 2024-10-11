WITH query AS (
	SELECT s.id, s.name, s.surname, sol.score
 	FROM solutions sol
 	JOIN students s ON sol.studentID = s.id
)
SELECT id, name, surname, score from query
WHERE score = (SELECT MAX(score) FROM query);