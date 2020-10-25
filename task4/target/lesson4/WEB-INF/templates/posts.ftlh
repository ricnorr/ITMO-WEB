<#import "./common.ftlh" as c>

<@c.page>
    <link rel="stylesheet" href="/css/post.css">
    <#list posts?reverse as p>
        <@post p/>
    </#list>
</@c.page>

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

<#import "./post.ftlh" as p>
<#import "./404.ftlh" as error404>

<#if user_id??>
    <#assign lst=c.findAll(posts, "user_id", user_id)/>
    <#assign  user = c.findBy(users, "id", user_id)/>
    <#if user != "null">
        <@c.page>
            <@page lst 250/>
        </@c.page>
    <#else>
        <@error404.page/>
    </#if>
</#if>

<#macro page container border>
    <link rel="stylesheet" href="/css/post.css">
    <#list container?reverse as item>
        <article>
            <div class="title">${item.title}</div>
            <div class="information">By <@c.userlink c.findBy(users, "id", item.user_id)/>, 2 days ago,
                translation
            </div>
            <div class="body">
                <@c.display item.text border/>
            </div>
            <ul class="attachment">
                <li>Visit users <a href="/users">list</a></li>
                <li><a href="/post?post_id=${item.id}">View all</a></li>
            </ul>
            <div class="footer">
                <div class="left">
                    <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                    <span class="positive-score">+173</span>
                    <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
                </div>
                <div class="right">
                    <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                    2 days ago
                    <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                    <a href="#">68</a>
                </div>
            </div>
        </article>
    </#list>
</#macro>