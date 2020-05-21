function addToCartOrCreateNewCartAndAdd(productId) {

    addPurchase();
    addEntry(productId);
}

function addPurchase() {
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
            }
        })
    }
}

function addEntry(productId) {
    let existing = localStorage.getItem('purches-id');
    console.log(existing)

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

$(document).ready(function () {
    let display = "all";

    $.ajax({
        url: "http://localhost:8080/product/" + display,
        dataType: "json"
    }).then(function (response) {
        console.log(response);
        response.forEach(element => {
            $('#afterProductsProductPage').append(`<tr>
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
                    </tr>`);

        })
    })


    $('#searchButton').click(function (event) {
        let param = $.trim($('#searchField').val());
        let template = ``;
        if (param.length === 0) {
            display = "all"
            $('#afterProductsProductPage').empty();
            $.ajax({
                url: "http://localhost:8080/product/" + display,
                dataType: "json"
            }).then(function (response) {
                console.log(response);
                response.forEach(element => {
                    $('#afterProductsProductPage').append(`<tr>
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
                    </tr>`);


                })
            })
        } else {

            console.log(param)
            display = "showProductsBySearch/" + param;
            console.log(display)

            $.ajax({
                url: "http://localhost:8080/product/" + display,
                dataType: "json"
            }).then(function (response) {
                console.log(response);
                response.forEach(element => {
                    template += `<tr>
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
                    </tr>`
                });
                $('#afterProductsProductPage').html(template);
            })
        }
    })

    $('#clearSearchAndShowAllButton').click(function (event) {
        display = "all"

    })
});