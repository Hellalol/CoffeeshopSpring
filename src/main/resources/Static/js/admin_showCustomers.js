$(document).ready(function () {
    let listCustomers = "";
    $.ajax({
        url: "http://localhost:8080/customer/all",
    }).then(function(data){
        data.forEach(elm => {
        addEventToBtn(elm.id);
           listCustomers +=
               `<tr>
                    <td>${elm.name}</td>
                    <td>${elm.username}</td>
                    <td>${elm.premiumCustomer}</td>
                    <td><button class="btn btn-secondary" id="btn${elm.id}">Show Orders</button>
                    </td>
                </tr>`;
            });
        $('#customer-list').html(listCustomers);
    })
});

function addEventToBtn(id){
        $("#customer-list").on("click", `#btn${id}`, function(){
            localStorage.setItem(`id`, id);
        });
}