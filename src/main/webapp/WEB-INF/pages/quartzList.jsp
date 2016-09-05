<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>任务管理</title>
<link href="../plugins/bootstrap-3.3.5-dist/css/bootstrap.min.css" rel="stylesheet" >
<script type="text/javascript" src="../plugins/jquery/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="../plugins/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>

<link href="../plugins/bootstrap-switch/bootstrap-switch.min.css" rel="stylesheet">
<script type="text/javascript" src="../plugins/bootstrap-switch/bootstrap-switch.min.js"></script>

<link href="../plugins/toastr/toastr.css" rel="stylesheet">
<script type="text/javascript" src="../plugins/toastr/toastr.js"></script>


<style type="text/css">
.job_table{
	padding: 15px 15px 15px 15px;
}
.sm {
    padding: 2px 12px;
}
</style>

</head>
<body>

	<div class="job_table" data-example-id="hoverable-table">
		<c:forEach items="${grouplist}" var="group" varStatus="status" >
			<table class="table table-hover table-bordered">
			<caption>任务组：${group.groupName}</caption>
				<thead>
					<tr>
						<th style="width:10%;">任务名</th>
						<th style="width:18%;">描述</th>
						<th style="width:10%;">cron表达式</th>
						<th style="width:15%;">上次运行时间</th>
						<th style="width:15%;">下次运行时间</th>
						<th style="width:5%;">状态</th>
						<th style="width:10%;">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${group.list}" var="job" >
						<tr>
							<td>${job.jobName }</td>
							<td>${job.description}</td>
							<td>${job.trigger.cronExpression}</td>
							<td>
								<fmt:formatDate value="${job.trigger.previousFireTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
								<fmt:formatDate value="${job.trigger.nextFireTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
								<c:if test="${job.status == 'NORMAL'}">
									<font color="green">正常</font>
								</c:if>
								<c:if test="${job.status == 'PAUSED'}">
									暂停
								</c:if>
								<c:if test="${job.status == 'COMPLETE'}">
									完成
								</c:if>
								<c:if test="${job.status == 'ERROR'}">
									<font color="red">错误</font>
								</c:if>
								<c:if test="${job.status == 'BLOCKED'}">
									<font color="red">阻塞</font>
								</c:if>
								<c:if test="${job.status == 'NONE'}">
									无
								</c:if>
							</td>
							<td>
								<c:if test="${job.status == 'NORMAL'}">
									<button type="button" class="btn btn-warning sm">暂停</button>
								</c:if>
								<c:if test="${job.status == 'PAUSED'}">
									<button type="button" class="btn btn-danger sm">启动</button>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:forEach>
		
		<c:if test="${grouplist == '[]'}">
		<div style="text-align: center;" ><h2>暂无任务。。。</h2></div>
		</c:if>
	</div>
<script type="text/javascript">
	
	function pauseJob(id, status){
		var msg = "";
		if(status == 1){
			msg = "暂停";
		}else if(status == 0){
			msg = "启动";
		}else{
			return;
		}
		$.ajax({
				url : "../jobTask/pauseJob",
				dataType : "json",
				type : "post",
				data : {jobId:id,status:status},
				success : function(data) {
					if (data.flag == true) {
						toastr.success(msg+"成功");
					}else{
						toastr.error(data.msg,msg+"失败");
					}
					setTimeout("location.reload();",1000); 
				},
				error : function() {
					toastr.error(msg+"失败");
				}
			});
	
	}

</script>
</body>
</html>