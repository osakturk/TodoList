CREATE TABLE TodoItems(
	[ID] BIGINT IDENTITY(1,1) NOT NULL,
	NAME nvarchar(50) NOT NULL,
	DESCRIPTION nvarchar(500) NOT NULL,
	CREATE_DATE datetime DEFAULT GETDATE(),
	DEADLINE datetime null,
	TODOLISTID BIGINT NOT NULL FOREIGN KEY REFERENCES TodoList(ID),
	STATUS tinyint not null default 1,
	COMPLETE tinyint not null default 0
)
GO