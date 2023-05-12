CREATE TABLE IF NOT EXISTS public.Employee
(    

	employeeId bigint primary key, 
 	employeeEmail character varying(255) COLLATE pg_catalog."default" NOT NULL,
 	employeeName character varying(255) COLLATE pg_catalog."default" NOT NULL,
 	employeeAddress character varying(255) COLLATE pg_catalog."default" NOT NULL
 	

);
 
 ALTER TABLE IF EXISTS public.Employee
    OWNER to whd_admin;
