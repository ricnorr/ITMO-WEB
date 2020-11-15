<#import "commons.ftlh" as c>
<@c.page>
    <form id="sendMessage" method="post">
        <input type="submit" name="action" value="sendMessage"/>
        <select name="targetUserId">
            <#list users as user>
                <option>${user.id}</option>
            </#list>
        </select>
        <div><textarea id="textArea" name="textMessage"></textarea></div>
    </form>

    <#if error??>
        <div>${error}</div>
    </#if>


    <table class="messageTable" style="display:flow-root">
        <tr>
           <th>source user</th>
           <th>target user</th>
           <th>text</th>
        </tr>

    <#if size gt 0>
        <#list 0..size-1 as i>
            <tr>
                <td>${usersSource[i].id}</td>
                <td>${usersTarget[i].id}</td>
                <td>${messages[i].text?replace("<", "&lt")}</td>
            <tr>
        </#list>
    </#if>
    </table>
</@c.page>