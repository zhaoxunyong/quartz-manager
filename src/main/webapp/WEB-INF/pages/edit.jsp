<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>任务管理</title>
</head>
<body>
	<form id="addForm" class="form-horizontal" method="post">
		<div class="form-group">
			<label for="jobName" class="col-sm-3 control-label">任务名称</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" id="jobName" name="jobName" value="${job.jobName }" readonly="readonly">
			</div>
		</div>
		<div class="form-group">
			<label for="jobGroup" class="col-sm-3 control-label">任务分组</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" id="jobGroup" name="jobGroup" value="${job.jobGroup }" readonly="readonly" >
			</div>
		</div>
		<div class="form-group">
			<label for="description" class="col-sm-3 control-label">描 述</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" id="description" name="description" value="${job.description }">
			</div>
		</div>
		<div class="form-group">
			<label for="cronExpression" class="col-sm-3 control-label">cron表达式</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" id="cronExpression" name="cronExpression"  value="${job.cronExpression }" placeholder="如 0/5 * * * * ?" >
			</div>
		</div>
		<div class="form-group">
			<label for="beanClass" class="col-sm-3 control-label">任务类</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" id="beanClass" name="beanClass"  value="${job.beanClass }" placeholder="如 com.woyi.mhub.TestJob">
			</div>
		</div>

		<div class="form-group">
			<div class="col-sm-offset-3 col-sm-8">
				<button type="button" onclick="doAdd();" class="btn btn-default">保 存</button>
			</div>
		</div>
	</form>
	<script type="text/javascript">
		//添加
		function doAdd() {
			if($.trim($("#jobName").val()) == ""){
				toastr.info("请输入任务名称");
				return false;
			};
			if($.trim($("#jobGroup").val()) == ""){
				toastr.info("请输入任务分组");
				return false;
			};
			if($.trim($("#cronExpression").val()) == ""){
				toastr.info("请输入cron表达式");
				return false;
			};
			if($.trim($("#beanClass").val()) == ""){
				toastr.info("请输入任务类");
				return false;
			};
		
			var params = $("#addForm").serialize();
			//验证
			$.ajax({
				url : "../jobTask/doAdd",
				dataType : "json",
				type : "post",
				data : params,
				success : function(data) {
					if (data.flag == true) {
						toastr.success("增加成功");
						location.reload();
						return;
					}else{
						toastr.error(data.msg,"添加失败");
					}
				},
				error : function() {
					toastr.error("添加失败");
				}
			});

		}
	</script>
</body>
</html>