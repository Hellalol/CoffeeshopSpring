$(document).ready(function () {

    $.getJSON("src\\main\\webapp\\WEB-INF\\MOCK_DATA.json", function (response) {

        $.each(response, function (result) {
            $("#productbox").append(
                `<div class="product">
            <div class="productname">'+ result.basePrice+ '</div>   
            <div class="productname">'+ result.basePrice+ '</div>
            <div class="productname">'+ result.basePrice+ '</div>
            <button class="minusbtn">-</button>
            <div class="amount">0</div>
            <button class="plusbtn">+</button>
        </div>`
            )
        })
    })
})