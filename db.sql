/*
SQLyog Professional v12.09 (64 bit)
MySQL - 5.6.43 : Database - job
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`job` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

/*Table structure for table `job` */

DROP TABLE IF EXISTS `job`;

CREATE TABLE `job` (
  `id` varchar(32) NOT NULL COMMENT '主键：id',
  `name` varchar(20) DEFAULT NULL COMMENT '工作名称',
  `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `job_classify_id` varchar(32) DEFAULT NULL COMMENT '分类id',
  `settle_type` int(11) DEFAULT NULL COMMENT '结算方式 ( 1日结 2 月结 3 季结 4 年结 )',
  `salary` varchar(20) DEFAULT NULL COMMENT '薪资',
  `type` int(11) DEFAULT NULL COMMENT '工作类型( 1 全职 2 兼职 )',
  `address` varchar(200) DEFAULT NULL COMMENT '工作地点',
  `people_count_limit` int(11) DEFAULT NULL COMMENT '招聘人数',
  `work_time` text COMMENT '工作时间',
  `content` text COMMENT '工作详情',
  `publish_status` int(11) DEFAULT NULL COMMENT '发布状态（0 未审核 1审核通过 2审核不通过 3下架）',
  `sex_limit` int(11) DEFAULT NULL COMMENT '性别限制（0 女 1男 2不限）',
  `salary_unit` varchar(20) DEFAULT NULL COMMENT '报酬单位',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工作信息表';

/*Data for the table `job` */

insert  into `job`(`id`,`name`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`job_classify_id`,`settle_type`,`salary`,`type`,`address`,`people_count_limit`,`work_time`,`content`,`publish_status`,`sex_limit`,`salary_unit`) values ('0644C9F457FF4E3BA2BE775F79C9DAB3','【急】诚招传单员 包吃+高薪','5D3BAEB5450A4A48B059EDA58DE10534','2019-02-14 16:58:53','A001','2019-03-02 17:50:29','164DC93D4C3E424B9538DCFB462036DE',1,NULL,1,'东街口',NULL,'工作时间：9：00 到 17：00','发传单',1,NULL,NULL);
insert  into `job`(`id`,`name`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`job_classify_id`,`settle_type`,`salary`,`type`,`address`,`people_count_limit`,`work_time`,`content`,`publish_status`,`sex_limit`,`salary_unit`) values ('3C14888F89E744A4A98D2467F87C23BD','诚聘闽江东门奶茶店服务员','706AAF4013DD487388C9482BF5B41EB3','2019-03-02 17:21:02','A001','2019-03-02 17:50:26','03AF32C1ECE848FA9B78119C3EE8867B',2,'15',1,'测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试',2,'早上：XXXXXXXXXXXXX\n下午：XXXXXXXXXXXXXX\n晚上：XXXXXXXXXXXXX','测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试\n测试测试测试测试测试测试测试测试测试测试测试\n测试测试测试测试测试\n测试测试测试测试测试测试\n测试测试测试测试测试测试测试测试测试测试测试测试测试\n测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试\n测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试',1,2,'元/小时');
insert  into `job`(`id`,`name`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`job_classify_id`,`settle_type`,`salary`,`type`,`address`,`people_count_limit`,`work_time`,`content`,`publish_status`,`sex_limit`,`salary_unit`) values ('4397AF97C8954E389EAFC4D9F1F49ECF','测试的招聘名称333','706AAF4013DD487388C9482BF5B41EB3','2019-03-02 17:54:35','A001','2019-03-02 17:55:05','B2E2264FCF1F4FDBB125ED1DE0FFF872',2,'300',1,'测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的',9,'测试的测试的测试的测试的测试的测试的\n测试的测试的测试的测试的测试的测\n试的测试的测试的测试的测试的','测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的',1,2,'元/天');
insert  into `job`(`id`,`name`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`job_classify_id`,`settle_type`,`salary`,`type`,`address`,`people_count_limit`,`work_time`,`content`,`publish_status`,`sex_limit`,`salary_unit`) values ('4B3B3A56D4AC43818B97C0F9941B3F4C','测试的招聘名称123','706AAF4013DD487388C9482BF5B41EB3','2019-03-02 17:51:51','A001','2019-03-02 17:52:05','164DC93D4C3E424B9538DCFB462036DE',1,'100',1,'测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的',10,'测试的测试的测试的测试的测试的测试的\n测试的测试的测试的测试的测试的测\n试的测试的测试的测试的测试的','测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的',1,2,'元/天');
insert  into `job`(`id`,`name`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`job_classify_id`,`settle_type`,`salary`,`type`,`address`,`people_count_limit`,`work_time`,`content`,`publish_status`,`sex_limit`,`salary_unit`) values ('561DE20D000246899D0B6359488DC9BF','测试的招聘名称','706AAF4013DD487388C9482BF5B41EB3','2019-03-02 17:53:35','A001','2019-03-02 17:53:42','7C65523CC0694FEC9802B025363A4043',3,'100',1,'测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的',5,'测试的测试的测试的测试的测试的测试的\n测试的测试的测试的测试的测试的测\n试的测试的测试的测试的测试的','测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的',1,2,'元/天');
insert  into `job`(`id`,`name`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`job_classify_id`,`settle_type`,`salary`,`type`,`address`,`people_count_limit`,`work_time`,`content`,`publish_status`,`sex_limit`,`salary_unit`) values ('7DBAAB659DA2428BBEA8806B744AC38C','测试的招聘名称test','706AAF4013DD487388C9482BF5B41EB3','2019-03-02 17:53:10','A001','2019-03-02 17:53:43','A3D77DF5F5684F298D3A9A06D62BC2CF',2,'50',1,'测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的',15,'测试的测试的测试的测试的测试的测试的\n测试的测试的测试的测试的测试的测\n试的测试的测试的测试的测试的','测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的',1,1,'元/小时');
insert  into `job`(`id`,`name`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`job_classify_id`,`settle_type`,`salary`,`type`,`address`,`people_count_limit`,`work_time`,`content`,`publish_status`,`sex_limit`,`salary_unit`) values ('8FE5F39241C94B4D888080C37FEE5653','测试的招聘名称666','706AAF4013DD487388C9482BF5B41EB3','2019-03-02 17:55:47','A001','2019-03-02 17:56:01','6578D5C208DC471A9E17FF1779E762E6',3,'300',1,'测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的',9,'测试的测试的测试的测试的测试的测试的\n测试的测试的测试的测试的测试的测\n试的测试的测试的测试的测试的','测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的',1,2,'元/天');
insert  into `job`(`id`,`name`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`job_classify_id`,`settle_type`,`salary`,`type`,`address`,`people_count_limit`,`work_time`,`content`,`publish_status`,`sex_limit`,`salary_unit`) values ('B27A92FA96564BAC8869E559E61F0401','测试的招聘名称222','706AAF4013DD487388C9482BF5B41EB3','2019-03-02 17:54:17','A001','2019-03-02 17:55:00','10712D7CBA684DAAB3495BAA2D3AE118',3,'200',1,'测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的',8,'测试的测试的测试的测试的测试的测试的\n测试的测试的测试的测试的测试的测\n试的测试的测试的测试的测试的','测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的',1,1,'元/天');
insert  into `job`(`id`,`name`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`job_classify_id`,`settle_type`,`salary`,`type`,`address`,`people_count_limit`,`work_time`,`content`,`publish_status`,`sex_limit`,`salary_unit`) values ('FA7EE0F9421840FAA323CA3449DE718C','测试的招聘名称111','706AAF4013DD487388C9482BF5B41EB3','2019-03-02 17:52:37','A001','2019-03-02 17:53:44','350BE8DB281C486AAB9AE45E44018003',3,'80',1,'测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的',10,'测试的测试的测试的测试的测试的测试的\n测试的测试的测试的测试的测试的测\n试的测试的测试的测试的测试的','测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的测试的',1,2,'元/天');

/*Table structure for table `job_classify` */

DROP TABLE IF EXISTS `job_classify`;

CREATE TABLE `job_classify` (
  `id` varchar(32) NOT NULL COMMENT '主键：id',
  `name` varchar(20) DEFAULT NULL COMMENT '名称',
  `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `type` int(11) DEFAULT NULL COMMENT '类型( 1.兼职 2.全职 )',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工作分类表';

/*Data for the table `job_classify` */

insert  into `job_classify`(`id`,`name`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`type`) values ('03AF32C1ECE848FA9B78119C3EE8867B','服务员','A001','2019-03-02 16:43:54','A001','2019-03-02 16:43:54',1);
insert  into `job_classify`(`id`,`name`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`type`) values ('05ECCA29C7D34E41970E59DB9BB32C7C','游戏开发','A001','2019-03-02 16:49:30','A001','2019-03-02 16:49:30',2);
insert  into `job_classify`(`id`,`name`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`type`) values ('07015EAF77B844EEB6E835DD74F12DED','家庭教师','A001','2019-03-02 16:49:05','A001','2019-03-02 16:49:05',2);
insert  into `job_classify`(`id`,`name`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`type`) values ('0880758F314143E8B0D92ABFB7707C5B','文案编辑','A001','2019-03-02 16:49:22','A001','2019-03-02 16:49:22',2);
insert  into `job_classify`(`id`,`name`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`type`) values ('10712D7CBA684DAAB3495BAA2D3AE118','物流管理','A001','2019-03-02 16:45:33','A001','2019-03-02 16:45:33',1);
insert  into `job_classify`(`id`,`name`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`type`) values ('164DC93D4C3E424B9538DCFB462036DE','传单员','A001','2019-02-11 11:06:31','A001','2019-02-22 16:07:29',1);
insert  into `job_classify`(`id`,`name`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`type`) values ('350BE8DB281C486AAB9AE45E44018003','JAVA开发','A001','2019-03-02 16:48:00','A001','2019-03-02 16:50:11',1);
insert  into `job_classify`(`id`,`name`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`type`) values ('444A69C437044E3CB1D5C78573904B39','视频剪辑','A001','2019-03-02 16:48:18','A001','2019-03-02 16:48:18',1);
insert  into `job_classify`(`id`,`name`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`type`) values ('6578D5C208DC471A9E17FF1779E762E6','课程顾问','A001','2019-03-02 16:44:43','A001','2019-03-02 16:44:43',1);
insert  into `job_classify`(`id`,`name`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`type`) values ('68D587CC7CDD4272A05EC6763D1CE739','市场推广','A001','2019-03-02 16:51:20','A001','2019-03-02 16:51:20',2);
insert  into `job_classify`(`id`,`name`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`type`) values ('7C65523CC0694FEC9802B025363A4043','文案编辑','A001','2019-03-02 16:46:57','A001','2019-03-02 16:46:57',1);
insert  into `job_classify`(`id`,`name`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`type`) values ('A3D77DF5F5684F298D3A9A06D62BC2CF','送餐员','A001','2019-03-02 16:44:11','A001','2019-03-02 16:44:11',1);
insert  into `job_classify`(`id`,`name`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`type`) values ('A4191666D5914C7EB7FDAF506FCEEEBC','JAVA开发','A001','2019-02-11 11:06:56','A001','2019-03-02 16:49:59',2);
insert  into `job_classify`(`id`,`name`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`type`) values ('AC611A5B2528472A8CE016E16C1A5CF9','市场推广','A001','2019-03-02 16:47:53','A001','2019-03-02 16:47:53',1);
insert  into `job_classify`(`id`,`name`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`type`) values ('B2E2264FCF1F4FDBB125ED1DE0FFF872','游戏开发','A001','2019-03-02 16:48:26','A001','2019-03-02 16:48:26',1);
insert  into `job_classify`(`id`,`name`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`type`) values ('CCCF3CB2339E4D0CA5E52938D0767683','前端开发','A001','2019-03-02 16:50:18','A001','2019-03-02 16:50:18',1);
insert  into `job_classify`(`id`,`name`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`type`) values ('D12A66B72F7741F0B4772E820B3EA94F','前端开发','A001','2019-03-02 16:49:50','A001','2019-03-02 16:49:50',2);
insert  into `job_classify`(`id`,`name`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`type`) values ('DDC8877B280D4804A0A1A4B0D96104BB','家庭教师','A001','2019-03-02 16:46:08','A001','2019-03-02 16:46:08',1);
insert  into `job_classify`(`id`,`name`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`type`) values ('E413117EB95B4BBC91D7386ACA3E870E','送餐员','A001','2019-03-02 16:50:32','A001','2019-03-02 16:50:32',2);
insert  into `job_classify`(`id`,`name`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`type`) values ('EE71CA5196D54E20847B39C190FAF7F2','视频剪辑','A001','2019-03-02 16:51:07','A001','2019-03-02 16:51:07',2);
insert  into `job_classify`(`id`,`name`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`type`) values ('F707505CBF7B4C8C81DF6EE29A72CE27','培训辅导','A001','2019-03-02 16:46:22','A001','2019-03-02 16:46:22',1);
insert  into `job_classify`(`id`,`name`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`type`) values ('FFF3D6737EAB49C39ADA7E0B0277B495','培训辅导','A001','2019-03-02 16:50:50','A001','2019-03-02 16:50:50',2);

/*Table structure for table `job_report` */

DROP TABLE IF EXISTS `job_report`;

CREATE TABLE `job_report` (
  `id` varchar(32) NOT NULL COMMENT '主键：id',
  `job_id` varchar(32) DEFAULT NULL COMMENT '工作名称',
  `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户id',
  `note` varchar(500) DEFAULT NULL COMMENT '备注',
  `job_name` varchar(20) DEFAULT NULL COMMENT '工作名称',
  `company_name` varchar(20) DEFAULT NULL COMMENT '公司名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='简历举报';

/*Data for the table `job_report` */

insert  into `job_report`(`id`,`job_id`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`user_id`,`note`,`job_name`,`company_name`) values ('FE3A6770BF314F86AAF888884C74A52D','0644C9F457FF4E3BA2BE775F79C9DAB3','23AEF4CCB2474082A81E571836DD830F','2019-03-03 16:06:26','23AEF4CCB2474082A81E571836DD830F','2019-03-03 16:06:26','23AEF4CCB2474082A81E571836DD830F','哈哈哈','【急】诚招传单员 包吃+高薪',NULL);

/*Table structure for table `job_resume` */

DROP TABLE IF EXISTS `job_resume`;

CREATE TABLE `job_resume` (
  `id` varchar(32) NOT NULL COMMENT '主键：id',
  `job_id` varchar(32) DEFAULT NULL COMMENT '工作名称',
  `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户id',
  `status` int(11) DEFAULT NULL COMMENT '状态( 0待处理 1已处理 2不再接受该简历 )',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='简历投递表';

/*Data for the table `job_resume` */

insert  into `job_resume`(`id`,`job_id`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`user_id`,`status`) values ('24478C0FEFEE43A98FACA01A34643611','3C14888F89E744A4A98D2467F87C23BD','23AEF4CCB2474082A81E571836DD830F','2019-03-03 14:25:20','23AEF4CCB2474082A81E571836DD830F','2019-03-03 14:25:20','23AEF4CCB2474082A81E571836DD830F',NULL);
insert  into `job_resume`(`id`,`job_id`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`user_id`,`status`) values ('79E26A8FB666463BA1D0471BE393BEC2','0644C9F457FF4E3BA2BE775F79C9DAB3','839DB6ADE55A46929B48D5978D30ECAD','2019-02-15 15:33:15','839DB6ADE55A46929B48D5978D30ECAD','2019-02-15 15:42:15','839DB6ADE55A46929B48D5978D30ECAD',0);
insert  into `job_resume`(`id`,`job_id`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`user_id`,`status`) values ('7C413C2BC3754FD6B177BE9ED52051FE','0644C9F457FF4E3BA2BE775F79C9DAB3','23AEF4CCB2474082A81E571836DD830F','2019-03-03 14:04:21','23AEF4CCB2474082A81E571836DD830F','2019-03-03 14:04:21','23AEF4CCB2474082A81E571836DD830F',NULL);
insert  into `job_resume`(`id`,`job_id`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`user_id`,`status`) values ('BCB30CDA00284BFA9517F12C76F773E9','0644C9F457FF4E3BA2BE775F79C9DAB3','706AAF4013DD487388C9482BF5B41EB3','2019-03-03 13:58:24','706AAF4013DD487388C9482BF5B41EB3','2019-03-03 13:58:24','706AAF4013DD487388C9482BF5B41EB3',NULL);
insert  into `job_resume`(`id`,`job_id`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`user_id`,`status`) values ('E253201C573C44079CBDC8FC98FFCE4B','7DBAAB659DA2428BBEA8806B744AC38C','23AEF4CCB2474082A81E571836DD830F','2019-03-03 16:10:34','23AEF4CCB2474082A81E571836DD830F','2019-03-03 16:10:34','23AEF4CCB2474082A81E571836DD830F',NULL);

/*Table structure for table `sys_config` */

DROP TABLE IF EXISTS `sys_config`;

CREATE TABLE `sys_config` (
  `id` varchar(32) NOT NULL COMMENT '主键：id',
  `introduce_pic_url` varchar(100) DEFAULT NULL COMMENT '介绍图片路径',
  `copyright` varchar(50) DEFAULT NULL COMMENT '版权',
  `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `logo_pic_url` varchar(100) DEFAULT NULL COMMENT 'logo图片路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置';

/*Data for the table `sys_config` */

insert  into `sys_config`(`id`,`introduce_pic_url`,`copyright`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`logo_pic_url`) values ('0A93D052E9854E1F83B974F1BB22D3B8','/Uploader/20190303/sysConfig/D85280D4227E4048A09E8E37843DC5F1.png','©闽江学院版权所有','A001','2019-02-08 11:08:01','A001','2019-03-03 14:19:49','/Uploader/20190303/sysConfig/8A915AC2DA9E474F9698E6DF578BC3C8.png');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` varchar(32) NOT NULL COMMENT '主键：id',
  `username` varchar(20) DEFAULT NULL COMMENT '用户名',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `name` varchar(32) DEFAULT NULL COMMENT '姓名 | 公司名称',
  `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user_id` varchar(32) DEFAULT NULL COMMENT '修改人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `type` int(11) DEFAULT NULL COMMENT '用户类型（-1管理员 1普通用户 2 公司账户）',
  `sex` int(11) DEFAULT NULL COMMENT '性别(0女 1男)',
  `head_pic_url` varchar(100) DEFAULT NULL COMMENT '头像图片路径',
  `school` varchar(50) DEFAULT NULL COMMENT '所在学校',
  `introduce` varchar(300) DEFAULT NULL COMMENT '个人简介 | 公司简介',
  `work_live` text COMMENT '工作经历',
  `exp_month` int(11) DEFAULT NULL COMMENT '简历过期时间（月）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

/*Data for the table `user` */

insert  into `user`(`id`,`username`,`password`,`name`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`type`,`sex`,`head_pic_url`,`school`,`introduce`,`work_live`,`exp_month`) values ('23AEF4CCB2474082A81E571836DD830F','18060368069','F5BB0C8DE146C67B44BABBF4E6584CC0','柠七','23AEF4CCB2474082A81E571836DD830F','2019-02-24 19:40:48','23AEF4CCB2474082A81E571836DD830F','2019-03-02 11:41:49',1,0,'/Uploader/20190224/user/ED4427E386584DB38646806A240F36BD.jpg','闽江123','无','没有',NULL);
insert  into `user`(`id`,`username`,`password`,`name`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`type`,`sex`,`head_pic_url`,`school`,`introduce`,`work_live`,`exp_month`) values ('5D3BAEB5450A4A48B059EDA58DE10534','test','E10ADC3949BA59ABBE56E057F20F883E',NULL,'5D3BAEB5450A4A48B059EDA58DE10534','2019-02-14 16:46:10','5D3BAEB5450A4A48B059EDA58DE10534','2019-02-14 16:46:10',2,NULL,NULL,NULL,NULL,NULL,2);
insert  into `user`(`id`,`username`,`password`,`name`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`type`,`sex`,`head_pic_url`,`school`,`introduce`,`work_live`,`exp_month`) values ('706AAF4013DD487388C9482BF5B41EB3','15060163267','F5BB0C8DE146C67B44BABBF4E6584CC0','测试的一个公司','706AAF4013DD487388C9482BF5B41EB3','2019-02-24 21:55:21','706AAF4013DD487388C9482BF5B41EB3','2019-03-03 13:13:17',2,NULL,'/Uploader/20190303/user/B56F27B2627843ACA295067D6EC745C1.jpg',NULL,'公司定位于打造全球性知名度的跨境电商企业，已形成B2B和B2C模式同步发展。B2B以生产做为后盾，专业立足于毕业宗教产品开发，订制， 生产，销售，工厂位于绍兴上虞区成立于2004年，占地8700平方，员工近百名。B2C以强大的电商运营推广团队为后盾，开拓了毕业，宗教，户外，宠物等多个产品领域。多年的跨境电商平台运营和产品开发经营，平台遍布各大跨境电商平台，独立站,alibaba, Amazon, eBay。自主开发，建立了多个自主品牌，畅销海外。','电话：0591-XXXXXXX\n地址：XXXXXXXXXXXXXXXXXXXXXXXXX\n邮箱：###########.XX.com',2);
insert  into `user`(`id`,`username`,`password`,`name`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`type`,`sex`,`head_pic_url`,`school`,`introduce`,`work_live`,`exp_month`) values ('839DB6ADE55A46929B48D5978D30ECAD','user','E10ADC3949BA59ABBE56E057F20F883E',NULL,'839DB6ADE55A46929B48D5978D30ECAD','2019-02-14 16:16:55','839DB6ADE55A46929B48D5978D30ECAD','2019-02-14 16:36:16',1,1,NULL,NULL,NULL,NULL,NULL);
insert  into `user`(`id`,`username`,`password`,`name`,`create_user_id`,`create_time`,`update_user_id`,`update_time`,`type`,`sex`,`head_pic_url`,`school`,`introduce`,`work_live`,`exp_month`) values ('A001','admin','E10ADC3949BA59ABBE56E057F20F883E','管理员','A001','2019-02-07 17:30:18','A001','2019-02-22 16:07:52',-1,NULL,NULL,NULL,NULL,NULL,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
