$(document).ready(function () {
    $("#showAll").on("click", function () {
        let done = "all";
        getItemsByDone(done);
    })
});

$(document).ready(function () {
    $("#showFalse").on("click", function () {
        let done = false;
        getItemsByDone(done);
    })
});

$(document).ready(function () {
    $("#showTrue").on("click", function () {
        let done = true;
        getItemsByDone(done);
    })
});

$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/job4j_todo/index",
        dataType: "json"
    }).done(function (data) {
        let stringJson = JSON.stringify(data);
        let answer = JSON.parse(stringJson);
        parseAnswerToHTML(answer.items);
    }).fail(function (error) {
        alert("Что то пошло не так " + error.val())
    });
});

function getItemsByDone(done) {
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/job4j_todo/index",
        dataType: "json",
        data: {"done": done}
    }).done(function (data) {
        let stringJson = JSON.stringify(data);
        let answer = JSON.parse(stringJson);
        parseAnswerToHTML(answer.items);
    }).fail(function (error) {
        alert("Что то пошло не так " + error.val())
    });
}

function parseAnswerToHTML(answer) {
    let result = "";
    for (let index = 0; index !== answer.length; index++) {
        let item = answer[index];
        let input = "";
        if (item.done === true) {
            result += "<tr class=\"success\"><td>";
        } else {
            result += "<tr class=\"active\"><td>";
            input = "<input class=\"form-check-input\" type=\"checkbox\" name=\"item\" value=\"" + item.id + "\"><label class=\"form-check-label\">Выполнить</label>";
        }
        result += item.description + "</td><td>" + item.created + "</td><td>" + input + "</td></tr>";
    }
    $("#table").html(result);
}