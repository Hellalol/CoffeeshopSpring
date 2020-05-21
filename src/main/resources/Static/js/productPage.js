function addToCartOrCreateNewCartAndAdd(productId) {

    let existing = localStorage.getItem('purches-id');
    let currentCustomerId = localStorage.getItem('customer-id');
    let newPurchesId;

    //om inte pågående purchase finns, skapa ny purchase och lägg till produkt
    if(existing === null){
        $.ajax({
            url: `/purchase/new2/` + currentCustomerId,
            type: 'POST',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (result) {
                newPurchesId = result.id
                localStorage.setItem('purches-id', newPurchesId)
                $.ajax({
                    url: `/purchase/`+ newPurchesId +`/addProductToPurches/` + productId,
                    type: 'POST',
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    success: function (result) {
                    }
                })
                return result
            }
        })
        //om pågående purchase finns. lägg in produkt i den
    }else{
        $.ajax({
            url: `/purchase/`+ existing +`/addProductToPurches/` + productId,
            type: 'POST',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (result) {
            }
        })
    }
}

$(document).ready(function () {

    $("#logout").click(function (event) {
        localStorage.clear()
    });

    let display = "all";
    //start display
    $.ajax({
        url: "http://localhost:8080/product/" + display,
        dataType: "json"
    }).then(function (response) {
        response.forEach(element => {
            $('#afterProductsProductPage').append(`<tr>
                        <td class="col-md-6">
                        <div class="media">
                            <a class="thumbnail pull-left" href="#"> <img class="media-object" src="http://icons.iconarchive.com/icons/custom-icon-design/flatastic-2/72/product-icon.png" style="width: 72px; height: 72px;"> </a>
                            <div class="media-body">
                                <h4 class="media-heading">${element.productName}</h4>
                            </div>
                        <td class="col-md-1" style="text-align: center">
                        </td>
                        <td class="col-md-1 text-center"><strong>${element.currentPrice} SEK</strong></td> 
                        <td class="col-md-1">
                        <button class="btn btn-secondary" style="display: inline-block" onclick="addToCartOrCreateNewCartAndAdd(${element.productId})">Add</button>
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
            display = "all"
            $.ajax({
                url: "http://localhost:8080/product/" + display,
                dataType: "json"
            }).then(function (response) {
                response.forEach(element => {
                    $('#afterProductsProductPage').append(`<tr>
                        <td class="col-md-6">
                        <div class="media">
                            <a class="thumbnail pull-left" href="#"> <img class="media-object" src="http://icons.iconarchive.com/icons/custom-icon-design/flatastic-2/72/product-icon.png" style="width: 72px; height: 72px;"> </a>
                            <div class="media-body">
                                <h4 class="media-heading">${element.productName}</h4>
                            </div>
                        <td class="col-md-1" style="text-align: center">
                        </td>
                        <td class="col-md-1 text-center"><strong>${element.currentPrice} SEK</strong></td> 
                        <td class="col-md-1">
                        <button class="btn btn-secondary" style="display: inline-block" onclick="addToCartOrCreateNewCartAndAdd(${element.productId})">Add</button>
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
            display = "showProductsBySearch/" + param;
            //display för sökresultat
            $.ajax({
                url: "http://localhost:8080/product/" + display,
                dataType: "json"
            }).then(function (response) {
                response.forEach(element => {
                    $('#afterProductsProductPage').append(`<tr>
                        <td class="col-md-6">
                        <div class="media">
                            <a class="thumbnail pull-left" href="#"> <img class="media-object" src="http://icons.iconarchive.com/icons/custom-icon-design/flatastic-2/72/product-icon.png" style="width: 72px; height: 72px;"> </a>
                            <div class="media-body">
                                <h4 class="media-heading">${element.productName}</h4>
                            </div>
                        <td class="col-md-1" style="text-align: center">
                        </td>
                        <td class="col-md-1 text-center"><strong>${element.currentPrice} SEK</strong></td> 
                        <td class="col-md-1">
                        <button class="btn btn-secondary" style="display: inline-block" onclick="addToCartOrCreateNewCartAndAdd(${element.productId})">Add</button>
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
                });

            })
        }
    })
});