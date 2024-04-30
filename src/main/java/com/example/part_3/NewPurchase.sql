DROP PROCEDURE IF EXISTS NewPurchase;


DELIMITER //

CREATE PROCEDURE NewPurchase (
    IN p_id INT,
    IN p_employee_id INT,
    IN p_card_num VARCHAR(255),
    IN p_date VARCHAR(255)
)
BEGIN
    INSERT INTO Receipt (Customer_ID, Employee_ID, Date_Time, Payment_Card_Number)
    VALUES (p_id, p_employee_id, p_date, p_card_num);

    INSERT INTO Receipt_Items (Customer_ID, Employee_ID, Date_Time, Item_ID, Quantity)
    SELECT Customer_ID, p_employee_id, p_date, Item_ID, Quantity
    FROM Cart
    WHERE Customer_ID = p_id;

    DELETE FROM Cart WHERE Customer_ID = p_id;
END //

DELIMITER ;