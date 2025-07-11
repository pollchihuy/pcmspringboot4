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

    SET IDENTITY_INSERT [projectz].[MstAkses] ON
;

INSERT INTO [projectz].[MstAkses] ([CreatedBy], [CreatedDate], [ID], [ModifiedBy], [ModifiedDate], [Nama], [Deskripsi]) VALUES (N'1', N'2025-07-11 20:23:37.000000', N'1', NULL, NULL, N'Admin', N'Dapet Semua Menu')
;

INSERT INTO [projectz].[MstAkses] ([CreatedBy], [CreatedDate], [ID], [ModifiedBy], [ModifiedDate], [Nama], [Deskripsi]) VALUES (N'1', N'2025-07-11 20:23:37.000000', N'2', NULL, NULL, N'Member', N'Default Akses Untuk User Baru')
;

INSERT INTO [projectz].[MstAkses] ([CreatedBy], [CreatedDate], [ID], [ModifiedBy], [ModifiedDate], [Nama], [Deskripsi]) VALUES (N'1', N'2025-07-11 20:23:37.000000', N'3', NULL, NULL, N'Staff', N'Dapet Menu Operation')
;

SET IDENTITY_INSERT [projectz].[MstAkses] OFF
;

SET IDENTITY_INSERT [projectz].[MstGroupMenu] ON
;

INSERT INTO [projectz].[MstGroupMenu] ([CreatedBy], [CreatedDate], [ID], [ModifiedBy], [ModifiedDate], [Nama], [Deskripsi]) VALUES (N'1', N'2025-07-11 20:18:34.000000', N'1', NULL, NULL, N'User Management', N'Untuk User Management')
;

INSERT INTO [projectz].[MstGroupMenu] ([CreatedBy], [CreatedDate], [ID], [ModifiedBy], [ModifiedDate], [Nama], [Deskripsi]) VALUES (N'1', N'2025-07-11 20:18:34.000000', N'2', NULL, NULL, N'Artikel', N'Untuk Menu Default sebagai Member')
;

INSERT INTO [projectz].[MstGroupMenu] ([CreatedBy], [CreatedDate], [ID], [ModifiedBy], [ModifiedDate], [Nama], [Deskripsi]) VALUES (N'1', N'2025-07-11 20:18:34.000000', N'3', NULL, NULL, N'Operation', N'Untuk Menu Operasional')
;

SET IDENTITY_INSERT [projectz].[MstGroupMenu] OFF
;

SET IDENTITY_INSERT [projectz].[MstMenu] ON
;

INSERT INTO [projectz].[MstMenu] ([CreatedBy], [CreatedDate], [ID], [IDGroupMenu], [ModifiedBy], [ModifiedDate], [Nama], [Path], [Deskripsi]) VALUES (N'1', N'2025-07-11 20:22:23.000000', N'1', N'1', NULL, NULL, N'Group-Menu', N'/group-menu', N'Untuk API Group Menu')
;

INSERT INTO [projectz].[MstMenu] ([CreatedBy], [CreatedDate], [ID], [IDGroupMenu], [ModifiedBy], [ModifiedDate], [Nama], [Path], [Deskripsi]) VALUES (N'1', N'2025-07-11 20:23:37.000000', N'2', N'1', NULL, NULL, N'Menu', N'/menu', N'Untuk API Menu')
;

INSERT INTO [projectz].[MstMenu] ([CreatedBy], [CreatedDate], [ID], [IDGroupMenu], [ModifiedBy], [ModifiedDate], [Nama], [Path], [Deskripsi]) VALUES (N'1', N'2025-07-11 20:23:37.000000', N'3', N'1', NULL, NULL, N'Akses', N'/akses', N'Untuk API Akses')
;

INSERT INTO [projectz].[MstMenu] ([CreatedBy], [CreatedDate], [ID], [IDGroupMenu], [ModifiedBy], [ModifiedDate], [Nama], [Path], [Deskripsi]) VALUES (N'1', N'2025-07-11 20:23:37.000000', N'4', N'1', NULL, NULL, N'User', N'/user', N'Untuk API User')
;

INSERT INTO [projectz].[MstMenu] ([CreatedBy], [CreatedDate], [ID], [IDGroupMenu], [ModifiedBy], [ModifiedDate], [Nama], [Path], [Deskripsi]) VALUES (N'1', N'2025-07-11 20:23:37.000000', N'5', N'2', NULL, NULL, N'Artikel-1', N'/artikel-1', N'Untuk API Artikel 1')
;

INSERT INTO [projectz].[MstMenu] ([CreatedBy], [CreatedDate], [ID], [IDGroupMenu], [ModifiedBy], [ModifiedDate], [Nama], [Path], [Deskripsi]) VALUES (N'1', N'2025-07-11 20:23:37.000000', N'6', N'2', NULL, NULL, N'Artikel-2', N'/artikel-2', N'Untuk API Artikel 2')
;

INSERT INTO [projectz].[MstMenu] ([CreatedBy], [CreatedDate], [ID], [IDGroupMenu], [ModifiedBy], [ModifiedDate], [Nama], [Path], [Deskripsi]) VALUES (N'1', N'2025-07-11 20:23:37.000000', N'7', N'3', NULL, NULL, N'Kategori-Produk', N'/kategoriproduk', N'Untuk API Kategori Produk')
;

INSERT INTO [projectz].[MstMenu] ([CreatedBy], [CreatedDate], [ID], [IDGroupMenu], [ModifiedBy], [ModifiedDate], [Nama], [Path], [Deskripsi]) VALUES (N'1', N'2025-07-11 20:23:37.000000', N'8', N'3', NULL, NULL, N'Produk', N'/produk', N'Untuk API Produk')
;

INSERT INTO [projectz].[MstMenu] ([CreatedBy], [CreatedDate], [ID], [IDGroupMenu], [ModifiedBy], [ModifiedDate], [Nama], [Path], [Deskripsi]) VALUES (N'1', N'2025-07-11 20:23:37.000000', N'9', N'3', NULL, NULL, N'Supplier', N'/supplier', N'Untuk API Supplier')
;

SET IDENTITY_INSERT [projectz].[MstMenu] OFF
;
        
        INSERT INTO [projectz].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'1', N'1')
;

INSERT INTO [projectz].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'1', N'2')
;

INSERT INTO [projectz].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'1', N'3')
;

INSERT INTO [projectz].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'1', N'4')
;

INSERT INTO [projectz].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'1', N'5')
;

INSERT INTO [projectz].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'1', N'6')
;

INSERT INTO [projectz].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'1', N'7')
;

INSERT INTO [projectz].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'1', N'8')
;

INSERT INTO [projectz].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'1', N'9')
;

INSERT INTO [projectz].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'2', N'5')
;

INSERT INTO [projectz].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'2', N'6')
;

INSERT INTO [projectz].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'3', N'5')
;

INSERT INTO [projectz].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'3', N'6')
;

INSERT INTO [projectz].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'3', N'7')
;

INSERT INTO [projectz].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'3', N'8')
;

INSERT INTO [projectz].[MapAksesMenu] ([IDAkses], [IDMenu]) VALUES (N'3', N'9')
;

SET IDENTITY_INSERT [projectz].[MstUser] ON
;

INSERT INTO [projectz].[MstUser] ([IsRegistered], [TanggalLahir], [CreatedBy], [CreatedDate], [ID], [IDAkses], [ModifiedBy], [ModifiedDate], [Username], [NoHp], [OTP], [Password], [TokenEstafet], [NamaLengkap], [Email], [LinkImage], [PathImage], [Alamat]) VALUES (N'1', N'1995-12-12', N'1', N'2025-07-11 21:31:44.148265', N'1', N'1', N'1', N'2025-07-11 21:31:51.108072', N'paul.321', N'08121314151', N'$2a$11$f3H2QBT9YIZ1YCV9dDtcTu/vl6WqxYLtOy7DcDsvl8bayiiVUS7Kq', N'$2a$11$iQbzUcipqfzv85bOMEJwz.e24gPebLjfHbkKAcpCzAuUMCFXCfZOm', NULL, N'Paul Malau', N'poll.chihuy@gmail.com', NULL, NULL, N'Bogor Bogor Bogor Bogor Bogor ')
;

SET IDENTITY_INSERT [projectz].[MstUser] OFF
;