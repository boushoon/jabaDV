WITH query AS (
	SELECT r.id, r.surname, sol.score
 	FROM solutions sol
 	JOIN reviewers r ON sol.reviewerID = r.id
)
SELECT id, surname, score from query
WHERE score = (SELECT MAX(score) FROM query);