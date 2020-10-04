<#import "./post.ftlh" as p>
<@c.page>
    <#if user_id??>
        <@p.page c.findAll(posts, "user_id", user_id) "null"/>
    </#if>
</@c.page>
