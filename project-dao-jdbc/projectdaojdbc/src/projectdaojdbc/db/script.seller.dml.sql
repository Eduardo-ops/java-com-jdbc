SELECT SELLER.*,DEPARTMENT.NAME AS DEPNAME
FROM SELLER INNER JOIN DEPARTMENT
ON SELLER.DEPARTMENTID = DEPARTMENT.ID
WHERE SELLER.ID = 3

SELECT SELLER.*,DEPARTMENT.NAME AS DEPNAME
FROM SELLER INNER JOIN DEPARTMENT
ON SELLER.DEPARTMENTID = DEPARTMENT.ID
WHERE DEPARTMENTID = 2
ORDER BY NAME

SELECT SELLER.*,DEPARTMENT.NAME AS DEPNAME
FROM SELLER INNER JOIN DEPARTMENT
ON SELLER.DEPARTMENTID = DEPARTMENT.ID
ORDER BY NAME

INSERT INTO SELLER (NAME, EMAIL, BIRTHDATE, BASESALARY, DEPARTMENTID)
VALUES (?, ?, ?, ?, ?)

UPDATE SELLER
SET NAME = ?, EMAIL = ?, BIRTHDATE = ?, BASESALARY = ?, DEPARTMENTID = ?
WHERE ID = ?

DELETE FROM SELLER
WHERE ID = ?