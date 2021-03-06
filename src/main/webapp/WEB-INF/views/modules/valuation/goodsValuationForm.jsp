<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品定价管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/valuation/goodsValuation/">商品定价列表</a></li>
		<li class="active"><a href="${ctx}/valuation/goodsValuation/form?id=${goodsValuation.id}">商品定价<shiro:hasPermission name="valuation:goodsValuation:edit">${not empty goodsValuation.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="valuation:goodsValuation:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="goodsValuation" action="${ctx}/valuation/goodsValuation/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<div class="div-a">
				<label class="control-label">商品id：</label>
				<div class="controls">
					<form:input path="goodsId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
				</div>
			</div>
			<div class="div-b">
				<label class="control-label">第三方查看价格标识：</label>
				<div class="controls">
					<form:input path="thirdQueryPriceFlag" htmlEscape="false" maxlength="1" class="input-xlarge "/>
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="div-a">
				<label class="control-label">除第三方外客户标识：</label>
				<div class="controls">
					<form:input path="notThirdQueryPriceFlag" htmlEscape="false" maxlength="1" class="input-xlarge "/>
				</div>
			</div>
			<div class="div-b">
				<label class="control-label">一类价格：</label>
				<div class="controls">
					<form:input path="onePrice" htmlEscape="false" class="input-xlarge  number"/>
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="div-a">
				<label class="control-label">二类价格：</label>
				<div class="controls">
					<form:input path="twoPrice" htmlEscape="false" class="input-xlarge  number"/>
				</div>
			</div>
			<div class="div-b">
				<label class="control-label">三类价格：</label>
				<div class="controls">
					<form:input path="threePrice" htmlEscape="false" class="input-xlarge  number"/>
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="div-a">
				<label class="control-label">四类价格：</label>
				<div class="controls">
					<form:input path="fourPrice" htmlEscape="false" class="input-xlarge  number"/>
				</div>
			</div>
			<div class="div-b">
				<label class="control-label">五类价格：</label>
				<div class="controls">
					<form:input path="fivePrice" htmlEscape="false" class="input-xlarge  number"/>
				</div>
			</div>
		</div>
		<div class="control-group">
			<div class="div-a">
				<label class="control-label">公营价：</label>
				<div class="controls">
					<form:input path="publicPrice" htmlEscape="false" class="input-xlarge  number"/>
				</div>
			</div>
			<div class="div-b">
				<label class="control-label">备注信息：</label>
				<div class="controls">
					<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xlarge "/>
				</div>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="valuation:goodsValuation:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>