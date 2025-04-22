[DB Queries.txt](https://github.com/user-attachments/files/19846371/DB.Queries.txt)M. Hamza Faheem   -  FAST NUCES

Fifth Corp Spring Boot Project

Tools:
 pgAdmin 4 for hosting Postgre SQL Server
 DBeaver for Database Operations
 Eclipse IDE For Java Development
 Spring fox for Swagger UI
 Github for repository
 Spring Initializer for Spring Boot Project
 Lombok for Getter / Setters etc

Setup:
 Setup database from pgAdmin 4 and start hosting it on localhost server.
 Create database connection from DBeaver according to credentials set while creating database on pgAdmin.
 Create Spring Boot project using Spring Initializer.
 Unzip created project and open it in Eclipse Workspace.
 Maven Clean / Install , Maven Update Project for downloading neccesary dependencies.
 Verify database connections from application.yml file so that it connects succesfully.
 Run project as Spring Boot application. 
 Run APIs through Postman. ( I have attached reference POSTMAN Collection ).
 You may confirm creation / Updation in Database as well by running select queries attached.

 Note: All Queries related to creation of tables etc are attached for reference.

ER Diagram: 
![ER Diagram](https://github.com/user-attachments/assets/07a44c77-47bf-47b2-9d4d-8e8d4008f2d1)

TEST DATA EXPLANATION:
Contact with Id 1 is Landlord of Unit Id = 1
Contact with Id 2 is Landlord of Unit Id = 2 and Tenant in Unit Id = 1
Lease explaining these relations in detail.

Queries For DB:

-------------------------------------------------------------------------------------------------------------------------------------------
--                 Hamza Faheem
-------------------------------------------------------------------------------------------------------------------------------------------
--            Tables Insertion Queries
-------------------------------------------------------------------------------------------------------------------------------------------

CREATE TABLE contact (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(10) CHECK (type IN ('LANDLORD', 'TENANT')),
    contact_info TEXT
);

	CREATE TABLE unit (
	    id SERIAL PRIMARY KEY,
	    unit_number VARCHAR(20) NOT NULL,
	    type VARCHAR(50), -- e.g., Apartment, Office, etc.
	    location TEXT,
	    value NUMERIC(12, 2),
	    status VARCHAR(10) CHECK (status IN ('Vacant', 'Leased')),
	    owner_id INTEGER REFERENCES contact(id) ON DELETE SET NULL
	);

// I removed contraint as well before adding new Constraint

ALTER TABLE unit
ADD CONSTRAINT unit_status_check CHECK (status IN ('VACANT', 'LEASED'));


CREATE TABLE lease (
    id SERIAL PRIMARY KEY,
    unit_id INTEGER REFERENCES unit(id) ON DELETE CASCADE,
    tenant_id INTEGER REFERENCES contact(id) ON DELETE SET NULL,
    landlord_id INTEGER REFERENCES contact(id) ON DELETE SET NULL,
    start_date DATE NOT NULL,
    duration_months INTEGER,
    rent_amount NUMERIC(10, 2),
    payment_frequency VARCHAR(20) CHECK (payment_frequency IN ('MONTHLY', 'QUARTERLY', 'YEARLY'))
);

-------------------------------------------------------------------------------------------------------------------------------------------
--            Select Queries
-------------------------------------------------------------------------------------------------------------------------------------------


select * from contact c ;

select * from lease l ;

select * from unit u ;



-------------------------------------------------------------------------------------------------------------------------------------------
--            Test Data Queries
-------------------------------------------------------------------------------------------------------------------------------------------


INSERT INTO contact (name, type, contact_info)
VALUES ('Hamza', 'LANDLORD', 'hamza123@example.com');

INSERT INTO contact (name, type, contact_info)
VALUES ('Faheem', 'TENANT', 'faheem@example.com');

INSERT INTO unit (unit_number, type, location, value, status, owner_id)
VALUES ('A1', 'Apartment', '123 Street', 150000.00, 'Leased', 1);

INSERT INTO lease (unit_id, tenant_id, landlord_id, start_date, duration_months, rent_amount, payment_frequency)
VALUES (1, 1, 2, '2025-04-21', 12, 1200.00, 'Monthly');


