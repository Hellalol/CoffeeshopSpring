function increaseQuantityWithOne(productId) {
    let purchaseId = sessionStorage.getItem('purches-id');
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/purchase/" + purchaseId + "/addByOne/" + productId, //ädras
        data: JSON,
        success: function () {
            location.reload();
        }
    });
}

function decreaseQuantityWithOne(productId) {
    let purchaseId = sessionStorage.getItem('purches-id');
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/purchase/" + purchaseId + "/subtractByOne/" + productId, //ädras
        data: JSON,
        success: function () {
            location.reload();
        }
    });
}

function removeProduct(productId) {
    let purchaseId = sessionStorage.getItem('purches-id');
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/purchase/" + purchaseId + "/removeProduct/" + productId, //ädras
        data: JSON,
        success: function () {
            location.reload();
        }
    });
}

function confirmOrder() {
    let purchaseId = sessionStorage.getItem('purches-id');
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/purchase/" + purchaseId + "/checkout", //ändras
        dataType: "json"
    })
}

$(document).ready(function () {


    $("#anotherPurchase").click(function (event) {
        sessionStorage.removeItem("purches-id")
    })

    $("#logout").click(function (event) {
        sessionStorage.clear()
    })

    let purchaseId = sessionStorage.getItem('purches-id');
    $.ajax({
        url: "http://localhost:8080/purchase/" + purchaseId,
        dataType: "json"
    }).then(function (data) {
        console.log(data);
        data.purchaseEntries.forEach(element => {
            $(`<tr>
                        <td class="col-md-6">
                        <div class="media">
                            <a class="thumbnail pull-left" href="#"> <img class="media-object" src="${element.imagePath}" style="width: 72px; height: 72px;"> </a>
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
        });
        $("#totalPrice").html(`<h3><strong>${data.totalPrice} SEK</strong></h3>`);
    })

    $.ajax({
        url: "http://localhost:8080/purchase/" + purchaseId,
        dataType: "json"
    }).then(function (data) {
        console.log("132");
        console.log(data);
        data.purchaseEntries.forEach(element => {
            $(`<tr>
                        <td class="col-md-6">
                        <div class="media">
                            <a class="thumbnail pull-left" href="#"> <img class="media-object" src="${element.imagePath}" style="width: 72px; height: 72px;"> </a>
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
        });
        $("#finalPrice").html(`<h3><strong>${data.totalPrice} SEK</strong></h3>`);
    })

    $('#searchButton').click(function (event) {
        let param = $.trim($('#searchField').val());
        console.log(param)
        display = "showProductsBySearch/" + param;
        location.reload()
    })
});



