function getCustomerById() {
    var currentCustomerId = localStorage.getItem('customer-id');

    $.ajax({
        url: `/customer/`+ currentCustomerId,
        type: 'GET',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
}).then(function (data) {
    console.log(data)
    return data
})
}

function addToCartOrCreateNewCartAndAdd(productId) {

    let existing = localStorage.getItem('purches-id');
    let currentCustomerId = localStorage.getItem('customer-id');
    let newPurchesId;

if(existing === null){
    $.ajax({
        url: `/purchase/new2/` + currentCustomerId,
        type: 'POST',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (result) {
            console.log(result.id)
            newPurchesId = result.id
            localStorage.setItem('purches-id', newPurchesId)
            return result
        }
    })
    //save product in purches
}else{
    $.ajax({
        url: `/purchase/`+ existing +`/addProductToPurches/` + productId,
        type: 'POST',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (result) {
            console.log("kommer till else")
            console.log(result)
        }
    })
}
}

function increaseQuantityWithOne(productId){
    var purchesId = localStorage.getItem('purches-id');
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/purchase/" + purchesId + "/addByOne/" + productId, //ädras
        data: JSON,
        success: function() {
            location.reload();
        }
    });
}

function decreaseQuantityWithOne(productId){
    var purchesId = localStorage.getItem('purches-id');
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/purchase/" + purchesId + "/subtractByOne/" + productId, //ädras
        data: JSON,
        success: function() {
            location.reload();
        }
    });
}

function removeProduct(productId){
    var purchesId = localStorage.getItem('purches-id');
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/purchase/" + purchesId + "/removeProduct/" + productId, //ädras
        data: JSON,
        success: function() {
            location.reload();
        }
    });
}

function confirmOrder(){
    var purchesId = localStorage.getItem('purches-id');
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/purchase/" + purchesId + "/checkout", //ändras
        dataType: "json"
    })
}

$(document).ready(function () {



    var purchesId = localStorage.getItem('purches-id');
    $.ajax({
        url: "http://localhost:8080/purchase/" + purchesId,
        dataType: "json"
    }).then(function (data) {
        console.log(data);
        data.purchaseEntries.forEach(element => {
            $(`<tr>
                        <td class="col-md-6">
                        <div class="media">
                            <a class="thumbnail pull-left" href="#"> <img class="media-object" src="http://icons.iconarchive.com/icons/custom-icon-design/flatastic-2/72/product-icon.png" style="width: 72px; height: 72px;"> </a>
                            <div class="media-body">
                                <h4 class="media-heading">${element.productName}</h4>
                                <h5 class="media-heading">${element.productDescription}</h5>
                            </div>
                        <td class="col-md-1" style="text-align: center">
                        </td>
                        <td class="col-md-1 text-center"><strong>${element.currentPrice} SEK</strong></td> 
                        <td class="col-md-1">
                        <button class="btn btn-secondary" style="margin-left: 40px display: inline-block" onclick="decreaseQuantityWithOne(${element.productId})">-</button>
      <div class="amount" style="display: inline-block"> ${element.quantity}</div>
      <button class="btn btn-secondary" style="display: inline-block" onclick="increaseQuantityWithOne(${element.productId})">+</button>
                        <button type="button" class="btn btn-danger" style="display: inline-block margin-left: 40px" onclick="removeProduct(${element.productId})">
                            <span class="glyphicon glyphicon-remove"></span> Remove
                        </button></td>
                    </tr>`).insertBefore($("#afterProductsCartPage"))
            //document.getElementById("totalPrice").innerHTML = data.totalPrice + " SEK";
        });
            $("#finalPrice").html(`<h3><strong>${data.totalPrice} SEK2</strong></h3>`);
    })

    $.ajax({
        url: "http://localhost:8080/purchase/" + purchesId,
        dataType: "json"
    }).then(function (data) {
        console.log("132");
        console.log(data);
        data.purchaseEntries.forEach(element => {
            $(`<tr>
                        <td class="col-md-6">
                        <div class="media">
                            <a class="thumbnail pull-left" href="#"> <img class="media-object" src="http://icons.iconarchive.com/icons/custom-icon-design/flatastic-2/72/product-icon.png" style="width: 72px; height: 72px;"> </a>
                            <div class="media-body">
                                <h4 class="media-heading">${element.productName}</h4>
                                <h5 class="media-heading"> ${element.productDescription}</h5>
                            </div>
                        <td class="col-md-1" style="text-align: center">
                        </td>
                        <td class="col-md-1 text-center"><strong>${element.currentPrice} SEK</strong></td> 
                        <td class="col-md-1">
                       <div class="amount" style="display: inline-block; margin-left: 78px"> ${element.quantity}</div>
                    </tr>`).insertBefore($("#afterProductsReceiptPage"))
            //document.getElementById("finalPrice").innerHTML = data.totalPrice + " SEK";
            //document.getElementById("finalPrice").innerHTML = data.totalPrice + " SEK";
        });
            $("#finalPrice").html(`<h3><strong>${data.totalPrice} SEK3</strong></h3>`);
    })



    let display = "all"

    $('#searchButton').click(function (event) {
        let param = $.trim($('#searchField').val());
        console.log(param)
        display = "showProductsBySearch/" + param;
        //window.location = "http://www.yourdomain.com/";
        location.reload()
    })

    $('#clearSearchAndShowAllButton').click(function (event) {
        display = "all"
        location.reload()
    })

    $.ajax({
        url: "http://localhost:8080/product/" + display,
        dataType: "json"
    }).then(function (response) {
        console.log(response);
        response.forEach(element => {
            $(`<tr>
                        <td class="col-md-6">
                        <div class="media">
                            <a class="thumbnail pull-left" href="#"> <img class="media-object" src="http://icons.iconarchive.com/icons/custom-icon-design/flatastic-2/72/product-icon.png" style="width: 72px; height: 72px;"> </a>
                            <div class="media-body">
                                <h4 class="media-heading">${element.productName}</h4>
                                <h5 class="media-heading"> ${element.productDescription}</h5>
                            </div>
                        <td class="col-md-1" style="text-align: center">
                        </td>
                        <td class="col-md-1 text-center"><strong>${element.currentPrice} SEK</strong></td> 
                        <td class="col-md-1">
                        <button class="btn btn-secondary" style="display: inline-block" onclick="addToCartOrCreateNewCartAndAdd(${element.productId})">Add to cart</button>
                    </tr>`).insertBefore($("#afterProductsProductPage"))
        });
    })
});



