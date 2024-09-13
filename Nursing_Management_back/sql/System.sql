/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 80032
 Source Host           : 127.0.0.1:3306
 Source Schema         : oldster_system

 Target Server Type    : MySQL
 Target Server Version : 80032
 File Encoding         : 65001

 Date: 13/09/2024 16:31:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'admin', '14e1b600b1fd579f47433b88e8d85291');
INSERT INTO `admin` VALUES (2, 'root', '14e1b600b1fd579f47433b88e8d85291');

-- ----------------------------
-- Table structure for custom
-- ----------------------------
DROP TABLE IF EXISTS `custom`;
CREATE TABLE `custom`  (
  `cid` int NOT NULL AUTO_INCREMENT,
  `cname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `cage` int NULL DEFAULT NULL,
  `cgender` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `cphone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `centrydate` date NULL DEFAULT NULL COMMENT '入院时间',
  `cstate` tinyint(1) NULL DEFAULT NULL COMMENT '在院状态',
  `caddress` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `did` int NULL DEFAULT NULL COMMENT '院系编号',
  `fid` int NULL DEFAULT NULL COMMENT '家属编号',
  `eid` int NULL DEFAULT NULL COMMENT '护理等级',
  `hid` int NULL DEFAULT NULL,
  PRIMARY KEY (`cid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 77 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of custom
-- ----------------------------
INSERT INTO `custom` VALUES (1, '李姥姥', 65, '女', '11128695874', '2024-08-22', 1, '青岛', 1, 1, 1, 17);
INSERT INTO `custom` VALUES (2, '刘爷爷', 67, '男', '12869578472', '2024-06-11', 1, '青岛', 1, 3, 1, 16);
INSERT INTO `custom` VALUES (3, '福姥姥', 72, '女', '26575789361', '2024-01-19', 1, '济南', 2, 2, 2, 17);
INSERT INTO `custom` VALUES (4, '王爷爷', 75, '男', '26475869876', '2024-09-29', 2, '潍坊', 2, 4, 2, 3);
INSERT INTO `custom` VALUES (5, '哈爷爷', 65, '男', '98767681232', '2024-09-09', 1, '济宁', 1, 5, 1, 16);
INSERT INTO `custom` VALUES (6, '府爷爷', 78, '女', '12867548762', '2024-04-20', 1, '潍坊', 2, 6, 2, 17);
INSERT INTO `custom` VALUES (7, '习爷爷', 65, '男', '17682647351', '2023-07-27', 1, '青岛', 2, 8, 2, 1);
INSERT INTO `custom` VALUES (8, '木爷爷', 70, '男', '11786982764', '2024-08-03', 1, '济南', 1, 11, 2, 1);
INSERT INTO `custom` VALUES (9, '路爷爷', 76, '男', '18796758232', '2023-05-14', 2, '青岛', 1, 12, 1, 2);
INSERT INTO `custom` VALUES (10, '夏姥姥', 82, '女', '98787231451', '2024-09-15', 2, '潍坊', 2, 7, 2, 2);
INSERT INTO `custom` VALUES (11, '温姥姥', 72, '女', '81265097231', '2024-09-01', 2, '青岛', 1, 9, 1, 16);
INSERT INTO `custom` VALUES (12, '屏姥姥', 69, '女', '27181859672', '2024-08-26', 1, '青岛', 2, 10, 2, 2);
INSERT INTO `custom` VALUES (13, '嘻嘻姥', 52, '女', '18692657481', '2024-09-02', 1, '海南', 1, 13, 1, 2);
INSERT INTO `custom` VALUES (49, '哈哈姥', 76, '女', '10191018765', '2024-09-03', 1, '青岛', 2, 41, 2, 3);
INSERT INTO `custom` VALUES (58, '七姥姥', 80, '女', '18274658921', '2024-09-03', 1, '济南', 1, 47, 1, 3);
INSERT INTO `custom` VALUES (59, '飞劳沃', 81, '男', '10928576281', '2024-09-03', 2, '青岛', 1, 48, 1, 3);
INSERT INTO `custom` VALUES (75, '王老五', 75, '男', '12456776542', '2024-09-03', 1, '青岛', 1, 55, 1, 1);
INSERT INTO `custom` VALUES (76, '枉我自', 21, '男', '28596857463', '2024-09-03', 1, '11', 1, 56, 1, 1);

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`  (
  `did` int NOT NULL AUTO_INCREMENT,
  `dname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `dlocation` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `personnum` int NULL DEFAULT NULL,
  PRIMARY KEY (`did`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES (1, '护理中心', '人工湖左岸2号', 12);
INSERT INTO `department` VALUES (2, '护理分院', '南门右侧5号', 7);
INSERT INTO `department` VALUES (3, '药物院', '南门2号', 2);

-- ----------------------------
-- Table structure for expend
-- ----------------------------
DROP TABLE IF EXISTS `expend`;
CREATE TABLE `expend`  (
  `eid` int NOT NULL,
  `egrade` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `esalary` decimal(8, 2) NULL DEFAULT NULL,
  `enumber` int NULL DEFAULT NULL,
  `eincome` float NULL DEFAULT NULL,
  PRIMARY KEY (`eid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of expend
-- ----------------------------
INSERT INTO `expend` VALUES (1, '护理一级', 15000.00, 7, 105000);
INSERT INTO `expend` VALUES (2, '护理二级', 10000.00, 6, 60000);

-- ----------------------------
-- Table structure for family
-- ----------------------------
DROP TABLE IF EXISTS `family`;
CREATE TABLE `family`  (
  `fid` int NOT NULL AUTO_INCREMENT,
  `fname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `fage` int NULL DEFAULT NULL,
  `fgender` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `fphone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `cid` int NULL DEFAULT NULL,
  PRIMARY KEY (`fid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of family
-- ----------------------------
INSERT INTO `family` VALUES (1, '张三', 29, '男', '17859375893', 1);
INSERT INTO `family` VALUES (2, '李四', 31, '男', '275912857292', 3);
INSERT INTO `family` VALUES (3, '小红', 25, '女', '176263729503', 2);
INSERT INTO `family` VALUES (4, '王五', 35, '男', '172894756930', 4);
INSERT INTO `family` VALUES (5, '小黑', 28, '男', '12985867322', 5);
INSERT INTO `family` VALUES (6, '小庄', 21, '男', '12849384921', 6);
INSERT INTO `family` VALUES (7, '小枣', 32, '男', '12985744634', 10);
INSERT INTO `family` VALUES (8, '子路', 35, '男', '11209763395', 7);
INSERT INTO `family` VALUES (9, '夏目', 20, '男', '18763340927', 11);
INSERT INTO `family` VALUES (10, '埃罗', 22, '男', '10986572893', 12);
INSERT INTO `family` VALUES (11, '王喜请', 26, '男', '10009374652', 8);
INSERT INTO `family` VALUES (12, '婉惜之', 21, '女', '18394576841', 9);
INSERT INTO `family` VALUES (13, '鸿飞', 32, '男', '12878462768', 13);
INSERT INTO `family` VALUES (41, '哈哈姐', 25, '女', '11123765832', 49);
INSERT INTO `family` VALUES (47, '七弟', 19, '男', '14556788765', 58);
INSERT INTO `family` VALUES (48, '老窝', 18, '男', '18767742692', 59);
INSERT INTO `family` VALUES (55, '务系', 19, '男', '13998744523', 75);
INSERT INTO `family` VALUES (56, '王杰', 21, '男', '18295768696', 76);

-- ----------------------------
-- Table structure for hostel
-- ----------------------------
DROP TABLE IF EXISTS `hostel`;
CREATE TABLE `hostel`  (
  `hid` int NOT NULL AUTO_INCREMENT,
  `hno` char(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `did` int NULL DEFAULT NULL,
  `dnumber` int NULL DEFAULT NULL,
  PRIMARY KEY (`hid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hostel
-- ----------------------------
INSERT INTO `hostel` VALUES (1, '1001', 1, 4);
INSERT INTO `hostel` VALUES (2, '2001', 2, 4);
INSERT INTO `hostel` VALUES (3, '1002', 1, 4);
INSERT INTO `hostel` VALUES (16, '2002', 2, 3);
INSERT INTO `hostel` VALUES (17, '1003', 1, 3);

-- ----------------------------
-- Table structure for hostel_custom
-- ----------------------------
DROP TABLE IF EXISTS `hostel_custom`;
CREATE TABLE `hostel_custom`  (
  `hid` int NULL DEFAULT NULL,
  `cid` int NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hostel_custom
-- ----------------------------
INSERT INTO `hostel_custom` VALUES (1, 1);
INSERT INTO `hostel_custom` VALUES (2, 2);

-- ----------------------------
-- Table structure for payout
-- ----------------------------
DROP TABLE IF EXISTS `payout`;
CREATE TABLE `payout`  (
  `pid` int NOT NULL AUTO_INCREMENT,
  `pnumber` int NULL DEFAULT NULL,
  `pout` float NULL DEFAULT NULL,
  PRIMARY KEY (`pid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of payout
-- ----------------------------
INSERT INTO `payout` VALUES (1, 8, 28500);

-- ----------------------------
-- Table structure for staff
-- ----------------------------
DROP TABLE IF EXISTS `staff`;
CREATE TABLE `staff`  (
  `sid` int NOT NULL AUTO_INCREMENT,
  `sno` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sage` int NULL DEFAULT NULL,
  `sgender` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sentrydate` date NULL DEFAULT NULL,
  `ssalary` decimal(8, 2) NULL DEFAULT NULL,
  `sstate` tinyint(1) NULL DEFAULT NULL,
  `did` int NULL DEFAULT NULL,
  `savatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`sid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of staff
-- ----------------------------
INSERT INTO `staff` VALUES (1, '10001', '李白', 21, '男', '2024-03-13', 3500.00, 1, 3, '1.jpeg');
INSERT INTO `staff` VALUES (2, '10002', '杜甫', 23, '男', '2024-09-10', 3500.00, 1, 3, '1.jpeg');
INSERT INTO `staff` VALUES (3, '10003', '王伟', 18, '男', '2024-09-07', 4000.00, 2, 3, '1.jpeg');
INSERT INTO `staff` VALUES (4, '10004', '王羲之', 22, '女', '2024-09-02', 3500.00, 1, 1, '1.jpeg');
INSERT INTO `staff` VALUES (5, '10005', '涡我', 19, '男', '2024-09-17', 2000.00, 2, 2, '1.jpeg');
INSERT INTO `staff` VALUES (12, '10006', '李墨', 21, '男', '2024-06-15', 2500.00, 1, 1, '17450ed79ee24774ab3d3a8d6754c3d8_f6a04e47ebf1e03a41785ee949898695.jpeg');
INSERT INTO `staff` VALUES (13, '10007', '五人心', 21, '女', '2024-09-09', 1500.00, 1, 2, '24d5725644a14e7fb8c89805de91ad4b_c992b1ab7d6cf684f40b01e55328a5e1.jpeg');
INSERT INTO `staff` VALUES (14, '10008', '丽江sam', 21, '女', '2024-06-01', 2000.00, 1, 1, '09f2a7f532e0482b9c84785225315824_71f30842127ca9636cdb124a022c5ee9.jpeg');
INSERT INTO `staff` VALUES (15, '10009', '海西', 21, '女', '2023-08-26', 4500.00, 1, 1, 'f3cfd815336d4146a2c1fb5f2b89e381_55f0195ceb18ba7712b80324ea1dd155.jpeg');
INSERT INTO `staff` VALUES (18, '10010', '哈兮若', 21, '女', '2024-09-15', 1500.00, 1, 2, '78584b98dbab41618c89221430d8c752_77094b36acaf2edd9f9c89d7cb1001e93901939b.jpg');

-- ----------------------------
-- Table structure for travel
-- ----------------------------
DROP TABLE IF EXISTS `travel`;
CREATE TABLE `travel`  (
  `tid` int NOT NULL AUTO_INCREMENT,
  `tlocation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `tstart` time NULL DEFAULT NULL,
  `tend` time NULL DEFAULT NULL,
  `tprogress` int NULL DEFAULT NULL,
  `tdescription` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`tid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of travel
-- ----------------------------
INSERT INTO `travel` VALUES (1, '河畔花园', '08:00:00', '11:30:00', 0, '南门出发，途径人工湖、五人山、最后到达河畔花园');
INSERT INTO `travel` VALUES (2, '南山门', '13:00:00', '15:30:00', 0, '南门出发，路途三十分钟到达');
INSERT INTO `travel` VALUES (3, '人民广场', '16:30:00', '19:15:00', 0, '东南门出发，途径华庭购物中心，20分钟到达');
INSERT INTO `travel` VALUES (4, '铺子夜市', '20:15:00', '22:30:00', 0, '北门出发，直至夜市，5分钟即达');
INSERT INTO `travel` VALUES (34, '院内夜游', '22:45:00', '23:30:00', 0, '适用于需要深夜出行的客户');

-- ----------------------------
-- Table structure for travel_custom
-- ----------------------------
DROP TABLE IF EXISTS `travel_custom`;
CREATE TABLE `travel_custom`  (
  `tid` int NULL DEFAULT NULL,
  `cid` int NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of travel_custom
-- ----------------------------
INSERT INTO `travel_custom` VALUES (1, 1);
INSERT INTO `travel_custom` VALUES (1, 2);
INSERT INTO `travel_custom` VALUES (2, 5);
INSERT INTO `travel_custom` VALUES (2, 7);
INSERT INTO `travel_custom` VALUES (2, 12);
INSERT INTO `travel_custom` VALUES (3, 49);
INSERT INTO `travel_custom` VALUES (3, 75);
INSERT INTO `travel_custom` VALUES (3, 76);
INSERT INTO `travel_custom` VALUES (34, 2);
INSERT INTO `travel_custom` VALUES (34, 5);
INSERT INTO `travel_custom` VALUES (4, 8);
INSERT INTO `travel_custom` VALUES (4, 49);
INSERT INTO `travel_custom` VALUES (4, 75);

-- ----------------------------
-- Table structure for travel_staff
-- ----------------------------
DROP TABLE IF EXISTS `travel_staff`;
CREATE TABLE `travel_staff`  (
  `tid` int NULL DEFAULT NULL,
  `sid` int NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of travel_staff
-- ----------------------------
INSERT INTO `travel_staff` VALUES (1, 2);
INSERT INTO `travel_staff` VALUES (2, 4);
INSERT INTO `travel_staff` VALUES (2, 12);
INSERT INTO `travel_staff` VALUES (3, 1);
INSERT INTO `travel_staff` VALUES (34, 12);
INSERT INTO `travel_staff` VALUES (34, 13);
INSERT INTO `travel_staff` VALUES (4, 2);
INSERT INTO `travel_staff` VALUES (4, 4);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'AsKiro', '161cc6cec473ef3ddbe271777ae04ee9');

SET FOREIGN_KEY_CHECKS = 1;
