<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>计划任务管理</title>
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
.btn-warning {
    padding: 2px 12px;
}
</style>

</head>
<body>

<div style="padding: 20px 0px 0px 20px;">
	<button type="button" onclick="showAdd();" class="btn btn-primary">添加任务</button>
</div>



	<div class="job_table" data-example-id="hoverable-table">
		<table class="table table-hover table-bordered">
		<caption>计划任务列表</caption>
			<thead>
				<tr>
					<th style="width:3%;">序号</th>
					<th>任务</th>
					<th>分组</th>
					<th>cron表达式</th>
					<th>描述</th>
					<th>任务类</th>
					<th>状态</th>
					<th>是否同步</th>
					<th style="width:10%;">创建时间</th>
					<th style="width:10%;">更新时间</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${taskList}" var="job" varStatus="status">
					<tr>
						<th scope="row">${status.count}</th>
						<td>${job.jobName }</td>
						<td>${job.jobGroup }</td>
						<td>${job.cronExpression }</td>
						<td>${job.description }</td>
						<td>${job.beanClass }</td>
						<td>
							<c:choose>
								<c:when test="${job.jobStatus=='1' }">
									<input type="checkbox" name="checkStatus" onchange="changeStatus('${job.jobId}',0);" checked="checked" data-size="mini" />
								</c:when>
								<c:otherwise>
									<input type="checkbox" name="checkStatus" onchange="changeStatus('${job.jobId}',1);" data-size="mini" />
								</c:otherwise>
							</c:choose>
						</td>
						<td>是</td>
						<td>
							<fmt:formatDate value="${job.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td> 
							<fmt:formatDate value="${job.updateTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							<button type="button" class="btn btn-warning">编辑</button>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
<script type="text/javascript">
$("[name='checkStatus']").bootstrapSwitch();
	
	function changeStatus(id,status){
		var msg = "";
		if(status == 1){ //启动
			msg = "启动";
		}else if(status == 0){
			msg = "关闭";
		}else{
			return;
		}
		$.ajax({
				url : "../jobTask/changeStatus",
				dataType : "json",
				type : "post",
				data : {jobId:id,status:status},
				success : function(data) {
					if (data.flag == true) {
						toastr.success(msg+"成功");
						setTimeout("location.reload();",1000); 
						return;
					}else{
						toastr.error(data.msg,msg+"失败");
					}
				},
				error : function() {
					toastr.error(msg+"失败");
				}
			});
	
	}



	
	function showAdd(){
		$("#modal_box").modal("toggle");
		$("#modal_title").html("添加任务");
		$("#page_content").load("../jobTask/showAdd");
		$(".modal-backdrop").not(":first").remove();
	}
	
	function showEdit(){
		$("#modal_box").modal("toggle");
		$("#modal_title").html("编辑任务");
		$("#page_content").load("../jobTask/showAdd");
		$(".modal-backdrop").not(":first").remove();
	}
</script>
<div id="modal_box" class="modal fade bs-example-modal-lg" tabindex="1" role="dialog" style="display: none;">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
          <h4 class="modal-title" id="modal_title"></h4>
        </div>
        <div id="page_content" class="modal-body"></div>
      </div>
    </div>
</div>
</body>
</html>