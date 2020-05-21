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
                $.ajax({
                    url: `/purchase/`+ newPurchesId +`/addProductToPurches/` + productId,
                    type: 'POST',
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    success: function (result) {
                        console.log("ajax inne i axaj")
                        console.log(result)
                    }
                })
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

$(document).ready(function () {

    $(function () {
        $('.example-popover').popover({
            container: 'body'
        })
    })

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
                        </td>
                        <td>
                            <button type="button" class="btn btn-primary" data-toggle="modal${element.productId}" data-target="#exampleModalCenter${element.productId}">
                        Launch demo modal
                        </button>
                        </td>
                    </tr>`);
            $('#idModel').append(`
                <div className="modal-dialog modal-dialog-centered" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title" id="exampleModalLongTitle${element.productId}">${element.productName}</h5>
                            <button type="button" className="close" data-dismiss="Model${element.productId}" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div className="modal-body">
                            ${element.productDescription}
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" data-dismiss="Model${element.productId}">Close</button>
                        </div>
                    </div>
                </div>`)
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
                        </td>
                        <td>
                        <button type="button" class="btn btn-lg btn-danger" data-toggle="popover" title="Popover title" data-content="And here's some amazing content. It's very engaging. Right?">Click to toggle popover</button>
                        </td>
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
                        </td>
                        <td>
                        <button type="button" class="btn btn-lg btn-danger" data-toggle="popover" title="Popover title" data-content="And here's some amazing content. It's very engaging. Right?">Click to toggle popover</button>                        
                        </td>
                    </tr>`
                });
                $('#afterProductsProductPage').html(template);
                $("#exampleModalLongTitle").html("test");
                $("#exempleModalBodyText").append("test");
            })
        }
    })

    $('#clearSearchAndShowAllButton').click(function (event) {
        display = "all"

    })
});