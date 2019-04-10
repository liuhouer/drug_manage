<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>客户管理</title>
	<meta name="decorator" content="default"/>
	
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/hrmuser/hrmUser/customers>客户列表</a></li>
	</ul>
	<form id="assignSalemanForm" action="${ctx}/hrmuser/hrmUser/assignSaleman" method="post" class="hide">
		<input type="hidden" name="manageId" id="manageId" value=""/>
		<input id="idsArr" type="hidden" name="idsArr" value=""/>
	</form>
	<form id="assignCustomerForm" action="${ctx}/hrmuser/hrmUser/assignCustomer" method="post" class="hide">
		<input type="hidden" name="manageId" id="hrmUserId" value=""/>
		<input id="idsCustomerArr" type="hidden" name="idsArr" value=""/>
	</form>

	<form:form id="searchForm" modelAttribute="hrmUser" action="${ctx}/hrmuser/hrmUser/customersList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="J_manageId" name="manageId" type="hidden" value="${manageId}"/>
		<ul class="ul-form">
			<li><label>客户名称：</label>
				<input id="J_name" name="name" value="${customer.name }" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="J_bind_btn" class="btn btn-primary" type="button" value="绑定"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>绑定</th>
				<th>取消</th>
				<th>客户姓名</th>
				<th>助记码</th>
				<th>客户档案号</th>
				<th>法人代表</th>
				<th>企业地址</th>
				<th>企业负责人</th>
				<th>社会统一信用代码</th>
				<th>客户类别</th>
				<th>传真</th>
				<th>邮编</th>
				<th>纳税类别</th>
				<th>送货上门</th>
				<th>经营范围</th>
				<th>配送路线</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="c" varStatus="vs">
			<tr>
				<td>
					<input type="radio" value="bind" cid="${c.id }" <c:if test="${c.uc.hrmUserId !=null}">checked="checked"</c:if> name="bindbtn${vs.index }" />
				</td>
				
				<td>
					<input type="radio" value="unbind" cid="${c.id }" name="bindbtn${vs.index }" <c:if test="${c.uc.hrmUserId ==null}">checked="checked"</c:if> />
				</td>
				<td><a href="${ctx}/hrmuser/hrmUser/form?id=${c.id}">
					${c.name}
				</a></td>
				<td>
					${c.pinyin}
				</td>
				<td>
					${c.customerNumber}
				</td>
				<td>
					${c.legalRepresentative}
				</td>
				<td>
					${c.enterpriseAddress}
				</td>
				<td>
					${c.enterpriseManage}  
				</td>
				<td>
					${c.creditCode}
				</td>
				<td>
					${fns:getDictLabel(c.customerType, 'client_type', '')}
				</td>
				<td>
					${c.fax} 
				</td>
				<td>
					${c.zipcode}  
				</td>
				<td>
					${fns:getDictLabel(c.taxableCategory, 'tax_type', '')}
					
				</td>
				<td>
					${c.provideHome}
				</td>
				<td>
					${c.bizScope}
				</td>
				<td>
					${c.distributionRoute}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
	
	
	
	
	<script type="text/javascript">
		$(function() {
			$("#J_bind_btn").click(function(){
				var radios = $("input[name^='bindbtn']");
				var ids = "";
				radios.each(function(){
					var cid = $(this).attr("cid");
					if($(this).val() == 'bind' && $(this).attr("checked") == 'checked'){
					   ids += cid +","
					}
				})
				
				var manageId = $("#J_manageId").val();
				if(!manageId){
					$.jBox.info('请选择区域经理或者业务员');
					return ;
				}
				if(!ids){
					$.jBox.info('请勾选需要绑定的客户');
					return ;
				}
				
				var submit = function (v, h, f) {
				    if (v == 'ok')
				    	$.ajax({
				             url:"${ctx}/hrmuser/hrmUser/assignCustomer",
				             type:"post",
				             data:{"manageId":manageId,"ids":ids},
				             success:function(data){
				                 $.jBox.tip(data.msg);
				             }
				         });
				    else if (v == 'cancel')
				        jBox.tip(v, '取消操作');

				    return true; //close
				};

				$.jBox.confirm("确定要绑定勾选的客户吗？","提示", submit);
				
				
			})
		});


	</script>
</body>
</html>