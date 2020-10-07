<#import "./common.ftlh" as c>
<#import "./404.ftlh" as error404>

<#if post_id??>
    <@c.page>
        <@c.displayBody c.findBy(posts, "id", post_id).text c.findBy(posts, "id", post_id).text?length/>
    </@c.page>
<#else>
    <@error404.page/>
</#if>



