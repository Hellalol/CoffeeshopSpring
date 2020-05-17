function showPasswordFunction() {
    $("#inputCheckbox").change(function(){
        if($(this).is(':checked')){
            $('#inputPassword').attr("type","text");
        } else {
            $('#inputPassword').attr("type","password");
        }
    });
}

// Example starter JavaScript for disabling form submissions if there are invalid fields
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

$(document).ready(function() {
    showPasswordFunction();
    validateForm();
        $("#customer-form").submit(function(event){

            event.preventDefault();

            let counter = 0;
            let inputname = $.trim($('#inputname').val());
                if(inputname.length === 0)
                    counter++;
            let inputlastname = $.trim($('#inputlastname').val());
                if(inputlastname.length === 0)
                    counter++;
            let inputusername = $.trim($('#inputEmail').val());
                if(inputusername.length === 0)
                    counter++;
            let inputpassword = $.trim($('#inputPassword').val());
                if(inputpassword.length === 0)
                    counter++;
            let data = { name: inputname + " " + inputlastname, username : inputusername, password : inputpassword};

            console.log(data);
            console.log(counter);

            if(counter < 1){
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
