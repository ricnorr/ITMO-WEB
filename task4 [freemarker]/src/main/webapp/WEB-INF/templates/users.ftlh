<#import "./table.ftlh" as t>
<#import "./common.ftlh" as c>

<@t.table ["user_id", "handle", "name"]>
    <#list users as user>
        <tr>
            <td>${user.id}</td>
            <td><@c.userlink user/></td>
            <td>${user.name}</td>
        </tr>
    </#list>
</@t.table>