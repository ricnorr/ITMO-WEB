<#import "./table.ftlh" as t>
<#import "./common.ftlh" as c>

<@t.table ["user_id", "handle", "name"]>
    <#list users as u>
        <tr>
            <td>${u.id}</td>
            <td>${u.handle}</td>
            <td>${u.name}</td>
        </tr>
    </#list>
</@t.table>