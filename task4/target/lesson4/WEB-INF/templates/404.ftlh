<#import "./common.ftlh" as c>
<@page/>
    
<#macro page>
    <@c.page>
        <div class="404error">
            <p style="font-size: 2rem; font-weight: bold">404</p>
            <p>No such page</p>
        </div>
    </@c.page>
</#macro>