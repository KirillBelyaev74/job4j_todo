$(document).ready(function () {
    $("#perform").on("click", function () {
        let values = [];
        $('[name="item"]:checked').each(function () {
            values.push($(this).val());
        });
        if (values.length !== 0) {
            let itemsId = values.join(",");
            $.ajax({
                type: "POST",
                url: "http://localhost:8080/job4j_todo/completed",
                dataType: "json",
                data: {"itemsId": itemsId}
            }).done(function (data) {
                let stringJson = JSON.stringify(data);
                let answer = JSON.parse(stringJson);
                let message = answer.text;
                alert(message);
                window.location.href = "http://localhost:8080/job4j_todo/";
            }).fail(function (error) {
                alert("Что то пошло не так " + error.val())
            });
        }
    })
});