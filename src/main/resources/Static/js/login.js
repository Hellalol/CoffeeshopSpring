$(document).ready(function () {
    setOnClickListeners();
});

function setOnClickListeners() {
    $('#submit').click(function (event) {
        event.preventDefault()
        let username = $("#username").val();
        let password = $("#password").val();
        console.log(username)
        console.log(password)
        if(username.length>0 && password.length>0){
            authenticate(username,password);
        }
    })
}

function authenticate(username, password) {

    $.ajax({
        type: "GET",
        url: `http://localhost:8080/login/${username}/${password}`,
        dataType: 'json',
        success: function (response) {
            localStorage.setItem("customer-id",response.id);
            determineRoleAndRedirect(response);
        }
    })
}

function determineRoleAndRedirect(user) {
    if (user.userType === "ROLE_ADMIN")
        $(location).attr('href','admin-show-customers.html')
    else
        $(location).attr('href','product-details.html')
}

