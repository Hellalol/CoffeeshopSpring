let counter = 0;

function createPurchase() {
    let currentCustomerId = sessionStorage.getItem('customer-id');
    let newPurchesId;
    $.ajax({
        url: `http://localhost:8080/purchase/new2/` + currentCustomerId,
        type: 'POST',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        async: false,
        success: function (result) {
            newPurchesId = result.id
            sessionStorage.setItem('purches-id', newPurchesId)
        }
    })
}

function addToBadge(quantity) {
    document.getElementById("quantityCounter").innerText = quantity;
}

function addToCart(productId) {
    counter = quantityCounter();
    let newPurchesId = sessionStorage.getItem('purches-id');
    //om inte pågående purchase finns, skapa ny purchase och lägg till produkt
    $.ajax({
        url: `http://localhost:8080/purchase/` + newPurchesId + `/addProductToPurches/` + productId,
        type: 'POST',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        async: false,
        success: function () {
        }
    })
    document.getElementById("quantityCounter").innerText = quantityCounter();
}

function quantityCounter() {
    let existing = sessionStorage.getItem('purches-id');
    $.ajax({
        url: `http://localhost:8080/purchase/` + existing,
        type: 'GET',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        async: false,
        success: function (result) {
            counter = result.totalQuantity;
        }
    })
    return counter;
}

$(document).ready(function () {



    counter = quantityCounter();
    if (counter > 0)
        addToBadge(counter);
    let newPurchesId = sessionStorage.getItem('purches-id');
    if (newPurchesId === null) {
        createPurchase();
    }

    let currentCustomerId = sessionStorage.getItem('customer-id');

    $("#logout").click(function (event) {
        sessionStorage.clear()
    });

    let display = "all/" + currentCustomerId;
    //start display
    $.ajax({
        url: "http://localhost:8080/product/" + display,
        dataType: "json",
        async: false,
    }).then(function (response) {
        response.forEach(element => {
            $('#afterProductsProductPage').append(`<tr>
                        <td class="col-md-6">
                        <div class="media">
                            <a class="thumbnail pull-left" href="#"> <img class="media-object" src="${element.imagePath}" style="width: 72px; height: 72px;"> </a>
                            <div class="media-body">
                                <h4 class="media-heading">${element.productName}</h4>
                            </div>
                        <td class="col-md-1" style="text-align: center">
                        </td>
                        <td class="col-md-1 text-center"><strong>${element.currentPrice} SEK</strong></td> 
                        <td class="col-md-1">
                        <button class="glow-on-hover" style="display: inline-block" onclick="addToCart(${element.productId});">Add</button>
                        <td class="col-md-1">
                          <button type="button" class="btn btn-secondary" data-container="body" data-toggle="popover" 
                          data-placement="right">
                              Info
                          </button>
                        </td>                    
                    
                    </tr>`);
            $('[data-toggle="popover"]').popover({
                title: `<strong>Name: ${element.productName}</strong>`,
                content: `Description: ${element.productDescription} <br>
                Price: ${element.currentPrice} SEK`,
                html: true,
                placement: "right",
                trigger: 'focus',
            });

        })
    })

//display för tomt sökresultat

    $('#searchButton').click(function (event) {
        let param = $.trim($('#searchField').val());
        let template = ``;
        $('#afterProductsProductPage').last().empty()
        if (param.length === 0) {
            display = "all/" + currentCustomerId;
            $.ajax({
                url: "http://localhost:8080/product/" + display,
                dataType: "json",
                async: false,
            }).then(function (response) {
                response.forEach(element => {
                    $('#afterProductsProductPage').append(`<tr>
                        <td class="col-md-6">
                        <div class="media">
                            <a class="thumbnail pull-left" href="#"> <img class="media-object" src="${element.imagePath}" style="width: 72px; height: 72px;"> </a>
                            <div class="media-body">
                                <h4 class="media-heading">${element.productName}</h4>
                            </div>
                        <td class="col-md-1" style="text-align: center">
                        </td>
                        <td class="col-md-1 text-center"><strong>${element.currentPrice} SEK</strong></td> 
                        <td class="col-md-1">
                        <button class="glow-on-hover" style="display: inline-block" onclick="addToCart(${element.productId}); quantityCounter();">Add</button>
                        <td class="col-md-1">
                          <button type="button" class="btn btn-secondary" data-container="body" data-toggle="popover" 
                          data-placement="right">
                              Info
                          </button>
                        </td>   
                    </tr>`);
                    $('[data-toggle="popover"]').popover({
                        title: `<strong>Name: ${element.productName}</strong>`,
                        content: `Description: ${element.productDescription} <br>
                Price: ${element.currentPrice} SEK`,
                        html: true,
                        placement: "right",
                        trigger: 'focus',
                    });
                })
            })
        } else {
            display = "showProductsBySearch/" + param + "/" + currentCustomerId;
            //display för sökresultat
            $.ajax({
                url: "http://localhost:8080/product/" + display,
                dataType: "json",
                async: false,
            }).then(function (response) {
                response.forEach(element => {
                    $('#afterProductsProductPage').append(`<tr>
                        <td class="col-md-6">
                        <div class="media">
                            <a class="thumbnail pull-left" href="#"> <img class="media-object" src="${element.imagePath}" style="width: 72px; height: 72px;"> </a>
                            <div class="media-body">
                                <h4 class="media-heading">${element.productName}</h4>
                            </div>
                        <td class="col-md-1" style="text-align: center">
                        </td>
                        <td class="col-md-1 text-center"><strong>${element.currentPrice} SEK</strong></td> 
                        <td class="col-md-1">
                        <button class="glow-on-hover" style="display: inline-block" onclick="addToCart(${element.productId}); quantityCounter();">Add</button>
                        <td class="col-md-1">
                          <button type="button" class="btn btn-secondary" data-container="body" data-toggle="popover" 
                          data-placement="right">
                              Info
                          </button>
                        </td>   
                    </tr>`);
                    $('[data-toggle="popover"]').popover({
                        title: `<strong>Name: ${element.productName}</strong>`,
                        content: `Description: ${element.productDescription} <br>
                Price: ${element.currentPrice} SEK`,
                        html: true,
                        placement: "right",
                        trigger: 'focus'
                    });
                });
            })
        }
    })
});