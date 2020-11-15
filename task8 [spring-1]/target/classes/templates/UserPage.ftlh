<#import "commons.ftlh" as c>
<#import "/spring.ftl" as spring>

<@c.page>
    <#if error??>
        <div class="message">${error}</div>
    <#else>
        <div class="id">${user.id!} </div>
        <div class="login">${user.login!} </div>
        <div class="creationTime">${user.creationTime!} </div>
    </#if>
</@c.page>
