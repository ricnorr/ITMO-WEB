<#import "commons.ftlh" as c>

<@c.page>
    <div class="form-box">
        <form class="article-form" method="post">
            <div class="title">
                <label for="title">Enter article's title:</label>
                <input id="title" name="title"/>
            </div>

            <div class="text">
                <label for="text">Enter article:</label>
                <textarea id="text" name="text"></textarea>
            </div>

            <div class="button">
                <input value="submit" type="submit"/>
            </div>

            <div class="error" style="font-size: 0.7rem; color: red;">${error!}</div>
        </form>
    </div>

    <script>
        $(function () {
            $(".form-box form").submit(function () {
                const title = $(this).find("input[name='title']").val();
                const text = $(this).find("textarea[name='text']").val(); // HOW TO DEBUG THIS???????????????
                const $error = $(this).find(".error");
                ajaxWrap({action: "send", title: title, text: text}, $error);
                return false;
            });
        });
    </script>
</@c.page>