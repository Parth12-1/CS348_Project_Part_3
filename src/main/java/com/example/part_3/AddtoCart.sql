DROP PROCEDURE IF EXISTS AddToCart;


DELIMITER //

CREATE PROCEDURE AddToCart(
    IN p_Customer_ID INT,
    IN p_Item_ID INT,
    IN p_Quantity INT
)
BEGIN
    DECLARE existing_quantity INT;

    SELECT Quantity
    INTO existing_quantity
    FROM Cart
    WHERE Customer_ID = p_Customer_ID
      AND Item_ID = p_Item_ID;

    IF existing_quantity IS NOT NULL THEN
        UPDATE Cart
        SET Quantity = Quantity + p_Quantity
        WHERE Customer_ID = p_Customer_ID
          AND Item_ID = p_Item_ID;
    ELSE
        INSERT INTO Cart (Customer_ID, Item_ID, Quantity)
        VALUES (p_Customer_ID, p_Item_ID, p_Quantity);
    END IF;
END //


DELIMITER ;