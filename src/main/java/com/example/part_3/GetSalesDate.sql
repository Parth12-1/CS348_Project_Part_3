DELIMITER //

CREATE PROCEDURE GetSalesData(IN employeeId INT)
BEGIN
    -- Create a temporary table to store the total orders per date for employee_id = 2
    CREATE TEMPORARY TABLE IF NOT EXISTS Temp_Total_Orders (
        Date_Time VARCHAR(255),
        Total_Orders INT,
        PRIMARY KEY (Date_Time)
    );

    -- Insert data into the temporary table using the query
    INSERT IGNORE INTO Temp_Total_Orders (Date_Time, Total_Orders)
    SELECT
        DISTINCT Date_Time,
        COUNT(*) AS Total_Orders
    FROM
        Receipt
    WHERE
        Employee_ID = employeeId
    GROUP BY
        Date_Time;

    -- Main query using the temporary table to calculate Ave_Hours_Per_Customer
    SELECT
        r.Customer_ID,
        r.Employee_ID,
        r.Date_Time,
        c.Name AS Customer_Name,
        c.Email AS Customer_Email,
        ri.Item_ID,
        i.Name AS Item_Name,
        i.Price AS Item_Price,
        ri.Quantity,
        i.Price * ri.Quantity AS Total_Price,
		ROUND((i.Price * ri.Quantity) * (e.Commission_Percent), 2) AS Total_Commission,
        hw.Hours AS Total_Hours_Worked,
        hw.Hours / tto.Total_Orders AS Ave_Hours_Per_Customer
    FROM
        Receipt r
        INNER JOIN Customer c ON r.Customer_ID = c.ID
        INNER JOIN Receipt_Items ri ON r.Customer_ID = ri.Customer_ID
            AND r.Employee_ID = ri.Employee_ID
            AND r.Date_Time = ri.Date_Time
        INNER JOIN Item i ON ri.Item_ID = i.ID
        INNER JOIN Employee e ON r.Employee_ID = e.ID
        INNER JOIN Hours_Worked hw ON r.Employee_ID = hw.Employee_ID
            AND r.Date_Time = hw.Date
        LEFT JOIN Temp_Total_Orders tto ON r.Date_Time = tto.Date_Time
    WHERE
        r.Employee_ID = employeeId
    ORDER BY
        r.Customer_ID,
        r.Employee_ID,
        r.Date_Time;

    -- Drop the temporary table after use
    DROP TEMPORARY TABLE IF EXISTS Temp_Total_Orders;
END //

DELIMITER ;