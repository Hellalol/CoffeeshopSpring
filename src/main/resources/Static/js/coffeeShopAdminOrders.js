function getProduct(id) {
    $.ajax({
        url: `/customer/product/${id}`,
        type: 'GET',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        }).then(function (data) {
        console.log(data);
            return data
        })
}

$(document).ready(function () {
   // let idFromStorage = localStorage.getItem("id");
    $.ajax({
        url: `http://localhost:8080/customer/order/4`,
    }).then(function (data) {

        $('.collapse').collapse();
        let productList = ``;

        data.forEach(elm => {
            productList += `<div class="card">
                 <div class="card-header ,heading">
                     <h2 class="mb-0">
                         <button class="btn btn-link btn-block text-left" type="button" data-toggle="collapse"
                              data-target="#collapse${elm.id}" aria-expanded="false" aria-controls="collapse${elm.id}">
                          Order ${elm.id}
                         </button>
                     </h2>
                 </div>`;
        elm.purchaseEntries.forEach(elm2 => {

            productList += `<div id="collapse${elm.id}" class="collapse" aria-labelledby="heading" data-parent="#show-order">
            <div class="card-body order">
                <tr>
                 <td scope="row">Brand: ${elm2.productName}</td>
                 <td scope="row">Description: ${elm2.productDescription}</td>
                 <td scope="row">Price: ${elm2.currentPrice} SEK</td>
                 <td scope="row">Quantity: ${elm2.quantity}</td>         
                 <td scope="row">Total Price: ${elm.totalPrice} SEK</td>
                </tr> 
            </div>
        </div> `;

            });
        });
        $('.order').append(productList);
    });
});