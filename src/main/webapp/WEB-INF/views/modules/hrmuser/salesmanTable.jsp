<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>内部员工管理</title>
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

        function showSaleDiv(id){

            var width = $("#content",window.top.document).width();
            if(width!=undefined){
                width = width-100;
            }else{
                width = 1400;
            }
            var _jBoxConfig = {};
            _jBoxConfig.defaults = {
                opacity: 0.5,
                persistent: true, /* 在显示隔离层的情况下，点击隔离层时，是否坚持窗口不关闭 */
            }
            top.$.jBox.setDefaults(_jBoxConfig);
            top.$.jBox.open("iframe:${ctx}/hrmuser/hrmUser/bindSale?id="+id, "业务员信息", 810,$(top.document).height()-240, {
                buttons:{"确定分配":"ok", "清除已选":"clear", "关闭":true}, bottomText:"区域经理分配业务员信息。",submit:function(v, h, f){
                    var pre_ids = h.find("iframe")[0].contentWindow.pre_ids;
                    var ids = h.find("iframe")[0].contentWindow.ids;
                    var manageId = h.find("iframe")[0].contentWindow.hrmManageId;
                    var empName = h.find("iframe")[0].contentWindow.empName;
                    //nodes = selectedTree.getSelectedNodes();
                    if (v=="ok"){
                        // 删除''的元素
                        if(ids[0]==''){
                            ids.shift();
                            pre_ids.shift();
                        }
                        if(pre_ids.sort().toString() == ids.sort().toString()){
                            top.$.jBox.tip("未给员工【"+empName+"】分配新成员！", 'info');
                            return false;
                        };
                        // 执行保存
                        loading('正在提交，请稍等...');
                        var idsArr = "";
                        for (var i = 0; i<ids.length; i++) {
                            idsArr = (idsArr + ids[i]) + (((i + 1)== ids.length) ? '':',');
                        }
                        $('#idsArr').val(idsArr);
                        $("#manageId").val(manageId);
                        $('#assignSalemanForm').submit();
                        return true;
                    } else if (v=="clear"){
                        h.find("iframe")[0].contentWindow.clearSalesman();
                        return false;
                    }
                }, loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                }
            });
        }

        function showCustomerDiv(id){

            var width = $("#content",window.top.document).width();
            if(width!=undefined){
                width = width-100;
            }else{
                width = 1400;
            }
            var _jBoxConfig = {};
            _jBoxConfig.defaults = {
                opacity: 0.5,
                persistent: true, /* 在显示隔离层的情况下，点击隔离层时，是否坚持窗口不关闭 */
            }
            top.$.jBox.setDefaults(_jBoxConfig);
            top.$.jBox.open("iframe:${ctx}/hrmuser/hrmUser/bindCustomer?id="+id, "客户信息", 810,$(top.document).height()-240, {
                buttons:{"确定分配":"ok", "清除已选":"clear", "关闭":true}, bottomText:"分配客户信息。",submit:function(v, h, f){
                    var pre_ids = h.find("iframe")[0].contentWindow.pre_ids;
                    var ids = h.find("iframe")[0].contentWindow.ids;
                    var manageId = h.find("iframe")[0].contentWindow.hrmManageId;
                    var empName = h.find("iframe")[0].contentWindow.empName;
                    //nodes = selectedTree.getSelectedNodes();
                    if (v=="ok"){
                        // 删除''的元素
                        if(ids[0]==''){
                            ids.shift();
                            pre_ids.shift();
                        }
                        if(pre_ids.sort().toString() == ids.sort().toString()){
                            top.$.jBox.tip("未给员工【"+empName+"】分配客户！", 'info');
                            return false;
                        };
                        // 执行保存
                        loading('正在提交，请稍等...');
                        var idsArr = "";
                        for (var i = 0; i<ids.length; i++) {
                            idsArr = (idsArr + ids[i]) + (((i + 1)== ids.length) ? '':',');
                        }
                        $('#idsCustomerArr').val(idsArr);
                        $("#hrmUserId").val(manageId);
                        $('#assignCustomerForm').submit();
                        return true;
                    } else if (v=="clear"){
                        h.find("iframe")[0].contentWindow.clearSalesman();
                        return false;
                    }
                }, loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                }
            });

        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/hrmuser/hrmUser/">业务员工列表</a></li>
	</ul>
	<form id="assignSalemanForm" action="${ctx}/hrmuser/hrmUser/assignSaleman" method="post" class="hide">
		<input type="hidden" name="manageId" id="manageId" value=""/>
		<input id="idsArr" type="hidden" name="idsArr" value=""/>
	</form>
	<form id="assignCustomerForm" action="${ctx}/hrmuser/hrmUser/assignCustomer" method="post" class="hide">
		<input type="hidden" name="manageId" id="hrmUserId" value=""/>
		<input id="idsCustomerArr" type="hidden" name="idsArr" value=""/>
	</form>

	<form:form id="searchForm" modelAttribute="hrmUser" action="${ctx}/hrmuser/hrmUser/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>业务员名称：</label>
				<form:input path="empName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>绑定</th>
				<th>取消</th>
				<th>登录用户名</th>
				<th>职员编号</th>
				<th>职员名称</th>
				<th>助记码</th>
				<th>性别</th>
				<th>学历</th>
				<th>QQ号</th>
				<th>手机号</th>
				<th>邮箱</th>
				<th>用户状态</th>
				<th>应聘登记时间</th>
				<th>应聘职务</th>
				<th>紧急联系人电话</th>
				<th>用户类型</th>
				<th>公司类型</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="hrmUser" varStatus="vs">
			<tr>
				<td>
					<input type="checkbox" value="1"  />
				</td>
				<td>
					<input type="checkbox" value="1"  />
				</td>
				<td><a href="${ctx}/hrmuser/hrmUser/form?id=${hrmUser.id}">
					${hrmUser.sysUser.name}
				</a></td>
				<td>
					${hrmUser.empNumber}
				</td>
				<td>
					${hrmUser.empName}
				</td>
				<td>
					${hrmUser.pinyin}
				</td>
				<td>
					${fns:getDictLabel(hrmUser.sex, 'sex', '')}
				</td>
				<td>
					${fns:getDictLabel(hrmUser.education, 'edu_type', '')}
				</td>
				<td>
					${hrmUser.qqNumber}
				</td>
				<td>
					${hrmUser.mobile}
				</td>
				<td>
					${hrmUser.email}
				</td>
				<td>
					${fns:getDictLabel(hrmUser.userStatus, 'user_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${hrmUser.regTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${hrmUser.jobObjective}
				</td>
				<td>
					${hrmUser.emergencyContactPhone}
				</td>
				<td>
					${fns:getDictLabel(hrmUser.userType, 'user_type', '')}
				</td>
				<td>
					${fns:getDictLabel(hrmUser.companyType, 'user_company_type', '')}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>