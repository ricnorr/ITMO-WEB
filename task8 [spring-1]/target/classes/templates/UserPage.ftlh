<#import "commons.ftlh" as c>
<#import "/spring.ftl" as spring>

<@c.page>
    <#if error??>
        <div class="message">${error}</div>
    <#else>
        <div class="id">${userInfo.id!} </div>
        <div class="login">${userInfo.login!} </div>
        <div class="creationTime">${userInfo.creationTime!} </div>
    </#if>
</@c.page>
