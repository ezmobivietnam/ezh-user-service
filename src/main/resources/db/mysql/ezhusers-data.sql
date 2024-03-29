USE
ezhusers;

--
-- Dumping data for table country
--
SET AUTOCOMMIT=0;
INSERT INTO `ezhusers`.`country` (`id`, `name`)
VALUES 
(1, 'Afghanistan'),
(2, 'Albania'),
(3, 'Algeria'),
(4, 'Andorra'),
(5, 'Angola'),
(6, 'Antigua and Barbuda'),
(7, 'Argentina'),
(8, 'Armenia'),
(9, 'Australia'),
(10, 'Austria'),
(11, 'Azerbaijan'),
(12, 'Bahamas'),
(13, 'Bahrain'),
(14, 'Bangladesh'),
(15, 'Barbados'),
(16, 'Belarus'),
(17, 'Belgium'),
(18, 'Belize'),
(19, 'Benin'),
(20, 'Bhutan'),
(21, 'Bolivia'),
(22, 'Bosnia and Herzegovina'),
(23, 'Botswana'),
(24, 'Brazil'),
(25, 'Brunei'),
(26, 'Bulgaria'),
(27, 'Burkina Faso'),
(28, 'Burundi'),
(29, 'Cabo Verde'),
(30, 'Cambodia'),
(31, 'Cameroon'),
(32, 'Canada'),
(33, 'Central African Republic'),
(34, 'Chad'),
(35, 'Chile'),
(36, 'China'),
(37, 'Colombia'),
(38, 'Comoros'),
(39, 'Congo'),
(40, 'Costa Rica'),
(41, 'Croatia'),
(42, 'Cuba'),
(43, 'Cyprus'),
(44, 'Czech Republic (Czechia)'),
(45, 'Côte d''Ivoire'),
(46, 'Denmark'),
(47, 'Djibouti'),
(48, 'Dominica'),
(49, 'Dominican Republic'),
(50, 'DR Congo'),
(51, 'Ecuador'),
(52, 'Egypt'),
(53, 'El Salvador'),
(54, 'Equatorial Guinea'),
(55, 'Eritrea'),
(56, 'Estonia'),
(57, 'Eswatini'),
(58, 'Ethiopia'),
(59, 'Fiji'),
(60, 'Finland'),
(61, 'France'),
(62, 'Gabon'),
(63, 'Gambia'),
(64, 'Georgia'),
(65, 'Germany'),
(66, 'Ghana'),
(67, 'Greece'),
(68, 'Grenada'),
(69, 'Guatemala'),
(70, 'Guinea'),
(71, 'Guinea-Bissau'),
(72, 'Guyana'),
(73, 'Haiti'),
(74, 'Holy See'),
(75, 'Honduras'),
(76, 'Hungary'),
(77, 'Iceland'),
(78, 'India'),
(79, 'Indonesia'),
(80, 'Iran'),
(81, 'Iraq'),
(82, 'Ireland'),
(83, 'Israel'),
(84, 'Italy'),
(85, 'Jamaica'),
(86, 'Japan'),
(87, 'Jordan'),
(88, 'Kazakhstan'),
(89, 'Kenya'),
(90, 'Kiribati'),
(91, 'Kuwait'),
(92, 'Kyrgyzstan'),
(93, 'Laos'),
(94, 'Latvia'),
(95, 'Lebanon'),
(96, 'Lesotho'),
(97, 'Liberia'),
(98, 'Libya'),
(99, 'Liechtenstein'),
(100, 'Lithuania'),
(101, 'Luxembourg'),
(102, 'Madagascar'),
(103, 'Malawi'),
(104, 'Malaysia'),
(105, 'Maldives'),
(106, 'Mali'),
(107, 'Malta'),
(108, 'Marshall Islands'),
(109, 'Mauritania'),
(110, 'Mauritius'),
(111, 'Mexico'),
(112, 'Micronesia'),
(113, 'Moldova'),
(114, 'Monaco'),
(115, 'Mongolia'),
(116, 'Montenegro'),
(117, 'Morocco'),
(118, 'Mozambique'),
(119, 'Myanmar'),
(120, 'Namibia'),
(121, 'Nauru'),
(122, 'Nepal'),
(123, 'Netherlands'),
(124, 'New Zealand'),
(125, 'Nicaragua'),
(126, 'Niger'),
(127, 'Nigeria'),
(128, 'North Korea'),
(129, 'North Macedonia'),
(130, 'Norway'),
(131, 'Oman'),
(132, 'Pakistan'),
(133, 'Palau'),
(134, 'Panama'),
(135, 'Papua New Guinea'),
(136, 'Paraguay'),
(137, 'Peru'),
(138, 'Philippines'),
(139, 'Poland'),
(140, 'Portugal'),
(141, 'Qatar'),
(142, 'Romania'),
(143, 'Russia'),
(144, 'Rwanda'),
(145, 'Saint Kitts & Nevis'),
(146, 'Saint Lucia'),
(147, 'Samoa'),
(148, 'San Marino'),
(149, 'Sao Tome & Principe'),
(150, 'Saudi Arabia'),
(151, 'Senegal'),
(152, 'Serbia'),
(153, 'Seychelles'),
(154, 'Sierra Leone'),
(155, 'Singapore'),
(156, 'Slovakia'),
(157, 'Slovenia'),
(158, 'Solomon Islands'),
(159, 'Somalia'),
(160, 'South Africa'),
(161, 'South Korea'),
(162, 'South Sudan'),
(163, 'Spain'),
(164, 'Sri Lanka'),
(165, 'St. Vincent & Grenadines'),
(166, 'State of Palestine'),
(167, 'Sudan'),
(168, 'Suriname'),
(169, 'Sweden'),
(170, 'Switzerland'),
(171, 'Syria'),
(172, 'Tajikistan'),
(173, 'Tanzania'),
(174, 'Thailand'),
(175, 'Timor-Leste'),
(176, 'Togo'),
(177, 'Tonga'),
(178, 'Trinidad and Tobago'),
(179, 'Tunisia'),
(180, 'Turkey'),
(181, 'Turkmenistan'),
(182, 'Tuvalu'),
(183, 'Uganda'),
(184, 'Ukraine'),
(185, 'United Arab Emirates'),
(186, 'United Kingdom'),
(187, 'United States'),
(188, 'Uruguay'),
(189, 'Uzbekistan'),
(190, 'Vanuatu'),
(191, 'Venezuela'),
(192, 'Vietnam'),
(193, 'Yemen'),
(194, 'Zambia'),
(195, 'Zimbabwe');
COMMIT;

--
-- Dumping data for table city
--
SET AUTOCOMMIT=0;
INSERT INTO `ezhusers`.`city`
(`id`, `name`, `is_capital`, `country_id`)
VALUES
(1,	'An Giang', 0, 192),
(2,	'Bà Rịa – Vũng Tàu', 0, 192),
(3,	'Bắc Giang', 0, 192),
(4,	'Bắc Kạn', 0, 192),
(5,	'Bạc Liêu', 0, 192),
(6,	'Bắc Ninh', 0, 192),
(7,	'Bến Tre', 0, 192),
(8,	'Bình Định', 0, 192),
(9,	'Bình Dương', 0, 192),
(10, 'Bình Phước', 0, 192),
(11, 'Bình Thuận', 0, 192),
(12, 'Cà Mau', 0, 192),
(13, 'Cần Thơ', 0, 192),
(14, 'Cao Bằng', 0, 192),
(15, 'Đà Nẵng', 0, 192),
(16, 'Đắk Lắk', 0, 192),
(17, 'Đắk Nông', 0, 192),
(18, 'Điện Biên', 0, 192),
(19, 'Đồng Nai', 0, 192),
(20, 'Đồng Tháp', 0, 192),
(21, 'Gia Lai', 0, 192),
(22, 'Hà Giang', 0, 192),
(23, 'Hà Nam', 0, 192),
(24, 'Hà Nội', 1, 192),
(25, 'Hà Tĩnh', 0, 192),
(26, 'Hải Dương', 0, 192),
(27, 'Hải Phòng', 0, 192),
(28, 'Hậu Giang', 0, 192),
(29, 'Hòa Bình', 0, 192),
(30, 'Hưng Yên', 0, 192),
(31, 'Khánh Hòa', 0, 192),
(32, 'Kiên Giang', 0, 192),
(33, 'Kon Tum', 0, 192),
(34, 'Lai Châu', 0, 192),
(35, 'Lâm Đồng', 0, 192),
(36, 'Lạng Sơn', 0, 192),
(37, 'Lào Cai', 0, 192),
(38, 'Long An', 0, 192),
(39, 'Nam Định', 0, 192),
(40, 'Nghệ An', 0, 192),
(41, 'Ninh Bình', 0, 192),
(42, 'Ninh Thuận', 0, 192),
(43, 'Phú Thọ', 0, 192),
(44, 'Phú Yên', 0, 192),
(45, 'Quảng Bình', 0, 192),
(46, 'Quảng Nam', 0, 192),
(47, 'Quảng Ngãi', 0, 192),
(48, 'Quảng Ninh', 0, 192),
(49, 'Quảng Trị', 0, 192),
(50, 'Sóc Trăng', 0, 192),
(51, 'Sơn La', 0, 192),
(52, 'Tây Ninh', 0, 192),
(53, 'Thái Bình', 0, 192),
(54, 'Thái Nguyên', 0, 192),
(55, 'Thanh Hóa', 0, 192),
(56, 'Thừa Thiên Huế', 0, 192),
(57, 'Tiền Giang', 0, 192),
(58, 'TP Hồ Chí Minh', 0, 192),
(59, 'Trà Vinh', 0, 192),
(60, 'Tuyên Quang', 0, 192),
(61, 'Vĩnh Long', 0, 192),
(62, 'Vĩnh Phúc', 0, 192),
(63, 'Yên Bái', 0, 192);
COMMIT;

--
-- Dumping data for table address
--
SET AUTOCOMMIT=0;
INSERT INTO `ezhusers`.`address`
(`id`, `address`, `district`, `city_id`)
VALUES
(1, '16C Tôn Đức Thắng, phường Mỹ Bình', 'TP. Long Xuyên', 1),
(2, '1 Phạm Văn Đồng, phường Phước Trung', 'TP. Bà Rịa', 2),
(3, '82  đường Hùng Vương', 'TP. Bắc Giang', 3),
(4, '1A, phường Phùng Chí Kiên', 'TX.Bắc Kạn', 4),
(5, '04 đường Phan Đình Phùng, phường 3', 'TP.Bạc Liêu', 5),
(6, '10 đường Phù Đổng Thiên Vươn, phường Suối Hoa', 'TP.Bắc Ninh', 6),
(7, '7 đường Cách Mạng Tháng 8, phường 3', 'TP. Bến Tre', 7),
(8, '01 Trần Phú', 'TP.Quy Nhơn', 8),
(9, 'Tầng 16, Tòa nhà Trung tâm hành chính tập trung tỉnh Bình Dương, đường Lê Lợi, Phường Hoà Phú', 'Thành Phố Thủ Dầu Một', 9),
(10, 'Đường 6/1, Phường Tân Phú', 'TX.Đồng Xoài', 10),
(11, '04 Hải Thượng Lãn Ông', 'TP.Phan Thiết', 11),
(12, '02, đường Hùng Vương, phường 5', 'TP.Cà Mau', 12),
(13, '02 Hòa Bình, phường Tân An', 'quận Ninh Kiều', 13),
(14, '011 đường Hoàng Đình Giong', 'TP.Cao Bằng', 14),
(15, '31 Trần Phú', 'Q.Hải Châu', 15),
(16, '08 Mai Hắc Đế', 'TP.Buôn Ma Thuột', 16),
(17, 'Đường 23 tháng 3', 'TX.Gia Nghĩa', 17),
(18, 'Phường Mường Thanh', 'TP.Điện Biên Phủ', 18),
(19, '281 đường 30-4, phường Quyết Thắng', 'TP.Biên Hòa', 19),
(20, '12 đường 30/4, phường 1', 'TP.Cao Lãnh', 20),
(21, '02 Trần Phú', 'TP.Pleiku', 21),
(22, '222, đường Nguyễn Trãi', 'thành phố Hà Giang', 22),
(23, '90 đường Trần Phú, phường Quang Trung', 'TP.Phủ Lý', 23),
(24, '12 Lê Lai', 'Hoàn Kiếm', 24),
(25, '66 Phan Đình Phùng', 'TP.Hà Tĩnh', 25),
(26, '45 Quang Trung', 'TP.Hải Dương', 26),
(27, '18 Hoàng Diệu', 'quận Hồng Bàng', 27),
(28, '2 Hòa Bình', 'TP. Vị Thanh', 28),
(29, '8 đường An Dương Vương, phường Phương Lâm', 'TP.Hòa Bình', 29),
(30, '10 Đường Chùa Chuông', 'TP.Hưng Yên', 30),
(31, 'Nhà A1, Khu Liên cơ số 1 Trần Phú', 'TP.Nha Trang', 31),
(32, '06 Nguyễn Công Trứ, phường Vĩnh Thanh', 'TP.Rạch Giá', 32),
(33, '542 Nguyễn Huệ', 'TP.Kon Tum', 33),
(34, 'Phường Tân Phong', 'TP.Lai Châu', 34),
(35, '04 Trần Hưng Đạo', 'TP. Đà Lạt', 35),
(36, 'Đường Hùng Vương, phường Chi Lăng', 'TP.Lạng Sơn', 36),
(37, 'Đại Lộ Trần Hưng Đạo, phường Nam Cường', 'TP.Lào Cai', 37),
(38, '61 Nguyễn Huệ, phường 1', 'TP.Tân An', 38),
(39, '3 Lê Hồng phong, phường Vân Giang', 'TP.Ninh Bình', 39),
(40, '3 đường Trường Thi', 'TP. Vinh', 40),
(41, '3 Lê Hồng phong, phường Vân Giang', 'TP.Ninh Bình', 41),
(42, '450  Thống Nhất', 'TP.Phan Rang - Tháp Chàm', 42),
(43, 'Đường Trần Phú, phường Tân Dân', 'TP.Việt Trì', 43),
(44, '07 Độc Lập', 'TP.Tuy Hòa', 44),
(45, '06 Hùng Vương', 'TP. Đồng Hới', 45),
(46, '06 Hùng Vương', 'TP. Đồng Hới', 46),
(47, '52 Hùng Vương', 'TP.Quảng Ngãi', 47),
(48, 'Phường Hồng Hà', 'TP.Hạ Long', 48),
(49, '45 Hùng Vương', 'TP Đông Hà', 49),
(50, '37 đường Nguyễn Văn Thêm, phường 3', 'TP.Sóc Trăng', 50),
(51, '131 Nguyễn Lương Bằng', 'TP.Sơn La', 51),
(52, '136  đường Trần Hưng Đạo, phường 2', 'TX.Tây Ninh', 52),
(53, '76 phố Lý Thường Kiệt', 'TP.Thái Bình', 53),
(54, '18 đường Nha Trang', 'TP. Thái Nguyên', 54),
(55, '35 Đại lộ Lê Lợi, phường Lam Sơn', 'TP.Thanh Hóa', 55),
(56, '16 Lê Lợi', 'TP.Huế', 56),
(57, '23 đường 30/4, phường 1', 'TP.Mỹ Tho', 57),
(58, '86 Lê Thánh Tôn, phường Bến Nghé', 'Quận 1', 58),
(59, '52A  đường Lê Lợi, phường 1', 'TP.Trà Vinh', 59),
(60, '160 đường Trần Hưng Đạo', 'TX.Tuyên Quang', 60),
(61, '88 Hoàng Thái Hiếu, Phường 1', 'TP.Vĩnh Long', 61),
(62, 'Đường Mê Linh, phường Khai Quang', 'TP.Vĩnh Yên', 62),
(63, 'Tổ 55, phường Đồng Tâm', 'TP.Yên Bái', 63),
(64, '47 Lê Duẩn, phường Bến Nghé', 'quận 1', 58),
(65, '168 Trương Văn Bang, khu phố 1, P.Thạnh Mỹ Lợi', 'Q.2', 58),
(66, '185 Cách Mạng Tháng Tám, phường 4', 'quận 3', 58),
(67, '5 Đoàn Như Hài, phường 12', 'quận 4', 58),
(68, '203 An Dương Vương, phường 8', 'quận 5', 58),
(69, '107 Cao Văn Lầu, phường 1', 'quận 6', 58),
(70, '7 Tân Phú, Phường Tân Phú', 'Quận 7', 58),
(71, '04 Đường 1011, phường 5', 'quận 8', 58),
(72, '2/304 xa lộ Hà Nội, P.Hiệp Phú', 'Quận 9', 58),
(73, '474 đường Ba Tháng Hai, phường 14', 'quận 10', 58),
(74, '270 Bình Thới, P10', 'Quận 11', 58),
(75, '01 Lê Thị Riêng, phường Tân Thới An', 'Quận 12', 58),
(76, '43 Nguyễn Văn Bá', ' Quận Thủ Đức', 58),
(77, '70A Thoại Ngọc Hầu, P.Hòa Thạnh', 'Quận Tân Phú', 58),
(78, '19 Quang Trung, P10', 'Quận Gò Vấp', 58),
(79, '387 A Trường Chinh, Phường 14', 'quận Tân Bình', 58),
(80, '6 Phan Đăng Lưu, P14', 'Quận Bình Thạnh', 58),
(81, '521 Kinh Dương Vương, P.An Lạc', 'Quận Bình Tân', 58),
(82, '155 Nguyễn Văn Trỗi, phường 11', 'Quận Phú Nhuận', 58),
(83, 'E8/9A Nguyễn Hữu Trí, Thị trấn Tân Túc', 'Huyện Bình Chánh', 58),
(84, 'Đường Lương Văn Nho, Khu phố Giồng Ao, Thị trấn Cần Thạnh', 'Huyện Cần Giờ', 58),
(85, '330 Nguyễn Bình, Ấp 1, Xã Phú Xuân', 'Huyện Nhà Bè', 58),
(86, '77, Tỉnh lộ 8, Khu phố 7, Thị trấn Củ Chi', 'Huyện Củ Chi', 58),
(87, '1 Lý Nam Đế, Thị trấn Hóc Môn', 'Huyện Hóc Môn', 58);
COMMIT;

--
-- Dumping data for table role
--
SET AUTOCOMMIT=0;
INSERT INTO `ezhusers`.`role`
(`id`, `name`)
VALUES
(1, 'ADMIN'),
(2, 'SUPER_ADMIN'),
(3, 'USER');
COMMIT;

--
-- Dumping data for table privilege
--
SET AUTOCOMMIT=0;
INSERT INTO `ezhusers`.`privilege`
(`id`, `name`)
VALUES
(1, 'country:read'),
(2, 'country:write'),
(3, 'country:delete'),
(4, 'city:read'),
(5, 'city:write'),
(6, 'city:delete'),
(7, 'address:read'),
(8, 'address:write'),
(9, 'address:delete'),
(10, 'user:read'),
(11, 'user:write'),
(12, 'user:delete');
COMMIT;

--
-- Dumping data for table roles_privileges
--
SET AUTOCOMMIT=0;
INSERT INTO `ezhusers`.`roles_privileges`
(`role_id`, `privilege_id`)
VALUES
(1, 1),
(1, 2),
(1, 4),
(1, 5),
(1, 7),
(1, 8),
(1, 10),
(1, 11),
(2, 1),
(2, 2),
(2, 3),
(2, 4),
(2, 5),
(2, 6),
(2, 7),
(2, 8),
(2, 9),
(2, 10),
(2, 11),
(2, 12),
(3, 1),
(3, 4),
(3, 7),
(3, 10),
(3, 11);
COMMIT;

--
-- Dumping data for table user
--
SET AUTOCOMMIT=0;
INSERT INTO `ezhusers`.`user`
(`id`, `email`, `password`, `first_name`, `last_name`, `sex`, `active`)
VALUES
(1, 'admin@ezmobi.com.vn', '$2a$10$pf4K3nK5mCPNHSu72o89/.ojrl6WJz4D8BTea8gIJ9xfiqMS1zHYO', 'Admin', 'Admin', 1, 1),
(2, 'superadmin@ezmobi.com.vn', '$2a$10$pf4K3nK5mCPNHSu72o89/.ojrl6WJz4D8BTea8gIJ9xfiqMS1zHYO', 'Super', 'Admin', 0, 1);
COMMIT;

--
-- Dumping data for table users_roles
--
SET AUTOCOMMIT=0;
INSERT INTO `ezhusers`.`users_roles`
(`user_id`, `role_id`, `active`)
VALUES
(1, 1, 1),
(2, 2, 1);
COMMIT;