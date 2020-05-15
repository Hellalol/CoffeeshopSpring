$(document).ready(function () {
    $.getJSON("MOCK_DATA.json", function (response) {
        response.forEach(element => {
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
                        <td class="col-md-1 text-center"><strong>${element.basePrice} SEK</strong></td> 
                        <td class="col-md-1">
                        <button class="btn btn-secondary" style="margin-left: 40px">-</button>
      <div class="amount" style="display: inline-block">0</div>
      <button class="btn btn-secondary" style="display: inline-block" >+</button>
                        <button type="button" class="btn btn-danger" style="display: inline-block; margin-left: 40px">
                            <span class="glyphicon glyphicon-remove"></span> Remove
                        </button></td>
                    </tr>`).insertBefore($("#afterProducts"))
        });
    })})

