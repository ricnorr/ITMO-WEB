<#macro page>
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="stylesheet" href="/css/normalize.css">
        <link rel="stylesheet" href="/css/style.css">
        <link rel="icon" href="/favicon.ico">
    </head>
    <body>
    <@header/>
    <div class="middle">
        <@sidebar/>
        <main>
            <#nested/>
        </main>
    </div>
    <@footer/>
    </body>
    </html>
</#macro>

<#macro header>
    <header>
        <a href="#" class="logo"><img alt="Codeforces" title="Codeforces"
                                      src="/img/codeforces-vs-coronavirus-65.png"></a>
        <div class="languages">
            <a href="#"><img alt="In English" title="In English"
                             src="/img/gb.png"></a>
            <a href="#"><img alt="In Russian" title="In Russian"
                             src="/img/ru.png"></a>
        </div>

        <div class="enter-or-register-box">
            <#if logged_user_id?? && findBy(users, "id", logged_user_id)!="null">
                <@nameonly findBy(users, "id", logged_user_id)/>
                |
                <a href="/logout">Logout</a>
            <#else>
                <a href="/enter">Enter</a>
                |
                <a href="/register">Register</a>
            </#if>
        </div>
        <nav>
            <#assign items=[
            {"href": "/index", "name": "Home"},
            {"href": "/misc/help", "name": "Help"},
            {"href": "/contests", "name": "Contests"},
            {"href": "/users", "name":"Users"}
            ]/>
            <ul>
                <#list items as item>
                    <li>
                        <#if uri?starts_with(item.href)>
                            <a style="text-decoration: underline;" href="${item.href}">${item.name}</a>
                        <#else>
                            <a href="${item.href}">${item.name}</a>
                        </#if>

                    </li>
                </#list>
            </ul>
        </nav>
    </header>
</#macro>

<#macro sidebar>
    <#import "./post.ftlh" as p>
    <aside>
        <#list posts?reverse as item>
            <section>
                <@HeaderBodyFooterPost "Post # ${item.id}" item 250/>
            </section>
        </#list>
    </aside>
</#macro>

<#macro HeaderBodyFooterPost header item border>
    <div class="header">
        ${header}
    </div>
    <@displayBody item.text border/>
    <div class="footer">
        <a href="/post?post_id=${item.id}">View all</a>
    </div>
</#macro>

<#macro footer>
    <footer>
        2010-2020 &copy; by Mike Mirzayanov
    </footer>
</#macro>

<#macro userlink user>
    <#assign map={"RED" : "red", "BLUE" : "blue", "GREEN" : "green" }>
    <a style="color:${map[user.color]}; text-decoration: none" href="/user?handle=${user.handle}">${user.handle}</a>
</#macro>

<#macro nameonly user>
    <a href="/user?handle=${user.handle}">${user.handle}</a>
</#macro>

<#function findBy items key value>
    <#list items as item>
        <#if item[key] == value>
            <#return item/>
        </#if>
    </#list>
    <#return "null">
</#function>

<#function findAll items key value>
    <#assign result=[]>
    <#list items as item>
        <#if item[key] == value>
            <#assign result=result+[item]>
        </#if>
    </#list>
    <#return result>
</#function>

<#function getNext items key value>
    <#list items as item>
        <#if item[key] == value>
            <#if item_has_next>
                <#return items[item_index + 1][key]>
            <#else>
                <#return "null">
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

<#macro displayBody text border>
    <div class="body">
        <@display text border/>
    </div>
</#macro>

<#macro display var border>
    <#if var?length gt border>
        ${var?substring(0, border)}…
    <#else>
        ${var}
    </#if>
</#macro>

<#macro linkById element>
    <a href="/post?post_id=${element}"><#nested></a>
</#macro>