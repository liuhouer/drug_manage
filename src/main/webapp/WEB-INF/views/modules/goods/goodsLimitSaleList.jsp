<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品限销管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/goods/goodsLimitSale/">商品限销列表</a></li>
		<shiro:hasPermission name="goods:goodsLimitSale:edit"><li><a href="${ctx}/goods/goodsLimitSale/form">商品限销添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="goodsLimitSale" action="${ctx}/goods/goodsLimitSale/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商品：</label>
				<form:input path="goodsId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>限销类型：</label>
				<form:input path="limitType" htmlEscape="false" maxlength="1" class="input-medium"/>
			</li>
			<li><label>开始限销时间：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${goodsLimitSale.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>结束限销时间：</label>
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${goodsLimitSale.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商品</th>
				<th>限销类型</th>
				<th>开始限销时间</th>
				<th>结束限销时间</th>
				<th>创建者</th>
				<th>创建时间</th>
				<th>更新者</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="goods:goodsLimitSale:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="goodsLimitSale">
			<tr>
				<td><a href="${ctx}/goods/goodsLimitSale/form?id=${goodsLimitSale.id}">
					${goodsLimitSale.goodsId}
				</a></td>
				<td>
					${goodsLimitSale.limitType}
				</td>
				<td>
					<fmt:formatDate value="${goodsLimitSale.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${goodsLimitSale.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${goodsLimitSale.createBy.id}
				</td>
				<td>
					<fmt:formatDate value="${goodsLimitSale.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${goodsLimitSale.updateBy.id}
				</td>
				<td>
					<fmt:formatDate value="${goodsLimitSale.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${goodsLimitSale.remarks}
				</td>
				<shiro:hasPermission name="goods:goodsLimitSale:edit"><td>
    				<a href="${ctx}/goods/goodsLimitSale/form?id=${goodsLimitSale.id}">修改</a>
					<a href="${ctx}/goods/goodsLimitSale/delete?id=${goodsLimitSale.id}" onclick="return confirmx('确认要删除该商品限销吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>