<#import "./common.ftlh" as c>
<@c.page>
    <#if post_id??>
        <@page [c.findBy(posts, "id", post_id)] "null"/>
    </#if>
</@c.page>

<#macro page source border>
    <link rel="stylesheet" href="/css/post.css">
    <#list source?reverse as p>
        <article>
            <@displayHeader p/>
            <div class="information">By user: ${c.findBy(users, "id", p.user_id).handle}</div>
            <@displayBody p border/>
        </article>
    </#list>
</#macro>

<#macro displayHeader element>
    <div class="header">${element.title}</div>
</#macro>

<<<<<<< HEAD
<#macro post element>
    <article>
        <div class="title">${element.title}</div>
        <div class="information">By user: ${c.findBy(users, "id", element.user_id).handle}</div>
        <div class="body">
            <p><@display element.text/></p>
        </div>
    </article>
=======
<#macro linkById element>
    <a href="/post?post_id=${displayId(element)}"><#nested></a>
>>>>>>> task4.5
</#macro>

<#function displayId element>
    <#return element.id/>
</#function>

<#macro displayBody element border>
    <div class="body">
        <@display element.text border/>
    </div>
</#macro>

<#macro display var border>
    <#if border?string != "null" && var?length gt border>
        <td>${var?substring(0, border)}…</td>
    <#else>
        <td>${var}</td>
    </#if>
</#macro>
