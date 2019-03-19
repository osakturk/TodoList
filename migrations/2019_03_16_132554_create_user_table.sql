CREATE TABLE [dbo].[Users](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[FIRSTNAME] [nvarchar](100) NOT NULL,
	[USERNAME] [nvarchar](50) NOT NULL,
	[PASSWORD] [varbinary](255) NOT NULL,
	[STATUS] [char](1) NOT NULL,
	[LOCALE] [varchar](5) NULL
	PRIMARY KEY (ID)
)

GO