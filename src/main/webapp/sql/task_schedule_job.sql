/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50610
Source Host           : localhost:3306
Source Database       : quartz

Target Server Type    : MYSQL
Target Server Version : 50610
File Encoding         : 65001

Date: 2016-09-02 18:06:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for task_schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `task_schedule_job`;
CREATE TABLE `task_schedule_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(30) NOT NULL,
  `job_group` varchar(50) NOT NULL,
  `job_status` varchar(1) NOT NULL,
  `cron_expression` varchar(20) NOT NULL,
  `bean_class` varchar(100) NOT NULL,
  `is_concurrent` varchar(255) DEFAULT NULL COMMENT '1',
  `description` varchar(300) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`job_id`),
  UNIQUE KEY `name_group` (`job_name`,`job_group`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task_schedule_job
-- ----------------------------
INSERT INTO `task_schedule_job` VALUES ('12', 'JOB1', 'taobao', '1', '0/10 * * * * ?', 'com.woyi.mhub.job.TestJob', null, '测试job添加', '2016-09-01 15:03:11', '2016-09-02 17:53:37');
INSERT INTO `task_schedule_job` VALUES ('13', 'JOB2', 'ALI', '1', '0/10 * * * * ?', 'com.woyi.mhub.job.TestJob', null, '测试2', '2016-09-01 16:16:53', '2016-09-02 17:58:44');
INSERT INTO `task_schedule_job` VALUES ('16', 'JOB3', 'taobao', '0', '0/1 * * * * ?', 'com.woyi.mhub.task.TestJob', null, '测试job添加', '2016-09-01 16:18:07', '2016-09-02 17:45:25');
INSERT INTO `task_schedule_job` VALUES ('17', '测试任务2', '测试11', '1', '0/5 * * * * ?', 'com.woyi.mhub.job.TestJob', null, '测试11测试11测试11测试11测试11', '2016-09-02 18:05:27', '2016-09-02 18:05:35');
