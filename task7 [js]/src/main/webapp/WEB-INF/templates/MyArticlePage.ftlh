<#import "commons.ftlh" as c>

<@c.page>
    <#if error??>
        <div class="error">${error}</div>
    <#elseif articles??>
        <table class="article-table">
            <tr>
                <th>Id</th>
                <th>Title</th>
                <th>Hide/Show</th>
            </tr>

            <#list articles as article>
                <tr class="${article.id}">
                    <td>${article.id}</td>
                    <td>${article.title}</td>
                    <td>
                        <form class="form-box" method="post">
                            <div>${error!}</div>
                            <input type="submit" name="action" value=${article.hidden?then("show","hide")}>
                            <input type="hidden" name="id" value="${article.id}">
                            <input type="hidden" name="title" value="${article.title}">
                        </form>
                    </td>
                </tr>
            </#list>
        </table>
    </#if>

    <script>
        $(function () {
            $(".form-box").submit(function () {
                const id = $(this).find("input[name='id']").val();
                const action = $(this).find("input[name='action']").val();
                ajaxWrap.call(this, {id: id, action: action}, null, (response) => {
                        if (response["error"]) {
                            alert("error");
                        } else {
                            $(this).find("input[name='action']").val(response["message"]);
                            alert("done");
                        }
                    }
                );
                return false;
            });
        })
        ;
    </script>
</@c.page>