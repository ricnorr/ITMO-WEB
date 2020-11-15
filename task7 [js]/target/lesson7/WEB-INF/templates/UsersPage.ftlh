<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <#if user?? && user.admin>
                    <th>Admin</th>
                </#if>
            </tr>
            </thead>
            <tbody>
            <template>
                <tr>
                    <td class="user_id"></td>
                    <td class="user_login"></td>
                    <#if user?? && user.admin>
                        <td class="enable_link"></td>
                    </#if>
                </tr>
            </template>
            <tr class="noData">
                <td colspan="3">
                    No data
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <script>
        $(function () {
            const $noData = $(".users .noData");
            $noData.find("td").text("Loading...");

            $.ajax({
                dataType: "json",
                data: {
                    action: "findAll"
                },
                success: function(response) {
                    response["users"].forEach(function(user) {
                        $noData.hide();
                        const $tr = $($(".users template").prop("content")).clone();
                        $tr.find(".user_id").text(user["id"]);
                        $tr.find(".user_login").text(user["login"]);
                        $tr.find("tr").attr("data-userId", user["id"]);
                        <#if user?? && user.admin>
                             if (user["admin"] === true) {
                                 $tr.find(".enable_link").append("<a style=\"color:blue\" id=\""+user["id"]+"\">true | disable</a>");
                             } else {
                                 $tr.find(".enable_link").append("<a style=\"color:blue\" id=\""+user["id"]+"\">false | enable</a>");
                             }
                        </#if>
                        $(".users tbody").append($tr);
                    });
                    $(".users a").click(function() {
                            const id = $(this).attr("id");
                            const text = $(this).text();
                            const action = (text === "true | disable") ? "setUser" : "setAdmin";
                            ajaxWrap.call(this, {action:action, id:id}, null, (response) => {
                                if (response["message"] === "user") {
                                    $(this).text("false | enable");
                                } else if (response["message"] === "admin") {
                                    $(this).text("true | disable");
                                }
                                alert("done");
                            });
                    })
                    $(".users tr").click(function() {
                        $.ajax({
                            dataType: "json",
                            data: {
                                action: "findUser",
                                userId: $(this).attr("data-userId")
                            },
                            success: function (response) {
                                notify(response["user"].creationTime);
                            },
                            type: "POST"
                        })
                    });
                },
                type: "POST"
            });
        })
    </script>
</@c.page>
