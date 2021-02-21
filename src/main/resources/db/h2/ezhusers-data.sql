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
INSERT INTO `ezhusers`.`address`
(`id`, `address`, `district`, `city_id`)
VALUES
(1, '86 Lê Thánh Tôn, phường Bến Nghé', 'Quận 1', 58);