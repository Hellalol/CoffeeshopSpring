function getProduct(id){
    $.ajax({
        url: `http://localhost:8080/customer/product/${JSON.parse(id)}`,
    }).then(function (data) {
        return data
    });
}

$(document).ready(function () {
    $.ajax({
        url: "http://localhost:8080/customer/order/3",
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
            console.log("elm2");
            console.log(elm2);
            console.log("getProduct()§");
            console.log(getProduct(elm2.productId));
            console.log("product id");
            console.log(elm2.productId);
            console.log("elm");
            console.log(elm);

            productList += `<div id="collapse${elm.id}" class="collapse" aria-labelledby="heading" data-parent="#show-order">
            <div class="card-body order">
                <tr>
                 <td scope="row">Brand: ${elm2.productId}</td>
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