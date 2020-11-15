window.notify = function (message) {
    $.notify(message, {
        position: "right bottom",
        className: "success"
    });
}


baseSuccess = (response) => {
    if (response["error"]) {
        $error.text(response["error"]);
    } else if (response["redirect"]) {
        console.log("redirect");
        location.href = response["redirect"];
    } else {
        alert("done!");
    }
}
ajaxWrap = function (map, $error, success = baseSuccess) {
    $.ajax({
        type: "POST",
        data: map,
        dataType: "json",
        success: success
    });
}
