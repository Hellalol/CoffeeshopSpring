$(document).ready(function () {

    function getAllProductsAndReturnName(id) {
        // Skriv kod för att hämta alla produkter spara i en lista,
        // söka igenom listan med hjälp av ID, tar produkten vars id stämmer och skicka tillbaka produkt namnet.

        $.ajax({
            url: "http://localhost:8080/purchase/" + id
        }).then(function (data) {
            console.log(data);
            return data.productName;
        })
    }


    function getAllProductsAndReturnDescription(id) {
        // Skriv kod för att hämta alla produkter spara i en lista,
        // söka igenom listan med hjälp av ID, tar produkten vars id stämmer och skicka tillbaka produkt description.

        $.ajax({
            url: "http://localhost:8080/purchase/" + id
        }).then(function (data) {
            console.log(data);
            return data.description;
        })
    }

    $.ajax({
        url: "http://localhost:8080/purchase/3/",
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
                        <button class="btn btn-secondary" style="margin-left: 40px display: inline-block">-</button>
      <div class="amount" style="display: inline-block"> ${element.quantity}</div>
      <button class="btn btn-secondary" style="display: inline-block" >+</button>
                        <button type="button" class="btn btn-danger" style="display: inline-block margin-left: 40px">
                            <span class="glyphicon glyphicon-remove"></span> Remove
                        </button></td>
                    </tr>`).insertBefore($("#afterProductsCartPage"))
        });
    })



    $.ajax({
        url: "http://localhost:8080/purchase/3/",
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
                                <h5 class="media-heading"> ${element.description}</h5>
                            </div>
                        <td class="col-md-1" style="text-align: center">
                        </td>
                        <td class="col-md-1 text-center"><strong>${element.currentPrice} SEK</strong></td> 
                        <td class="col-md-1">
                        <div class="amount" style="display: inline-block; margin-left: 78px">0</div>
                    </tr>`).insertBefore($("#afterProductsReceiptPage"))
        });
    });
});



