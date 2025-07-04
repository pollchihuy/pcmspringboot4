SET IDENTITY_INSERT [projectz].[MstKategoriProduk] ON
;

INSERT INTO [projectz].[MstKategoriProduk] ([CreatedAt], [CreatedBy], [ID], [ModifiedAt], [ModifiedBy], [NamaProduk], [Deskripsi], [Notes]) VALUES (N'2025-07-02 19:44:34.145000', N'1', N'1', NULL, NULL, N'Barang Selundupan', N'Segala sesuatu yang ilegal', N' Ini hanya Catatan Saja Untuk Contoh')
;

INSERT INTO [projectz].[MstKategoriProduk] ([CreatedAt], [CreatedBy], [ID], [ModifiedAt], [ModifiedBy], [NamaProduk], [Deskripsi], [Notes]) VALUES (N'2025-07-02 19:44:38.528000', N'1', N'2', NULL, NULL, N'Barang Selundupan1', N'Segala sesuatu yang ilegal1', N' Ini hanya Catatan Saja Untuk Contoh')
;

INSERT INTO [projectz].[MstKategoriProduk] ([CreatedAt], [CreatedBy], [ID], [ModifiedAt], [ModifiedBy], [NamaProduk], [Deskripsi], [Notes]) VALUES (N'2025-07-02 19:44:45.030000', N'1', N'3', NULL, NULL, N'Barang Selundupan2', N'Segala sesuatu yang ilegal2', N' Ini hanya Catatan Saja Untuk Contoh')
;

SET IDENTITY_INSERT [projectz].[MstKategoriProduk] OFF
;
    
    SET IDENTITY_INSERT [projectz].[MstProduk] ON
;

INSERT INTO [projectz].[MstProduk] ([Stok], [CreatedAt], [CreatedBy], [ID], [IDKategoriProduk], [ModifiedAt], [ModifiedBy], [Warna], [Merk], [Model], [NamaProduk], [DeskripsiProduk]) VALUES (N'200', N'2025-07-04 21:10:02.059000', N'1', N'1', N'2', NULL, NULL, N'abu abu monyet', N'Toyota', N'panjang tak berujung', N'Kain Pel Super', N'pokoknya bersih deh semua')
;

INSERT INTO [projectz].[MstProduk] ([Stok], [CreatedAt], [CreatedBy], [ID], [IDKategoriProduk], [ModifiedAt], [ModifiedBy], [Warna], [Merk], [Model], [NamaProduk], [DeskripsiProduk]) VALUES (N'200', N'2025-07-04 21:15:08.861000', N'1', N'2', N'2', NULL, NULL, N'abu abu monyet1', N'Toyot1a', N'panjang tak berujung1', N'Kain Pel Super1', N'pokoknya bersih deh semua1')
;

INSERT INTO [projectz].[MstProduk] ([Stok], [CreatedAt], [CreatedBy], [ID], [IDKategoriProduk], [ModifiedAt], [ModifiedBy], [Warna], [Merk], [Model], [NamaProduk], [DeskripsiProduk]) VALUES (N'200', N'2025-07-04 21:15:24.902000', N'1', N'3', N'1', NULL, NULL, N'abu abu monyet2', N'Toyota2', N'panjang tak berujung2', N'Kain Pel Super2', N'pokoknya bersih deh semua2')
;

INSERT INTO [projectz].[MstProduk] ([Stok], [CreatedAt], [CreatedBy], [ID], [IDKategoriProduk], [ModifiedAt], [ModifiedBy], [Warna], [Merk], [Model], [NamaProduk], [DeskripsiProduk]) VALUES (N'200', N'2025-07-04 21:15:38.466000', N'1', N'4', N'3', NULL, NULL, N'abu abu monyet3', N'Toyota3', N'panjang tak berujung3', N'Kain Pel Super3', N'pokoknya bersih deh semua3')
;

INSERT INTO [projectz].[MstProduk] ([Stok], [CreatedAt], [CreatedBy], [ID], [IDKategoriProduk], [ModifiedAt], [ModifiedBy], [Warna], [Merk], [Model], [NamaProduk], [DeskripsiProduk]) VALUES (N'200', N'2025-07-04 21:15:54.517000', N'1', N'5', N'2', NULL, NULL, N'abu abu monyet4', N'Toyota4', N'panjang tak berujung4', N'Kain Pel Super4', N'pokoknya bersih deh semua4')
;

INSERT INTO [projectz].[MstProduk] ([Stok], [CreatedAt], [CreatedBy], [ID], [IDKategoriProduk], [ModifiedAt], [ModifiedBy], [Warna], [Merk], [Model], [NamaProduk], [DeskripsiProduk]) VALUES (N'200', N'2025-07-04 21:16:10.172000', N'1', N'6', N'1', NULL, NULL, N'abu abu monyet5', N'Toyota5', N'panjang tak berujung5', N'Kain Pel Super5', N'pokoknya bersih deh semua5')
;

SET IDENTITY_INSERT [projectz].[MstProduk] OFF
;
    
    SET IDENTITY_INSERT [projectz].[MstSupplier] ON
;

INSERT INTO [projectz].[MstSupplier] ([CreatedAt], [CreatedBy], [ID], [ModifiedAt], [ModifiedBy], [NamaSupplier], [AlamatSupplier]) VALUES (N'2025-07-04 21:33:45.000000', N'1', N'1', NULL, NULL, N'Garuda Group', N'jln Hongkong 12 palace')
;

INSERT INTO [projectz].[MstSupplier] ([CreatedAt], [CreatedBy], [ID], [ModifiedAt], [ModifiedBy], [NamaSupplier], [AlamatSupplier]) VALUES (N'2025-07-04 21:33:45.000000', N'1', N'2', NULL, NULL, N'Cumi Group', N'jln Capcay 13 menara prima')
;

INSERT INTO [projectz].[MstSupplier] ([CreatedAt], [CreatedBy], [ID], [ModifiedAt], [ModifiedBy], [NamaSupplier], [AlamatSupplier]) VALUES (N'2025-07-04 21:33:45.000000', N'1', N'3', NULL, NULL, N'Honda Group', N'jln Kuningan 14 Menara Salep')
;

SET IDENTITY_INSERT [projectz].[MstSupplier] OFF
;