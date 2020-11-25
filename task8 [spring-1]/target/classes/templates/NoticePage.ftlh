<#-- @ftlvariable name="noticeForm" type="ru.itmo.wp.form.NoticeCredentials" -->
<#import "commons.ftlh" as c>

<@c.page>
   <form method="post">
       <textarea name="content">${noticeForm.content!}</textarea>
       <input type="submit" value="send">
       <@c.error "noticeForm.content"/>
   </form>
</@c.page>