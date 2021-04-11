$(document).ready(function () {
    $("#addDescription").on("click", function () {
        let description = $("#description").val();
        if (description === "") {
            alert("Опишите задачу которую хотите добавить!")
        } else {
            $.ajax({
                type: "POST",
                url: "http://localhost:8080/job4j_todo/addDescription",
                dataType: "json",
                data: {"description": description}
            }).done(function (data) {
                let stringJson = JSON.stringify(data);
                let answer = JSON.parse(stringJson);
                let message = answer.item + "\n" + answer.text;
                alert(message);
                window.location.href = "http://localhost:8080/job4j_todo/";
            }).fail(function (error) {
                alert("Что то пошло не так " + error.val())
            });
        }
    })
});