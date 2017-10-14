<#--页眉-->
<#import "/common/pagination.ftl" as pagination>
<#include "/common/head.ftl" />
<#include "/common/foot.ftl" />
<#--页面导航-->
<div class="widget-content nopadding" id="logResultList">
        <table class="table table-bordered data-table" id="c_list">
            <thead> 
                  <tr>
						<th>工号</th>
						<th>姓名</th>
						<th>上级</th>
						<th>用户名</th>
						<th>部门</th>
						<th>职位</th>
						<th>电话</th>
						<th>性别</th>
						<th>手机号码</th>
					    <th>在职状态</th>
					    <th>账号状态</th>
						<th width="150px">操作</th>
					</tr>
            </thead>
            <tbody>
                <#list pageParam.items as clientUser>
					<tr>
						<td>${clientUser.userNo!''}</td>
						<td>${clientUser.name!''}</td>
						<td>${clientUser.parentName!''}</td>
						<td>${clientUser.loginName!''}</td>
						<td>${clientUser.structureName!''}</td>
						<td>${clientUser.positionName!''}</td>
						<td>${clientUser.telephoneNo!''}</td>
						<td><#if clientUser.sex = 1>男<#elseif clientUser.sex = 2>女</#if></td>
						<td>${clientUser.mobileNo}&nbsp;</td>
						<td><#if clientUser.status = 1>在职<#elseif clientUser.status = 0>离职</#if></td>
						<td><#if clientUser.isActivation = 1>启用<#elseif clientUser.isActivation = 0>禁用</#if></td>
						<td>
							<a class="editClientUser" href="javascript:void(0);" dataId="${clientUser.clientUserId!''}" dataName="${clientUser.name!''}">编辑</a>
							<a class="deleteClientUser" href="javascript:void(0);" data="${clientUser.clientUserId!''}">删除</a>
							<a class="resetPasswd" href="javascript:void(0);" data="${clientUser.clientUserId!''}">重置密码</a>
							<a class="check" href="javascript:void(0);" dataId="${clientUser.clientUserId!''}"  dataName="${clientUser.name!''}">查看</a>
						</td>
					</tr>
				</#list>
            </tbody>
        </table>
        <div class="paging">	
        	<@pagination.paging pageParam true "#logResultList"/>
		 </div>
  </div>