$(document).ready(function () {
    let listCustomers = "";
    $.ajax({
        url: "http://localhost:8080/customer/all",
    }).then(function(data){
        console.log(data);
        data.forEach(elm => {
           listCustomers +=
               `<tr>
                    <td>${elm.name}</td>
                    <td>${elm.username}</td>
                    <td>${elm.premiumCustomer}</td>
                    <td><button class="btn btn-secondary" id="submit-button">Show Orders</button>
                    </td>
                </tr>`;
            });
        $('#customer-list').html(listCustomers);
    })
});