<#import "./common.ftlh" as c>
<#import "./404.ftlh" as error404>
<#import "./posts.ftlh" as postTemplate>
<#if post_id?? && c.findBy(posts, "id", post_id) != "null">
    <@c.page>
        <@postTemplate.page [c.findBy(posts, "id", post_id)] c.findBy(posts, "id", post_id).text?length />
    </@c.page>
<#else>
    <@error404.page/>
</#if>



