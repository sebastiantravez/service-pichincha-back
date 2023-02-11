--CLIENTS
CREATE TABLE public.clients (
	client_id numeric(16, 6) NOT NULL,
	full_name varchar(200) NULL,
	gender_person varchar(50) NULL,
	age int4 NULL,
	dni varchar(20) NULL,
	identification_pattern varchar(50) NULL,
	address varchar(250) NULL,
	phone varchar(50) NULL,
	create_date timestamp NULL,
	"password" varchar(100) NULL,
	status bool NULL DEFAULT true,
	CONSTRAINT pk_client_id PRIMARY KEY (client_id)
);

--ACCOUNTS
CREATE TABLE public.accounts (
	account_id numeric(16, 6) NOT NULL,
	account_number varchar(100) NULL,
	account_type varchar(20) NULL,
	initial_amount numeric(16, 6) NULL DEFAULT 0.00,
	status bool NULL DEFAULT true,
	client_id numeric(16, 6) NULL,
	create_date timestamp NULL,
	CONSTRAINT accounts_account_number_key UNIQUE (account_number),
	CONSTRAINT ck_account_type_client_unique UNIQUE (account_type, client_id),
	CONSTRAINT pk_accounts PRIMARY KEY (account_id)
);
ALTER TABLE public.accounts ADD CONSTRAINT fk_accounts_on_client FOREIGN KEY (client_id) REFERENCES public.clients(client_id);

--MOVEMENTS
CREATE TABLE public.movements (
	movement_id numeric(16, 6) NOT NULL,
	movement_date timestamp NULL,
	movement_type varchar(20) NULL,
	transaction_type varchar(20) NULL,
	observation varchar(255) NULL,
	movement_amount numeric(16, 6) NULL,
	balance_available numeric(16, 6) NULL,
	account_id numeric(16, 6) NULL,
	CONSTRAINT pk_movements PRIMARY KEY (movement_id)
);
ALTER TABLE public.movements ADD CONSTRAINT fk_movements_on_account FOREIGN KEY (account_id) REFERENCES public.accounts(account_id);