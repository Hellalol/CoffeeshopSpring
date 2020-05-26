$(document).ready(function () {
    setOnClickListeners();
});

function setOnClickListeners() {
    $('#submit').click(function (event) {
        event.preventDefault()
        let username = $("#username").val();
        let password = $("#password").val();
        if (username.length > 0 && password.length > 0) {
            requestJSON(username, password);
        }
    })
}

function requestJSON(username, password) {
    $.ajax({
        type: "GET",
        url: `http://localhost:8080/login/${username}/${password}`,
        dataType: 'json',
        success: function (user) {
            authenticate(user);
            sessionStorage.setItem("customer-id", user.id);

        }
    })
}

function authenticate(user) {
    if (user == null) {
        $('#errormsg').html(`<i>Fel användarnamn eller lösenord!</i>`);
        $(".login-form").effect("bounce");
    } else if (user.userType === "ROLE_ADMIN")
        $(location).attr('href', 'admin-show-customers.html')
    else
        $(location).attr('href', 'productPage.html')
}



