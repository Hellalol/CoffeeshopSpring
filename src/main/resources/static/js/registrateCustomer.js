function showPasswordFunction() {
    $("#inputCheckbox").change(function () {
        if ($(this).is(':checked')) {
            $('#inputPassword').attr("type", "text");
        } else {
            $('#inputPassword').attr("type", "password");
        }
    });
}

function validateForm() {
    'use strict';
    window.addEventListener('load', function () {
        let forms = document.getElementsByClassName('needs-validation');
        let validation = Array.prototype.filter.call(forms, function (form) {
            form.addEventListener('submit', function (event) {
                if (form.checkValidity() === false) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
                $("#check-label").css("color", "#000")
            }, false);
        });
    }, false);
}

function validateEmail(email) {
    let re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}

function validateLength(input1, input2, input3, input4) {
    return input1.length === 0 || input2.length === 0 || input3.length === 0 || input4.length === 0;
}

$(document).ready(function () {



    showPasswordFunction();
    validateForm();
    $("#customer-form").submit(function (event) {
        event.preventDefault();

        let allInputsAreFilledOutCorrectly = true;

        //Input fields stored in variables
        let inputfirstname = $.trim($('#inputname').val());
        let inputlastname = $.trim($('#inputlastname').val());
        let inputusername = $.trim($('#inputEmail').val());
        let inputpassword = $.trim($('#inputPassword').val());

        let inputname = inputfirstname + " " + inputlastname;

        let data = {name: inputname, username: inputusername, password: inputpassword, active: true};
        console.log(data);
        //Check if anything is missing or email invalid
        if (validateLength(inputfirstname, inputlastname, inputusername, inputpassword) || !validateEmail(inputusername))
            allInputsAreFilledOutCorrectly = false;

        if (allInputsAreFilledOutCorrectly) {
            $('#submit-button').attr("disabled", true);
            $('#reset-button').attr("disabled", true);
            console.log("All input finns")
            $.ajax({
                url: '/customer/register',
                type: 'POST',
                data: JSON.stringify({name: inputname, username: inputusername, password: inputpassword}),
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                async: false,
                success: function (result) {
                    console.log("Success!")
                    $('#result-message').html(
                        `<p>Hi ${inputfirstname}!<br>
                         Thank you for the account registration.<br>
                        "Please use your email and password to log in.</p>`
                    );
                    return result;
                }
            });
        }
    });
});
