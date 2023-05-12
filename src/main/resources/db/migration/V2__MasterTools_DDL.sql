CREATE TABLE IF NOT EXISTS public.ToolsMain
(    

	itemId character varying(255) COLLATE pg_catalog."default" NOT NULL primary key, 
 	description character varying(255) COLLATE pg_catalog."default" NOT NULL,
 	globalDescription character varying(255) COLLATE pg_catalog."default" NOT NULL,
 	searchA character varying(255) COLLATE pg_catalog."default" NOT NULL,
 	searchB character varying(255) COLLATE pg_catalog."default" NOT NULL,
 	supplier character varying(255) COLLATE pg_catalog."default" NOT NULL

);
 
 ALTER TABLE IF EXISTS public.project
    OWNER to whd_admin;

    
CREATE TABLE IF NOT EXISTS public.ToolsRefrence
(    
	itemId character varying(255)  COLLATE pg_catalog."default" NOT NULL  primary key, 
 	location character varying(255) COLLATE pg_catalog."default" NOT NULL,
 	itemGroup character varying(255) COLLATE pg_catalog."default" NOT NULL,
 	orderingCode character varying(255) COLLATE pg_catalog."default" NOT NULL,
 	globalToolNo character varying(255) COLLATE pg_catalog."default" NOT NULL,
 	dimensionParameters character varying(255) COLLATE pg_catalog."default" NOT NULL,
 	combinable_numberLink character varying(255) COLLATE pg_catalog."default" NOT NULL,
 	CONSTRAINT fk_toolsRefrence_toolsMaster FOREIGN KEY (itemId) 
 		REFERENCES public.ToolsMain (itemId) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
 
 ALTER TABLE IF EXISTS public.project
    OWNER to whd_admin;