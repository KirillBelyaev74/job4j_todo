$(document).ready(function () {
    $("#loginCustomer").on("click", function () {
        let login = $("#login").val();
        let password = $("#password").val();
        if (login === "" || password === "") {
            alert("Введите логин и пароль!");
        } else {
            $.ajax({
                type: "POST",
                url: "http://localhost:8080/job4j_todo/login",
                dataType: "json",
                data: {"login": login, "password": password}
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
})