<#import "./common.ftlh" as c>

<#assign null = "null"!/>
<@c.page>
    <link rel="stylesheet" href="/css/user.css">
    <div class="user">
        <div class="left">
            <#if id??>
                <#assign key="id">
                <#assign value=id>
            <#elseif handle??>
                <#assign key="handle">
                <#assign value=handle>
            </#if>
            <#assign u = c.findBy(users, key, value)!/>
            <#if u?has_content>
                <@displayBLock u/>
            <#else>
                no user
            </#if>
        </div>
        <div class="right">
            <img src="/img/no-title.jpg">
        </div>
    </div>
    <div class="left-arrow">
        <@activeCssArrow getPrev(users, key, value)>←</@activeCssArrow>
    </div>
    <div class="right-arrow">
        <@activeCssArrow getNext(users, key, value)>→</@activeCssArrow>
    </div>
</@c.page>

<#function getNext items key value>
    <#list items as item>
        <#if item[key] == value>
            <#if item_has_next>
                <#return items[item_index + 1][key]>
            <#else>
                <#return null>
            </#if>
        </#if>
    </#list>
</#function>

<#function getPrev items key value>
    <#list items as item>
        <#if item[key] == value>
            <#if item_index gt 0>
                <#return items[item_index - 1][key]>
            <#else>
                <#return "null">
            </#if>
        </#if>
    </#list>
</#function>

<#macro activeCssArrow next>
    <#if "${next}" != null>
        <a style="
            font-weight: bold;
            font-size: 2rem;
            color: #3B5998;
            " href="/user?${key}=${next}"><#nested></a>
    <#else>
        <span style="
            font-weight: bold;
            font-size: 2rem;
            color: gray;
            "><#nested></span>
    </#if>
</#macro>

<#macro displayBLock obj>
    <a href="#">
        ${obj.name}
    </a>
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



