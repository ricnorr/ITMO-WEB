<#import "./common.ftlh" as c>

<#macro page>
    <@c.page>
        <link rel="stylesheet" href="/css/post.css">
        <#list posts?reverse as p>
            <@post p/>
        </#list>
    </@c.page>
</#macro>

<#macro post element>
    <article>
        <div class="title">${element.title}</div>
        <div class="information">By user: ${c.findBy(users, "id", element.user_id).handle}</div>
        <div class="body">
            <p><@display element.text/></p>
        </div>
    </article>
</#macro>


<#macro display var>
    <#if var?length gt 250>
        <td>${var?substring(0, 250)}…</td>
    <#else>
        <td>${var}</td>
    </#if>
</#macro>
