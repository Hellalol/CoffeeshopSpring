function getProduct(id) {
    $.ajax({
        url: `/customer/product/${id}`,
        type: 'GET',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        }).then(function (data) {
            return data
        })
}

$(document).ready(function () {
    $("#logout").click(function (event) {
        localStorage.clear()
    })
    $("#goBack").click(function (event) {
        localStorage.removeItem("id")
    })

   let idFromStorage = localStorage.getItem("id");
    $.ajax({
        url: `http://localhost:8080/customer/order/${idFromStorage}`,
    }).then(function (data) {

        $('.collapse').collapse();
        let productList = ``;

        data.forEach(elm => {
            productList += `<div class="card">
                 <div class="card-header ,heading">
                     <h4 class="mb-0">
                                Order # ${elm.id} Total Amount: ${elm.totalPrice} SEK
                         <button class="btn btn-link btn-block text-left" type="button" data-toggle="collapse"
                              data-target="#collapse${elm.id}" aria-expanded="false" aria-controls="collapse${elm.id}">
                                Show Order
                         </button>
                     </h4>
                 </div>`;
        elm.purchaseEntries.forEach(elm2 => {

            productList += `<div id="collapse${elm.id}" class="collapse" aria-labelledby="heading" data-parent="#show-order">
            <div class="card-body order">
                <tr>
                 <td scope="row">Brand: ${elm2.productName}</td>
                 <td scope="row">Description: ${elm2.productDescription}</td>
                 <td scope="row">Price: ${elm2.currentPrice} SEK</td>
                 <td scope="row">Quantity: ${elm2.quantity}</td>   
                     <td scope="row">
                          <button type="button" class="btn btn-secondary" data-container="body" data-toggle="popover" 
                          data-placement="right">
                              Info
                          </button>
                        </td> 
                       
                </tr> 
            </div>
        </div> `;

            });

        });

        $('.order').append(productList);
    });
});