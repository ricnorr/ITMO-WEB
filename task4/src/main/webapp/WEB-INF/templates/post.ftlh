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
        <div class="information">By user_id: ${element.user_id}</div>
        <div class="body">
            <p><@display element.text/></p>
        </div>
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
</#macro>


<#macro display var>
    <#if var?length gt 250>
        <td>${var?substring(0, 250)}…</td>
    <#else>
        <td>${var}</td>
    </#if>
</#macro>
