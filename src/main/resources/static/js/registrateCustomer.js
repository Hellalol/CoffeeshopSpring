function showPasswordFunction() {
    $("#inputCheckbox").change(function(){
        if($(this).is(':checked')){
            $('#inputPassword').attr("type","text");
        } else {
            $('#inputPassword').attr("type","password");
        }
    });
}

function validateForm() {
    'use strict';
    window.addEventListener('load', function() {
        // Fetch all the forms we want to apply custom Bootstrap validation styles to
        var forms = document.getElementsByClassName('needs-validation');
        // Loop over them and prevent submission
        var validation = Array.prototype.filter.call(forms, function(form) {
            form.addEventListener('submit', function(event) {
                if (form.checkValidity() === false) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
            }, false);
        });
    }, false);
}

function validateEmail(email){
    let re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}

$(document).ready(function() {
    showPasswordFunction();
    validateForm();
        $("#customer-form").submit(function(event){

            event.preventDefault();

            let allInputsAreFilledOutCorrectly = true;

            //Input fields stored in variables
            let inputname = $.trim($('#inputname').val());
            let inputlastname = $.trim($('#inputlastname').val());
            let inputusername = $.trim($('#inputEmail').val());
            let inputpassword = $.trim($('#inputPassword').val());


            //Check if anything is missing
            if(inputpassword.length === 0 || inputusername.length === 0 || inputlastname.length === 0 || inputname.length === 0 || !validateEmail(inputusername))
                allInputsAreFilledOutCorrectly = false;

            let data = { name: inputname + " " + inputlastname, username : inputusername, password : inputpassword};

            if(allInputsAreFilledOutCorrectly){
                $('#submit-button').attr("disabled", true);
                $('#reset-button').attr("disabled", true);
                console.log("All input finns")
                $.ajax({
                    url: '/customer',
                    type: 'POST',
                    data: JSON.stringify(data),
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    async: false,
                    success: function (result) {
                        console.log("Success!")
                        $('#result-message').append("Hi " + inputname + "! Thank you for the account registration." + '<br>' +
                            "Please use your email and password to log in.");
                    }
                });
            }
            
    });
});
