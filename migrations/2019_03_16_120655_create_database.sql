USE [Master]
GO

CREATE DATABASE [TestDB] ON  PRIMARY
( NAME = N'TestDB', FILENAME = N'\FSASQLDBTestDB.mdf' ,
  SIZE = 2GB , MAXSIZE = 8GB, FILEGROWTH = 1GB )
LOG ON
( NAME = N'TestDB_log', FILENAME = N'\FSASQLDBTestDB_log.ldf' ,
  SIZE = 1GB , MAXSIZE = 2GB , FILEGROWTH = 10%)
GO

USE [TestDB]
GO



