<#import "./common.ftlh" as c>
<#import "./404.ftlh" as error404>

<#if user_id??>
    <#assign key="id">
    <#assign value=user_id>
    <#if !user_id?is_number>
        <@error404.page/>
    </#if>
<#elseif handle??>
    <#assign key="handle">
    <#assign value=handle>
</#if>
<#assign u = c.findBy(users, key, value)!/>

<#if u != "null">
    <@c.page>
    <link rel="stylesheet" href="/css/user.css">
    <div class="user">
        <div class="left">
            <@displayBLock u/>
        </div>
        <div class="right">
            <img src="/img/no-title.jpg">
        </div>
    </div>
    <div class="left-arrow">
        <@activeCssArrow c.getPrev(users, "handle", u.handle)>←</@activeCssArrow>
    </div>
    <div class="right-arrow">
        <@activeCssArrow c.getNext(users, "handle", u.handle)>→</@activeCssArrow>
    </div>
</@c.page>
<#else>
    <@error404.page/>
</#if>

<#macro activeCssArrow next>
    <#if next != "null">
        <a style="
            font-weight: bold;
            font-size: 2rem;
            color: #3B5998;
            " href="/user?handle=${next}"><#nested></a>
    <#else>
        <span style="
            font-weight: bold;
            font-size: 2rem;
            color: gray;
            "><#nested></span>
    </#if>
</#macro>

<#macro displayBLock obj>
    <span>
        ${obj.name}
    </span>
    <ul class="list">
        <li>
            <@c.userlink obj/>
        </li>
        <li>
            ${obj.id}
        </li>
        <li>
            ${obj.name}
        </li>
    </ul>
</#macro>



