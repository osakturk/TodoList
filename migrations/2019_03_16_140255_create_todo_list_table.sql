CREATE TABLE TodoList(
	ID BIGINT IDENTITY(1,1) NOT NULL,
	NAME varchar(50) NOT NULL,
	USERID BIGINT NOT NULL,
	STATUS tinyint not null
	PRIMARY KEY (ID)
)

GO

