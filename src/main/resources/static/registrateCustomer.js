



function showPasswordFunction() {
    $("#inputCheckbox").change(function(){
        if($(this).is(':checked')){
            $('#inputPassword').attr("type","text");
        } else {
            $('#inputPassword').attr("type","password");
        }
    });
}

$(document).ready(function() {
    showPasswordFunction();
        $("#customer-form").submit(function(event){

            event.preventDefault();

            let inputname = $.trim($('#inputname').val());
            let inputusername = $.trim($('#inputEmail').val());
            let inputpassword = $.trim($('#inputPassword').val());

            let data = { name: inputname, username : inputusername, password : inputpassword};

            console.log(data);
            $.ajax({
                url: '/customer',
                type: 'POST',
                data: JSON.stringify(data),
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                async: false,
                success: function (result) {
                    $('#result-message').append("You made it!")
                }
        });
    });
});
