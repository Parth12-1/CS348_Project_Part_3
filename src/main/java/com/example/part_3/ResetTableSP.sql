DROP PROCEDURE IF EXISTS ResetTables;

DELIMITER //

CREATE PROCEDURE ResetTables() 
BEGIN
    DROP TABLE IF EXISTS Receipt_Items;
    DROP TABLE IF EXISTS Receipt;
    DROP TABLE IF EXISTS Hours_Worked;
	DROP TABLE IF EXISTS Cart;
    DROP TABLE IF EXISTS Item;
    DROP TABLE IF EXISTS Customer;
    DROP TABLE IF EXISTS Employee;
    

    CREATE TABLE Employee (
        ID INT PRIMARY KEY,
        Name VARCHAR(255),
        Email VARCHAR(255),
        Position VARCHAR(255),
        Commission_Percent DECIMAL(10, 2),
        Hourly_Rate DECIMAL(10, 2),
        Password VARCHAR(255)
    );

    CREATE TABLE Hours_Worked (
        Employee_ID INT,
        Date VARCHAR(255),
        Hours DECIMAL(5, 2),
        PRIMARY KEY (Employee_ID, Date),
        FOREIGN KEY (Employee_ID) REFERENCES Employee(ID)
    );

    CREATE TABLE Item (
        ID INT PRIMARY KEY,
        Name VARCHAR(255),
        Price DECIMAL(10, 2),
        Inventory INT
    );

    CREATE TABLE Customer (
        ID INT PRIMARY KEY,
        Name VARCHAR(255),
        Email VARCHAR(255),
        Password VARCHAR(255)
    );

    CREATE TABLE Receipt (
        Customer_ID INT,
        Employee_ID INT,
        Date_Time VARCHAR(255),
        Payment_Card_Number VARCHAR(255),
        PRIMARY KEY (Customer_ID, Employee_ID, Date_Time),
        FOREIGN KEY (Customer_ID) REFERENCES Customer(ID),
        FOREIGN KEY (Employee_ID) REFERENCES Employee(ID),
        INDEX (Date_Time)
    );

    CREATE TABLE Receipt_Items (
        Customer_ID INT,
        Employee_ID INT,
        Date_Time VARCHAR(255),
        Item_ID INT,
        Quantity INT,
        PRIMARY KEY (Customer_ID, Employee_ID, Date_Time, Item_ID),
        FOREIGN KEY (Customer_ID) REFERENCES Customer(ID),
        FOREIGN KEY (Employee_ID) REFERENCES Employee(ID),
        FOREIGN KEY (Date_Time) REFERENCES Receipt(Date_Time),
        FOREIGN KEY (Item_ID) REFERENCES Item(ID)
    );
    
    CREATE TABLE Cart (
		Customer_ID INT,
		Item_ID INT,
		Quantity INT,
		PRIMARY KEY (Customer_ID, Item_ID),
		FOREIGN KEY (Customer_ID) REFERENCES Customer(ID),
		FOREIGN KEY (Item_ID) REFERENCES Item(ID)
	);

    
    INSERT INTO Employee (ID, Name, Email, Position, Commission_Percent, Hourly_Rate, Password) VALUES 
        (1, 'John Doe', 'john.doe@example.com', 'Sales Manager', 0.15, 32, '1231'),
        (2, 'Jane Smith', 'jane.smith@example.com', 'Employee', 0.05, 27, '1232'),
        (3, 'Bob Johnson', 'bob.johnson@example.com', 'Employee', 0.10, 24, '1233'),
        (4, 'Alice Brown', 'alice.brown@example.com', 'Employee', 0.15, 18, '1234'),
        (5, 'Charlie Davis', 'charlie.davis@example.com', 'Employee', 0.05, 19, '1235'),
        (6, 'Diana Green', 'diana.green@example.com', 'Employee', 0.07, 21, '1236'),
        (7, 'Eva White', 'eva.white@example.com', 'Employee', 0.09, 24, '1237');

    INSERT INTO Customer (ID, Name, Email, Password) VALUES 
        (1, 'John Doe', 'john.doe@example.com','1231'),
        (2, 'Jane Smith', 'jane.smith@example.com', '1232'),
        (3, 'Bob Johnson', 'bob.johnson@example.com', '1233'),
        (4, 'Alice Brown', 'alice.brown@example.com', '1234'),
        (5, 'Charlie Davis', 'charlie.davis@example.com', '1235'),
        (6, 'Diana Green', 'diana.green@example.com', '1236'),
        (7, 'Eva White', 'eva.white@example.com', '1237');
        
	INSERT INTO Item (ID, Name, Price, Inventory) VALUES
		(1, 'Hammer', 15.99, 5),
		(2, 'Screwdriver Set', 12.49, 8),
		(3, 'Pliers', 9.99, 4),
		(4, 'Tape Measure', 7.95, 9),
		(5, 'Utility Knife', 14.75, 6),
		(6, 'Screw Assortment', 5.25, 7),
		(7, 'Wrench Set', 18.50, 3),
		(8, 'Drill Bit Set', 21.99, 2),
		(9, 'Flashlight', 8.99, 1),
		(10, 'Paint Brush Set', 11.25, 10);
        
        
	INSERT INTO Receipt (Customer_ID, Employee_ID, Date_Time, Payment_Card_Number) VALUES
		(1, 1, '04-01-2024', '1234567890123456'),
		(1, 2, '04-02-2024', '6543210987654321'),
		(1, 3, '04-03-2024', '9876543210123456'),
		(2, 4, '04-04-2024', '5678901234567890'),
		(3, 1, '04-07-2024', '1111222233334444'),
		(3, 2, '04-08-2024', '4444333322221111'),
		(3, 3, '04-09-2024', '8888777766665555'),
		(4, 4, '04-10-2024', '1234432156789876'),
		(5, 1, '04-13-2024', '1111333344445555'),
		(5, 2, '04-14-2024', '5555444433331111'),
		(5, 3, '04-15-2024', '9999888877776666'),
		(6, 4, '04-16-2024', '1357924680123456'),
		(1, 4, '03-15-2024', '1111222233334444'),
		(1, 3, '03-17-2024', '4444333322221111'),
		(1, 2, '03-18-2024', '8888777766665555'),
		(2, 1, '03-19-2024', '1234432156789876'),
		(3, 2, '03-23-2024', '5555444433331111'),
		(3, 1, '03-24-2024', '9999888877776666'),
		(4, 4, '03-27-2024', '9876987698769876'),
		(5, 3, '03-28-2024', '1111222233334444'),
		(5, 1, '03-30-2024', '8888777766665555'),
		(6, 4, '04-01-2024', '9876123454321543'),
		(6, 3, '04-02-2024', '9876987698769876');

	-- Generate random receipt items for each receipt
	INSERT INTO Receipt_Items (Customer_ID, Employee_ID, Date_Time, Item_ID, Quantity) VALUES
		-- Receipts for Customer_ID 1
		(1, 1, '04-01-2024', 1, 2),
		(1, 1, '04-01-2024', 2, 1),
		(1, 2, '04-02-2024', 3, 3),
		(1, 2, '04-02-2024', 4, 2),
		(1, 3, '04-03-2024', 5, 1),
		(1, 3, '04-03-2024', 6, 4),
		-- Receipts for Customer_ID 2
		(2, 4, '04-04-2024', 7, 2),
		(2, 4, '04-04-2024', 8, 1),
		-- Receipts for Customer_ID 3
		(3, 1, '04-07-2024', 3, 2),
		(3, 1, '04-07-2024', 4, 1),
		(3, 2, '04-08-2024', 5, 3),
		(3, 2, '04-08-2024', 6, 2),
		(3, 3, '04-09-2024', 7, 1),
		(3, 3, '04-09-2024', 8, 4),
		-- Receipts for Customer_ID 4
		(4, 4, '04-10-2024', 9, 2),
		(4, 4, '04-10-2024', 10, 1),
		(5, 1, '04-13-2024', 7, 2),
		(5, 2, '04-14-2024', 9, 3),
		(5, 2, '04-14-2024', 10, 2),
		(5, 3, '04-15-2024', 1, 1),
		(5, 3, '04-15-2024', 2, 4),
		(6, 4, '04-16-2024', 3, 2),
		(6, 4, '04-16-2024', 4, 1),
		-- Receipts for Customer_ID 1
		(1, 4, '03-15-2024', 7, 2),
		(1, 4, '03-15-2024', 8, 1),
		(1, 3, '03-17-2024', 9, 3),
		(1, 3, '03-17-2024', 10, 2),
		(1, 2, '03-18-2024', 1, 1),
		(1, 2, '03-18-2024', 2, 4),
		-- Receipts for Customer_ID 2
		(2, 1, '03-19-2024', 3, 2),
		(2, 1, '03-19-2024', 4, 1),
		-- Receipts for Customer_ID 3
		(3, 2, '03-23-2024', 1, 3),
		(3, 2, '03-23-2024', 2, 2),
		(3, 1, '03-24-2024', 3, 1),
		(3, 1, '03-24-2024', 4, 2),
		-- Receipts for Customer_ID 4
		(4, 4, '03-27-2024', 9, 1),
		(4, 4, '03-27-2024', 10, 4),
		-- Receipts for Customer_ID 5
		(5, 3, '03-28-2024', 1, 2),
		(5, 3, '03-28-2024', 2, 1),
		(5, 1, '03-30-2024', 5, 1),
		(5, 1, '03-30-2024', 6, 4),
		-- Receipts for Customer_ID 6
		(6, 4, '04-01-2024', 9, 3),
		(6, 4, '04-01-2024', 10, 2),
		(6, 3, '04-02-2024', 1, 1),
		(6, 3, '04-02-2024', 2, 4);
		

	-- Random data for Cart table for Customer_ID 1 and 2 only
	INSERT INTO Cart (Customer_ID, Item_ID, Quantity) VALUES
		(1, 1, 1),
		(1, 2, 2),
		(2, 3, 3),
		(2, 4, 1),
		(2, 5, 2);
		
		
	INSERT INTO Hours_Worked (Employee_ID, Date, Hours) VALUES
		(1, '03-19-2024', 4),
		(1, '03-24-2024', 7),
		(1, '03-30-2024', 5),
		(1, '04-01-2024', 6),
		(1, '04-07-2024', 8),
		(1, '04-13-2024', 8),
		(2, '03-18-2024', 8),
		(2, '03-23-2024', 7),
		(2, '04-02-2024', 6),
		(2, '04-08-2024', 8),
		(2, '04-14-2024', 7),
		(3, '03-17-2024', 8),
		(3, '03-28-2024', 7),
		(3, '04-02-2024', 8),
		(3, '04-03-2024', 9),
		(3, '04-09-2024', 6),
		(3, '04-15-2024', 8),
		(4, '03-15-2024', 8),
		(4, '03-27-2024', 8),
		(4, '04-01-2024', 8),
		(4, '04-04-2024', 8),
		(4, '04-10-2024', 8),
		(4, '04-16-2024', 8);

        
        

END //

DELIMITER ;

CALL ResetTables();