<#macro table columnNames>
    <#import "./common.ftlh" as c>
    <@c.page>
        <link rel="stylesheet" href="/css/table.css">
        <div class="datatable">
            <table>
                <thead>
                <tr>
                    <#list columnNames as col>
                        <th>${col}</th>
                    </#list>
                </tr>
                </thead>
                <tbody>
                <#nested/>
                </tbody>
            </table>
        </div>
    </@c.page>
</#macro>