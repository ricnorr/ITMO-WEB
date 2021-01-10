<#import "commons.ftlh" as c>

<@c.page>
    <div class="post-page">
        <#if error??>
            ${error}
        <#else>
            <div class="post-title">Title: ${post.title!}</div>
            <div class="post-text">Text: ${post.text!}</div>
            <div class="post-author">User id: ${post.user.id!}</div>
            <div class="tags">
                Tags:
                <#list post.tags as tag>
                    ${tag.name}
                </#list>
            </div>
            <#if user??>
                <form class="comment-form-box" method="post">
                    <textarea name="text">${commentForm.text!}</textarea>
                    <input type="hidden" name="postId" value="${post.id}">
                    <input type="submit"/>
                    <@c.error "commentForm.text"/>
                </form>
            </#if>
            <table class="comment-table">
                <tr>
                    <th>text</th>
                    <th>author</th>
                    <th>creation time</th>
                </tr>
                <#list post.comments as comment>
                    <tr>
                        <td>
                            ${comment.text!}
                        </td>
                        <td>
                            ${comment.user.id!}
                        </td>
                        <td>
                            ${(comment.creationTime?datetime)!}
                        </td>
                    </tr>
                </#list>
            </table>
        </#if>

    </div>
</@c.page>
